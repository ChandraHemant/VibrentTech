package htkc.ebaba.ecom.vibrentech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.ResendResponse;
import htkc.ebaba.ecom.vibrentech.response.VerifyResponse;
import htkc.ebaba.ecom.vibrentech.utils.Tools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerificationActivity extends AppCompatActivity {

    private TextInputEditText code;
    private AppCompatButton go,rsend;
    private ProgressDialog mProgress;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        initToolbar();

        code = findViewById( R.id.verify );
        go = findViewById( R.id.submit );
        rsend = findViewById( R.id.resend );

        mProgress =new ProgressDialog(this);
        String titleId="Processing...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        rsend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = SharedPrefManager.getInstance(getApplicationContext()).verifyNo().getMobile();
                mProgress.show();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl( ApiURL.BASE_URL)
                        .addConverterFactory( GsonConverterFactory.create())
                        .build();
                ApiService apiService=retrofit.create(ApiService.class);
                Call<ResendResponse> call1=apiService.resend(mobile);
                call1.enqueue( new Callback<ResendResponse>() {
                    @Override
                    public void onResponse(Call<ResendResponse> call, Response<ResendResponse> response) {
                        if(!response.body().getError()) {

                            mProgress.dismiss();
                            Toast.makeText( getApplicationContext(), "Please Check Your Inbox...!", Toast.LENGTH_LONG ).show();

                        }
                        else{
                            mProgress.dismiss();
                            Toast.makeText( getApplicationContext(), "Please Check Your Internet Connection...!", Toast.LENGTH_LONG ).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResendResponse> call, Throwable t) {
                        mProgress.dismiss();
                        Toast.makeText( getApplicationContext(), "Connection Failed...!", Toast.LENGTH_LONG ).show();

                    }
                } );
            }
        } );

        go.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = code.getText().toString();
                String mobile = SharedPrefManager.getInstance(getApplicationContext()).verifyNo().getMobile();
                mProgress.show();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl( ApiURL.BASE_URL)
                        .addConverterFactory( GsonConverterFactory.create())
                        .build();
                ApiService apiService=retrofit.create(ApiService.class);
                Call<VerifyResponse> call=apiService.verify(check,mobile);
                call.enqueue( new Callback<VerifyResponse>() {
                    @Override
                    public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                        if(!response.body().getError()) {
                            String muname = response.body().getUname();
                            String mmobile = response.body().getMobile();
                            String mid = response.body().getId();

                            // set the shared preference and go to home
                            SharedPrefManager.getInstance( getApplicationContext() ).userLogin( muname, mmobile, mid );

                            mProgress.dismiss();
                            Toast.makeText( getApplicationContext(), "Successfully Loged In...!", Toast.LENGTH_LONG ).show();
                            startActivity( new Intent( getApplicationContext(), HomeActivity.class ) );
                        }
                        else{
                            mProgress.dismiss();
                            Toast.makeText( getApplicationContext(), "Please Confirm Your OTP...!", Toast.LENGTH_LONG ).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyResponse> call, Throwable t) {

                    }
                } );
            }
        } );
    }

    private void initToolbar() {
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_900);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
