package ua.mainacad.maintest.maintest.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import ua.mainacad.maintest.maintest.model.Photo;
import ua.mainacad.maintest.maintest.model.Post;
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;

public interface Api {

    interface Get {
        @GET("/photos")
        Single<List<Photo>> getAllPhotos();

        @GET("/posts")
        Single<List<Post>> getAllPosts();

        @GET("/users")
        Single<List<User>> getAllUsers();
    }
}
