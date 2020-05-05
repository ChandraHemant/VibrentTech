package htkc.ebaba.ecom.vibrentech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import htkc.ebaba.ecom.vibrentech.adapter.ProductAdapter;
import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.AddCartResponse;
import htkc.ebaba.ecom.vibrentech.response.ProductDetailResponse;
import htkc.ebaba.ecom.vibrentech.utils.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductDetails extends AppCompatActivity {

    private View parent_view;
    private TextView tv_qty;
    TextView pname,p_id,price,pdescr;
    ImageView pimg,slider1,slider2,slider3;
    AppCompatButton add_cart;

    private List<ProductDetailResponse> productList;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details );
        parent_view = findViewById(R.id.parent_view);

        initToolbar();
        initComponent();

    }


    private void initComponent() {

        p_id = (TextView)findViewById(R.id.p_id);
        pname = (TextView)findViewById(R.id.fullname);
        price = (TextView)findViewById(R.id.pprice);
        pdescr = (TextView)findViewById(R.id.descr);
        pimg=(ImageView)findViewById(R.id.image);
        slider1=(ImageView)findViewById(R.id.image_1);
        slider2=(ImageView)findViewById(R.id.image_2);
        slider3=(ImageView)findViewById(R.id.image_3);
        tv_qty = (TextView) findViewById(R.id.tv_qty);

        ((FloatingActionButton) findViewById(R.id.fab_qty_sub)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(tv_qty.getText().toString());
                if (qty > 1) {
                    qty--;
                    tv_qty.setText(qty + "");
                }
            }
        });

        ((FloatingActionButton) findViewById(R.id.fab_qty_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(tv_qty.getText().toString());
                if (qty < 10) {
                    qty++;
                    tv_qty.setText(qty + "");
                }
            }
        });

        Intent intent = getIntent();
        String p_ids = intent.getStringExtra("p_id");
        //Log.d( "bhh",sub_ids );
        //Toast.makeText( this, p_ids, Toast.LENGTH_SHORT ).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( ApiURL.BASE_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);


        Call<ProductDetailResponse> call = apiService.getProductDetail(p_ids);
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {

                if(!response.body().getError()){
                    pname.setText( response.body().getP_name() );
                    pdescr.setText( response.body().getP_descr() );
                    price.setText( response.body().getP_price() );
                    p_id.setText( response.body().getP_id() );

                    // picasso ka code
                    String picname = response.body().getP_img();
                    String picpath = ApiURL.BASE_URL  + picname;
                    Log.d("Complete", picpath);

                    Glide.with(pimg)
                            .load(picpath)
                            .into(pimg);

                    Glide.with(slider1)
                            .load(ApiURL.BASE_URL+response.body().getP_slide1())
                            .into(slider1);
                    Glide.with(slider2)
                            .load(ApiURL.BASE_URL+response.body().getP_slide2())
                            .into(slider2);
                    Glide.with(slider3)
                            .load(ApiURL.BASE_URL+response.body().getP_slide3())
                            .into(slider3);
                }



            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }

        });
        ((AppCompatButton) findViewById(R.id.bt_add_to_cart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
                {
                    startActivity( new Intent( getApplicationContext(), UserLogin.class ) );
                }
                String uid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();
                String qntty = tv_qty.getText().toString();
                String pids = p_id.getText().toString();
                String pprice = price.getText().toString();
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl( ApiURL.BASE_URL)
                        .addConverterFactory( GsonConverterFactory.create())
                        .build();
                ApiService apiService=retrofit.create(ApiService.class);
                Call<AddCartResponse> call=apiService.addToCart(uid,qntty,pids,pprice);
                call.enqueue(new Callback<AddCartResponse>() {
                    @Override
                    public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {

                        if(!response.body().isError()) {
                            Snackbar.make(parent_view, "Added to Cart", Snackbar.LENGTH_SHORT).show();
                        }
                        else {

                            Toast.makeText( getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCartResponse> call, Throwable t) {

                    }
                });

            }
        });
    }



    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
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
