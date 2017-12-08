package com.example.arka.arasfreezone1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.arka.arasfreezone1.models.ImgModel;
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



    public List<String> selectMedicalById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Medicals WHERE id=" + r;
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

    public void insertNewMedical(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Medicals (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectMedicalByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Medicals WHERE id=" + r;
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

    public void updateMedical(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Medicals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteMedical(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Medicals WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }



    public List<String> selectServiceById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Services WHERE id=" + r;
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

    public void insertNewService(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Services (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectServiceByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Services WHERE id=" + r;
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

    public void updateService(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Services SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteService(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Services WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }




    public List<String> selectShoppingById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Shoppings WHERE id=" + r;
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

    public void insertNewShopping(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Shoppings (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectShoppingByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Shoppings WHERE id=" + r;
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

    public void deleteShopping(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Shoppings WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public void updateShopping(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Shoppings SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }




    public List<String> selectTourismById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Tourisms WHERE id=" + r;
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

    public void insertNewTourism(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Tourisms (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,cost) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "','" +placesModel.cost + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectTourismByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Tourisms WHERE id=" + r;
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

    public void deleteTourism(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Tourisms WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public void updateTourism(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Tourisms SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "',cost='" + placesModel.cost + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }




    public List<String> selectTransportById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Transports WHERE id=" + r;
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

    public void insertNewTransport(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Transports (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectTransportByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Transports WHERE id=" + r;
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

    public void deleteTransport(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Transports WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public void updateTransport(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Transports SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }




    public List<String> selectRestmById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Rests WHERE id=" + r;
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

    public void insertNewRest(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Rests (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,placeStar) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" +placesModel.name + "','" +placesModel.lat + "','" +placesModel.lon + "','" +placesModel.phone + "','" +placesModel.star + "','" +placesModel.starCount + "','" + placesModel.likeCount + "','" +placesModel.info + "','" +placesModel.website + "','" +placesModel.visibility + "','" +placesModel.lastUpdate + "','" +placesModel.address + "','" +placesModel.placeStar + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectRestByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Rests WHERE id=" + r;
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

    public void deleteRest(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Tourisms WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public void updateRest(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Rests SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat+ ",lon=" + placesModel.lon + ",phone='" + placesModel.phone+ "',star=" + placesModel.star+ ",starCount=" + placesModel.starCount+ ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info+ "',website='" + placesModel.website+ "',visibility=" + placesModel.visibility+ ",lastUpdate='" + placesModel.lastUpdate+ "',address='" + placesModel.address + "',placeStar='" + placesModel.placeStar + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }




    public List<String> selectImageId(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Images WHERE id=" + r;
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

    public void insertNewImage(ImgModel imgModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Images (id,type,name,lastUpdate,idRow) VALUES('"
                + imgModel.id + "','" + imgModel.type + "','" +imgModel.name + "','" +imgModel.lastUpdate + "','" +imgModel.idRow + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectImageByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Images WHERE id=" + r;
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

    public void updateImage(ImgModel imgModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Images SET type=" + imgModel.type + ",name='" + imgModel.name + "',lastUpdate='" + imgModel.lastUpdate+ "',idRow=" + imgModel.idRow + " WHERE id=" + imgModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

}
