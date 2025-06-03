/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;
import java.sql.*;

/**
 *
 * @author dafaa
 */
public class koneksi {
    public Connection koneksi;
    public Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil Terkoneksi");
        }
        catch (ClassNotFoundException ex){
            System.out.println("Gagal Terkoneksi " + ex);
        }
        String url = "jdbc:mysql://localhost:3306/inventaris";
        try{
            koneksi = DriverManager.getConnection(url, "root", "");
            System.out.println("Berhasil Terkoneksi Ke Database");
        }
        catch (SQLException ex){
            System.out.println("Gagal Terkoneksi ke Database" + ex);
        }
        return koneksi;
    }
}