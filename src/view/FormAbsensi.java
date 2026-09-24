/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

/**
 *
 * @author HYPE AMD
 */
import database.Koneksi;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;

public class FormAbsensi extends javax.swing.JPanel {

    // Variabel global untuk menampung id_jadwal yang dikirim dari Dashboard
    private final int idJadwalAktif;
    /**
     * Constructor baru yang menerima parameter data dari Dashboard
     * @param idJadwal
     * @param namaMK
     * @param namaDosen
     * @param kelas
     */
    
    public FormAbsensi(int idJadwal, String namaMK, String namaDosen, String kelas) {
        initComponents();

    // Kunci textfield agar tidak bisa diedit manual
        txtMatakuliah.setEditable(false);
        txtDosen.setEditable(false);
        txtKelas.setEditable(false);
        txtTanggal.setEditable(false);
        cbJadwal.setVisible(false);
        jLabel2.setVisible(false);

        // 1. Simpan id_jadwal ke variabel global
        this.idJadwalAktif = idJadwal;

        // 2. Set teks otomatis ke field sesuai data yang dikirim
        txtMatakuliah.setText(namaMK);
        txtDosen.setText(namaDosen);
        txtKelas.setText(kelas);
        txtTanggal.setText(java.time.LocalDate.now().toString()); // Otomatis tanggal hari ini
        txtPertemuan.setText(""); // Biarkan diisi nomor pertemuan oleh pengguna

        // 3. Jalankan set tabel dan panggil mahasiswa berdasarkan kelas perkuliahan
        setModelTabel();
        tampilDataMahasiswaPerKelas(kelas);   
        tampilComboStatus();     
    }
    
    private void tampilComboStatus() {
        String[] status = {"Hadir", "Izin", "Sakit", "Alpha"};
        JComboBox<String> combo = new JComboBox<>(status);
        tblAbsensi.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(combo));
    }
    
    private void setModelTabel() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"No", "NIM", "Nama Mahasiswa", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Hanya kolom Status yang bisa diedit
            }
        };
        tblAbsensi.setModel(model);
    }
    
    private void tampilDataMahasiswaPerKelas(String kelas) {
        DefaultTableModel model = (DefaultTableModel) tblAbsensi.getModel();
    model.setRowCount(0);

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT nim,nama FROM mahasiswa WHERE kelas=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, kelas);

        ResultSet rs = ps.executeQuery();

        int no = 1;

        while(rs.next()){

            model.addRow(new Object[]{
                no++,
                rs.getString("nim"),
                rs.getString("nama"),
                "Hadir"
            });

        }

    } catch(SQLException e){

        JOptionPane.showMessageDialog(this,e.getMessage());

    }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDosen = new javax.swing.JTextField();
        txtKelas = new javax.swing.JTextField();
        txtPertemuan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbJadwal = new javax.swing.JComboBox<>();
        txtMatakuliah = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAbsensi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnbatal = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel1.setText("Input Absensi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Jadwal");

        jLabel3.setText("Mata Kuliah");

        jLabel4.setText("Dosen");

        jLabel5.setText("Kelas");

        jLabel6.setText("Pertemuan");

        jLabel7.setText("Tanggal");

        cbJadwal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pemrograman Berorientasi Objek (TI-2A)", "Basis Data Lanjut (TI-2B)", "Struktur Data (TI-2A)" }));
        cbJadwal.addActionListener(this::cbJadwalActionPerformed);

        txtMatakuliah.addActionListener(this::txtMatakuliahActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbJadwal, 0, 1, Short.MAX_VALUE)
                    .addComponent(txtMatakuliah, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(txtTanggal))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDosen)
                    .addComponent(txtKelas)
                    .addComponent(txtPertemuan))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(txtDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(txtKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatakuliah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtPertemuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblAbsensi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nim", "Nama Mahasigma", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblAbsensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAbsensiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAbsensi);

        btnSimpan.setBackground(new java.awt.Color(0, 102, 153));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setText("SIMPAN ABSENSI");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        btnbatal.setBackground(new java.awt.Color(153, 153, 153));
        btnbatal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnbatal.setForeground(new java.awt.Color(255, 255, 255));
        btnbatal.setText("BATAL");
        btnbatal.addActionListener(this::btnbatalActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addComponent(btnbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnbatal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtMatakuliahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatakuliahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatakuliahActionPerformed

    private void tblAbsensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAbsensiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblAbsensiMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
if(idJadwalAktif == 0){
    JOptionPane.showMessageDialog(this,
            "Jadwal belum dipilih!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
    return;
}
if (txtPertemuan.getText().trim().isEmpty() || txtTanggal.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lengkapi nomor pertemuan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idJadwal = this.idJadwalAktif; // Mengambil id_jadwal global dari Constructor
        int pertemuan = Integer.parseInt(txtPertemuan.getText().trim());
        String tanggal = txtTanggal.getText().trim(); 

        DefaultTableModel model = (DefaultTableModel) tblAbsensi.getModel();
        int jumlahBaris = model.getRowCount();

        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "INSERT INTO absensi (nim, id_jadwal, tanggal, pertemuan, status) VALUES (?, ?, ?, ?, ?)";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < jumlahBaris; i++) {
                String nim = model.getValueAt(i, 1).toString();
                String status = model.getValueAt(i, 3).toString();

                ps.setString(1, nim);
                ps.setInt(2, idJadwal);
                ps.setString(3, tanggal);
                ps.setInt(4, pertemuan);
                ps.setString(5, status);
                
                ps.addBatch();
            }
            
            System.out.println("ID Jadwal = " + idJadwal);

            ps.executeBatch();
            JOptionPane.showMessageDialog(
        this,
        "Absensi berhasil disimpan!"
);

txtPertemuan.setText("");

// Muat ulang mahasiswa
setModelTabel();
tampilDataMahasiswaPerKelas(txtKelas.getText());
tampilComboStatus(); // Refresh status kembali ke 'Hadir'

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal Menyimpan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }     // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbatalActionPerformed
int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin membatalkan pengisian absensi? Semua perubahan di tabel akan di-reset.", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
                
        if (konfirmasi == JOptionPane.YES_OPTION) {
            txtPertemuan.setText("");
            // Memuat ulang data mahasiswa untuk mereset status absen kembali ke "Hadir"
            tampilDataMahasiswaPerKelas(txtKelas.getText());
            JOptionPane.showMessageDialog(this, "Pengisian absensi dibatalkan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnbatalActionPerformed

    private void cbJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJadwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbJadwalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnbatal;
    private javax.swing.JComboBox<String> cbJadwal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAbsensi;
    private javax.swing.JTextField txtDosen;
    private javax.swing.JTextField txtKelas;
    private javax.swing.JTextField txtMatakuliah;
    private javax.swing.JTextField txtPertemuan;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}