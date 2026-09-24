/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Absensi;
/**
 *
 * @author HYPE AMD
 */
public class AbsensiController {
     Connection con = Koneksi.getKoneksi();
     
     //method simpan
     public void simpan(Absensi a){

    try{

        String sql="INSERT INTO absensi VALUES(?,?,?,?,?)";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setInt(1,a.getIdAbsensi());
        ps.setString(2,a.getNim());
        ps.setInt(3,a.getIdJadwal());
        ps.setDate(4,new java.sql.Date(a.getTanggal().getTime()));
        ps.setString(5,a.getStatus());

        ps.executeUpdate();

        System.out.println("Absensi berhasil disimpan");

    }catch(Exception e){

        System.out.println(e);

    }

}
    //method ubah
     public void ubah(Absensi a){

    try{

        String sql="UPDATE absensi SET nim=?, id_jadwal=?, tanggal=?, status=? WHERE id_absensi=?";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setString(1,a.getNim());
        ps.setInt(2,a.getIdJadwal());
        ps.setDate(3,new java.sql.Date(a.getTanggal().getTime()));
        ps.setString(4,a.getStatus());
        ps.setInt(5,a.getIdAbsensi());

        ps.executeUpdate();

        System.out.println("Absensi berhasil diubah");

    }catch(Exception e){

        System.out.println(e);

    }

}
     //method hapus
     public void hapus(int id){

    try{

        String sql="DELETE FROM absensi WHERE id_absensi=?";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setInt(1,id);

        ps.executeUpdate();

        System.out.println("Absensi berhasil dihapus");

    }catch(Exception e){

        System.out.println(e);

    }

}
     //method tampildata
     public ResultSet tampilData(){

    try{

        String sql=
        "SELECT a.id_absensi, " +
        "mhs.nim, " +
        "mhs.nama, " +
        "mk.nama_mk, " +
        "a.tanggal, " +
        "a.status " +
        "FROM absensi a " +
        "JOIN mahasiswa mhs ON a.nim=mhs.nim " +
        "JOIN jadwal j ON a.id_jadwal=j.id_jadwal " +
        "JOIN matakuliah mk ON j.kode_mk=mk.kode_mk";

        PreparedStatement ps=con.prepareStatement(sql);

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println(e);

        return null;

    }

}
     //method cari
     public ResultSet cari(String keyword){

    try{

        String sql=
        "SELECT a.id_absensi,mhs.nim,mhs.nama,mk.nama_mk,a.tanggal,a.status " +
        "FROM absensi a " +
        "JOIN mahasiswa mhs ON a.nim=mhs.nim " +
        "JOIN jadwal j ON a.id_jadwal=j.id_jadwal " +
        "JOIN matakuliah mk ON j.kode_mk=mk.kode_mk " +
        "WHERE mhs.nim LIKE ? OR mhs.nama LIKE ?";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setString(1,"%"+keyword+"%");
        ps.setString(2,"%"+keyword+"%");

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println(e);

        return null;

    }

}
     //method hitung status
     public int hitungStatus(String status){

    try{

        String sql="SELECT COUNT(*) FROM absensi WHERE status=?";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setString(1,status);

        ResultSet rs=ps.executeQuery();

        if(rs.next()){

            return rs.getInt(1);

        }

    }catch(Exception e){

        System.out.println(e);

    }

    return 0;

}
}

