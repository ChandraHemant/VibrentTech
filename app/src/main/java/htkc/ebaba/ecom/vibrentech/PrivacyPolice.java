package htkc.ebaba.ecom.vibrentech;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import htkc.ebaba.ecom.vibrentech.utils.Constant_Api;
import htkc.ebaba.ecom.vibrentech.utils.Method;


public class PrivacyPolice extends AppCompatActivity {

    public Toolbar toolbar;
    private TextView privacy_policy_textview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar_privacy_policy);
        toolbar.setTitle(getResources().getString(R.string.privacy_policy));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        privacy_policy_textview = (TextView) findViewById(R.id.textview_privacy_policy);
        if(Method.isNetworkAvailable(PrivacyPolice.this))
            privacy_policy_textview.setText( Html.fromHtml( Constant_Api.aboutUsList.getApp_privacy_policy() ) );
        else {
            Toast.makeText(this,getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
