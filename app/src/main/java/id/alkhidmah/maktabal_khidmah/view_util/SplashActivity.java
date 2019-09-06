package id.alkhidmah.maktabal_khidmah.view_util;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.request.StringRequest;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.util.AppController;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;
import id.alkhidmah.maktabal_khidmah.view_akun.LoginActivity;
import id.alkhidmah.maktabal_khidmah.view_menu.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        StartAnimations();

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha_on);
        anim.reset();
        LinearLayout l=findViewById(R.id.mainview);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate_on);
        anim.reset();
        ImageView iv = findViewById(R.id.logosplash);
        iv.clearAnimation();
        iv.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (Prefs.getString(PrefKeys.nama, "").isEmpty()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            move();
                        }
                    }, 1500);
                }else{
                    getdata();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void move() {
        if(!Prefs.getBoolean(PrefKeys.tutorial,false)){
            Intent i = new Intent(this, OnboardActivity.class);
            startActivity(i);
        }

        if(!Prefs.getBoolean(PrefKeys.permission,false)){
            Intent i = new Intent(this, PermissionActivity.class);
            startActivity(i);
        }

        Intent i = new Intent(this, Prefs.getString(PrefKeys.nama, "").isEmpty() ? LoginActivity.class : MainActivity.class);
        startActivity(i);
        finish();
    }

    private void getdata() {
        String url = PrefKeys.LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jObject = new JSONObject(response);
//                            Prefs.putInt(PrefKeys.idakun, Integer.parseInt(jObject.getString(PrefKeys.idakun)));
//                            Prefs.putString(PrefKeys.email, jObject.getString(PrefKeys.email));
//                            Prefs.putBoolean(PrefKeys.isblock, jObject.getString(PrefKeys.isblock).contentEquals("1") );
//                            Prefs.putBoolean(PrefKeys.isadmin, jObject.getString(PrefKeys.isadmin).contentEquals("1") );
//                            Prefs.putString(PrefKeys.nohp, jObject.getString(PrefKeys.nohp));
//                            Prefs.putString(PrefKeys.nama, jObject.getString(PrefKeys.nama));
//                            Prefs.putString(PrefKeys.foto, jObject.getString(PrefKeys.foto));
//                            Prefs.putString(PrefKeys.nomorid, jObject.getString(PrefKeys.nomorid));
//                            Prefs.putString(PrefKeys.password, jObject.getString(PrefKeys.password));
//                            Prefs.putString(PrefKeys.alamat, jObject.getString(PrefKeys.alamat));
//                            Prefs.putString(PrefKeys.pekerjaan, jObject.getString(PrefKeys.pekerjaan));
//                            Prefs.putString(PrefKeys.tanggalbuat, jObject.getString(PrefKeys.tanggalbuat));
//                            Prefs.putString(PrefKeys.fotothumb, jObject.getString(PrefKeys.fotothumb));
//                            Prefs.putInt(PrefKeys.jumlahkreasi, Integer.parseInt(jObject.getString(PrefKeys.jumlahkreasi)));
                        move();
                    } catch (Exception e) {
                        Log.d(PrefKeys.ErrorTAG,e.getMessage());
                    }
                },
                error -> move()){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(PrefKeys.nohp,Prefs.getString(PrefKeys.nohp,""));
                params.put(PrefKeys.peran,Prefs.getString(PrefKeys.peran,""));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
