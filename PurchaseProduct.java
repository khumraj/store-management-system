package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kiranastoremanagement.AdapterClass.Product;
import com.example.kiranastoremanagement.AdapterClass.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProduct extends AppCompatActivity {
    RecyclerView rvPurchaseProduct;
    ProductAdapter productAdapter;
    CardView cvAddNewProduct;
    List<Product>productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_product);
        cvAddNewProduct=findViewById(R.id.cvAddNewProduct);
        createProductList();
        buildRecyclerView();
        retrievePurchaseProduct();

        cvAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(PurchaseProduct.this,AddPrice.class);
                startActivity(add);
            }
        });


    }

    public void createProductList(){

        //initializing the productList
        productList=new ArrayList<>();

    }

    public void buildRecyclerView(){
        rvPurchaseProduct=findViewById(R.id.PurchaseProductRecyclerView);
        rvPurchaseProduct.setHasFixedSize(true);
        rvPurchaseProduct.setLayoutManager(new GridLayoutManager(this,2));
        productAdapter=new ProductAdapter(PurchaseProduct.this,productList);
        rvPurchaseProduct.setAdapter(productAdapter);

    }

    public void retrievePurchaseProduct(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.PURCHASE_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array=new JSONArray(response);

                    //traversing through all the object
                    for (int i=0; i<array.length();i++){

                        //getting product object from json array
                        JSONObject product=array.getJSONObject(i);

                        //adding the product to product list

                        productList.add(new Product(
                                product.getInt("id"),
                                product.getString("product_image"),
                                product.getString("product_name"),
                                product.getInt("available_stock"),
                                product.getInt("min_stock"),
                                product.getString("measurement_unit"),
                                product.getDouble("purchase_cost"),
                                product.getDouble("sale_cost"),
                                Product.layoutType.PURCHASE_PRODUCT

                        ));

                    }

                    //creating adapter object and setting it to recyclerView
                    productAdapter= new ProductAdapter(PurchaseProduct.this,productList);
                    rvPurchaseProduct.setAdapter(productAdapter);
                    productAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //adding our stringRequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
