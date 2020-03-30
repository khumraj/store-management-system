package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kiranastoremanagement.AdapterClass.Customer;
import com.example.kiranastoremanagement.AdapterClass.Product;
import com.example.kiranastoremanagement.AdapterClass.Supplier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddPurchase extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    ImageView ivDate;
    TextView tvPurchaseDate,tvPurchaseUnitPrice;
    EditText etPurchaseQty;
    SearchableSpinner spSupplierName,spProductName;
    FloatingActionButton fabAddNewPurchase,fabAddNewSupplier,fabAddProducts;
    Button btnCancelPurchase,btnSavePurchase;
    private ArrayList<Supplier> supplierArrayList;
    private ArrayList<String> supplier_name=new ArrayList<String>();
    private ArrayList<Product>productArrayList;
    private ArrayList<String>product_name=new ArrayList<String>();
    private ArrayList<String>purchase_cost=new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
        ivDate=findViewById(R.id.ivCalender);
        tvPurchaseDate=findViewById(R.id.tvPurchaseDate);
        spSupplierName=findViewById(R.id.spSupplierName);
        tvPurchaseUnitPrice=findViewById(R.id.tvPurchaseUnitPrice);
        etPurchaseQty=findViewById(R.id.etPurchaseQuantity);
        fabAddNewPurchase=findViewById(R.id.fabaddNewProducts);
        fabAddNewSupplier=findViewById(R.id.fabaddNewSupplier);
        fabAddProducts=findViewById(R.id.fabaddPurchase);
        spProductName=findViewById(R.id.spProductName);

        //set default time and date in textView
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        tvPurchaseDate.setText(currentDate);

        supplierArrayList=new ArrayList<Supplier>();
        populateSupplierName();

        productArrayList=new ArrayList<Product>();
        //populateProductName();

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerHelper();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });
        fabAddNewSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddPurchase.this,SuppilerAdd.class);
                startActivity(i);
            }
        });
        fabAddNewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddPurchase.this,PurchaseProduct.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        tvPurchaseDate.setText(currentDateString);
    }

    public void populateSupplierName(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.RETRIEVE_SUPPLIER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array=new JSONArray(response);

                    //traversing through all the object
                    for (int i=0; i<array.length();i++){

                        //getting customer object from json array
                        JSONObject supplier=(JSONObject) array.get(i);

                        //adding the supplier data  to supplier list
                        Supplier supplier1=new Supplier(supplier.getInt("id"),
                                supplier.getString("supplier_image"),
                                supplier.getString("store_name"),
                                supplier.getString("supplier_name"),
                                supplier.getString("supplier_address"),
                                supplier.getString("mobile_number"),
                                supplier.getString("supplier_balance"),
                                supplier.getString("supplier_status"),
                                supplier.getString("supplier_notes"),
                                Supplier.layoutType.SUPPLIER_LIST);



                        supplier1.setSupplier_name( supplier.getString("supplier_name"));
                        supplierArrayList.add(supplier1);
                    }

                    for (int i = 0; i < supplierArrayList.size(); i++){
                        supplier_name.add(supplierArrayList.get(i).getSupplier_name());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddPurchase.this, android.R.layout.simple_spinner_item, supplier_name);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spSupplierName.setAdapter(spinnerArrayAdapter);




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
        // populateSpinner();

    }

    public void populateProductName(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.RETRIEVE_PRODUCT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array=new JSONArray(response);

                    //traversing through all the object
                    for (int i=0; i<array.length();i++){

                        //getting product object from json array
                        JSONObject product=(JSONObject) array.get(i);

                        //adding the product data  to customer list
                        Product product1 =new Product(
                                product.getInt("id"),
                                product.getString("product_image"),
                                product.getString("product_name"),
                                product.getInt("available_stock"),
                                product.getInt("min_stock"),
                                product.getString("measurement_unit"),
                                product.getDouble("purchase_cost"),
                                product.getDouble("sale_cost"),
                                Product.layoutType.PRODUCT_LIST);



                        product1.setProduct_name(product.getString("product_name"));
                        product1.setPurchase_cost(product.getDouble("purchase_cost"));
                        productArrayList.add(product1);
                    }

                    for (int i = 0; i < productArrayList.size(); i++){
                        product_name.add(productArrayList.get(i).getProduct_name());
                        purchase_cost.add(String.valueOf(productArrayList.get(i).getPurchase_cost()));

                    }

                    ArrayAdapter<String> productArrayAdapter = new ArrayAdapter<String>(AddPurchase.this, android.R.layout.simple_spinner_item, product_name);
                    productArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spProductName.setAdapter(productArrayAdapter);
                    spProductName.getSelectedItemPosition();
                    tvPurchaseUnitPrice.setText(String.valueOf(purchase_cost));




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
        // populateSpinner();

    }

}
