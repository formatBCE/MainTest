package ua.mainacad.maintest.maintest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import ua.mainacad.maintest.maintest.model.Photo;
import ua.mainacad.maintest.maintest.model.Post;
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;

public interface Api {

    interface Get {

        @GET("/photos")
        Call<List<Photo>> getPhotos();

        @GET("/posts")
        Call<List<Post>> getPosts();

        @GET("/users")
        Call<List<User>> getUsers();
    }
}
