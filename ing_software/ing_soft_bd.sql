-- phpMyAdmin SQL Dump
-- version 4.6.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 30, 2017 at 04:06 PM
-- Server version: 5.7.13-log
-- PHP Version: 5.6.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ing_soft_bd`
--

-- --------------------------------------------------------

--
-- Table structure for table `acciones`
--

CREATE TABLE `acciones` (
  `accion_id` int(11) NOT NULL,
  `accion_cantidad` int(11) DEFAULT NULL,
  `accion_fecha` datetime DEFAULT NULL,
  `accion_vender_A` enum('N','J','T','JY','JN','NY','NN') DEFAULT 'T' COMMENT 'Campo a quien yo deseo vender mis acciones\nN : Personas naturales\nJ : Personas Juridicas\nT : Todos\nJY : Persona Juridica que tenga mas de [accion_condicion] acciones\nJN: Persona Juridica que tenga menos de [accion_condicion] acciones\nNY: Persona Natural que tenga mas de [accion_condicion] acciones\nNN: Persona Natural que tenga menos de [accion_condicion] acciones',
  `accion_condicion` int(11) DEFAULT '0' COMMENT 'Cantidad de acciones para condicionar la venta ',
  `usuario_usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `compra_acciones`
--

CREATE TABLE `compra_acciones` (
  `compra_acciones_id` int(11) NOT NULL,
  `compra_acciones_fecha` datetime DEFAULT NULL,
  `compra_acciones_estado` enum('A','R','E') DEFAULT NULL COMMENT 'Estado en el cual se encuentra la compra de una accion\nA : Aceptada\nR : Rechazada\nE : En espera de aprobacion',
  `valor_accion_valor_accion_id` int(11) NOT NULL,
  `usuario_usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `empresa`
--

CREATE TABLE `empresa` (
  `empresa_id` int(11) NOT NULL,
  `empresa_nit` varchar(20) DEFAULT NULL,
  `empresa_nombre` varchar(60) DEFAULT NULL,
  `empresa_sociedad` enum('LTDA','S.A.','Comandita','S.A.S') DEFAULT 'LTDA' COMMENT 'Tipos de sociedad para filtros\nLTDA\nS.A.\n''Comandita\nS.A.S\n''Comandita',
  `empresa_telefono` float DEFAULT NULL,
  `empresa_direccion` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `empresa`
--

INSERT INTO `empresa` (`empresa_id`, `empresa_nit`, `empresa_nombre`, `empresa_sociedad`, `empresa_telefono`, `empresa_direccion`) VALUES
(19, '411111-1', 'prueba', 'S.A.', 123456, 'Av 12 No 12 -12 '),
(20, '5111111-1', 'Prueba_2', 'Comandita', 123456, 'Av 12 No 12 -!2'),
(21, '4111112-2', 'Prueba', 'LTDA', 12345, 'admin'),
(22, '311111-1', 'test', 'S.A.', 12341, 'aDSF');

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL,
  `usuario_nombre` varchar(100) DEFAULT NULL,
  `usuario_apellido` varchar(100) DEFAULT NULL,
  `usuario_cedula` float DEFAULT NULL,
  `usuario_correo` varchar(45) DEFAULT NULL,
  `usuario_telefono` float DEFAULT NULL,
  `usuairo_clave` varchar(45) DEFAULT NULL,
  `usuario_tipo` enum('AD','AF') DEFAULT 'AF' COMMENT 'Tipo de Usuario\n''AD : Administrador , AF : Afiliado''',
  `usuario_persona` enum('N','J') DEFAULT 'J' COMMENT 'Usuario_Persona\nN : Persona Natural , J : Persona Juridica\n',
  `empresa_empresa_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`usuario_id`, `usuario_nombre`, `usuario_apellido`, `usuario_cedula`, `usuario_correo`, `usuario_telefono`, `usuairo_clave`, `usuario_tipo`, `usuario_persona`, `empresa_empresa_id`) VALUES
(1, 'Admin', 'Prueba', 1234, 'admin@prueba.com', 123456, 'abc12345', 'AD', 'J', 19),
(2, 'Admin', 'Admin', 123, 'admin@comandita.com', 123456, '12345abc', 'AD', 'J', 20),
(3, 'admin', 'test', 12313, 'admin@test.com', 1231320, 'abcd12345', 'AD', 'J', 21),
(4, 'ADMIN', 'ADMIN', 141234, 'admin@admin.com', 123412, 'abc12345', 'AD', 'J', 22);

-- --------------------------------------------------------

--
-- Table structure for table `valor_accion`
--

CREATE TABLE `valor_accion` (
  `valor_accion_inicio` datetime DEFAULT NULL COMMENT 'Fecha en que inicia un valor de una accion',
  `valor_accion_fin` datetime DEFAULT NULL COMMENT 'Fecha en que termina un valor de accion',
  `valor_accion_estado` enum('A','F') DEFAULT NULL COMMENT 'Estado en que se encuentra el valor de una accion\nA : Valor activo de las acciones\nF : Valor finalizado de la accion',
  `valor_accion_valor` float DEFAULT NULL COMMENT 'Valor de la accion',
  `acciones_accion_id` int(11) NOT NULL,
  `valor_accion_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acciones`
--
ALTER TABLE `acciones`
  ADD PRIMARY KEY (`accion_id`),
  ADD KEY `fk_acciones_usuario1_idx` (`usuario_usuario_id`);

--
-- Indexes for table `compra_acciones`
--
ALTER TABLE `compra_acciones`
  ADD PRIMARY KEY (`compra_acciones_id`,`valor_accion_valor_accion_id`),
  ADD KEY `fk_compra_acciones_valor_accion1_idx` (`valor_accion_valor_accion_id`),
  ADD KEY `fk_compra_acciones_usuario1_idx` (`usuario_usuario_id`);

--
-- Indexes for table `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`empresa_id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuario_id`),
  ADD KEY `fk_usuario_empresa_idx` (`empresa_empresa_id`);

--
-- Indexes for table `valor_accion`
--
ALTER TABLE `valor_accion`
  ADD PRIMARY KEY (`valor_accion_id`),
  ADD KEY `fk_valor_accion_acciones1_idx` (`acciones_accion_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `compra_acciones`
--
ALTER TABLE `compra_acciones`
  MODIFY `compra_acciones_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `empresa`
--
ALTER TABLE `empresa`
  MODIFY `empresa_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuario_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `valor_accion`
--
ALTER TABLE `valor_accion`
  MODIFY `valor_accion_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `acciones`
--
ALTER TABLE `acciones`
  ADD CONSTRAINT `fk_acciones_usuario1` FOREIGN KEY (`usuario_usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `compra_acciones`
--
ALTER TABLE `compra_acciones`
  ADD CONSTRAINT `fk_compra_acciones_usuario1` FOREIGN KEY (`usuario_usuario_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_compra_acciones_valor_accion1` FOREIGN KEY (`valor_accion_valor_accion_id`) REFERENCES `valor_accion` (`valor_accion_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_empresa` FOREIGN KEY (`empresa_empresa_id`) REFERENCES `empresa` (`empresa_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `valor_accion`
--
ALTER TABLE `valor_accion`
  ADD CONSTRAINT `fk_valor_accion_acciones1` FOREIGN KEY (`acciones_accion_id`) REFERENCES `acciones` (`accion_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
