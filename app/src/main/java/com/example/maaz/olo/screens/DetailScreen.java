package com.example.maaz.olo.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.maaz.olo.R;
import utils.IntentsConstants;

/**This is detail screen of meal items
 *
 */
public class DetailScreen extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView item_name,item_price,add_quantity,subtract_quantity, quantity_totalprice,item_quantity;
    private Button add_cart_btn;
    private double itemprice;
    static  double totalprice;
    static double total_cart_bill;
    private boolean valuechanged=false;
    private int minQuantity=1,maxQuantity=20;
    private int selectedItemQuantity =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      setContentView(R.layout.detailed_item_screen_withpicker);


        initViews();
        setHomeButton();
        get_set_intentdata();
        setInitial_itemPrice();

        send_data_tomainmenu();



    }
    public void setHomeButton()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void initViews()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar_details_item_screen);
    //   quantity_picker= (NumberPicker) findViewById(R.id.quantity_numberPicker);
        add_cart_btn= (Button) findViewById(R.id.addcart_btn);
        item_name= (TextView) findViewById(R.id.textView_itemname);
        item_price= (TextView) findViewById(R.id.textView_itemPrice);
        add_quantity= (TextView) findViewById(R.id.add_txtview);
       subtract_quantity= (TextView) findViewById(R.id.subtract_txtview);
        quantity_totalprice = (TextView) findViewById(R.id.total_price);
        item_quantity= (TextView) findViewById(R.id.itemquantity_txtview);
        selectedItemQuantity=minQuantity;
        item_quantity.setText("Quantity :"+selectedItemQuantity);
        add_quantity.setOnClickListener(this);
        subtract_quantity.setOnClickListener(this);
//        quantity_picker.setMinValue(1);
//        quantity_picker.setMaxValue(10);
//        quantity_picker.setWrapSelectorWheel(false);


    }
    private void send_data_tomainmenu()
    {
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                    total_cart_bill +=totalprice;
                   // Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                   // startActivity(intent);
                    finish();

//
//
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
    //set initial total price total price in txtview when activity created
    private void setInitial_itemPrice()
    {
        if(quantity_totalprice.getText().equals(""))
        {
            quantity_totalprice.setText("" + itemprice);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//     increemnent quantity here
            case R.id.add_txtview:
                if(selectedItemQuantity==minQuantity||selectedItemQuantity <maxQuantity)
                {
                    selectedItemQuantity++;
                    item_quantity.setText("Quantity :"+selectedItemQuantity);
                   totalprice= calcTotalPrice(selectedItemQuantity,itemprice);
                    quantity_totalprice.setText(""+totalprice);

                }
                break;
//     decreemnent quantity  here
            case R.id.subtract_txtview:


                if(selectedItemQuantity>minQuantity)
                {

                    selectedItemQuantity--;
                    //selectedItemQuantity=minQuantity;
                    item_quantity.setText("Quantity :"+selectedItemQuantity);
                    totalprice= calcTotalPrice(selectedItemQuantity,itemprice);
                    quantity_totalprice.setText(""+totalprice);

                }
                break;


        }



    }
}
