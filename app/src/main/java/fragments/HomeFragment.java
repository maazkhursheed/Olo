package fragments;

import adapters.MenuAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.maaz.olo.R;
import models.Menus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maaz on 5/25/2016.
 */
public class HomeFragment extends Fragment {

//    private List<MenusFragment> menuList = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private MenuAdapter menuAdapter;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

//        recyclerView = (RecyclerView)rootView.findViewById(R.id.listMenu);
//        menuAdapter = new MenuAdapter(menuList);
//
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(menuAdapter);
//
//        menuAdapter.SetOnItemClickListner(new MenuAdapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                Intent intent = new Intent(getActivity(), DetailMenuScreen.class);
//                startActivity(intent);
//            }
//        });
//
//        prepareMenuData();
        return rootView;
    }

//    private void prepareMenuData() {
//
//        MenusFragment menus = new MenusFragment("BBQ : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Chicken : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Beef : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Mutton : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Chicken : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Beef : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Mutton : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Chicken : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Beef : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menus = new MenusFragment("Mutton : Mastana", "Spicy Hot", "2015");
//        menuList.add(menus);
//
//        menuAdapter.notifyDataSetChanged();
//    }
}
