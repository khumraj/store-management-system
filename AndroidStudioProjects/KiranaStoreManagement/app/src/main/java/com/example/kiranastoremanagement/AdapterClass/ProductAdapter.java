package com.example.kiranastoremanagement.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kiranastoremanagement.AddSale;
import com.example.kiranastoremanagement.R;
import com.example.kiranastoremanagement.UpdateProduct;
import com.squareup.picasso.Picasso;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class ProductAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mCtx;
    private List<Product> productList;
    private OnItemClickListener mListener;
    private static final int PRODUCT_LIST=1;
    private static final int SALE_PRODUCT=2;
    String product_id;
    private final static String DELETE_URL="http://gurungonlineshopping.com/StoreManagement/DeleteProduct.php";

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    //interface class
    public interface OnItemClickListener {
        //void onItemClick(int position);
        void onDeleteClick(int position);
        void  onEditClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // LayoutInflater inflater= LayoutInflater.from(mCtx);
       // View view=inflater.inflate(R.layout.products_list,null); // this line inflate our product list.
        if (viewType==PRODUCT_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list, parent, false);
            RecyclerView.ViewHolder pvh = new ViewHolderProductList(view, mListener);

            return pvh; // this will return our view to holder class
        }else if (viewType==SALE_PRODUCT){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_product, parent, false);
            RecyclerView.ViewHolder pvh = new ViewHolderSaleProduct(view,mListener);
            return pvh;
        }else {
            throw new RuntimeException("The type has to be PRODUCT LIST or SALE PRODUCT");
        }
    }

    //load data in each row element

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int ListPosition) {
        switch (holder.getItemViewType()){
            case PRODUCT_LIST:
                initProductListLayout((ViewHolderProductList)holder,ListPosition);
                break;
            case SALE_PRODUCT:
                initSaleProductLayout((ViewHolderSaleProduct)holder,ListPosition);
                break;
                default:
                    break;
        }



    }

    private void initProductListLayout(ViewHolderProductList holder,int position){
        final Product product=productList.get(position);
        product_id= Integer.toString(product.getId());

        //loading the image
        /*Glide.with(mCtx)
                .load(product.getProduct_image())
                .placeholder(R.drawable.product96)
                .into(holder.ivProductImage);*/

        if (TextUtils.isEmpty(product.getProduct_image())) {

            Picasso.with(mCtx).cancelRequest(holder.ivProductImage);

            holder.ivProductImage.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.product96));
        } else {
            Picasso.with(mCtx)
                    .load(product.getProduct_image())
                    .noFade()
                    .placeholder(R.drawable.product96)
                    .into(holder.ivProductImage);
        }

        holder.tvProductName.setText(product.getProduct_name());
        holder.tvAvailableStock.setText(Integer.toString(product.getAvailable_stock()));
        holder.tvSaleCost.setText("Rs."+product.getSale_cost());
        holder.product=product;
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });


    }
    private void initSaleProductLayout(ViewHolderSaleProduct holder,int position){
        Product product=productList.get(position);
        if (TextUtils.isEmpty(product.getProduct_image())) {

            Picasso.with(mCtx).cancelRequest(holder.ivSaleProductImage);

            holder.ivSaleProductImage.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.product96));
        } else {
            Picasso.with(mCtx)
                    .load(product.getProduct_image())
                    .noFade()
                    .placeholder(R.drawable.product96)
                    .into(holder.ivSaleProductImage);
        }

        holder.tvSale_Product_Name.setText(product.getProduct_name());
        holder.tvAvai_Stock.setText(Integer.toString(product.getAvailable_stock()));
        holder.tvSalePrice.setText("Rs."+product.getSale_cost());
        holder.cvSaleProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mCtx,AddSale.class);
                intent.putExtra("product_id",product.getId());
                intent.putExtra("product_name",product.getProduct_name());
                intent.putExtra("unit_price",product.getSale_cost());
                intent.putExtra("product_stock",product.getAvailable_stock());
                mCtx.startActivity(intent);
            }
        });




    }

    @Override
    public long getItemId(int position) {
        return productList != null ? productList.get(position).getId() : 0;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Product product=productList.get(position);
        if (product.getType()==Product.layoutType.PRODUCT_LIST){
            return PRODUCT_LIST;
        }else if (product.getType()==Product.layoutType.SALE_PRODUCT){
            return SALE_PRODUCT;
        }else {
            return -1;
        }

    }

    class ViewHolderProductList extends RecyclerView.ViewHolder{
        TextView tvProductName, tvAvailableStock,tvMinimumStock,tvMeasurementUnit,tvPriceCost,tvSaleCost,tvDeleteProduct,tvUpdateProduct,productId;
        ImageView ivProductImage,ivDelete, ivEdit; LinearLayout llOnclick;
        Product product;



        public ViewHolderProductList(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            ivProductImage=itemView.findViewById(R.id.ivProductImage);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvAvailableStock=itemView.findViewById(R.id.tvAvailableStock);
            tvSaleCost=itemView.findViewById(R.id.tvProductPrice);
            llOnclick=itemView.findViewById(R.id.llOnclick);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivDelete=itemView.findViewById(R.id.ivDelete);



            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductAdapter productAdapter=new ProductAdapter(mCtx,productList);
                    Intent update = new Intent(mCtx, UpdateProduct.class);
                    update.putExtra("update",1);
                    update.putExtra("product_id", product.getId() );
                    update.putExtra("product_image", product.getProduct_image() );
                    update.putExtra("product_name", product.getProduct_name());
                    update.putExtra("available_stock", product.getAvailable_stock());
                    update.putExtra("min_stock", product.getMin_stock());
                    update.putExtra("measurement_unit", product.getMeasurement_unit());
                    update.putExtra("purchase_cost", product.getPurchase_cost());
                    update.putExtra("sale_cost", product.getSale_cost());
                    mCtx.startActivity(update);

                }
            });



        }

        public ViewHolderProductList(@NonNull View itemView) {
            super(itemView);
        }

    }
    class ViewHolderSaleProduct extends RecyclerView.ViewHolder{
        TextView tvSalePrice,tvAvai_Stock,tvSale_Product_Name;
        ImageView ivSaleProductImage;
        CardView cvSaleProduct;

        public ViewHolderSaleProduct(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvSalePrice=itemView.findViewById(R.id.tvProductSalePrice);
            tvAvai_Stock=itemView.findViewById(R.id.tvAvailableSaleStock);
            ivSaleProductImage=itemView.findViewById(R.id.ivSaleProductImage);
            tvSale_Product_Name=itemView.findViewById(R.id.tvS_ProductName);
            cvSaleProduct=itemView.findViewById(R.id.cvSaleProduct);
        }
    }

    public void deleteData(final int position){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, DELETE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                makeText(mCtx, response, LENGTH_SHORT).show();
                productList.remove(position);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeText(mCtx,error.getMessage().toString(),LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>param=new HashMap<String, String>();
                param.put("product_id",Integer.toString(productList.get(position).getId()));
                return param;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);


    }



}
