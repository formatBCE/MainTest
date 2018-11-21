package ua.mainacad.maintest.maintest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import ua.mainacad.maintest.maintest.model.Geo;

import java.util.List;

@Dao
public interface GeoDao {
    @Insert
    void insert(Geo geo);

    @Query("SELECT * from geo_table WHERE id = :id LIMIT 1")
    List<Geo> getGeo(int id);
}
