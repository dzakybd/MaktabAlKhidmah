package id.alkhidmah.maktabal_khidmah.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SharedMethods {
    public void nointalert(final Context ctx) {
        new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Internet mati")
                .setContentText("Mohon menghidupkan internet")
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                    ctx.startActivity(intent);
                })
                .show();
    }


    private void nogpsalert(final Context ctx) {
        new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("GPS mati")
                .setContentText("Mohon aktifkan GPS Anda")
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    ctx.startActivity(intent);
                })
                .show();
    }

    public boolean checkgps(Context ctx, boolean showdialog){
        final LocationManager manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }else{
            if(showdialog){
                nogpsalert(ctx);
            }
            return false;
        }
    }

    public boolean checkint(Context ctx, boolean showdialog){
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity.getActiveNetworkInfo() != null) {
            if (connectivity.getActiveNetworkInfo().isConnected()) return true;
            else {
                if(showdialog){
                    nointalert(ctx);
                }
                return false;
            }
        }else {
            if(showdialog){
                nointalert(ctx);
            }
            return false;
        }
    }

}
