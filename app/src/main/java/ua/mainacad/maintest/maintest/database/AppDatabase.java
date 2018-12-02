package ua.mainacad.maintest.maintest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import ua.mainacad.maintest.maintest.dao.PostDao;
import ua.mainacad.maintest.maintest.dao.UserDao;
import ua.mainacad.maintest.maintest.model.Post;
import ua.mainacad.maintest.maintest.model.User;

@Database(entities = {Post.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract UserDao userDao();
}
