package id.alkhidmah.maktabal_khidmah.view_akun;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.model.Akun;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;

public class DataAkunActivity extends AppCompatActivity {

    private MaterialButton mButtonLokasi;
    private TextInputLayout mTextKeterangan;
    private MaterialButton mButtonSimpan;
    private MaterialButton mButtonHapus;
    private Spinner mSpinnerPeran;
    private Toolbar mToolbar;
    private TextInputLayout mTextDaerah;
    private TextInputLayout mTextPassword;
    private TextView mTextWaiting;
    private SwitchMaterial mSwitchAktivasi;
    private TextInputLayout mTextNama;
    private TextInputLayout mTextNohp;
    private TextView mStatus;
    private int mode, myperan, myakunid;
    private LinearLayout mStatusLayout;

    private int target_akunid, target_daerahpdkr, target_rombongankrjamaah;

    private Akun target_akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_akun);

        mButtonLokasi = findViewById(R.id.button_lokasi);
        mTextKeterangan = findViewById(R.id.text_keterangan);
        mButtonSimpan = findViewById(R.id.button_simpan);
        mButtonHapus = findViewById(R.id.button_hapus);
        mSpinnerPeran = findViewById(R.id.spinner_peran);
        mToolbar = findViewById(R.id.toolbar);
        mTextDaerah = findViewById(R.id.text_daerah);
        mTextPassword = findViewById(R.id.text_password);
        mTextWaiting = findViewById(R.id.text_waiting);
        mSwitchAktivasi = findViewById(R.id.switch_aktivasi);
        mTextNama = findViewById(R.id.text_nama);
        mTextNohp = findViewById(R.id.text_nohp);
        mStatus = findViewById(R.id.status);
        mStatusLayout = findViewById(R.id.status_layout);

        setlayout();
    }

    private void setlayout() {
        target_akunid = -1;
        mode = Objects.requireNonNull(getIntent().getExtras()).getInt(PrefKeys.mode);
        myperan = Prefs.getInt(PrefKeys.peran, 0);
        myakunid = Prefs.getInt(PrefKeys.akunid, 0);
        target_akunid = Objects.requireNonNull(getIntent().getExtras()).getInt(PrefKeys.akunid);

        switch (mode) {
            case PrefKeys.mode_create:
//                buat baru/daftar
                mButtonLokasi.setVisibility(View.GONE);
                mButtonHapus.setVisibility(View.GONE);

                if(myperan==PrefKeys.peran_pdaerah){
                    mTextDaerah.setClickable(false);
                    Objects.requireNonNull(mTextDaerah.getEditText()).setText(Prefs.getString(PrefKeys.daerahpdkr_nama, ""));
                    target_daerahpdkr = Prefs.getInt(PrefKeys.daerahpdkr, 0);
                }else{
                    mTextDaerah.setVisibility(View.GONE);
                }

                mTextKeterangan.setVisibility(View.GONE);

                mTextWaiting.setVisibility(View.GONE);
                mStatusLayout.setVisibility(View.GONE);

                if(myperan==PrefKeys.peran_pdaerah || myperan==PrefKeys.peran_admin) {
                    mSwitchAktivasi.setChecked(true);
                }
                else{
                    mSwitchAktivasi.setVisibility(View.GONE);
                }

                setTitle("Daftar akun");
            case PrefKeys.mode_update:
                getdata(target_akunid);
//                edit
                if(myakunid==target_akunid){
                    mButtonLokasi.setVisibility(View.GONE);
                }

                if(myperan==PrefKeys.peran_admin){
                    if(myakunid==target_akunid){
                        mTextKeterangan.setVisibility(View.GONE);
                        mTextDaerah.setVisibility(View.GONE);
                    }else{
                        if(target_akun.peran==PrefKeys.peran_pdaerah || target_akun.peran==PrefKeys.peran_krombongan){
                            Objects.requireNonNull(mTextDaerah.getEditText()).setText(target_akun.daerahpdkr_nama);
                            target_daerahpdkr = target_akun.daerahpdkr;
                        }
                        if(target_akun.peran==PrefKeys.peran_pdaerah ||target_akun.peran==PrefKeys.peran_pdapur || target_akun.peran==PrefKeys.peran_pmaktab || target_akun.peran==PrefKeys.peran_pparkir){
                            Objects.requireNonNull(mTextDaerah.getEditText()).setText(target_akun.keteranganpengurus);
                        }
                    }
                }

                if(myperan==PrefKeys.peran_pdaerah){
                    if(myakunid==target_akunid){
                        Objects.requireNonNull(mTextKeterangan.getEditText()).setText(Prefs.getString(PrefKeys.keteranganpengurus, ""));
                        Objects.requireNonNull(mTextDaerah.getEditText()).setText(Prefs.getString(PrefKeys.daerahpdkr_nama, ""));
                        target_daerahpdkr = Prefs.getInt(PrefKeys.daerahpdkr, 0);
                    }else{

                    }
                }


                if(!(myperan==PrefKeys.peran_pdaerah || myperan==PrefKeys.peran_admin)) {
                    mSwitchAktivasi.setVisibility(View.GONE);
                }

                mTextWaiting.setVisibility(View.GONE);
                mStatusLayout.setVisibility(View.GONE);

                setTitle("Edit akun");
            case PrefKeys.mode_read:
                getdata(target_akunid);
//                baca/view
                mButtonLokasi.setVisibility(View.GONE);
                mButtonHapus.setVisibility(View.GONE);

                mTextKeterangan.setVisibility(View.GONE);
                mTextDaerah.setVisibility(View.GONE);

                mTextWaiting.setVisibility(View.GONE);
                mStatusLayout.setVisibility(View.GONE);

                mSwitchAktivasi.setVisibility(View.GONE);
                setTitle("Lihat akun");
        }
    }

    private void getdata(int targetakunid) {
//        target_akun
    }

    public void pilihdaerah(View view) {
    }

    public void lihatlokasi(View view) {
    }

    public void hubungiclick(View view) {
    }

    public void simpanclick(View view) {
    }

    public void hapusclick(View view) {
    }
}
