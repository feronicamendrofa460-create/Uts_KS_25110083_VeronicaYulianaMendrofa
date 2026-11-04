package model;

public class Barang {

    private String kodeBarang;
    private String namaBarang;
    private String kategori;
    private int stok;

    public Barang(String kodeBarang, String namaBarang,
                  String kategori, int stok) {

        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.stok = stok;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getKategori() {
        return kategori;
    }

    public int getStok() {
        return stok;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}