package fragments;


import adapters.MenuAdapter;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.example.maaz.olo.R;
//import com.example.maaz.olo.screens.DetailScreen;
import com.google.gson.Gson;
import models.MenusItem;
import network.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenusFragment extends Fragment {

    private List<models.MenusItem> menuList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private ProgressDialog progressDialog;
    private Gson gson;
    static String totalprice=null;
    public MenusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menus, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.listMenu);
        prepareMenusBy_Id(get_CatId());

        return rootView;
    }
    private int get_CatId()
    {
        int cat_id=getArguments().getInt("Category_id",1);
        return cat_id;
    }

    private void prepareMenusBy_Id(int category_id)
    {
        showProgress("Loading.....");
        RestClient.getAdapter().getMenuItems(category_id, new Callback<ArrayList<MenusItem>>() {
            @Override
            public void success(final ArrayList<MenusItem> menusItems, Response response) {
                hideProgress();

                    if(!menusItems.isEmpty())
                    {
                        menuAdapter=new MenuAdapter(menusItems);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(menuAdapter);

                        menuAdapter.SetOnItemClickListner(new MenuAdapter.OnItemClickListner() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                Intent intent = new Intent(getActivity(), DetailScreen.class);
//                               // intent.putExtra("Itemname")
//                                intent.putExtra(IntentsConstants.item_name,menusItems.get(position).getName());
//                               // intent.putExtra("ItemDesc",menusItems.get(position).getDescription());
//                                intent.putExtra(IntentsConstants.item_price,menusItems.get(position).getPrice());
//                                startActivity(intent);
//                                //startActivityForResult(intent, 1);

                                Bundle data = new Bundle();
                                MenusItem item = menusItems.get(position);
                                data.putSerializable("Item", (Serializable) item);

                                DetailsFragment detailsFragment = new DetailsFragment();
                                detailsFragment.setArguments(data);
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame_container, detailsFragment).commit();



                            }
                        });








                }
                else
                {
                    Toast.makeText(getActivity(),"No Menu Found of this category",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                hideProgress();
                Toast.makeText(getActivity(),retrofitError.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void saveImageIntoFile(String imageUrl, String itemName, String imageCreateDate){
        //Image file is saved in following pattern
        //filepath = ItemName+ImageFileCreationDateTime
        try {
            URL imageURL = new URL(imageUrl);
            URLConnection connection = imageURL.openConnection();

            InputStream inputStream = new BufferedInputStream(imageURL.openStream(),10240);

            File cacheDir = getContext().getCacheDir();
            File cacheFile = new File(cacheDir,itemName + imageCreateDate);
            FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);

            byte buffer []= new byte[1024];
            int dataSize;
            int loadSize = 0;

            while((dataSize = inputStream.read(buffer))!= -1){

                loadSize += dataSize;
                fileOutputStream.write(buffer,0,dataSize);

            }

            fileOutputStream.close();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgress(String message){

        progressDialog=ProgressDialog.show(getActivity(),"",message,false);

    }

    private void hideProgress(){

        progressDialog.dismiss();
    }

}
