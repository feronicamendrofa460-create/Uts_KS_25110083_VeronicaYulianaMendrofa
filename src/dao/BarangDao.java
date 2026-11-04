package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Barang;

public class BarangDAO {

    // SIMPAN DATA
    public boolean simpan(Barang b) {
        String sql = "INSERT INTO barang VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setInt(4, b.getStok());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menyimpan: " + e.getMessage());
            return false;
        }
    }

    // MENAMPILKAN DATA
    public List<Barang> tampilkan() {
        List<Barang> daftar = new ArrayList<>();

        String sql = "SELECT * FROM barang";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Barang b = new Barang(
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getInt("stok")
                );

                daftar.add(b);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data: " + e.getMessage());
        }

        return daftar;
    }

    // MENGUBAH DATA
    public boolean ubah(Barang b) {
        String sql = "UPDATE barang SET nama_barang=?, "
                   + "kategori=?, stok=? WHERE kode_barang=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getNamaBarang());
            ps.setString(2, b.getKategori());
            ps.setInt(3, b.getStok());
            ps.setString(4, b.getKodeBarang());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal mengubah: " + e.getMessage());
            return false;
        }
    }

    // MENGHAPUS DATA
    public boolean hapus(String kode) {
        String sql = "DELETE FROM barang WHERE kode_barang=?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kode);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus: " + e.getMessage());
            return false;
        }
    }
}