package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cart.ItemCart;
import com.example.maaz.olo.R;
import models.MenusItem;

import java.util.List;

/**
 * Created by Maaz on 6/7/2016.
 */
public class CartEditListAdapter extends RecyclerView.Adapter<CartEditListAdapter.MyViewHolder> {

    private List<MenusItem> menusList;
    private MenusItem menus;
    Context mContext;

    public CartEditListAdapter(List<MenusItem> menusList ) {
        this.menusList = menusList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public EditText  itemEditQuantity;
        public TextView  itemEditName, itemEditPrice, itemEditCross;

        public MyViewHolder(View view) {
            super(view);

            itemEditQuantity = (EditText) view.findViewById(R.id.editItemQuantity);
            itemEditName = (TextView) view.findViewById(R.id.ediItemName);
            itemEditPrice = (TextView) view.findViewById(R.id.editItemCost);
            itemEditCross = (TextView) view.findViewById(R.id.editItemCross);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_check_edit_list, parent, false);
        mContext = parent.getContext();
        return new CartEditListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        menus = menusList.get(position);
        holder.itemEditQuantity.setText("" + menus.getDesiredQuantity());
        holder.itemEditName.setText("" + menus.getName());
        holder.itemEditPrice.setText("" + menus.getPrice());

        holder.itemEditCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ItemCart.getInstance().getOrderList().remove(position);
                ItemCart.getOrderableItems().remove(position);
                Toast.makeText(mContext,"Item Removed",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }

}
