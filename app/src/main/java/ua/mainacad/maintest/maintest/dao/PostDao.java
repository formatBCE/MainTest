package ua.mainacad.maintest.maintest.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;

@Dao
public interface PostDao extends BaseDao<Post> {

    /*@Update
    void update(Post post);*/

    @Query("SELECT * from post_table ORDER BY id ASC")
    LiveData<List<Post>> getAll();

   /* @Query("SELECT * from post_table WHERE userId = :userId ORDER BY id ASC")
    Single<List<Post>> getAllForUser(int userId);*/
}
