package id.alkhidmah.maktabal_khidmah.view_util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import id.alkhidmah.maktabal_khidmah.R;

public class InfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private MaterialButton mButtonOnboard;
    private MaterialButton mButtonShare;
    private MaterialButton mButtonHubungi;
    private TextView mTextVersi;
    private ImageView mLogo;
    private MaterialButton mButtonRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mToolbar = findViewById(R.id.toolbar);
        mButtonOnboard = findViewById(R.id.button_onboard);
        mButtonShare = findViewById(R.id.button_share);
        mButtonHubungi = findViewById(R.id.button_hubungi);
        mTextVersi = findViewById(R.id.text_versi);
        mLogo = findViewById(R.id.logo);
        mButtonRate = findViewById(R.id.button_rate);

        settoolbar();
        setTitle("Informasi");
    }

    public void onboardclick(View view) {
        Intent i = new Intent(this, OnboardActivity.class);
        startActivity(i);
    }

    public void hubungiclick(View view) {
        String smsNumber = getString(R.string.admin_contact);
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        Log.d("test", String.valueOf(isWhatsappInstalled));
        if (isWhatsappInstalled) {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");
            startActivity(sendIntent);

        } else {
            Intent I =new Intent(Intent.ACTION_VIEW);
            I.setDataAndType(Uri.parse("smsto:"), "vnd.android-dir/mms-sms");
            I.putExtra("address", PhoneNumberUtils.stripSeparators(smsNumber));
            I.putExtra("sms_body","");
        }
    }

    public void rateclick(View view) {
        String package_name = "com.whatsapp";
//        String package_name = BuildConfig.APPLICATION_ID;
        Uri uri = Uri.parse("market://details?id=" + package_name);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }

    public void shareclick(View view) {
        String package_name = "com.whatsapp";
//        String package_name = BuildConfig.APPLICATION_ID;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Yuk! Download aplikasi Al-Khidmah Mobile di http://play.google.com/store/apps/details?id=" + package_name);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void settoolbar() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
