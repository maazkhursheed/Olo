package fragments;



import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.maaz.olo.R;

/**
 * Created by Maaz on 6/6/2016.
 */
public class OrderCheck_EditListFragment extends Fragment {

    public OrderCheck_EditListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.order_check_edit_list, container, false);
    }

}
