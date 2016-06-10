package fragments;

import Interfaces.OnItemRemoveListener;
import Interfaces.OnQuantityChangeListener;
import adapters.CartEditListAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cart.ItemCart;
import com.example.maaz.olo.R;
import com.example.maaz.olo.screens.MainActivity;

/**
 * Created by Maaz on 6/9/2016.
 */
public class OrderCheckoutFragment extends Fragment {

    private CartEditListAdapter cartEditListAdapter;

    TextView editOrderTv, applyOrderChanges, addProduct, orderSubTotal, orderAllTotal;
    int subTotal = 0;
    int allTotal = 0;

    public OrderCheckoutFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_order_checkout_screen, container, false);
        initViews(rootView);

        OrderCheckSimpleListFragment simpleListFragment = new OrderCheckSimpleListFragment();
        android.app.FragmentManager fragment = getFragmentManager();
        android.app.FragmentTransaction transaction = fragment.beginTransaction();
        transaction.replace(R.id.orderframe_container, simpleListFragment);
        transaction.commit();
        return rootView ;
    }

    private void initViews(View view) {

        editOrderTv = (TextView) view.findViewById(R.id.tv_editOrder);
        editOrderTv.setOnClickListener(new EditableOrderItemListner());

        applyOrderChanges = (TextView)view.findViewById(R.id.tv_editApplyOrder);
        applyOrderChanges.setOnClickListener(new EditApplyOrderListner());

        addProduct = (TextView)view.findViewById(R.id.addProductBtn);
        addProduct.setOnClickListener(new AddProductListner());

        orderSubTotal = (TextView)view.findViewById(R.id.tv_subTotal);
        subTotal = (int) ItemCart.getInstance().getTotal();
        orderSubTotal.setText(Integer.toString(subTotal));

        orderAllTotal = (TextView)view.findViewById(R.id.tv_allTotal);
        allTotal = (int) ItemCart.getInstance().getAllTotal();
        orderAllTotal.setText(Integer.toString(allTotal));

    }


    private class EditableOrderItemListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            editOrderTv.setVisibility(v.GONE);
            applyOrderChanges.setVisibility(v.VISIBLE);

            OrderCheckEditListFragment editListFragment = new OrderCheckEditListFragment();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_in_left, R.animator.slide_out_right);
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
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_out_left, R.animator.slide_in_right);
            OrderCheckEditListFragment editListFragment = new OrderCheckEditListFragment();
            transaction.hide(editListFragment);
            transaction.replace(R.id.orderframe_container, simpleListFragment);
            transaction.commit();
        }
    }

    private class AddProductListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }
    }
}
