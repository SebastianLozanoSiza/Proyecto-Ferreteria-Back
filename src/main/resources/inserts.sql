-- Insertar departamentos
INSERT INTO Departamentos (nombre) VALUES
('Santander'),
('Cundinamarca'),
('Antioquia');

-- Insertar municipios
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Bucaramanga', 1),
('Floridablanca', 1),
('Bogotá', 2),
('Medellín', 3);

-- Insertar barrios
INSERT INTO Barrios (nombre, direccion, id_municipio) VALUES
('Cabecera del Llano', 'Cra 33 #48-12', 1),
('Cañaveral', 'Calle 40 #12-34', 2),
('Chapinero', 'Carrera 7 #80-23', 3),
('El Poblado', 'Avenida El Poblado #15-67', 4);

-- Insertar ferreterías
INSERT INTO Ferreterias (nit, razon_social, representante, fecha_registro) VALUES
('901234567-8', 'Ferretería Los Andes', 'Carlos Rodríguez', '2022-05-10'),
('900876543-2', 'Ferretería El Constructor', 'María Fernández', '2023-08-15');

-- Insertar sucursales
INSERT INTO sucursales (id_ferreteria) VALUES
(1), (2);

-- Asignar barrios a sucursales
INSERT INTO sucursales_barrios (id_sucursal, id_barrio) VALUES
(1, 1),
(2, 2);

-- Insertar terceros (clientes y empleados)
INSERT INTO Terceros (identificacion, nombre, apellidos, correo, direccion, telefono) VALUES
('1012345678', 'Juan', 'Pérez', 'juan.perez@example.com', 'Cra 12 #34-56', '3123456789'),
('1023456789', 'Ana', 'Gómez', 'ana.gomez@example.com', 'Calle 45 #67-89', '3156789123'),
('1034567890', 'Pedro', 'López', 'pedro.lopez@example.com', 'Av Siempre Viva #123', '3145678901'),
('1045678901', 'Sofía', 'Ramírez', 'sofia.ramirez@example.com', 'Diagonal 80 #25-30', '3201234567');

-- Insertar clientes
INSERT INTO Clientes (id_tercero) VALUES
(1), (2);

-- Insertar roles
INSERT INTO Roles (nombre_rol) VALUES
('Administrador'),
('Vendedor'),
('Cajero');

-- Insertar empleados
INSERT INTO Empleados (id_tercero, id_rol, id_ferreteria) VALUES
(3, 1, 1),
(4, 2, 2);

-- Insertar credenciales para los empleados
INSERT INTO Credenciales (nombre_usuario, contrasena, id_tercero) VALUES
('admin1', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 3),
('vendedor2', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 4);

-- Insertar productos
INSERT INTO Productos (nombre_producto, descripcion, categoria, precio, stock, id_ferreteria) VALUES
('Cemento Gris 50kg', 'Bolsa de cemento para construcción', 'Materiales de Construcción', 32000.00, 100, 1),
('Pintura Viniltex 1 Galón', 'Pintura blanca para interiores', 'Pinturas', 85000.00, 50, 1),
('Taladro Eléctrico 500W', 'Taladro para perforación en concreto y madera', 'Herramientas Eléctricas', 150000.00, 30, 2),
('Llave Stilson 18”', 'Llave de acero ajustable para tuberías', 'Herramientas Manuales', 45000.00, 40, 2);
