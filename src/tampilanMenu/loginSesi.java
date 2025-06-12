/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tampilanMenu;

/**
 *
 * @author dafaa
 */

public class loginSesi {
    private static String username;
    private static String hakAkses;
    private static String idKaryawan;
    private static String namaKaryawan;

    public static void setUser(String user, String akses, String idK, String namaK) {
        username = user;
        hakAkses = akses;
        idKaryawan = idK;
        namaKaryawan = namaK;
    }

    public static String getUsername() {
        return username;
    }

    public static String getHakAkses() {
        return hakAkses;
    }

    public static String getIdKaryawan() {
        return idKaryawan;
    }

    public static String getNamaKaryawan() {
        return namaKaryawan;
    }
}
