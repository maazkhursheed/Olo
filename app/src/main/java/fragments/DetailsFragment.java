package fragments;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.*;

import android.widget.Button;
import android.widget.TextView;
import cart.ItemCart;
import com.example.maaz.olo.R;

import models.MenusItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
   private View view;
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
    private OnDetailFragmentInteraction listner;
    MenusItem menusItem;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_details, container, false);
        //return inflater.inflate(R.layout.fragment_details, container, false);
        getIntentValues();
        initViews();
        return view;
    }


    private void initViews() {
        //toolbar = (Toolbar) findViewById(R.id.toolbar_details_item_screen);

        button_addCart = (Button) view.findViewById(R.id.addcart_btn);
        label_itemName = (TextView) view.findViewById(R.id.textView_itemname);
        label_itemPrice = (TextView) view.findViewById(R.id.textView_itemPrice);
        label_incrementQuantity = (TextView) view.findViewById(R.id.add_txtview);
        label_decrementQuantity = (TextView) view.findViewById(R.id.subtract_txtview);
        label_totalPrice = (TextView) view.findViewById(R.id.total_price);
        label_quantity = (TextView) view.findViewById(R.id.itemquantity_txtview);
        selectedItemQuantity=minQuantity;
        label_quantity.setText("Quantity: "+selectedItemQuantity);


        label_itemName.setText(itemname);
        label_itemPrice.setText("Rs: "+ String.valueOf(itemPrice));
        totalprice= itemPrice;

        menusItem.setDesiredQuantity(selectedItemQuantity);


        //setHomeButton();

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

//        itemname=  getIntent().getExtras().getString(IntentsConstants.item_name);
//        itemPrice = getIntent().getExtras().getDouble(IntentsConstants.item_price);
        Bundle bundle = getArguments();
        menusItem= (MenusItem) bundle.getSerializable("Item");
        itemname=menusItem.getName();
        itemPrice=menusItem.getPrice();

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
    private void backToMenus(int cat_id)
    {
        Bundle bundle=new Bundle();
        bundle.putInt("Category_id",cat_id);
        MenusFragment menufragment = new MenusFragment();
        menufragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, menufragment).commit();


    }


//========================================Overridden methods=======================================================


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            listner = (OnDetailFragmentInteraction) context;
        }
        catch (ClassCastException ex) {

            throw new ClassCastException(ex.toString() +
                    "must implement OnDetailFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            listner = (OnDetailFragmentInteraction) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    //==========================================Inner Classes / Listeners=========================================
    private class AddCartListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


           ItemCart.getInstance().addItem(menusItem);
           // total_cart_bill +=totalprice;
            listner.OnItemAddedInCart();
           // getActivity().getFragmentManager().popBackStack();
           // getActivity().getFragmentManager().beginTransaction().hide(DetailsFragment.this).commit();
            backToMenus(menusItem.getCategory_id());
           // getActivity().getFragmentManager().beginTransaction().replace();











            //finish();
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
                menusItem.setDesiredQuantity(selectedItemQuantity);

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
                menusItem.setDesiredQuantity(selectedItemQuantity);

            }
        }
    }


    public interface OnDetailFragmentInteraction{

        void OnItemAddedInCart();
    }




}

