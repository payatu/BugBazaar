package com.BugBazaar.ui.myorders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.BugBazaar.R;
import android.text.Html;
import android.text.Spanned;


import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {
    private List<OrderHistoryItem> orderItems;
    private Context context;

    public OrderHistoryAdapter(List<OrderHistoryItem> orderItems, Context context) {
        this.orderItems = orderItems;
        this.context = context;
    }
    // Method to set dividers
    public void addDividers(RecyclerView recyclerView) {
        Drawable divider = context.getResources().getDrawable(R.drawable.divider); // The custom divider XML
        DividerItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(divider);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderHistoryItem orderItem = orderItems.get(position);

        // Use HTML formatting to make "Order ID" text bold
        String orderIdText = "<b>Order ID:</b> " + orderItem.getOrderID();
        holder.orderIdTextView.setText(Html.fromHtml(orderIdText, Html.FROM_HTML_MODE_COMPACT));

        // Concatenate all product names with bullet points
        String productsText = "";
        for (String productName : orderItem.getProductNames()) {
            productsText += "• " + productName + "\n";
        }
        holder.itemsTextView.setText(productsText);
        String orderTotal="<b>Order Total:</b> " + orderItem.getFinalCost();
        holder.orderTotalTextView.setText(Html.fromHtml(orderTotal,Html.FROM_HTML_MODE_COMPACT));
    }




    // Format the list of products with bullet points
    private String formatProductList(String products) {
        String[] productList = products.split(", "); // Assuming products are comma-separated
        StringBuilder formattedList = new StringBuilder();
        for (String product : productList) {
            formattedList.append("• ").append(product).append("\n");
        }
        return formattedList.toString();
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTextView;
        TextView itemsTextView;
        TextView orderTotalTextView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            orderTotalTextView = itemView.findViewById(R.id.orderTotalTextView);
        }
    }
}

