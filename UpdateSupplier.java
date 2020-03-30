package com.example.kiranastoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kiranastoremanagement.AdapterClass.Supplier;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class UpdateSupplier extends AppCompatActivity {

    //Initialize Variable
    CircleImageView civSupplierImage;
    EditText etStoreName,etSupplierName,etSupplierAddress,etSupplierNumber,etSupplierBalance,etSupplierNotes;
    RadioGroup rgUpdateSupplierStatus;
    RadioButton rbUpdateSupplierPayable,rbUpdateSupplierReceivable;
    Button btnUpdateSupplier,btnCancelSupplier,btnIncrease,btnDecrease;

    Bundle bundle;
    final String UPDATE_SUPPLIER_URL = "http://gurungonlineshopping.com/StoreManagement/UpdateSupplierData.php";
    final String UPDATE_SUPPLIER_IMAGE_URL= "http://gurungonlineshopping.com/StoreManagement/UpdateSupplierImage.php";


    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;

    String supplierImage,storeName,supplierName,supplierAddress,supplierNumber,supplierBalance,supplierStatus,supplierNotes;
    int supplierId,counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_supplier);

        //Declaring variables
        etStoreName=findViewById(R.id.etUpdateStoreName);
        etSupplierName=findViewById(R.id.etUpdateSupplierName);
        etSupplierAddress=findViewById(R.id.etUpdateSupplierAddress);
        etSupplierNumber=findViewById(R.id.etUpdateSupplierPhoneNo);
        etSupplierBalance=findViewById(R.id.etUpdateSupplierBalance);
        etSupplierNotes=findViewById(R.id.etUpdateSupplierNotes);
        civSupplierImage=findViewById(R.id.civUpdateSupplier);

        rgUpdateSupplierStatus=findViewById(R.id.radioGroupUpdateSupplier);
        rbUpdateSupplierPayable=findViewById(R.id.rbUpdateSupplierPayable);
        rbUpdateSupplierReceivable=findViewById(R.id.rbUpdateSupplierReceivable);

        btnUpdateSupplier=findViewById(R.id.btnUpdateSupplier);
        btnCancelSupplier=findViewById(R.id.btnCancelUpdateSupplier);
        btnIncrease=findViewById(R.id.btn_Sup_Sum);
        btnDecrease=findViewById(R.id.btn_Sup_decrease);


        //Method call
        RetrieveSupplierData();
        setSupplierData();
        radioGroup();



        btnUpdateSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSupplierData();

            }
        });

        btnCancelSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = counter + 1;
                etSupplierBalance.setText(String.valueOf(counter));

            }
        });

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = counter - 1;
                etSupplierBalance.setText(String.valueOf(counter));
            }
        });

        civSupplierImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });

    }

    public void radioGroup(){
        rgUpdateSupplierStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbUpdateSupplierPayable:
                        supplierStatus="Payable";
                        break;

                    case  R.id.rbUpdateSupplierReceivable:
                        supplierStatus="Receivable";

                        break;
                }
            }
        });

    }

    public void RetrieveSupplierData(){
        Intent intent=getIntent();
        supplierId=intent.getIntExtra("supplierId",0);
        supplierImage=intent.getStringExtra("supplierImage");
        storeName=intent.getStringExtra("storeName");
        supplierName=intent.getStringExtra("supplierName");
        supplierAddress=intent.getStringExtra("supplierAddress");
        supplierNumber=intent.getStringExtra("supplierNumber");
        supplierBalance=intent.getStringExtra("supplierBalance");
        supplierStatus=intent.getStringExtra("supplierStatus");
        supplierNotes=intent.getStringExtra("supplierNotes");

    }

    public void setSupplierData(){
        if (TextUtils.isEmpty(supplierImage)) {

            Picasso.with(UpdateSupplier.this).cancelRequest(civSupplierImage);
            civSupplierImage.setImageDrawable(ContextCompat.getDrawable(UpdateSupplier.this, R.drawable.product96));
        } else {
            Picasso.with(UpdateSupplier.this)
                    .load(supplierImage)
                    .placeholder(R.drawable.product96)
                    .noFade()
                    .into(civSupplierImage);
        }
        etStoreName.setText(storeName);
        etSupplierName.setText(supplierName);
        etSupplierAddress.setText(supplierAddress);
        etSupplierNumber.setText(supplierNumber);
        etSupplierBalance.setText(supplierBalance);
        etSupplierNotes.setText(supplierNotes);

    }
    private void  ChooseImage(){

        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateSupplier.this);
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
                civSupplierImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
            UpdateSupplierImage();
        }
    }


    private String imageToString(Bitmap bm){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = ((BitmapDrawable) civSupplierImage.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //ivUpdateProduct.setImageBitmap(bm);
        return encodedImage;
    }

    public void UpdateSupplierData(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        storeName = etStoreName.getText().toString().trim();
        supplierName= etSupplierName.getText().toString().trim();
        supplierAddress= etSupplierAddress.getText().toString().trim();
        supplierNumber= etSupplierNumber.getText().toString().trim();
        supplierBalance= etSupplierBalance.getText().toString().trim();
        supplierNotes=etSupplierNotes.getText().toString().trim();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, UPDATE_SUPPLIER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                makeText(UpdateSupplier.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(UpdateSupplier.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("supplier_id",Integer.toString(supplierId));
                params.put("store_name", storeName);
                params.put("supplier_name", supplierName);
                params.put("supplier_address", supplierAddress);
                params.put("supplier_number", supplierNumber);
                params.put("supplier_balance", supplierBalance);
                params.put("supplier_status", supplierStatus);
                params.put("supplier_notes",supplierNotes);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(UpdateSupplier.this);
        requestQueue.add(stringRequest);

    }
    public void UpdateSupplierImage(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating image...");
        progressDialog.show();

        final String SupplierImage=imageToString(bitmap);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, UPDATE_SUPPLIER_IMAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                makeText(UpdateSupplier.this, response, LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(UpdateSupplier.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("supplier_id",Integer.toString(supplierId));
                params.put("supplier_image", SupplierImage);
                return params;
            }
        };

        //int socketTimeout = 30000;
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue= Volley.newRequestQueue(UpdateSupplier.this);
        requestQueue.add(stringRequest);
    }



}
