USE GlosaNet;

-- GlosaNet.GN_Catalogo definition

CREATE TABLE `GN_Catalogo` (
  `PK_Catalogo` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador del catálogo',
  `NombreCatalogo` varchar(200) NOT NULL COMMENT 'Nombre del catálogo',
  `Descripcion` varchar(250) DEFAULT NULL COMMENT 'Descripción breve ',
  `Activo` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Catalogo habilitado o deshabilitado',
  `FechaCreacion` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del catálogo',
  `FechaModificacion` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de modificación del catálogo',
  `FechaEliminacion` datetime DEFAULT NULL COMMENT 'Fecha de eliminación del catálogo',
  PRIMARY KEY (`PK_Catalogo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='Catálogos de consumo';

INSERT INTO GN_Catalogo (NombreCatalogo,Descripcion,Activo,FechaCreacion,FechaModificacion,FechaEliminacion) VALUES 
('Tipo de documentos de Vucem','Listado de tipos de documento de Vucem obtenido del Manual del usuario',1,NOW(),NOW(),NULL)
;

-- GlosaNet.GN_Catalogo_Contenido definition

CREATE TABLE `GN_Catalogo_Contenido` (
  `PK_Catalogo_Contenido` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador único del registro de catálogos',
  `FK_Catalogo` int(11) NOT NULL COMMENT 'Identificador de catálogo',
  `Posicion` int(11) DEFAULT NULL COMMENT 'Para efectos de ordenamiento',
  `Clave` varchar(50) DEFAULT NULL COMMENT 'Clave de item de catálogo general',
  `ClaveReporte` varchar(50) DEFAULT NULL COMMENT 'Clave de item de catálogo para mostrar en reportes',
  `Nombre` varchar(250) DEFAULT NULL COMMENT 'Nombre del item del catálogo',
  `DescripcionBreve` varchar(300) DEFAULT NULL COMMENT 'Descripción corta',
  `DescripcionExtensa` varchar(2000) DEFAULT NULL COMMENT 'Descripción larga',
  `Tooltip` varchar(150) DEFAULT NULL COMMENT 'Mensaje para mostrar en un tooltip en front',
  `Predeterminado` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Indica si el item es predeterminado en la lista. Solo debe de haber uno por lista.',
  `Activo` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Item habilitado o deshabilitado',
  `FechaCreacion` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación del item',
  `FechaModificacion` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de modificación del item',
  `FechaEliminacion` datetime DEFAULT NULL COMMENT 'Fecha de baja lógica',
  PRIMARY KEY (`PK_Catalogo_Contenido`),
  UNIQUE KEY `UK_GN_Catalogo_Contenido_PK_Catalogo_Contenido` (`PK_Catalogo_Contenido`),
  KEY `FK_GN_Catalogo_Contenido_FK_Catalogo` (`FK_Catalogo`),
  CONSTRAINT `FK_GN_Catalogo_Contenido_FK_Catalogo` FOREIGN KEY (`FK_Catalogo`) REFERENCES `GN_Catalogo` (`PK_Catalogo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1 COMMENT='Contenido de catálogos de consumo';

INSERT INTO GN_Catalogo_Contenido (FK_Catalogo,Posicion,Clave,ClaveReporte,Nombre,DescripcionBreve,DescripcionExtensa,Tooltip,Predeterminado,Activo,FechaCreacion,FechaModificacion,FechaEliminacion) VALUES 
(1,0,'0168',NULL,'Calca o fotografía digital del NIV del vehículo',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,1,'0169',NULL,'Aviso',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,2,'0170',NULL,'Factura',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,3,'0171',NULL,'Documento con el que se acredite la propiedad de la mercancía',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,4,'0172',NULL,'Contratos',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,5,'0176',NULL,'Documentación relacionada con la garantía otorgada en términos de los artículos 84-A y 86-A de la L.A. ',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,6,'0177',NULL,'Identificación Oficial.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,7,'0179',NULL,'Comprobante de domicilio.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,8,'0184',NULL,'Documento que ampara el avaluó de las mercancías.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,9,'0185',NULL,'Documentos de adjudicación judicial de las mercancías.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
;
INSERT INTO GN_Catalogo_Contenido (FK_Catalogo,Posicion,Clave,ClaveReporte,Nombre,DescripcionBreve,DescripcionExtensa,Tooltip,Predeterminado,Activo,FechaCreacion,FechaModificacion,FechaEliminacion) VALUES 
(1,10,'0187',NULL,'Solicitud de retiro de mercancías que causaron abandono.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,11,'0189',NULL,'Actas',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,12,'0192',NULL,'Escritos',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,13,'0420',NULL,'Certificado de peso o volumen.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,14,'0421',NULL,'Comprobante de la importación temporal de la embarcación debidamente formalizado',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,15,'0422',NULL,'Comprobante expedido por donataria.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,16,'0423',NULL,'Consulta en la que conste que el vehículo no se encuentra reportado como robado, siniestrado, restringido o prohibido para su circulación en el país de procedencia',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,17,'0424',NULL,'Clave Única del Registro de Población.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,18,'0425',NULL,'Declaración de internación o extracción de cantidades en efectivo y/o documentos por cobrar',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,19,'0426',NULL,'Declaración de operaciones que no confieren origen en países no parte de acuerdo al TLCI.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
;
INSERT INTO GN_Catalogo_Contenido (FK_Catalogo,Posicion,Clave,ClaveReporte,Nombre,DescripcionBreve,DescripcionExtensa,Tooltip,Predeterminado,Activo,FechaCreacion,FechaModificacion,FechaEliminacion) VALUES 
(1,20,'0427',NULL,'Declaración en la que se señalen los motivos por los que efectúa la devolución de mercancías en los términos. de la regla 3.8.4',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,21,'0428',NULL,'Documentación con información que permita la identificación, análisis y control en términos del artículo 36 de la L.A',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,22,'0429',NULL,'Documentación que acredite que acepta y subsana la irregularidad ',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,23,'0430',NULL,'Documentación que ampare la importación temporal del vehículo de que se trate ',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,24,'0431',NULL,'Documentación que compruebe que la adquisición de las mercancías fue efectuada cuando se contaba con autorización para operar bajo un Programa IMMEX.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,25,'0433',NULL,'Documento con base en el cual se determine la procedencia y el origen de las mercancías',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,26,'0434',NULL,'Documento con que se acredite el reintegro del IVA, en caso de que el contribuyente hubiere obtenido la devolución, o efectuado el acreditamiento de los saldos a favor declarados con motivo de la exportación',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,27,'0435',NULL,'Documentos previstos en la regla 8.7., fracciones I a IV de la Resolución del TLCAN',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,28,'0436',NULL,'El Documento que compruebe el cumplimiento de las regulaciones y restricciones no arancelarias',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,29,'0437',NULL,'formato denominado “Relación de documentos”',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
;
INSERT INTO GN_Catalogo_Contenido (FK_Catalogo,Posicion,Clave,ClaveReporte,Nombre,DescripcionBreve,DescripcionExtensa,Tooltip,Predeterminado,Activo,FechaCreacion,FechaModificacion,FechaEliminacion) VALUES 
(1,30,'0438',NULL,'Guía aérea, conocimiento de embarque o carta de porte',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,31,'0439',NULL,'Hoja con los datos de la matrícula y nombre del barco, el lugar donde se localiza y se indique que la mercancía se encuentra almacenada en los depósitos para combustible del barco para su propio consumo.',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,32,'0440',NULL,'Manifiesto de carga',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,33,'0441',NULL,'Oficios emitidos por autoridad',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,34,'0442',NULL,'Pedimentos',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,35,'0443',NULL,'Programa IMMEX',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,36,'0444',NULL,'Relación de candados',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL)
,(1,37,'0445',NULL,'Relación de certificados de origen',NULL,NULL,NULL,0,1,NOW(),NOW(),NULL);
