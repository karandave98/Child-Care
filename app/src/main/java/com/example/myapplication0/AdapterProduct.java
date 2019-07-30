package com.example.myapplication0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    Context context;
    ArrayList<ProductInfo> arrayProduct;
    ArrayList<String> listKey;
    public static ClickListener clickListener;

    public AdapterProduct(Context c,ArrayList<ProductInfo> arPr, ArrayList<String> arKey ){
        context = c;
        arrayProduct = arPr;
        listKey = arKey;
//        clickListener1 = clickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_view,viewGroup,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        productViewHolder.tvPrName.setText(arrayProduct.get(i).getProduct_Name());
//        productViewHolder.tvPrDesc.setText(arrayProduct.get(i).getDesc());
        productViewHolder.tvPrPrice.setText(arrayProduct.get(i).getPrice());
        final int pos = i;

        productViewHolder.tvPrName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbought = new Intent(context,ProductBought.class);
                intentbought.putExtra("productId",listKey.get(pos));
                intentbought.putExtra("productName",arrayProduct.get(pos).getProduct_Name());
                intentbought.putExtra("productDesc",arrayProduct.get(pos).getDesc());
                intentbought.putExtra("productPrice",arrayProduct.get(pos).getPrice());
                intentbought.putExtra("productUrl",arrayProduct.get(pos).getImage());
                context.startActivity(intentbought);
            }
        });
//        productViewHolder.recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentbought = new Intent(context,ProductBought.class);
//                intentbought.putExtra("productId",listKey.get(pos));
//                intentbought.putExtra("productName",arrayProduct.get(pos).getProdusct_Name());
//                intentbought.putExtra("productDesc",arrayProduct.get(pos).getDesc());
//                intentbought.putExtra("productPrice",arrayProduct.get(pos).getPrice());
//                intentbought.putExtra("productUrl",arrayProduct.get(pos).getImage());
//                context.startActivity(intentbought);
//
//            }
//        });
        final int grtP = i;
//        productViewHolder.recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListener.OnItemClick(grtP,v);
//            }
//        });
        Picasso.get().load(arrayProduct.get(i).getImage()).into(productViewHolder.imageProduct);

    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    class  ProductViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{

        TextView tvPrName,tvPrDesc,tvPrPrice;
        ImageView imageProduct;
        RecyclerView recyclerView;
        View cont;

        public ProductViewHolder(View item){
            super(item);
            tvPrName = item.findViewById(R.id.product_name);
//            tvPrDesc = item.findViewById(R.id.product_desrciption);
            tvPrPrice = item.findViewById(R.id.product_price);
            imageProduct = item.findViewById(R.id.image_product);
            recyclerView = item.findViewById(R.id.recycle_product);
            //item.findViewById(R.id.recycle_product).setOnClickListener(this);
           // cont = item;
        }

//        @Override
//        public void onClick(View v) {
//            clickListener.OnItemClick(getAdapterPosition(),v);
//
//        }

    }
    public void setOnItemClickListener(ClickListener clickListener){
        AdapterProduct.clickListener = clickListener;
    }
    public interface ClickListener{
        void OnItemClick(int position,View V);

    }
}
