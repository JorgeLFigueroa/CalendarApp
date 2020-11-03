package com.example.calendarapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendarapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView textViewDate = root.findViewById(R.id.textViewDate);
        final TextView textViewTime = root.findViewById(R.id.textViewTime);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                Date today = Calendar.getInstance().getTime();//getting date
                SimpleDateFormat formatterDate = new SimpleDateFormat("EEE MMM dd yyyy");
                String date = formatterDate.format(today);
                SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm");
                String time = formatterTime.format(today);
                textViewDate.setText(date);
                textViewTime.setText(time);
            }
        });
        return root;
    }
}