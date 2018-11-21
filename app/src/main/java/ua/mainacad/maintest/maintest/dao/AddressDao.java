package ua.mainacad.maintest.maintest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import ua.mainacad.maintest.maintest.model.Address;

import java.util.List;

@Dao
public interface AddressDao {
    @Insert
    void insert(Address address);

    @Query("SELECT * from address_table WHERE id = :id LIMIT 1")
    List<Address> getAddress(int id);
}
