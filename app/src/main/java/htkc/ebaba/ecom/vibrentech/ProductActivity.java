package htkc.ebaba.ecom.vibrentech;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.List;

import htkc.ebaba.ecom.vibrentech.adapter.ProductAdapter;
import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.ProductRetResponse;
import htkc.ebaba.ecom.vibrentech.utils.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private List<ProductRetResponse> productList;
    private SliderLayout Slider;
    private ProductAdapter productAdapter;
    private boolean is_account_mode = false;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product );

        initToolbar();
        initComponent();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        productAdapter= new ProductAdapter(getApplicationContext(),productList);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager( new GridLayoutManager( this, 2 ) );
        recyclerView.addItemDecoration( new SpacingItemDecoration( 2, Tools.dpToPx( this, 8 ), true ) );
        recyclerView.setHasFixedSize( true );
        Intent intent = getIntent();
        String sub_ids = intent.getStringExtra("sub_cat_id");
        //Log.d( "bhh",sub_ids );
        //Toast.makeText( this, sub_ids, Toast.LENGTH_SHORT ).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( ApiURL.BASE_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);


        Call<List<ProductRetResponse>> call = apiService.getAllProduct(sub_ids);
        call.enqueue(new Callback<List<ProductRetResponse>>() {
            @Override
            public void onResponse(Call<List<ProductRetResponse>> call, Response<List<ProductRetResponse>> response) {
                List<ProductRetResponse> obj = response.body();

                //Toast.makeText( this, cat_ids, Toast.LENGTH_SHORT ).show();
                productList=response.body();

                productAdapter.setProductList(productList);

            }

            @Override
            public void onFailure(Call<List<ProductRetResponse>> call, Throwable t) {

            }

        });

    }


    private void initComponent() {


    }


    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimaryDark);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart_setting, menu);
        return true;
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


    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void openCart(MenuItem item) {

        if(!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            startActivity( new Intent( ProductActivity.this, UserLogin.class ) );
            finish();
        }
        else {
            startActivity( new Intent( getApplicationContext(), CartActivity.class ) );
        }
    }

    public void logoutFun(MenuItem item) {

    }
}