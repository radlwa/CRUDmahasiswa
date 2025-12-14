import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static MahasiswaManager manager = new MahasiswaManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Data awal (Opsional, untuk pengujian)
        manager.tambahMahasiswa(new Mahasiswa("12345", "Budi Santoso", 85.5));
        manager.tambahMahasiswa(new Mahasiswa("67890", "Ani Wijaya", 92.0));
        manager.tambahMahasiswa(new Mahasiswa("11223", "Citra Dewi", 78.0));

        int pilihan;
        do {
            tampilkanMenu();
            System.out.print("Masukkan pilihan: ");
            if (scanner.hasNextInt()) {
                pilihan = scanner.nextInt();
                scanner.nextLine(); // Membuang newline
                prosesPilihan(pilihan);
            } else {
                System.out.println("❌ Input tidak valid. Mohon masukkan angka.");
                scanner.nextLine(); // Membuang input yang salah
                pilihan = -1;
            }
        } while (pilihan != 0);

        System.out.println("Program selesai. Sampai jumpa!");
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== MANAJEMEN DATA MAHASISWA ===");
        System.out.println("1. Tambah Data Baru (CREATE)");
        System.out.println("2. Tampilkan Seluruh Data (READ)");
        System.out.println("3. Ubah Data (UPDATE)");
        System.out.println("4. Hapus Data (DELETE)");
        System.out.println("5. Urutkan Nilai (SORTING)");
        System.out.println("6. Cari Data (SEARCHING)");
        System.out.println("0. Keluar");
        System.out.println("---------------------------------");
    }
    
    // 

    private static void prosesPilihan(int pilihan) {
        switch (pilihan) {
            case 1:
                tambahData();
                break;
            case 2:
                tampilkanData(manager.lihatSemuaMahasiswa(), "DATA SEMUA MAHASISWA");
                break;
            case 3:
                ubahData();
                break;
            case 4:
                hapusData();
                break;
            case 5:
                sortingData();
                break;
            case 6:
                cariData();
                break;
            case 0:
                break;
            default:
                System.out.println("❌ Pilihan tidak valid!");
        }
    }

    private static void tampilkanData(List<Mahasiswa> list, String judul) {
        System.out.println("\n=== " + judul + " ===");
        if (list.isEmpty()) {
            System.out.println("Data mahasiswa kosong.");
            return;
        }

        System.out.println("+------------+----------------------+--------+");
        System.out.println("| NIM        | Nama                 | Nilai  |");
        System.out.println("+------------+----------------------+--------+");
        for (Mahasiswa mhs : list) {
            System.out.println(mhs);
        }
        System.out.println("+------------+----------------------+--------+");
    }

    // Operasi detail lainnya...
    private static void tambahData() {
        System.out.println("\n--- TAMBAH DATA ---");
        System.out.print("Masukkan NIM: ");
        String nim = scanner.nextLine();
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Nilai: ");
        
        try {
            double nilai = scanner.nextDouble();
            scanner.nextLine(); // Membuang newline
            Mahasiswa mhsBaru = new Mahasiswa(nim, nama, nilai);
            if (manager.tambahMahasiswa(mhsBaru)) {
                System.out.println("✅ Data mahasiswa berhasil ditambahkan!");
            } else {
                System.out.println("❌ Gagal: NIM sudah terdaftar!");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Input nilai tidak valid. Harap masukkan angka.");
            scanner.nextLine(); // Membuang input yang salah
        }
    }

    private static void ubahData() {
        System.out.println("\n--- UBAH DATA ---");
        System.out.print("Masukkan NIM Mahasiswa yang akan diubah: ");
        String nim = scanner.nextLine();
        
        Mahasiswa mhsLama = manager.cariMahasiswaByNim(nim);
        if (mhsLama == null) {
            System.out.println("❌ Gagal: NIM tidak ditemukan!");
            return;
        }
        
        System.out.println("Data saat ini: " + mhsLama.toString());
        
        System.out.print("Masukkan Nama Baru (" + mhsLama.getNama() + "): ");
        String namaBaru = scanner.nextLine();
        // Cek jika user hanya tekan Enter, pakai nama lama
        if (namaBaru.isEmpty()) {
            namaBaru = mhsLama.getNama();
        }
        
        System.out.print("Masukkan Nilai Baru (" + mhsLama.getNilai() + "): ");
        String nilaiInput = scanner.nextLine();
        double nilaiBaru = mhsLama.getNilai(); // Nilai default
        
        if (!nilaiInput.isEmpty()) {
            try {
                nilaiBaru = Double.parseDouble(nilaiInput);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Nilai baru tidak valid, menggunakan nilai lama.");
            }
        }
        
        if (manager.ubahMahasiswa(nim, namaBaru, nilaiBaru)) {
            System.out.println("✅ Data mahasiswa berhasil diubah!");
        } else {
            System.out.println("❌ Gagal mengubah data.");
        }
    }
    
    private static void hapusData() {
        System.out.println("\n--- HAPUS DATA ---");
        System.out.print("Masukkan NIM Mahasiswa yang akan dihapus: ");
        String nim = scanner.nextLine();

        if (manager.hapusMahasiswa(nim)) {
            System.out.println("✅ Data mahasiswa dengan NIM " + nim + " berhasil dihapus!");
        } else {
            System.out.println("❌ Gagal: NIM tidak ditemukan!");
        }
    }
    
    private static void sortingData() {
        List<Mahasiswa> listTerurut = manager.urutkanBerdasarkanNilai();
        tampilkanData(listTerurut, "DATA MAHASISWA TERURUT (NILAI TERTINGGI KE TERENDAH)");
    }
    
    private static void cariData() {
        System.out.println("\n--- CARI DATA ---");
        System.out.print("Masukkan Kata Kunci (NIM atau Nama): ");
        String keyword = scanner.nextLine();

        List<Mahasiswa> hasilCari = manager.cariMahasiswa(keyword);
        
        if (hasilCari.isEmpty()) {
            System.out.println("🔎 Tidak ada data yang cocok dengan kata kunci '" + keyword + "'.");
        } else {
            tampilkanData(hasilCari, "HASIL PENCARIAN DENGAN KATA KUNCI: " + keyword);
        }
    }
}
