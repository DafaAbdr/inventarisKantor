/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
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
import java.util.HashMap;
import javax.swing.JLabel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author ALKHOIR
 */
public class permintaan_Barang extends javax.swing.JPanel {
    final private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    final private JTextField pathFoto;
    private String hakAkses;
    /**
     * Creates new form permintaan_Barang
     */
    public permintaan_Barang() {
        initComponents();
        dataTableBarang();
        isiComboBoxIdBarang();
        generateIdPermintaan();
        
        pathFoto = new JTextField();
        pathFoto.setVisible(false);
        add(pathFoto);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logof.png"));
        JLabel label = new JLabel(icon);
        this.add(label);
        
        String idBaru = generateIdPermintaan();
        idTransaksi.setText(idBaru);
        
        String loginIdKaryawan = loginSesi.getIdKaryawan();
        idKaryawan.setText(loginIdKaryawan);
        String loginNamaKaryawan = loginSesi.getNamaKaryawan();
        namaKaryawan.setText(loginNamaKaryawan);
        hakAkses = loginSesi.getHakAkses();
    }
    
    private String generateIdPermintaan() {
        String idBaru = "PR001";
        try {
            String sql = "SELECT MAX(RIGHT(id_transaksi, 3)) AS nomor FROM dataTransaksi WHERE id_transaksi LIKE 'PR%'";
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
        idTransaksi.setText(generateIdPermintaan());
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
        tanggal = new com.toedter.calendar.JDateChooser();
        bKeluar = new javax.swing.JButton();
        jumlah = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        bKembali = new javax.swing.JButton();
        namaKaryawan = new javax.swing.JTextField();
        foto = new javax.swing.JLabel();
        bTambah = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        bSimpan = new javax.swing.JButton();
        idBarang = new javax.swing.JComboBox<>();
        idKaryawan = new javax.swing.JTextField();
        bCetak = new javax.swing.JButton();
        namaBarang = new javax.swing.JTextField();
        idTransaksi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setText("ID Karyawan");

        bKeluar.setBackground(new java.awt.Color(41, 76, 55));
        bKeluar.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bKeluar.setForeground(new java.awt.Color(255, 255, 255));
        bKeluar.setText("Exit");
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

        jLabel12.setText("Tanggal");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Barang Tersedia");

        jLabel11.setText("Jumlah");

        jLabel10.setText("ID Barang");

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

        jLabel9.setText("Nama Barang");

        jLabel8.setText("ID Permintaan");

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

        bKembali.setBackground(new java.awt.Color(41, 76, 55));
        bKembali.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bKembali.setForeground(new java.awt.Color(255, 255, 255));
        bKembali.setText("Back");
        bKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKembaliActionPerformed(evt);
            }
        });

        namaKaryawan.setEditable(false);
        namaKaryawan.setFocusable(false);

        foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        foto.setMaximumSize(new java.awt.Dimension(186, 143));
        foto.setMinimumSize(new java.awt.Dimension(186, 143));
        foto.setPreferredSize(new java.awt.Dimension(186, 143));

        bTambah.setBackground(new java.awt.Color(41, 76, 55));
        bTambah.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Add");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        jLabel7.setText("Nama Karyawan");

        bSimpan.setBackground(new java.awt.Color(41, 76, 55));
        bSimpan.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bSimpan.setForeground(new java.awt.Color(255, 255, 255));
        bSimpan.setText("Save");
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        idBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        idKaryawan.setEditable(false);
        idKaryawan.setFocusable(false);

        bCetak.setBackground(new java.awt.Color(41, 76, 55));
        bCetak.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bCetak.setForeground(new java.awt.Color(255, 255, 255));
        bCetak.setText("Print");
        bCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakActionPerformed(evt);
            }
        });

        namaBarang.setEditable(false);
        namaBarang.setFocusable(false);

        idTransaksi.setEditable(false);
        idTransaksi.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Transaction > Item Request");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(idKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(namaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(245, 245, 245))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(25, 25, 25))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(bSimpan)
                            .addGap(30, 30, 30)
                            .addComponent(bCetak)
                            .addGap(30, 30, 30)
                            .addComponent(bKeluar)
                            .addGap(30, 30, 30)
                            .addComponent(bKembali)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 296, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(25, 25, 25)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(45, 45, 45)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(12, 12, 12)
                .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(12, 12, 12)
                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bTambah)
                .addContainerGap(162, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(24, 24, 24)
                                    .addComponent(jLabel14))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(193, 193, 193)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGap(52, 52, 52)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bCetak)
                        .addComponent(bSimpan)
                        .addComponent(bKeluar)
                        .addComponent(bKembali))
                    .addGap(81, 81, 81)))
        );
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

    private void bKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKembaliActionPerformed
        if ("admin".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menu_admin().setVisible(true);
        } else if ("karyawan".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menu_karyawan().setVisible(true);
        } else if ("karyawan inventaris".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menu_inventaris().setVisible(true);
        } else if ("bos".equalsIgnoreCase(hakAkses)) {
            this.dispose();
            new tampilanMenu.menu_bos().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Hak akses tidak dikenal!");
        }
    }//GEN-LAST:event_bKembaliActionPerformed

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

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaksi Peminjaman Barang Berhasil di Simpan");

            kosong2();
        } catch (SQLException ex) {
            Logger.getLogger(permintaan_Barang.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data transaksi: " + ex.getMessage());
        }
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakActionPerformed
        try {
            String reportPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "report" + File.separator + "LaporanPermintaan.jasper";
            HashMap<String, Object> parameters = new HashMap<>();
            JasperPrint print = JasperFillManager.fillReport(reportPath,parameters,conn);
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan:\n" + e.getMessage());
        }
        
        DefaultTableModel model = (DefaultTableModel) tableTransaksi.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_bCetakActionPerformed


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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTextField namaKaryawan;
    private javax.swing.JTable tableBarang;
    private javax.swing.JTable tableTransaksi;
    private com.toedter.calendar.JDateChooser tanggal;
    // End of variables declaration//GEN-END:variables


    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
