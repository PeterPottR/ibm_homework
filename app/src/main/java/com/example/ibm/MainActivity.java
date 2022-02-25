package com.example.ibm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Item[] dataPoints = new Item[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager dataManager = new DataManager();

        dataManager.ImportData("https://android-intern-homework.vercel.app/api");
        dataPoints = dataManager.items;

        LinearLayout layout = (LinearLayout)findViewById(R.id.main_layout);
        for (int i =0; i<10; i++)
        {
            addCard(layout);
        }
    }

    public void addCard(LinearLayout parentLayout)
    {
        MaterialCardView itemCard = new MaterialCardView(this);
        TextView textView = new TextView(this);
        textView.setText(dataPoints[0].Guid);
        itemCard.addView(textView);
        parentLayout.addView(itemCard);
    }

}

