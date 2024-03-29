package com.example.calendarapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.calendarapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
        final TextView textViewQuote = root.findViewById(R.id.textQuote);
        final TextInputLayout textInputLayout = root.findViewById(R.id.textInputLayout);
        final Button submitGoal = root.findViewById(R.id.buttonSubmit);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                Date today = Calendar.getInstance().getTime();//getting date
                SimpleDateFormat formatterDate = new SimpleDateFormat("EEE MMM dd yyyy");
                String date = formatterDate.format(today);
                SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm aa");
                String time = formatterTime.format(today);
                textViewDate.setText(date);
                textViewTime.setText(time);
                textViewQuote.setText(getRandomQuote());
                textView.setText("Today's Goal: " + getGoal(getContext()));
            }
        });
        submitGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goal = textInputLayout.getEditText().getText().toString();
                setGoal(view.getContext(), goal);
                textView.setText("Today's Goal: " + getGoal(getContext()));
            }
        });

        return root;
    }

    public static void setGoal(Context context, String text) {
        SharedPreferences preferences = context.getSharedPreferences("myAppPackage", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("goal", text);
        editor.commit();
    }

    public static String getGoal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("myAppPackage", 0);
        return preferences.getString("goal", "");
    }

    public String getRandomQuote() {
        ArrayList<String> quotes = new ArrayList<>();
        quotes.add("“All our dreams can come true, if we have the courage to pursue them.” – Walt Disney");
        quotes.add("“Don’t limit yourself. Many people limit themselves to what they think they can do. You can go as far as your mind lets you. What you believe, remember, you can achieve.” – Mary Kay Ash");
        quotes.add("“It’s hard to beat a person who never gives up.” – Babe Ruth");
        Random random = new Random();
        int upperbound = quotes.size();
        int index = random.nextInt(upperbound);
        return quotes.get(index);


    }
}