/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Color;
/**
 *
 * @author USER
 */
public class FormRekapAbsensi extends javax.swing.JPanel {

    /**
     * Creates new form FormRekapAbsensi
     */
    public FormRekapAbsensi() {
        initComponents();
        
        

        tblRekap.setSelectionBackground(new Color(52,152,219));
        tblRekap.setSelectionForeground(Color.WHITE);
        loadMahasiswa();
        loadMataKuliah();
        tampilkanRingkasan();
        tampilkanTabel();
        settingTabel();
    }
    private void hitungRekap() {

    if (cbMahasiswa.getSelectedIndex() <= 0) {
        lblTotalPertemuan.setText("0");
        lblHadir.setText("0");
        lblIzin.setText("0");
        lblSakit.setText("0");
        lblAlpha.setText("0");
        lblPersentase.setText("0%");
        return;
    }

    try {
        Connection conn = Koneksi.getKoneksi();

        String nim = cbMahasiswa.getSelectedItem().toString().split(" - ")[0];

        String sql =
            "SELECT COUNT(*) AS total, " +
            "SUM(CASE WHEN a.status='Hadir' THEN 1 ELSE 0 END) AS hadir, " +
            "SUM(CASE WHEN a.status='Izin' THEN 1 ELSE 0 END) AS izin, " +
            "SUM(CASE WHEN a.status='Sakit' THEN 1 ELSE 0 END) AS sakit, " +
            "SUM(CASE WHEN a.status='Alpha' THEN 1 ELSE 0 END) AS alpha " +
            "FROM absensi a " +
            "JOIN jadwal j ON a.id_jadwal=j.id_jadwal " +
            "JOIN matakuliah mk ON j.kode_mk=mk.kode_mk " +
            "WHERE a.nim=?";

        if (cbMataKuliah.getSelectedIndex() > 0) {
           sql += " AND mk.kode_mk=?";
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nim);

       if (cbMataKuliah.getSelectedIndex() > 0) {

    String kodeMK = cbMataKuliah.getSelectedItem()
            .toString().split(" - ")[0];

    ps.setString(2, kodeMK);
}

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            int total = rs.getInt("total");
            int hadir = rs.getInt("hadir");
            int izin = rs.getInt("izin");
            int sakit = rs.getInt("sakit");
            int alpha = rs.getInt("alpha");

            lblTotalPertemuan.setText(String.valueOf(total));
            lblHadir.setText(String.valueOf(hadir));
            lblIzin.setText(String.valueOf(izin));
            lblSakit.setText(String.valueOf(sakit));
            lblAlpha.setText(String.valueOf(alpha));
            double persen = 0;

            if (total != 0) {
                persen = (hadir * 100.0) / total;
            }

            lblPersentase.setText(String.format("%.0f%%", persen));
    }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
   private void tampilkanTabel() {

    DefaultTableModel model = (DefaultTableModel) tblRekap.getModel();
    model.setRowCount(0);

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql =
            "SELECT a.tanggal, m.nama, mk.nama_mk, a.pertemuan, a.status " +
            "FROM absensi a " +
            "JOIN mahasiswa m ON a.nim = m.nim " +
            "JOIN jadwal j ON a.id_jadwal = j.id_jadwal " +
            "JOIN matakuliah mk ON j.kode_mk = mk.kode_mk " +
            "WHERE 1=1 ";

        // Filter Mahasiswa
        if (cbMahasiswa.getSelectedIndex() > 0) {
            sql += "AND a.nim = ? ";
        }

        // Filter Mata Kuliah
        if (cbMataKuliah.getSelectedIndex() > 0) {
            sql += "AND mk.kode_mk = ? ";
        }

        sql += "ORDER BY a.tanggal DESC";

        PreparedStatement ps = conn.prepareStatement(sql);

        int index = 1;

        // Isi parameter Mahasiswa
        if (cbMahasiswa.getSelectedIndex() > 0) {
            String nim = cbMahasiswa.getSelectedItem()
                    .toString().split(" - ")[0];
            ps.setString(index++, nim);
        }

        // Isi parameter Mata Kuliah
        if (cbMataKuliah.getSelectedIndex() > 0) {
            String kodeMK = cbMataKuliah.getSelectedItem()
                    .toString().split(" - ")[0];
            ps.setString(index++, kodeMK);
        }

        ResultSet rs = ps.executeQuery();

        int no = 1;

        while (rs.next()) {

            model.addRow(new Object[]{
                no++,
                rs.getDate("tanggal"),
                rs.getString("nama"),
                rs.getString("nama_mk"),
                rs.getInt("pertemuan"),
                rs.getString("status")
            });

        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}
    private void tampilkanRingkasan() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql =
        "SELECT COUNT(*) total," +
        "SUM(status='Hadir') hadir," +
        "SUM(status='Izin') izin," +
        "SUM(status='Sakit') sakit," +
        "SUM(status='Alpha') alpha " +
        "FROM absensi";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            int total = rs.getInt("total");
            int hadir = rs.getInt("hadir");
            int izin = rs.getInt("izin");
            int sakit = rs.getInt("sakit");
            int alpha = rs.getInt("alpha");

            lblTotalPertemuan.setText(String.valueOf(total));
            lblHadir.setText(String.valueOf(hadir));
            lblIzin.setText(String.valueOf(izin));
            lblSakit.setText(String.valueOf(sakit));
            lblAlpha.setText(String.valueOf(alpha));

            double persen = 0;

            if(total != 0){
                persen = (hadir * 100.0) / total;
            }

            lblPersentase.setText(String.format("%.0f%%", persen));

        }

    } catch(Exception e){

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    private void loadMahasiswa() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT nim, nama FROM mahasiswa ORDER BY nama ASC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        cbMahasiswa.removeAllItems();
        cbMahasiswa.addItem("-- Semua Mahasiswa --");

        while (rs.next()) {

            cbMahasiswa.addItem(
                rs.getString("nim") + " - " + rs.getString("nama")
            );

        }

        rs.close();
        ps.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal memuat data mahasiswa\n" + e.getMessage());

    }

}
    private void loadMataKuliah() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT kode_mk, nama_mk FROM matakuliah ORDER BY nama_mk ASC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        cbMataKuliah.removeAllItems();
        cbMataKuliah.addItem("-- Semua Mata Kuliah --");

        while (rs.next()) {

            cbMataKuliah.addItem(
                rs.getString("kode_mk") + " - " + rs.getString("nama_mk")
            );

        }

        rs.close();
        ps.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal memuat data mata kuliah\n" + e.getMessage());

    }

}
    private void settingTabel() {

    tblRekap.setRowHeight(32);
    tblRekap.setFont(new Font("Segoe UI", Font.PLAIN, 13));

    tblRekap.getTableHeader().setFont(
            new Font("Segoe UI", Font.BOLD, 14));

    tblRekap.getTableHeader().setPreferredSize(
            new Dimension(100,38));

    tblRekap.getTableHeader().setReorderingAllowed(false);
    tblRekap.getTableHeader().setResizingAllowed(false);

    tblRekap.setSelectionBackground(new Color(52,152,219));
    tblRekap.setSelectionForeground(Color.WHITE);

    tblRekap.setGridColor(new Color(225,225,225));
    tblRekap.setShowHorizontalLines(true);
    tblRekap.setShowVerticalLines(false);

    tblRekap.setIntercellSpacing(new Dimension(0,1));

    tblRekap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    tblRekap.getColumnModel().getColumn(0).setPreferredWidth(50);
    tblRekap.getColumnModel().getColumn(1).setPreferredWidth(110);
    tblRekap.getColumnModel().getColumn(2).setPreferredWidth(200);
    tblRekap.getColumnModel().getColumn(3).setPreferredWidth(250);
    tblRekap.getColumnModel().getColumn(4).setPreferredWidth(90);
    tblRekap.getColumnModel().getColumn(5).setPreferredWidth(90);

    jScrollPane1.setViewportView(tblRekap);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblMahasiswa = new javax.swing.JLabel();
        cbMahasiswa = new javax.swing.JComboBox<>();
        lblMataKuliah = new javax.swing.JLabel();
        cbMataKuliah = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTotalPertemuan1 = new javax.swing.JLabel();
        lblTotalPertemuan = new javax.swing.JLabel();
        lblHadir1 = new javax.swing.JLabel();
        lblHadir = new javax.swing.JLabel();
        lblIzin1 = new javax.swing.JLabel();
        lblIzin = new javax.swing.JLabel();
        lblSakit1 = new javax.swing.JLabel();
        lblSakit = new javax.swing.JLabel();
        lblAlpha1 = new javax.swing.JLabel();
        lblAlpha = new javax.swing.JLabel();
        lblPersentase1 = new javax.swing.JLabel();
        lblPersentase = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRekap = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel1.setText("Rekap Absensi Mahasiswa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblMahasiswa.setText("Mahasiswa");

        cbMahasiswa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMahasiswa.addActionListener(this::cbMahasiswaActionPerformed);

        lblMataKuliah.setText("Mata Kuliah");

        cbMataKuliah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMataKuliah.addActionListener(this::cbMataKuliahActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMahasiswa)
                .addGap(42, 42, 42)
                .addComponent(cbMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(lblMataKuliah)
                .addGap(56, 56, 56)
                .addComponent(cbMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMahasiswa)
                    .addComponent(cbMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMataKuliah)
                    .addComponent(cbMataKuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTotalPertemuan1.setBackground(new java.awt.Color(0, 0, 0));
        lblTotalPertemuan1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblTotalPertemuan1.setText("Total Pertemuan");

        lblTotalPertemuan.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalPertemuan.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblTotalPertemuan.setForeground(new java.awt.Color(0, 102, 153));
        lblTotalPertemuan.setText("0");

        lblHadir1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblHadir1.setText("Hadir");

        lblHadir.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblHadir.setForeground(new java.awt.Color(0, 102, 153));
        lblHadir.setText("0");

        lblIzin1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblIzin1.setText("Izin");

        lblIzin.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblIzin.setForeground(new java.awt.Color(0, 102, 153));
        lblIzin.setText("0");

        lblSakit1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblSakit1.setText("Sakit");

        lblSakit.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblSakit.setForeground(new java.awt.Color(0, 102, 153));
        lblSakit.setText("0");

        lblAlpha1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblAlpha1.setText("Alpha");

        lblAlpha.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblAlpha.setForeground(new java.awt.Color(0, 102, 153));
        lblAlpha.setText("0");

        lblPersentase1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblPersentase1.setText("Persentase Kehadiran");

        lblPersentase.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblPersentase.setForeground(new java.awt.Color(0, 102, 153));
        lblPersentase.setText("0");
        lblPersentase.setPreferredSize(new java.awt.Dimension(70, 16));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalPertemuan1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHadir1)
                    .addComponent(lblIzin1)
                    .addComponent(lblSakit1)
                    .addComponent(lblAlpha1)
                    .addComponent(lblPersentase1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalPertemuan, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSakit, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIzin, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHadir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPersentase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalPertemuan1)
                    .addComponent(lblTotalPertemuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHadir1)
                    .addComponent(lblHadir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIzin1)
                    .addComponent(lblIzin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSakit1)
                    .addComponent(lblSakit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlpha1)
                    .addComponent(lblAlpha))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPersentase1)
                    .addComponent(lblPersentase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        tblRekap.setBackground(new java.awt.Color(0, 102, 153));
        tblRekap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblRekap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Tanggal", "Nama Mahasiswa", "Mata Kuliah", "Pertemuan Ke", "Status"
            }
        ));
        jScrollPane1.setViewportView(tblRekap);

        jScrollPane2.setViewportView(jScrollPane1);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("RINGKASAN KEHADIRAN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMahasiswaActionPerformed
       tampilkanTabel();
       hitungRekap();

    }//GEN-LAST:event_cbMahasiswaActionPerformed

    private void cbMataKuliahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMataKuliahActionPerformed
        tampilkanTabel();
        hitungRekap();
    }//GEN-LAST:event_cbMataKuliahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbMahasiswa;
    private javax.swing.JComboBox<String> cbMataKuliah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAlpha;
    private javax.swing.JLabel lblAlpha1;
    private javax.swing.JLabel lblHadir;
    private javax.swing.JLabel lblHadir1;
    private javax.swing.JLabel lblIzin;
    private javax.swing.JLabel lblIzin1;
    private javax.swing.JLabel lblMahasiswa;
    private javax.swing.JLabel lblMataKuliah;
    private javax.swing.JLabel lblPersentase;
    private javax.swing.JLabel lblPersentase1;
    private javax.swing.JLabel lblSakit;
    private javax.swing.JLabel lblSakit1;
    private javax.swing.JLabel lblTotalPertemuan;
    private javax.swing.JLabel lblTotalPertemuan1;
    private javax.swing.JTable tblRekap;
    // End of variables declaration//GEN-END:variables
}
   
