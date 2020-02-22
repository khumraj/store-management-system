package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class SaleProducts extends AppCompatActivity {
    private RecyclerView SaleProductRecyclerView;
    private ProductAdapter pAdapter;
    private List<Product> productList;
    final String RETRIEVE_SALE_PRODUCT="http://gurungonlineshopping.com/StoreManagement/RetrieveProductData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_products);
        createProductList();
        buildRecyclerView();
        RetrieveSaleProduct();


    }

    public void createProductList(){

        //initializing the productList
        productList=new ArrayList<>();

    }

    public void buildRecyclerView(){
        SaleProductRecyclerView=findViewById(R.id.SaleProductRecyclerView);
        SaleProductRecyclerView.setHasFixedSize(true);
        SaleProductRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //SaleProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pAdapter=new ProductAdapter(SaleProducts.this,productList);
        SaleProductRecyclerView.setAdapter(pAdapter);

    }

    public void RetrieveSaleProduct(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, RETRIEVE_SALE_PRODUCT, new Response.Listener<String>() {
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
                                product.getString("purchase_cost"),
                                product.getString("sale_cost"),
                                Product.layoutType.SALE_PRODUCT

                        ));

                    }

                    //creating adapter object and setting it to recyclerView
                    pAdapter= new ProductAdapter(SaleProducts.this,productList);
                    pAdapter.notifyDataSetChanged();


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
