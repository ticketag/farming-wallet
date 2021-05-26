package eu.farmingpool.farmingwallet.ui.dapps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import eu.farmingpool.farmingwallet.R;

public class DAppsFragment extends Fragment {

    private DAppsViewModel DAppsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DAppsViewModel =
                new ViewModelProvider(this).get(DAppsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_d_apps, container, false);

        DAppsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}