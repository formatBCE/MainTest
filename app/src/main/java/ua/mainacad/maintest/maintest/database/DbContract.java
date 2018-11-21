package ua.mainacad.maintest.maintest.database;

public interface DbContract {

    interface Posts {
        String TABLE_NAME = "posts";
        String ID = "id";
        String USER_ID = "userId";
        String TITLE = "title";
        String BODY = "body";
    }
}
