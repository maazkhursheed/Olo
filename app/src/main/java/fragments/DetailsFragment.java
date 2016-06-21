package fragments;


import Interfaces.OnDrawerToggleListner;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cart.ItemCart;
import com.example.maaz.olo.R;

import com.squareup.picasso.Picasso;
import models.Category;
import models.MenusItem;

import java.io.Serializable;

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
    private int initialQuantity =1,maxQuantity=20;
    private int selectedItemQuantity =1;
    private String itemname;
    private OnDetailFragmentInteraction listner;
    private MenusItem menusItem;
    private OnDrawerToggleListner mListner;
    private ProgressDialog progressDialog;
    ImageView itemView;
    int catId;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_details, container, false);
        itemView = (ImageView) view.findViewById(R.id.itemFullView);
        //showProgress("Loading...");
        getIntentValues();
        initViews();
        setHasOptionsMenu(true);
        //backToMenus(menusItem.getId());
        return view;
    }



    private void initViews() {
        //toolbar = (Toolbar) findViewById(R.id.toolbar_details_item_screen);

       // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Details Screen");

        button_addCart = (Button) view.findViewById(R.id.addcart_btn);
        label_itemName = (TextView) view.findViewById(R.id.textView_itemname);
        label_itemPrice = (TextView) view.findViewById(R.id.textView_itemPrice);
        label_incrementQuantity = (TextView) view.findViewById(R.id.add_txtview);
        label_decrementQuantity = (TextView) view.findViewById(R.id.subtract_txtview);
        label_totalPrice = (TextView) view.findViewById(R.id.total_price);
        label_quantity = (TextView) view.findViewById(R.id.itemquantity_txtview);
        selectedItemQuantity= checkItemQuantity();
        label_quantity.setText("Quantity "+selectedItemQuantity);


        label_itemName.setText(itemname);
        label_itemPrice.setText("Rs "+ String.valueOf(itemPrice));


//        set total price with quantity
        totalprice=(calcTotalPrice(checkItemQuantity(),itemPrice));

        menusItem.setDesiredQuantity(selectedItemQuantity);


        //setHomeButton();

        setInitial_itemPrice();

        setListeners();


    }

    /**This method determines the pre selected quantity of item, if it
     * already exists in  cart
     *
     */
    private int checkItemQuantity() {

        int desiredQuantity = initialQuantity;
        MenusItem tempItem = null;
        tempItem = ItemCart.getInstance().checkItem(menusItem);

        if(tempItem != null){
            desiredQuantity = tempItem.getDesiredQuantity();
        }


        return desiredQuantity;
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

        Bundle bundle = getArguments();
        menusItem= (MenusItem) bundle.getSerializable("Item");
        itemname=menusItem.getName();
        itemPrice=menusItem.getPrice();

        if(menusItem.getImages().size() > 0) {
           // showProgress("Loading.....");

            Picasso.with(getActivity()).load(menusItem.getImages().get(0).getUrl()).
                    placeholder(R.drawable.progress_animation).into(itemView);
           // hideProgress();
            //.resize(640,344)
        }else{
            itemView.setBackgroundResource(R.drawable.fastfood);
        }


}

    private void hideProgress() { progressDialog.dismiss();}

    private void showProgress(String message) {
        progressDialog= ProgressDialog.show(getActivity(),"",message,false);
    }

    //set initial total price total price in txtview when activity created
    private void setInitial_itemPrice()
    {

        calcTotalPrice(selectedItemQuantity, itemPrice);
        if(label_totalPrice.getText().equals(""))
        {
           // label_totalPrice.setText("" + itemPrice);
            label_totalPrice.setText("Rs" + " " + totalprice);
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

//        Bundle data = new Bundle();
//        // MenusItem item = menusItems.get(position);
//        data.putSerializable("Category", (Serializable) category);
//
//        MenusFragment menufragment = new MenusFragment();
//        menufragment.setArguments(data);
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.frame_container, menufragment).commit();


    }



//========================================Overridden methods=======================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:

                MenusFragment menusFragment=new MenusFragment();
                FragmentManager frgmentManager = getFragmentManager();
                frgmentManager.beginTransaction().replace(R.id.frame_container, menusFragment).commit();


                return true;

            // Other case statements...

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
   public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Details Screen");



}

    @Override
    public void onResume() {
        super.onResume();
       mListner.showDrawerToggle(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            listner = (OnDetailFragmentInteraction) context;
            mListner= (OnDrawerToggleListner) context;
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
            mListner= (OnDrawerToggleListner) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnDetailFragmentInteractionListener");
        }
    }

    //==========================================Inner Classes / Listeners=========================================
    private class AddCartListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {


           ItemCart.getInstance().addOrUpdateItem(menusItem);
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
            if(selectedItemQuantity== initialQuantity ||selectedItemQuantity <maxQuantity)
            {
                selectedItemQuantity++;
                label_quantity.setText("Quantity "+selectedItemQuantity);
                totalprice= calcTotalPrice(selectedItemQuantity, itemPrice);
                label_totalPrice.setText("Rs" + " "+totalprice);
                menusItem.setDesiredQuantity(selectedItemQuantity);

            }
        }
    }

    private class DecrementListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(selectedItemQuantity> initialQuantity)
            {

                selectedItemQuantity--;
                //selectedItemQuantity=initialQuantity;
                label_quantity.setText("Quantity "+selectedItemQuantity);
                totalprice= calcTotalPrice(selectedItemQuantity, itemPrice);
                label_totalPrice.setText("Rs" + " "+totalprice);
                menusItem.setDesiredQuantity(selectedItemQuantity);

            }
        }
    }


    public interface OnDetailFragmentInteraction{

        void OnItemAddedInCart();
    }




}

