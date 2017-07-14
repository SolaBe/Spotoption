package com.sola.spotoption.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sola2Be on 13.07.2017.
 */

public class CountriesLoader extends AsyncTaskLoader<List<String>> {

    private final static String URL = "http://trade.spotoption.com/platformAjax/getJsonFile/CountryData/en/CountryData.json";

    public CountriesLoader(Context context) {
        super(context);
    }

    @Override
    public List<String> loadInBackground() {
        List<String> countries = new ArrayList<>();
        try {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            connection.disconnect();
            isr.close();
            br.close();

            String json = builder.toString();

            if (json.length() > 0) {
                JSONObject jsObject = new JSONObject(json);
                JSONObject data = (JSONObject) jsObject.get("data");
                JSONArray jsonArray = data.getJSONArray("countries");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String name = (String) jsonArray.getJSONObject(i).get("name");
                    countries.add(name);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // no internet
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return countries;
    }
}
