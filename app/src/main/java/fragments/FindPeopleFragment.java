package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.maaz.olo.R;

/**
 * Created by Maaz on 5/25/2016.
 */
public class FindPeopleFragment extends Fragment {


    public FindPeopleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_find_peaople, container, false);

        return rootView;
    }
}
