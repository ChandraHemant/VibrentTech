package htkc.ebaba.ecom.vibrentech.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import htkc.ebaba.ecom.vibrentech.CategoryActivity;
import htkc.ebaba.ecom.vibrentech.api.ApiURL;


import htkc.ebaba.ecom.vibrentech.R;
import htkc.ebaba.ecom.vibrentech.response.CategoryRetResponse;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyviewHolder>
{
    Context context;
    List<CategoryRetResponse> categoryList;

    public CategoryAdapter(Context context, List<CategoryRetResponse> productResponseList)
    {
        this.context = context;
        this.categoryList = productResponseList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate( R.layout.category_row_item,parent,false);
        return new MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        holder.pname.setText(categoryList.get(position).getCat_name());
        holder.cate_id.setText(categoryList.get(position).getCat_id());

        // picasso ka code
        String picname = categoryList.get(position).getCat_img();
        String picpath = ApiURL.BASE_URL  + picname;
        Log.d("Complete", picpath);

        Glide.with(holder.pimg)
                .load(picpath)
        .into(holder.pimg);

        holder.pimg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String catids = holder.cate_id.getText().toString();
                Intent intent = new Intent(context, CategoryActivity.class);
                //Toast.makeText( context, catids, Toast.LENGTH_SHORT ).show();
                intent.putExtra( "my_cat_id",catids );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        } );

    }

    @Override
    public int getItemCount() {
        if(categoryList!= null){
            return categoryList.size();
        }

        return 0;
    }

    public void setProductList(List<CategoryRetResponse> productList)
    {
            this.categoryList=productList;
            notifyDataSetChanged();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder
    {
        TextView pname,cate_id;
        ImageView pimg;
        public MyviewHolder(View itemView)
        {
            super(itemView);

            cate_id = (TextView)itemView.findViewById(R.id.cat_id);
            pname = (TextView)itemView.findViewById(R.id.name);
            pimg=(ImageView)itemView.findViewById(R.id.image);

        }
    }
}
