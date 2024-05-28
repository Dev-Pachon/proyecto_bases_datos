DROP TABLE IF EXISTS `DetalleEmpleadoProfesion`;
DROP TABLE IF EXISTS `Contrato`;
DROP TABLE IF EXISTS `Sucursal`;
DROP TABLE IF EXISTS `Empleado`;
DROP TABLE IF EXISTS `Bitacora`;
DROP TABLE IF EXISTS `Usuario`;
DROP TABLE IF EXISTS `Nivel`;
DROP TABLE IF EXISTS `Profesion`;
DROP TABLE IF EXISTS `FuncionCargo`;
DROP TABLE IF EXISTS `Cargo`;
DROP TABLE IF EXISTS `Funcion`;
DROP TABLE IF EXISTS `Municipio`;
DROP TABLE IF EXISTS `Prioridad`;
DROP TABLE IF EXISTS `Departamento`;

-- Crear la tabla "Departamento"
CREATE TABLE `Departamento`
(
    `Codigo`    INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre`    VARCHAR(64) NOT NULL,
    `Poblacion` INT
);

-- Crear la tabla "Prioridad"
CREATE TABLE `Prioridad`
(
    `Codigo` INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre` VARCHAR(64) NOT NULL
);

-- Crear la tabla "Municipio"
CREATE TABLE `Municipio`
(
    `Codigo`       INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre`       VARCHAR(64) NOT NULL,
    `Poblacion`    INT,
    `Prioridad`    INT         NOT NULL,
    `Departamento` INT         NOT NULL,
    FOREIGN KEY (`Prioridad`) REFERENCES `Prioridad` (`Codigo`),
    FOREIGN KEY (`Departamento`) REFERENCES `Departamento` (`Codigo`)
);

-- Crear la tabla "Funcion"
CREATE TABLE `Funcion`
(
    `Codigo`      INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre`      VARCHAR(64) NOT NULL,
    `Descripcion` TEXT
);

-- Crear la tabla "Cargo"
CREATE TABLE `Cargo`
(
    `Codigo`  INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre`  VARCHAR(64) NOT NULL,
    `Salario` FLOAT
);

CREATE TABLE `FuncionCargo`
(
    `Funcion` INT NOT NULL,
    `Cargo`   INT NOT NULL,
    PRIMARY KEY (`Funcion`, `Cargo`),
    FOREIGN KEY (`Funcion`) REFERENCES `Funcion` (`Codigo`),
    FOREIGN KEY (`Cargo`) REFERENCES `Cargo` (`Codigo`)
);


-- Crear la tabla "Profesion"
CREATE TABLE `Profesion`
(
    `Codigo`      INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre`      VARCHAR(64) NOT NULL,
    `Descripcion` TEXT
);

-- Crear la tabla "Nivel"
CREATE TABLE `Nivel`
(
    `Codigo` INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre` VARCHAR(64) NOT NULL
);

-- Crear la tabla "Usuario"
CREATE TABLE `Usuario`
(
    `Codigo`        INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `NomUsuario`    VARCHAR(64) NOT NULL UNIQUE,
    `Clave`         VARCHAR(64) NOT NULL,
    `FechaCreacion` DATETIME    NOT NULL,
    `Nivel`         INT         NOT NULL,
    FOREIGN KEY (`Nivel`) REFERENCES `Nivel` (`Codigo`)
);

-- Crear la tabla "Bitacora"
CREATE TABLE `Bitacora`
(
    `Codigo`       INT NOT NULL AUTO_INCREMENT PRIMARY KEY AUTO_INCREMENT,
    `FechaIngreso` DATETIME,
    `FechaSalida`  DATETIME,
    `Usuario`      INT NOT NULL,
    FOREIGN KEY (`Usuario`) REFERENCES `Usuario` (`Codigo`)
);

-- Crear la tabla "Empleado"
CREATE TABLE `Empleado`
(
    `Codigo`              INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Cedula`              VARCHAR(20) NOT NULL UNIQUE,
    `Nombre`              VARCHAR(64) NOT NULL,
    `DireccionResidencia` TEXT,
    `Telefono`            VARCHAR(20),
    `Usuario`             INT         NOT NULL,
    FOREIGN KEY (`Usuario`) REFERENCES `Usuario` (`Codigo`)
);

-- Crear la tabla "Sucursal"
CREATE TABLE `Sucursal`
(
    `Codigo`      INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Nombre`      VARCHAR(64) NOT NULL,
    `Presupuesto` FLOAT,
    `Municipio`   INT         NOT NULL,
    `Director`    INT         NOT NULL,
    FOREIGN KEY (`Municipio`) REFERENCES `Municipio` (`Codigo`),
    FOREIGN KEY (`Director`) REFERENCES `Empleado` (`Codigo`)
);

-- Crear la tabla "Contrato"
CREATE TABLE `Contrato`
(
    `Codigo`      INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `Empleado`    INT NOT NULL,
    `Cargo`       INT NOT NULL,
    `Sucursal`    INT NOT NULL,
    `FechaInicio` DATE,
    `FechaFin`    DATE,
    FOREIGN KEY (`Empleado`) REFERENCES `Empleado` (`Codigo`),
    FOREIGN KEY (`Cargo`) REFERENCES `Cargo` (`Codigo`),
    FOREIGN KEY (`Sucursal`) REFERENCES `Sucursal` (`Codigo`)
);

-- Crear la tabla "DetalleEmpleadoProfesion"
CREATE TABLE `DetalleEmpleadoProfesion`
(
    `Empleado`  INT NOT NULL,
    `Profesion` INT NOT NULL,
    PRIMARY KEY (`Empleado`, `Profesion`),
    FOREIGN KEY (`Empleado`) REFERENCES `Empleado` (`Codigo`),
    FOREIGN KEY (`Profesion`) REFERENCES `Profesion` (`Codigo`)
);
