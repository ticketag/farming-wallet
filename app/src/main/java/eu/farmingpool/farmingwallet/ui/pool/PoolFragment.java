package eu.farmingpool.farmingwallet.ui.pool;

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

public class PoolFragment extends Fragment {

    private PoolViewModel poolViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        poolViewModel =
                new ViewModelProvider(this).get(PoolViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pool, container, false);
        poolViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}