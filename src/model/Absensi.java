/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class Absensi {

    private int idAbsensi;
    private String nim;
    private int idJadwal;
    private Date tanggal;
    private int pertemuan;
    private String status;

    public Absensi() {
    }

    public Absensi(int idAbsensi, String nim, int idJadwal, Date tanggal, int pertemuan, String status) {
        this.idAbsensi = idAbsensi;
        this.nim = nim;
        this.idJadwal = idJadwal;
        this.tanggal = tanggal;
        this.pertemuan = pertemuan;
        this.status = status;
    }

    public int getIdAbsensi() {
        return idAbsensi;
    }

    public void setIdAbsensi(int idAbsensi) {
        this.idAbsensi = idAbsensi;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getPertemuan() {
        return pertemuan;
    }

    public void setPertemuan(int pertemuan) {
        this.pertemuan = pertemuan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
