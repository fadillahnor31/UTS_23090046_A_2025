package com.mycompany.p_23090046_a_2025;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;

public class CRUD_23090046_A_2025 {

    private static final String URI = "mongodb://localhost:27017";
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public CRUD_23090046_A_2025() {
        mongoClient = MongoClients.create(URI);
        database = mongoClient.getDatabase("mahasiswa_db");
        collection = database.getCollection("data_mahasiswa");
        System.out.println("Terhubung ke MongoDB: Mahasiswa Database");
    }

    // Fungsi untuk menambah data mahasiswa
    public void addMahasiswa(String nim, String nama, String kelas, String prodi) {
        Document mahasiswa = new Document("nim", nim)
                .append("nama", nama)
                .append("kelas", kelas)
                .append("prodi", prodi);
        collection.insertOne(mahasiswa);
        System.out.println("Data mahasiswa berhasil ditambahkan.");
    }

    // Fungsi untuk menampilkan semua data mahasiswa
    public void showAllMahasiswa() {
        FindIterable<Document> documents = collection.find();
        documents.forEach(doc -> System.out.println(doc.toJson()));
    }

    // Fungsi untuk mengupdate data mahasiswa
    public void updateMahasiswa(String nim, String field, Object value) {
        Bson filter = Filters.eq("nim", nim);
        Bson update = Updates.set(field, value);
        collection.updateOne(filter, update);
        System.out.println("Data mahasiswa berhasil diupdate.");
    }

    // Fungsi untuk menghapus data mahasiswa
    public void deleteMahasiswa(String nim) {
        Bson filter = Filters.eq("nim", nim);
        collection.deleteOne(filter);
        System.out.println("Data mahasiswa berhasil dihapus.");
    }

    // Fungsi untuk mencari data mahasiswa berdasarkan nama
    public void searchMahasiswa(String keyword) {
        Bson filter = Filters.regex("nama", keyword, "i");
        FindIterable<Document> results = collection.find(filter);
        results.forEach(doc -> System.out.println(doc.toJson()));
    }

    public static void main(String[] args) {
        CRUD_23090046_A_2025 app = new CRUD_23090046_A_2025();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu Data Mahasiswa ---");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Tampilkan Semua Mahasiswa");
            System.out.println("3. Update Mahasiswa");
            System.out.println("4. Hapus Mahasiswa");
            System.out.println("5. Cari Mahasiswa");
            System.out.println("6. Keluar");
            System.out.print("Pilih: ");
            int pilih = sc.nextInt();
            sc.nextLine(); // membersihkan newline

            switch (pilih) {
                case 1:
                    // Tambah data mahasiswa
                    System.out.print("NIM: ");
                    String nim = sc.nextLine();
                    System.out.print("Nama: ");
                    String nama = sc.nextLine();
                    System.out.print("Kelas: ");
                    String kelas = sc.nextLine();
                    System.out.print("Prodi: ");
                    String prodi = sc.nextLine();
                    app.addMahasiswa(nim, nama, kelas, prodi);
                    break;

                case 2:
                    // Tampilkan semua mahasiswa
                    app.showAllMahasiswa();
                    break;

                case 3:
                    // Update data mahasiswa
                    System.out.print("NIM yang diupdate: ");
                    String nimUpdate = sc.nextLine();
                    System.out.print("Field yang diupdate (nama/kelas/prodi): ");
                    String field = sc.nextLine();
                    System.out.print("Nilai baru: ");
                    String nilai = sc.nextLine();
                    Object value = nilai;
                    app.updateMahasiswa(nimUpdate, field, value);
                    break;

                case 4:
                    // Hapus data mahasiswa
                    System.out.print("NIM yang dihapus: ");
                    String nimHapus = sc.nextLine();
                    app.deleteMahasiswa(nimHapus);
                    break;

                case 5:
                    // Cari data mahasiswa
                    System.out.print("Keyword nama mahasiswa: ");
                    String keyword = sc.nextLine();
                    app.searchMahasiswa(keyword);
                    break;

                case 6:
                    // Keluar dari program
                    System.out.println("Keluar dari program.");
                    sc.close();
                    return;

                default:
                    // Jika input tidak valid
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
