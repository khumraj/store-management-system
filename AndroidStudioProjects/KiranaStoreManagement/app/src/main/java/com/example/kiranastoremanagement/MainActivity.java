package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    //Initialize Variable
    TextView createAccount, forgotPass; Button btnSignIn;
    EditText etUserName, etPassword;
    String email_id, mobile_number, conform_password;
    String SIGN_IN_URL = "http://gurungonlineshopping.com/StoreManagement/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Defining section of variable
        etUserName=(EditText)findViewById(R.id.etUserName);
        etPassword=(EditText)findViewById(R.id.etPassword);
        createAccount=(TextView) findViewById(R.id.tvCreateAccount);
        forgotPass=(TextView)findViewById(R.id.tvForgotPassowrd);
        btnSignIn=(Button) findViewById(R.id.btnLogin);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CreateAccount.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent1);
                Toast.makeText(MainActivity.this, "Forgot Password", Toast.LENGTH_SHORT).show();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                email_id = etUserName.getText().toString().trim();
                mobile_number = etUserName.getText().toString().trim();
                conform_password = etPassword.getText().toString().trim();

               /* if(email_id.isEmpty() || conform_password.isEmpty()){

                    Toast.makeText(MainActivity.this, "Please enter the email id or password.",LENGTH_SHORT);
                    return;

                }*/
                StringRequest stringRequest= new StringRequest(Request.Method.POST, SIGN_IN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        etUserName.setText("");
                        etPassword.setText("");
                        startActivity(new Intent(getApplicationContext(),SignIn.class));
                        makeText(MainActivity.this, response, LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        makeText(MainActivity.this, error.getMessage().toString(), LENGTH_SHORT).show();

                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<String, String>();

                        params.put("email_id", email_id);
                        params.put("mobile_number", mobile_number);
                        params.put("conform_password", conform_password);
                        return params;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);

            }
        });
    }
}
