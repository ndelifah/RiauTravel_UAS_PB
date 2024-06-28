-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Jun 2024 pada 07.34
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_travel`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `boking`
--

CREATE TABLE `boking` (
  `id` int(11) NOT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `no_telpon` varchar(255) DEFAULT NULL,
  `destinasi` varchar(255) DEFAULT NULL,
  `jumlah_tiket` int(11) DEFAULT NULL,
  `tanggal` datetime DEFAULT NULL,
  `harga` int(11) DEFAULT NULL,
  `gambar` text NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `boking`
--

INSERT INTO `boking` (`id`, `nama`, `no_telpon`, `destinasi`, `jumlah_tiket`, `tanggal`, `harga`, `gambar`, `createdAt`, `updatedAt`) VALUES
(1, 'Nur Delifa', '082285393727', 'pekanbaru - Sumbar', 2, '2024-05-27 00:00:00', 300000, '', '2024-06-19 16:12:05', '2024-06-19 17:22:28'),
(2, 'Jhon Doe', '08123456789', 'pekanbaru - Bukittinggi', 1, '2024-05-27 00:00:00', 180000, '', '2024-06-19 17:19:35', '2024-06-19 17:19:35'),
(3, 'Harry Finaldhi', '081267352270', 'pekanbaru - Indragiri Hulu', 2, '2024-05-27 00:00:00', 300000, '', '2024-06-19 16:06:48', '2024-06-19 17:21:41'),
(5, 'poter', '082212341234', 'Pekanbaru - Indragiri Hilir', 1, '2024-06-20 00:00:00', 200000, '', '2024-06-19 21:06:04', '2024-06-19 21:06:04'),
(6, 'Akbar Faisal', '082189428976', 'Pekanbaru - Taluk', 1, '2024-06-21 00:00:00', 250000, '', '2024-06-19 21:11:03', '2024-06-20 05:41:48'),
(11, 'Bagas', '089155893456', 'pekanbaru - meranti', 1, '2024-06-21 00:00:00', 200000, '', '2024-06-20 06:10:56', '2024-06-20 06:10:56'),
(12, 'Ayu', '082189428976', 'pekanbaru - meranti', 3, '2024-06-28 00:00:00', 400000, '', '2024-06-20 10:14:01', '2024-06-20 10:14:01'),
(14, 'AMEK', '086647561214', 'JAKARTA - PERAWANG', 2, '2024-06-24 19:24:08', 1000000, '', '2024-06-24 19:24:08', '2024-06-24 14:41:38'),
(15, 'MAS FAIZ', '0895618142221', 'PEKANBARU - DURI', 1, '2024-06-25 12:32:25', 250000, '667a566933937-cat4.png', '2024-06-25 12:32:25', '2024-06-25 12:32:25'),
(16, 'AQMAL SYARIF', '089513464879', 'ROHUL', 6, '2024-06-25 12:34:44', 12000000, '1000051201.jpg', '2024-06-25 12:34:44', '2024-06-26 16:12:24'),
(26, 'ADE CHANDRA ', '08124513551245', 'Panpua', 598, '2024-06-27 11:17:31', 2147483647, '667ce7dbd941e-1000051199.jpg', '2024-06-27 11:17:31', '2024-06-27 11:17:31');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(256) NOT NULL,
  `fullname` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `email`, `fullname`, `password`) VALUES
(1, 'irfanafrizal2020@gmail.com', 'IRPAN AFRIZAL PUTRA ERIANI', '202cb962ac59075b964b07152d234b70');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `boking`
--
ALTER TABLE `boking`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `boking`
--
ALTER TABLE `boking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
