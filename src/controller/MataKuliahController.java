/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Matakuliah;

/**
 *
 * @author HYPE AMD
 */
public class MataKuliahController {
     Connection con = Koneksi.getKoneksi();

    // ================= SIMPAN =================
    public void simpan(Matakuliah mk){

        try{

            String sql="INSERT INTO matakuliah VALUES(?,?,?,?)";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1, mk.getKodeMk());
            ps.setString(2, mk.getNamaMk());
            ps.setInt(3, mk.getSks());
            ps.setInt(4, mk.getSemester());

            ps.executeUpdate();

            System.out.println("Data berhasil disimpan");

        }catch(Exception e){

            System.out.println(e);

        }

    }

    // ================= UBAH =================
    public void ubah(Matakuliah mk){

        try{

            String sql="UPDATE matakuliah SET nama_mk=?, sks=?, semester=? WHERE kode_mk=?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1, mk.getNamaMk());
            ps.setInt(2, mk.getSks());
            ps.setInt(3, mk.getSemester());
            ps.setString(4, mk.getKodeMk());

            ps.executeUpdate();

            System.out.println("Data berhasil diubah");

        }catch(Exception e){

            System.out.println(e);

        }

    }

    // ================= HAPUS =================
    public void hapus(String kodeMk){

        try{

            String sql="DELETE FROM matakuliah WHERE kode_mk=?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1, kodeMk);

            ps.executeUpdate();

            System.out.println("Data berhasil dihapus");

        }catch(Exception e){

            System.out.println(e);

        }

    }

    // ================= CARI =================
    public ResultSet cari(String keyword){

        try{

            String sql="SELECT * FROM matakuliah WHERE kode_mk LIKE ? OR nama_mk LIKE ?";

            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,"%"+keyword+"%");
            ps.setString(2,"%"+keyword+"%");

            return ps.executeQuery();

        }catch(Exception e){

            System.out.println(e);

            return null;

        }

    }

    // ================= TAMPIL DATA =================
    public ResultSet tampilData(){

        try{

            String sql="SELECT * FROM matakuliah";

            PreparedStatement ps=con.prepareStatement(sql);

            return ps.executeQuery();

        }catch(Exception e){

            System.out.println(e);

            return null;

        }

    }
}
