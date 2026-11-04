package view;

import dao.BarangDAO;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormBarang extends JFrame {

    JTextField txtKode, txtNama, txtStok;
    JComboBox<String> cmbKategori;

    JButton btnSimpan, btnUbah, btnHapus, btnBersihkan;

    JTable tabel;
    DefaultTableModel model;

    BarangDAO dao = new BarangDAO();

    public FormBarang() {

        setTitle("Aplikasi Inventori Barang");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // JUDUL
        JLabel judul = new JLabel(
            "DATA INVENTORI BARANG",
            JLabel.CENTER
        );

        judul.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(judul, BorderLayout.NORTH);

        // FORM INPUT
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));

        txtKode = new JTextField();
        txtNama = new JTextField();
        txtStok = new JTextField();

        cmbKategori = new JComboBox<>(
            new String[]{"Komputer", "Jaringan", "Alat Ukur"}
        );

        form.add(new JLabel("Kode Barang"));
        form.add(txtKode);

        form.add(new JLabel("Nama Barang"));
        form.add(txtNama);

        form.add(new JLabel("Kategori"));
        form.add(cmbKategori);

        form.add(new JLabel("Stok"));
        form.add(txtStok);

        // TOMBOL
        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan");

        JPanel tombol = new JPanel();

        tombol.add(btnSimpan);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnBersihkan);

        JPanel atas = new JPanel(new BorderLayout(10, 10));
        atas.add(form, BorderLayout.CENTER);
        atas.add(tombol, BorderLayout.SOUTH);

        // TABEL
        model = new DefaultTableModel(
            new String[]{"Kode", "Nama Barang", "Kategori", "Stok"}, 0
        );

        tabel = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabel);

        JPanel tengah = new JPanel(new BorderLayout(10, 10));
        tengah.add(atas, BorderLayout.NORTH);
        tengah.add(scroll, BorderLayout.CENTER);

        panel.add(tengah, BorderLayout.CENTER);

        add(panel);

        // AKSI TOMBOL
        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersihkan.addActionListener(e -> bersihkan());

        // KLIK TABEL
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int baris = tabel.getSelectedRow();

                if (baris != -1) {
                    txtKode.setText(model.getValueAt(baris, 0).toString());
                    txtNama.setText(model.getValueAt(baris, 1).toString());
                    cmbKategori.setSelectedItem(
                        model.getValueAt(baris, 2).toString()
                    );
                    txtStok.setText(model.getValueAt(baris, 3).toString());

                    txtKode.setEditable(false);
                }
            }
        });

        tampilData();
    }

    // MENAMPILKAN DATA
    private void tampilData() {

        model.setRowCount(0);

        List<Barang> daftar = dao.tampilkan();

        for (Barang b : daftar) {
            model.addRow(new Object[]{
                b.getKodeBarang(),
                b.getNamaBarang(),
                b.getKategori(),
                b.getStok()
            });
        }
    }

    // VALIDASI INPUT
    private Barang ambilDataForm() {

        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        String kategori = cmbKategori.getSelectedItem().toString();
        String stokText = txtStok.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || stokText.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Kode, nama, dan stok wajib diisi!"
            );
            return null;
        }

        if (kode.length() > 10) {
            JOptionPane.showMessageDialog(
                this, "Kode maksimal 10 karakter!"
            );
            return null;
        }

        if (nama.length() > 50) {
            JOptionPane.showMessageDialog(
                this, "Nama maksimal 50 karakter!"
            );
            return null;
        }

        try {
            int stok = Integer.parseInt(stokText);

            if (stok < 0) {
                JOptionPane.showMessageDialog(
                    this, "Stok tidak boleh kurang dari 0!"
                );
                return null;
            }

            return new Barang(kode, nama, kategori, stok);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this, "Stok harus berupa angka!"
            );
            return null;
        }
    }

    // SIMPAN
    private void simpanData() {

        Barang b = ambilDataForm();

        if (b == null) return;

        if (dao.simpan(b)) {
            JOptionPane.showMessageDialog(
                this, "Data barang berhasil disimpan!"
            );

            tampilData();
            bersihkan();

        } else {
            JOptionPane.showMessageDialog(
                this, "Gagal menyimpan data! Periksa kode barang."
            );
        }
    }

    // UBAH
    private void ubahData() {

        Barang b = ambilDataForm();

        if (b == null) return;

        if (dao.ubah(b)) {
            JOptionPane.showMessageDialog(
                this, "Data barang berhasil diubah!"
            );

            tampilData();
            bersihkan();

        } else {
            JOptionPane.showMessageDialog(
                this, "Data gagal diubah!"
            );
        }
    }

    // HAPUS
    private void hapusData() {

        String kode = txtKode.getText().trim();

        if (kode.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Pilih data barang terlebih dahulu!"
            );
            return;
        }

        int jawab = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus barang " + kode + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (jawab == JOptionPane.YES_OPTION) {

            if (dao.hapus(kode)) {
                JOptionPane.showMessageDialog(
                    this, "Data berhasil dihapus!"
                );

                tampilData();
                bersihkan();

            } else {
                JOptionPane.showMessageDialog(
                    this, "Data gagal dihapus!"
                );
            }
        }
    }

    // BERSIHKAN FORM
    private void bersihkan() {

        txtKode.setText("");
        txtNama.setText("");
        txtStok.setText("");

        cmbKategori.setSelectedIndex(0);

        txtKode.setEditable(true);
        tabel.clearSelection();
    }

    // MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FormBarang().setVisible(true);
        });
    }
}