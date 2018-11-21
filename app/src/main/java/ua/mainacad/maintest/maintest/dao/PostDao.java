package ua.mainacad.maintest.maintest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insert(Post post);

    @Query("SELECT * from post_table ORDER BY id ASC")
    List<Post> getAllPosts();

    @Query("SELECT * from post_table WHERE userId = :userId ORDER BY id ASC")
    List<Post> getAllPostsForUser(int userId);
}
