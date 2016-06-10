package com.example.maaz.olo.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import cart.ItemCart;
import com.example.maaz.olo.R;
import fragments.OrderCheckEditListFragment;
import fragments.OrderCheckSimpleListFragment;

public class OrderCheckoutScreen extends AppCompatActivity {

    private Toolbar toolbar;
    TextView editOrderTv, applyOrderChanges, addProduct, orderSubTotal, orderAllTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_checkout_screen);

        toolbar= (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        editOrderTv = (TextView)findViewById(R.id.tv_editOrder);
        editOrderTv.setOnClickListener(new EditableOrderItemListner());

        applyOrderChanges = (TextView)findViewById(R.id.tv_editApplyOrder);
        applyOrderChanges.setOnClickListener(new EditApplyOrderListner());

        addProduct = (TextView)findViewById(R.id.addProductBtn);
        addProduct.setOnClickListener(new AddProductListner());

        orderSubTotal = (TextView)findViewById(R.id.tv_subTotal);
        int subTotal = (int) ItemCart.getInstance().getTotal();
        orderSubTotal.setText(Integer.toString(subTotal));

        orderAllTotal = (TextView)findViewById(R.id.tv_allTotal);
        int allTotal = (int) ItemCart.getInstance().getAllTotal();
        orderAllTotal.setText(Integer.toString(allTotal));

        OrderCheckSimpleListFragment simpleListFragment = new OrderCheckSimpleListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.orderframe_container, simpleListFragment);
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.cart_text).setTitle("Rs:"+ ItemCart.getInstance().getTotal());
        return super.onPrepareOptionsMenu(menu);
    }


    private class EditableOrderItemListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            editOrderTv.setVisibility(v.GONE);
            applyOrderChanges.setVisibility(v.VISIBLE);

            OrderCheckEditListFragment editListFragment = new OrderCheckEditListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            OrderCheckSimpleListFragment simpleListFragment = new OrderCheckSimpleListFragment();
            transaction.hide(simpleListFragment);
            transaction.replace(R.id.orderframe_container, editListFragment);
            transaction.commit();
        }
    }

    private class EditApplyOrderListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            editOrderTv.setVisibility( v.VISIBLE);
            applyOrderChanges.setVisibility(v.GONE);

            OrderCheckSimpleListFragment simpleListFragment = new OrderCheckSimpleListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
            OrderCheckEditListFragment editListFragment = new OrderCheckEditListFragment();
            transaction.hide(editListFragment);
            transaction.replace(R.id.orderframe_container, simpleListFragment);
            transaction.commit();
        }
    }

    private class AddProductListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}
