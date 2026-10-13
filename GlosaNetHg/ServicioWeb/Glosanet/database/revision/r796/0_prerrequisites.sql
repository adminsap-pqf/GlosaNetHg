USE GlosaNet;

-- SI NO EXISTE, AGREGA EL NUEVO PERMISO DE VUCEM
INSERT INTO GN_Permisos (PK_Permiso, Permiso )
SELECT 13 PK_Permiso, 'Vucem' Permiso
WHERE NOT EXISTS (SELECT 1 FROM GN_Permisos gp WHERE gp.Permiso = 'Vucem') LIMIT 1;

-- AGREGA EL PERMISO DE VUCEM DESACTIVADO A TODOS LOS USUARIOS EXISTENTES
INSERT INTO GN_Usuario_Permisos (FK01_Usuario, FK02_Permiso, Activo)
SELECT DISTINCT FK01_Usuario, (SELECT PK_Permiso FROM GN_Permisos gp WHERE Permiso = 'Vucem') FK02_Permiso, 0 Activo FROM GN_Usuario_Permisos gup 
WHERE gup.FK01_Usuario NOT IN (SELECT FK01_Usuario FROM GN_Usuario_Permisos gup WHERE FK02_Permiso = (SELECT PK_Permiso FROM GN_Permisos gp WHERE Permiso = 'Vucem'));

-- AGREGAR EL PERMISO DE VUCEM ACTIVADO A USUARIOS ESPECIFICOS
UPDATE GN_Usuario_Permisos SET Activo = 1 
WHERE FK02_Permiso = (SELECT PK_Permiso FROM GN_Permisos gp WHERE Permiso = 'Vucem') AND FK01_Usuario IN (6, 8);