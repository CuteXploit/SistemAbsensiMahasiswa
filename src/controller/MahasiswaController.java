/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Mahasiswa;
import java.sql.ResultSet;
/**
 *
 * @author HYPE AMD
 */
public class MahasiswaController {
     Connection con = Koneksi.getKoneksi();
     

    public void simpan(Mahasiswa m){

        try{

            String sql="INSERT INTO mahasiswa VALUES(?,?,?,?,?,?)";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,m.getNim());
            ps.setString(2,m.getNama());
            ps.setString(3,m.getProdi());
            ps.setInt(4,m.getSemester());
            ps.setString(5,m.getKelas());
            ps.setString(6,m.getEmail());

            ps.executeUpdate();

            System.out.println("Data berhasil disimpan");

        }catch(Exception e){

            System.out.println(e);

        }

    }
    public void ubah(Mahasiswa m){

    try{

        String sql = "UPDATE mahasiswa SET nama=?, prodi=?, semester=?, kelas=?, email=? WHERE nim=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, m.getNama());
        ps.setString(2, m.getProdi());
        ps.setInt(3, m.getSemester());
        ps.setString(4, m.getKelas());
        ps.setString(5, m.getEmail());
        ps.setString(6, m.getNim());

        ps.executeUpdate();

        System.out.println("Data berhasil diubah");

    }catch(Exception e){

        System.out.println(e);

    }

}
    public void hapus(String nim){

    try{

        String sql = "DELETE FROM mahasiswa WHERE nim=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, nim);

        ps.executeUpdate();

        System.out.println("Data berhasil dihapus");

    }catch(Exception e){

        System.out.println(e);

    }

}
    public ResultSet cari(String keyword){

    try{

        String sql = "SELECT * FROM mahasiswa WHERE nim LIKE ? OR nama LIKE ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println(e);

        return null;

    }

}
    public ResultSet tampilData(){

    try{

        String sql = "SELECT * FROM mahasiswa";

        PreparedStatement ps = con.prepareStatement(sql);

        return ps.executeQuery();

    }catch(Exception e){

        System.out.println(e);

        return null;

    }

}
}

