package com.example.kiranastoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kiranastoremanagement.AdapterClass.LRUBitmapCache;
import com.example.kiranastoremanagement.AdapterClass.Product;
import com.example.kiranastoremanagement.AdapterClass.ProductAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class UpdateProduct extends AppCompatActivity {
    private ProductAdapter pAdapter;
    private List<Product> productList;
    Spinner UpdateUnitProduct;
    ImageView ivUpdateProduct,ivImageProduct;
    Button btnUpdateProduct,btnCancelUpdateProduct;
    private EditText etUpdateProductName,etUpdateAvailableStock,etUpdateMinStock,etUpdatePurchaseCost,etUpdateSaleCost;
    private String product_image,product_name,measurement_unit,purchase_cost,sale_cost,available_stock,minimum_stock;
    private int product_id,availableStock,minimumStock;
    Double purchaseCost,saleCost;

    Bundle bundle;


    int spinnerPosition=0;


    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);


        etUpdateProductName=findViewById(R.id.etUpdateProductName);
        etUpdateAvailableStock=findViewById(R.id.etUpdateStockAvailable);
        etUpdateMinStock=findViewById(R.id.etUpdateMinStockAvailable);
        etUpdatePurchaseCost=findViewById(R.id.etUpdatePurchaseCost);
        etUpdateSaleCost=findViewById(R.id.etUpdateSaleCost);
        btnUpdateProduct=findViewById(R.id.btnUpdateProduct);
        btnCancelUpdateProduct=findViewById(R.id.btnCancelUpdateProduct);
        ivUpdateProduct=findViewById(R.id.ivUpdateProduct);

        pAdapter=new ProductAdapter(UpdateProduct.this,productList);

        productList=new ArrayList<>();


        Intent data= getIntent();
        final int update=data.getIntExtra("update",0);
        product_id=data.getIntExtra("product_id",1);
        product_image=data.getStringExtra("product_image");
        product_name=data.getStringExtra("product_name");
        availableStock=data.getIntExtra("available_stock",0);
        minimumStock=data.getIntExtra("min_stock",0);
        measurement_unit=data.getStringExtra("measurement_unit");
        purchaseCost=data.getDoubleExtra("purchase_cost",0);
        saleCost=data.getDoubleExtra("sale_cost",0);

        available_stock=Integer.toString(availableStock);
        minimum_stock=Integer.toString(minimumStock);
        purchase_cost=Double.toString(purchaseCost);
        sale_cost=Double.toString(saleCost);


           if (update==1){
                etUpdateProductName.setText(product_name);
                etUpdateAvailableStock.setText(available_stock);
                etUpdateMinStock.setText(minimum_stock);
                etUpdatePurchaseCost.setText(purchase_cost);
                etUpdateSaleCost.setText(sale_cost);
                etUpdateAvailableStock.setText(available_stock);
                etUpdateMinStock.setText(minimum_stock);

                 if (TextUtils.isEmpty(product_image)) {

                     Picasso.with(UpdateProduct.this).cancelRequest(ivUpdateProduct);
                     ivUpdateProduct.setImageDrawable(ContextCompat.getDrawable(UpdateProduct.this, R.drawable.product96));
                 } else {
                            Picasso.with(UpdateProduct.this)
                                .load(product_image)
                                .placeholder(R.drawable.product96)
                                .noFade()
                                .into(ivUpdateProduct);
                        }


                Spinner();

            }



        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update==1){
                    UpdateProduct();
                }else {
                    makeText(UpdateProduct.this, "Something Wrong", LENGTH_SHORT).show();
                }


            }
        });


        btnCancelUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ChooseImage();

            }
        });


    }

    public void Spinner(){
        UpdateUnitProduct=findViewById(R.id.spUpdateAddMeasurementUnit);
        List<String> categories= new ArrayList<>();
            categories.add(0,measurement_unit);
            categories.add("Pcs");
            categories.add("Kg");
            categories.add("Meter");
            categories.add("Ltr");
            categories.add("Pack");



        //Add Style  and populate the spinner

        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);

        //Dropdown layout style

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching data adapter to spinner
        UpdateUnitProduct.setAdapter(dataAdapter);

        UpdateUnitProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals(measurement_unit))
                {
                    // Do nothing
                }
                else
                {
                    // On selecting a spinner item

                    measurement_unit= parent.getItemAtPosition(position).toString();

                    //Show selected spinner item

                    //Anything else you want to do on item selection do here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO Auto-generated method stub

            }
        });
        spinnerPosition=dataAdapter.getPosition(measurement_unit);
        UpdateUnitProduct.setSelection(spinnerPosition);
    }

    private void  ChooseImage(){

        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateProduct.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){
                    Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")){

                    Intent intent = new Intent();

                    intent.setType("image/*");

                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), SELECT_FILE);

                    /*Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent,"Select File"), SELECT_FILE);*/

                } else if (items[i].equals("Cancel")){
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivUpdateProduct.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
            UpdateImage();
        }
    }


    private String imageToString(Bitmap bm){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = ((BitmapDrawable) ivUpdateProduct.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //ivUpdateProduct.setImageBitmap(bm);
        return encodedImage;
    }

    public void UpdateProduct(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating Product...");
        progressDialog.show();
         //product_image=imageToString(bitmap);
         product_name = etUpdateProductName.getText().toString().trim();
         available_stock =etUpdateAvailableStock.getText().toString().trim();
         minimum_stock = etUpdateMinStock.getText().toString().trim();
         measurement_unit = UpdateUnitProduct.getSelectedItem().toString();
         purchase_cost = etUpdatePurchaseCost.getText().toString().trim();
         sale_cost = etUpdateSaleCost.getText().toString().trim();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, URLs.UPDATE_PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                makeText(UpdateProduct.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(UpdateProduct.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("product_id",Integer.toString(product_id));
                //params.put("product_image", product_image);
                params.put("product_name", product_name);
                params.put("available_stock", available_stock);
                params.put("min_stock", minimum_stock);
                params.put("measurement_unit", measurement_unit);
                params.put("purchase_cost", purchase_cost);
                params.put("sale_cost",sale_cost );
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(UpdateProduct.this);
        requestQueue.add(stringRequest);

    }
    public void UpdateImage(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating image...");
        progressDialog.show();

        final String ProductImage=imageToString(bitmap);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.UPDATE_PRODUCT_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                makeText(UpdateProduct.this, response, LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(UpdateProduct.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("product_id",Integer.toString(product_id));
                params.put("product_image", ProductImage);
                return params;
            }
        };

        //int socketTimeout = 30000;
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //stringRequest.setRetryPolicy(policy);

        //Creating a request queue
        RequestQueue requestQueue= Volley.newRequestQueue(UpdateProduct.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
