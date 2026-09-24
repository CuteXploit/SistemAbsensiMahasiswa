🚀 Cara Menjalankan Project
1. Persiapan

Pastikan komputer sudah terinstall:

Java JDK
NetBeans IDE
XAMPP
MySQL

Disarankan menggunakan versi Java yang sesuai dengan konfigurasi project.

2. Clone Repository

Buka Git Bash atau Terminal, kemudian jalankan:

git clone https://github.com/CuteXploit/SistemAbsensiMahasiswa.git

Masuk ke folder project:

cd SistemAbsensiMahasiswa
3. Membuka Project di NetBeans
Buka NetBeans IDE
Pilih menu:
File → Open Project
Cari folder:
SistemAbsensiMahasiswa
Pilih project tersebut.
Klik Open Project.

Project akan muncul pada bagian Projects di NetBeans.

🗄️ 4. Menyiapkan Database MySQL

Jalankan XAMPP Control Panel.

Aktifkan:

Apache
MySQL

Kemudian buka:

http://localhost/phpmyadmin

Buat database sesuai dengan nama database yang digunakan pada project.

Contoh:

CREATE DATABASE db_absensi;

Jika repository menyediakan file .sql, import file tersebut melalui phpMyAdmin untuk membuat database beserta tabel yang dibutuhkan.

🔌 5. Mengatur Koneksi Database

Cari file koneksi database pada project, biasanya berada di:

src/database/

atau file:

Koneksi.java

Sesuaikan konfigurasi database:

String url = "jdbc:mysql://localhost:3306/db_absensi";
String user = "root";
String password = "";

Sesuaikan:

db_absensi → nama database
root → username MySQL
password → password MySQL jika ada

Contoh jika MySQL menggunakan password:

String password = "password_mysql";
▶️ 6. Menjalankan Aplikasi

Setelah database dan koneksi selesai:

Buka project di NetBeans.
Pastikan tidak terdapat error pada project.
Klik kanan project.
Pilih:
Run

atau tekan:

F6

Aplikasi akan menampilkan halaman Login.

🔐 Login

Gunakan akun yang sudah tersedia pada database.

Contoh akun:

Username : admin
Password : admin123
Role     : Admin

Akun login dapat berbeda tergantung data yang terdapat pada database.

📌 Alur Penggunaan
Login
  ↓
Dashboard
  ↓
Mahasiswa
  ↓
Dosen
  ↓
Mata Kuliah
  ↓
Jadwal
  ↓
Absensi
  ↓
Rekap Absensi
  ↓
Laporan
📸 Tampilan Aplikasi
Login

Halaman login digunakan untuk melakukan autentikasi pengguna sebelum masuk ke sistem.

Dashboard

Dashboard menampilkan informasi singkat seperti jumlah mahasiswa, jumlah dosen, mata kuliah, jadwal, serta statistik absensi.

Data Mahasiswa

Digunakan untuk mengelola data mahasiswa yang terdaftar dalam sistem.

Data Dosen

Digunakan untuk mengelola data dosen.

Mata Kuliah

Digunakan untuk mengelola data mata kuliah.

Jadwal

Digunakan untuk mengatur jadwal perkuliahan.

Absensi

Digunakan untuk mencatat kehadiran mahasiswa.

Rekap Absensi

Menampilkan hasil rekap kehadiran mahasiswa.

👨‍💻 Pengembang

Kelompok 1

Project:

Sistem Absensi Mahasiswa

Dibangun sebagai project pembelajaran dalam pengembangan aplikasi desktop menggunakan Java, NetBeans, dan MySQL.

📄 Lisensi

Project ini dibuat untuk keperluan pembelajaran dan pengembangan akademik.

⭐ Jika project ini membantu, jangan lupa berikan Star pada repository.


### 🔥 Biar README-nya makin keren

Menurutku ada **satu tambahan penting**: screenshot aplikasi kamu.

Misalnya buat folder:

```text
screenshots/
├── login.png
├── dashboard.png
├── mahasiswa.png
└── absensi.png
