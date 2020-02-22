package com.example.kiranastoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kiranastoremanagement.AdapterClass.Product;
import com.example.kiranastoremanagement.AdapterClass.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PriceList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FloatingActionButton fabAddPrice;
    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProductAdapter pAdapter;
    private List<Product> productList;
    private Context context;

    String RETRIEVE_PRODUCT_LIST = "http://gurungonlineshopping.com/StoreManagement/RetrieveProductData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        toolbar = findViewById(R.id.toolbar1);
        drawer = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        fabAddPrice=findViewById(R.id.fabAddPrice);




        loadProduct();
        createProductList();
        buildRecyclerView();


        fabAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPrice=new Intent(PriceList.this, AddPrice.class);
                startActivity(addPrice);
            }
        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void createProductList(){

        //initializing the productList
        productList=new ArrayList<>();

    }




    public void buildRecyclerView(){

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pAdapter=new ProductAdapter(PriceList.this,productList);
        recyclerView.setAdapter(pAdapter);
        swipeRefreshLayout=findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pAdapter=new ProductAdapter(PriceList.this,productList);
                pAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //loadProduct();
        pAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {


            }

            @Override
            public void onEditClick(int position) {
                /*Intent update=new Intent(context,UpdateProduct.class);
                update.putExtra("update",1);
                update.putExtra("product_id", productList.get(position).getId() );
                update.putExtra("product_image", productList.get(position).getProduct_image() );
                update.putExtra("product_name", productList.get(position).getProduct_name());
                update.putExtra("available_stock", productList.get(position).getAvailable_stock());
                update.putExtra("min_stock", productList.get(position).getMin_stock());
                update.putExtra("measurement_unit", productList.get(position).getMeasurement_unit());
                update.putExtra("purchase_cost", productList.get(position).getPurchase_cost());
                update.putExtra("sale_cost", productList.get(position).getSale_cost());
                context.startActivity(update);*/

            }
        });

    }


    public void loadProduct(){


        StringRequest stringRequest=new StringRequest(Request.Method.POST, RETRIEVE_PRODUCT_LIST, new Response.Listener<String>() {
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
                                    Product.layoutType.PRODUCT_LIST

                            ));

                    }

                    //creating adapter object and setting it to recyclerView
                    pAdapter= new ProductAdapter(PriceList.this,productList);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setAdapter(pAdapter);
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

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent i;
        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                i = new Intent(this, SignIn.class);
                startActivity(i);
                break;

            case R.id.nav_price_list:
                i = new Intent(this, PriceList.class);
                startActivity(i);
                break;

            case R.id.nav_customer_list:
                i = new Intent(this, TransactionList.class);
                startActivity(i);
                break;

            case R.id.nav_profile: i= new Intent(this, Profile.class);
                startActivity(i);
                break;

            case R.id.nav_transaction_details:
                i = new Intent(this, CustomerList.class);
                startActivity(i);
                break;

            case R.id.nav_supplier:
                i = new Intent(this, SupplierList.class);
                startActivity(i);
            default:
                break;
        }
        return true;
    }


}
