-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 28 jan. 2025 à 11:31
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_stock`
--

-- --------------------------------------------------------

--
-- Structure de la table `fichierhistorique`
--

CREATE TABLE `fichierhistorique` (
  `id` int(11) NOT NULL,
  `nom_fichier` varchar(255) NOT NULL,
  `date_import` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` enum('SUCCES','ERREUR') NOT NULL,
  `erreurs` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `mouvementstock`
--

CREATE TABLE `mouvementstock` (
  `id` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `type_mouvement` enum('ENTREE','SORTIE') NOT NULL,
  `quantite` int(11) NOT NULL,
  `id_rack` int(11) DEFAULT NULL,
  `date_mouvement` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `mouvementstock`
--

INSERT INTO `mouvementstock` (`id`, `id_produit`, `type_mouvement`, `quantite`, `id_rack`, `date_mouvement`) VALUES
(1, 1, 'ENTREE', 5, 1, '2025-01-22 15:41:30'),
(2, 1, 'SORTIE', 1, 1, '2025-01-22 15:42:48'),
(5, 1, 'ENTREE', 1, 1, '2025-01-22 15:43:07'),
(9, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:09'),
(10, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:10'),
(11, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:11'),
(12, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:11'),
(13, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:11'),
(14, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:11'),
(15, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:11'),
(17, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:15'),
(18, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:17'),
(19, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:18'),
(20, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:18'),
(21, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:19'),
(22, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:19'),
(23, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:19'),
(24, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:20'),
(25, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:20'),
(26, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:21'),
(27, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:21'),
(28, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(29, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(30, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(31, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(32, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(33, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(34, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:24'),
(35, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:25'),
(36, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:25'),
(37, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:25'),
(38, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:25'),
(39, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:25'),
(40, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:25'),
(41, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:26'),
(42, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:26'),
(43, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:26'),
(44, 1, 'ENTREE', 1, 1, '2025-01-23 08:06:26'),
(45, 1, 'ENTREE', 20, 1, '2025-01-23 08:06:33'),
(47, 1, 'ENTREE', 2, 1, '2025-01-23 08:06:41'),
(49, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:48'),
(50, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:49'),
(51, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:49'),
(52, 1, 'SORTIE', 1, 1, '2025-01-23 08:06:49'),
(53, 1, 'SORTIE', 46, 1, '2025-01-23 08:06:54');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `code_barre` varchar(50) NOT NULL,
  `categorie` varchar(50) DEFAULT NULL,
  `date_creation` timestamp NOT NULL DEFAULT current_timestamp(),
  `quantite_min` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `nom`, `description`, `code_barre`, `categorie`, `date_creation`, `quantite_min`) VALUES
(1, 'Roue de Vélo', NULL, '0783159888452', 'Vélo', '2025-01-22 15:40:43', 0);

-- --------------------------------------------------------

--
-- Structure de la table `rack`
--

CREATE TABLE `rack` (
  `id` int(11) NOT NULL,
  `reference` varchar(50) NOT NULL,
  `capacite_max` int(11) NOT NULL,
  `description` text DEFAULT NULL,
  `emplacement` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `rack`
--

INSERT INTO `rack` (`id`, `reference`, `capacite_max`, `description`, `emplacement`) VALUES
(1, 'Roue', 50, 'Possession des roues', 'Paris - entrepot decathlon - 5');

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

CREATE TABLE `stock` (
  `id` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `id_rack` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  `date_ajout` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `stock`
--

INSERT INTO `stock` (`id`, `id_produit`, `id_rack`, `quantite`, `date_ajout`) VALUES
(1, 1, 1, 0, '2025-01-22 15:41:30'),
(3, 1, 1, 1, '2025-01-23 09:49:33'),
(4, 1, 1, 5, '2025-01-23 09:55:46'),
(5, 1, 1, 5, '2025-01-28 10:19:42');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `fichierhistorique`
--
ALTER TABLE `fichierhistorique`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `mouvementstock`
--
ALTER TABLE `mouvementstock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_rack` (`id_rack`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code_barre` (`code_barre`);

--
-- Index pour la table `rack`
--
ALTER TABLE `rack`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `reference` (`reference`);

--
-- Index pour la table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_rack` (`id_rack`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `fichierhistorique`
--
ALTER TABLE `fichierhistorique`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `mouvementstock`
--
ALTER TABLE `mouvementstock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `rack`
--
ALTER TABLE `rack`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `stock`
--
ALTER TABLE `stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `mouvementstock`
--
ALTER TABLE `mouvementstock`
  ADD CONSTRAINT `mouvementstock_ibfk_1` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `mouvementstock_ibfk_2` FOREIGN KEY (`id_rack`) REFERENCES `rack` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `stock_ibfk_2` FOREIGN KEY (`id_rack`) REFERENCES `rack` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
