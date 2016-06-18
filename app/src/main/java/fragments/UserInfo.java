package fragments;


import Interfaces.OnDrawerToggleListner;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import cart.ItemCart;
import com.example.maaz.olo.R;
import com.example.maaz.olo.screens.MainActivity;
import com.google.gson.Gson;
import models.*;
import network.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfo extends Fragment {
    private View view;

    private EditText edittxt_name,
                     edittxt_phone,
                     edittxt_address;
        private FrameLayout thankyou;
    private LinearLayout userinfo_linear;
    private Button button_confirm;
    private String userName,userPhone,userAddress;
    private OnDrawerToggleListner mListner;
    private ProgressDialog progressDialog;
    SharedPreferences mPrefs ;


    public UserInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_user_info, container, false);
        setHasOptionsMenu(true);
        initViews();
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListner = (OnDrawerToggleListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnDrawerToggleListner");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        try {

            mListner= (OnDrawerToggleListner) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnDrawerToggleListner");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Place Order");


    }
    private void hideKeyboard(){

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void onResume() {
        super.onResume();
        mListner.showDrawerToggle(false);
        hideKeyboard();

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Place Order");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);




    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.userinfo_menu,menu);
        menu.findItem(R.id.cart_text).setVisible(true);
        menu.findItem(R.id.cart).setVisible(false);
    }




    /**
     * This method initialize a views of screen
     */
    private void initViews(){
        userinfo_linear= (LinearLayout) view.findViewById(R.id.user_info_mainlayout);
        thankyou= (FrameLayout) view.findViewById(R.id.fragment_order_thankyouFrame);
        edittxt_name= (EditText) view.findViewById(R.id.edittxt_username);
        edittxt_phone= (EditText) view.findViewById(R.id.edittxt_userphone);

        edittxt_address= (EditText) view.findViewById(R.id.edittxt_useraddress);
        button_confirm= (Button) view.findViewById(R.id.confirm_btn);

        mPrefs =getActivity().getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("User",null);
        User obj = gson.fromJson(json, User.class);


         buttonListner();
        if(obj!=null){

            edittxt_name.setText(obj.getName().toString());
            edittxt_phone.setText(obj.getPhone().toString());
            edittxt_address.setText(obj.getAddress().toString());
        }
        else {


        }





    }

    /**
     * set the button listner
     */
    private void buttonListner() {
        button_confirm.setOnClickListener(new ConfirmOrderListner());
    }
//    =========================helper Methos ==========================================//
   private void showOrderCheckoutFragment() {
      OrderCheckoutFragment orderCheckoutFragment = new OrderCheckoutFragment();

//    FragmentManager fragmentManager = getFragmentManager();
//    fragmentManager.beginTransaction().replace(R.id.frame_container, orderCheckoutFragment).commit();
       Intent intent=new Intent(getActivity(), MainActivity.class);
       startActivity(intent);
}

    private void hideFragment(){

        userinfo_linear.setVisibility(View.GONE);
        thankyou.setVisibility(View.VISIBLE);
        hideKeyboard();
        //  userinfo_linear.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showOrderCheckoutFragment();

            }
        }, 4000);
    }


    /**
     * validates  input fields
     * @return
     */
    private boolean validateInput(){

        if(edittxt_name.getText().toString().isEmpty()||edittxt_address.getText().toString().isEmpty()
                ||edittxt_phone.getText().toString().isEmpty())
        {
            Toast.makeText(getActivity().getApplicationContext(),"Fields Missing",Toast.LENGTH_SHORT).show();
            return false;

        }
        else {
            userName=edittxt_name.getText().toString();
            userPhone=edittxt_phone.getText().toString();
            userAddress=edittxt_address.getText().toString();

            User user=new User(userName,userPhone,userAddress);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user); //  - instance of MyObject
            prefsEditor.putString("User", json);
            prefsEditor.commit();


            return true;
        }


    }
    private void showProgress(String message){

        progressDialog= ProgressDialog.show(getActivity(),"",message,false);

    }

    private void hideProgress(){

        progressDialog.dismiss();
    }

    private void getUserInfo(){
        Gson gson = new Gson();
        String json = mPrefs.getString("User","");
        User obj = gson.fromJson(json, User.class);

    }

    //=========================InnerClass/Button Listner ==============================================///




    private class ConfirmOrderListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            placeOrders();
           // hideFragment();

        }


// ==========================This method place an order to server ===================///

        private void placeOrders(){
            if(validateInput()==true) {

//                DevicePreference.getInstance().initPref(getActivity().getApplicationContext());
//                DevicePreference.getInstance().setAuthHeaderFlag(true);
               // showProgress("Loading.....");
                double ordertotal =  ItemCart.getInstance().getTotal();
                int orderTime = 462970960;
                List<MenusItem> itemsList = ItemCart.getOrderableItems();
                ArrayList<order_detail> orderdetail = new ArrayList<>();

                for (MenusItem item : itemsList) {

                    int id = item.getId();
                    String itemname = item.getName();
                    int desiredQuantity = item.getDesiredQuantity();
                    double price = item.getPrice();

                    order_detail detail = new order_detail(id, itemname, desiredQuantity,price);
                //order_detail detail = new order_detail(1,"Tikka",3,400);
                   orderdetail.add(detail);
                }

                Orders placeorders = new Orders(userName, userPhone,userAddress, ordertotal, orderTime, orderdetail);
                showProgress("Loading.....");
                RestClient.getAdapter().placeOrder(placeorders, new Callback<OrderResponse>() {
                    @Override
                    public void success(OrderResponse orderResponse, Response response) {
                        hideProgress();
                        hideFragment();
                        ItemCart.getOrderableItems().clear();
                        //Toast.makeText(getActivity().getApplicationContext(), "Status" + ":" +""+orderResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(getActivity().getApplicationContext(), "SomeThing goes Wrong", Toast.LENGTH_LONG).show();
                        hideProgress();


                    }
                });


            }
            else {

//                exception here
            }
        }
    }


}
