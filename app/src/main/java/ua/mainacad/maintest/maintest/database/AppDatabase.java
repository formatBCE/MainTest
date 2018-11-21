package ua.mainacad.maintest.maintest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import ua.mainacad.maintest.maintest.dao.PostDao;
import ua.mainacad.maintest.maintest.model.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostDao postDao();
}
