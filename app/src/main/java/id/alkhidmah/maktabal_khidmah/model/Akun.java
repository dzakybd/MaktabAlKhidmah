package id.alkhidmah.maktabal_khidmah.model;

import com.google.gson.annotations.SerializedName;

import id.alkhidmah.maktabal_khidmah.util.PrefKeys;

public class Akun {
    @SerializedName(PrefKeys.akunid)
    public int akunid;
    @SerializedName(PrefKeys.nama)
    public String nama;
    @SerializedName(PrefKeys.nohp)
    public String nohp;
    @SerializedName(PrefKeys.password)
    public String password;
    @SerializedName(PrefKeys.peran)
    public int peran;
    @SerializedName(PrefKeys.daerahpdkr)
    public int daerahpdkr;
    @SerializedName(PrefKeys.rombongankrjamaah)
    public int rombongankrjamaah;
    @SerializedName(PrefKeys.aktif)
    public boolean aktif;
    @SerializedName(PrefKeys.lat)
    public double lat;
    @SerializedName(PrefKeys.lng)
    public double lng;
    @SerializedName(PrefKeys.keteranganpengurus)
    public String keteranganpengurus;
    @SerializedName(PrefKeys.daerahpdkr_nama)
    public int daerahpdkr_nama;
    @SerializedName(PrefKeys.rombongankrjamaah_nama)
    public String rombongankrjamaah_nama;

}
