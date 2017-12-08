package com.example.arka.arasfreezone1.services;

import android.util.Log;

import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.PlacesModel;
import com.example.arka.arasfreezone1.models.ReligiousTimesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by EHSAN on 12/1/2017.
 */

public class WebService {


    String addr = "http://gsharing.ir/api/";


    private String connectToServer(String address, String RequestMethod) {
        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(RequestMethod);
            return inputStreamToString(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String connectToServerByJson(String address, String requestMethod, String JsonDATA) {

        String JsonResponse = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine);
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
//response data
            Log.i(TAG, JsonResponse);
            //send to post execute
            return JsonResponse;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;

    }

    private String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String nextLine;
        try {
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public ReligiousTimesModel getReligiousTimes(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer("https://prayer.aviny.com/api/prayertimes/1003", "GET");
            Log.i("LOG", response + "");


            if (response != null) {
                ReligiousTimesModel religiousTimesModel = new ReligiousTimesModel();
                try {
                    JSONObject Object = new JSONObject(response);

                    religiousTimesModel.Imsaak = Object.getString("Imsaak");
                    religiousTimesModel.Maghreb = Object.getString("Maghreb");
                    religiousTimesModel.Midnight = Object.getString("Midnight");
                    religiousTimesModel.Noon = Object.getString("Noon");
                    religiousTimesModel.Sunrise = Object.getString("Sunrise");
                    religiousTimesModel.Sunset = Object.getString("Sunset");
                    religiousTimesModel.TimeZone = Object.getString("TimeZone");
                    religiousTimesModel.Today = Object.getString("Today");
                    religiousTimesModel.TodayQamari = Object.getString("TodayQamari");

                    return religiousTimesModel;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }

    public int getCulture(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Culturals");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "culture/select?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<PlacesModel> placeslList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        PlacesModel placesModel = new PlacesModel();
                        placesModel.id = Object.getInt("id");
                        placesModel.type = Object.getInt("Type");
                        placesModel.idStartDay = Object.getInt("idStartDay");
                        placesModel.idEndDay = Object.getInt("idEndDay");
                        placesModel.startTime = Object.getString("StartTime");
                        placesModel.endTime = Object.getString("EndTime");
                        placesModel.name = Object.getString("Name");
                        placesModel.lat = Object.getDouble("Lat");
                        placesModel.lon = Object.getDouble("Long");
                        placesModel.phone = Object.getString("Phone");
                        placesModel.star = Object.getDouble("Star");
                        placesModel.starCount = Object.getInt("StarCount");
                        placesModel.likeCount = Object.getInt("likeCount");
                        placesModel.info = Object.getString("Info");
                        placesModel.website = Object.getString("webSite");
                        placesModel.visibility = Object.getBoolean("Visibility");
                        placesModel.lastUpdate = Object.getString("lastUpdate");
                        placesModel.address = Object.getString("Address");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectCaltureById(placesModel.id + "");
                            //List<String> s1 = helper.selectCity("name");
                            //Log.i("MAH", s1 + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewCalture(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectCaltureByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteCalture(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateCalture(placesModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

    public int getCOffices(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Offices");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "office/select?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<PlacesModel> placeslList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        PlacesModel placesModel = new PlacesModel();
                        placesModel.id = Object.getInt("id");
                        placesModel.type = Object.getInt("Type");
                        placesModel.name = Object.getString("Name");
                        placesModel.lat = Object.getDouble("Lat");
                        placesModel.lon = Object.getDouble("Long");
                        placesModel.info = Object.getString("Info");
                        placesModel.website = Object.getString("webSite");
                        placesModel.visibility = Object.getBoolean("Visibility");
                        placesModel.lastUpdate = Object.getString("lastUpdate");
                        placesModel.address = Object.getString("Address");
                        placesModel.tel = Object.getString("Tell");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectOfficeById(placesModel.id + "");

                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewOffice(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectOfficeByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteOffice(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateOffice(placesModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

    public int getEatings(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Eating");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "eating/select?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<PlacesModel> placeslList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        PlacesModel placesModel = new PlacesModel();
                        placesModel.id = Object.getInt("id");
                        placesModel.type = Object.getInt("Type");
                        placesModel.idStartDay = Object.getInt("idStartDay");
                        placesModel.idEndDay = Object.getInt("idEndDay");
                        placesModel.startTime = Object.getString("StartTime");
                        placesModel.endTime = Object.getString("EndTime");
                        placesModel.name = Object.getString("Name");
                        placesModel.lat = Object.getDouble("Lat");
                        placesModel.lon = Object.getDouble("Long");
                        placesModel.phone = Object.getString("Phone");
                        placesModel.star = Object.getDouble("Star");
                        placesModel.starCount = Object.getInt("StarCount");
                        placesModel.likeCount = Object.getInt("likeCount");
                        placesModel.info = Object.getString("Info");
                        placesModel.website = Object.getString("webSite");
                        placesModel.visibility = Object.getBoolean("Visibility");
                        placesModel.lastUpdate = Object.getString("lastUpdate");
                        placesModel.address = Object.getString("Address");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectEatingById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewEating(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectEatingByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteEating(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateEating(placesModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

}
