--
-- Set default database
--
USE GlosaNet;

--
-- Create table `GN_Vucem_Token`
--
CREATE TABLE IF NOT EXISTS GN_Vucem_Token (
  PK_Token INT(11) NOT NULL AUTO_INCREMENT,
  FK01_Cliente INT(11) NOT NULL,
  Token VARCHAR(70) DEFAULT NULL,
  LastUpdateDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  activo TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (PK_Token)
)
ENGINE = INNODB,
CHARACTER SET latin1,
COLLATE latin1_swedish_ci;

--
-- Create index `FK01_Cliente_UNIQUE` on table `GN_Vucem_Token`
--
ALTER TABLE GN_Vucem_Token 
  ADD UNIQUE INDEX FK01_Cliente_UNIQUE(FK01_Cliente);

--
-- Create foreign key
--
ALTER TABLE GN_Vucem_Token 
  ADD CONSTRAINT FK01_Cliente_Token FOREIGN KEY (FK01_Cliente)
    REFERENCES GN_Cliente(PK_Cliente) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Create index `Tasas_Pedimento_509_SeccionAduanera_IDX` on table `Tasas_Pedimento_509`
--
ALTER TABLE Tasas_Pedimento_509 
  ADD INDEX Tasas_Pedimento_509_SeccionAduanera_IDX(SeccionAduanera, Pedimento, Patente);

--
-- Create index `Partidas_551_Patente_IDX` on table `Partidas_551`
--
ALTER TABLE Partidas_551 
  ADD INDEX Partidas_551_Patente_IDX(Patente, Pedimento, SeccionAduanera);

--
-- Create index `Guias_503_Patente_IDX` on table `Guias_503`
--
ALTER TABLE Guias_503 
  ADD INDEX Guias_503_Patente_IDX(Patente, Pedimento, SeccionAduanera);

--
-- Create index `Facturas_505_Patente_IDX` on table `Facturas_505`
--
ALTER TABLE Facturas_505 
  ADD FULLTEXT INDEX Facturas_505_Patente_IDX(Patente, Pedimento, SeccionAduanera);

--
-- Drop index `Partidas557` from table `Contribuciones_Partida_557`
--
ALTER TABLE Contribuciones_Partida_557 
   DROP INDEX Partidas557;

--
-- Create index `Contribuciones_Partida_557_Patente_IDX` on table `Contribuciones_Partida_557`
--
ALTER TABLE Contribuciones_Partida_557 
  ADD INDEX Contribuciones_Partida_557_Patente_IDX(Patente, Pedimento, SeccionAduanera, SecuenciaFraccion);

--
-- Create index `Contenedores_504_Patente_IDX` on table `Contenedores_504`
--
ALTER TABLE Contenedores_504 
  ADD INDEX Contenedores_504_Patente_IDX(Patente, Pedimento, SeccionAduanera);

--
-- Create table `GN_Vucem_Edoc`
--
CREATE TABLE IF NOT EXISTS GN_Vucem_Edoc (
  PK_SolicitudEdoc INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Llave primaria de la solicitud de Edoc',
  idRequestCase MEDIUMTEXT DEFAULT NULL COMMENT 'Identificador del caso de pedimento (T_507)',
  idDocument VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Identificador del documento a solicitar',
  idCustomer MEDIUMTEXT DEFAULT NULL COMMENT 'Identificador del cliente',
  status INT(11) DEFAULT 0 COMMENT 'creado = 0, solicitado = 1, falla en la descarga = 2, descargado = 3',
  requestDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de solicitud',
  lastUpdateDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de última modificación',
  path VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ruta de la solicitud',
  notes VARCHAR(500) DEFAULT NULL COMMENT 'Notas adicionales de la solicitud',
  errorMessage VARCHAR(500) DEFAULT NULL COMMENT 'En caso de existir un error en la solicitud, se indicará el título del error',
  stackTrace VARCHAR(8000) DEFAULT NULL COMMENT 'En caso de existir un error en la solicitud, se indica la traza',
  PRIMARY KEY (PK_SolicitudEdoc)
)
ENGINE = INNODB,
CHARACTER SET latin1,
COLLATE latin1_swedish_ci,
COMMENT = 'Tabla de solicitudes de Edocs de VUCEM';

--
-- Create table `GN_Vucem_AcuseEdoc`
--
CREATE TABLE IF NOT EXISTS GN_Vucem_AcuseEdoc (
  PK_SolicitudAcuseEdoc INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Llave primaria de la solicitud de acuse de Edoc',
  idRequestCase MEDIUMTEXT DEFAULT NULL COMMENT 'Identificador del caso de pedimento (T_507)',
  idDocument VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Identificador del documento a solicitar',
  idCustomer MEDIUMTEXT DEFAULT NULL COMMENT 'Identificador del cliente',
  status INT(11) DEFAULT 0 COMMENT 'creado = 0, solicitado = 1, falla en la descarga = 2, descargado = 3',
  requestDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de solicitud',
  lastUpdateDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de última modificación',
  path VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ruta de la solicitud',
  notes VARCHAR(500) DEFAULT NULL COMMENT 'Notas adicionales de la solicitud',
  errorMessage VARCHAR(500) DEFAULT NULL COMMENT 'En caso de existir un error en la solicitud, se indicará el título del error',
  stackTrace VARCHAR(8000) DEFAULT NULL COMMENT 'En caso de existir un error en la solicitud, se indica la traza',
  PRIMARY KEY (PK_SolicitudAcuseEdoc)
)
ENGINE = INNODB,
CHARACTER SET latin1,
COLLATE latin1_swedish_ci,
COMMENT = 'Tabla de solicitudes de acuses de edocs de VUCEM';

--
-- Create table `GN_Vucem_AcuseCove`
--
CREATE TABLE IF NOT EXISTS GN_Vucem_AcuseCove (
  PK_SolicitudAcuseFactura INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Llave primaria de la solicitud de acuse de factura',
  idReceipt MEDIUMTEXT DEFAULT NULL COMMENT 'Identificador de la factura (T_505)',
  idDocument VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Identificador del documento a solicitar',
  idCustomer MEDIUMTEXT DEFAULT NULL COMMENT 'Identificador del cliente',
  status INT(11) DEFAULT 0 COMMENT 'creado = 0, solicitado = 1, falla en la descarga = 2, descargado = 3',
  requestDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de solicitud',
  lastUpdateDate TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de última modificación',
  path VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Ruta de la solicitud',
  notes VARCHAR(500) DEFAULT NULL COMMENT 'Notas adicionales de la solicitud',
  errorMessage VARCHAR(500) DEFAULT NULL COMMENT 'En caso de existir un error en la solicitud, se indicará el título del error',
  stackTrace VARCHAR(8000) DEFAULT NULL COMMENT 'En caso de existir un error en la solicitud, se indica la traza',
  PRIMARY KEY (PK_SolicitudAcuseFactura)
)
ENGINE = INNODB,
CHARACTER SET latin1,
COLLATE latin1_swedish_ci,
COMMENT = 'Tabla de solicitudes de acuse de facturas de VUCEM';