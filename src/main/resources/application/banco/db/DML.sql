
-- Inserciones en las tablas

-- Inserción en la tabla Departamento
INSERT INTO `Departamento` (`Nombre`, `Poblacion`)
VALUES ('Caldas', 1000000),
       ('Risaralda', 1500000),
       ('Sucre', 800000),
       ('Quindío', 1200000),
       ('Cundinamarca', 2000000),
       ('Vaupés', 700000),
       ('Magdalena', 900000),
       ('Santander', 600000),
       ('Guajira', 1100000),
       ('Cesar', 1300000);

-- Inserción en la tabla Prioridad
INSERT INTO `Prioridad` (`Nombre`)
VALUES ('Muy importante'),
       ('Importante,'),
       ('Medianamente importante'),
       ('Poco importante');

-- Inserción en la tabla Municipio
INSERT INTO `Municipio` (`Nombre`, `Poblacion`, `Prioridad`, `Departamento`)
VALUES ('Santa Marta', 50000, 1, 7),
       ('Pereira', 75000, 3, 2),
       ('Armenia', 30000, 2, 4),
       ('Manizales', 45000, 2, 1),
       ('Valledupar', 60000, 3, 10),
       ('Bogotá', 35000, 1, 5),
       ('Riohacha', 40000, 2, 9),
       ('Mitú', 55000, 3, 6),
       ('Sincelejo', 48000, 2, 3),
       ('Bucaramanga', 52000, 1, 8);

-- Inserción en la tabla Funcion
INSERT INTO `Funcion` (`Nombre`, `Descripcion`)
VALUES ('Gerente de Ventas', 'Encargado de supervisar las operaciones de ventas.'),
       ('Analista de Marketing', 'Responsable de realizar análisis de mercado y estrategias de marketing.'),
       ('Contador', 'Encargado de llevar la contabilidad y los registros financieros de la empresa.'),
       ('Ingeniero de Sistemas', 'Desarrolla y mantiene sistemas de información.'),
       ('Jefe de Recursos Humanos', 'Encargado de gestionar el personal y las relaciones laborales.'),
       ('Abogado Corporativo', 'Asesora legalmente a la empresa en temas corporativos y comerciales.'),
       ('Técnico de Soporte', 'Brinda soporte técnico a los usuarios de la empresa.'),
       ('Diseñador Gráfico', 'Crea material gráfico y visual para la empresa.'),
       ('Analista Financiero', 'Realiza análisis y proyecciones financieras.'),
       ('Asistente Administrativo', 'Brinda apoyo administrativo en diversas áreas de la empresa.');

-- Inserción en la tabla Cargo
INSERT INTO `Cargo` (`Nombre`, `Salario`)
VALUES ('Director Comercial', 5000.00),
       ('Especialista en Redes Sociales', 3000.00),
       ('Auxiliar Contable', 2500.00),
       ('Desarrollador Full Stack', 4000.00),
       ('Especialista en Selección de Personal', 3200.00),
       ('Abogado Corporativo Senior', 4500.00),
       ('Técnico de Soporte Nivel 2', 2800.00),
       ('Diseñador Gráfico Senior', 3500.00),
       ('Analista Financiero Senior', 4200.00),
       ('Asistente Administrativo Junior', 2300.00);

INSERT INTO `FuncionCargo` (Funcion, Cargo)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Inserción en la tabla Profesion
INSERT INTO `Profesion` (`Nombre`, `Descripcion`)
VALUES ('Administración de Empresas', 'Profesional encargado de la gestión administrativa de empresas.'),
       ('Marketing', 'Profesional dedicado a la promoción y venta de productos y servicios.'),
       ('Contaduría Pública', 'Profesional experto en contabilidad y finanzas.'),
       ('Ingeniería de Sistemas', 'Profesional encargado del diseño y desarrollo de sistemas informáticos.'),
       ('Recursos Humanos', 'Profesional especializado en la gestión del talento humano.'),
       ('Derecho', 'Profesional en leyes y asesoría legal.'),
       ('Soporte Técnico', 'Profesional encargado del soporte y mantenimiento técnico.'),
       ('Diseño Gráfico', 'Profesional en la creación de material gráfico y visual.'),
       ('Finanzas', 'Profesional en análisis y gestión financiera.'),
       ('Administración', 'Profesional encargado de tareas administrativas.');

-- Inserción en la tabla Nivel
INSERT INTO `Nivel` (`Nombre`)
VALUES ('Principal'),
       ('Paramétrico'),
       ('esporádico');

-- Inserción en la tabla Usuario
INSERT INTO `Usuario` (`NomUsuario`, `Clave`, `FechaCreacion`, `Nivel`)
VALUES ('admin', 'admin123', '2023-05-01', 1),
       ('jdoe', 'password', '2023-05-01', 2),
       ('amaria', 'amaria2023', '2023-05-01', 2),
       ('carlos', 'carlos2023', '2023-05-01', 3),
       ('julia', 'julia2023', '2023-05-01', 2),
       ('david', 'david2023', '2023-05-01', 2),
       ('paula', 'paula2023', '2023-05-01', 2),
       ('luis', 'luis2023', '2023-05-01', 3),
       ('marta', 'marta2023', '2023-05-01', 2),
       ('pedro', 'pedro2023', '2023-05-01', 3);

-- Inserción en la tabla Empleado
INSERT INTO `Empleado` (`Cedula`, `Nombre`, `DireccionResidencia`, `Telefono`, `Usuario`)
VALUES ('1234567890', 'Juan Pérez', 'Calle 123 #45-67', '555-1234', 2),
       ('0987654321', 'Ana Gómez', 'Carrera 10 #20-30', '555-5678', 3),
       ('1122334455', 'Carlos Ruiz', 'Avenida 6 #7-89', '555-9012', 4),
       ('6677889900', 'Laura Medina', 'Diagonal 8 #9-10', '555-3456', 5),
       ('5566778899', 'Mario López', 'Transversal 4 #5-6', '555-7890', 6),
       ('4433221100', 'Sofía Martínez', 'Carrera 3 #4-5', '555-2345', 7),
       ('2211003344', 'Pedro Álvarez', 'Avenida 2 #3-4', '555-6789', 8),
       ('9988776655', 'Lucía Torres', 'Calle 1 #2-3', '555-1234', 9),
       ('7766554433', 'Miguel Sánchez', 'Carrera 5 #6-7', '555-5678', 10),
       ('5544332211', 'Sara Ramírez', 'Diagonal 9 #10-11', '555-9012', 1);

-- Inserción en la tabla Sucursal
INSERT INTO `Sucursal` (`Nombre`, `Presupuesto`, `Municipio`, `Director`)
VALUES ('Sucursal Centro', 1000000.00, 1, 2),
       ('Sucursal Norte', 1500000.00, 2, 3),
       ('Sucursal Sur', 800000.00, 3, 4),
       ('Sucursal Este', 1200000.00, 4, 5),
       ('Sucursal Oeste', 2000000.00, 5, 6);

-- Inserción en la tabla Contrato
INSERT INTO `Contrato` (`Empleado`, `Cargo`, `Sucursal`, `FechaInicio`, `FechaFin`)
VALUES (1, 1, 1, '2023-01-01', '2023-12-31'),
       (2, 2, 2, '2023-01-01', '2023-12-31'),
       (3, 3, 3, '2023-01-01', '2023-12-31'),
       (4, 4, 4, '2023-01-01', '2023-12-31'),
       (5, 5, 5, '2023-01-01', '2023-12-31'),
       (6, 6, 1, '2023-01-01', '2023-12-31'),
       (7, 7, 2, '2023-01-01', '2023-12-31'),
       (8, 8, 3, '2023-01-01', '2023-12-31'),
       (9, 9, 4, '2023-01-01', '2023-12-31'),
       (10, 10, 5, '2023-01-01', '2023-12-31');

-- Inserción en la tabla DetalleEmpleadoProfesion
INSERT INTO `DetalleEmpleadoProfesion` (`Empleado`, `Profesion`)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);