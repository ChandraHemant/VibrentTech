package htkc.ebaba.ecom.vibrentech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.RegisterResponse;
import htkc.ebaba.ecom.vibrentech.utils.Tools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegister extends AppCompatActivity {

    private View parent_view;
    private ProgressDialog mProgress;
    public EditText user,password,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register );

        parent_view = findViewById(android.R.id.content);
        user = findViewById(R.id.uname);
        password = findViewById(R.id.pass);
        phone = findViewById(R.id.mobile);


        mProgress =new ProgressDialog(this);
        String titleId="Processing...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        ((View) findViewById(R.id.sign_up)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1,s2,s3;
                s1=user.getText().toString();
                s2=phone.getText().toString();
                s3=password.getText().toString();

                mProgress.show();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl( ApiURL.BASE_URL)
                        .addConverterFactory( GsonConverterFactory.create())
                        .build();
                ApiService apiService=retrofit.create(ApiService.class);
                Call<RegisterResponse> call=apiService.register(s1,s2,s3);
                call.enqueue( new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(!(response.body().getError())){
                            String verify = response.body().getPhone();

                            htkc.ebaba.ecom.vibrentech.SharedPrefManager.getInstance(getApplicationContext()).userVerify(verify);

                            Snackbar.make(parent_view, "Please Verify Your Mobile No.", Snackbar.LENGTH_SHORT).show();
                            startActivity( new Intent( getApplicationContext(), VerificationActivity.class ) );
                            finish();

                        }else if(response.body().getSuccess()=="repeat" ){
                            Snackbar.make(parent_view, "Please Change Your Mobile No.", Snackbar.LENGTH_SHORT).show();

                        }else{
                            Snackbar.make(parent_view, "Something Went Wrong.", Snackbar.LENGTH_SHORT).show();

                        }

                        mProgress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {

                        mProgress.dismiss();
                        Snackbar.make(parent_view, "Connection Failed...", Snackbar.LENGTH_SHORT).show();

                    }

                } );
            }
        });

        Tools.setSystemBarColor(this);
    }


    public void signInTxt(View view) {
        startActivity( new Intent( getApplicationContext(), UserLogin.class ) );
        finish();
    }
}
