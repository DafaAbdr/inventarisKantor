/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package master;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        idBarang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedId = idBarang.getSelectedItem().toString();

                    String query = "SELECT db.nama_barang, db.gambar, sb.satuan " +
                                   "FROM dataBarang db " +
                                   "JOIN stokbarang sb ON db.id_barang = sb.id_barang " +
                                   "WHERE db.id_barang = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, selectedId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        namaBarang.setText(rs.getString("nama_barang"));
                        satuan.setText(rs.getString("satuan"));

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
                                JOptionPane.showMessageDialog(null, "File gambar tidak ditemukan!");
                            }
                        } else {
                            foto.setIcon(null);
                        }
                    } else {
                        namaBarang.setText("Tidak ditemukan");
                        satuan.setText(""); // reset satuan
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
        idBarang.setSelectedIndex(0);
        namaBarang.setText("");
        jumlah.setText("");
        foto.setIcon(null);
    }
    
    protected void dataTable(){
        Object[] baris = {"id_barang", "nama_barang", "stok_barang", "satuan"};
        tabmode = new DefaultTableModel(null, baris);
        tableStokBarang.setModel(tabmode);
        String sql = "SELECT * FROM stokBarang";
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
        bKembali = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bKeluar = new javax.swing.JButton();
        satuan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setMaximumSize(new java.awt.Dimension(910, 600));
        setMinimumSize(new java.awt.Dimension(910, 600));
        setPreferredSize(new java.awt.Dimension(910, 600));

        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });

        bCari.setBackground(new java.awt.Color(41, 76, 55));
        bCari.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bCari.setForeground(new java.awt.Color(255, 255, 255));
        bCari.setText("Search");
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

        idBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Master > Stock Inventory");

        bTambah.setBackground(new java.awt.Color(41, 76, 55));
        bTambah.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Add");
        bTambah.setPreferredSize(new java.awt.Dimension(74, 23));
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bEdit.setBackground(new java.awt.Color(41, 76, 55));
        bEdit.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bEdit.setForeground(new java.awt.Color(255, 255, 255));
        bEdit.setText("Edit");
        bEdit.setPreferredSize(new java.awt.Dimension(74, 23));
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });

        bKembali.setBackground(new java.awt.Color(41, 76, 55));
        bKembali.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bKembali.setForeground(new java.awt.Color(255, 255, 255));
        bKembali.setText("Back");
        bKembali.setPreferredSize(new java.awt.Dimension(74, 23));
        bKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKembaliActionPerformed(evt);
            }
        });

        bHapus.setBackground(new java.awt.Color(41, 76, 55));
        bHapus.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bHapus.setForeground(new java.awt.Color(255, 255, 255));
        bHapus.setText("Delete");
        bHapus.setPreferredSize(new java.awt.Dimension(74, 23));
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bKeluar.setBackground(new java.awt.Color(41, 76, 55));
        bKeluar.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        bKeluar.setForeground(new java.awt.Color(255, 255, 255));
        bKeluar.setText("Exit");
        bKeluar.setPreferredSize(new java.awt.Dimension(74, 23));
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(bCari))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(31, 31, 31)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(56, 56, 56)
                                        .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5))
                    .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9)
                        .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(idBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(satuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCari))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
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
                    Image img = bi.getScaledInstance(143, 109, Image.SCALE_SMOOTH);
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
        Object[] baris = {"id_barang", "nama_barang", "stok_barang", "satuan"};
        tabmode = new DefaultTableModel(null, baris);
        tableStokBarang.setModel(tabmode);

        String sql = "INSERT INTO stokBarang (id_barang, nama_barang, stok_barang, satuan) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idBarang.getSelectedItem().toString());
            ps.setString(2, namaBarang.getText());
            ps.setString(3, jumlah.getText());
            ps.setString(4, satuan.getText());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Stok Barang Berhasil Disimpan");

            kosong();
            dataTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menyimpan Data Stok Barang: " + e.getMessage());
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

    private void bKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKembaliActionPerformed
        this.dispose();
        new tampilanMenu.menu_admin().setVisible(true);
    }//GEN-LAST:event_bKembaliActionPerformed

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

    private void satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_satuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_satuanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCari;
    private javax.swing.JButton bEdit;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bKembali;
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
