package com.example.arka.arasfreezone1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EHSAN on 11/29/2017.
 */

public class DatabaseHelper  extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, IOHelper.DATA_DIRECTORY + IOHelper.DATABASE_FILE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public String getLastUpdate(String tableName) {

        String lastUpdate;
        SQLiteDatabase candidateDB = getReadableDatabase();
        Cursor cursor = candidateDB.rawQuery("SELECT MAX(lastUpdate) FROM "
                + tableName, null);
        cursor.moveToFirst();
        lastUpdate = cursor.getString(0);
        cursor.close();
        if (lastUpdate == null)
            return "0";
        else
            return lastUpdate;
    }



    public List<String> selectCaltureById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Culturals WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewCalture(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Culturals (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectCaltureByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Culturals WHERE id=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void updateCalture(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Culturals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteCalture(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Culturals WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }



    public List<String> selectOfficeById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Offices WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewOffice(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Offices (id,type,name,lat,lon,info,website,visibility,lastUpdate,address, tel) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "','" +placesModel.tel + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectOfficeByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Offices WHERE id=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void deleteOffice(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Offices WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public void updateOffice(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Offices SET type=" + placesModel.type + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "',tel='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }



    public List<String> selectEatingById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Eating WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewEating(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Eating (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectEatingByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Eating WHERE id=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void updateEating(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Eating SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteEating(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Eating WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

}
