package com.example.maaz.olo.screens;

import Interfaces.OnItemRemoveListener;
import Interfaces.OnQuantityChangeListener;
import adapters.CategoryAdapter;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import cart.ItemCart;
import com.example.maaz.olo.R;
import fragments.DetailsFragment;
import fragments.MenusFragment;
import fragments.OrderCheckoutFragment;
import models.Category;
import network.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DetailsFragment.OnDetailFragmentInteraction, OnItemRemoveListener, OnQuantityChangeListener{

    private DrawerLayout mDrawerLayout;
    public static OnItemRemoveListener onItemRemoveListener = null;
    public static OnQuantityChangeListener onQuantityChangeListener = null;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;        // used to store app title
    private String[] navMenuTitles;    // slide menu items
    private TypedArray navMenuIcons;
    private Toolbar toolbar;
    private ArrayList<Category> navCategoryItems;
    private CategoryAdapter categoryAdapter;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_views();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        getCategory();     //show category on Drawer
        setDraweropened();    //always open a drawer when activity is opened
        drawer_Toggle_Handling(savedInstanceState);    //  enabling action bar app icon and behaving it as toggle button

    }


    public void setDraweropened()
    {
        mDrawerLayout.openDrawer(mDrawerList);
    }


    private void drawer_Toggle_Handling(Bundle savedInstanceState)
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                // getSupportActionBar().setTitle(mTitle);
                ////set actionbar tittle when closed
               // getSupportActionBar().setTitle("Kababjees Menu");
                invalidateOptionsMenu();  // calling onPrepareOptionsMenu() to show action bar icons
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();  // calling onPrepareOptionsMenu() to hide action bar icons
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }
    }


    private void init_views()
    {
        toolbar= (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        mTitle = mDrawerTitle = getSupportActionBar().getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);      // load slide menu items
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);    // nav drawer icons from resources

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.drawer_header,null, false);
        mDrawerList.addHeaderView(listHeaderView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        onItemRemoveListener = this;
        onQuantityChangeListener = this;

    }

    private void getCategory() {


        RestClient.getAdapter().getCategories(new Callback<ArrayList<Category>>() {
            @Override
            public void success(ArrayList<Category> categories, Response response) {

                categoryAdapter = new CategoryAdapter(getApplicationContext(),categories);
                mDrawerList.setAdapter(categoryAdapter);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(getApplicationContext(),retrofitError.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // menu.findItem(R.id.cart_text).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.cart_text:
//                Intent intent = new Intent(getApplicationContext(), OrderCheckoutScreen.class);
//                startActivity(intent);
                OrderCheckoutFragment orderCheckoutFragment = new OrderCheckoutFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_container, orderCheckoutFragment).commit();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
//    get total cart price
//public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    if (requestCode == 1) {
//        if(resultCode == RESULT_OK){
//            String totalprice=data.getStringExtra("totalprice");
//            Toast.makeText(getApplicationContext(),"your cart value"+totalprice,Toast.LENGTH_SHORT).show();
//        }
//    }
//}
//
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        //get total cart price and show in menu
        //menu.findItem(R.id.cart_text).setVisible(true);
        //menu.findItem(R.id.cart_text).setTitle("Rs:"+String.valueOf(DetailScreen.total_cart_bill));
        menu.findItem(R.id.cart_text).setTitle("Rs:"+ItemCart.getInstance().getTotal());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void OnItemAddedInCart() {
        invalidateOptionsMenu();
//        Toast.makeText(getApplicationContext(),"Total "+ItemCart.getInstance().getTotal(),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onItemRemoved(int price) {
        invalidateOptionsMenu();
    }

    @Override
    public void onQuantityChanged(int price) {
        invalidateOptionsMenu();
    }

    private class SlideMenuClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           // displayView(position);   // display view for selected nav drawer item

            //send categoryid to fragment .. to display menus for selected category

            category= (Category) parent.getItemAtPosition(position);
            send_CategoryId(category.getId());
         //   set actionbar tittle when closed

            mDrawerLayout.closeDrawer(mDrawerList);
            getSupportActionBar().setTitle(category.getName());

           // Toast.makeText(getApplicationContext(),"Cat_id"+category.getId(),Toast.LENGTH_LONG).show();

        }
    }
    private void  send_CategoryId(int cat_id)
    {
        Bundle bundle=new Bundle();
        bundle.putInt("Category_id",cat_id);
        MenusFragment menufragment = new MenusFragment();
        menufragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, menufragment).commit();
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }



}