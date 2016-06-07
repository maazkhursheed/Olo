package com.example.maaz.olo.screens;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.maaz.olo.R;
import fragments.OrderCheck_EditListFragment;
import fragments.OrderCheck_SimpleListFragment;

public class OrderCheckoutScreen extends AppCompatActivity {

    private Toolbar toolbar;
    TextView editOrderTv;

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

        OrderCheck_SimpleListFragment simpleListFragment = new OrderCheck_SimpleListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.orderframe_container, simpleListFragment);
        transaction.commit();
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.orderframe_container, simpleListFragment).commit();

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

        menu.findItem(R.id.cart_text).setTitle("Rs:"+String.valueOf(Item_Detailed_Screen.total_cart_bill));
        return super.onPrepareOptionsMenu(menu);
    }


    private class EditableOrderItemListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            OrderCheck_EditListFragment editListFragment = new OrderCheck_EditListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            OrderCheck_SimpleListFragment simpleListFragment = new OrderCheck_SimpleListFragment();
            transaction.hide(simpleListFragment);
            transaction.replace(R.id.orderframe_container, editListFragment);
            transaction.commit();
        }
    }
}
