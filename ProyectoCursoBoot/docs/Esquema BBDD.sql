-- Creación de la base de datos
CREATE SCHEMA `gestiondocumentos` ;

-- Creación de la tabla de usuarios
CREATE TABLE `gestiondocumentos`.`usuario` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `idCliente` INT NULL,
  `fechaAlta` DATE NOT NULL,
  `nombre` VARCHAR(20) NOT NULL,
  `apellidos` VARCHAR(30) NULL,
  `direccion` VARCHAR(45) NULL,
  `ciudad` VARCHAR(20) NULL,
  `provincia` VARCHAR(15) NULL,
  `tfnoFijo` VARCHAR(9) NULL,
  `tfnoMovil` VARCHAR(12) NULL,
  `correo` VARCHAR(45) NULL,
  `usrAcceso` VARCHAR(12) NOT NULL,
  `pwdAcceso` VARCHAR(15) NOT NULL,
  UNIQUE INDEX `idCliente_UNIQUE` (`idCliente` ASC) VISIBLE,
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `usrAcceso_UNIQUE` (`usrAcceso` ASC) VISIBLE);
  
-- Creación de la tabla de administradores
CREATE TABLE `gestiondocumentos`.`administrador` (
  `idAdmin` INT NOT NULL AUTO_INCREMENT,
  `usrAcceso` VARCHAR(12) NOT NULL,
  `pwdAcceso` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`idAdmin`),
  UNIQUE INDEX `usrAcceso_UNIQUE` (`usrAcceso` ASC) VISIBLE);
  
-- Creación de la tabla de documentos
CREATE TABLE `gestiondocumentos`.`documento` (
  `idDocumento` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) NOT NULL,
  `ubicacion` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`idDocumento`));
  
-- Creación de la tabla de grupos de documentos
CREATE TABLE `gestiondocumentos`.`grupodocumentos` (
  `idGrupo` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idGrupo`));
  
-- Creación de la tabla de relaciones Usuario-Grupo
CREATE TABLE `gestiondocumentos`.`rel_usr_grupo` (
  `idUsr` INT NOT NULL,
  `idGrupo` INT NOT NULL,
  PRIMARY KEY (`idUsr`, `idGrupo`));

-- Creación de la tabla de relaciones Grupo-Documento
CREATE TABLE `gestiondocumentos`.`rel_grupo_doc` (
  `idGrupo` INT NOT NULL,
  `idDocumento` INT NOT NULL,
  PRIMARY KEY (`idGrupo`, `idDocumento`));
  
-- Foreing key rel_usr_grupo.idUsr -> usuario.idUsuario
-- Foreing key rel_usr_grupo.idGrupo -> documento.idGrupo
ALTER TABLE `gestiondocumentos`.`rel_usr_grupo` 
ADD INDEX `fk_grupo_idx` (`idGrupo` ASC) VISIBLE;
;
ALTER TABLE `gestiondocumentos`.`rel_usr_grupo` 
ADD CONSTRAINT `fk_usr`
  FOREIGN KEY (`idUsr`)
  REFERENCES `gestiondocumentos`.`usuario` (`idUsuario`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_grupo`
  FOREIGN KEY (`idGrupo`)
  REFERENCES `gestiondocumentos`.`grupodocumentos` (`idGrupo`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
  
-- Foreing key rel_grupo_doc.idDocumento -> documento.idDocumento
-- Foreing key rel_grupo_doc.idGrupo -> grupodocumentos.idGrupo
ALTER TABLE `gestiondocumentos`.`rel_grupo_doc` 
ADD INDEX `fk_toDoc_idx` (`idDocumento` ASC) VISIBLE;
;
ALTER TABLE `gestiondocumentos`.`rel_grupo_doc` 
ADD CONSTRAINT `fk_toGrupo`
  FOREIGN KEY (`idGrupo`)
  REFERENCES `gestiondocumentos`.`grupodocumentos` (`idGrupo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_toDoc`
  FOREIGN KEY (`idDocumento`)
  REFERENCES `gestiondocumentos`.`documento` (`idDocumento`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  