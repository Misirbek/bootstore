package com.example.bootstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bootstore.Models.Product;
import com.example.bootstore.R;

import java.util.List;

public class PlacedOrdersAdapter extends RecyclerView.Adapter<PlacedOrdersAdapter.MyOrderViewHolder>
{
    private Context context;
    private List<Product> myOrderList;
    //public OrderListAdapter.OrderRemoveClickListener mOnClickListener;

    public PlacedOrdersAdapter(Context context, List<Product> orderList)
    {
        this.context = context;
        this.myOrderList = orderList;
    }

    @NonNull
    @Override
    public PlacedOrdersAdapter.MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_card, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacedOrdersAdapter.MyOrderViewHolder holder, int position)
    {
        final Product orderedProduct = myOrderList.get(position);
        holder.tvProductName.setText(orderedProduct.getProductName());
        holder.tvProductQty.setText(""+orderedProduct.getProdcutQty());
        holder.tvProductPrice.setText("$"+orderedProduct.getProductTotal());
        holder.btnDelete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvProductName, tvProductPrice, tvProductQty;
        Button btnDelete;

        public MyOrderViewHolder(@NonNull View view)
        {
            super(view);

            tvProductName = (TextView) itemView.findViewById(R.id.item_product_name);
            tvProductPrice = (TextView) itemView.findViewById(R.id.item_product_price);
            tvProductQty = (TextView) itemView.findViewById(R.id.item_product_qty);
            btnDelete = (Button) itemView.findViewById(R.id.btnOrderDelete);
        }
    }
}
