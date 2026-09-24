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
import java.util.Date;
import java.text.MessageFormat;
import javax.swing.JTable;
import java.awt.*;
import java.awt.print.*;
/**
 *
 * @author USER
 */
public class FormLaporanAbsensi extends javax.swing.JPanel {

    /**
     * Creates new form FormLaporanAbsensi
     */
    public FormLaporanAbsensi() {
    initComponents();

    isiComboJenisLaporan();
    isiComboMatkul();
    isiComboMahasiswa();
}
    
    private void isiComboJenisLaporan(){

    cmbJenisLaporan.removeAllItems();

    cmbJenisLaporan.addItem("Rekap Per Mahasiswa");
    cmbJenisLaporan.addItem("Rekap Per Mata Kuliah");
    cmbJenisLaporan.addItem("Rekap Per Kelas");
    cmbJenisLaporan.addItem("Semua Mahasiswa");

}
    private void isiComboMatkul() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String nim = "";

        if (cmbMahasiswa.getSelectedItem() != null) {

            String item = cmbMahasiswa.getSelectedItem().toString();

            if (item.contains("(") && item.contains(")")) {
                nim = item.substring(
                        item.indexOf("(") + 1,
                        item.indexOf(")")
                );
            } else {
                return;
            }
        }

        String sql =
        "SELECT DISTINCT mk.nama_mk " +
        "FROM absensi a " +
        "JOIN jadwal j ON a.id_jadwal=j.id_jadwal " +
        "JOIN matakuliah mk ON j.kode_mk=mk.kode_mk " +
        "WHERE a.nim=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, nim);

        ResultSet rs = pst.executeQuery();

        cmbMatkul.removeAllItems();

        while (rs.next()) {

            cmbMatkul.addItem(rs.getString("nama_mk"));

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    private void isiComboMahasiswa(){

    try{

        Connection conn=Koneksi.getKoneksi();

        String sql="SELECT nim,nama FROM mahasiswa ORDER BY nama";

        PreparedStatement ps=conn.prepareStatement(sql);

        ResultSet rs=ps.executeQuery();

        cmbMahasiswa.removeAllItems();

        while(rs.next()){

            cmbMahasiswa.addItem(

                    rs.getString("nama")
                    +" ("
                    +rs.getString("nim")
                    +")"

            );

        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(null,e.getMessage());

    }

}
    public void tampilIdentitas() {

    try {
        Connection conn = Koneksi.getKoneksi();
System.out.println(cmbMahasiswa.getSelectedItem());
        String nim = "";

        if (cmbMahasiswa.getSelectedItem() != null) {
            String item = cmbMahasiswa.getSelectedItem().toString();

            if (item.contains("(") && item.contains(")")) {
            nim = item.substring(
                    item.indexOf("(") + 1,
                    item.indexOf(")")
            );
        } else {
            JOptionPane.showMessageDialog(this, "Format data mahasiswa tidak valid.");
            return;
        }
        }

       String sql =
        "SELECT m.nama, m.nim, m.kelas, " +
        "mk.nama_mk, d.nama AS nama_dosen " +
        "FROM mahasiswa m " +
        "JOIN absensi a ON m.nim = a.nim " +
        "JOIN jadwal j ON a.id_jadwal = j.id_jadwal " +
        "JOIN matakuliah mk ON j.kode_mk = mk.kode_mk " +
        "JOIN dosen d ON j.nidn = d.nidn " +
        "WHERE m.nim = ? " +
        "AND mk.nama_mk = ?";
        
        String matkul = cmbMatkul.getSelectedItem().toString();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, nim);
        pst.setString(2, cmbMatkul.getSelectedItem().toString());
        ResultSet rs = pst.executeQuery();

        if(rs.next()){

        lblNama.setText(rs.getString("nama"));
        lblNim.setText(rs.getString("nim"));
        lblKelas.setText(rs.getString("kelas"));
        lblMatkul.setText(rs.getString("nama_mk"));
        lblDosen.setText(rs.getString("nama_dosen"));

}

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
private void tampilData() {

    try{

        Connection conn = Koneksi.getKoneksi();

        String sql =
        "SELECT COUNT(*) AS total_pertemuan, " +
        "SUM(CASE WHEN status='Hadir' THEN 1 ELSE 0 END) AS hadir, " +
        "SUM(CASE WHEN status='Izin' THEN 1 ELSE 0 END) AS izin, " +
        "SUM(CASE WHEN status='Sakit' THEN 1 ELSE 0 END) AS sakit, " +
        "SUM(CASE WHEN status='Alpha' THEN 1 ELSE 0 END) AS alpha " +
        "FROM absensi a " +
        "JOIN jadwal j ON a.id_jadwal = j.id_jadwal " +
        "JOIN matakuliah mk ON j.kode_mk = mk.kode_mk " +
        "WHERE a.nim = ? " +
        "AND mk.nama_mk = ?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, lblNim.getText());
        pst.setString(2, cmbMatkul.getSelectedItem().toString());

        ResultSet rs = pst.executeQuery();
        DefaultTableModel model =
        (DefaultTableModel)tblLaporan.getModel();

        model.setRowCount(0);

        if(rs.next()){

            int total=rs.getInt("total_pertemuan");
            int hadir=rs.getInt("hadir");
            int izin=rs.getInt("izin");
            int sakit=rs.getInt("sakit");
            int alpha=rs.getInt("alpha");

            double persen=0;

            if(total!=0){

                persen=((double)hadir/total)*100;

            }

            model.addRow(new Object[]{

                total,
                hadir,
                izin,
                sakit,
                alpha,
                String.format("%.2f%%",persen)

            });

        }

    }catch(Exception e){

        JOptionPane.showMessageDialog(null,e);

    }

}
    private void cetakLaporan() {

    PrinterJob job = PrinterJob.getPrinterJob();

    job.setJobName("Laporan Rekap Absensi");

    job.setPrintable(new Printable() {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2 = (Graphics2D) graphics;

            g2.translate(pageFormat.getImageableX(),
                         pageFormat.getImageableY());

            double scaleX = pageFormat.getImageableWidth() / panelLaporan.getWidth();
            double scaleY = pageFormat.getImageableHeight() / panelLaporan.getHeight();

            double scale = Math.min(scaleX, scaleY);

            g2.scale(scale, scale);

            panelLaporan.printAll(g2);

            return PAGE_EXISTS;
        }
    });

    try {

        job.print();

    } catch (PrinterException e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cmbJenisLaporan = new javax.swing.JComboBox<>();
        cmbMatkul = new javax.swing.JComboBox<>();
        cmbMahasiswa = new javax.swing.JComboBox<>();
        btnTampilkan = new javax.swing.JButton();
        btnCetakLaporan = new javax.swing.JButton();
        panelLaporan = new javax.swing.JPanel();
        lblNama = new javax.swing.JTextField();
        lblNim = new javax.swing.JTextField();
        lblMatkul = new javax.swing.JTextField();
        lblDosen = new javax.swing.JTextField();
        lblKelas = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        lblNama2 = new javax.swing.JLabel();
        lblNim2 = new javax.swing.JLabel();
        lblMatkul2 = new javax.swing.JLabel();
        lblDosen2 = new javax.swing.JLabel();
        lblKelas2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel4.setText("laporan Absensi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(942, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cmbJenisLaporan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rekap Per Mahasiswa", "Rekap Per Mata Kuliah", "Rekap Per Kelas", "Rekap Semua Mahasiswa" }));

        cmbMatkul.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMatkul.addActionListener(this::cmbMatkulActionPerformed);

        cmbMahasiswa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMahasiswa.addActionListener(this::cmbMahasiswaActionPerformed);

        btnTampilkan.setBackground(new java.awt.Color(0, 102, 153));
        btnTampilkan.setForeground(new java.awt.Color(255, 255, 255));
        btnTampilkan.setText("Tampilkan");
        btnTampilkan.addActionListener(this::btnTampilkanActionPerformed);

        btnCetakLaporan.setBackground(new java.awt.Color(0, 153, 0));
        btnCetakLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakLaporan.setText("Cetak");
        btnCetakLaporan.addActionListener(this::btnCetakLaporanActionPerformed);

        panelLaporan.setBackground(new java.awt.Color(255, 255, 255));
        panelLaporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblNim.addActionListener(this::lblNimActionPerformed);

        tblLaporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Total Pertemuan", "Hadir", "Izin", "Sakit", "Alpha", "Persentase"
            }
        ));
        jScrollPane1.setViewportView(tblLaporan);

        jScrollPane2.setViewportView(jScrollPane1);

        lblNama2.setText("Nama Mahasiswa");

        lblNim2.setText("NIM");

        lblMatkul2.setText("Mata Kuliah");

        lblDosen2.setText("Dosen");

        lblKelas2.setText("Kelas");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("LAPORAN REKAP ABSENSI MAHASISWA");

        javax.swing.GroupLayout panelLaporanLayout = new javax.swing.GroupLayout(panelLaporan);
        panelLaporan.setLayout(panelLaporanLayout);
        panelLaporanLayout.setHorizontalGroup(
            panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
            .addGroup(panelLaporanLayout.createSequentialGroup()
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLaporanLayout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNama2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(lblNim2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMatkul2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDosen2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblKelas2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDosen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(lblKelas, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMatkul)
                            .addComponent(lblNim)
                            .addComponent(lblNama)))
                    .addGroup(panelLaporanLayout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLaporanLayout.setVerticalGroup(
            panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLaporanLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNama2))
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLaporanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMatkul2)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLaporanLayout.createSequentialGroup()
                                .addComponent(lblNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLaporanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNim2)
                        .addGap(34, 34, 34)))
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDosen2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLaporanLayout.createSequentialGroup()
                        .addComponent(lblDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKelas2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );

        jLabel1.setText("Jenis Laporan");

        jLabel2.setText("Mata Kuliah");

        jLabel3.setText("Mahasiswa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbJenisLaporan, 0, 124, Short.MAX_VALUE)
                    .addComponent(cmbMatkul, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(129, 129, 129)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTampilkan)
                        .addGap(18, 18, 18)
                        .addComponent(btnCetakLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbMahasiswa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbJenisLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbMahasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTampilkan)
                            .addComponent(btnCetakLaporan)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        tampilIdentitas();
        tampilData();
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void btnCetakLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakLaporanActionPerformed
    cetakLaporan();
    }//GEN-LAST:event_btnCetakLaporanActionPerformed

    private void cmbMatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMatkulActionPerformed
        
    }//GEN-LAST:event_cmbMatkulActionPerformed

    private void cmbMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMahasiswaActionPerformed
         isiComboMatkul();
    }//GEN-LAST:event_cmbMahasiswaActionPerformed

    private void lblNimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblNimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetakLaporan;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JComboBox<String> cmbJenisLaporan;
    private javax.swing.JComboBox<String> cmbMahasiswa;
    private javax.swing.JComboBox<String> cmbMatkul;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lblDosen;
    private javax.swing.JLabel lblDosen2;
    private javax.swing.JTextField lblKelas;
    private javax.swing.JLabel lblKelas2;
    private javax.swing.JTextField lblMatkul;
    private javax.swing.JLabel lblMatkul2;
    private javax.swing.JTextField lblNama;
    private javax.swing.JLabel lblNama2;
    private javax.swing.JTextField lblNim;
    private javax.swing.JLabel lblNim2;
    private javax.swing.JPanel panelLaporan;
    private javax.swing.JTable tblLaporan;
    // End of variables declaration//GEN-END:variables
}
