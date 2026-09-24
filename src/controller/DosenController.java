/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Dosen;
/**
 *
 * @author HYPE AMD
 */
public class DosenController {
    Connection con = Koneksi.getKoneksi();

    // ================= SIMPAN =================
    public void simpan(Dosen d){

        try{

            String sql = "INSERT INTO dosen VALUES(?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, d.getNidn());
            ps.setString(2, d.getNama());
            ps.setString(3, d.getProdi());
            ps.setString(4, d.getNoHp());
            ps.setString(5, d.getEmail());

            ps.executeUpdate();

            System.out.println("Data dosen berhasil disimpan");

        }catch(Exception e){

            System.out.println(e);

        }

    }

    // ================= UBAH =================
    public void ubah(Dosen d){

        try{

            String sql = "UPDATE dosen SET nama=?, prodi=?, no_hp=?, email=? WHERE nidn=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, d.getNama());
            ps.setString(2, d.getProdi());
            ps.setString(3, d.getNoHp());
            ps.setString(4, d.getEmail());
            ps.setString(5, d.getNidn());

            ps.executeUpdate();

            System.out.println("Data dosen berhasil diubah");

        }catch(Exception e){

            System.out.println(e);

        }

    }

    // ================= HAPUS =================
    public void hapus(String nidn){

        try{

            String sql = "DELETE FROM dosen WHERE nidn=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nidn);

            ps.executeUpdate();

            System.out.println("Data dosen berhasil dihapus");

        }catch(Exception e){

            System.out.println(e);

        }

    }

    // ================= CARI =================
    public ResultSet cari(String keyword){

        try{

            String sql = "SELECT * FROM dosen WHERE nidn LIKE ? OR nama LIKE ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            return ps.executeQuery();

        }catch(Exception e){

            System.out.println(e);

            return null;

        }

    }

    // ================= TAMPIL DATA =================
    public ResultSet tampilData(){

        try{

            String sql = "SELECT * FROM dosen";

            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        }catch(Exception e){

            System.out.println(e);

            return null;

        }

    }
}
