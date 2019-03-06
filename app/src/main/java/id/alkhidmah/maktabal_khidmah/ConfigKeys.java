package id.alkhidmah.maktabal_khidmah;

import java.util.ArrayList;
import java.util.List;

public class ConfigKeys {
    public static final String akunid="akunid";
    public static final String nama="nama";
    public static final String nohp="nohp";
    public static final String peran="peran";
    public static final String daerahpdkr="daerahpdkr";
    public static final String rombongankrjamaah="rombongankrjamaah";
    public static final String aktif="aktif";
    public static final String lat="lat";
    public static final String lng="lng";
    public static final String keteranganpengurus="keteranganpengurus";
    public static final String daerahid="daerahid";
    public static final String namadaerah="namadaerah";
    public static final String judul="judul";
    public static final String nilai="nilai";
    public static final String waktu="waktu";
    public static final String jadwalmakanid="jadwalmakanid";
    public static final String maktabid="maktabid";
    public static final String namapemilik="namapemilik";
    public static final String nohppemilik="nohppemilik";
    public static final String alamat="alamat";
    public static final String kategori="kategori";
    public static final String kapasitas="kapasitas";
    public static final String riwayatdaerah="riwayatdaerah";
    public static final String parkirid="parkirid";
    public static final String status="status";
    public static final String rombonganid="rombonganid";
    public static final String namarombongan="namarombongan";
    public static final String koordinator="koordinator";
    public static final String namapo="namapo";
    public static final String jeniskendaraan="jeniskendaraan";
    public static final String jumlahjamaah="jumlahjamaah";
    public static final String waktuberangkat="waktuberangkat";
    public static final String waktutiba="waktutiba";
    public static final String alokasimaktab="alokasimaktab";
    public static final String alokasiparkir="alokasiparkir";


    public static final String BASE_URL = "http://128.199.245.249/";
    public static final String LOGIN = BASE_URL+"login.php";


    public static final List<String> mapkategori = new ArrayList<String>() {{
        add("Agribisnis dan pangan");
        add("Teknologi dan manufaktur");
        add("Kesehatan, obat dan kosmetik");
        add("Kerajinan dan industri kecil");
        add("Kehutanan dan lingkungan hidup");
        add("Sosial dan budaya");
        add("Kelautan dan perikanan");
        add("Pendidikan");
        add("Energi");
        add("Pariwisata");
        add("Smart city");
    }};


    public static final List<String> kategoriRombongan = new ArrayList<String>() {{
        add("VVIP");
        add("VIP");
        add("Santri");
        add("Umum");
    }};

    public static final List<String> kategoriMaktab = new ArrayList<String>() {{
        add("VVIP");
        add("VIP");
        add("Santri");
        add("Umum");
    }};

    public static final List<String> kategoriParkir = new ArrayList<String>() {{
        add("VVIP");
        add("VIP");
        add("Umum");
    }};

    public static final List<String> peranAkun = new ArrayList<String>() {{
        add("Admin");
        add("Pengurus Daerah");
        add("Koordinator Rombongan");
        add("Jamaah");
        add("Pengurus Maktab");
        add("Pengurus Dapur Umum");
        add("Pengurus Parkir");
    }};


    public static final List<String> pilihanAktivasi = new ArrayList<String>() {{
        add("Tidak aktif");
        add("Aktif");
    }};

    public static final List<String> statusMakan = new ArrayList<String>() {{
        add("Tersedia");
        add("Sudah diambil");
    }};

    public static final List<String> statusParkir = new ArrayList<String>() {{
        add("Tersedia");
        add("Penuh");
    }};

    public static final List<String> jenisKendaraan = new ArrayList<String>() {{
        add("Bus Besar");
        add("Bus Medium");
        add("Bus Micro/Elf");
        add("Mobil Pribadi");
    }};

    public static final String ErrorTAG = "ErrorTAG";
}
