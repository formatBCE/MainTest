package ua.mainacad.maintest.maintest;

import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import ua.mainacad.maintest.maintest.dao.PostDao;
import ua.mainacad.maintest.maintest.database.AppDatabase;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UtilsTest {

    @Test
    public void isEmailValid_WhenInputIsEmpty() {
        // Arrange
        String email = "";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEmailValid_WhenInputIsNull() {
        // Act
        Utils.isEmailValid(null);
    }

    @Test
    public void isEmailValid_WhenInputHasTwoAts() {
        // Arrange
        String email = "user@google@com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isEmailValid_WhenInputHasNoAts() {
        // Arrange
        String email = "user.google.com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isEmailValid_WhenInputIsCorrect() {
        // Arrange
        String email = "user@mydomain.com";
        // Act
        boolean isValid = Utils.isEmailValid(email);
        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testPostsList() {
        // Arrange
        List<Post> data = new ArrayList<>();
        data.add(new Post());
        ICallback<List<Post>> callback = mock(ICallback.class);
        AppDatabase db = mock(AppDatabase.class);
        PostDao postDao = mock(PostDao.class);
        when(postDao.getAllSynchronously()).thenReturn(data);
        when(db.postDao()).thenReturn(postDao);
        // Act
        Utils.loadPostsFromDb(db, callback, Schedulers.trampoline(), Schedulers.trampoline());
        // Assert
        verify(callback).call(data);
    }
}