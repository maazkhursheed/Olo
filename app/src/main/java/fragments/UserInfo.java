package fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cart.ItemCart;
import com.example.maaz.olo.R;
import models.MenusItem;
import models.OrderDetail;
import models.OrderResponse;
import models.Orders;
import network.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.DeviceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.userinfo_menu,menu);
        menu.findItem(R.id.cart_text).setVisible(false);
        menu.findItem(R.id.cart).setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
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
//            String deviceId="755a29c9c999885a";









        }

        //validates  input fields

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



// ==========================This method simply place an order to server ===================///

        private void placeOrders(){

            String deviceId="755a29c9c999885a";


              validateInput();

            int ordertotal= (int) ItemCart.getInstance().getTotal();
            int orderTime=111;



            List<MenusItem> itemsList = ItemCart.getOrderableItems();
            ArrayList<OrderDetail> orderdetail = new ArrayList<>();


            for(MenusItem item: itemsList) {

                int id = item.getId();
                String itemname = item.getName();
                int desiredQuantity = item.getDesiredQuantity();
                double price = item.getPrice();

                OrderDetail detail = new OrderDetail(id, itemname, desiredQuantity, (int) price);
                orderdetail.add(detail);
            }





            Orders placeorders=new Orders(deviceId,userName,userPhone,ordertotal,userAddress,orderTime,orderdetail);

            RestClient.getAdapter().placeOrder(placeorders, new Callback<OrderResponse>() {
                @Override
                public void success(OrderResponse orderResponse, Response response) {
                    Toast.makeText(getActivity().getApplicationContext(),"Status" +":"+orderResponse.getMessage(),Toast.LENGTH_LONG).show();

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity().getApplicationContext(),"Retrofit Error"+error.toString(),Toast.LENGTH_LONG).show();


                }
            });



        }
    }
}
