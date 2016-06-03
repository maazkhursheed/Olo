package com.example.maaz.olo.screens;

import android.content.Intent;
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
public class DetailScreen extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView label_itemName,
                     label_itemPrice,
                     label_incrementQuantity,
                     label_decrementQuantity,
                     label_totalPrice,
                     label_quantity;

    private Button button_addCart;
    private double itemPrice;
    static  double totalprice;
    static double total_cart_bill;
    private boolean valuechanged=false;
    private int minQuantity=1,maxQuantity=20;
    private int selectedItemQuantity =1;
    private String itemname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailed_item_screen_withpicker);

        getIntentValues();
        initViews();



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


//=======================================Helper Methods=======================================================
    public void setHomeButton()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_details_item_screen);

        button_addCart = (Button) findViewById(R.id.addcart_btn);
        label_itemName = (TextView) findViewById(R.id.textView_itemname);
        label_itemPrice = (TextView) findViewById(R.id.textView_itemPrice);
        label_incrementQuantity = (TextView) findViewById(R.id.add_txtview);
        label_decrementQuantity = (TextView) findViewById(R.id.subtract_txtview);
        label_totalPrice = (TextView) findViewById(R.id.total_price);
        label_quantity = (TextView) findViewById(R.id.itemquantity_txtview);
        selectedItemQuantity=minQuantity;
        label_quantity.setText("Quantity: "+selectedItemQuantity);


        label_itemName.setText(itemname);
        label_itemPrice.setText("Rs: "+ String.valueOf(itemPrice));
        totalprice= itemPrice;


        setHomeButton();

        setInitial_itemPrice();

        setListeners();
    }

    private void setListeners() {
        button_addCart.setOnClickListener(new AddCartListener());
        label_incrementQuantity.setOnClickListener(new IncrementListener());
        label_decrementQuantity.setOnClickListener(new DecrementListener());
    }



    /**This method receives intent values from previous screen and set them in
     * appropriate instance variables
     *
     */
    private void getIntentValues() {

        itemname=  getIntent().getExtras().getString(IntentsConstants.item_name);
        itemPrice = getIntent().getExtras().getDouble(IntentsConstants.item_price);

    }

    //set initial total price total price in txtview when activity created
    private void setInitial_itemPrice()
    {

        calcTotalPrice(selectedItemQuantity, itemPrice);
        if(label_totalPrice.getText().equals(""))
        {
            label_totalPrice.setText("" + itemPrice);
        }

    }






   private double calcTotalPrice(int quantity, double itemprice ){

        return quantity*itemprice;
    }







//==========================================Inner Classes / Listeners=========================================
    private class AddCartListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            total_cart_bill +=totalprice;

//            Intent intent = new Intent(DetailScreen.this,MainActivity.class);
//            startActivity(intent);

            finish();
        }
    }

    private class IncrementListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(selectedItemQuantity==minQuantity||selectedItemQuantity <maxQuantity)
            {
                selectedItemQuantity++;
                label_quantity.setText("Quantity :"+selectedItemQuantity);
                totalprice= calcTotalPrice(selectedItemQuantity, itemPrice);
                label_totalPrice.setText(""+totalprice);

            }
        }
    }

    private class DecrementListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(selectedItemQuantity>minQuantity)
            {

                selectedItemQuantity--;
                //selectedItemQuantity=minQuantity;
                label_quantity.setText("Quantity :"+selectedItemQuantity);
                totalprice= calcTotalPrice(selectedItemQuantity, itemPrice);
                label_totalPrice.setText(""+totalprice);

            }
        }
    }
}
