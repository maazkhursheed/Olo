package adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.maaz.olo.R;
import models.Menus;
import models.MenusItem;

import java.util.List;

/**
 * Created by Maaz on 5/25/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<MenusItem> menusList;
    OnItemClickListner menuItemClickListner;


    public MenuAdapter(List<MenusItem> menusList) {
        this.menusList = menusList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView menu_item_name, menus_item_desc, menu_item_price;
        ImageView item_pic;

        public MyViewHolder(View view) {
            super(view);

            menu_item_name = (TextView) view.findViewById(R.id.item_name);
            menus_item_desc = (TextView) view.findViewById(R.id.item_description);
            menu_item_price = (TextView) view.findViewById(R.id.item_price);
            item_pic= (ImageView) view.findViewById(R.id.itemmenu_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(menuItemClickListner != null){

                menuItemClickListner.onItemClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListner{

        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListner(final OnItemClickListner menuItemClickListner){

        this.menuItemClickListner = menuItemClickListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_menu_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenusItem menus = menusList.get(position);

            holder.menu_item_name.setText("" + menus.getName());

            holder.menus_item_desc.setText("" + menus.getDescription());
            holder.menu_item_price.setText("Rs" + ":" + (int) menus.getPrice());

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#dddddd"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }


    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }


}
