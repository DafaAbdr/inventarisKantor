/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package transaksi;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import tampilanMenu.loginSesi;
/**
 *
 * @author ALKHOIR
 */
public class barang_masuk extends javax.swing.JPanel {
    final private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    final private JTextField pathFoto;
    private String hakAkses;
    /**
     * Creates new form barang_masuk
     */
    public barang_masuk() {
        initComponents();
        dataTable();
        generateIdBarangMasuk();
        isiComboBoxNamaBarang();
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logof.png"));
        JLabel label = new JLabel(icon);
        this.add(label);
        
        pathFoto = new JTextField();
        pathFoto.setVisible(false);
        add(pathFoto);
        
        String loginIdKaryawan = loginSesi.getIdKaryawan();
        idKaryawan.setText(loginIdKaryawan);
        String loginNamaKaryawan = loginSesi.getNamaKaryawan();
        namaKaryawan.setText(loginNamaKaryawan);
        hakAkses = loginSesi.getHakAkses();
        
        String idBaru = generateIdBarangMasuk();
        idBarangMasuk.setText(idBaru);
    }
    
    private void dataTable(){
        String[] kolom = {"Id Barang Masuk", "Nama Barang", "Jumlah", "Tanggal"};
        tabmode = new DefaultTableModel(null, kolom);
        tableBarangMasuk.setModel(tabmode);
    }

    private String generateIdBarangMasuk(){
        String idBaru = "TB001";
        try {
            String sql = "SELECT MAX(RIGHT(id_transaksi, 3)) AS nomor FROM dataTransaksi WHERE id_transaksi LIKE 'TB%'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int nomor = rs.getInt("nomor") + 1;
                idBaru = String.format("TB%03d", nomor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal generate ID Barang Masuk: " + e.getMessage());
        }
        return idBaru;
    }
    
    protected void isiComboBoxNamaBarang() {
        try {
            String sql = "SELECT nama_barang FROM databarang";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Pilihan barang");

            while (rs.next()) {
                model.addElement(rs.getString("nama_barang"));
            }

            namaBarang.setModel(model);

            for (ActionListener al : namaBarang.getActionListeners()) {
                namaBarang.removeActionListener(al);
            }

            namaBarang.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedNama = namaBarang.getSelectedItem().toString();

                    if (selectedNama.equals("Pilihan barang")) {
                        foto.setIcon(null);
                        return;
                    }
                    try {
                        String query = "SELECT gambar FROM databarang WHERE nama_barang = ?";
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, selectedNama);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            String namaGambar = rs.getString("gambar");
                            if (namaGambar != null && !namaGambar.isEmpty()) {
                                String pathGambar = System.getProperty("user.dir") + File.separator +
                                                    "src" + File.separator + "imagesBarang" + File.separator + namaGambar;
                                File file = new File(pathGambar);
                                if (file.exists()) {
                                    ImageIcon icon = new ImageIcon(pathGambar);
                                    Image img = icon.getImage().getScaledInstance(
                                            foto.getWidth(),
                                            foto.getHeight(),
                                            Image.SCALE_SMOOTH);
                                    foto.setIcon(new ImageIcon(img));
                                } else {
                                    foto.setIcon(null);
                                    JOptionPane.showMessageDialog(null, "File gambar tidak ditemukan:\n" + pathGambar);
                                }
                            } else {
                                foto.setIcon(null);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Gagal mengambil data gambar: " + ex.getMessage());
                    }
                }
            });
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Gagal mengisi ComboBox: " + e.getMessage());
        }
    }
    
    protected void kosong(){
        namaBarang.setSelectedIndex(0);
        jumlah.setText("");
    }
    
    protected void kosong2(){
        namaBarang.setSelectedIndex(0);
        jumlah.setText("");
        tanggal.setDate(null);
        idBarangMasuk.setText(generateIdBarangMasuk());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        bBersihkan = new javax.swing.JButton();
        bCetak = new javax.swing.JButton();
        idKaryawan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        namaKaryawan = new javax.swing.JTextField();
        bSimpan = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarangMasuk = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        idBarangMasuk = new javax.swing.JTextField();
        tanggal = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        namaBarang = new javax.swing.JComboBox<>();
        foto = new javax.swing.JLabel();
        bTambah = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("DATA TRANSAKSI > BARANG MASUK");

        bBersihkan.setBackground(new java.awt.Color(41, 76, 55));
        bBersihkan.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bBersihkan.setForeground(new java.awt.Color(255, 255, 255));
        bBersihkan.setText("Bersihkan");
        bBersihkan.setMaximumSize(new java.awt.Dimension(200, 40));
        bBersihkan.setMinimumSize(new java.awt.Dimension(200, 40));
        bBersihkan.setPreferredSize(new java.awt.Dimension(200, 40));
        bBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBersihkanActionPerformed(evt);
            }
        });

        bCetak.setBackground(new java.awt.Color(41, 76, 55));
        bCetak.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bCetak.setForeground(new java.awt.Color(255, 255, 255));
        bCetak.setText("Cetak");
        bCetak.setMaximumSize(new java.awt.Dimension(200, 40));
        bCetak.setMinimumSize(new java.awt.Dimension(200, 40));
        bCetak.setPreferredSize(new java.awt.Dimension(200, 40));
        bCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakActionPerformed(evt);
            }
        });

        idKaryawan.setEditable(false);
        idKaryawan.setFocusable(false);
        idKaryawan.setMaximumSize(new java.awt.Dimension(250, 25));
        idKaryawan.setMinimumSize(new java.awt.Dimension(250, 25));
        idKaryawan.setPreferredSize(new java.awt.Dimension(250, 25));
        idKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idKaryawanActionPerformed(evt);
            }
        });

        jLabel4.setText("Nama Karyawan");
        jLabel4.setMaximumSize(new java.awt.Dimension(90, 20));
        jLabel4.setMinimumSize(new java.awt.Dimension(90, 20));
        jLabel4.setPreferredSize(new java.awt.Dimension(90, 20));

        jLabel2.setText("ID Karyawan");
        jLabel2.setMaximumSize(new java.awt.Dimension(90, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(90, 20));
        jLabel2.setPreferredSize(new java.awt.Dimension(90, 20));

        namaKaryawan.setEditable(false);
        namaKaryawan.setFocusable(false);
        namaKaryawan.setMaximumSize(new java.awt.Dimension(250, 25));
        namaKaryawan.setMinimumSize(new java.awt.Dimension(250, 25));
        namaKaryawan.setPreferredSize(new java.awt.Dimension(250, 25));
        namaKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaKaryawanActionPerformed(evt);
            }
        });

        bSimpan.setBackground(new java.awt.Color(41, 76, 55));
        bSimpan.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bSimpan.setForeground(new java.awt.Color(255, 255, 255));
        bSimpan.setText("Simpan");
        bSimpan.setMaximumSize(new java.awt.Dimension(200, 40));
        bSimpan.setMinimumSize(new java.awt.Dimension(200, 40));
        bSimpan.setPreferredSize(new java.awt.Dimension(200, 40));
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama Barang");

        jLabel10.setText("Jumlah");

        jumlah.setMaximumSize(new java.awt.Dimension(250, 25));
        jumlah.setMinimumSize(new java.awt.Dimension(250, 25));
        jumlah.setPreferredSize(new java.awt.Dimension(250, 25));

        tableBarangMasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Barang Masuk", "Nama Barang", "Jumlah", "Tanggal Masuk", "Harga", "Kondisi", "Spesifikasi"
            }
        ));
        jScrollPane1.setViewportView(tableBarangMasuk);

        jLabel3.setText("ID Barang Masuk");

        idBarangMasuk.setEditable(false);
        idBarangMasuk.setFocusable(false);
        idBarangMasuk.setMaximumSize(new java.awt.Dimension(250, 25));
        idBarangMasuk.setMinimumSize(new java.awt.Dimension(250, 25));
        idBarangMasuk.setPreferredSize(new java.awt.Dimension(250, 25));

        tanggal.setMaximumSize(new java.awt.Dimension(250, 25));
        tanggal.setMinimumSize(new java.awt.Dimension(250, 25));
        tanggal.setPreferredSize(new java.awt.Dimension(250, 25));

        jLabel5.setText("Tanggal Masuk");

        namaBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        namaBarang.setMaximumSize(new java.awt.Dimension(250, 25));
        namaBarang.setMinimumSize(new java.awt.Dimension(250, 25));
        namaBarang.setPreferredSize(new java.awt.Dimension(250, 25));

        foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        foto.setMaximumSize(new java.awt.Dimension(163, 161));
        foto.setMinimumSize(new java.awt.Dimension(163, 161));
        foto.setPreferredSize(new java.awt.Dimension(163, 161));

        bTambah.setBackground(new java.awt.Color(41, 76, 55));
        bTambah.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Tambahkan");
        bTambah.setMaximumSize(new java.awt.Dimension(120, 70));
        bTambah.setMinimumSize(new java.awt.Dimension(120, 70));
        bTambah.setPreferredSize(new java.awt.Dimension(120, 70));
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 942, Short.MAX_VALUE)
                        .addGap(25, 25, 25))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(namaBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(idBarangMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105)
                        .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(idBarangMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3))))
                    .addComponent(foto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(767, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(bCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(bBersihkan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(idKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(174, 174, 174)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(namaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bBersihkan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBersihkanActionPerformed
        kosong();
    }//GEN-LAST:event_bBersihkanActionPerformed

    private void bCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakActionPerformed
        try {
            String reportPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "report" + File.separator + "LaporanBarangMasuk.jasper";
            HashMap<String, Object> parameters = new HashMap<>();
            JasperPrint print = JasperFillManager.fillReport(reportPath,parameters,conn);
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan:\n" + e.getMessage());
        }
        
        DefaultTableModel model = (DefaultTableModel) tableBarangMasuk.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_bCetakActionPerformed

    private void idKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idKaryawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idKaryawanActionPerformed

    private void namaKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaKaryawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaKaryawanActionPerformed

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal2 = sdf.format(tanggal.getDate());

        String sql = "INSERT INTO dataTransaksi (id_transaksi, id_karyawan, nama_karyawan, tanggal) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idBarangMasuk.getText());
            ps.setString(2, idKaryawan.getText());
            ps.setString(3, namaKaryawan.getText());
            ps.setString(4, tanggal2);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaksi Barang Masuk Berhasil di Simpan");

            kosong2();
        } catch (SQLException ex) {
            Logger.getLogger(permintaan_Barang.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data transaksi: " + ex.getMessage());
        }
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        String idBarangMasuk = generateIdBarangMasuk();
        String namaBarang1 = namaBarang.getSelectedItem().toString();
        String jumlahBarang = jumlah.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalBarangMasuk = sdf.format(tanggal.getDate());

        String idBarang = "";

        try {
            String sqlGetBarang = "SELECT id_barang FROM stokbarang WHERE nama_barang = ?";
            PreparedStatement psGet = conn.prepareStatement(sqlGetBarang);
            psGet.setString(1, namaBarang1);
            ResultSet rs = psGet.executeQuery();

            if (rs.next()) {
                idBarang = rs.getString("id_barang");
            } else {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan di database!");
                return;
            }

            //String sqlInsert = "INSERT INTO stokbaranginfo (id_stok, id_barang, nama_barang, tanggal, stok_barang) VALUES (?, ?, ?, ?, ?)";
            String sqlInsert = "INSERT INTO datatransaksiinfo (id_transaksi, id_barang, nama_barang, tanggal, jumlah_barang) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, idBarangMasuk);
            ps.setString(2, idBarang);
            ps.setString(3, namaBarang1);
            ps.setString(4, tanggalBarangMasuk);
            ps.setString(5, jumlahBarang);
            ps.executeUpdate();

            String sqlUpdateStok = "UPDATE stokbarang SET stok_barang = stok_barang + ? WHERE id_barang = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStok);
            psUpdate.setInt(1, Integer.parseInt(jumlahBarang));
            psUpdate.setString(2, idBarang);
            psUpdate.executeUpdate();

            String[] data = {idBarangMasuk, namaBarang1, jumlahBarang, tanggalBarangMasuk};
            tabmode.insertRow(0, data);

            kosong();
            JOptionPane.showMessageDialog(null, "Barang berhasil dimasukkan!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan barang masuk: " + e.getMessage());
        }
    }//GEN-LAST:event_bTambahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBersihkan;
    private javax.swing.JButton bCetak;
    private javax.swing.JButton bSimpan;
    private javax.swing.JButton bTambah;
    private javax.swing.JLabel foto;
    private javax.swing.JTextField idBarangMasuk;
    private javax.swing.JTextField idKaryawan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jumlah;
    private javax.swing.JComboBox<String> namaBarang;
    private javax.swing.JTextField namaKaryawan;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tableBarangMasuk;
    private com.toedter.calendar.JDateChooser tanggal;
    // End of variables declaration//GEN-END:variables


    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
