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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class UpdateCustomer extends AppCompatActivity {

    //Initialize variables
    CircleImageView civCustomerImage;
    EditText etCustomerName,etCustomerAddress,etPhoneNumber,etCustomerBalance,etCustomerNotes;
    Button btnUpdateCustomer,btnCancelCustomer;
    RadioGroup rgUpdateCustomerStatus;
    RadioButton rbCustomerPayable,rbCustomerReceivable;
    String customerImage,customerName,customerAddress,customerNumber,customerBalance,customerStatus,customerNotes;
    int customerId, counter;

    Bundle bundle;
    int position;



    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);

        //Defining variables
        civCustomerImage=findViewById(R.id.civUpdateCustomerImage);
        etCustomerName=findViewById(R.id.etUpdateCustomerName);
        etCustomerAddress=findViewById(R.id.etUpdateCustomerAddress);
        etPhoneNumber=findViewById(R.id.etUpdateCustomerPhoneNo);
        etCustomerBalance=findViewById(R.id.etUpdateCustomerBalance);
        etCustomerNotes=findViewById(R.id.etUpdateCustomerNotes);

        rgUpdateCustomerStatus=findViewById(R.id.radioGroupUpdateCustomer);
        rbCustomerPayable=findViewById(R.id.rbUpdateCustomerPayable);
        rbCustomerReceivable=findViewById(R.id.rbUpdateCustomerReceivable);

        btnUpdateCustomer=findViewById(R.id.btnUpdateCustomer);
        btnCancelCustomer=findViewById(R.id.btnCancelUpdateCustomer);

        //Method calling to main method
        customerDataRetrieve();
        setCustomerData();
        radioGroup();



        //function for buttons (Setting onclick listener)
        btnUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCustomer();

            }
        });


        btnCancelCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void radioGroup(){
        rgUpdateCustomerStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbUpdateCustomerPayable:
                        customerStatus="Payable";
                        break;

                    case  R.id.rbUpdateCustomerReceivable:
                        customerStatus="Receivable";

                        break;
                }
            }
        });

        civCustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
    }

    //Retrieve data from another activity
    public void customerDataRetrieve(){
        Intent intent=getIntent();
        customerId=intent.getIntExtra("customerId",0);
        customerImage=intent.getStringExtra("customerImage");
        customerName=intent.getStringExtra("customerName");
        customerAddress=intent.getStringExtra("customerAddress");
        customerBalance=intent.getStringExtra("customerBalance");
        customerNumber=intent.getStringExtra("customerPhoneNumber");
        customerStatus=intent.getStringExtra("customerStatus");
        customerNotes=intent.getStringExtra("customerNotes");


    }
    //Setting customer data in their respective fields
    public void setCustomerData(){

        if (TextUtils.isEmpty(customerImage)) {

            Picasso.with(UpdateCustomer.this).cancelRequest(civCustomerImage);
            civCustomerImage.setImageDrawable(ContextCompat.getDrawable(UpdateCustomer.this, R.drawable.product96));
        } else {
            Picasso.with(UpdateCustomer.this)
                    .load(customerImage)
                    .placeholder(R.drawable.product96)
                    .noFade()
                    .into(civCustomerImage);
        }
        etCustomerName.setText(customerName);
        etCustomerAddress.setText(customerAddress);
        etPhoneNumber.setText(customerNumber);
        etCustomerBalance.setText(customerBalance);
        etCustomerNotes.setText(customerNotes);



    }

    private void  ChooseImage(){

        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateCustomer.this);
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
                civCustomerImage.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
            UpdateCustomerImage();
        }
    }


    private String imageToString(Bitmap bm){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap = ((BitmapDrawable) civCustomerImage.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        //ivUpdateProduct.setImageBitmap(bm);
        return encodedImage;
    }

    public void UpdateCustomer(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();
        //product_image=imageToString(bitmap);
        customerName = etCustomerName.getText().toString().trim();
        customerAddress= etCustomerAddress.getText().toString().trim();
        customerNumber = etPhoneNumber.getText().toString().trim();
        customerBalance = etCustomerBalance.getText().toString().trim();
        customerNotes = etCustomerNotes.getText().toString().trim();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, URLs.UPDATE_CUSTOMER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                makeText(UpdateCustomer.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(UpdateCustomer.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("customer_id",Integer.toString(customerId));
                params.put("customer_name", customerName);
                params.put("customer_address", customerAddress);
                params.put("customer_number", customerNumber);
                params.put("customer_balance", customerBalance);
                params.put("customer_status", customerStatus);
                params.put("customer_notes",customerNotes );
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(UpdateCustomer.this);
        requestQueue.add(stringRequest);

    }
    public void UpdateCustomerImage(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Updating image...");
        progressDialog.show();

        final String CustomerImage=imageToString(bitmap);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.UPDATE_CUSTOMER_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                makeText(UpdateCustomer.this, response, LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(UpdateCustomer.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("customerId",Integer.toString(customerId));
                params.put("customer_image", CustomerImage);
                return params;
            }
        };

        //int socketTimeout = 30000;
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue= Volley.newRequestQueue(UpdateCustomer.this);
        requestQueue.add(stringRequest);
    }
}
