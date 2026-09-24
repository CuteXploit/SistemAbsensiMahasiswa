-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 24 Sep 2026 pada 05.43
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
-- Database: `db_absensi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `absensi`
--

CREATE TABLE `absensi` (
  `id_absensi` int(11) NOT NULL,
  `nim` varchar(15) NOT NULL,
  `id_jadwal` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `pertemuan` int(11) NOT NULL,
  `status` enum('Hadir','Izin','Sakit','Alpha') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `absensi`
--

INSERT INTO `absensi` (`id_absensi`, `nim`, `id_jadwal`, `tanggal`, `pertemuan`, `status`) VALUES
(1, '220101001', 1, '2026-05-23', 7, 'Hadir'),
(2, '220101002', 1, '2026-05-23', 7, 'Izin'),
(3, '220101003', 1, '2026-05-23', 7, 'Hadir'),
(4, '220101004', 1, '2026-05-23', 7, 'Sakit'),
(5, '220101005', 1, '2026-05-23', 7, 'Alpha'),
(6, '220101001', 2, '2026-05-24', 1, 'Hadir'),
(7, '220101001', 3, '2026-05-25', 1, 'Hadir'),
(8, '220101001', 4, '2026-05-26', 1, 'Izin'),
(9, '220101001', 2, '2026-05-24', 7, 'Hadir'),
(10, '220101002', 2, '2026-05-24', 7, 'Hadir'),
(11, '220101003', 2, '2026-05-24', 7, 'Izin'),
(12, '220101004', 2, '2026-05-24', 7, 'Hadir'),
(13, '220101005', 2, '2026-05-24', 7, 'Sakit'),
(14, '220101001', 3, '2026-05-25', 7, 'Alpha'),
(15, '220101002', 3, '2026-05-25', 7, 'Hadir'),
(16, '220101003', 3, '2026-05-25', 7, 'Hadir'),
(17, '220101004', 3, '2026-05-25', 7, 'Izin'),
(18, '220101005', 3, '2026-05-25', 7, 'Hadir'),
(19, '220101001', 4, '2026-05-26', 7, 'Hadir'),
(20, '220101002', 4, '2026-05-26', 7, 'Sakit'),
(21, '220101003', 4, '2026-05-26', 7, 'Hadir'),
(22, '220101004', 4, '2026-05-26', 7, 'Hadir'),
(23, '220101005', 4, '2026-05-26', 7, 'Izin'),
(24, '220101001', 1, '2026-07-13', 1, 'Hadir'),
(25, '220101002', 1, '2026-07-13', 1, 'Hadir'),
(26, '220101003', 1, '2026-07-13', 1, 'Hadir'),
(27, '220101004', 1, '2026-07-13', 1, 'Hadir'),
(28, '220101005', 1, '2026-07-13', 1, 'Hadir'),
(29, '220101001', 1, '2026-07-13', 1, 'Hadir'),
(30, '220101002', 1, '2026-07-13', 1, 'Hadir'),
(31, '220101003', 1, '2026-07-13', 1, 'Hadir'),
(32, '220101004', 1, '2026-07-13', 1, 'Hadir'),
(33, '220101005', 1, '2026-07-13', 1, 'Hadir'),
(34, '220101001', 1, '2026-07-14', 1, 'Alpha'),
(35, '220101002', 1, '2026-07-14', 1, 'Hadir'),
(36, '220101003', 1, '2026-07-14', 1, 'Hadir'),
(37, '220101004', 1, '2026-07-14', 1, 'Hadir'),
(38, '220101005', 1, '2026-07-14', 1, 'Hadir'),
(39, '220101001', 2, '2026-07-14', 2, 'Sakit'),
(40, '220101002', 2, '2026-07-14', 2, 'Hadir'),
(41, '220101003', 2, '2026-07-14', 2, 'Hadir'),
(42, '220101004', 2, '2026-07-14', 2, 'Hadir'),
(43, '220101005', 2, '2026-07-14', 2, 'Hadir'),
(44, '1111111111', 5, '2026-07-14', 1, 'Hadir'),
(45, '2501161004', 5, '2026-07-14', 1, 'Alpha'),
(46, '2501161006', 5, '2026-07-14', 1, 'Hadir'),
(47, '2501162001', 5, '2026-07-14', 1, 'Hadir'),
(48, '2501162004', 5, '2026-07-14', 1, 'Hadir'),
(49, '2501163007', 5, '2026-07-14', 1, 'Hadir'),
(50, '1111111111', 5, '2026-07-14', 1, 'Hadir'),
(51, '2501161004', 5, '2026-07-14', 1, 'Hadir'),
(52, '2501161006', 5, '2026-07-14', 1, 'Hadir'),
(53, '2501162001', 5, '2026-07-14', 1, 'Hadir'),
(54, '2501162004', 5, '2026-07-14', 1, 'Hadir'),
(55, '2501163007', 5, '2026-07-14', 1, 'Hadir'),
(56, '220101001', 1, '2026-07-14', 1, 'Hadir'),
(57, '220101002', 1, '2026-07-14', 1, 'Hadir'),
(58, '220101003', 1, '2026-07-14', 1, 'Hadir'),
(59, '220101004', 1, '2026-07-14', 1, 'Hadir'),
(60, '220101005', 1, '2026-07-14', 1, 'Hadir'),
(61, '220101001', 1, '2026-07-14', 2, 'Alpha'),
(62, '220101002', 1, '2026-07-14', 2, 'Hadir'),
(63, '220101003', 1, '2026-07-14', 2, 'Hadir'),
(64, '220101004', 1, '2026-07-14', 2, 'Hadir'),
(65, '220101005', 1, '2026-07-14', 2, 'Hadir');

-- --------------------------------------------------------

--
-- Struktur dari tabel `dosen`
--

CREATE TABLE `dosen` (
  `nidn` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `prodi` varchar(50) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `dosen`
--

INSERT INTO `dosen` (`nidn`, `nama`, `prodi`, `no_hp`, `email`) VALUES
('0001129501', 'Ulia Ulfa test', 'Sistem Informasi', 'Item 1', 'uliaulfacantik123@gmail.com'),
('0412019001', 'Budi Santoso, M.Kom', 'Teknik Informatika', '081234567890', 'budi@email.com'),
('0415069201', 'Andi Wijaya, M.Kom', 'Teknik Informatika', '081234567892', 'andi@email.com'),
('0420058001', 'Siti Aminah, M.Kom', 'Teknik Informatika', '081234567891', 'siti@email.com'),
('0430128801', 'Dewi Lestari, M.Kom', 'Teknik Informatika', '081234567893', 'dewi@email.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jadwal`
--

CREATE TABLE `jadwal` (
  `id_jadwal` int(11) NOT NULL,
  `kode_mk` varchar(10) NOT NULL,
  `nidn` varchar(20) NOT NULL,
  `kelas` varchar(20) NOT NULL,
  `hari` enum('Senin','Selasa','Rabu','Kamis','Jumat','Sabtu') NOT NULL,
  `jam` varchar(20) NOT NULL,
  `ruangan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jadwal`
--

INSERT INTO `jadwal` (`id_jadwal`, `kode_mk`, `nidn`, `kelas`, `hari`, `jam`, `ruangan`) VALUES
(1, 'MK001', '0412019001', 'TI-2A', 'Senin', '08:00 - 09:40', 'A-101'),
(2, 'MK002', '0420058001', 'TI-2A', 'Senin', '10:00 - 11:40', 'A-102'),
(3, 'MK003', '0415069201', 'TI-2A', 'Selasa', '13:00 - 14:40', 'B-201'),
(4, 'MK004', '0430128801', 'TI-1A', 'Selasa', '15:00 - 16:40', 'B-202'),
(5, 'MK001', '0412019001', 'A', 'Senin', '07.30 - 09.10', 'Lab 1'),
(6, 'MK005', '0420058001', 'A', 'Jumat', '09.20 - 11.00', 'R101');

-- --------------------------------------------------------

--
-- Struktur dari tabel `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `nim` varchar(15) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `prodi` varchar(50) NOT NULL,
  `semester` int(11) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `kelas` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mahasiswa`
--

INSERT INTO `mahasiswa` (`nim`, `nama`, `prodi`, `semester`, `status`, `kelas`, `email`) VALUES
('1111111111', 'arkaa', 'Sistem Informasi', 1, 'Mahasiswa', '1A', 'arka@roketmail.com'),
('220101001', 'Ahmad Rizki', 'Teknik Informatika', 4, NULL, 'TI-2A', 'ahmadrizki@email.com'),
('220101002', 'Budi Setiawan', 'Teknik Informatika', 4, NULL, 'TI-2A', 'budi@email.com'),
('220101003', 'Citra Lestari', 'Teknik Informatika', 4, NULL, 'TI-2A', 'citra@email.com'),
('220101004', 'Dimas Saputra', 'Teknik Informatika', 4, NULL, 'TI-2A', 'dimas@email.com'),
('220101005', 'Eka Putri', 'Teknik Informatika', 4, NULL, 'TI-2A', 'eka@email.com'),
('2501161004', 'Gea Amanda', 'Sistem Informasi', 2, 'Mahasiswa', '1A', ''),
('2501161006', 'hansel', 'Sistem Informasi', 1, 'Mahasiswa', '1A', 'hasel@gmail.com'),
('2501162001', 'hasbi', 'Sistem Informasi', 1, 'Mahasiswa', '1A', 'hasbi@gmail.com'),
('2501162004', 'haris tampan rupawan', 'Sistem Informasi', 1, 'Mahasiswa', '1A', 'harisaja@gmail.com'),
('2501163007', 'Syeren Imut', 'Sistem Informasi', 6, 'Mahasiswa', '1A', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `matakuliah`
--

CREATE TABLE `matakuliah` (
  `kode_mk` varchar(10) NOT NULL,
  `nama_mk` varchar(100) NOT NULL,
  `sks` int(11) NOT NULL,
  `semester` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `matakuliah`
--

INSERT INTO `matakuliah` (`kode_mk`, `nama_mk`, `sks`, `semester`) VALUES
('MK001', 'Pemrograman Berorientasi Objek', 3, 4),
('MK002', 'Basis Data', 3, 4),
('MK003', 'Jaringan Komputer', 3, 4),
('MK004', 'Algoritma dan Pemrograman', 3, 2),
('MK005', 'Teknik Industri', 2, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('Admin','Dosen','Mahasiswa') NOT NULL,
  `id_ref` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `role`, `id_ref`) VALUES
(1, 'admin', 'admin123', 'Admin', NULL),
(2, 'dosen1', 'dosen123', 'Dosen', '0412019001'),
(3, 'dosen2', 'dosen123', 'Dosen', '0420058001'),
(4, 'dosen3', 'dosen123', 'Dosen', '0415069201'),
(5, 'dosen4', 'dosen123', 'Dosen', '0430128801'),
(6, 'mhs1', 'mhs123', 'Mahasiswa', '220101001'),
(7, 'mhs2', 'mhs123', 'Mahasiswa', '220101002'),
(8, 'mhs3', 'mhs123', 'Mahasiswa', '220101003'),
(9, 'mhs4', 'mhs123', 'Mahasiswa', '220101004'),
(10, 'mhs5', 'mhs123', 'Mahasiswa', '220101005');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`id_absensi`),
  ADD KEY `fk_absensi_mahasiswa` (`nim`),
  ADD KEY `fk_absensi_jadwal` (`id_jadwal`);

--
-- Indeks untuk tabel `dosen`
--
ALTER TABLE `dosen`
  ADD PRIMARY KEY (`nidn`);

--
-- Indeks untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`id_jadwal`),
  ADD KEY `fk_jadwal_mk` (`kode_mk`),
  ADD KEY `fk_jadwal_dosen` (`nidn`);

--
-- Indeks untuk tabel `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`nim`);

--
-- Indeks untuk tabel `matakuliah`
--
ALTER TABLE `matakuliah`
  ADD PRIMARY KEY (`kode_mk`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `absensi`
--
ALTER TABLE `absensi`
  MODIFY `id_absensi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;

--
-- AUTO_INCREMENT untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  MODIFY `id_jadwal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `fk_absensi_jadwal` FOREIGN KEY (`id_jadwal`) REFERENCES `jadwal` (`id_jadwal`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_absensi_mahasiswa` FOREIGN KEY (`nim`) REFERENCES `mahasiswa` (`nim`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `jadwal`
--
ALTER TABLE `jadwal`
  ADD CONSTRAINT `fk_jadwal_dosen` FOREIGN KEY (`nidn`) REFERENCES `dosen` (`nidn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_jadwal_mk` FOREIGN KEY (`kode_mk`) REFERENCES `matakuliah` (`kode_mk`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
