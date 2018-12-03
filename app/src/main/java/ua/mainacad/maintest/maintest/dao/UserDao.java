package ua.mainacad.maintest.maintest.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import ua.mainacad.maintest.maintest.model.User;
import java.util.List;

@Dao
public interface UserDao extends BaseDao<User> {

    @Query("SELECT * from user_table ORDER BY id ASC")
    LiveData<List<User>> getAll();
}
