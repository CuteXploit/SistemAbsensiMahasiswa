# 🎓 Sistem Absensi Mahasiswa

Aplikasi desktop **Sistem Absensi Mahasiswa** berbasis Java yang digunakan untuk mengelola data mahasiswa, dosen, mata kuliah, jadwal, absensi, rekap absensi, dan laporan.

---
<div>
  <img align="center" width="60%" src="https://raw.githubusercontent.com/CuteXploit/SistemAbsensiMahasiswa/refs/heads/main/Screenshot%202026-09-24%20104605.png">
</div>

## ✨ Fitur

- 🔐 Login Admin
- 📊 Dashboard
- 👨‍🎓 Data Mahasiswa
- 👨‍🏫 Data Dosen
- 📚 Data Mata Kuliah
- 📅 Jadwal Kuliah
- 📝 Absensi Mahasiswa
- 📋 Rekap Absensi
- 📄 Laporan
- 🚪 Logout

---

## 🛠️ Teknologi

- ☕ Java
- 🖥️ NetBeans IDE
- 🎨 Java Swing
- 🗄️ MySQL
- 🔌 JDBC
- ⚙️ XAMPP

---

## 📁 Struktur Project

```text
SistemAbsensiMahasiswa/
├── nbproject/
├── src/
│   ├── controller/
│   ├── database/
│   ├── model/
│   └── view/
├── test/
├── build.xml
├── manifest.mf
├── login.png
├── dashboard.png
├── mahasiswa.png
├── dosen.png
├── matakuliah.png
├── jadwal.png

🚀 Cara Install
1. Clone Repository
bash
git clone https://github.com/CuteXploit/SistemAbsensiMahasiswa.git
Masuk ke folder:

bash
cd SistemAbsensiMahasiswa
2. Buka di NetBeans
Buka NetBeans IDE

Pilih File → Open Project

Pilih folder SistemAbsensiMahasiswa

Klik Open Project

Tunggu NetBeans membaca project

3. Siapkan Database
Jalankan XAMPP, kemudian aktifkan:

text
Apache
MySQL
Buka:

text
http://localhost/phpmyadmin
Buat database sesuai dengan database yang digunakan pada project.

4. Konfigurasi Database
Buka file koneksi database pada:

text
src/database/
Sesuaikan konfigurasi MySQL, contoh:

java
String url = "jdbc:mysql://localhost:3306/db_absensi";
String user = "root";
String password = "";
Sesuaikan nama database, username, dan password dengan konfigurasi MySQL pada komputer.

5. Jalankan Project
Di NetBeans:

text
Klik kanan Project
→ Run
Atau tekan:

text
F6
🔐 Login
Contoh akun:

text
Username : admin
Password : admin123
Role     : Admin
⚠️ Akun dapat berbeda tergantung data yang tersedia pada database.

👨‍💻 Pengembang
Kelompok 1

Sistem Absensi Mahasiswa

Dibuat menggunakan Java, NetBeans, dan MySQL sebagai project pembelajaran.

📄 Lisensi
Project ini dibuat untuk keperluan pembelajaran dan akademik.
├── absensi.png
├── rekap.png
└── README.md
