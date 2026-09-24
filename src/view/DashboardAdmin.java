/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import java.awt.BorderLayout;
import java.awt.Color;
import database.Koneksi;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.Dimension;
import org.jfree.chart.plot.PiePlot;
import view.FormRekapAbsensi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author HYPE AMD
 */
public class DashboardAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardAdmin.class.getName());

    /**
     * Creates new form DashboardAdmin
     */
    public DashboardAdmin() {
        initComponents();
    panelHijau.setBackground(new Color(46, 204, 113));   // Hijau
    panelBiru.setBackground(new Color(52, 152, 219));    // Biru
    panelKuning.setBackground(new Color(241, 196, 15));  // Kuning
    panelMerah.setBackground(new Color(231, 76, 60));    // Merah
    
    tblJadwal.setFillsViewportHeight(true);
    tblJadwal.setRowHeight(30);
    tblJadwal.getTableHeader().setReorderingAllowed(false);
    tblJadwal.setShowGrid(false);
    tblJadwal.setIntercellSpacing(new java.awt.Dimension(0, 0));
    
    //mengganti bacground tiap panel
    jPanel9.setBackground(Color.WHITE);
    lbMhs.setBackground(Color.WHITE);
    jPanel4.setBackground(Color.WHITE);
    jPanel5.setBackground(Color.WHITE);
    jPanel6.setBackground(Color.WHITE);
    jPanel8.setBackground(Color.WHITE);
    
    try {
        tampilJumlahMahasiswa();
        tampilJumlahDosen();
        tampilJumlahMatkul();
        tampilJumlahJadwal();
        tampilPieChart();
        tampilJadwalHariIni();
    } catch (Exception e) {
       
          
    }}
    
    public void tampilPieChart() {

    int hadir = 2;
    int izin = 3;
    int sakit = 5;
    int alpha = 5;

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql =
        "SELECT status, COUNT(*) jumlah " +
        "FROM absensi " +
        "WHERE tanggal = CURDATE() " +
        "GROUP BY status";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String status = rs.getString("status");
            int jumlah = rs.getInt("jumlah");

            if(status.equalsIgnoreCase("Hadir")){
                hadir = jumlah;
            }else if(status.equalsIgnoreCase("Izin")){
                izin = jumlah;
            }else if(status.equalsIgnoreCase("Sakit")){
                sakit = jumlah;
            }else if(status.equalsIgnoreCase("Alpha")){
                alpha = jumlah;
            }

        }

        rs.close();
        ps.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }

    int total = hadir + izin + sakit + alpha;

    lblHadir.setText("Hadir : " + hadir);
    lblIzin.setText("Izin : " + izin);
    lblSakit.setText("Sakit : " + sakit);
    lblAlpha.setText("Alpha : " + alpha);
    lblTotal.setText("Total : " + total);

    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
    dataset.setValue("Hadir", hadir);
    dataset.setValue("Izin", izin);
    dataset.setValue("Sakit", sakit);
    dataset.setValue("Alpha", alpha);

    JFreeChart chart = ChartFactory.createRingChart(
            null,
            dataset,
            false,
            true,
            false);

    RingPlot plot = (RingPlot) chart.getPlot();

    plot.setSectionPaint("Hadir", new Color(46,204,113));
    plot.setSectionPaint("Izin", new Color(241,196,15));
    plot.setSectionPaint("Sakit", new Color(52,152,219));
    plot.setSectionPaint("Alpha", new Color(231,76,60));

    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlinePaint(null);
    plot.setShadowPaint(null);
    plot.setLabelGenerator(null);
    plot.setSectionDepth(0.35);

    ChartPanel cp = new ChartPanel(chart);
    cp.setMouseZoomable(false);
    cp.setPopupMenu(null);

    panelChart.removeAll();
    panelChart.setLayout(new BorderLayout());
    panelChart.add(cp, BorderLayout.CENTER);
    panelChart.revalidate();
    panelChart.repaint();
}
    //hak akseb login dosen
    public void loginDosen(){

    btnMahasiswa.setVisible(false);
    btnDosen.setVisible(false);
    btnMatkul.setVisible(false);

    btnJadwal.setVisible(true);
    btnAbsensi.setVisible(true);
    btnLaporan.setVisible(false);

}
    public void loginMahasiswa(){

    // Menu yang boleh diakses mahasiswa
    butondashboard.setVisible(true);
    btnLaporan.setVisible(true);

    // Menu yang tidak boleh diakses
    btnMahasiswa.setVisible(false);
    btnDosen.setVisible(false);
    btnMatkul.setVisible(false);
    btnJadwal.setVisible(false);
    btnAbsensi.setVisible(false);
}
    //hak akses login admin
    public void loginAdmin(){

    btnMahasiswa.setVisible(true);
    btnDosen.setVisible(true);
    btnMatkul.setVisible(true);
    btnJadwal.setVisible(true);
    btnAbsensi.setVisible(true);
    btnLaporan.setVisible(true);

}
    //Jumlah dosen
    public void tampilJumlahDosen() {

    try {

        Connection conn = Koneksi.getKoneksi();

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(
                "SELECT COUNT(*) AS jumlah FROM dosen");

        if (rs.next()) {

            lblDosen.setText(rs.getString("jumlah"));

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    //Jumlah matkul
    public void tampilJumlahMatkul() {

    try {

        Connection conn = Koneksi.getKoneksi();

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(
                "SELECT COUNT(*) AS jumlah FROM matakuliah");

        if (rs.next()) {

            lblMatkul.setText(rs.getString("jumlah"));

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    //jumlah jadwal 
    public void tampilJumlahJadwal() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String hari = java.time.LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(
                        java.time.format.TextStyle.FULL,
                        new java.util.Locale("id", "ID"));

        PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS jumlah FROM jadwal WHERE hari=?");

        ps.setString(1, hari);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblJadwal.setText(rs.getString("jumlah"));

        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    private void cetakLaporan() {

    try {

        Connection conn = Koneksi.getKoneksi();

        JasperReport report = (JasperReport) JRLoader.loadObject(
                getClass().getResource("/report/LaporanAbsensi.jasper"));

        JasperPrint print = JasperFillManager.fillReport(report, null, conn);

        JasperViewer.viewReport(print, false);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this, e.getMessage());

    }

}
    //tampil jadwal hari ini
    public void tampilJadwalHariIni() {

    DefaultTableModel model = (DefaultTableModel) tblJadwal.getModel();
    model.setRowCount(0);

    try {

        String hari = LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("id", "ID"));

        Connection conn = Koneksi.getKoneksi();

      String sql =
    "SELECT j.id_jadwal, m.nama_mk, d.nama, j.kelas, j.jam, j.ruangan " +
    "FROM jadwal j " +
    "JOIN matakuliah m ON j.kode_mk = m.kode_mk " +
    "JOIN dosen d ON j.nidn = d.nidn " +
    "WHERE j.hari = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, hari);

        ResultSet rs = ps.executeQuery();

        int no = 1;

        while (rs.next()) {

            model.addRow(new Object[]{
            no++,
            rs.getString("nama_mk"),
            rs.getString("nama"),
            rs.getString("kelas"),
            rs.getString("jam"),
            rs.getString("ruangan")
        });
            

        }

        rs.close();
        ps.close();

        // Jika tidak ada jadwal hari ini
        if (model.getRowCount() == 0) {

            JOptionPane.showMessageDialog(this,
                    "Tidak ada jadwal hari ini.\nMenampilkan seluruh jadwal.");

            String sql2 =
            "SELECT j.id_jadwal, m.nama_mk, d.nama, j.kelas, j.jam, j.ruangan " +
            "FROM jadwal j " +
            "JOIN matakuliah m ON j.kode_mk = m.kode_mk " +
            "JOIN dosen d ON j.nidn = d.nidn " +
            "ORDER BY FIELD(j.hari,'Senin','Selasa','Rabu','Kamis','Jumat','Sabtu','Minggu'), j.jam";

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();

            no = 1;

            while (rs2.next()) {

                model.addRow(new Object[]{
                    no++,
                    rs2.getString("nama_mk"),
                    rs2.getString("nama"),
                    rs2.getString("kelas"),
                    rs2.getString("jam"),
                    rs2.getString("ruangan")
                });

            }

            rs2.close();
            ps2.close();

        }
        // Mengatur tinggi baris
        tblJadwal.setRowHeight(28);

        // Mengatur tinggi header
        tblJadwal.getTableHeader().setPreferredSize(
                new java.awt.Dimension(0, 30));

        // Mengatur lebar masing-masing kolom
        tblJadwal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        tblJadwal.getColumnModel().getColumn(0).setPreferredWidth(40);   // No
        tblJadwal.getColumnModel().getColumn(1).setPreferredWidth(180);  // Mata Kuliah
        tblJadwal.getColumnModel().getColumn(2).setPreferredWidth(160);  // Dosen
        tblJadwal.getColumnModel().getColumn(3).setPreferredWidth(70);   // Kelas
        tblJadwal.getColumnModel().getColumn(4).setPreferredWidth(120);  // Jam
        tblJadwal.getColumnModel().getColumn(5).setPreferredWidth(80);   // Ruang

    } catch (Exception e) {

        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Gagal menampilkan jadwal.\n" + e.getMessage());

    }

    }
    
public void tampilJumlahMahasiswa() {

    try {

        Connection conn = Koneksi.getKoneksi();

        String sql = "SELECT COUNT(*) AS jumlah FROM mahasiswa";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            lblMhs.setText(rs.getString("jumlah"));
        }

        rs.close();
        ps.close();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(this,
                "Gagal menampilkan jumlah mahasiswa!\n" + e.getMessage());

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
        Dashboard = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        butondashboard = new javax.swing.JButton();
        btnMahasiswa = new javax.swing.JButton();
        btnDosen = new javax.swing.JButton();
        btnMatkul = new javax.swing.JButton();
        btnJadwal = new javax.swing.JButton();
        btnAbsensi = new javax.swing.JButton();
        butonrekap = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        pnlKonten = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblDosen = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblMatkul = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblJadwal = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbMhs = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblMhs = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblJadwal = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        panelKeterangan = new javax.swing.JPanel();
        lblHadir = new javax.swing.JLabel();
        panelHijau = new javax.swing.JPanel();
        lblIzin = new javax.swing.JLabel();
        lblSakit = new javax.swing.JLabel();
        lblAlpha = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        panelBiru = new javax.swing.JPanel();
        panelKuning = new javax.swing.JPanel();
        panelMerah = new javax.swing.JPanel();
        panelChart = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Dashboard.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Dashboard.setText("Dashboard - Sistem Absensi Mahasiswa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Dashboard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Dashboard)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SISTEM ABSENSI");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MAHASISWA");

        butondashboard.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        butondashboard.setForeground(new java.awt.Color(0, 0, 102));
        butondashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home-button.png"))); // NOI18N
        butondashboard.setText("       Dashboard");
        butondashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        butondashboard.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        butondashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                butondashboardMousePressed(evt);
            }
        });
        butondashboard.addActionListener(this::butondashboardActionPerformed);

        btnMahasiswa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMahasiswa.setForeground(new java.awt.Color(0, 0, 102));
        btnMahasiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/graduation-hat.png"))); // NOI18N
        btnMahasiswa.setText("       Mahasiswa");
        btnMahasiswa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMahasiswa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMahasiswaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnMahasiswaMousePressed(evt);
            }
        });
        btnMahasiswa.addActionListener(this::btnMahasiswaActionPerformed);

        btnDosen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDosen.setForeground(new java.awt.Color(0, 0, 102));
        btnDosen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/teacher (1).png"))); // NOI18N
        btnDosen.setText("          Dosen");
        btnDosen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDosen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDosen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDosenMousePressed(evt);
            }
        });
        btnDosen.addActionListener(this::btnDosenActionPerformed);

        btnMatkul.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMatkul.setForeground(new java.awt.Color(0, 0, 102));
        btnMatkul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/open-book.png"))); // NOI18N
        btnMatkul.setText("      Mata Kuliah");
        btnMatkul.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMatkul.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMatkul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnMatkulMousePressed(evt);
            }
        });
        btnMatkul.addActionListener(this::btnMatkulActionPerformed);

        btnJadwal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnJadwal.setForeground(new java.awt.Color(0, 0, 102));
        btnJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar (1).png"))); // NOI18N
        btnJadwal.setText("          Jadwal");
        btnJadwal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnJadwal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnJadwalMousePressed(evt);
            }
        });
        btnJadwal.addActionListener(this::btnJadwalActionPerformed);

        btnAbsensi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAbsensi.setForeground(new java.awt.Color(0, 0, 102));
        btnAbsensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clipboard-list-check.png"))); // NOI18N
        btnAbsensi.setText("         Absensi");
        btnAbsensi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAbsensi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAbsensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAbsensiMousePressed(evt);
            }
        });
        btnAbsensi.addActionListener(this::btnAbsensiActionPerformed);

        butonrekap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        butonrekap.setForeground(new java.awt.Color(0, 0, 102));
        butonrekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/folder.png"))); // NOI18N
        butonrekap.setText("   Rekap Absensi");
        butonrekap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        butonrekap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        butonrekap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                butonrekapMousePressed(evt);
            }
        });
        butonrekap.addActionListener(this::butonrekapActionPerformed);

        btnLaporan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLaporan.setForeground(new java.awt.Color(0, 0, 102));
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/registered-document.png"))); // NOI18N
        btnLaporan.setText("        Laporan");
        btnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLaporan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLaporanMousePressed(evt);
            }
        });
        btnLaporan.addActionListener(this::btnLaporanActionPerformed);

        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(0, 0, 102));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/log-out.png"))); // NOI18N
        btnLogout.setText("         Logout");
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLogoutMousePressed(evt);
            }
        });
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo3.png"))); // NOI18N
        jLabel22.setText("jLabel22");
        jLabel22.setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDosen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMahasiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(butondashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnJadwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAbsensi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(butonrekap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLaporan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(btnMatkul, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6))
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(butondashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMahasiswa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDosen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMatkul)
                .addGap(12, 12, 12)
                .addComponent(btnJadwal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAbsensi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(butonrekap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLaporan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogout)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlKonten.setBackground(new java.awt.Color(217, 230, 236));

        jLabel7.setText("Jumlah Dosen");

        lblDosen.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblDosen.setForeground(new java.awt.Color(37, 155, 248));
        lblDosen.setText("jml");
        lblDosen.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/teacher.png"))); // NOI18N
        jLabel11.setText("jLabel11");
        jLabel11.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel13.setText("Orang");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(lblDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(33, 33, 33))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText("Mata Kuliah");

        lblMatkul.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblMatkul.setForeground(new java.awt.Color(26, 172, 114));
        lblMatkul.setText("jmlh");
        lblMatkul.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/book.png"))); // NOI18N
        jLabel10.setText("jLabel10");
        jLabel10.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel16.setText("Mata Kuliah");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(lblMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(45, 45, 45))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblMatkul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel9.setText("Jadwal Hari Ini");

        lblJadwal.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblJadwal.setForeground(new java.awt.Color(15, 65, 154));
        lblJadwal.setText("jml");
        lblJadwal.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar.png"))); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel18.setText("Jadwal");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(lblJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(39, 39, 39))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJadwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Jumlah Mahasiswa");

        lblMhs.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblMhs.setForeground(new java.awt.Color(5, 58, 129));
        lblMhs.setText("jml");
        lblMhs.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel12.setForeground(new java.awt.Color(153, 204, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/student.png"))); // NOI18N
        jLabel12.setText("jLabel12");
        jLabel12.setPreferredSize(new java.awt.Dimension(40, 40));

        jLabel3.setText("Orang");

        javax.swing.GroupLayout lbMhsLayout = new javax.swing.GroupLayout(lbMhs);
        lbMhs.setLayout(lbMhsLayout);
        lbMhsLayout.setHorizontalGroup(
            lbMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbMhsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(lbMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lbMhsLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(lbMhsLayout.createSequentialGroup()
                        .addGroup(lbMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        lbMhsLayout.setVerticalGroup(
            lbMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lbMhsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(lbMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Jadwal Hari Ini");

        tblJadwal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Mata Kuliah", "Dosen", "Kelas", "Jam", "Ruang"
            }
        ));
        jScrollPane1.setViewportView(tblJadwal);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Absensi Hari Ini");

        panelKeterangan.setBackground(new java.awt.Color(255, 255, 255));
        panelKeterangan.setPreferredSize(new java.awt.Dimension(180, 70));

        lblHadir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHadir.setText("Hadir:    0");

        panelHijau.setBackground(new java.awt.Color(0, 204, 0));
        panelHijau.setPreferredSize(new java.awt.Dimension(12, 12));

        javax.swing.GroupLayout panelHijauLayout = new javax.swing.GroupLayout(panelHijau);
        panelHijau.setLayout(panelHijauLayout);
        panelHijauLayout.setHorizontalGroup(
            panelHijauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        panelHijauLayout.setVerticalGroup(
            panelHijauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblIzin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIzin.setText("Izin:       0 ");

        lblSakit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSakit.setText("Sakit:     0");

        lblAlpha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAlpha.setText("Alpha:   0");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotal.setText("Total:          0");

        panelBiru.setBackground(new java.awt.Color(0, 0, 153));
        panelBiru.setPreferredSize(new java.awt.Dimension(18, 20));

        javax.swing.GroupLayout panelBiruLayout = new javax.swing.GroupLayout(panelBiru);
        panelBiru.setLayout(panelBiruLayout);
        panelBiruLayout.setHorizontalGroup(
            panelBiruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        panelBiruLayout.setVerticalGroup(
            panelBiruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelKuning.setBackground(new java.awt.Color(255, 204, 0));
        panelKuning.setPreferredSize(new java.awt.Dimension(18, 20));

        javax.swing.GroupLayout panelKuningLayout = new javax.swing.GroupLayout(panelKuning);
        panelKuning.setLayout(panelKuningLayout);
        panelKuningLayout.setHorizontalGroup(
            panelKuningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        panelKuningLayout.setVerticalGroup(
            panelKuningLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        panelMerah.setBackground(new java.awt.Color(255, 0, 0));
        panelMerah.setPreferredSize(new java.awt.Dimension(18, 20));

        javax.swing.GroupLayout panelMerahLayout = new javax.swing.GroupLayout(panelMerah);
        panelMerah.setLayout(panelMerahLayout);
        panelMerahLayout.setHorizontalGroup(
            panelMerahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        panelMerahLayout.setVerticalGroup(
            panelMerahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelKeteranganLayout = new javax.swing.GroupLayout(panelKeterangan);
        panelKeterangan.setLayout(panelKeteranganLayout);
        panelKeteranganLayout.setHorizontalGroup(
            panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKeteranganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelKeteranganLayout.createSequentialGroup()
                            .addComponent(jSeparator1)
                            .addGap(2, 2, 2))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelKeteranganLayout.createSequentialGroup()
                            .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(panelHijau, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelBiru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelKuning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelMerah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblAlpha)
                                .addComponent(lblSakit)
                                .addComponent(lblIzin)
                                .addComponent(lblHadir))))
                    .addComponent(lblTotal))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        panelKeteranganLayout.setVerticalGroup(
            panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKeteranganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelKeteranganLayout.createSequentialGroup()
                        .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelHijau, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .addComponent(lblHadir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIzin)
                            .addComponent(panelBiru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblSakit))
                    .addComponent(panelKuning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelKeteranganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAlpha)
                    .addComponent(panelMerah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelChart.setPreferredSize(new java.awt.Dimension(80, 80));

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 153));
        jLabel4.setText("Selamat Datang, Admin");

        jLabel5.setText("berikut adalah informasi singkat sistem absensi.");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/admin.png"))); // NOI18N
        jLabel20.setText("jLabel20");
        jLabel20.setPreferredSize(new java.awt.Dimension(40, 40));

        javax.swing.GroupLayout pnlKontenLayout = new javax.swing.GroupLayout(pnlKonten);
        pnlKonten.setLayout(pnlKontenLayout);
        pnlKontenLayout.setHorizontalGroup(
            pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKontenLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlKontenLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addGroup(pnlKontenLayout.createSequentialGroup()
                        .addGroup(pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlKontenLayout.createSequentialGroup()
                                .addComponent(lbMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlKontenLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        pnlKontenLayout.setVerticalGroup(
            pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKontenLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbMhs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(pnlKontenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlKonten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlKonten, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butondashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butondashboardActionPerformed
    // 1. Buka/buat instance DashboardAdmin yang baru (tampilan awal gress)
    DashboardAdmin dashboardBaru = new DashboardAdmin();
    dashboardBaru.setVisible(true);
    
    // 2. Tutup frame dashboard lama yang sedang error/terbuka ini
    this.dispose();  // TODO add your handling code here:
    }//GEN-LAST:event_butondashboardActionPerformed

    private void btnMahasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMahasiswaActionPerformed
    // 1. Bersihkan isi panel abu-abu besar
    pnlKonten.removeAll();
    
    // 2. Buat objek FormMahasiswa
    FormMahasiswa mhs = new FormMahasiswa();
    
    // 3. TRIK SAKTI: Set layout pnlKonten ke BorderLayout SECARA DINAMIS lewat kode
    // Cara ini aman karena cuman merubah layout lewat kode SAAT RUNTIME (tidak merusak tab Design lu!)
    pnlKonten.setLayout(new java.awt.BorderLayout());
    
    // 4. Masukkan FormMahasiswa dengan posisi CENTER biar otomatis ditarik melar ke segala sisi
    pnlKonten.add(mhs, java.awt.BorderLayout.CENTER);
    
    // 5. Refresh tampilan secara real-time
    pnlKonten.repaint();
    pnlKonten.revalidate(); // TODO add your handling code here:
    }//GEN-LAST:event_btnMahasiswaActionPerformed

    private void btnDosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDosenActionPerformed
// 1. Bersihkan pnlKonten dari halaman/dashboard bawaan sebelumnya
    pnlKonten.removeAll();
    pnlKonten.repaint();
    pnlKonten.revalidate();
    
    // 2. Panggil JPanel FormDosen yang udah kita buat
    view.FormDosen formDosen = new view.FormDosen();
    
    // 3. Set layout pnlKonten jadi BorderLayout biar panel dosen terbentang pas penuh
    pnlKonten.setLayout(new java.awt.BorderLayout());
    
    // 4. Masukkan FormDosen ke dalam pnlKonten dashboard
    pnlKonten.add(formDosen, java.awt.BorderLayout.CENTER);
    pnlKonten.repaint();
    pnlKonten.revalidate();
    }//GEN-LAST:event_btnDosenActionPerformed

    private void btnMatkulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatkulActionPerformed
    pnlKonten.removeAll();
    FormMatakuliah mk = new FormMatakuliah();
    pnlKonten.setLayout(new java.awt.BorderLayout());
    pnlKonten.add(mk, java.awt.BorderLayout.CENTER);
    pnlKonten.repaint();
    pnlKonten.revalidate();
    }//GEN-LAST:event_btnMatkulActionPerformed

    private void btnJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJadwalActionPerformed
         // Bersihkan panel konten
    pnlKonten.removeAll();

    // Tampilkan Form Data Jadwal
    FormDataJadwal jadwal = new FormDataJadwal();

    pnlKonten.setLayout(new java.awt.BorderLayout());
    pnlKonten.add(jadwal, java.awt.BorderLayout.CENTER);

    pnlKonten.repaint();
    pnlKonten.revalidate();
    }//GEN-LAST:event_btnJadwalActionPerformed

    private void btnAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsensiActionPerformed
// 1. Ambil JTable jadwal yang ada di Dashboard lu (Pastikan namanya sesuai, misal: tblJadwal)
    int barisDipilih = tblJadwal.getSelectedRow();
    
    // Cek apakah user sudah milih baris jadwal di tabel atau belum
    if (barisDipilih == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih jadwal kuliah terlebih dahulu pada tabel!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        // 2. Ambil data dari kolom tabel yang dipilih (Sesuaikan urutan indeks kolom tabelmu)
        // Indeks: 0 = ID Jadwal, 1 = Mata Kuliah, 2 = Dosen, 3 = Kelas
        int idJadwal = Integer.parseInt(tblJadwal.getValueAt(barisDipilih, 0).toString());
        String namaMK = tblJadwal.getValueAt(barisDipilih, 1).toString();
        String namaDosen = tblJadwal.getValueAt(barisDipilih, 2).toString();
        String kelas = tblJadwal.getValueAt(barisDipilih, 3).toString();
        
        // 3. Panggil FormAbsensi baru sambil melempar 4 data di atas
        view.FormAbsensi formAbsen = new view.FormAbsensi(idJadwal, namaMK, namaDosen, kelas);
        
        // 4. Masukkan FormAbsensi ke dalam panel kontainer utama dashboard kamu
        // 🔥 GANTI 'pnlKonten' di bawah ini dengan nama panel tengahmu yang asli!
        pnlKonten.removeAll();
        pnlKonten.add(formAbsen);
        pnlKonten.repaint();
        pnlKonten.revalidate();
        
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error saat memuat form absensi: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbsensiActionPerformed

    private void butonrekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonrekapActionPerformed

    pnlKonten.removeAll();

    FormRekapAbsensi rekap = new FormRekapAbsensi();

    pnlKonten.setLayout(new java.awt.BorderLayout());
    pnlKonten.add(rekap, java.awt.BorderLayout.CENTER);

    pnlKonten.repaint();
    pnlKonten.revalidate();


    }//GEN-LAST:event_butonrekapActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
    pnlKonten.removeAll();

    FormLaporanAbsensi laporan = new FormLaporanAbsensi();

    pnlKonten.setLayout(new java.awt.BorderLayout());
    pnlKonten.add(laporan, java.awt.BorderLayout.CENTER);

    pnlKonten.repaint();
    pnlKonten.revalidate();

       // TODO add your handling code here:
    }//GEN-LAST:event_btnLaporanActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int pilih = JOptionPane.showConfirmDialog(
            this,
            "Yakin Logout?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
    );

    if(pilih == JOptionPane.YES_OPTION){

        new FormLogin().setVisible(true);

        dispose();

    }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void butondashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butondashboardMousePressed
      butondashboard.setBackground(new java.awt.Color(41, 128, 185));  // TODO add your handling code here:
    }//GEN-LAST:event_butondashboardMousePressed

    private void btnMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMahasiswaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMahasiswaMouseClicked

    private void btnMahasiswaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMahasiswaMousePressed
// Mengembalikan ke warna biru tua aslimu pas klik dilepas
btnMahasiswa.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_btnMahasiswaMousePressed

    private void btnDosenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDosenMousePressed
    btnDosen.setBackground(new java.awt.Color(0, 102, 153));    // TODO add your handling code here:
    }//GEN-LAST:event_btnDosenMousePressed

    private void btnMatkulMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMatkulMousePressed
btnMatkul.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_btnMatkulMousePressed

    private void btnJadwalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJadwalMousePressed
btnJadwal.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_btnJadwalMousePressed

    private void btnAbsensiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbsensiMousePressed
btnAbsensi.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbsensiMousePressed

    private void butonrekapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butonrekapMousePressed
butonrekap.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_butonrekapMousePressed

    private void btnLaporanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMousePressed
btnLaporan.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_btnLaporanMousePressed

    private void btnLogoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMousePressed
btnLogout.setBackground(new java.awt.Color(0, 102, 153));        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new DashboardAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dashboard;
    private javax.swing.JButton btnAbsensi;
    private javax.swing.JButton btnDosen;
    private javax.swing.JButton btnJadwal;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMahasiswa;
    private javax.swing.JButton btnMatkul;
    private javax.swing.JButton butondashboard;
    private javax.swing.JButton butonrekap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel lbMhs;
    private javax.swing.JLabel lblAlpha;
    private javax.swing.JLabel lblDosen;
    private javax.swing.JLabel lblHadir;
    private javax.swing.JLabel lblIzin;
    private javax.swing.JLabel lblJadwal;
    private javax.swing.JLabel lblMatkul;
    private javax.swing.JLabel lblMhs;
    private javax.swing.JLabel lblSakit;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel panelBiru;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelHijau;
    private javax.swing.JPanel panelKeterangan;
    private javax.swing.JPanel panelKuning;
    private javax.swing.JPanel panelMerah;
    private javax.swing.JPanel pnlKonten;
    private javax.swing.JTable tblJadwal;
    // End of variables declaration//GEN-END:variables
}
