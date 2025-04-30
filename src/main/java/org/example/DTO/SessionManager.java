package org.example.DTO;

public class SessionManager {
    private static UsersDTO currentUser;
    private static SessionManager instance;

    public void setCurrentUser(UsersDTO user) {
        currentUser = user;
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public static UsersDTO getCurrentUser() {
        return currentUser;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }
}