package com.example.maaz.olo.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.example.maaz.olo.R;
import utils.IntentsConstants;
//AppCompatActivity

public class Item_Detailed_Screen extends AppCompatActivity {
//    declare variables
    Toolbar toolbar;
    TextView item_name,item_price,add_quantity,subtract_quantity, quantity_totalprice;
    NumberPicker quantity_picker;
    Button add_cart_btn;
    double itemprice;
   static  double totalprice;
    static double total_cart_bill;
    boolean valuechanged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item__detailed__screen);
        setContentView(R.layout.detailed_item_screen_withpicker);


        init_views();
        setHomeButton();

        get_set_intentdata();
        setInitial_itemPrice();
       // setTotalPrice_withquantity();
      //  getTotalPrice_withquantity();
       // computetotalprice();
        send_data_tomainmenu();
       // getscrollvalue();
        //final double tot=Double.parseDouble(item_price.getText().toString());


    }
    public void setHomeButton()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void init_views()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar_details_item_screen);
        //quantity_picker= (NumberPicker) findViewById(R.id.quantity_numberPicker);
        add_cart_btn= (Button) findViewById(R.id.addcart_btn);
        item_name= (TextView) findViewById(R.id.textView_itemname);
        item_price= (TextView) findViewById(R.id.textView_itemPrice);
        add_quantity= (TextView) findViewById(R.id.add_quantity);
        subtract_quantity= (TextView) findViewById(R.id.subtract_quantity);
        quantity_totalprice = (TextView) findViewById(R.id.total_price);
//        quantity_picker.setMinValue(1);
//        quantity_picker.setMaxValue(10);
//        quantity_picker.setWrapSelectorWheel(false);


    }
    private void send_data_tomainmenu()
    {
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_cart_bill +=totalprice;
               // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               // intent.putExtra("totalprice",String.valueOf(totalprice));
               //setResult(RESULT_OK, intent);
               // startActivityForResult(intent,RESULT_OK);
               // startActivity(intent);
               // startActivityForResult(intent,1);
                //finish();
               // startActivity(intent);
                finish();

            }
        });

    }
    private void get_set_intentdata()
    {
        String itemname_i;
        double itemprice_i;

        itemname_i=getIntent().getExtras().getString(IntentsConstants.item_name);
        itemprice_i=getIntent().getExtras().getDouble(IntentsConstants.item_price);
        item_name.setText(String.valueOf(itemname_i).toString());
        item_price.setText("Rs :"+ String.valueOf(itemprice_i));
        itemprice=itemprice_i;


    }
    ///set total price txtview when activity created
    private void setInitial_itemPrice()
    {
        if(quantity_totalprice.getText().equals(""))
        {
            quantity_totalprice.setText("" + itemprice);
        }

    }
    ///////////set total price in textview according to quantity
//    private void setTotalPrice_withquantity()
//    {
//
//        quantity_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                /// get desired quantity of selected item
//                int quantity=quantity_picker.getValue();
//               totalprice = calcTotalPrice(quantity,itemprice);
//                if (!quantity_totalprice.getText().equals("")) {
//                    ///update totalprice in txtview according to quantity of item
//                    quantity_totalprice.setText("" + totalprice);
//                   // total_cart_bill +=totalprice;
//
//                }
//
//            }
//        });



  //  }

    ///////////set total price without value listner
    private void getTotalPrice_withquantity()
    {
//        final int quantity=quantity_picker.getValue();;
//
//        if (quantity==1)
//        {
//            totalprice=calcTotalPrice(1,itemprice);
//
//        }
//        else {

            quantity_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    valuechanged=true;
                    /// get desired quantity of selected item
                    //  int quantity=quantity_picker.getValue();
//                    totalprice = calcTotalPrice(newVal, itemprice);
//                    if (!quantity_totalprice.getText().equals("")) {
//                        ///update totalprice in txtview according to quantity of item
//                        quantity_totalprice.setText("" + totalprice);
                        // total_cart_bill +=totalprice;

                   // }

                }
            });
       // }
   }
                  public void computetotalprice()
                  {
                      if(valuechanged==true)
                      {
                          int quantity=quantity_picker.getValue();
                          totalprice = calcTotalPrice(quantity,itemprice);
                          if (!quantity_totalprice.getText().equals("")) {
                              ///update totalprice in txtview according to quantity of item
                              quantity_totalprice.setText("" + totalprice);
                          }
                          else
                          {
                              totalprice = calcTotalPrice(1,itemprice);
                           //   Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

                          }

                      }


                  }

   private double calcTotalPrice(int quantity, double itemprice ){

        return quantity*itemprice;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
