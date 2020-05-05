package htkc.ebaba.ecom.vibrentech;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.UserResponse;

import htkc.ebaba.ecom.vibrentech.utils.Method;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin extends AppCompatActivity
{
    EditText mymob,mypwd;
    Button btn1;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login );
        mymob=findViewById(R.id.mob);
        mypwd=findViewById(R.id.password);
        btn1=findViewById(R.id.signin);
        Button forgot = findViewById( R.id.button_forgotPassword_login_activity );

        mProgress =new ProgressDialog(this);
        String titleId="Processing...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserLogin.this);
                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogbox_forgetpassword);
                dialog.getWindow().setLayout( ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.WRAP_CONTENT);
                final EditText editText_forgetPassword = (EditText) dialog.findViewById(R.id.editText_forget_password);
                Button buttonForgetPassword = (Button) dialog.findViewById(R.id.button_forgetPassword);
                buttonForgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String stringForgetPassword = editText_forgetPassword.getText().toString();
                        editText_forgetPassword.setError(null);
                        if (!isValidMail(stringForgetPassword) || stringForgetPassword.isEmpty()) {
                            editText_forgetPassword.requestFocus();
                            editText_forgetPassword.setError(getResources().getString(R.string.please_enter_email));
                        } else {
                            if (Method.isNetworkAvailable(UserLogin.this)) {
                                forgetPassword(stringForgetPassword);
                            } else {
                                Toast.makeText(UserLogin.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });

        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            startActivity( new Intent( UserLogin.this, HomeActivity.class ) );
            finish();
        }



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1,s2;
                s1=mymob.getText().toString();
                s2=mypwd.getText().toString();

                mProgress.show();

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl( ApiURL.BASE_URL)
                        .addConverterFactory( GsonConverterFactory.create())
                        .build();
                ApiService apiService=retrofit.create(ApiService.class);
                Call<UserResponse> call=apiService.login(s1,s2);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        //Toast.makeText(UserLogin.this, "AAA gayaa", Toast.LENGTH_SHORT).show();

                        if(!response.body().getError())
                        {
                            String muname=response.body().getUname();
                            String mmobile=response.body().getMobile();
                            String mid=response.body().getId();

                                // set the shared preference and go to home
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(muname,mmobile,mid);

                            mProgress.dismiss();
                            Toast.makeText( UserLogin.this, "Successfully Loged In...!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        }
                        else
                        {
                            mProgress.dismiss();
                            Toast.makeText( UserLogin.this, "Username Or Password is Incorrect...!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        mProgress.dismiss();
                        Toast.makeText( UserLogin.this, "Connection Failled...!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void forgetPassword(String sendEmail_forget_password) {

    }


    public void skipSignIn(View view) {
        startActivity( new Intent( getApplicationContext(), HomeActivity.class ) );
        finish();
    }

    public void btn_signup(View view) {
        startActivity( new Intent( getApplicationContext(), UserRegister.class ) );
        finish();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder( UserLogin.this);
        builder.setMessage("Are you sure want to do this ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        UserLogin.super.onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
