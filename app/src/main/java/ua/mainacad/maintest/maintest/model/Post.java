package ua.mainacad.maintest.maintest.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;
import io.reactivex.annotations.Nullable;

@Entity(tableName = "post_table")
public class Post {

    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    private int userId;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "body")
    @SerializedName("body")
    private String body;

    @Exclude
    private transient boolean isFromFirebase;

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isFromFirebase() {
        return isFromFirebase;
    }

    public void setFromFirebase(boolean fromFirebase) {
        isFromFirebase = fromFirebase;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Post)) {
            return false;
        }
        return ((Post) obj).id == id;
    }
}
