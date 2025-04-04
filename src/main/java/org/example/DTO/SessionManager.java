package org.example.DTO;

public class SessionManager {
    private static UsersDTO currentUser;

    public void setCurrentUser(UsersDTO user) {
        currentUser = user;
    }

    public static UsersDTO getCurrentUser() {
        return currentUser;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }
}