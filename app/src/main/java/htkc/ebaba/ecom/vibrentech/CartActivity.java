package htkc.ebaba.ecom.vibrentech;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import htkc.ebaba.ecom.vibrentech.adapter.CartAdapter;
import htkc.ebaba.ecom.vibrentech.adapter.CategoryAdapter;
import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.CartResponse;
import htkc.ebaba.ecom.vibrentech.response.CategoryRetResponse;
import htkc.ebaba.ecom.vibrentech.response.UpdateCartResponse;
import htkc.ebaba.ecom.vibrentech.utils.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    private List<CartResponse> cartList;
    private CartAdapter cartAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initToolbar();
        initComponent();

        Intent intent = getIntent();
        String cart_id = intent.getStringExtra("cart_id");
        String quantity = intent.getStringExtra("qntty");


    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_10);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter= new CartAdapter(getApplicationContext(),cartList);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        String uid = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( ApiURL.BASE_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<CartResponse>> call = apiService.getCart(uid);
        call.enqueue( new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                cartList = response.body();

                cartAdapter.setProductList(cartList);

            }

            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                Toast.makeText( getApplicationContext(),"Connection Failed...",Toast.LENGTH_LONG ).show();
            }
        } );


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
