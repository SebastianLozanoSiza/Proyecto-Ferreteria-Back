-- Insertar todos los departamentos de Colombia
INSERT INTO Departamentos (nombre) VALUES
('Amazonas'), ('Antioquia'), ('Arauca'), ('Atlántico'), ('Bolívar'), ('Boyacá'), 
('Caldas'), ('Caquetá'), ('Casanare'), ('Cauca'), ('Cesar'), ('Chocó'), 
('Córdoba'), ('Cundinamarca'), ('Guainía'), ('Guaviare'), ('Huila'), 
('La Guajira'), ('Magdalena'), ('Meta'), ('Nariño'), ('Norte de Santander'), 
('Putumayo'), ('Quindío'), ('Risaralda'), ('San Andrés y Providencia'), 
('Santander'), ('Sucre'), ('Tolima'), ('Valle del Cauca'), ('Vaupés'), ('Vichada');

-- Insertar municipios para cada departamento (7 por departamento)
-- Antioquia 
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Bello', 2), ('Itagüí', 2), ('Envigado', 2), ('Rionegro', 2), ('Marinilla', 2), ('La Ceja', 2), ('Sabaneta', 2), ('Medellín', 2);

-- Cundinamarca 
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Soacha', 14), ('Facatativá', 14), ('Zipaquirá', 14), ('Chía', 14), ('Girardot', 14), ('Fusagasugá', 14), ('Mosquera', 14), ('Bogotá', 14);

-- Santander 
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Girón', 27), ('Piedecuesta', 27), ('Barrancabermeja', 27), ('San Gil', 27), ('Socorro', 27), ('Málaga', 27), ('Puerto Wilches', 27), ('Bucaramanga', 27), ('Floridablanca', 27);

-- Atlántico
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Barranquilla', 4), ('Soledad', 4), ('Malambo', 4), ('Sabanalarga', 4), ('Puerto Colombia', 4), ('Baranoa', 4), ('Galapa', 4);

-- Valle del Cauca
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Cali', 30), ('Palmira', 30), ('Buenaventura', 30), ('Tuluá', 30), ('Cartago', 30), ('Buga', 30), ('Yumbo', 30);

-- Bolívar
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Cartagena', 5), ('Magangué', 5), ('Turbaco', 5), ('Arjona', 5), ('Carmen de Bolívar', 5), ('San Juan Nepomuceno', 5), ('El Carmen de Bolívar', 5);

-- Boyacá
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Tunja', 6), ('Duitama', 6), ('Sogamoso', 6), ('Chiquinquirá', 6), ('Paipa', 6), ('Moniquirá', 6), ('Villa de Leyva', 6);

-- Nariño
INSERT INTO Municipios (nombre, id_departamento) VALUES
('Pasto', 21), ('Tumaco', 21), ('Ipiales', 21), ('Samaniego', 21), ('La Unión', 21), ('Sandona', 21), ('Yacuanquer', 21);

-- Barrios para Bucaramanga (ID Municipio 1)
INSERT INTO Barrios (nombre, direccion, id_municipio) VALUES
('Centro', 'Calle 35 #10-20', 1),
('La Concordia', 'Carrera 27 #45-30', 1),
('San Francisco', 'Calle 45 #30-15', 1),
('Garcés Navas', 'Carrera 33 #28-40', 1),
('Cabecera', 'Calle 56 #35-22', 1),
('La Floresta', 'Carrera 36 #52-10', 1),
('Alvarez', 'Calle 30 #25-18', 1),
('Mutis', 'Carrera 22 #30-45', 1),
('La Salle', 'Calle 40 #28-33', 1),
('Morrorico', 'Carrera 15 #40-50', 1);

-- Barrios para Floridablanca (ID Municipio 2)
INSERT INTO Barrios (nombre, direccion, id_municipio) VALUES
('Cañaveral', 'Calle 40 #12-34', 2),
('Bucarica', 'Carrera 8 #25-30', 2),
('Ruitoque', 'Calle 32 #15-20', 2),
('Prados', 'Carrera 12 #30-45', 2),
('La Hacienda', 'Calle 45 #22-18', 2),
('Campo Hermoso', 'Carrera 10 #35-40', 2),
('Parque', 'Calle 28 #20-25', 2),
('San Miguel', 'Carrera 15 #33-28', 2),
('Santa Bárbara', 'Calle 38 #18-22', 2),
('Cumbre', 'Carrera 5 #40-50', 2);

-- Barrios para Bogotá (ID Municipio 3)
INSERT INTO Barrios (nombre, direccion, id_municipio) VALUES
('Chapinero', 'Carrera 7 #80-23', 3),
('Usaquén', 'Calle 120 #7-10', 3),
('Teusaquillo', 'Carrera 24 #53-20', 3),
('La Candelaria', 'Calle 10 #5-22', 3),
('Engativá', 'Carrera 70 #80-45', 3),
('Suba', 'Calle 145 #12-30', 3),
('Kennedy', 'Carrera 80 #38-25', 3),
('Fontibón', 'Calle 17 #100-20', 3),
('Bosa', 'Carrera 80D #60-10', 3),
('Puente Aranda', 'Calle 13 #28-35', 3);

-- Insertar más ferreterías
INSERT INTO Ferreterias (nit, razon_social, representante, fecha_registro) VALUES
('901112223-4', 'Ferretería El Martillo', 'Roberto Mendoza', '2021-03-15'),
('902223334-5', 'Ferretería La Económica', 'Luisa Castro', '2022-11-20'),
('903334445-6', 'Ferretería Todo Construcción', 'Alberto Rojas', '2023-01-05'),
('904445556-7', 'Ferretería Los Expertos', 'Patricia Gómez', '2020-07-30'),
('905556667-8', 'Ferretería El Tornillo Feliz', 'Jorge Ramírez', '2023-05-18');

-- Insertar sucursales (3 por ferretería)
INSERT INTO sucursales (id_ferreteria) VALUES
(1), (1), (1),  -- 3 sucursales para Ferretería Los Andes
(2), (2),       -- 2 sucursales para Ferretería El Constructor
(3), (3), (3),  -- 3 sucursales para Ferretería El Martillo
(4), (4),       -- 2 sucursales para Ferretería La Económica
(5), (5), (5);  -- 3 sucursales para Ferretería Todo Construcción

-- Asignar barrios a sucursales (2-3 barrios por sucursal)
INSERT INTO sucursales_barrios (id_sucursal, id_barrio) VALUES
(1, 1), (1, 2),   -- Sucursal 1 atiende barrios 1 y 2
(2, 3), (2, 4),   -- Sucursal 2 atiende barrios 3 y 4
(3, 5),           -- Sucursal 3 atiende barrio 5
(4, 11), (4, 12), -- Sucursal 4 atiende barrios 11 y 12 (Cañaveral y Bucarica)
(5, 13),          -- Sucursal 5 atiende barrio 13 (Ruitoque)
(6, 21), (6, 22), -- Sucursal 6 atiende barrios 21 y 22 (Chapinero y Usaquén)
(7, 23),          -- Sucursal 7 atiende barrio 23 (Teusaquillo)
(8, 26), (8, 30), -- Sucursal 8 atiende barrios 31 y 32 (Bello centro)
(9, 27),          -- Sucursal 9 atiende barrio 33 (Bello norte)
(10, 28), (10, 29); -- Sucursal 10 atiende barrios 41 y 42 (Barranquilla centro)

-- Insertar más terceros (50 adicionales)
INSERT INTO Terceros (identificacion, nombre, apellidos, correo, direccion, telefono) VALUES
('1101234567', 'Camila', 'Rojas', 'camila.rojas@example.com', 'Calle 70 #12-34', '3151234567'),
('1112345678', 'Diego', 'Silva', 'diego.silva@example.com', 'Carrera 45 #23-10', '3162345678'),
('1123456789', 'María', 'Vargas', 'maria.vargas@example.com', 'Avenida 68 #45-67', '3173456789'),
('1134567890', 'Carlos', 'Mendoza', 'carlos.mendoza@example.com', 'Transversal 23 #12-45', '3184567890'),
('1145678901', 'Laura', 'Pineda', 'laura.pineda@example.com', 'Diagonal 25 #34-56', '3195678901'),
('1156789012', 'Andrés', 'Giraldo', 'andres.giraldo@example.com', 'Calle 100 #45-67', '3206789012'),
('1167890123', 'Sara', 'Ortiz', 'sara.ortiz@example.com', 'Carrera 60 #78-90', '3217890123'),
('1178901234', 'Jorge', 'Hernández', 'jorge.hernandez@example.com', 'Avenida 30 #56-78', '3228901234'),
('1189012345', 'Patricia', 'Gómez', 'patricia.gomez@example.com', 'Calle 85 #34-56', '3239012345'),
('1190123456', 'Ricardo', 'Díaz', 'ricardo.diaz@example.com', 'Carrera 50 #67-89', '3240123456'),
('1201234567', 'Fernanda', 'López', 'fernanda.lopez@example.com', 'Avenida 19 #45-67', '3251234567'),
('1212345678', 'Gustavo', 'Martínez', 'gustavo.martinez@example.com', 'Calle 72 #56-78', '3262345678'),
('1223456789', 'Alejandra', 'Castro', 'alejandra.castro@example.com', 'Carrera 15 #34-56', '3273456789'),
('1234567890', 'Oscar', 'Ramírez', 'oscar.ramirez@example.com', 'Diagonal 40 #67-89', '3284567890'),
('1245678901', 'Diana', 'Torres', 'diana.torres@example.com', 'Transversal 10 #12-34', '3295678901'),
('1256789012', 'Felipe', 'Jiménez', 'felipe.jimenez@example.com', 'Calle 45 #78-90', '3306789012'),
('1267890123', 'Carolina', 'Ruiz', 'carolina.ruiz@example.com', 'Carrera 30 #45-67', '3317890123'),
('1278901234', 'Hugo', 'Peña', 'hugo.pena@example.com', 'Avenida 26 #56-78', '3328901234'),
('1289012345', 'Adriana', 'Ríos', 'adriana.rios@example.com', 'Calle 80 #34-56', '3339012345'),
('1290123456', 'Mauricio', 'Moreno', 'mauricio.moreno@example.com', 'Carrera 25 #67-89', '3340123456'),
('1301234567', 'Claudia', 'Gutiérrez', 'claudia.gutierrez@example.com', 'Avenida 15 #45-67', '3351234567'),
('1312345678', 'Raúl', 'Vega', 'raul.vega@example.com', 'Calle 65 #56-78', '3362345678'),
('1323456789', 'Tatiana', 'Molina', 'tatiana.molina@example.com', 'Carrera 20 #34-56', '3373456789'),
('1334567890', 'Alberto', 'Reyes', 'alberto.reyes@example.com', 'Diagonal 35 #67-89', '3384567890'),
('1345678901', 'Natalia', 'Cárdenas', 'natalia.cardenas@example.com', 'Transversal 18 #12-34', '3395678901'),
('1356789012', 'Julián', 'Arias', 'julian.arias@example.com', 'Calle 55 #78-90', '3406789012'),
('1367890123', 'Gabriela', 'Franco', 'gabriela.franco@example.com', 'Carrera 40 #45-67', '3417890123'),
('1378901234', 'Santiago', 'Rangel', 'santiago.rangel@example.com', 'Avenida 22 #56-78', '3428901234'),
('1389012345', 'Paola', 'Guerrero', 'paola.guerrero@example.com', 'Calle 75 #34-56', '3439012345'),
('1390123456', 'Daniel', 'Soto', 'daniel.soto@example.com', 'Carrera 18 #67-89', '3440123456'),
('1401234567', 'Marcela', 'Cortés', 'marcela.cortes@example.com', 'Avenida 14 #45-67', '3451234567'),
('1412345678', 'Federico', 'Marín', 'federico.marin@example.com', 'Calle 60 #56-78', '3462345678'),
('1423456789', 'Verónica', 'Parra', 'veronica.parra@example.com', 'Carrera 10 #34-56', '3473456789'),
('1434567890', 'Arturo', 'Orozco', 'arturo.orozco@example.com', 'Diagonal 30 #67-89', '3484567890'),
('1445678901', 'Lucía', 'Castaño', 'lucia.castano@example.com', 'Transversal 15 #12-34', '3495678901'),
('1456789012', 'Héctor', 'Blanco', 'hector.blanco@example.com', 'Calle 50 #78-90', '3506789012'),
('1467890123', 'Isabel', 'Guerra', 'isabel.guerra@example.com', 'Carrera 35 #45-67', '3517890123'),
('1478901234', 'Eduardo', 'Méndez', 'eduardo.mendez@example.com', 'Avenida 20 #56-78', '3528901234'),
('1489012345', 'Angélica', 'Zapata', 'angelica.zapata@example.com', 'Calle 70 #34-56', '3539012345'),
('1490123456', 'Mario', 'Acosta', 'mario.acosta@example.com', 'Carrera 22 #67-89', '3540123456'),
('1501234567', 'Beatriz', 'Cano', 'beatriz.cano@example.com', 'Avenida 16 #45-67', '3551234567'),
('1512345678', 'Roberto', 'Santos', 'roberto.santos@example.com', 'Calle 62 #56-78', '3562345678'),
('1523456789', 'Silvia', 'Villa', 'silvia.villa@example.com', 'Carrera 12 #34-56', '3573456789'),
('1534567890', 'Alfredo', 'Paredes', 'alfredo.paredes@example.com', 'Diagonal 32 #67-89', '3584567890'),
('1545678901', 'Carmen', 'Lara', 'carmen.lara@example.com', 'Transversal 17 #12-34', '3595678901'),
('1556789012', 'Gonzalo', 'Miranda', 'gonzalo.miranda@example.com', 'Calle 52 #78-90', '3606789012'),
('1567890123', 'Adela', 'Valencia', 'adela.valencia@example.com', 'Carrera 38 #45-67', '3617890123'),
('1578901234', 'Pablo', 'Godoy', 'pablo.godoy@example.com', 'Avenida 24 #56-78', '3628901234'),
('1589012345', 'Rosa', 'Cifuentes', 'rosa.cifuentes@example.com', 'Calle 78 #34-56', '3639012345'),
('1590123456', 'Hernán', 'Cordero', 'hernan.cordero@example.com', 'Carrera 20 #67-89', '3640123456');

-- Insertar más clientes (40 de los nuevos terceros)
INSERT INTO Clientes (id_tercero) VALUES
(25), (26), (27), (28), (29), (30), (31), (32), (33), (34),
(35), (36), (37), (38), (39),(40), (41), (42), (43), (44), 
(45), (46), (47), (48), (49), (50);

INSERT INTO Roles (nombre_rol) VALUES
('Admin'),
('Supervisor');

-- Insertar más empleados (10 de los nuevos terceros)
INSERT INTO Empleados (id_tercero, id_rol, id_ferreteria) VALUES
(1, 1, 1),
(2, 2, 1), 
(3, 1, 2), 
(4, 2, 2), 
(5, 1, 3),
(6, 2, 3),  
(7, 1, 4), 
(8, 2, 4), 
(9, 1, 5), 
(10, 2, 5),
(11, 1, 1), 
(12, 2, 1), 
(13, 1, 2), 
(14, 2, 2), 
(15, 1, 3),
(16, 2, 3),  
(17, 1, 4), 
(18, 2, 4), 
(19, 1, 5), 
(20, 2, 5),
(21, 1, 1), 
(22, 2, 1), 
(23, 1, 2), 
(24, 2, 2);  

-- Credenciales para los nuevos empleados
INSERT INTO Credenciales (nombre_usuario, contrasena, id_tercero) VALUES
('admin', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 1),
('supervisor', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 2),
('admin.elconstructor', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 3),
('supervisor.elconstructor', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 4),
('admin.elmartillo', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 5),
('supervisor.elmartillo', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 6),
('cliente1', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 25),
('cliente2', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 26),
('cliente3', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 27),
('cliente4', '$2a$10$pNkUFTtHp5WcXactPUDOFuGRVL/eHJ09C6UYlirNnnFIbfTeZ5H2G', 28);

-- Insertar más productos (50 adicionales)
-- Insertar productos con categorías únicas (versión corregida)
INSERT INTO Productos (nombre_producto, descripcion, categoria, precio, stock, id_ferreteria) VALUES
('Cemento Gris 50kg', 'Bolsa de cemento para construcción', 'Materiales de Construcción', 32000.00, 100, 1),
('Pintura Viniltex 1 Galón', 'Pintura blanca para interiores', 'Pinturas', 85000.00, 50, 1),
('Taladro Eléctrico 500W', 'Taladro para perforación en concreto y madera', 'Herramientas Eléctricas', 150000.00, 30, 2),
('Llave Stilson 18”', 'Llave de acero ajustable para tuberías', 'Herramientas Manuales', 45000.00, 40, 2),
('Bloque de Cemento 15x20x40 cm', 'Bloque para construcción de muros', 'Materiales de Construcción', 2800.00, 500, 1),
('Esmalte Sintético 1 Galón', 'Pintura de alta resistencia para exteriores', 'Pinturas', 92000.00, 35, 1),
('Sierra Circular 1200W', 'Herramienta eléctrica para cortar madera', 'Herramientas Eléctricas', 230000.00, 20, 2),
('Destornillador de Estría 6”', 'Herramienta manual para tornillos de estría', 'Herramientas Manuales', 8000.00, 100, 2),
('Tubo PVC 1/2” x 3m', 'Tubo para instalaciones hidráulicas', 'Plomería', 6200.00, 200, 1),
('Silicona Transparente 280ml', 'Sellador impermeable para juntas y grietas', 'Adhesivos y Selladores', 7500.00, 60, 1),
('Cinta Métrica 5m', 'Herramienta para medición con carcasa plástica', 'Herramientas Manuales', 12000.00, 80, 2),
('Regadera Ducha Cromada', 'Cabezal de ducha con múltiples chorros', 'Plomería', 32000.00, 25, 1),
('Brocha 3” Profesional', 'Brocha para aplicación de pintura en muros', 'Pinturas', 6500.00, 90, 1),
('Disco de Corte 4.5” Metal', 'Disco para esmeriladora angular', 'Herramientas Eléctricas', 5500.00, 150, 2),
('Ladrillo Macizo 6 Huecos', 'Ladrillo para construcción de muros', 'Materiales de Construcción', 1200.00, 800, 1),
('Arena Gruesa 40kg', 'Arena para mezcla de concreto', 'Materiales de Construcción', 8000.00, 200, 1),
('Gravilla 40kg', 'Gravilla para mezcla de concreto', 'Materiales de Construcción', 8500.00, 150, 1),
('Varilla Corrugada 1/2" x 6m', 'Varilla de acero para refuerzo', 'Materiales de Construcción', 25000.00, 120, 1),
('Alambre Negro #18 1kg', 'Alambre para amarre en construcción', 'Materiales de Construcción', 5000.00, 90, 1),
('Pintura Latex 4L', 'Pintura para interiores base agua', 'Pinturas', 95000.00, 60, 1),
('Brocha 4" Profesional', 'Brocha para pintura de calidad profesional', 'Pinturas', 12000.00, 70, 1),
('Rodillo Lana 9"', 'Rodillo para pintura de superficie lisa', 'Pinturas', 15000.00, 50, 1),
('Lija al Agua #220 10un', 'Paquete de lijas para acabados finos', 'Pinturas', 8000.00, 100, 1),
('Masilla para Madera 1kg', 'Masilla para reparación de maderas', 'Pinturas', 12000.00, 40, 1),
('Taladro Percutor 650W', 'Taladro con función percutor para concreto', 'Herramientas Eléctricas', 220000.00, 25, 2),
('Esmeril Angular 7"', 'Esmeril para corte y desbaste', 'Herramientas Eléctricas', 180000.00, 20, 2),
('Sierra Caladora 500W', 'Sierra para cortes curvos en madera', 'Herramientas Eléctricas', 150000.00, 15, 2),
('Lijadora Orbital 250W', 'Lijadora para acabados en madera', 'Herramientas Eléctricas', 120000.00, 12, 2),
('Pistola de Calor 2000W', 'Pistola para decapado y soldadura plástica', 'Herramientas Eléctricas', 85000.00, 18, 2),
('Martillo 16oz Mango Fibra', 'Martillo para carpintería y construcción', 'Herramientas Manuales', 25000.00, 60, 2),
('Destornillador Phillips #2', 'Destornillador para tornillos Phillips', 'Herramientas Manuales', 8000.00, 100, 2),
('Alicate Universal 8"', 'Alicate para múltiples usos', 'Herramientas Manuales', 15000.00, 80, 2),
('Llave Adjustable 10"', 'Llave ajustable para tuercas y tornillos', 'Herramientas Manuales', 22000.00, 50, 2),
('Cincel 1" Punta Plana', 'Cincel para trabajo en metal y concreto', 'Herramientas Manuales', 12000.00, 40, 2),
('Tubo PVC 3/4" x 3m', 'Tubo para instalaciones hidráulicas', 'Plomería', 8500.00, 150, 3),
('Codo PVC 3/4" 90°', 'Codo para cambio de dirección en tuberías', 'Plomería', 2500.00, 200, 3),
('Tee PVC 3/4"', 'Conexión en T para tuberías', 'Plomería', 3000.00, 180, 3),
('Válvula Esfera 1/2"', 'Válvula de cierre rápido para agua', 'Plomería', 18000.00, 60, 3),
('Sifón para Lavamanos', 'Sifón plástico para lavamanos', 'Plomería', 12000.00, 45, 3),
('Cinta Teflón 1/2"', 'Cinta para sellado de roscas', 'Plomería', 3000.00, 120, 3),
('Llave para Lavamanos', 'Llave monomando para lavamanos', 'Plomería', 45000.00, 30, 3),
('Regadera Ducha Ahorradora', 'Cabezal de ducha con bajo consumo', 'Plomería', 38000.00, 25, 3),
('Bomba Sumergible 1HP', 'Bomba para extracción de agua', 'Plomería', 450000.00, 8, 3),
('Tanque de Agua 500L', 'Tanque para almacenamiento de agua', 'Plomería', 280000.00, 10, 3),
('Cable THHN #12 100m', 'Cable eléctrico para instalaciones', 'Electricidad', 120000.00, 30, 4),
('Tubo Conduit 1/2" x 3m', 'Tubo para protección de cables', 'Electricidad', 8000.00, 80, 4),
('Caja Octagonal Metálica', 'Caja para conexiones eléctricas', 'Electricidad', 5000.00, 100, 4),
('Interruptor Simple', 'Interruptor de pared de 1 vía', 'Electricidad', 6000.00, 120, 4),
('Toma Corriente Doble', 'Toma para conexión de aparatos', 'Electricidad', 8000.00, 90, 4),
('Breaker 20A 1P', 'Disyuntor para protección eléctrica', 'Electricidad', 25000.00, 50, 4),
('Foco LED 9W E27', 'Foco ahorrador de energía', 'Electricidad', 10000.00, 200, 4),
('Tira LED 5m RGB', 'Tira de luces LED multicolor', 'Electricidad', 65000.00, 25, 4),
('Panel Solar 100W', 'Panel para generación de energía solar', 'Electricidad', 350000.00, 12, 4),
('Inversor 1000W', 'Inversor de corriente DC a AC', 'Electricidad', 280000.00, 8, 4),
('Clavo 2" 1kg', 'Clavos para carpintería y construcción', 'Ferretería General', 8000.00, 150, 5),
('Tornillo para Madera 1.5"', 'Tornillos autorroscantes para madera', 'Ferretería General', 12000.00, 120, 5),
('Ancla Expansiva 1/4"', 'Ancla para fijación en concreto', 'Ferretería General', 500.00, 300, 5),
('Bisagra 3" Acero', 'Bisagra para puertas y muebles', 'Ferretería General', 3000.00, 100, 5),
('Cerrojo 4"', 'Cerrojo de seguridad para puertas', 'Ferretería General', 12000.00, 60, 5),
('Candado Latón 40mm', 'Candado de seguridad', 'Ferretería General', 18000.00, 45, 5),
('Cadenas 5mm x 1m', 'Cadena de acero para múltiples usos', 'Ferretería General', 15000.00, 30, 5),
('Grapas para Pistola 1/2"', 'Grapas para fijación con pistola', 'Ferretería General', 5000.00, 80, 5),
('Rueda Giratoria 2"', 'Rueda para muebles y carritos', 'Ferretería General', 8000.00, 50, 5),
('Organizador de Herramientas', 'Portaherramientas de pared', 'Ferretería General', 35000.00, 25, 5);