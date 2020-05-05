package htkc.ebaba.ecom.vibrentech;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import htkc.ebaba.ecom.vibrentech.adapter.CategoryAdapter;
import htkc.ebaba.ecom.vibrentech.adapter.MainAdapter;
import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.CategoryRetResponse;
import htkc.ebaba.ecom.vibrentech.response.SliderRetResponse;
import htkc.ebaba.ecom.vibrentech.utils.Method;
import htkc.ebaba.ecom.vibrentech.utils.RecyclerTouchListener;
import htkc.ebaba.ecom.vibrentech.utils.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;
    private List<CategoryRetResponse> categoryList;
    private CategoryAdapter categoryAdapter;
    private DrawerLayout drawer;
    private View navigation_header;
    private TextView logout;
    private boolean is_account_mode = false;

    private RecyclerView recyclerView;
    private SliderLayout sliderLayout;
    private RecyclerView recyclerView1;
    public static MainAdapter mainAdapter;

    private String[] name = {"Home", "Latest", "Category", "Author", "Download", "Favorite",
            "Share App", "Rate App", "More App", "About Us", "Privacy Policy", "Profile", "Logout"
    };

    private Integer[] image =
            {
                    R.drawable.ic_home, R.drawable.ic_latest, R.drawable.ic_category, R.drawable.ic_author,
                    R.drawable.ic_download_slider, R.drawable.ic_favorite, R.drawable.ic_share_slider, R.drawable.ic_rate,
                    R.drawable.ic_more, R.drawable.ic_about, R.drawable.ic_parivacy, R.drawable.ic_profile,
                    R.drawable.ic_logout
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );

        initToolbar();
        initComponent();
        initNavigationMenu();


        recyclerView1 = findViewById( R.id.recyclerView_main );
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setFocusable(false);

        mainAdapter = new MainAdapter(MainActivity.this, name, image);
        recyclerView1.setAdapter(mainAdapter);

        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this, recyclerView1, new MainAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                navigation(position);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        final HashMap<String,String> url_maps = new HashMap<String, String>();

        sliderLayout = findViewById(R.id.imageSlider);

        sliderLayout.setIndicatorAnimation( IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation( SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( ApiURL.BASE_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<SliderRetResponse>> call = apiService.getSlider();
        call.enqueue(new Callback<List<SliderRetResponse>>() {
            @Override
            public void onResponse(Call<List<SliderRetResponse>> call, Response<List<SliderRetResponse>> response) {

                List<SliderRetResponse> heros = response.body();
                //ArrayList<String> mylist = new ArrayList<String>();
                HashMap<String, String> url_maps = new HashMap<String, String>();
                for (SliderRetResponse h : heros) {
                    DefaultSliderView sliderView = new DefaultSliderView( getApplicationContext() );

                    String dbUri = ApiURL.BASE_URL + h.getSimage();
                    sliderView.setImageUrl( dbUri );

                    sliderView.setImageScaleType( ImageView.ScaleType.CENTER_CROP );
                    sliderView.setDescription( "" + h.getSname() );


                    sliderView.setOnSliderClickListener( new SliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(SliderView sliderView) {
                            //Toast.makeText(getContext(), "This is slider " +h.getName(), Toast.LENGTH_SHORT).show();

                        }
                    } );

                    //at last add this view in your layout :
                    sliderLayout.addSliderView( sliderView );


                }
            }

            @Override
            public void onFailure(Call<List<SliderRetResponse>> call, Throwable t) {

            }

        });

    }

    private void initComponent() {

        recyclerView = findViewById( R.id.recyclerView );

        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );
        categoryAdapter = new CategoryAdapter( getApplicationContext(), categoryList );
        recyclerView.setAdapter( categoryAdapter );
        recyclerView.setLayoutManager( new GridLayoutManager( this, 2 ) );
        recyclerView.addItemDecoration( new SpacingItemDecoration( 2, Tools.dpToPx( this, 8 ), true ) );
        recyclerView.setHasFixedSize( true );
        recyclerView.setNestedScrollingEnabled( false );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( ApiURL.BASE_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        ApiService apiService = retrofit.create( ApiService.class );

        Call<List<CategoryRetResponse>> call = apiService.getCategory();
        call.enqueue( new Callback<List<CategoryRetResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryRetResponse>> call, Response<List<CategoryRetResponse>> response) {

                categoryList = response.body();

                categoryAdapter.setProductList( categoryList );
            }

            @Override
            public void onFailure(Call<List<CategoryRetResponse>> call, Throwable t) {

            }

        } );
    }

    private void navigation(int position) {

        switch (position) {

            case 0:
                drawer.closeDrawers();
                break;

            case 1:
                drawer.closeDrawers();
                break;

            case 2:
                drawer.closeDrawers();
                break;

            case 3:
                drawer.closeDrawers();
                break;

            case 4:
                drawer.closeDrawers();
                break;

            case 5:
                drawer.closeDrawers();
                break;

            case 6:
                shareApp();
                drawer.closeDrawers();
                break;

            case 7:
                rateApp();
                drawer.closeDrawers();
                break;

            case 8:
                moreApp();
                drawer.closeDrawers();
                break;

            case 9:
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 10:
                startActivity(new Intent(MainActivity.this, PrivacyPolice.class));
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 11:
                if (Method.isNetworkAvailable(MainActivity.this)) {
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        startActivity(new Intent(MainActivity.this, ContactsContract.Profile.class));
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.you_have_not_login), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
                Method.onBackPress = true;
                drawer.closeDrawers();
                break;

            case 12:
                if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                    finishAffinity();
                    drawer.closeDrawers();
                } else {
                    startActivity(new Intent(MainActivity.this, UserLogin.class));
                    drawer.closeDrawers();
                    finishAffinity();
                }
                break;
        }

    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
        }
    }

    private void moreApp() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.play_more_app))));
    }

    private void shareApp() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String sAux = "\n" + getResources().getString(R.string.Let_me_recommend_you_this_application) + "\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getApplication().getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }

    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        actionBar.setTitle("Home");
    }



    private void initNavigationMenu() {
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                updateCounter(nav_view);
                super.onDrawerOpened(drawerView);
            }
        };
       /* nav_view.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.login:
                        // Handle menu click
                        startActivity( new Intent( getApplicationContext(),UserLogin.class ) );
                        //finish();
                    case R.id.logout:
                        // Handle settings click
                        SharedPrefManager.getInstance( getApplicationContext() ).logout();
                        startActivity( new Intent( getApplicationContext(),UserLogin.class ) );

                    default:
                        return false;
                }
            }
        } );*/

        navigation_header = nav_view.getHeaderView(0);

    }


    private void updateCounter(NavigationView nav) {
        if (is_account_mode) return;

        Menu m = nav.getMenu();


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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this);
        builder.setMessage("Are you sure want to do this ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        MainActivity.super.onBackPressed();

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void openCart(MenuItem item) {

        if(!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            startActivity( new Intent( MainActivity.this, UserLogin.class ) );
            finish();
        }
        else {
            startActivity( new Intent( getApplicationContext(), CartActivity.class ) );
        }
    }
}