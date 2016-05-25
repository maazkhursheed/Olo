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
public class PhotosFragment extends Fragment {
    public PhotosFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);

        return rootView;
    }
}
