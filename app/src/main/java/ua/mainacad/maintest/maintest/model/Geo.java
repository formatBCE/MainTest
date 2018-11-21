package ua.mainacad.maintest.maintest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "geo_table")
public class Geo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lng")
    private String longitude;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
