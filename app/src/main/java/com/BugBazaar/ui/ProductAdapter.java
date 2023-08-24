package com.BugBazaar.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BugBazaar.R;
import com.BugBazaar.ui.DetailedProductActivity;
import com.BugBazaar.ui.Product;
import java.io.Serializable;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        } else {
            view = convertView;
        }

        ImageView itemImage = view.findViewById(R.id.itemImage);
        TextView itemName = view.findViewById(R.id.itemName);
        TextView itemPrice = view.findViewById(R.id.itemPrice);

        Product product = productList.get(position);

        itemImage.setImageResource(product.getImageResId());
        itemName.setText(product.getName());
        itemPrice.setText(product.getPrice());

        // Set OnClickListener for the product click
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open detailed product page or activity here
                Intent intentz = new Intent(context, DetailedProductActivity.class);
                intentz.putExtra("product", product);
                context.startActivity(intentz);
            }
        });
        return view;
    }
}
