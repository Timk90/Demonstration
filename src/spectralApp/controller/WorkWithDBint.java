package spectralApp.controller;

/**
 * Created by Tim on 29.06.2017.
 */
public interface WorkWithDBint {

    int checkUserName(String name, String pass);

    int checkUserName(String name);

    int addUser(String name, String password);

    int deleteUser(String name);
}
