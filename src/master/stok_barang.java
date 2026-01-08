/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package master;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
/**
 *
 * @author ALKHOIR
 */
public class stok_barang extends javax.swing.JPanel {
    final private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    final private JTextField pathFoto;
    /**
     * Creates new form stok_barang
     */
    public stok_barang() {
        initComponents();
        dataTable();
        isiComboBoxIdBarang();
        
        pathFoto = new JTextField();
        pathFoto.setVisible(false);
        add(pathFoto);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logof.png"));
        JLabel label = new JLabel(icon);
        this.add(label);
        
        idBarang.addActionListener(e -> {
            if (idBarang.getSelectedItem() != null) {
                loadDataBarang(idBarang.getSelectedItem().toString());
            }
        });
    }
    
    protected void isiComboBoxIdBarang() {
    try {
        String sql = "SELECT id_barang FROM dataBarang";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        while (rs.next()) {
            model.addElement(rs.getString("id_barang"));
        }
        idBarang.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal mengisi ComboBox ID: " + e.getMessage());
    }
    }

    protected void kosong(){
        namaBarang.setText("");
        jumlah.setText("");
        satuan.setText("");
        foto.setIcon(null);
        cari.setText("");
    }
    
    protected void dataTable(){
        Object[] baris = {"id_barang", "nama_barang", "stok_barang", "satuan"};
        tabmode = new DefaultTableModel(null, baris);
        tableStokBarang.setModel(tabmode);
        String sql = "SELECT * FROM stokBarang ORDER BY id_barang ASC";
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("id_barang");
                String b = hasil.getString("nama_barang");
                String c = hasil.getString("stok_barang");
                String d = hasil.getString("satuan");
                
                String[] data = {a, b, c, d};
                tabmode.addRow(data);
            }
        }catch (Exception e){
        }
    }
    
    private void loadDataBarang(String id) {
        try {
            String query =
                "SELECT db.nama_barang AS nama_barang, " +
                "       db.gambar AS gambar, " +
                "       sb.satuan AS satuan, " +
                "       sb.stok_barang AS stok_barang " +
                "FROM dataBarang db " +
                "LEFT JOIN stokbarang sb ON db.id_barang = sb.id_barang " +
                "WHERE db.id_barang = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                namaBarang.setText(rs.getString("nama_barang"));
                satuan.setText(rs.getString("satuan") != null ? rs.getString("satuan") : "");
                jumlah.setText(rs.getString("stok_barang") != null ? rs.getString("stok_barang") : "");

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
                    }
                } else {
                    foto.setIcon(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data barang: " + e.getMessage());
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

        jumlah = new javax.swing.JTextField();
        bCari = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        foto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStokBarang = new javax.swing.JTable();
        namaBarang = new javax.swing.JTextField();
        idBarang = new javax.swing.JComboBox<>();
        cari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        bTambah = new javax.swing.JButton();
        bEdit = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bBersihkan = new javax.swing.JButton();
        satuan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setPreferredSize(new java.awt.Dimension(1040, 600));

        jumlah.setMaximumSize(new java.awt.Dimension(250, 25));
        jumlah.setMinimumSize(new java.awt.Dimension(250, 25));
        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });

        bCari.setBackground(new java.awt.Color(41, 76, 55));
        bCari.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bCari.setForeground(new java.awt.Color(255, 255, 255));
        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        jLabel3.setText("ID Barang");

        jLabel5.setText("Nama Barang");

        jLabel9.setText("Stok");

        foto.setBackground(new java.awt.Color(255, 0, 248));
        foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        foto.setMaximumSize(new java.awt.Dimension(218, 207));
        foto.setMinimumSize(new java.awt.Dimension(218, 207));
        foto.setPreferredSize(new java.awt.Dimension(218, 207));

        tableStokBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Barang ", "Nama Barang", "Jumlah", "Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStokBarang.setShowGrid(true);
        tableStokBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableStokBarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableStokBarang);

        namaBarang.setEditable(false);
        namaBarang.setFocusable(false);
        namaBarang.setMaximumSize(new java.awt.Dimension(250, 25));
        namaBarang.setMinimumSize(new java.awt.Dimension(250, 25));

        idBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        idBarang.setMaximumSize(new java.awt.Dimension(250, 25));
        idBarang.setMinimumSize(new java.awt.Dimension(250, 25));

        cari.setMaximumSize(new java.awt.Dimension(250, 25));
        cari.setMinimumSize(new java.awt.Dimension(250, 25));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("DATA UTAMA > STOK BARANG");

        bTambah.setBackground(new java.awt.Color(41, 76, 55));
        bTambah.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Tambah");
        bTambah.setMaximumSize(new java.awt.Dimension(200, 40));
        bTambah.setMinimumSize(new java.awt.Dimension(200, 40));
        bTambah.setPreferredSize(new java.awt.Dimension(200, 40));
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bEdit.setBackground(new java.awt.Color(41, 76, 55));
        bEdit.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bEdit.setForeground(new java.awt.Color(255, 255, 255));
        bEdit.setText("Ubah");
        bEdit.setMaximumSize(new java.awt.Dimension(200, 40));
        bEdit.setMinimumSize(new java.awt.Dimension(200, 40));
        bEdit.setPreferredSize(new java.awt.Dimension(200, 40));
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });

        bHapus.setBackground(new java.awt.Color(41, 76, 55));
        bHapus.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bHapus.setForeground(new java.awt.Color(255, 255, 255));
        bHapus.setText("Hapus");
        bHapus.setMaximumSize(new java.awt.Dimension(200, 40));
        bHapus.setMinimumSize(new java.awt.Dimension(200, 40));
        bHapus.setPreferredSize(new java.awt.Dimension(200, 40));
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

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

        satuan.setMaximumSize(new java.awt.Dimension(250, 25));
        satuan.setMinimumSize(new java.awt.Dimension(250, 25));
        satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                satuanActionPerformed(evt);
            }
        });

        jLabel10.setText("Satuan");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(bCari))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(bEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(bBersihkan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(40, 40, 40)
                                        .addComponent(foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9)
                        .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCari))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bBersihkan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        Object[] baris = {"id_barang", "nama_barang", "jumlah"};
        tabmode = new DefaultTableModel(null, baris);
        tableStokBarang.setModel(tabmode);

        String keyword = cari.getText();
        
        String sql = "SELECT * FROM stokBarang WHERE " +
                     "id_barang LIKE ? OR " +
                     "nama_barang LIKE ? OR " +
                     "stok_barang LIKE ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, "%" + keyword + "%");
            }

            ResultSet hasil = ps.executeQuery();
            
            while (hasil.next()) {
                String a = hasil.getString("id_barang");
                String b = hasil.getString("nama_barang");
                String c = hasil.getString("stok_barang");
                
                String[] data = {a, b, c};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Mencari Data Stok!\n" + e.getMessage());
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void tableStokBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStokBarangMouseClicked
        int baris = tableStokBarang.getSelectedRow();

        if (baris != -1) {
            idBarang.setSelectedItem(tabmode.getValueAt(baris, 0).toString());
            namaBarang.setText(tabmode.getValueAt(baris, 1).toString());
            jumlah.setText(tabmode.getValueAt(baris, 2).toString());

            String id = idBarang.getSelectedItem().toString();
            String destDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "imagesBarang" + File.separator;
            String fileName = null;

            try {
                String query = "SELECT gambar FROM dataBarang WHERE id_barang = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    fileName = rs.getString("gambar");
                } else {
                    query = "SELECT gambar FROM dataBarang WHERE id_barang = ?";
                    ps = conn.prepareStatement(query);
                    ps.setString(1, id);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        fileName = rs.getString("gambar");
                    }
                }

                if (fileName != null) {
                    String fullPath = destDir + fileName;
                    pathFoto.setText(fullPath);
                    BufferedImage bi = ImageIO.read(new File(fullPath));
                    Image img = bi.getScaledInstance(218, 207, Image.SCALE_SMOOTH);
                    foto.setIcon(new ImageIcon(img));
                } else {
                    foto.setIcon(null);
                    JOptionPane.showMessageDialog(null, "Gambar tidak ditemukan untuk ID ini.");
                }

            } catch (Exception e) {
                foto.setIcon(null);
                JOptionPane.showMessageDialog(null, "Gagal menampilkan gambar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_tableStokBarangMouseClicked

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        try {
        String cek = "SELECT id_barang FROM stokBarang WHERE id_barang=?";
        PreparedStatement c = conn.prepareStatement(cek);
        c.setString(1, idBarang.getSelectedItem().toString());
        ResultSet r = c.executeQuery();

        if (r.next()) {
            JOptionPane.showMessageDialog(null, "Stok barang sudah ada");
            return;
        }

        String sql = "INSERT INTO stokbarang (id_barang, nama_barang, stok_barang, satuan) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idBarang.getSelectedItem().toString());
        ps.setString(2, namaBarang.getText());
        ps.setString(3, jumlah.getText());
        ps.setString(4, satuan.getText());

        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data Stok Barang Berhasil Disimpan");

        dataTable();
        kosong();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditActionPerformed
        try {
            String sql = "UPDATE stokBarang SET `stok_barang`=?, `satuan`=? WHERE `id_barang`=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, jumlah.getText());
            ps.setString(2, satuan.getText());
            ps.setString(3, idBarang.getSelectedItem().toString());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Stok Berhasil Diedit.");
            dataTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Mengedit Data Stok!\n" + e.getMessage());
        }
    }//GEN-LAST:event_bEditActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        try {
            String sql = "DELETE FROM stokBarang WHERE id_barang=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idBarang.getSelectedItem().toString());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Stok Berhasil Dihapus.");
            kosong();
            dataTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data Stok!\n" + e.getMessage());
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void bBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBersihkanActionPerformed
        kosong();
    }//GEN-LAST:event_bBersihkanActionPerformed

    private void satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_satuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_satuanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBersihkan;
    private javax.swing.JButton bCari;
    private javax.swing.JButton bEdit;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bTambah;
    private javax.swing.JTextField cari;
    private javax.swing.JLabel foto;
    private javax.swing.JComboBox<String> idBarang;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField namaBarang;
    private javax.swing.JTextField satuan;
    private javax.swing.JTable tableStokBarang;
    // End of variables declaration//GEN-END:variables


    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
