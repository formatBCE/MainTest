package ua.mainacad.maintest.maintest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * from user_table ORDER BY id ASC")
    List<User> getAllUsers();
}
