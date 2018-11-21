package ua.mainacad.maintest.maintest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "main_db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + DbContract.Posts.TABLE_NAME
                + " ("
                + DbContract.Posts.ID
                + " INTEGER PRIMARY KEY, "
                + DbContract.Posts.USER_ID
                + " INTEGER, "
                + DbContract.Posts.TITLE
                + " TEXT, "
                + DbContract.Posts.BODY
                + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void savePosts(List<Post> posts) throws Exception {
        SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        for (Post post : posts) {
            values.clear();
            final int id = post.getId();
            values.put(DbContract.Posts.ID, id);
            values.put(DbContract.Posts.USER_ID, post.getUserId());
            values.put(DbContract.Posts.BODY, post.getBody());
            values.put(DbContract.Posts.TITLE, post.getTitle());
            if (exists(DbContract.Posts.TABLE_NAME, DbContract.Posts.ID, id)) {
                db.update(
                        DbContract.Posts.TABLE_NAME,
                        values,
                        DbContract.Posts.ID + " = ?",
                        new String[]{String.valueOf(id)}
                );
            } else {
                db.insert(
                        DbContract.Posts.TABLE_NAME,
                        null,
                        values);
            }

        }
        db.setTransactionSuccessful();
    }

    private boolean exists(
            @NonNull String tableName,
            @NonNull String columnName,
            @NonNull Object value) {
        boolean exists = false;
        final Cursor cursor = getReadableDatabase()
                .rawQuery(
                        "SELECT COUNT(*) FROM "
                                + tableName
                                + " WHERE "
                                + columnName
                                + "= ?",
                        new String[]{String.valueOf(value)});
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0;
        }
        cursor.close();
        return exists;
    }

    @NonNull
    public List<Post> fetchAllPosts() {
        List<Post> posts = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(
                DbContract.Posts.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DbContract.Posts.ID));
                int userId = cursor.getInt(cursor.getColumnIndex(DbContract.Posts.USER_ID));
                String title = cursor.getString(cursor.getColumnIndex(DbContract.Posts.TITLE));
                String body = cursor.getString(cursor.getColumnIndex(DbContract.Posts.BODY));
                Post post = new Post();
                post.setId(id);
                post.setUserId(userId);
                post.setTitle(title);
                post.setBody(body);
                posts.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return posts;
    }
}
