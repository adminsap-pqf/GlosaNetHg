-- =====================================================================
-- GUÍA PASO A PASO: CREAR CLIENTE NUEVO EN GLOSANET
-- =====================================================================
-- Conexión: DataGrip → GlosaNet PROD (o DEV/QA según corresponda)
-- Antes de empezar:
--   1. Cambia las contraseñas en texto plano por SHA1 (ver Paso 5).
--   2. Si el cliente usará VUCEM, ten el token a la mano antes del Paso 7.
--   3. Esta guía ABORTA si el RFC ya existe — créalo nuevo.
-- =====================================================================

-- =====================================================================
-- PASO 0: PRE-FLIGHT (solo lectura — verificar catálogos)
-- =====================================================================
-- Qué hace: confirma que GN_Paquete y GN_Permisos tienen datos antes
-- de cualquier INSERT. Si GN_Paquete está vacío, los INSERTs de cliente
-- fallarán por la FK01_Paquete.
SELECT 'PRE-FLIGHT: Catálogo de Paquetes' AS paso;
SELECT PK_Paquete, Nombre, Tipo, Usuarios, Pedimentos, Costo_Mensual
FROM GlosaNet.GN_Paquete
ORDER BY PK_Paquete;

SELECT 'PRE-FLIGHT: Catálogo de Permisos' AS paso;
SELECT PK_Permiso, Permiso
FROM GlosaNet.GN_Permisos
ORDER BY PK_Permiso;

SELECT 'PRE-FLIGHT: Verificar que el cliente NO existe' AS paso;
SELECT PK_Cliente, Nombre, RFC
FROM GlosaNet.GN_Cliente
WHERE RFC = 'AIC783456BN9';          -- ⚠️ cambiar por el RFC real del cliente nuevo

-- Si esta consulta trae una fila, el cliente YA EXISTE y NO debes continuar.
-- Si trae 0 filas, sigue adelante.

-- =====================================================================
-- PASO 1: CREAR EL CLIENTE
-- =====================================================================
-- Qué hace: inserta el registro raíz. Devuelve @idCliente que se reutiliza
-- en todos los pasos siguientes.
SELECT 'PASO 1: Crear cliente' AS paso;

INSERT INTO GlosaNet.GN_Cliente (
    Nombre, Representante, RFC, RazonSocial, Habilitado,
    Telefono, FCreacion, FUA, FAuditable,
    RegimenFiscal, UsoCFDI, claveProducto, FK01_Paquete
) VALUES (
    'Astrum Impulsor',                    -- Nombre comercial
    'Sergio Zavala',                      -- Representante legal
    'AIC783456BN9',                       -- RFC del cliente (ÚNICO)
    'Astrum Impulsor SA de CV',           -- Razón social completa
    1,                                    -- Habilitado
    '5555550100',                         -- Teléfono
    CURDATE(), CURDATE(), CURDATE(),
    '601',                                -- Régimen fiscal CFDI
    'G03',                                -- Uso CFDI
    'CLI-AIC-001',                        -- Clave interna
    (SELECT PK_Paquete FROM GlosaNet.GN_Paquete
     WHERE Nombre = 'Empresarial' LIMIT 1)   -- Nombre del paquete contratado
);

SET @idCliente = LAST_INSERT_ID();
SELECT @idCliente AS cliente_creado;

-- ⚠️ Si el INSERT falla por FK01_Paquete, el catálogo GN_Paquete está vacío.
--    Pide a Ryndem los paquetes semilla antes de continuar.

-- =====================================================================
-- PASO 2: CREAR EMPRESA MATRIZ
-- =====================================================================
-- Qué hace: crea la primera empresa (matriz) bajo el cliente recién creado.
-- FK_Cliente = @idCliente.
SELECT 'PASO 2: Crear empresa matriz' AS paso;

INSERT INTO GlosaNet.GN_Empresa (
    RazonSocial, RFC, Pais, Estado, Calle, Delegacion, Municipio, CP,
    AEmpresarial, Email, telefono,
    MSeguridad, Vigencia, VigenciaMatriz, VigenciaMatrizFin,
    Auditable, Representante, FK_Cliente
) VALUES (
    'Astrum Impulsor SA de CV',
    'Madrid, Gómez y Asociados',
    'España', 'Comunidad de Madrid',
    'Calle Mayor 1', 'Centro', 'Madrid', '28013',
    'Matriz',
    'contacto@astrum.es', '911234567',
    0,                                    -- MSeguridad
    NOW(),                                -- Vigencia
    NULL, NULL,                           -- VigenciaMatriz, VigenciaMatrizFin
    NULL,                                 -- Auditable
    'Sergio Zavala',
    @idCliente
);

-- ⚠️ OPCIONAL: si el cliente tiene filiales con RFC distinto, repite este
--    bloque cambiando RazonSocial, RFC, domicilio y representante.

-- =====================================================================
-- PASO 3: CREAR USUARIO ADMINISTRADOR
-- =====================================================================
-- Qué hace: crea el usuario que tendrá Consola=1 (puede entrar a admin).
-- Se liga al CLIENTE (@idCliente), no a una empresa específica.
-- Por eso este usuario ve todas las empresas del cliente.
SELECT 'PASO 3: Crear usuario administrador' AS paso;

INSERT INTO GlosaNet.GN_Usuarios (
    usuario, password, FK01_Cliente, Permiso, Habilitado, Consola,
    Nombre, Apellido, Email, Telefono, Ext, fcreacion
) VALUES (
    'admin.acerezo',                                  -- Login único
    SHA1('CambiarEsto.123'),                         -- ⚠️ SHA-1, no texto plano
    @idCliente,
    'PermisoAdmin',                                  -- String legacy
    1, 1,                                            -- Habilitado, Consola
    'Alejandro', 'Cerezo',
    'admin@astrum.mx', '5555550010', '100',
    NOW()
);

SET @idUsuarioAdmin = LAST_INSERT_ID();
SELECT @idUsuarioAdmin AS usuario_admin_creado;

-- =====================================================================
-- PASO 4: CREAR USUARIO OPERADOR
-- =====================================================================
-- Qué hace: crea un usuario sin consola (no admin). Repite este bloque
-- para más operadores cambiando usuario, password, datos personales.
SELECT 'PASO 4: Crear usuario operador' AS paso;

INSERT INTO GlosaNet.GN_Usuarios (
    usuario, password, FK01_Cliente, Permiso, Habilitado, Consola,
    Nombre, Apellido, Email, Telefono, Ext, fcreacion
) VALUES (
    'jperez',
    SHA1('Operador2026!'),
    @idCliente,
    'PermisoOperador',
    1, 0,                                            -- Habilitado, Consola=0
    'Juan', 'Pérez López',
    'juan.perez@astrum.mx', '5555550011', '101',
    NOW()
);

SET @idUsuarioOperador = LAST_INSERT_ID();
SELECT @idUsuarioOperador AS usuario_operador_creado;

-- =====================================================================
-- PASO 5: ASIGNAR PERMISOS
-- =====================================================================
-- Qué hace: inserta en GN_Usuario_Permisos una fila por cada permiso del
-- sistema, vinculando a cada usuario con Activo=1 (activo) o 0 (inactivo).
SELECT 'PASO 5: Asignar permisos' AS paso;

-- 5a) Administrador → TODOS los permisos activos
INSERT INTO GlosaNet.GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT @idUsuarioAdmin, PK_Permiso, 1
FROM GlosaNet.GN_Permisos;

-- 5b) Operador → solo algunos permisos activos, los demás explícitamente 0
-- ⚠️ Ajusta la lista IN (...) según los permisos que quieras darle.
-- Los que NO estén en el IN se insertan Activo=0 (desactivados).
INSERT INTO GlosaNet.GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT @idUsuarioOperador, PK_Permiso, 1
FROM GlosaNet.GN_Permisos
WHERE Permiso IN ('Previsión', 'Reporte');

INSERT INTO GlosaNet.GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT @idUsuarioOperador, PK_Permiso, 0
FROM GlosaNet.GN_Permisos
WHERE Permiso NOT IN ('Previsión', 'Reporte');

-- =====================================================================
-- PASO 6: VERIFICACIÓN INTERMEDIA
-- =====================================================================
-- Qué hace: muestra la jerarquía completa creada hasta el momento.
-- NO modifica nada. Si ves algo mal, borra manualmente antes del Paso 7.
SELECT 'PASO 6: Verificación de la jerarquía cliente/empresa/usuario' AS paso;

SELECT c.PK_Cliente, c.Nombre AS cliente,
       e.PK_Empresa, e.RazonSocial, e.RFC,
       u.PK_Usuario, u.usuario, u.Habilitado, u.Consola,
       COUNT(up.PK_Usuario_Permiso) AS permisos_asignados,
       SUM(CASE WHEN up.Activo = 1 THEN 1 ELSE 0 END) AS permisos_activos
FROM GlosaNet.GN_Cliente c
LEFT JOIN GlosaNet.GN_Empresa         e  ON e.FK_Cliente   = c.PK_Cliente
LEFT JOIN GlosaNet.GN_Usuarios        u  ON u.FK01_Cliente = c.PK_Cliente
LEFT JOIN GlosaNet.GN_Usuario_Permisos up ON up.FK01_Usuario = u.PK_Usuario
WHERE c.PK_Cliente = @idCliente
GROUP BY c.PK_Cliente, e.PK_Empresa, u.PK_Usuario
ORDER BY e.PK_Empresa, u.PK_Usuario;

-- Esperado:
--   1 cliente × N empresas × 2 usuarios
--   permisos_asignados = total del catálogo (13 si Vucem está sembrado)
--   permisos_activos para admin = 13; para operador = 2 (los del WHERE IN)

-- =====================================================================
-- PASO 7: TOKEN VUCEM (OPCIONAL)
-- =====================================================================
-- Qué hace: si el cliente usará descarga automática de EDoc/COVE/acuses,
-- inserta el token que VUCEM le entregó.
--
-- ⚠️ ANTES DE EJECUTAR:
--   1. Cliente debe tener FIEL vigente.
--   2. Cliente debe entrar a VUCEM y autorizar a Glosanet como sistema tercero.
--   3. VUCEM le devuelve un token (cadena alfanumérica ~64 chars).
--   4. Cliente te lo pasa por canal seguro.
SELECT 'PASO 7: Token VUCEM (opcional)' AS paso;

-- OPCIÓN A — El cliente ya tiene token (recomendado):
INSERT INTO GlosaNet.GN_Vucem_Token (FK01_Cliente, Token, activo)
VALUES (@idCliente, 'TOKEN_QUE_VUCEM_TE_DIO_AQUI', 1);

-- OPCIÓN B — El cliente aún no tiene token (dummy desactivado):
-- INSERT INTO GlosaNet.GN_Vucem_Token (FK01_Cliente, Token, activo)
-- VALUES (@idCliente, 'PENDIENTE', 0);

-- ⚠️ Si ejecutas OPCIÓN A y luego el cliente renueva el token, haz UPDATE:
-- UPDATE GlosaNet.GN_Vucem_Token
-- SET Token = 'NUEVO_TOKEN', activo = 1
-- WHERE FK01_Cliente = @idCliente;

-- =====================================================================
-- PASO 8: VERIFICACIÓN FINAL
-- =====================================================================
SELECT 'PASO 8: Verificación final del cliente' AS paso;

-- 8a) Datos básicos
SELECT PK_Cliente, Nombre, RFC, RazonSocial, Habilitado, FK01_Paquete
FROM GlosaNet.GN_Cliente
WHERE PK_Cliente = @idCliente;

-- 8b) Empresas
SELECT PK_Empresa, RazonSocial, RFC, Pais, Estado, FK_Cliente
FROM GlosaNet.GN_Empresa
WHERE FK_Cliente = @idCliente;

-- 8c) Usuarios con conteo de permisos
SELECT u.PK_Usuario, u.usuario, u.Nombre, u.Apellido, u.Habilitado, u.Consola,
       (SELECT COUNT(*) FROM GlosaNet.GN_Usuario_Permisos
        WHERE FK01_Usuario = u.PK_Usuario) AS permisos_total,
       (SELECT COUNT(*) FROM GlosaNet.GN_Usuario_Permisos
        WHERE FK01_Usuario = u.PK_Usuario AND Activo = 1) AS permisos_activos
FROM GlosaNet.GN_Usuarios u
WHERE u.FK01_Cliente = @idCliente;

-- 8d) Token VUCEM (si se creó)
SELECT PK_Token, FK01_Cliente, LEFT(Token, 12) AS token_preview,
       activo, LastUpdateDate
FROM GlosaNet.GN_Vucem_Token
WHERE FK01_Cliente = @idCliente;

-- =====================================================================
-- PASO 9: ROLLBACK (solo si algo salió mal — déjalo comentado)
-- =====================================================================
-- ⚠️ Si necesitas revertir todo, ejecuta este bloque completo en orden.
-- Borra en cascada gracias a las FK del enunciado.

-- DELETE FROM Cliente
-- DELETE FROM GlosaNet.GN_Vucem_Token     WHERE FK01_Cliente = @idCliente;
-- DELETE FROM GlosaNet.GN_Usuario_Permisos
--   WHERE FK01_Usuario IN (
--     SELECT PK_Usuario FROM GlosaNet.GN_Usuarios WHERE FK01_Cliente = @idCliente
--   );
-- DELETE FROM GlosaNet.GN_Usuarios        WHERE FK01_Cliente = @idCliente;
-- DELETE FROM GlosaNet.GN_Empresa         WHERE FK_Cliente   = @idCliente;
-- DELETE FROM GlosaNet.GN_Cliente         WHERE PK_Cliente   = @idCliente;
--
-- SELECT 'ROLLBACK COMPLETO' AS resultado;

-- =====================================================================
-- FIN DE LA GUÍA
-- =====================================================================