package com.example.arka.arasfreezone1.services;

import android.content.Intent;
import android.util.Log;

import com.example.arka.arasfreezone1.app;
import com.example.arka.arasfreezone1.db.DatabaseHelper;
import com.example.arka.arasfreezone1.models.ActionModel;
import com.example.arka.arasfreezone1.models.CommentModel;
import com.example.arka.arasfreezone1.models.EventModel;
import com.example.arka.arasfreezone1.models.FacilityModel;
import com.example.arka.arasfreezone1.models.ImgModel;
import com.example.arka.arasfreezone1.models.MenuModel;
import com.example.arka.arasfreezone1.models.NewsModel;
import com.example.arka.arasfreezone1.models.PlacesModel;
import com.example.arka.arasfreezone1.models.ReligiousTimesModel;
import com.example.arka.arasfreezone1.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

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

    public UserModel postLoginInfo(boolean isInternetAvailable, String userName, String password) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/login?username=" + userName + "&pass=" + password, "GET");
            Log.i("LOG", response + "");

            if (response != null){

                if (response.equals("[]")){
                    UserModel userModel = new UserModel();
                    userModel.id = -1;
                    return userModel;
                }
                else {

                    try {

                        JSONArray Arrey = new JSONArray(response);
                        JSONObject Object = Arrey.getJSONObject(0);
                        UserModel userModel = new UserModel();
                        userModel.id = Object.getInt("id");
                        userModel.name = Object.getString("Name");
                        userModel.lName = Object.getString("LName");
                        userModel.mobile = Object.getString("Mobile");
                        userModel.email = Object.getString("Email");
                        userModel.pass = Object.getString("Pass");

                        return userModel;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        } else
            return null;
    }

    public String postRegisterInfo(boolean isInternetAvailable, String name, String lName, String mobile, String email, String pass) {

        if (isInternetAvailable) {

            String req = "{\"Name\":\"" + name + "\",\"LName\":\"" + lName + "\",\"Mobile\":\"" + mobile + "\",\"Email\":\"" + email + "\",\"Pass\":\"" + pass + "\",\"Visibility\":1,\"lastUpdate\":1}";
            String response = connectToServerByJson(addr + "login/register", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String editProfile(boolean isInternetAvailable, int id, String name, String lName, String mobile, String email, String pass) {

        if (isInternetAvailable) {

            String req = "{\"id\":" + id + ",\"Name\":\"" + name + "\",\"LName\":\"" + lName + "\",\"Mobile\":\"" + mobile + "\",\"Email\":\"" + email + "\",\"Pass\":\"" + pass + "\",\"Visibility\":1,\"lastUpdate\":1}";
            String response = connectToServerByJson(addr + "login/editProfile", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String changePass(boolean isInternetAvailable, int idUser, String lastPass, String newPass) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/changePassword?iduser=" + idUser + "&lastPass=" + lastPass + "&newpass=" + newPass, "GET");
            Log.i("LOG", response + "");

            return response;
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
                        placesModel.image = Object.getString("image");

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

    public int getOffices(boolean isInternetAvailable) {

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
                        placesModel.tel = Object.getString("tell");
                        placesModel.image = Object.getString("image");

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
                        placesModel.image = Object.getString("image");

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

    public int getMedicals(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Medicals");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "medical/select?lastUpdate=" + maxUpdate, "GET");
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
                        placesModel.image = Object.getString("image");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectMedicalById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewMedical(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectMedicalByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteMedical(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateMedical(placesModel);
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

    public int getServices(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Services");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "service/select?lastUpdate=" + maxUpdate, "GET");
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
                        placesModel.image = Object.getString("image");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectServiceById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewService(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectServiceByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteService(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateService(placesModel);
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

    public int getShoppings(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Shoppings");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "shopping/select?lastUpdate=" + maxUpdate, "GET");
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
                        placesModel.image = Object.getString("image");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectShoppingById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewShopping(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectShoppingByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteShopping(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateShopping(placesModel);
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

    public int getTourisms(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Tourisms");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "tourism/select?lastUpdate=" + maxUpdate, "GET");
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
                        placesModel.cost = Object.getString("Cost");
                        placesModel.image = Object.getString("image");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectTourismById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewTourism(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectTourismByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteTourism(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateTourism(placesModel);
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

    public int getTransports(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Transports");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "transport/select?lastUpdate=" + maxUpdate, "GET");
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
                        placesModel.image = Object.getString("image");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectTransportById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewTransport(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectTransportByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteTransport(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateTransport(placesModel);
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

    public int getRests(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Rests");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "rest/select?lastUpdate=" + maxUpdate, "GET");
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
                        placesModel.placeStar = Object.getInt("placeStar");
                        placesModel.image = Object.getString("image");

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectRestmById(placesModel.id + "");
                            if (s.isEmpty()) {
                                if (placesModel.visibility)
                                    helper.insertNewRest(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectRestByLastUpdate(placesModel.id + "");
                                    if (!placesModel.visibility)
                                        helper.deleteRest(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateRest(placesModel);
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

    public int getImages(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Images");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "images/select?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<ImgModel> imgList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ImgModel imgModel = new ImgModel();
                        imgModel.id = Object.getInt("id");
                        imgModel.type = Object.getInt("Type");
                        imgModel.name = Object.getString("Name");
                        imgModel.lastUpdate = Object.getString("lastUpdate");
                        imgModel.idRow = Object.getInt("idRow");

                        imgList.add(imgModel);

                    }


                    if (imgList.size() > 0) {

                        ImgModel imgModel = new ImgModel();

                        for (int i = 0; i < imgList.size(); i++) {
                            imgModel = imgList.get(i);
                            List<String> s = helper.selectImageId(imgModel.id + "");
                            if (s.isEmpty()) {
                                helper.insertNewImage(imgModel);
                            } else {
                                if (imgModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectImageByLastUpdate(imgModel.id + "");
//                                    if (!placesModel.visibility)
//                                        helper.deleteImage(s.get(0));
                                    if (imgModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateImage(imgModel);
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

    public int getEvents(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Events");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "events/select?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<EventModel> eventList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        EventModel eventModel = new EventModel();
                        eventModel.id = Object.getInt("id");
                        eventModel.startDate = Object.getInt("startDate");
                        eventModel.endDate = Object.getInt("endDate");
                        eventModel.lat = Object.getDouble("Lat");
                        eventModel.lon = Object.getDouble("Long");
                        eventModel.visibility = Object.getBoolean("Visibility");
                        eventModel.title = Object.getString("Title");
                        eventModel.body = Object.getString("Body");
                        eventModel.startTime = Object.getString("startTime");
                        eventModel.endTime = Object.getString("endTime");
                        eventModel.place = Object.getString("Place");
                        eventModel.address = Object.getString("Address");
                        eventModel.phone = Object.getString("Phone");
                        eventModel.website = Object.getString("webSite");
                        eventModel.lastUpdate = Object.getString("lastUpdate");
                        eventModel.image = Object.getString("img");

                        eventList.add(eventModel);

                    }


                    if (eventList.size() > 0) {

                        EventModel eventModel = new EventModel();

                        for (int i = 0; i < eventList.size(); i++) {
                            eventModel = eventList.get(i);
                            List<String> s = helper.selectEventId(eventModel.id + "");
                            if (s.isEmpty()) {
                                helper.insertNewEvent(eventModel);
                            } else {
                                if (eventModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectEventByLastUpdate(eventModel.id + "");
                                    if (!eventModel.visibility)
                                        helper.deleteEvent(s.get(0));
                                    if (eventModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateEvent(eventModel);
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

    public List<MenuModel> getMenu(boolean isInternetAvailable, int id, int idType) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "menu/select?idType=" + idType + "&id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<MenuModel> menuList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        MenuModel menuModel = new MenuModel();
                        menuModel.id = Object.getInt("id");
                        menuModel.idRow = Object.getInt("idRow");
                        menuModel.Type = Object.getInt("Type");
                        menuModel.Price = Object.getString("Price");
                        menuModel.Des = Object.getString("Des");
                        menuModel.Name = Object.getString("Name");

                        menuList.add(menuModel);

                    }
                    return menuList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<FacilityModel> getfacility(boolean isInternetAvailable, int id, int idType) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "facility/select?idType=" + idType + "&id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<FacilityModel> facilityList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        FacilityModel facilityModel = new FacilityModel();
                        facilityModel.id = Object.getInt("id");
                        facilityModel.idRow = Object.getInt("idRow");
                        facilityModel.Type = Object.getInt("Type");
                        facilityModel.Name = Object.getString("Name");

                        facilityList.add(facilityModel);

                    }
                    return facilityList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String postSuggestion(boolean isInternetAvailable, String name, String email, String title, String body, String date) {

        if (isInternetAvailable) {

            String req = "{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"title\":\"" + title + "\",\"body\":\"" + body + "\",\"date\":\"" + Integer.parseInt(date) + "}";
            String response = connectToServerByJson(addr + "suggestion/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

//    public List<EventModel> getEvents(boolean isInternetAvailable, String date) {
//
//        if (isInternetAvailable) {
//
//            String response = connectToServer(addr + "events/select?date=" + Integer.parseInt(date), "GET");
//            Log.i("LOG", response + "");
//
//            if (response != null) {
//
//                List<EventModel> eventList = new ArrayList<>();
//
//                try {
//
//                    JSONArray Arrey = new JSONArray(response);
//                    for (int i = 0; i < Arrey.length(); i++) {
//                        JSONObject Object = Arrey.getJSONObject(i);
//                        EventModel eventModel = new EventModel();
//                        eventModel.id = Object.getInt("id");
//                        eventModel.startDate = Object.getInt("startDate");
//                        eventModel.endDate = Object.getInt("endDate");
//                        eventModel.lat = Object.getDouble("Lat");
//                        eventModel.lon = Object.getDouble("Long");
//                        eventModel.visibility = Object.getBoolean("Visibility");
//                        eventModel.title = Object.getString("Title");
//                        eventModel.body = Object.getString("Body");
//                        eventModel.startTime = Object.getString("startTime");
//                        eventModel.endTime = Object.getString("endTime");
//                        eventModel.place = Object.getString("Place");
//                        eventModel.address = Object.getString("Address");
//                        eventModel.phone = Object.getString("Phone");
//
//
//                        eventList.add(eventModel);
//
//                    }
//                    return eventList;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            return null;
//
//        } else
//            return null;
//    }

    public String postFavorite(boolean isInternetAvailable, int idRow, int idUser, int Type) {

        if (isInternetAvailable) {

            String req = "{\"idRow\":" + idRow + ",\"idUser\":" + idUser + ",\"Type\":" + Type + "}";
            String response = connectToServerByJson(addr + "favorite/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String deleteFavorite(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "favorite/delete?id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null){

                return response;

            }
            return null;
        } else
            return null;
    }

    public String postLike(boolean isInternetAvailable, int idLR, int idRow, int idUser, int Type, int like, double rate) {

        if (isInternetAvailable) {


            String req = "{\"id\":" + idLR + ",\"idRow\":" + idRow + ",\"idUser\":" + idUser + ",\"Type\":" + Type + ",\"likes\":" + like + ",\"rate\":\"" + rate + "\"}";
            String response = connectToServerByJson(addr + "like/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public void getFavorites(boolean isInternetAvailable, int idUser) {

        if (isInternetAvailable) {

            DatabaseHelper helper = new DatabaseHelper(app.context);
            String response = connectToServer(addr + "favorite/select?idUser=" + idUser, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<ActionModel> actionList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ActionModel actionModel = new ActionModel();
                        actionModel.id = Object.getInt("id");
                        actionModel.idRow = Object.getInt("idRow");
                        actionModel.type = Object.getInt("Type");
                        actionModel.idUser = Object.getInt("idUser");
//                        actionModel.likes = Object.getInt("likes");
//                        actionModel.rate = Object.getDouble("rate");

                        actionList.add(actionModel);

                    }


                    if (actionList.size() > 0) {

                        ActionModel actionModel = new ActionModel();

                        for (int i = 0; i < actionList.size(); i++) {

                            actionModel = actionList.get(i);
                            String tblName = "";

                            switch (actionModel.type) {
                                case 1:
                                    tblName = "Tbl_Eating";
                                    break;
                                case 2:
                                    tblName = "Tbl_Shoppings";
                                    break;
                                case 3:
                                    tblName = "Tbl_Rests";
                                    break;
                                case 4:
                                    tblName = "Tbl_Tourisms";
                                    break;
                                case 5:
                                    tblName = "Tbl_Culturals";
                                    break;
                                case 6:
                                    tblName = "Tbl_Transports";
                                    break;
                                case 7:
                                    tblName = "Tbl_Services";
                                    break;
                                case 8:
                                    tblName = "Tbl_Offices";
                                    break;
                                case 9:
                                    tblName = "Tbl_Medicals";
                                    break;
                                case 10:
                                    tblName = "Tbl_Events";
                                    break;
                                default:
                            }

                            List<String> s = helper.selectAllById(tblName, actionModel.idRow + "");
                            if (!s.isEmpty())
                                helper.updateTblByFavorite(tblName, actionModel.idRow, actionModel.id);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void getLikesAndRates(boolean isInternetAvailable, int idUser) {

        if (isInternetAvailable) {

            DatabaseHelper helper = new DatabaseHelper(app.context);
            String response = connectToServer(addr + "like/userLikes?idUser=" + idUser, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<ActionModel> actionList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ActionModel actionModel = new ActionModel();
                        actionModel.id = Object.getInt("id");
                        actionModel.idRow = Object.getInt("idRow");
                        actionModel.type = Object.getInt("Type");
                        actionModel.idUser = Object.getInt("idUser");
                        actionModel.likes = Object.getInt("likes");
                        actionModel.rate = Object.getDouble("rate");

                        actionList.add(actionModel);

                    }


                    if (actionList.size() > 0) {

                        ActionModel actionModel = new ActionModel();

                        for (int i = 0; i < actionList.size(); i++) {

                            actionModel = actionList.get(i);
                            String tblName = "";

                            switch (actionModel.type) {
                                case 1:
                                    tblName = "Tbl_Eating";
                                    break;
                                case 2:
                                    tblName = "Tbl_Shoppings";
                                    break;
                                case 3:
                                    tblName = "Tbl_Rests";
                                    break;
                                case 4:
                                    tblName = "Tbl_Tourisms";
                                    break;
                                case 5:
                                    tblName = "Tbl_Culturals";
                                    break;
                                case 6:
                                    tblName = "Tbl_Transports";
                                    break;
                                case 7:
                                    tblName = "Tbl_Services";
                                    break;
                                case 8:
                                    tblName = "Tbl_Offices";
                                    break;
                                case 9:
                                    tblName = "Tbl_Medicals";
                                    break;
                                case 10:
                                    tblName = "Tbl_Events";
                                    break;
                                default:
                            }

                            List<String> s = helper.selectAllById(tblName, actionModel.idRow + "");
                            if (!s.isEmpty())
                                helper.updateTblByLikeAndRate(tblName, actionModel.idRow, actionModel.id, actionModel.rate, actionModel.likes);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    public List<CommentModel> getComments(boolean isInternetAvailable, int id, int idType) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "comment/select?type=" + idType + "&idRow=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CommentModel> commentList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CommentModel model = new CommentModel();
                        model.answers = new ArrayList<>();
                        model.id = Object.getInt("id");
                        model.likeCount = Object.getInt("likeCount");
                        model.date = Object.getInt("date");
                        model.name = Object.getString("name");
                        model.body = Object.getString("body");
                        String answers = Object.getString("answers");

                        JSONArray ArreyAnswer = new JSONArray(answers);
                        for (int j = 0; j < ArreyAnswer.length(); j++){
                            JSONObject ObjectAnswer = Arrey.getJSONObject(i);
                            CommentModel modelAnswer = new CommentModel();
                            modelAnswer.id = ObjectAnswer.getInt("id");
                            modelAnswer.name = ObjectAnswer.getString("name");
                            modelAnswer.body = ObjectAnswer.getString("body");

                            model.answers.add(modelAnswer);
                        }



                        commentList.add(model);

                    }
                    return commentList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String postComment(boolean isInternetAvailable, int idRow, int idUser, int idParent, int Type, int likeCount, String Body, int date) {

        if (isInternetAvailable) {


            String req = "{\"idRow\":" + idRow + ",\"idUser\":" + idUser + ",\"idParent\":" + idParent + ",\"Type\":" + Type + ",\"likeCount\":" + 0 + ",\"Body\":\"" + Body + "\",\"Date\":" + date + ", \"isActive\":0}";
            String response = connectToServerByJson(addr + "comment/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }



    public List<NewsModel> getNews(boolean isInternetAvailable, int count) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "news/select?num=" + count, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<NewsModel> newsList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        NewsModel model = new NewsModel();

                        model.id = Object.getInt("id");
                        model.likeCount = Object.getInt("likeCount");
                        model.Type = Object.getInt("Type");
                        model.commentCount = Object.getInt("commentCount");
                        model.Date = Object.getInt("Date");
                        model.Title = Object.getString("Title");
                        model.Body = Object.getString("Body");
                        model.Img = Object.getString("Img");

                        newsList.add(model);

                    }
                    return newsList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

}
