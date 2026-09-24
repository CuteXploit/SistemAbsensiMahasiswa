/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Jadwal;
/**
 *
 * @author HYPE AMD
 */
public class JadwalController {
     Connection con = Koneksi.getKoneksi();
     
     //method simpan
     public void simpan(Jadwal j){

    try{

       String sql =
        "INSERT INTO jadwal(kode_mk,nidn,kelas,hari,jam,ruangan) VALUES(?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, j.getKodeMk());
        ps.setString(2, j.getNidn());
        ps.setString(3, j.getKelas());
        ps.setString(4, j.getHari());
        ps.setString(5, j.getJam());
        ps.setString(6, j.getRuangan());

        ps.executeUpdate();

        System.out.println("Data jadwal berhasil disimpan");

    }catch(Exception e){

        System.out.println(e);

    }

}
     //method ubah
     public void ubah(Jadwal j){

    try{

        String sql="UPDATE jadwal SET kode_mk=?, nidn=?, kelas=?, hari=?, jam=?, ruangan=? WHERE id_jadwal=?";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setString(1,j.getKodeMk());
        ps.setString(2,j.getNidn());
        ps.setString(3,j.getKelas());
        ps.setString(4,j.getHari());
        ps.setString(5,j.getJam());
        ps.setString(6,j.getRuangan());
        ps.setInt(7,j.getIdJadwal());

        ps.executeUpdate();

        System.out.println("Data jadwal berhasil diubah");

    }catch(Exception e){

        System.out.println(e);

    }

}
     //method hapus
     public void hapus(int id){

    try{

        String sql="DELETE FROM jadwal WHERE id_jadwal=?";

        PreparedStatement ps=con.prepareStatement(sql);

        ps.setInt(1,id);

        ps.executeUpdate();

        System.out.println("Data jadwal berhasil dihapus");

    }catch(Exception e){

        System.out.println(e);

    }

}
     //method cari
     public ResultSet cari(String keyword){

    try{

        String sql =
        "SELECT j.id_jadwal, j.kode_mk, m.nama_mk, j.nidn, d.nama, " +
        "j.kelas, j.hari, j.jam, j.ruangan " +
        "FROM jadwal j " +
        "JOIN matakuliah m ON j.kode_mk = m.kode_mk " +
        "JOIN dosen d ON j.nidn = d.nidn " +
        "WHERE m.nama_mk LIKE ? " +
        "OR d.nama LIKE ? " +
        "OR j.kelas LIKE ? " +
        "OR j.hari LIKE ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1,"%"+keyword+"%");
        ps.setString(2,"%"+keyword+"%");
        ps.setString(3,"%"+keyword+"%");
        ps.setString(4,"%"+keyword+"%");

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println(e);

        return null;

    }

}
     //method tampil data(Join) antar mata kuliah dan nama dosen
     public ResultSet tampilData(){

    try{

        String sql =
        "SELECT " +
        "j.id_jadwal, " +
        "j.kode_mk, " +
        "m.nama_mk, " +
        "j.nidn, " +
        "d.nama, " +
        "j.kelas, " +
        "j.hari, " +
        "j.jam, " +
        "j.ruangan " +
        "FROM jadwal j " +
        "JOIN matakuliah m ON j.kode_mk = m.kode_mk " +
        "JOIN dosen d ON j.nidn = d.nidn";
        
        

        PreparedStatement ps=con.prepareStatement(sql);

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println(e);

        return null;

    }

}
}
