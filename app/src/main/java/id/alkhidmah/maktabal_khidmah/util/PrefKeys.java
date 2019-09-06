package id.alkhidmah.maktabal_khidmah.util;

import java.util.ArrayList;
import java.util.List;


public class PrefKeys {
    public static final String result="result";
    public static final String msg="msg";
    public static final String password="password";
    public static final String permission="permission";
    public static final String mode="mode";
    public static final String tutorial="tutorial";
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
    public static final String daerahpdkr_nama="daerahpdkr_nama";
    public static final String rombongankrjamaah_nama="rombongankrjamaah_nama";
    public static final String riwayatdaerah_nama="riwayatdaerah_nama";
    public static final String alokasimaktab_nama="alokasimaktab_nama";
    public static final String alokasiparkir_nama="alokasiparkir_nama";
    public static final String koordinator_nama="koordinator_nama";

    public static final int nothing=-1;
    public static final int mode_create=0;
    public static final int mode_read=1;
    public static final int mode_update=2;
    public static final int mode_delete=3;

    public static final int kategori_vvip=0;
    public static final int kategori_vip=1;
    public static final int kategori_santri=2;
    public static final int kategori_umum=3;


    public static final int peran_admin=0;
    public static final int peran_pdaerah=1;
    public static final int peran_pmaktab=2;
    public static final int peran_pparkir=3;
    public static final int peran_pdapur=4;
    public static final int peran_krombongan=5;
    public static final int peran_jamaah=6;

    public static final int status_nonaktif=0;
    public static final int status_aktif=1;

    public static final int kendaraan_bbesar=0;
    public static final int kendaraan_bmedium=1;
    public static final int kendaraan_bmikro=2;
    public static final int kendaraan_mpribadi=3;



    public static final String BASE_URL = "http://128.199.245.249:5000/";
    public static final String LOGIN = BASE_URL+"login.php";


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
        add("Pengurus Maktab");
        add("Pengurus Parkir");
        add("Pengurus Dapur Umum");
        add("Koordinator Rombongan");
        add("Jamaah");

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
        add("Bus Mikro/Elf");
        add("Mobil Pribadi");
    }};

    public static final String ErrorTAG = "ErrorTAG";

    public static final String[] headers = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;


}
