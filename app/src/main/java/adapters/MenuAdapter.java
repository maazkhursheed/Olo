package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.maaz.olo.R;
import models.Menus;

import java.util.List;

/**
 * Created by Maaz on 5/25/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<Menus> menusList;
    OnItemClickListner menuItemClickListner;

    public MenuAdapter(List<Menus> menusList) {
        this.menusList = menusList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Menus menus = menusList.get(position);
        holder.title.setText(menus.getTitle());
        holder.genre.setText(menus.getGenre());
        holder.year.setText(menus.getYear());
    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }


}
