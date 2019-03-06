package id.alkhidmah.maktabal_khidmah;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.pixplicity.easyprefs.library.Prefs;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.alkhidmah.maktabal_khidmah.view_akun.LoginActivity;

public class SharedMethods {
    public void nointalert(final Context ctx) {
        new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Internet mati")
                .setContentText("Mohon menghidupkan internet")
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                        ctx.startActivity(intent);
                    }
                })
                .show();
    }

    public boolean checkint(Context ctx){
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity.getActiveNetworkInfo() != null) {
            if (connectivity.getActiveNetworkInfo().isConnected()) return true;
            else {
                nointalert(ctx);
                return false;
            }
        }else {
            nointalert(ctx);
            return false;
        }
    }

    public boolean checkdaftar(final Context ctx) {
        if (Prefs.getString(ConfigKeys.nama, "").isEmpty()) {
            new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Anda belum login")
                    .setContentText("Mohon login atau mendaftar akun dahulu")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(ctx, LoginActivity.class);
                            ctx.startActivity(intent);
                        }
                    })
                    .show();
            return false;
        }
        else return true;
    }
}
