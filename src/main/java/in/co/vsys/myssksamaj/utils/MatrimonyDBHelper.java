package in.co.vsys.myssksamaj.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import in.co.vsys.myssksamaj.model.NotificationModel;

public class MatrimonyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notification.db";
    private static final String TABLE_NAME_NOTIFICATION = "tableNotification";
    private static final String COLUMN_NOTIFICATION_ID = "notificationId";
    private static final String COLUMN_NOTIFICATION_NAME = "notificationName";
    private static final String COLUMN_NOTIFICATION_DESC = "notificationDesc";
    private static final String COLUMN_NOTIFICATION_IMAGE = "notificationImage";
    private static final String COLUMN_NOTIFICATION_TIME = "notificationTime";
    private static final String COLUMN_NOTIFICATION_MEMBER_ID = "fkNotificationId";
    private static final String COLUMN_CREATED_BY = "createdBy";

    public MatrimonyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_NAME_NOTIFICATION + "(" +
                COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTIFICATION_NAME + " TEXT, " +
                COLUMN_NOTIFICATION_DESC + " TEXT, " +
                COLUMN_NOTIFICATION_IMAGE + " TEXT, " +
                COLUMN_NOTIFICATION_TIME + " TEXT, " +
                COLUMN_NOTIFICATION_MEMBER_ID + " INTEGER, " +
                COLUMN_CREATED_BY + " TEXT " +
                ")";

        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NOTIFICATION);
        onCreate(db);
    }

    //*********************************TABLE OPERATION HERE****************************//

    public boolean addNotification(NotificationModel model) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOTIFICATION_NAME, model.getNotificationTitle());
        contentValues.put(COLUMN_NOTIFICATION_DESC, model.getNotificationDesc());
        contentValues.put(COLUMN_NOTIFICATION_IMAGE, model.getNotificationImage());
        contentValues.put(COLUMN_NOTIFICATION_TIME, model.getNotificationTime());
        contentValues.put(COLUMN_NOTIFICATION_MEMBER_ID, model.getNotificationMemberId());
        contentValues.put(COLUMN_CREATED_BY, model.getCreatedBy());

        long insertRow = database.insertWithOnConflict(TABLE_NAME_NOTIFICATION, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
         return !(insertRow == -1);
    }

    public List<NotificationModel> notificationList(int memberId) {

        List<NotificationModel> list = new ArrayList<>();
//        String query = "SELECT * FROM " + TABLE_NAME_NOTIFICATION + " WHERE " + COLUMN_NOTIFICATION_MEMBER_ID + " = " + memberId + " ORDER BY " + TABLE_NAME_NOTIFICATION + "." + COLUMN_NOTIFICATION_ID + " DESC";
        String query = "SELECT * FROM " + TABLE_NAME_NOTIFICATION + " ORDER BY " + TABLE_NAME_NOTIFICATION + "." + COLUMN_NOTIFICATION_ID + " DESC";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationModel model = new NotificationModel();
                model.setNotificationId(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_ID)));
                model.setNotificationTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_NAME)));
                model.setNotificationDesc(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_DESC)));
                model.setNotificationImage(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_IMAGE)));
                model.setNotificationTime(cursor.getString(cursor.getColumnIndex(COLUMN_NOTIFICATION_TIME)));
                model.setCreatedBy(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_BY)));

                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}