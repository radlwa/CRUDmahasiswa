public class Mahasiswa {
    private String nim;
    private String nama;
    private double nilai;

    // Konstruktor
    public Mahasiswa(String nim, String nama, double nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
    }

    // Getter
    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public double getNilai() {
        return nilai;
    }

    // Setter (Hanya untuk Nama dan Nilai, NIM biasanya tidak diubah)
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }

    // Override toString untuk representasi objek yang mudah dibaca
    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-6.2f |", nim, nama, nilai);
    }
}
