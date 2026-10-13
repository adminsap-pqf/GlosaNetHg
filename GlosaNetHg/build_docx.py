#!/usr/bin/env python3
"""Genera la guía paso a paso en formato .docx (Word real)."""
from docx import Document
from docx.shared import Pt, RGBColor, Inches
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.oxml.ns import qn
from docx.oxml import OxmlElement

OUT = r"C:\Users\Fernando Betanzos\Documents\GlosaNetHg\guia_alta_cliente_nuevo.docx"

doc = Document()

# Estilo normal
style = doc.styles["Normal"]
style.font.name = "Calibri"
style.font.size = Pt(11)

# ---------- Helpers ----------
def add_title(text):
    p = doc.add_heading(text, level=0)
    return p

def add_h1(text):
    return doc.add_heading(text, level=1)

def add_h2(text):
    return doc.add_heading(text, level=2)

def add_para(text, bold=False):
    p = doc.add_paragraph()
    run = p.add_run(text)
    run.bold = bold
    return p

def add_bullet(text):
    return doc.add_paragraph(text, style="List Bullet")

def add_number(text):
    return doc.add_paragraph(text, style="List Number")

def add_code(text):
    """Bloque de código SQL con fuente monoespaciada y fondo gris."""
    p = doc.add_paragraph()
    p.paragraph_format.left_indent = Inches(0.2)
    p.paragraph_format.space_before = Pt(2)
    p.paragraph_format.space_after = Pt(2)
    # fondo gris suave
    pPr = p._p.get_or_add_pPr()
    shd = OxmlElement("w:shd")
    shd.set(qn("w:val"), "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"), "F2F2F2")
    pPr.append(shd)
    run = p.add_run(text)
    run.font.name = "Consolas"
    run.font.size = Pt(9)
    return p

def add_table(headers, rows):
    table = doc.add_table(rows=1 + len(rows), cols=len(headers))
    table.style = "Light Grid Accent 1"
    hdr = table.rows[0].cells
    for i, h in enumerate(headers):
        hdr[i].text = h
        for r in hdr[i].paragraphs[0].runs:
            r.bold = True
    for ri, row in enumerate(rows):
        for ci, val in enumerate(row):
            table.rows[1 + ri].cells[ci].text = val
    return table

# ---------- Portada ----------
add_title("Guía paso a paso")
p = doc.add_paragraph()
r = p.add_run("Alta de cliente nuevo en Glosanet (MySQL)")
r.bold = True
r.font.size = Pt(16)

p = doc.add_paragraph()
p.add_run("Versión: ").bold = True
p.add_run("1.0\n")
p.add_run("Aplicación: ").bold = True
p.add_run("GlosaNet — commons 1.0.0\n")
p.add_run("Esquema de BD: ").bold = True
p.add_run("GlosaNet\n")
p.add_run("Conexión sugerida en DataGrip: ").bold = True
p.add_run("GlosaNet PROD / Pruebas R9\n")

add_para("")

# ---------- Objetivo y pre-requisitos ----------
add_h1("Objetivo")
add_para(
    "Crear un cliente de cero en la base de datos de Glosanet, junto con sus empresas, "
    "usuarios y permisos, siguiendo el orden correcto para evitar errores de FK y "
    "mantener la integridad referencial."
)

add_h1("Pre-requisitos antes de empezar")
add_number("Conexión activa a la BD GlosaNet desde DataGrip (idealmente primero en Pruebas R9).")
add_number("Catálogo GN_Paquete poblado — verificar con el SELECT del Paso 0.")
add_number("RFC del cliente nuevo (no debe existir previamente en GN_Cliente).")
add_number("Nombre del paquete contratado por el cliente.")
add_number("Datos fiscales: razón social, representante, domicilio fiscal, régimen fiscal, uso CFDI.")
add_number("Para cada usuario: login, contraseña, nombre, apellido, email, teléfono.")
add_number("Si el cliente usará VUCEM: FIEL vigente del cliente + token entregado por VUCEM.")
add_number("Conocimiento de la contraseña en SHA-1 (ver Paso 5).")

add_para("")

# ---------- Cómo se ejecuta ----------
add_h1("Cómo ejecutar esta guía")
add_para(
    "Cada paso es un bloque numerado que debe correrse en orden. Se recomienda ejecutar "
    "paso a paso y revisar la salida antes de continuar. En DataGrip:"
)
add_bullet("Abre New → Query Console sobre la BD GlosaNet.")
add_bullet("Copia el contenido de cada paso en la consola.")
add_bullet("Resalta el bloque del paso a ejecutar y presiona Ctrl+Enter (F5).")
add_bullet("Revisa la salida en el panel inferior antes de avanzar al siguiente paso.")
add_para(
    "Importante: las contraseñas nunca se guardan en texto plano. Glosanet las almacena "
    "como SHA-1 hex de 40 caracteres. En MySQL puedes usar SHA1('miclave') directamente.",
    bold=False,
)

add_para("")

# ---------- Bloque por paso ----------
def paso(num, titulo, hace, sql, nota=None):
    add_h1(f"Paso {num} — {titulo}")
    add_para("Qué hace:", bold=True)
    add_para(hace)
    if nota:
        add_para("Notas importantes:", bold=True)
        for n in nota:
            add_bullet(n)
    add_para("SQL a ejecutar:", bold=True)
    add_code(sql)
    add_para("")

# ---------- Paso 0 ----------
paso(0, "Pre-flight (solo lectura)",
     "Verifica que los catálogos necesarios están poblados y que el cliente "
     "NO existe previamente. Esta consulta NO modifica nada.",
     """-- Catálogo de paquetes
SELECT PK_Paquete, Nombre, Tipo, Usuarios, Pedimentos, Costo_Mensual
FROM GlosaNet.GN_Paquete
ORDER BY PK_Paquete;

-- Catálogo de permisos
SELECT PK_Permiso, Permiso
FROM GlosaNet.GN_Permisos
ORDER BY PK_Permiso;

-- Verificar que el cliente NO existe
SELECT PK_Cliente, Nombre, RFC
FROM GlosaNet.GN_Cliente
WHERE RFC = 'AIC783456BN9';
-- ⚠️ Si trae una fila, el cliente YA EXISTE. Detente aquí.""",
     [
         "Si GN_Paquete está vacío, el Paso 1 fallará por FK01_Paquete. "
         "Pide a Ryndem los paquetes semilla antes de continuar.",
         "La búsqueda por RFC es solo validación. Si ya existe cliente con ese RFC, "
         "esta guía no aplica.",
     ],
)

# ---------- Paso 1 ----------
paso(1, "Crear el cliente",
     "Inserta el registro raíz en GN_Cliente. Devuelve @idCliente, que se "
     "reutiliza en todos los pasos siguientes.",
     """INSERT INTO GlosaNet.GN_Cliente (
    Nombre, Representante, RFC, RazonSocial, Habilitado,
    Telefono, FCreacion, FUA, FAuditable,
    RegimenFiscal, UsoCFDI, claveProducto, FK01_Paquete
) VALUES (
    'Astrum Impulsor',                       -- Nombre comercial
    'Sergio Zavala',                         -- Representante legal
    'AIC783456BN9',                          -- RFC del cliente (ÚNICO)
    'Astrum Impulsor SA de CV',              -- Razón social completa
    1,                                       -- Habilitado
    '5555550100',                            -- Teléfono
    CURDATE(), CURDATE(), CURDATE(),
    '601',                                   -- Régimen fiscal CFDI
    'G03',                                   -- Uso CFDI
    'CLI-AIC-001',                           -- Clave interna
    (SELECT PK_Paquete FROM GlosaNet.GN_Paquete
     WHERE Nombre = 'Empresarial' LIMIT 1)   -- Nombre del paquete contratado
);

SET @idCliente = LAST_INSERT_ID();
SELECT @idCliente AS cliente_creado;""",
     [
         "Sustituye los valores entre comillas por los datos reales del cliente.",
         "Si el INSERT falla por FK01_Paquete, GN_Paquete está vacío.",
     ],
)

# ---------- Paso 2 ----------
paso(2, "Crear empresa matriz",
     "Crea la primera empresa (matriz) bajo el cliente recién creado. "
     "FK_Cliente se ata al @idCliente del Paso 1. Si el cliente tiene filiales, "
     "repite este bloque cambiando RazonSocial, RFC y domicilio.",
     """INSERT INTO GlosaNet.GN_Empresa (
    RazonSocial, RFC, Pais, Estado, Calle, Delegacion, Municipio, CP,
    AEmpresarial, Email, telefono,
    MSeguridad, Vigencia, VigenciaMatriz, VigenciaMatrizFin,
    Auditable, Representante, FK_Cliente
) VALUES (
    'Astrum Impulsor SA de CV',
    'AIC783456BN9',
    'México', 'CIUDAD DE MEXICO',
    'Av. Reforma 100', 'Cuauhtémoc', 'Cuauhtémoc', '06600',
    'Matriz',
    'contacto@astrum.mx', '5555550100',
    0,                                       -- MSeguridad
    NOW(),                                   -- Vigencia
    NULL, NULL,                              -- VigenciaMatriz, VigenciaMatrizFin
    NULL,                                    -- Auditable
    'Sergio Zavala',
    @idCliente
);

-- Para más empresas (filiales), repite este bloque con RFC distinto:
-- INSERT INTO GlosaNet.GN_Empresa ( ... ) VALUES ( ... otra empresa ... );""",
     [
         "RFC distinto por empresa. Repetir RFC bajo el mismo cliente confunde los JOINs "
         "de pedimentos.",
         "VigenciaMatriz/VigenciaMatrizFin se llenan después, al subir la Matriz de Seguridad.",
     ],
)

# ---------- Paso 3 ----------
paso(3, "Crear usuario administrador",
     "Inserta el usuario con Consola=1 (acceso admin). Se liga al CLIENTE, "
     "no a una empresa específica, por lo que ve todas las empresas del cliente.",
     """INSERT INTO GlosaNet.GN_Usuarios (
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
SELECT @idUsuarioAdmin AS usuario_admin_creado;""",
     [
         "Si omites SHA1(), la contraseña queda en texto plano y el login desde "
         "la UI no funcionará.",
         "Contraseña máxima 18 caracteres (limitación del campo password VARCHAR(18)).",
     ],
)

# ---------- Paso 4 ----------
paso(4, "Crear usuario operador",
     "Inserta un usuario sin consola (no admin). Repite este bloque para más "
     "operadores cambiando login, password y datos personales.",
     """INSERT INTO GlosaNet.GN_Usuarios (
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
SELECT @idUsuarioOperador AS usuario_operador_creado;""",
)

# ---------- Paso 5 ----------
paso(5, "Asignar permisos",
     "Inserta en GN_Usuario_Permisos una fila por cada permiso del catálogo, "
     "vinculando a cada usuario con Activo=1 (activo) o 0 (inactivo). "
     "Sin este paso, los usuarios no ven nada en la UI.",
     """-- 5a) Administrador → TODOS los permisos activos
INSERT INTO GlosaNet.GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT @idUsuarioAdmin, PK_Permiso, 1
FROM GlosaNet.GN_Permisos;

-- 5b) Operador → solo algunos permisos activos, los demás desactivados
INSERT INTO GlosaNet.GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT @idUsuarioOperador, PK_Permiso, 1
FROM GlosaNet.GN_Permisos
WHERE Permiso IN ('Previsión', 'Reporte');

INSERT INTO GlosaNet.GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT @idUsuarioOperador, PK_Permiso, 0
FROM GlosaNet.GN_Permisos
WHERE Permiso NOT IN ('Previsión', 'Reporte');""",
     [
         "Ajusta la lista IN(...) según los permisos que quieras dar al operador.",
     ],
)

# ---------- Paso 6 ----------
paso(6, "Verificación intermedia",
     "Muestra la jerarquía completa creada. NO modifica nada. Si algo está mal, "
     "ejecuta el Paso 9 (rollback) antes de continuar.",
     """SELECT c.PK_Cliente, c.Nombre AS cliente,
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
ORDER BY e.PK_Empresa, u.PK_Usuario;""",
)

# ---------- Paso 7 ----------
paso(7, "Token VUCEM (opcional)",
     "Si el cliente usará descarga automática de EDoc/COVE/acuses, inserta el token "
     "que VUCEM le entregó. Si no lo tiene aún, usa la opción B con activo=0.",
     """-- OPCIÓN A — el cliente ya tiene token:
INSERT INTO GlosaNet.GN_Vucem_Token (FK01_Cliente, Token, activo)
VALUES (@idCliente, 'TOKEN_QUE_VUCEM_TE_DIO_AQUI', 1);

-- OPCIÓN B — el cliente aún no tiene token (dummy desactivado):
-- INSERT INTO GlosaNet.GN_Vucem_Token (FK01_Cliente, Token, activo)
-- VALUES (@idCliente, 'PENDIENTE', 0);

-- Renovar token más adelante:
-- UPDATE GlosaNet.GN_Vucem_Token
-- SET Token = 'NUEVO_TOKEN', activo = 1
-- WHERE FK01_Cliente = @idCliente;""",
     [
         "FK01_Cliente tiene UNIQUE INDEX — no se pueden tener dos tokens activos para "
         "el mismo cliente.",
         "El token lo emite VUCEM, no Glosanet ni Ryndem.",
     ],
)

# ---------- Paso 8 ----------
paso(8, "Verificación final",
     "SELECTs desglosados para confirmar que todo quedó correcto. NO modifica nada.",
     """-- 8a) Cliente
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
WHERE FK01_Cliente = @idCliente;""",
)

# ---------- Paso 9 ----------
paso(9, "Rollback (emergencia)",
     "Borra en orden inverso respetando las FK. Ejecutar solo si necesitas "
     "revertir todo lo creado en esta guía.",
     """DELETE FROM GlosaNet.GN_Vucem_Token
  WHERE FK01_Cliente = @idCliente;

DELETE FROM GlosaNet.GN_Usuario_Permisos
  WHERE FK01_Usuario IN (
    SELECT PK_Usuario FROM GlosaNet.GN_Usuarios WHERE FK01_Cliente = @idCliente
  );

DELETE FROM GlosaNet.GN_Usuarios WHERE FK01_Cliente = @idCliente;
DELETE FROM GlosaNet.GN_Empresa   WHERE FK_Cliente   = @idCliente;
DELETE FROM GlosaNet.GN_Cliente   WHERE PK_Cliente   = @idCliente;

SELECT 'ROLLBACK COMPLETO' AS resultado;""",
)

# ---------- Tabla resumen ----------
add_h1("Resumen de pasos")
add_table(
        ["Paso", "Acción", "Riesgo si falla"],
        [
            ["0", "Pre-flight (catálogos)", "Solo lectura. Abortar si hay duplicado."],
            ["1", "Crear cliente", "FK01_Paquete falla si no hay paquetes."],
            ["2", "Crear empresa(s)", "RFC duplicado = reportes rotos."],
            ["3", "Crear admin", "Password sin SHA1() = login roto."],
            ["4", "Crear operador", "Password sin SHA1() = login roto."],
            ["5", "Asignar permisos", "Si se omite, el usuario no ve nada."],
            ["6", "Verificación intermedia", "No modifica."],
            ["7", "Token VUCEM (opcional)", "Solo si el cliente lo va a usar."],
            ["8", "Verificación final", "No modifica."],
            ["9", "Rollback", "Solo en desastre."],
        ],
)

# ---------- Recomendaciones ----------
add_h1("Recomendaciones operativas")
add_bullet("Correr toda la guía primero en Pruebas R9 antes de tocar PROD.")
add_bullet("Sustituir los valores entre comillas por los datos reales del cliente.")
add_bullet("Si el cliente tiene más de 2 usuarios, duplique los Pasos del SQL separado.")
add_bullet("Si hay más empresas (filiales), duplique el bloque del Paso 2 con RFC distinto.")
add_bullet("Después de ejecutar, forzar cambio de contraseña en el primer login.")
add_bullet("Solicitar a Ryndem migrar el hash de SHA-1 sin sal a bcrypt o Argon2 — "
           "es un cambio mediano pero necesario para producción seria.")

add_h1("Lo que esta guía NO hace (debes hacerlo por separado)")
add_bullet("Subir la Matriz de Seguridad desde la UI Configuraciones → CARGAR MATRIZ.")
add_bullet("Subir el logo de la empresa desde Configuraciones → CARGAR LOGO.")
add_bullet("Validar manualmente la conexión VUCEM desde la UI.")
add_bullet("Rotar las contraseñas en el primer login (limitación del modelo).")

# ---------- Aviso ----------
add_h1("Aviso de seguridad")
add_para(
    "Glosanet almacena contraseñas con SHA-1 sin salt, lo cual es inseguro por diseño "
    "(vulnerable a rainbow tables y colisiones). Si necesitas rotación periódica, "
    "debes solicitarla al equipo de Ryndem. Esta guía no toca la columna password "
    "sin usar SHA1().",
    bold=False,
)

# ---------- Footer ----------
doc.add_paragraph()
p = doc.add_paragraph()
r = p.add_run("Documento generado automáticamente. Revisa y ajusta antes de aplicar en producción.")
r.italic = True
r.font.color.rgb = RGBColor(0x80, 0x80, 0x80)

doc.save(OUT)
print(f"OK: {OUT}")