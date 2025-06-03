/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package transaksi;

import tampilanMenu.loginSesi;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import koneksi.koneksi;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dafaa
 */

public class permintaanBarang extends javax.swing.JFrame {
    final private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    final private JTextField pathFoto;
    private String hakAkses;
    
    /**
     * Creates new form peminjamanBarang
     */
    
    public permintaanBarang() {
        initComponents();
        dataTableBarang();
        isiComboBoxIdBarang();
        generateIdPermintaan();
        pathFoto = new JTextField();
        pathFoto.setVisible(false);
        add(pathFoto);
        setTitle("Inventaris Perkantoran");
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logof.png"));
        String idBaru = generateIdPermintaan();
        idTransaksi.setText(idBaru);
        setIconImage(icon.getImage());
        String loginIdKaryawan = loginSesi.getIdKaryawan();
        idKaryawan.setText(loginIdKaryawan);
        String loginNamaKaryawan = loginSesi.getNamaKaryawan();
        namaKaryawan.setText(loginNamaKaryawan);
        hakAkses = loginSesi.getHakAkses();
    }

    private String generateIdPermintaan() {
    String idBaru = "PR001";
    try {
        String sql = "SELECT MAX(RIGHT(id_transaksi, 3)) AS nomor FROM dataTransaksi";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int nomor = rs.getInt("nomor") + 1;
            idBaru = String.format("PR%03d", nomor);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal generate ID Permintaan: " + e.getMessage());
    }
    return idBaru;
    }

    
    protected void dataTableBarang(){
        Object[] baris = {"nama_barang", "jumlah"};
        tabmode = new DefaultTableModel(null, baris);
        tableBarang.setModel(tabmode);
        
        String sql = "SELECT databarang.nama_barang, stokBarang.stok_barang " +
                     "FROM databarang " +
                     "INNER JOIN stokBarang ON databarang.id_barang = stokBarang.id_barang " +
                     "WHERE databarang.id_barang LIKE 'BP%'";
        
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("nama_barang");
                String b = hasil.getString("stok_barang");
                
                String[] data = {a, b};
                tabmode.addRow(data);
            }
        }catch (Exception e){
        }
    }

    protected void isiComboBoxIdBarang() {
        try {
            String sql = "SELECT id_barang FROM databarang WHERE id_barang LIKE 'BP%'";
            
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("Pilihan barang");

            while (rs.next()) {
                model.addElement(rs.getString("id_barang"));
            }

            idBarang.setModel(model);

            idBarang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedId = idBarang.getSelectedItem().toString();

                    String query = "SELECT nama_barang, gambar FROM databarang WHERE id_barang = ? ";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, selectedId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        namaBarang.setText(rs.getString("nama_barang"));
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
                                    foto.setIcon(null);
                                    foto.setIcon(new ImageIcon(img));
                                } else {
                                    foto.setIcon(null);
                                    JOptionPane.showMessageDialog(null, "File gambar tidak ditemukan!");
                                }
                        } else {
                            foto.setIcon(null);
                        }
                    } else {
                        namaBarang.setText("");
                        foto.setIcon(null);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Gagal mengambil data barang: " + ex.getMessage());
                }
            }
        });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengisi ComboBox ID: " + e.getMessage());
        }
    }
    
    protected void kosong(){
        namaBarang.setText("");
        idBarang.setSelectedIndex(0);
        jumlah.setText("");
    }
    
    protected void kosong2(){
        namaBarang.setText("");
        idBarang.setSelectedIndex(0);
        jumlah.setText("");
        tanggal.setDate(null);
        idTransaksi.setText(generateIdPermintaan()); // Generate ID baru

        // Kosongkan tabel transaksi
        DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel();
        model.setRowCount(0);
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
        jLabel6 = new javax.swing.JLabel();
        idKaryawan = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        namaKaryawan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        idTransaksi = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        tanggal = new com.toedter.calendar.JDateChooser();
        bCetak = new javax.swing.JButton();
        bSimpan = new javax.swing.JButton();
        foto = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        bKeluar = new javax.swing.JButton();
        namaBarang = new javax.swing.JTextField();
        idBarang = new javax.swing.JComboBox<>();
        bTambah = new javax.swing.JButton();
        bKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(905, 580));

        jLabel6.setText("ID Karyawan");

        idKaryawan.setEditable(false);
        idKaryawan.setFocusable(false);

        jLabel7.setText("Nama Karyawan");

        namaKaryawan.setEditable(false);
        namaKaryawan.setFocusable(false);

        jLabel8.setText("ID Permintaan");

        jLabel9.setText("Nama Barang");

        jLabel10.setText("ID Barang");

        jLabel11.setText("Jumlah");

        jLabel12.setText("Tanggal");

        idTransaksi.setEditable(false);
        idTransaksi.setFocusable(false);

        bCetak.setBackground(new java.awt.Color(41, 76, 55));
        bCetak.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bCetak.setForeground(new java.awt.Color(255, 255, 255));
        bCetak.setText("Print");
        bCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakActionPerformed(evt);
            }
        });

        bSimpan.setBackground(new java.awt.Color(41, 76, 55));
        bSimpan.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bSimpan.setForeground(new java.awt.Color(255, 255, 255));
        bSimpan.setText("Save");
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        foto.setMaximumSize(new java.awt.Dimension(186, 143));
        foto.setMinimumSize(new java.awt.Dimension(186, 143));
        foto.setPreferredSize(new java.awt.Dimension(186, 143));

        tableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama Barang", "Stok Barang"
            }
        ));
        tableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBarang);

        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Permintaan", "Nama Barang", "ID Barang", "Jumlah", "Tanggal"
            }
        ));
        jScrollPane2.setViewportView(tableTransaksi);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Barang Tersedia");

        jPanel2.setBackground(new java.awt.Color(41, 76, 55));

        jLabel1.setBackground(new java.awt.Color(41, 76, 55));
        jLabel1.setFont(new java.awt.Font("Montserrat ExtraBold", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ITEM REQUEST");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        bKeluar.setBackground(new java.awt.Color(41, 76, 55));
        bKeluar.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bKeluar.setForeground(new java.awt.Color(255, 255, 255));
        bKeluar.setText("Exit");
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

        namaBarang.setEditable(false);
        namaBarang.setFocusable(false);

        idBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        bTambah.setBackground(new java.awt.Color(41, 76, 55));
        bTambah.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Add");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bKembali.setBackground(new java.awt.Color(41, 76, 55));
        bKembali.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bKembali.setForeground(new java.awt.Color(255, 255, 255));
        bKembali.setText("Back");
        bKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(idKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(namaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(bSimpan)
                        .addGap(46, 46, 46)
                        .addComponent(bCetak)
                        .addGap(36, 36, 36)
                        .addComponent(bKeluar)
                        .addGap(40, 40, 40)
                        .addComponent(bKembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaBarang)
                    .addComponent(idBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jumlah)
                            .addComponent(tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(idKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(namaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bCetak)
                    .addComponent(bSimpan)
                    .addComponent(bKeluar)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bTambah)
                        .addComponent(bKembali)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1081, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKeluarActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog (
            this,
            "Apakah Anda yakin ingin keluar?",
            "Konfirmasi Keluar",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_bKeluarActionPerformed

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal2 = sdf.format(tanggal.getDate());

        String sql = "INSERT INTO dataTransaksi (id_transaksi, id_karyawan, nama_karyawan, tanggal) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idTransaksi.getText());
            ps.setString(2, idKaryawan.getText());
            ps.setString(3, namaKaryawan.getText());
            ps.setString(4, tanggal2);

            ps.executeUpdate();  // Jangan lupa eksekusi insert ke DB

            JOptionPane.showMessageDialog(null, "Transaksi Permintaan Barang Berhasil di Simpan");

        

        } catch (SQLException ex) {
            Logger.getLogger(permintaanBarang.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data transaksi: " + ex.getMessage());
        }
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakActionPerformed
        try {
            String reportPath = "";
            HashMap<String, Object> parameters = new HashMap<>();
            JasperPrint print = JasperFillManager.fillReport(reportPath,parameters,conn);
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_bCetakActionPerformed
                                 
    private void tableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBarangMouseClicked
        int baris = tableBarang.getSelectedRow();
    if (baris != -1) {
        String nama = tabmode.getValueAt(baris, 0).toString();
        namaBarang.setText(nama);
        try {
            String sql = "SELECT id_barang FROM databarang WHERE nama_barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id_barang");
                idBarang.setSelectedItem(id);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ambil ID barang: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_tableBarangMouseClicked

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal2 = sdf.format(tanggal.getDate());
    String idTransaksi2 = idTransaksi.getText();
    String idBarang2 = idBarang.getSelectedItem().toString();
    String jumlah2 = jumlah.getText();

    tabmode = (DefaultTableModel) tableTransaksi.getModel();

    String sqlCekStok = "SELECT stok_barang FROM stokbarang WHERE id_barang = ?";
    String sqlInsert = "INSERT INTO dataTransaksiInfo (id_transaksi, id_barang, nama_barang, jumlah_barang, tanggal) VALUES (?, ?, ?, ?, ?)";
    String sqlUpdateStok = "UPDATE stokbarang SET stok_barang = stok_barang - ? WHERE id_barang = ?";

    try {
        PreparedStatement psCek = conn.prepareStatement(sqlCekStok);
        psCek.setString(1, idBarang2);
        ResultSet rs = psCek.executeQuery();

        if (rs.next()) {
            int stokTersedia = rs.getInt("stok_barang");
            int jumlahDiminta = Integer.parseInt(jumlah2);

            if (jumlahDiminta > stokTersedia) {
                JOptionPane.showMessageDialog(null, "Stok tidak mencukupi! Stok tersedia: " + stokTersedia);
                return;
            }

            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, idTransaksi2);
            ps.setString(2, idBarang2);
            ps.setString(3, namaBarang.getText());
            ps.setString(4, jumlah2);
            ps.setString(5, tanggal2);
            ps.executeUpdate();

            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStok);
            psUpdate.setInt(1, jumlahDiminta);
            psUpdate.setString(2, idBarang2);
            psUpdate.executeUpdate();

            String[] data = {
                idTransaksi2,
                idBarang2,
                namaBarang.getText(),
                jumlah2,
                tanggal2
            };
            tabmode.insertRow(0, data);

            kosong();
            dataTableBarang();
        } else {
            JOptionPane.showMessageDialog(null, "Data stok barang tidak ditemukan.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal Menyimpan & Update Stok: " + e.getMessage());
    }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKembaliActionPerformed
        if ("admin".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menuAdmin().setVisible(true);
        } else if ("karyawan".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menuKaryawan().setVisible(true);
        } else if ("karyawan inventaris".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menuInventaris().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Hak akses tidak dikenal!");
        }
    }//GEN-LAST:event_bKembaliActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(permintaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(permintaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(permintaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(permintaanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new permintaanBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCetak;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bKembali;
    private javax.swing.JButton bSimpan;
    private javax.swing.JButton bTambah;
    private javax.swing.JLabel foto;
    private javax.swing.JComboBox<String> idBarang;
    private javax.swing.JTextField idKaryawan;
    private javax.swing.JTextField idTransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTextField namaKaryawan;
    private javax.swing.JTable tableBarang;
    private javax.swing.JTable tableTransaksi;
    private com.toedter.calendar.JDateChooser tanggal;
    // End of variables declaration//GEN-END:variables
}
