package com.example.kiranastoremanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rey.material.app.Dialog;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.criteria.CriteriaBuilder;

public class AddSale extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ImageView ivCalender;
    EditText etAmountReceive,etDiscount,etQuantity,etUnitPrice;
    TextView tvDateTime,tvSubTotal,tvGrandTotal,tvBalance,tvProductName1;
    Button btnQtyIncrease,btnQtyDecrease;
    FloatingActionButton fabAddSale,fabAddNewCustomer;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    private String UnitPrice,Quantity;

    private ArrayList<String> productId=new ArrayList<String>();
    private ArrayList<String> productName=new ArrayList<String>();
    private ArrayList<String> productQty=new ArrayList<String>();
    private ArrayList<String> productUnitPrice=new ArrayList<String>();
    private ArrayList<String> totalProductAmount=new ArrayList<String>();

    private String product_name,product_price;
    int product_stock;

    private int product_id;
    Spinner spCustomerName;

    private TableLayout AddSaleTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        tvSubTotal=findViewById(R.id.tvSubTotal);
        tvGrandTotal=findViewById(R.id.tvGrandTotal);
        tvBalance=findViewById(R.id.tvBalance);

        etAmountReceive=findViewById(R.id.etAmountReceive);
        etDiscount=findViewById(R.id.etDiscount);

        AddSale();

        ivCalender=findViewById(R.id.ivCalender);
        tvDateTime=findViewById(R.id.tvDateTime);





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

        fabAddSale=findViewById(R.id.fabAddSale);
        fabAddSale.setOnClickListener(new View.OnClickListener() {
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

    public void AddSale(){
        int totalAmount=120;
        int ProductPrice;
        int ProductQty;


        Intent intent=getIntent();
        product_id=intent.getIntExtra("product_id",0);
        product_name=intent.getStringExtra("product_name");
        product_price=intent.getStringExtra("unit_price");
        product_stock=intent.getIntExtra("product_stock",0);

        
        //totalAmount=ProductPrice*ProductQty;
        productId.add(String.valueOf(product_id));
        productName.add(product_name);
        productQty.add(String.valueOf(product_stock));
        productUnitPrice.add(product_price);
        totalProductAmount.add(String.valueOf(totalAmount));

        tvSubTotal.setText(product_price);


        TableLayout AddSaleTable=(TableLayout) findViewById(R.id.tbPrice);
        TableRow tableRow= new TableRow(this);
        TextView tbId=new TextView(this);
        TextView tbPName=new TextView(this);
        TextView tbQty=new TextView(this);
        TextView tbUnitPrice=new TextView(this);
        TextView tbTotal=new TextView(this);

        String total_amount;
        int sum= 0;

        for (int j=0; j< productId.size();j++)
        {
            String id=productId.get(j);
            product_name=productName.get(j);
            String product_stock=productQty.get(j);
            product_price=productUnitPrice.get(j);
            total_amount=totalProductAmount.get(j);

            tbId.setText(id);
            tbId.setGravity(Gravity.CENTER);

            tbPName.setText(product_name);
            tbPName.setGravity(Gravity.CENTER);

            tbQty.setText(product_stock);
            tbQty.setGravity(Gravity.CENTER);

            tbUnitPrice.setText(product_price);
            tbUnitPrice.setGravity(Gravity.CENTER);

            tbTotal.setText(total_amount);
            tbTotal.setGravity(Gravity.CENTER);

            sum= sum+Integer.parseInt(totalProductAmount.get(j).toString());

        }
        tableRow.addView(tbId);
        tableRow.addView(tbPName);
        tableRow.addView(tbQty);
        tableRow.addView(tbUnitPrice);
        tableRow.addView(tbTotal);
        tableRow.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {

            }

            @Override
            public void onChildViewRemoved(View parent, View child) {

            }
        });
        tableRow.setBackgroundResource(R.color.design_default_color_primary);
        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    dialog = new AlertDialog.Builder(AddSale.this);
                    inflater = getLayoutInflater();
                    dialogView = inflater.inflate(R.layout.unit_price, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(true);

                    EditText etQuantity   = (EditText) dialogView.findViewById(R.id.etQuantity);
                    EditText etUnitPrice  =(EditText)  dialogView.findViewById(R.id.etUnitPrice);
                    btnQtyIncrease  =dialogView.findViewById(R.id.btnQtyIncrease);
                    btnQtyDecrease =  dialogView.findViewById(R.id.btnQtyDecrease);
                    TextView tvProductName1=(TextView) dialogView.findViewById(R.id.tvProductName1);
                    etQuantity.setText(String.valueOf(product_stock));
                    etUnitPrice.setText(product_price);
                    tvProductName1.setText(product_name);

                    dialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Quantity   = etQuantity.toString();
                            UnitPrice   = etUnitPrice.toString();


                            dialog.dismiss();
                        }
                    });

                    dialog.setNegativeButton("REMOVE", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }

        });
        AddSaleTable.addView(tableRow);


    }


}
