DROP DATABASE IF EXISTS inventario;

CREATE DATABASE IF NOT EXISTS inventario
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_spanish_ci;
USE inventario;

CREATE TABLE IF NOT EXISTS categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

CREATE TABLE IF NOT EXISTS productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    stock_actual INT UNSIGNED NOT NULL,
    stock_minimo INT UNSIGNED NOT NULL CHECK (stock_minimo >= 1),
    id_categoria INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

INSERT INTO categorias (nombre, descripcion) VALUES
('Medicamento', 'Fármacos y medicamentos de uso clínico'),
('Fungible', 'Material de un solo uso o de reposición frecuente'),
('Inmovilización', 'Material para la inmovilización y soporte del paciente'),
('Electromedicina-medición', 'Equipos electrónicos de medición y soporte vital'),
('Limpieza', 'Productos de higiene y desinfección'),
('Otros', 'Material diverso no categorizado');

INSERT INTO productos (nombre, stock_actual, stock_minimo, id_categoria) VALUES
('Gasas', 30, 25, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Manta térmica', 5, 5, (SELECT id_categoria FROM categorias WHERE nombre = 'Otros')),
('Venda crepé 10x5', 5, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Mascarilla 7cc Adulto', 15, 13, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Mascarilla 100% Adulto', 10, 8, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Mascarilla nebulización Adulto', 10, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Jeringa 2ml', 15, 15, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Jeringa 5ml', 7, 15, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Jeringa 10ml', 11, 15, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Vacutainer', 10, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Tijeras quirúrgicas 14cm', 3, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Otros')),
('Guantes desechables Talla M', 100, 150, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Guantes desechables Talla L', 80, 100, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Esparadrapo 5m', 20, 30, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Bata estéril Talla XL', 8, 8, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Termómetro digital', 4, 5, (SELECT id_categoria FROM categorias WHERE nombre = 'Electromedicina-medición')),
('Desinfectante manos 500ml', 12, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Limpieza')),
('Tubo endotraqueal 8mm', 2, 5, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Venda elástica 10cm', 7, 15, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Compresa estéril', 20, 25, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Catéter intravenoso 20G', 5, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Catéter intravenoso 18G', 3, 8, (SELECT id_categoria FROM categorias WHERE nombre = 'Fungible')),
('Aspirina 500', 50, 20, (SELECT id_categoria FROM categorias WHERE nombre = 'Medicamento')),
('Adiro 100', 40, 15, (SELECT id_categoria FROM categorias WHERE nombre = 'Medicamento')),
('Adiro 300', 30, 10, (SELECT id_categoria FROM categorias WHERE nombre = 'Medicamento')),
('Collarín cervical 4 posiciones', 10, 5, (SELECT id_categoria FROM categorias WHERE nombre = 'Inmovilización')),
('Collarín cervical short', 8, 4, (SELECT id_categoria FROM categorias WHERE nombre = 'Inmovilización')),
('Collarín cervical no-neck', 5, 3, (SELECT id_categoria FROM categorias WHERE nombre = 'Inmovilización'));
