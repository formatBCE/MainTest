package ua.mainacad.maintest.maintest.dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

import ua.mainacad.maintest.maintest.model.User;

interface BaseDao<T> {

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int updateAll(List<T> objects);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<T> posts);
}
