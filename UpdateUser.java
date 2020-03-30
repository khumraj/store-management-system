package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kiranastoremanagement.AdapterClass.SharedPreManager;
import com.example.kiranastoremanagement.Model.User;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class UpdateUser extends AppCompatActivity {
    EditText etFirstName,etLastName,etStoreName,etEmailId,etMobileNumber,etLocation;
    Button btnUpdateUser;

    int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        if (SharedPreManager.getInstance(UpdateUser.this).isLoggedIn()) {
            etFirstName = findViewById(R.id.etFname);
            etLastName = findViewById(R.id.etLname);
            etStoreName = findViewById(R.id.et_store_name);
            etEmailId = findViewById(R.id.etID);
            etMobileNumber = findViewById(R.id.etMobileNumber);
            etLocation = findViewById(R.id.etLocation);
            btnUpdateUser = findViewById(R.id.btnUpdateUser);


            User user = SharedPreManager.getInstance(UpdateUser.this).getUser();
            userId=user.getId();
            etFirstName.setText(user.getFirst_name());
            etLastName.setText(user.getLast_name());
            etStoreName.setText(user.getStore_name());
            etEmailId.setText(user.getEmail_id());
            etMobileNumber.setText(user.getMobile_number());
            etLocation.setText(user.getLocation());

        }else {
            Intent intent = new Intent(UpdateUser.this,MainActivity.class);

            startActivity(intent);
            finish();
        }

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUpdate();
            }
        });
    }

    public void userUpdate(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
       final String firstName=etFirstName.getText().toString().trim();
       final String lastName=etLastName.getText().toString().trim();
       final String storeName=etStoreName.getText().toString().trim();
       final String emailId=etEmailId.getText().toString().trim();
       final String mobileNumber=etMobileNumber.getText().toString().trim();
       final String location=etLocation.getText().toString().trim();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.UPDATE_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                makeText(UpdateUser.this, response, LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeText(UpdateUser.this, error.toString(), LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("userId",String.valueOf(userId));
                params.put("first_name",firstName);
                params.put("last_name",lastName);
                params.put("store_name",storeName);
                params.put("email_id",emailId);
                params.put("mobile_number",mobileNumber);
                params.put("location",location);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(UpdateUser.this);
        requestQueue.add(stringRequest);
    }
}
