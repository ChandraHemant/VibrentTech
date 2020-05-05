package htkc.ebaba.ecom.vibrentech.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import htkc.ebaba.ecom.vibrentech.ProductActivity;
import htkc.ebaba.ecom.vibrentech.ProductDetails;
import htkc.ebaba.ecom.vibrentech.R;
import htkc.ebaba.ecom.vibrentech.response.SubCategoryRetResponse;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    Context context;
    List<SubCategoryRetResponse> subCategoryList;
    public SubCategoryAdapter(Context applicationContext, List<SubCategoryRetResponse> subCategoryList) {

        this.context = applicationContext;
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.tab_row_items,parent,false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.sname.setText( "tfyt" );

        holder.sname.setText(subCategoryList.get(position).getSub_name());
        holder.sub_ids.setText(subCategoryList.get(position).getSub_id());

        holder.sub_cat.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subcatids = holder.sub_ids.getText().toString();
                if(subcatids!="") {
                    Intent intent = new Intent( context, ProductActivity.class );
                    //Toast.makeText( context, subcatids, Toast.LENGTH_SHORT ).show();
                    intent.putExtra( "sub_cat_id", subcatids );
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity( intent );
                }

            }
        } );

    }

    @Override
    public int getItemCount() {
        if(subCategoryList!= null){
            return subCategoryList.size();
        }
        return 0;
    }

    public void setCategoryList(List<SubCategoryRetResponse> subCategoryList) {

        this.subCategoryList=subCategoryList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sub_ids;
        TextView sname;
        CardView sub_cat;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            sub_cat = itemView.findViewById( R.id.subcategory );
            sub_ids = (TextView)itemView.findViewById( R.id.sub_id);
            sname = (TextView)itemView.findViewById(R.id.tab_sub);


        }
    }
}
