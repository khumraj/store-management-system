package com.example.kiranastoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import faranjit.currency.edittext.CurrencyEditText;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class CustomerAdd extends AppCompatActivity {

    CircleImageView civCustomer;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Button btnIncreaseBalance, btnDecreaseBalance, btnAddCustomer, btnCancelCustomer;

    EditText etCustomerName, etCustomerAddress, etCustomerNumber,etCustomerNotes,etBalance;
    RadioGroup rgCustomer;
    RadioButton rbPayable, rbReceivable;
    int counter = 0;

    Intent intent ;

    //String customer_balance;
    String customer_balance;

    Bitmap bitmap;

    String customer_status;

    String customer_image,customer_name, customer_address, customer_number, customer_notes;
    String ADD_CUSTOMER_URL = "http://gurungonlineshopping.com/StoreManagement/add_customer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);

        rgCustomer=findViewById(R.id.radioGroupCustomer);
        rbPayable=findViewById(R.id.rbPayable);
        rbReceivable=findViewById(R.id.rbReceivable);

        etCustomerName=findViewById(R.id.etCustomerName);
        etCustomerAddress=findViewById(R.id.etCustomerAddress);
        etCustomerNumber=findViewById(R.id.etCustomerPhoneNo);
        etCustomerNotes=findViewById(R.id.etCustomerNotes);

        civCustomer = findViewById(R.id.civCustomer);

        btnDecreaseBalance = findViewById(R.id.btnSubstract);
        btnIncreaseBalance = findViewById(R.id.btnSum);
        etBalance = findViewById(R.id.etCustomerBalance);

        btnAddCustomer = findViewById(R.id.btnCustomerAdd);
        btnCancelCustomer = findViewById(R.id.btnCancelCustomer);

       // Currency symbol
        etBalance.addTextChangedListener(tw);


        rgCustomer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbPayable:
                        customer_status="Payable";
                    break;

                    case  R.id.rbReceivable:
                        customer_status="Receivable";

                    break;
                }
            }
        });

        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerAdd.this);
                builder.setTitle("Save Information");
                builder.setMessage("Do you want to save the customer's information?");
                builder.setPositiveButton("YES",   new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SaveCustomer(); //Function that saves your the data.
                    }
                });
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Finish Activity.
                            }
                        });
                builder.create();
                builder.show();
                //SaveCustomer();
            }
        });

        btnCancelCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        btnIncreaseBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = counter + 1;
                etBalance.setText(String.valueOf(counter));
            }
        });

        btnDecreaseBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = counter - 1;
                etBalance.setText(String.valueOf(counter));

            }
        });

        civCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();


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

                etBalance.removeTextChangedListener(this);
                etBalance.setText(cashAmountBuilder.toString());

                etBalance.setTextKeepState("Rs." + cashAmountBuilder.toString());
                Selection.setSelection(etBalance.getText(), cashAmountBuilder.toString().length() + 1);

                etBalance.addTextChangedListener(this);
            }
        }
    };




    // Method for camera and image choose from gallery
    private void ChooseImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerAdd.this);
        builder.setTitle("Add Image");
        //EnableRuntimePermissionToAccessCamera();
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

            Uri selectedImageUri = data.getData();

            try {

                // Adding captured image in bitmap.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                // adding captured image in imageView.
                civCustomer.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                civCustomer.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Requesting runtime permission to access camera.
    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(CustomerAdd.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(CustomerAdd.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(CustomerAdd.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

        }
    }

    private String imageToString(Bitmap bm){
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    public void SaveCustomer() {

    final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        customer_image=imageToString(bitmap);
        customer_name=etCustomerName.getText().toString();
        customer_address=etCustomerAddress.getText().toString();
        customer_number=etCustomerNumber.getText().toString();
        customer_balance=etBalance.getText().toString();
        customer_notes=etCustomerNotes.getText().toString();


    StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_CUSTOMER_URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            progressDialog.dismiss();


            makeText(CustomerAdd.this, response, LENGTH_SHORT).show();

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            makeText(CustomerAdd.this, error.getMessage().toString(), LENGTH_SHORT).show();

        }
    }
    ) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();

            params.put("customer_image", customer_image);
            params.put("customer_name", customer_name);
            params.put("customer_address", customer_address);
            params.put("customer_number", customer_number);
            params.put("customer_balance", customer_balance);
            params.put("customer_status", customer_status);
            params.put("customer_notes", customer_notes);
            return params;
        }
    };

    RequestQueue requestQueue = Volley.newRequestQueue(CustomerAdd.this);
        requestQueue.add(stringRequest);

    }


}
