package org.example.DTO;

public class UsersDTO {
    private int userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userFullName;
    private Boolean isAdmin;

    public UsersDTO() {
    }

    public UsersDTO(int userID, String userName, String userEmail, String userPassword, String userFullName,
            Boolean isAdmin) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userFullName = userFullName;
        this.isAdmin = isAdmin;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    // Setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // toString() để dễ dàng debug và log thông tin
    @Override
    public String toString() {
        return "UsersDTO{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
