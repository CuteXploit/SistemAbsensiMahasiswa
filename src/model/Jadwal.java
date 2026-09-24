/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HYPE AMD
 */
public class Jadwal {
    private int idJadwal;
    private String kodeMk;
    private String nidn;
    private String kelas;
    private String hari;
    private String jam;
    private String ruangan;

    public Jadwal() {
    }

    public Jadwal(int idJadwal, String kodeMk, String nidn, String kelas, String hari, String jam, String ruangan) {
        this.idJadwal = idJadwal;
        this.kodeMk = kodeMk;
        this.nidn = nidn;
        this.kelas = kelas;
        this.hari = hari;
        this.jam = jam;
        this.ruangan = ruangan;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getKodeMk() {
        return kodeMk;
    }

    public void setKodeMk(String kodeMk) {
        this.kodeMk = kodeMk;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }
}
