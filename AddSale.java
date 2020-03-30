package com.example.kiranastoremanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kiranastoremanagement.AdapterClass.Customer;
import com.example.kiranastoremanagement.AdapterClass.CustomerAdapter;
import com.example.kiranastoremanagement.AdapterClass.Product;
import com.example.kiranastoremanagement.AdapterClass.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rey.material.app.Dialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

public class AddSale extends OptionsMenuActivity implements DatePickerDialog.OnDateSetListener{
    private ArrayList<Customer>customerArrayList;
    private ArrayList<String> customerName=new ArrayList<String>();
    private ArrayList<TableRow>tableRows=new ArrayList<>();
   private ArrayList<TableLayout>tableLayouts=new ArrayList<TableLayout>();


    ImageView ivCalender;
    EditText etAmountReceive,etDiscount,etQty;
    TextView tvDateTime,tvSubTotal,tvGrandTotal,tvBalance,tvProductName,tvProductPrice;
    Button btnQtyIncrease,btnQtyDecrease,btnSaveSale,btnCancelSale;
    FloatingActionButton fabAddToBill,fabAddNewCustomer,fabAddProduct;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;




    //private String UnitPrice,Quantity;

    //An ArrayList for Spinner Items
    private ArrayList<String> data1=new ArrayList<String>();
    private ArrayList<String> data2=new ArrayList<String>();
    private ArrayList<String> data3=new ArrayList<String>();
    private ArrayList<String> data4=new ArrayList<String>();



    private String product_name,product_price,quantity;
    double productPrice;
    double grandTotal;
    double Discount=0;



    int productQty;

    private int pId=1;


    SearchableSpinner spCustomerName;


    private TableLayout AddSaleTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        tvSubTotal=findViewById(R.id.tvSubTotal);
        tvGrandTotal=findViewById(R.id.tvGrandTotal);
        tvBalance=findViewById(R.id.tvBalance);
        spCustomerName=findViewById(R.id.spCustName);
        tvProductName=findViewById(R.id.tvSaleProductName);
        tvProductPrice=findViewById(R.id.tvSaleUnitPrice);
        etQty=findViewById(R.id.etSaleQuantity);
        fabAddProduct=findViewById(R.id.fabAddProducts);



        etAmountReceive=findViewById(R.id.etAmountReceive);
        etDiscount=findViewById(R.id.etDiscount);




        etDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                double subTotal=Double.parseDouble(tvSubTotal.getText().toString());
                double discount=Double.parseDouble(etDiscount.getText().toString());
                grandTotal=subTotal-discount;
                tvGrandTotal.setText(String.valueOf(grandTotal));



            }
        });


        etAmountReceive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                double amountReceive=Double.parseDouble(etAmountReceive.getText().toString());

                double balance=amountReceive-grandTotal;
                tvBalance.setText(String.valueOf(balance));
            }
        });

        customerArrayList=new ArrayList<Customer>();



        populateCustomerName();


        Intent intent=getIntent();
        int productId=intent.getIntExtra("product_id",0);
        final String productName=intent.getStringExtra("product_name");
        productPrice=intent.getDoubleExtra("unit_price",0);

        product_price=Double.toString(productPrice);

        tvProductName.setText(productName);
        tvProductPrice.setText(product_price);




        ivCalender=findViewById(R.id.ivCalender);
        tvDateTime=findViewById(R.id.tvDateTime);

        btnSaveSale=findViewById(R.id.btnSaveSale);
        btnCancelSale=findViewById(R.id.btnCancelSale);
        btnSaveSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });





        //Set default date and time
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        tvDateTime.setText(currentDate);
        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerHelper();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        fabAddToBill=findViewById(R.id.fabAddToBill);
        fabAddToBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addSale();

            }
        });

        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addSale= new Intent(AddSale.this,SaleProducts.class);
                startActivity(addSale);

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
        tvDateTime.setText(currentDateString);
    }


    public void addSale(){
        double  totalAmount;
        //final String ProductQuantity=Integer.toString(productQty);


        product_name=tvProductName.getText().toString();
        productPrice=Double.parseDouble(tvProductPrice.getText().toString());
        productQty=Integer.parseInt(etQty.getText().toString());
        totalAmount=productQty*productPrice;

        data1.add(product_name);
        data2.add(String.valueOf(productQty));
        data3.add(String.valueOf(productPrice));
        data4.add(String.valueOf(totalAmount));

        TableLayout AddSaleTable=(TableLayout) findViewById(R.id.tb1);
        TableRow tableRow= new TableRow(this);
        TextView tbPName=new TextView(this);
        TextView tbQty=new TextView(this);
        TextView tbUnitPrice=new TextView(this);
        TextView tbTotal=new TextView(this);

        String total_amount;
        double sum= 0;




        for (int i=0; i<data1.size();i++) {
            String pName = data1.get(i);
            String Quantity = data2.get(i);
            String pPrice = data3.get(i);
            total_amount = data4.get(i);


            tbPName.setText(pName);
            tbPName.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tbPName.setGravity(Gravity.CENTER);

            tbQty.setText(Quantity);
            tbQty.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tbQty.setGravity(Gravity.CENTER);

            tbUnitPrice.setText(pPrice);
            tbUnitPrice.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tbUnitPrice.setGravity(Gravity.CENTER);

            tbTotal.setText(total_amount);
            tbTotal.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tbTotal.setGravity(Gravity.CENTER);

            sum = sum + Double.parseDouble(data4.get(i).toString());

        }

            tableRow.addView(tbPName);
            tableRow.addView(tbQty);
            tableRow.addView(tbUnitPrice);
            tableRow.addView(tbTotal);
            tableRow.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    tableRows.add(tableRow);
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {

                }
            });
            tableRow.setBackgroundResource(R.color.design_default_color_primary);
            AddSaleTable.addView(tableRow);
            tableLayouts.add(AddSaleTable);

            tvSubTotal.setText(String.valueOf(sum));
            etDiscount.setText(String.valueOf(Discount));
            tvGrandTotal.setText(String.valueOf(sum));
            /*tvProductPrice.setText("");
            tvProductName.setText("");
            etQty.setText("");
            tvProductName.requestFocus();*/



    }


    public void populateCustomerName(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLs.RETRIEVE_CUSTOMER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //converting the string to json array object
                    JSONArray array=new JSONArray(response);

                    //traversing through all the object
                    for (int i=0; i<array.length();i++){

                        //getting customer object from json array
                        JSONObject customer=(JSONObject) array.get(i);

                        //adding the customer data  to customer list
                        Customer customer1=new Customer(Customer.layoutType.CUSTOMER_LIST, customer.getInt("customerId"),
                                customer.getString("customerImage"),
                                customer.getString("customerName"),
                                customer.getString("customerAddress"),
                                customer.getString("customerNumber"),
                                customer.getString("customerBalance"),
                                customer.getString("customerStatus"),
                                customer.getString("customerNotes"));

                              //  customer1.setCustomerId(customer.getInt("customerId"));

                                customer1.setCustomerName( customer.getString("customerName"));
                                customerArrayList.add(customer1);
                    }

                    for (int i = 0; i < customerArrayList.size(); i++){
                        customerName.add(customerArrayList.get(i).getCustomerName().toString());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddSale.this, android.R.layout.simple_spinner_item, customerName);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spCustomerName.setAdapter(spinnerArrayAdapter);


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
   /* private void populateSpinner(){



        for (int i = 0; i < customerArrayList.size(); i++) {
            customerName.add(customerArrayList.get(i).getCustomerName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, customerName);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCustomerName.setAdapter(spinnerAdapter);

        spCustomerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spCustomerName.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO Auto-generated method stub

            }
        });
    }*/

}
