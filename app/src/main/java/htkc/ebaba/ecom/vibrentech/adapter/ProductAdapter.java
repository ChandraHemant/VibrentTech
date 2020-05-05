package htkc.ebaba.ecom.vibrentech.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import htkc.ebaba.ecom.vibrentech.ProductActivity;
import htkc.ebaba.ecom.vibrentech.ProductDetails;
import htkc.ebaba.ecom.vibrentech.R;
import htkc.ebaba.ecom.vibrentech.SharedPrefManager;
import htkc.ebaba.ecom.vibrentech.UserLogin;
import htkc.ebaba.ecom.vibrentech.api.ApiService;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;
import htkc.ebaba.ecom.vibrentech.response.AddCartResponse;
import htkc.ebaba.ecom.vibrentech.response.ProductRetResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyviewHolder>
{
    Context context;
    List<ProductRetResponse> productList;

    public ProductAdapter(Context context, List<ProductRetResponse> productResponseList)
    {
        this.context = context;
        this.productList = productResponseList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate( R.layout.item_product_card,parent,false);
        return new MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        holder.pname.setText(productList.get(position).getP_name());
        holder.p_id.setText(productList.get(position).getP_id());
        holder.price.setText(productList.get(position).getP_price());

        // picasso ka code
        String picname = productList.get(position).getP_img();
        String picpath = ApiURL.BASE_URL  + picname;
        Log.d("Complete", picpath);

        Glide.with(holder.pimg)
                .load(picpath)
        .into(holder.pimg);

        holder.cart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!SharedPrefManager.getInstance(context).isLoggedIn())
                {
                        Intent intent = new Intent(context, UserLogin.class);
                        //Toast.makeText( context, catids, Toast.LENGTH_SHORT ).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                }
                String uid = SharedPrefManager.getInstance(context).getUser().getId();
                String qntty = "1";
                String pids = holder.p_id.getText().toString();
                String pprice = holder.price.getText().toString();

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
                            Intent intent = new Intent( context, ProductActivity.class );
                            Toast.makeText( context, "Added To Cart", Toast.LENGTH_SHORT ).show();
                            intent.putExtra( "p_id", pids );
                            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                        }
                        else {

                            Toast.makeText( context, response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCartResponse> call, Throwable t) {

                    }
                });


            }
        } );

        holder.lyt_parent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!SharedPrefManager.getInstance(context).isLoggedIn())
                {
                    Intent intent = new Intent(context, UserLogin.class);
                    //Toast.makeText( context, catids, Toast.LENGTH_SHORT ).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                String pids = holder.p_id.getText().toString();
                Intent intent = new Intent(context, ProductDetails.class);
                //Toast.makeText( context, pids, Toast.LENGTH_SHORT ).show();
                intent.putExtra( "p_id",pids );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        } );
    }

    @Override
    public int getItemCount() {
        if(productList!= null){
            return productList.size();
        }

        return 0;
    }

    public void setProductList(List<ProductRetResponse> productList)
    {
            this.productList=productList;
            notifyDataSetChanged();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView pname,p_id,price,pdescr;
        ImageView pimg;
        ImageButton cart;
        LinearLayout lyt_parent;
        public MyviewHolder(View itemView)
        {
            super(itemView);
            p_id = (TextView)itemView.findViewById(R.id.pid);
            pname = (TextView)itemView.findViewById(R.id.pname);
            price = (TextView)itemView.findViewById(R.id.price);
            pimg=(ImageView)itemView.findViewById(R.id.image);
            cart=(ImageButton) itemView.findViewById(R.id.cart);
            lyt_parent=(LinearLayout) itemView.findViewById(R.id.lyt_parent);

        }
    }
}
