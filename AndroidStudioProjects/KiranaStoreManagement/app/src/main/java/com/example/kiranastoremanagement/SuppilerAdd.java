package com.example.kiranastoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import faranjit.currency.edittext.CurrencyEditText;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class SuppilerAdd extends AppCompatActivity {
    //Initialize  variable
    Toolbar tAddSupplier;
    Button btnAddSupplier, btnIncreaseBalance, btnDecreaseBalance, btnCancelSupplier;
    EditText etStoreName, etSupplierName,etSupplierAddress,etMobileNumber,etSupplierBalance,etSupplierNotes;
    RadioGroup rgSupplier;
    RadioButton rbSupplierPayable, rbSupplierReceivable;
    CircleImageView civSupplier;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    Intent intent;
    Bitmap bitmap;
    int counter=0;
    String supplier_image,store_name,supplier_name,supplier_address,mobile_number,supplier_balance,supplier_status,supplier_notes;
    String ADD_SUPPLIER_URL = "http://gurungonlineshopping.com/StoreManagement/add_supplier.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppiler_add);

        //Defining Section
        tAddSupplier= findViewById(R.id.tSupplieradd);
        btnAddSupplier= findViewById(R.id.btnAddSupplier);
        setSupportActionBar(tAddSupplier);
        etStoreName=(EditText)findViewById(R.id.etStoreName);
        etSupplierName=(EditText)findViewById(R.id.etSupplierName);
        etSupplierAddress=(EditText)findViewById(R.id.etSupplierAddress);
        etMobileNumber=(EditText)findViewById(R.id.etSupplierPhoneNo);
        etSupplierBalance=findViewById(R.id.etSupplierBalance);
        etSupplierNotes=findViewById(R.id.etSupplierNotes);

        rgSupplier=findViewById(R.id.radioGroupSupplier);
        rbSupplierPayable=findViewById(R.id.rbSupplierPayable);
        rbSupplierReceivable=findViewById(R.id.rbSupplierReceivable);


        civSupplier=findViewById(R.id.civSupplier);
        //bitmap = ((BitmapDrawable)civSupplier.getDrawable()).getBitmap();
        btnDecreaseBalance=findViewById(R.id.btnSupSubstract);
        btnIncreaseBalance=findViewById(R.id.btnSupSum);
        btnCancelSupplier=findViewById(R.id.btnCancelSupplier);

        etSupplierBalance.addTextChangedListener(tw);

        btnAddSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SuppilerAdd.this);
                builder.setTitle("Save Information");
                builder.setMessage("Do you want to save the supplier's information?");
                builder.setPositiveButton("YES",   new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SaveSupplier(); //Function that saves your the data.
                    }
                });
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();// Finish Activity.
                            }
                        });
                builder.create();
                builder.show();

            }
        });

        rgSupplier.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbSupplierPayable:
                        supplier_status="Payable";
                        break;
                    case  R.id.rbSupplierReceivable:
                        supplier_status="Receivable";
                        break;
                }

            }
        });

        civSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
                EnableRuntimePermissionToAccessCamera();

            }
        });


        btnIncreaseBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter= counter+1;
                etSupplierBalance.setText(String.valueOf(counter));

            }
        });

        btnDecreaseBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter= counter-1;
                etSupplierBalance.setText(String.valueOf(counter));

            }
        });

        btnCancelSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });


    }

    TextWatcher tw = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!s.toString().matches("^\\Rs.(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?Rs.")) {
                String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                    cashAmountBuilder.deleteCharAt(0);
                }
                while (cashAmountBuilder.length() < 3) {
                    cashAmountBuilder.insert(0, '0');
                }
                cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                etSupplierBalance.removeTextChangedListener(this);
                etSupplierBalance.setText(cashAmountBuilder.toString());

                etSupplierBalance.setTextKeepState("Rs." + cashAmountBuilder.toString());
                Selection.setSelection(etSupplierBalance.getText(), cashAmountBuilder.toString().length() + 1);

                etSupplierBalance.addTextChangedListener(this);
            }
        }
    };

    //Method for image selection
    private void  ChooseImage(){

        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(SuppilerAdd.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA );

                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent();

                    intent.setType("image/*");

                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {

                // Adding captured image in bitmap.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                // adding captured image in imageView.
                civSupplier.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                civSupplier.setImageBitmap(bitmap);
                //civSupplier.setImageDrawable();


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bmp){
        bitmap = ((BitmapDrawable) civSupplier.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    // Requesting runtime permission to access camera.
    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(SuppilerAdd.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(SuppilerAdd.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(SuppilerAdd.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

        }
    }
    //creating supplier details using addSuppliers method (Onclick method)
    public void SaveSupplier() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        supplier_image=imageToString(bitmap);
        store_name = etStoreName.getText().toString().trim();
        supplier_name = etSupplierName.getText().toString().trim();
        supplier_address = etSupplierAddress.getText().toString().trim();
        mobile_number = etMobileNumber.getText().toString().trim();
        supplier_balance=etSupplierBalance.getText().toString();
        supplier_notes=etSupplierNotes.getText().toString().trim();


        StringRequest stringRequest= new StringRequest(Request.Method.POST, ADD_SUPPLIER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
               /* etStoreName.setText("");
                etSupplierName.setText("");
                etSupplierAddress.setText("");
                etMobileNumber.setText("");*/

                makeText(SuppilerAdd.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(SuppilerAdd.this, error.getMessage().toString(), LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<String, String>();

                params.put("supplier_image", supplier_image);
                params.put("store_name", store_name);
                params.put("supplier_name", supplier_name);
                params.put("supplier_address", supplier_address);
                params.put("mobile_number", mobile_number);
                params.put("supplier_balance", supplier_balance);
                params.put("supplier_status", supplier_status);
                params.put("supplier_notes", supplier_notes);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(SuppilerAdd.this);
        requestQueue.add(stringRequest);


    }
}
