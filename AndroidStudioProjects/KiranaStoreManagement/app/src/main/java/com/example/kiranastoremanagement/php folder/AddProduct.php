
<?php
       
        include'DatabaseHelper.php';
    	
        $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
        
        
        $ImageData=$_POST['product_image'];
        $product_name= $_POST['product_name'];
        $available_stock= $_POST['available_stock'];
        $min_stock= $_POST['min_stock'];
        $measurement_unit= $_POST['measurement_unit'];
        $purchase_cost= $_POST['purchase_cost'];
        $sale_cost= $_POST['sale_cost'];
       
        $path="UploadImage/$product_name.png";
        $product_image="http://gurungonlineshopping.com/StoreManagement/$path";
       
        
        if($product_name== '' || $available_stock== '' || $min_stock== '' ||  $measurement_unit== '' ||  $purchase_cost== '' || $sale_cost== '')
    		{
    	
    	    	echo 'please fill all values';
    		}else{
        		    
        	    $sql = "SELECT * FROM  add_price WHERE product_name='$product_name' ";
        		
        	    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
        	   
        		if(isset($check)){
        		    
        		echo 'Product already added';
        	    
        	}else{
    		    
    		$sql = "INSERT INTO add_price (product_image,product_name,available_stock,min_stock,measurement_unit,purchase_cost,sale_cost) VALUES('$product_image','$product_name','$available_stock','$min_stock','$measurement_unit','$purchase_cost','$sale_cost')";
    
    		if(mysqli_query($conn,$sql)){
    		    file_put_contents($path, base64_decode($ImageData));
    		    
    		 
    			echo 'product successfully added';
    	
    	}
    		else{
    				
    			echo 'Please try again';
    		
    		}
    		
        }
    		
	        mysqli_close($conn);
		
        }
           
?>	    



package com.example.kiranastoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class AddPrice extends AppCompatActivity  {

    //Initialize variables
    Spinner addUnit;
    ImageView ivProduct;
    Button btnSavePrice,btnCancelPrice;
    EditText etProductName,etAvailableStock,etMinStock,etPurchaseCost,etSaleCost;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;

    String product_image,product_name,available_stock,minimum_stock,measurement_unit,purchase_cost,sale_cost;
    String ADD_PRICE_URL = "http://gurungonlineshopping.com/StoreManagement/add_price.php";
    String UPLOAD_PRODUCT_IMAGE_URL = "http://gurungonlineshopping.com/StoreManagement/UploadProductImage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        etProductName=findViewById(R.id.etProductName);
        etAvailableStock=findViewById(R.id.etStockAvailable);
        etMinStock=findViewById(R.id.etMinStockAvailable);
        etPurchaseCost=findViewById(R.id.etPurchaseCost);
        etSaleCost=findViewById(R.id.etSaleCost);
        btnSavePrice=findViewById(R.id.btnSavePrice);
        btnCancelPrice=findViewById(R.id.btnCancelPrice);



        btnSavePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePrice();
            }
        });

        btnCancelPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivProduct=findViewById(R.id.ivProduct);

        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseImage();
            }
        });

        addUnit=findViewById(R.id.spAddMeasurementUnit);
        List<String> categories= new ArrayList<>();
        categories.add(0,"Pcs");
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
        addUnit.setAdapter(dataAdapter);

        addUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pcs"))
                {
                    // Do nothing
                }
                else
                {
                    // On selecting a spinner item

                    String item= parent.getItemAtPosition(position).toString();

                    //Show selected spinner item

                    //Anything else you want to do on item selection do here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO Auto-generated method stub

            }
        });
    }

    private void  ChooseImage(){

        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(AddPrice.this);
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

            Uri uri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivProduct.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bm){
        /*Drawable drawable=this.getResources().getDrawable(R.drawable.camera48);
        bitmap=((BitmapDrawable)drawable).getBitmap();
        ivProduct.setImageBitmap(bitmap);
        Drawable d= new BitmapDrawable(this.getResources(),bitmap);
        ivProduct.setImageDrawable(d);
        bitmap = BitmapFactory.decodeResource(getResources(), R.id.ivProduct);
        bitmap = ((BitmapDrawable) ivProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = ((BitmapDrawable) ivProduct.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        product_image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        ivProduct.setImageBitmap(bm);
        return product_image;
    }




    public void SavePrice(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //product_image=imageToString(bitmap);
        product_name = etProductName.getText().toString().trim();
        available_stock = etAvailableStock.getText().toString().trim();
        minimum_stock = etMinStock.getText().toString().trim();
        measurement_unit = addUnit.getSelectedItem().toString();
        purchase_cost = etPurchaseCost.getText().toString().trim();
        sale_cost = etSaleCost.getText().toString().trim();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, ADD_PRICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                makeText(AddPrice.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(AddPrice.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
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



            RequestQueue requestQueue= Volley.newRequestQueue(AddPrice.this);
        requestQueue.add(stringRequest);

    }
    public void UploadImage(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        product_image=imageToString(bitmap);

        StringRequest uploadImage=new StringRequest(Request.Method.POST, UPLOAD_PRODUCT_IMAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("product_image", product_image);
                params.put("product_name", product_name);
                return params;
            }

        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(uploadImage);
    }
}


package com.example.kiranastoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class AddPrice extends AppCompatActivity  {

    //Initialize variables
    Spinner addUnit;
    ImageView ivProduct;
    Button btnSavePrice,btnCancelPrice;
    EditText etProductName,etAvailableStock,etMinStock,etPurchaseCost,etSaleCost;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;

    String product_image,product_name,available_stock,minimum_stock,measurement_unit,purchase_cost,sale_cost;
    String ADD_PRICE_URL = "http://gurungonlineshopping.com/StoreManagement/add_price.php";
    String UPLOAD_PRODUCT_IMAGE_URL = "http://gurungonlineshopping.com/StoreManagement/UploadProductImage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_price);

        etProductName=findViewById(R.id.etProductName);
        etAvailableStock=findViewById(R.id.etStockAvailable);
        etMinStock=findViewById(R.id.etMinStockAvailable);
        etPurchaseCost=findViewById(R.id.etPurchaseCost);
        etSaleCost=findViewById(R.id.etSaleCost);
        btnSavePrice=findViewById(R.id.btnSavePrice);
        btnCancelPrice=findViewById(R.id.btnCancelPrice);



        btnSavePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePrice();
            }
        });

        btnCancelPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ivProduct=findViewById(R.id.ivProduct);

        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseImage();
            }
        });

        addUnit=findViewById(R.id.spAddMeasurementUnit);
        List<String> categories= new ArrayList<>();
        categories.add(0,"Pcs");
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
        addUnit.setAdapter(dataAdapter);

        addUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pcs"))
                {
                    // Do nothing
                }
                else
                {
                    // On selecting a spinner item

                    String item= parent.getItemAtPosition(position).toString();

                    //Show selected spinner item

                    //Anything else you want to do on item selection do here
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO Auto-generated method stub

            }
        });
    }

    private void  ChooseImage(){

        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(AddPrice.this);
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

            Uri uri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivProduct.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bm){
        /*Drawable drawable=this.getResources().getDrawable(R.drawable.camera48);
        bitmap=((BitmapDrawable)drawable).getBitmap();
        ivProduct.setImageBitmap(bitmap);
        Drawable d= new BitmapDrawable(this.getResources(),bitmap);
        ivProduct.setImageDrawable(d);
        bitmap = BitmapFactory.decodeResource(getResources(), R.id.ivProduct);
        bitmap = ((BitmapDrawable) ivProduct.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = ((BitmapDrawable) ivProduct.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        product_image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //ivProduct.setImageBitmap(bm);
        return product_image;
    }




    public void SavePrice(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        product_image=imageToString(bitmap);
        product_name = etProductName.getText().toString().trim();
        available_stock = etAvailableStock.getText().toString().trim();
        minimum_stock = etMinStock.getText().toString().trim();
        measurement_unit = addUnit.getSelectedItem().toString();
        purchase_cost = etPurchaseCost.getText().toString().trim();
        sale_cost = etSaleCost.getText().toString().trim();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, ADD_PRICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                makeText(AddPrice.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(AddPrice.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("product_image", product_image);
                params.put("product_name", product_name);
                params.put("available_stock", available_stock);
                params.put("min_stock", minimum_stock);
                params.put("measurement_unit", measurement_unit);
                params.put("purchase_cost", purchase_cost);
                params.put("sale_cost",sale_cost );
                return params;
            }

        };



            RequestQueue requestQueue= Volley.newRequestQueue(AddPrice.this);
        requestQueue.add(stringRequest);

    }
    public void UploadImage(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        product_image=imageToString(bitmap);

        StringRequest uploadImage=new StringRequest(Request.Method.POST, UPLOAD_PRODUCT_IMAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();
                params.put("product_image", product_image);
                params.put("product_name", product_name);
                return params;
            }

        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(uploadImage);
    }
}

