package id.alkhidmah.maktabal_khidmah.view_akun;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.adapter.TextInputLayoutAdapter;
import id.alkhidmah.maktabal_khidmah.model.Akun;
import id.alkhidmah.maktabal_khidmah.util.AppController;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;
import id.alkhidmah.maktabal_khidmah.util.SharedMethods;
import id.alkhidmah.maktabal_khidmah.view_util.InfoActivity;
import id.alkhidmah.maktabal_khidmah.view_util.PermissionActivity;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {


    @NotEmpty(message = "Harap diisi")
    private TextInputLayout mTextPassword;

    private MaterialButton mButtonMasuk;
    private MaterialButton mButtonDaftar;
    private ImageView mLogo;
    private Validator mValidator;
    Gson gson;


    @NotEmpty(message = "Harap diisi")
    private TextInputLayout mTextNohp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextPassword = findViewById(R.id.text_password);
        mButtonMasuk = findViewById(R.id.button_masuk);
        mButtonDaftar = findViewById(R.id.button_daftar);
        mLogo = findViewById(R.id.logo);
        mTextNohp = findViewById(R.id.text_nohp);
        initValidator();
        gson = new GsonBuilder().create();

    }


    private void initValidator() {
        mValidator=new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.registerAdapter(TextInputLayout.class, new TextInputLayoutAdapter());
        mValidator.setViewValidatedAction(view -> {
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError("");
                ((TextInputLayout) view).setErrorEnabled(false);
            }
        });
    }



    public void masukclick(View view) {
        mValidator.validate();
    }

    public void daftarclick(View view) {
        Intent i = new Intent(this, PermissionActivity.class);
        i.putExtra(PrefKeys.mode, PrefKeys.nothing);
        i.putExtra(PrefKeys.peran, PrefKeys.nothing);
        startActivity(i);
    }

    public void hubungiclick(View view) {
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
    }

    @Override
    public void onValidationSucceeded() {
        SharedMethods sharedMethods = new SharedMethods();
        if(!sharedMethods.checkint(this, true))return;
        login();
    }

    private void login() {
        String url = PrefKeys.LOGIN;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    JSONArray result;
                    JSONObject header, content;
                    Log.d("Cobaa", response.toString());
                    try {
                        header = new JSONObject(response);
                        String msg = header.getString(PrefKeys.msg);
                        if(msg.contentEquals("Sukses")){
                            result = header.getJSONArray(PrefKeys.result);
                            content = (JSONObject) result.get(0);
                            //Deserialkan pada class POJO
                            Akun akunku = gson.fromJson(content.toString(), Akun.class);
                            Log.d("Cobaa", akunku.nama);
                        }else{
                            Log.d(PrefKeys.ErrorTAG,"efdef");
                        }

                    } catch (Exception e) {
                        Log.d(PrefKeys.ErrorTAG,e.getMessage());
                    }
                },
                (VolleyError error) -> {

                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(PrefKeys.nohp,mTextNohp.getEditText().getText().toString());
                params.put(PrefKeys.password,mTextPassword.getEditText().getText().toString());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        errors.get(0).getView().requestFocus();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
                ((TextInputLayout) view).setErrorEnabled(true);

            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}
