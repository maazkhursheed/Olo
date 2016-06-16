package fragments;


import Interfaces.OnDrawerToggleListner;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cart.ItemCart;
import com.example.maaz.olo.R;
import models.MenusItem;
import models.order_detail;
import models.OrderResponse;
import models.Orders;
import network.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfo extends Fragment {
    private View view;

    private EditText edittxt_name,
                     edittxt_phone,
                     edittxt_address;

    private Button button_confirm;
    private String userName,userPhone,userAddress;
    private OnDrawerToggleListner mListner;

    public UserInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_user_info, container, false);
        setHasOptionsMenu(true);
      //  shouldDisplayHomeUp();
        initViews();
//        android.app.ActionBar actionBar = getActivity().getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
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

    @Override
    public void onResume() {
        super.onResume();
        mListner.showDrawerToggle(false);

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

        edittxt_name= (EditText) view.findViewById(R.id.edittxt_username);
        edittxt_phone= (EditText) view.findViewById(R.id.edittxt_userphone);

        edittxt_address= (EditText) view.findViewById(R.id.edittxt_useraddress);
        button_confirm= (Button) view.findViewById(R.id.confirm_btn);

         buttonListner();





    }

    /**
     * set the button listner
     */
    private void buttonListner() {
        button_confirm.setOnClickListener(new ConfirmOrderListner());


    }

    //=========================InnerClass/Button Listner ==============================================///

    private class ConfirmOrderListner implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//
//           String deviceId= Settings.Secure.getString(getContext().getContentResolver(),
//                   Settings.Secure.ANDROID_ID);
            placeOrders();

        }

        /**
         * validates  input fields
         * @return
         */
        private boolean validateInput(){

            if(edittxt_name.getText().toString().isEmpty()||edittxt_address.getText().toString().isEmpty()
                    ||edittxt_phone.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity().getApplicationContext(),"Fields Missing",Toast.LENGTH_LONG).show();
                return false;

            }
            else {
                userName=edittxt_name.getText().toString();
                 userPhone=edittxt_phone.getText().toString();
                  userAddress=edittxt_address.getText().toString();
               return true;
            }


        }



// ==========================This method place an order to server ===================///

        private void placeOrders(){
            if(validateInput()==true) {

//                DevicePreference.getInstance().initPref(getActivity().getApplicationContext());
//                DevicePreference.getInstance().setAuthHeaderFlag(true);
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

                RestClient.getAdapter().placeOrder(placeorders, new Callback<OrderResponse>() {
                    @Override
                    public void success(OrderResponse orderResponse, Response response) {
                        Toast.makeText(getActivity().getApplicationContext(), "Status" + ":" +""+orderResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Retrofit Error" + error.toString(), Toast.LENGTH_LONG).show();


                    }
                });


            }
            else {

//                exception here
            }
        }
    }

}
