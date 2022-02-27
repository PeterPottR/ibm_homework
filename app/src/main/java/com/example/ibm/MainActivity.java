package com.example.ibm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public DataManager dm = new DataManager();
    ProgressDialog pd;
    private static String url = "https://android-intern-homework.vercel.app/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new JsonTask().execute(url);
    }

    public void AddCards(Item[] items) //adding the cardViews to the main layout
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
        for (int i = 0; i < items.length; i++)
        {
            //CARD VIEW CREATE
            CardView cardview = new CardView(MainActivity.this);
            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutparams.setMargins(20,20,20,20);
            cardview.setLayoutParams(layoutparams);
            cardview.setMinimumHeight(300);
            cardview.setRadius(20);
            cardview.setPadding(50, 50, 50, 50);
            cardview.setCardBackgroundColor(Color.WHITE);
            cardview.setMaxCardElevation(30);
            //IMAGE CREATE
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(300, 300);
            lp.gravity = Gravity.CENTER;
            ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.new_imageview, null);
            imageView.setLayoutParams(lp);
            if (items[i].AvatarUrl!=null) {
                Picasso.get().load(items[i].AvatarUrl).into(imageView);
            }



            //MAIN TEXT CREATE
            LinearLayout text_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.new_linear_layout, null);
            TextView title_tv = (TextView)getLayoutInflater().inflate(R.layout.title_tv, null);
            title_tv.setText(items[i].Title);
            TextView desc_tv = (TextView)getLayoutInflater().inflate(R.layout.desc_tv, null);
            desc_tv.setText(items[i].Description);

            //SECONDARY TEXT CREATE


            //ADDING VIEWS TO LAYOUT
            text_layout.addView(imageView);
            text_layout.addView(title_tv);
            text_layout.addView(desc_tv);
            cardview.addView(text_layout);
            layout.addView(cardview);
        }

    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
            Log.d("JSON:", result);
            dm.convertJSON(result);

            AddCards(dm.items);
        }
    }
}

