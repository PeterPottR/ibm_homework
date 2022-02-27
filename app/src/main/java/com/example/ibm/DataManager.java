package com.example.ibm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DataManager {

    public Item[] items = new Item[0];

    public void convertJSON (String stringJSON)
    {
        try {
            JSONObject jsonObject = new JSONObject(stringJSON);
            JSONArray playlist = jsonObject.getJSONArray("playlist");
            items = new Item[playlist.length()];
            for (int i = 0; i < playlist.length(); i++) {
                JSONObject obj = playlist.getJSONObject(i);
                items[i] = new Item(
                        obj.getString("guid"),
                        obj.getString("email"),
                        obj.getString("userName"),
                        obj.getString("description"),
                        obj.getString("title"),
                        obj.getString("avatarURL"),
                        obj.getString("mediaType"),
                        obj.getString("created"),
                        obj.getInt("durationInSec")
                );
                if (items[i].AvatarUrl.length()==0) //Avoiding exceptions because of no picture input data
                {
                    items[i].AvatarUrl=null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}





