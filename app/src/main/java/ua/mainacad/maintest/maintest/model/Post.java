package ua.mainacad.maintest.maintest.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "post_table")
public class Post {
    @SerializedName("userId")
    private Integer userId;

    @SerializedName("id")
    @PrimaryKey
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
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
}
