<?php
       
        include'DatabaseHelper.php';
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	    
        $store_name = $_POST['store_name'];
        $supplier_name = $_POST['supplier_name'];
        $supplier_address = $_POST['supplier_address'];
        $mobile_number= $_POST['mobile_number'];
        
        if($store_name == '' || $supplier_name == '' || $supplier_address == '' || $mobile_number == '')
    		{
    	
    	    	echo 'please fill all values';
    		}else{
        		    
        	    $sql = "SELECT * FROM  add_supplier  WHERE store_name='$store_name' OR supplier_name='$supplier_name' ";
        		
        	    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
        	   
        		if(isset($check)){
        		    
        		echo 'store name or suppiler name already registered';
        	    
        	}else{
    		    
    		$sql = "INSERT INTO add_supplier (store_name,supplier_name,supplier_address,mobile_number) VALUES('$store_name','$suppiler_name','$supplier_address','$mobile_number')";
    
    		if(mysqli_query($conn,$sql)){
    		    
    			echo 'suppliers successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    



<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="5dp"
    android:padding="5dp"
    android:clickable="true">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center">

        <ImageView
            android:id="@+id/ivProductImage"
            android:layout_width="140dp"
            android:layout_margin="5dp"
            android:layout_height="140dp"
            android:background="@drawable/product96"/>
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_marginLeft="5dp"
            android:orientation="vertical">

            <TextView
               android:layout_marginTop="30dp"
                android:id="@+id/tvProductName"
                android:layout_width="150dp"
                android:layout_height="wrap_content"
                android:textStyle="bold"
                android:textSize="20dp"
                android:text="Product Name"/>

            <TextView
                android:layout_marginTop="10dp"
                android:id="@+id/tvAvailableStock"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Available Stock" />

        </LinearLayout>

        <TextView
            android:id="@+id/tvProductPrice"
            android:layout_width="70dp"
            android:layout_height="wrap_content"
            android:layout_marginLeft="5dp"
            android:text="Product Price"/>
    </LinearLayout>

</androidx.cardview.widget.CardView>



<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    app:cardCornerRadius="10dp"
    android:background="#ccc">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="horizontal">

                <ImageView
                    android:id="@+id/ivProductImage"
                    android:layout_width="140dp"
                    android:layout_margin="5dp"
                    android:layout_height="140dp"
                    android:background="@drawable/product96"/>

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginLeft="5dp"
                    android:orientation="vertical">

                    <TextView
                        android:layout_marginTop="30dp"
                        android:id="@+id/tvProductName"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:textStyle="bold"
                        android:gravity="center"
                        android:textSize="20dp"
                        android:text="Product Name"/>

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:orientation="horizontal">

                        <TextView
                            android:layout_marginTop="10dp"
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:text="Available Stock:" />

                        <TextView
                            android:id="@+id/tvAvailableStock"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:gravity="center"
                            android:layout_marginTop="10dp"
                            android:text="Available Stock"/>

                    </LinearLayout>

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:orientation="horizontal">

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:text="Price:"/>

                        <EditText
                            android:id="@+id/tvProductPrice"
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center"
                             />


                    </LinearLayout>

                    <LinearLayout
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content">

                        <Button
                            android:layout_width="wrap_content"
                            android:layout_height="30dp"
                            android:layout_gravity="center"
                            android:cursorVisible="true"
                            android:text="Delete"
                            android:background="@drawable/rectangle1"/>

                        <Button
                            android:layout_width="wrap_content"
                            android:layout_height="30dp"
                            android:layout_gravity="center"
                            android:cursorVisible="true"
                            android:text="Edit"
                            android:background="@drawable/rectangle1"
                            android:layout_marginLeft="40dp"/>
                    </LinearLayout>


                </LinearLayout>

            </LinearLayout>


</androidx.cardview.widget.CardView>


<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:card_view="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_margin="10dp"
    app:cardCornerRadius="10dp"
    android:layout_marginBottom="1dp"
    card_view:cardElevation="@dimen/_2sdp"
    card_view:cardUseCompatPadding="true">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">

        <ImageView
            android:id="@+id/ivProductImage"
            android:layout_width="140dp"
            android:layout_margin="5dp"
            android:layout_height="145dp"
            android:background="@drawable/product96"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="5dp"
            android:orientation="vertical">

            <TextView
                android:layout_marginTop="30dp"
                android:id="@+id/tvProductName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textStyle="bold"
                android:gravity="center"
                android:textSize="20dp"
                android:text="Product Name"/>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="horizontal">

                <TextView
                    android:layout_marginTop="10dp"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Available Stock:" />

                <TextView
                    android:id="@+id/tvAvailableStock"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:gravity="center"
                    android:layout_marginTop="10dp"
                    android:text="Available Stock"/>

            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginTop="5dp"
                android:orientation="horizontal">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Price:"/>

                <TextView
                    android:id="@+id/tvProductPrice"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center" />


            </LinearLayout>

            <LinearLayout
                android:id="@+id/llOnclick"
                android:layout_marginTop="10dp"
                android:layout_width="match_parent"
                android:layout_gravity="center"
                android:gravity="center"
                android:layout_height="wrap_content">


                <TextView
                    android:id="@+id/tvDeleteProduct"
                    android:layout_width="100dp"
                    android:layout_height="30dp"
                    android:gravity="center"
                    android:text="Delete"
                    android:background="@drawable/rectangle1"
                    android:drawableLeft="@drawable/ic_delete"/>

                <TextView
                    android:id="@+id/tvUpdateProduct"
                    android:layout_width="100dp"
                    android:layout_height="30dp"
                    android:gravity="center"
                    android:layout_marginLeft="30dp"
                    android:text="Edit"
                    android:background="@drawable/rectangle1"
                    android:drawableLeft="@drawable/ic_edit"/>
            </LinearLayout>

        </LinearLayout>

    </LinearLayout>

</androidx.cardview.widget.CardView>




package com.example.kiranastoremanagement.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kiranastoremanagement.R;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {
    private Context mCtx;
    private List<Product> productList;
    private OnItemClickListener mOnItemClickListener;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // LayoutInflater inflater= LayoutInflater.from(mCtx);
       // View view=inflater.inflate(R.layout.products_list,null); // this line inflate our product list.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list, parent, false);

        return new ProductViewHolder(view); // this will return our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=productList.get(position);

        //loading the image

        Glide.with(mCtx)
                .load(product.getProduct_image())
                .into(holder.ivProductImage);

        holder.tvProductName.setText(product.getProduct_name());
        holder.tvAvailableStock.setText(product.getAvailable_stock());
        holder.tvSaleCost.setText(product.getSale_cost());
        holder.tvUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.tvDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvAvailableStock,tvMinimumStock,tvMeasurementUnit,tvPriceCost,tvSaleCost,tvDeleteProduct,tvUpdateProduct;
        ImageView ivProductImage; LinearLayout llOnclick;



        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImage=itemView.findViewById(R.id.ivProductImage);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvAvailableStock=itemView.findViewById(R.id.tvAvailableStock);
            tvSaleCost=itemView.findViewById(R.id.tvProductPrice);
            tvDeleteProduct=itemView.findViewById(R.id.tvDeleteProduct);
            tvUpdateProduct=itemView.findViewById(R.id.tvUpdateProduct);
            llOnclick=itemView.findViewById(R.id.llOnclick);


        }
    }



}




package com.example.kiranastoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

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
    RecyclerView recyclerView;
    List<Product> productList;

    String ADD_PRICE_URL = "http://gurungonlineshopping.com/StoreManagement/RetrieveProductData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        toolbar = findViewById(R.id.toolbar1);
        drawer = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        fabAddPrice=findViewById(R.id.fabAddPrice);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productList
        productList=new ArrayList<>();

        loadProduct();

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


    public void loadProduct(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, ADD_PRICE_URL, new Response.Listener<String>() {
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
                                product.getString("available_stock"),
                                product.getString("min_stock"),
                                product.getString("measurement_unit"),
                                product.getString("purchase_cost"),
                                product.getString("sale_cost")
                        ));

                    }
                    //creating adapter object and setting it to recyclerView

                    ProductAdapter adapter= new ProductAdapter(PriceList.this,productList);
                    recyclerView.setAdapter(adapter);


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



ProductAdapter

package com.example.kiranastoremanagement.AdapterClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kiranastoremanagement.R;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {
    private Context mCtx;
    private List<Product> productList;
    private OnItemClickListener mListener;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    //interface class
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void  onEditClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // LayoutInflater inflater= LayoutInflater.from(mCtx);
       // View view=inflater.inflate(R.layout.products_list,null); // this line inflate our product list.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list, parent, false);
        ProductViewHolder pvh=new ProductViewHolder(view,mListener);

        return pvh; // this will return our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=productList.get(position);

        //loading the image

        Glide.with(mCtx)
                .load(product.getProduct_image())
                .into(holder.ivProductImage);

        holder.tvProductName.setText(product.getProduct_name());
        holder.tvAvailableStock.setText(product.getAvailable_stock());
        holder.tvSaleCost.setText("Rs."+product.getSale_cost());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvAvailableStock,tvMinimumStock,tvMeasurementUnit,tvPriceCost,tvSaleCost,tvDeleteProduct,tvUpdateProduct;
        ImageView ivProductImage,ivDelete, ivEdit; LinearLayout llOnclick;



        public ProductViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            ivProductImage=itemView.findViewById(R.id.ivProductImage);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvAvailableStock=itemView.findViewById(R.id.tvAvailableStock);
            tvSaleCost=itemView.findViewById(R.id.tvProductPrice);
            llOnclick=itemView.findViewById(R.id.llOnclick);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivDelete=itemView.findViewById(R.id.ivDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position=getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position=getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onEditClick(position);
                        }
                    }
                }
            });



        }
    }



}


PriceList

package com.example.kiranastoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

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

    private RecyclerView recyclerView;
    private ProductAdapter pAdapter;
    private List<Product> productList;

    String ADD_PRICE_URL = "http://gurungonlineshopping.com/StoreManagement/RetrieveProductData.php";

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
    public void changeProduct(int position){
        productList.get(position).getId();
        pAdapter.notifyItemChanged(position);

    }

    public void deleteProduct(int position){
        productList.remove(position);
        pAdapter.notifyItemRemoved(position);

    }

    public void editProduct(int position){

    }

    public void buildRecyclerView(){

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pAdapter=new ProductAdapter(this,productList);
        recyclerView.setAdapter(pAdapter);
        //loadProduct();
        pAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteProduct(position);

            }

            @Override
            public void onEditClick(int position) {
                editProduct(position);

            }
        });

    }


    public void loadProduct(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, ADD_PRICE_URL, new Response.Listener<String>() {
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
                                product.getString("available_stock"),
                                product.getString("min_stock"),
                                product.getString("measurement_unit"),
                                product.getString("purchase_cost"),
                                product.getString("sale_cost")
                        ));

                    }

                    //creating adapter object and setting it to recyclerView
                    pAdapter= new ProductAdapter(PriceList.this,productList);
                    recyclerView.setAdapter(pAdapter);


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

