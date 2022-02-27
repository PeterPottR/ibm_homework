package com.example.ibm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Item[] dataPoints = new Item[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager dataManager = new DataManager();

        dataManager.ImportData("https://android-intern-homework.vercel.app/api");

        dataPoints[0] = new Item("090206cb-c412-43ec-b11c-b65ecec270f1",
                "cfuzzens0@economist.com",
                "kshilleto0",
                "In sagittis dui vel nisl. Duis ac nibh.",
                "Traffic Affairs (Mitfahrer)",
                "https://robohash.org/namrecusandaeet.png?size=50x50&set=set1",
                "recorded",
                "2/21/2021",
                703);

        LinearLayout layout = (LinearLayout)findViewById(R.id.main_layout);

        addCards(layout);
    }

    public void addCards(LinearLayout parentLayout)
    {
        for (int i = 0; i < dataPoints.length; i++) {

            MaterialCardView itemCard = new MaterialCardView(this);
            TextView titleView = new TextView(this);
            TextView descView = new TextView(this);
            titleView.setText(dataPoints[i].Title);
            descView.setText(dataPoints[i].Description);
            ImageView main_img = new ImageView();

            new DownloadImageTask(main_img)
                    .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");


            itemCard.addView(titleView);
            itemCard.addView(descView);


            parentLayout.addView(itemCard);
        }


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}

