/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class RekapAbsensi {

    private String nim;
    private String nama;
    private int hadir;
    private int izin;
    private int sakit;
    private int alpha;
    private double persentase;

    public RekapAbsensi() {
    }

    public RekapAbsensi(String nim, String nama, int hadir, int izin, int sakit, int alpha, double persentase) {
        this.nim = nim;
        this.nama = nama;
        this.hadir = hadir;
        this.izin = izin;
        this.sakit = sakit;
        this.alpha = alpha;
        this.persentase = persentase;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHadir() {
        return hadir;
    }

    public void setHadir(int hadir) {
        this.hadir = hadir;
    }

    public int getIzin() {
        return izin;
    }

    public void setIzin(int izin) {
        this.izin = izin;
    }

    public int getSakit() {
        return sakit;
    }

    public void setSakit(int sakit) {
        this.sakit = sakit;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public double getPersentase() {
        return persentase;
    }

    public void setPersentase(double persentase) {
        this.persentase = persentase;
    }
}
