package id.alkhidmah.maktabal_khidmah.view_util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;

public class PermissionActivity extends AppCompatActivity {
    private MultiplePermissionsListener allPermissionsListener;
    private PermissionRequestErrorListener errorListener;
    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        contentView=findViewById(android.R.id.content);
        createPermissionListeners();
    }


    private void createPermissionListeners() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener = new SampleMultiplePermissionListener(this);

        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(contentView,
                                R.string.all_permissions_denied_feedback)
                                .withOpenSettingsButton("SETTINGS")
                                .build());


        errorListener = new SampleErrorListener();
    }


    public void izinclick(View view) {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(allPermissionsListener)
                .withErrorListener(errorListener)
                .check();
    }

    private class SampleMultiplePermissionListener implements MultiplePermissionsListener {
        public SampleMultiplePermissionListener(PermissionActivity permissionActivity) {

        }

        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
            if(report.areAllPermissionsGranted()){
                Prefs.putBoolean(PrefKeys.permission,true);
                finish();
            }
        }


        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
            showPermissionRationale(token);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void showPermissionRationale(final PermissionToken token) {
            new AlertDialog.Builder(PermissionActivity.this).setTitle(R.string.permission_rationale_title)
                    .setMessage(R.string.permission_rationale_message)
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            token.cancelPermissionRequest();
                        }
                    })
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            token.continuePermissionRequest();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            token.cancelPermissionRequest();
                        }
                    })
                    .show();
        }
    }

    private class SampleErrorListener implements PermissionRequestErrorListener {
        @Override
        public void onError(DexterError error) {
            Log.e("Dexter", "There was an error: " + error.toString());
        }
    }
}