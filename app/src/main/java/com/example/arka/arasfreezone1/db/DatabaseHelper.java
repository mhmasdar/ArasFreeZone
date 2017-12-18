package com.example.arka.arasfreezone1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.arka.arasfreezone1.models.EventModel;
import com.example.arka.arasfreezone1.models.ImgModel;
import com.example.arka.arasfreezone1.models.PlacesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EHSAN on 11/29/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


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

    public List<PlacesModel> selectAllPlacesToList(String tblName) {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM " + tblName;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
                pm.name = cursor.getString(cursor.getColumnIndex("name"));
                pm.address = cursor.getString(cursor.getColumnIndex("address"));
                if (!tblName.equals("Tbl_Offices"))
                    pm.star = cursor.getDouble(cursor.getColumnIndex("star"));
                //pm.imgPersonal = cursor.getString(cursor.getColumnIndex("imgPersonal"));


                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public List<EventModel> selectAllEventsToList(String tblName) {

        List<EventModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM " + tblName;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                EventModel pm = new EventModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.startDate = cursor.getInt(cursor.getColumnIndex("startDate"));
                pm.title = cursor.getString(cursor.getColumnIndex("title"));
                pm.address = cursor.getString(cursor.getColumnIndex("address"));
                //pm.imgPersonal = cursor.getString(cursor.getColumnIndex("imgPersonal"));


                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public PlacesModel selectPlacesDetail(String tblName, int id) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + id;
        PlacesModel pm = new PlacesModel();
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {

                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.name = cursor.getString(cursor.getColumnIndex("name"));
                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
                pm.address = cursor.getString(cursor.getColumnIndex("address"));
                pm.phone = cursor.getString(cursor.getColumnIndex("phone"));
                pm.star = cursor.getDouble(cursor.getColumnIndex("star"));
                pm.starCount = cursor.getInt(cursor.getColumnIndex("starCount"));
                pm.likeCount = cursor.getInt(cursor.getColumnIndex("likeCount"));
                pm.info = cursor.getString(cursor.getColumnIndex("Info"));
                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));
                pm.idStartDay = cursor.getInt(cursor.getColumnIndex("idStartDay"));
                pm.idEndDay = cursor.getInt(cursor.getColumnIndex("idEndDay"));
                pm.startTime = cursor.getString(cursor.getColumnIndex("startTime"));
                pm.endTime = cursor.getString(cursor.getColumnIndex("endTime"));
                if (tblName.equals("Tbl_Tourisms"))
                    pm.cost = cursor.getString(cursor.getColumnIndex("cost"));
                if (tblName.equals("Tbl_Rests"))
                    pm.placeStar = cursor.getInt(cursor.getColumnIndex("placeStar"));
//                pm.visibility = cursor.getInt(cursor.getColumnIndex("visibility"));
//                pm.lastUpdate = cursor.getInt(cursor.getColumnIndex("lastUpdate"));


            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return pm;
    }

    public EventModel selectEventsDetail(String tblName, int id) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + id;
        EventModel pm = new EventModel();
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {

                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.body = cursor.getString(cursor.getColumnIndex("body"));
                pm.title = cursor.getString(cursor.getColumnIndex("title"));
                pm.startTime = cursor.getString(cursor.getColumnIndex("startTime"));
                pm.startDate = cursor.getInt(cursor.getColumnIndex("startDate"));
                pm.endDate = cursor.getInt(cursor.getColumnIndex("endDate"));
                pm.endTime = cursor.getString(cursor.getColumnIndex("endTime"));
                pm.place = cursor.getString(cursor.getColumnIndex("place"));
                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
                pm.address = cursor.getString(cursor.getColumnIndex("address"));
                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));
                pm.phone = cursor.getString(cursor.getColumnIndex("phone"));

//                pm.visibility = cursor.getInt(cursor.getColumnIndex("visibility"));
//                pm.lastUpdate = cursor.getInt(cursor.getColumnIndex("lastUpdate"));


            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return pm;
    }

    public PlacesModel selectOfficesDetail(String tblName, int id) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + id;
        PlacesModel pm = new PlacesModel();
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {

                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.name = cursor.getString(cursor.getColumnIndex("name"));
                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
                pm.address = cursor.getString(cursor.getColumnIndex("address"));
                pm.phone = cursor.getString(cursor.getColumnIndex("tel"));
                pm.info = cursor.getString(cursor.getColumnIndex("Info"));
                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));


            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return pm;
    }



    public double selectRateValueById(String tblName, int r) {
        String userLike = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "userRate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userLike = cursor.getString(cursor.getColumnIndex(request));

        }
        cursor.close();
        ArasDB.close();

        if (userLike != null) {
            if (Double.parseDouble(userLike) > 0)
                return Double.parseDouble(userLike);
            else
                return -1;
        } else
            return -1;

    }

    public int selectRateById(String tblName, int r) {
        String userLike = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "idUserRate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userLike = cursor.getString(cursor.getColumnIndex(request));

        }
        cursor.close();
        ArasDB.close();
        if (userLike != null) {
            if (Integer.parseInt(userLike) > 0)
                return Integer.parseInt(userLike);
            else
                return -1;
        } else
            return -1;

    }

    public int selectLikeById(String tblName, int r) {
        String userLike = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "userLike";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userLike = cursor.getString(cursor.getColumnIndex(request));
        }
        cursor.close();
        ArasDB.close();
        if (userLike != null) {
            if (Integer.parseInt(userLike) > 0)
                return Integer.parseInt(userLike);
            else
                return -1;
        } else return -1;

    }

    public void updateTblByLike(String tblName, int idRow, int idLike, int likeCount) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userLike=" + idLike + ",likeCount=" + likeCount + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblByRate(String tblName, int idRow, int idRate, double rateValue) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userRate=" + rateValue + ",idUserRate=" + idRate + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public int selectFavoriteById(String tblName, int r) {
        String userFavorite = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "userFavorite";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userFavorite = cursor.getString(cursor.getColumnIndex(request));

        }
        cursor.close();
        ArasDB.close();
        if (userFavorite != null) {
            if (Integer.parseInt(userFavorite) > 0)
                return Integer.parseInt(userFavorite);
            else
                return -1;
        } else
            return -1;

    }

    public void updateTblByFavorite(String tblName, int idRow, int idFavorite) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userFavorite=" + idFavorite + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblByLikeAndRate(String tblName, int idRow, int idLR, double rate, int like) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userLike=" + ((like == 0) ? 0 : idLR) + ",idUserRate=" + ((rate == -1) ? 0 : idLR) + ",userRate=" + rate + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblsAfterExit(String tblName) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userFavorite=-1,userLike=-1,userRate=-1,idUserRate=-1";
        ArasDB.execSQL(sql);
        ArasDB.close();
    }




    public List<String> selectAllById(String tblName, String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
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
        sql = "INSERT INTO Tbl_Culturals (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Culturals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Offices (id,type,name,lat,lon,info,website,visibility,lastUpdate,address,tel,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.tel + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Offices SET type=" + placesModel.type + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',tel='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Eating (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Eating SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Medicals (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Medicals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Services (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Services SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Shoppings (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Shoppings SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Tourisms (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,cost,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.cost + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Tourisms SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',cost='" + placesModel.cost + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Transports (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Transports SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
        sql = "INSERT INTO Tbl_Rests (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,placeStar,image) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.placeStar + "','" + placesModel.image + "')";
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
        sql = "UPDATE Tbl_Rests SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",info='" + placesModel.info + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',placeStar=" + placesModel.placeStar + ",image='" + placesModel.image + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }


    public List<String> selectEventId(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Events WHERE id=" + r;
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

    public void insertNewEvent(EventModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Events (id,body,title,startTime,startDate,endDate,endTime,place,lat,lon,address,phone,website,visibility,lastUpdate,image) VALUES('"
                + placesModel.id + "','" + placesModel.body + "','" + placesModel.title + "','" + placesModel.startTime + "','" + placesModel.startDate + "','" + placesModel.endDate + "','" + placesModel.endTime + "','" + placesModel.place + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.address + "','" + placesModel.phone + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.image + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectEventByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Events WHERE id=" + r;
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

    public void updateEvent(EventModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Events SET body='" + placesModel.body + "',title='" + placesModel.title + "',startTime='" + placesModel.startTime + "',startDate=" + placesModel.startDate + ",endDate=" + placesModel.endDate + ",endTime='" + placesModel.endTime + "',place='" + placesModel.place + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",address='" + placesModel.address + "',phone='" + placesModel.phone + "',website='" + placesModel.website + "',visibility=" + placesModel.visibility + ",lastUpdate='" + placesModel.lastUpdate + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteEvent(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Events WHERE id=" + id + "";
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
                + imgModel.id + "','" + imgModel.type + "','" + imgModel.name + "','" + imgModel.lastUpdate + "','" + imgModel.idRow + "')";
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
        sql = "UPDATE Tbl_Images SET type=" + imgModel.type + ",name='" + imgModel.name + "',lastUpdate='" + imgModel.lastUpdate + "',idRow=" + imgModel.idRow + " WHERE id=" + imgModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

}
