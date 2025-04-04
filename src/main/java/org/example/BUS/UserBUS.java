package org.example.BUS;

import java.util.ArrayList;

import org.example.DAO.UserDAO;
import org.example.DTO.UsersDTO;

public class UserBUS {
    private static ArrayList<UsersDTO> listAccount = new ArrayList<>();
    private UserDAO userDAO = new UserDAO();
    private static String currentUserName;

    public UserBUS() {
        listAccount = userDAO.getAllUsers();
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String currentUserName) {
        UserBUS.currentUserName = currentUserName;
    }

    public void getUserAll() {
        listAccount = userDAO.getAllUsers();
    }

    public ArrayList<UsersDTO> getListAccount() {
        return listAccount;
    }

    public boolean login(String userEmail, String password) {
        return userDAO.login(userEmail, password);
    }

    public boolean register(UsersDTO user) {
        return userDAO.signUp(user);
    }

    public UsersDTO getUser(String userName) {
        for (UsersDTO user : listAccount) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public Boolean insertUser(UsersDTO user) {
        if (userDAO.addUser(user)) {
            listAccount.add(user);
            return true;
        }
        return false;
    }

    // public boolean getInfoUser(String userEmail, String password) {
    // return userDAO.getInfoUser(userEmail, password);
    // }
    public boolean deleteUser(int userID) {
        if (userDAO.deleteUser(userID)) {
            listAccount.removeIf(user -> user.getUserID() == userID);
            return true;
        }
        return false;
    }

    public boolean updateUser(UsersDTO user) {
        if (userDAO.updateUser(user)) {
            for (int i = 0; i < listAccount.size(); i++) {
                if (listAccount.get(i).getUserID() == user.getUserID()) {
                    listAccount.set(i, user);
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean insertUsers(ArrayList<UsersDTO> users) {
        boolean allInserted = true;

        for (UsersDTO user : users) {
            if (userDAO.checkIfUserExists(user.getUserName(), user.getUserEmail())) {
                allInserted = false;
                return false;
            }
        }
        if (allInserted) {
            boolean inserted = userDAO.addUsersBatch(users);
            if (inserted) {
                listAccount.addAll(users);
            } else {
                allInserted = false;
            }
        }

        return allInserted;
    }

    public String[] getHeaders() {
        return new String[] { "Tên người dùng", "Email", "Mật khẩu", "Tên dầy đủ ", "Vai trò" };
    }

    public static ArrayList<UsersDTO> search(String keyword, String type) {
        ArrayList<UsersDTO> result = new ArrayList<>();
        listAccount.forEach((user) -> {
            switch (type) {
                case "Tất cả":
                    if (String.valueOf(user.getUserID()).toString().toLowerCase().contains(keyword.toLowerCase())
                            || user.getUserName().toLowerCase().contains(keyword.toLowerCase())
                            || user.getUserEmail().toLowerCase().contains(keyword.toLowerCase())
                            || user.getUserPassword().toLowerCase().contains(keyword.toLowerCase())
                            || user.getUserFullName().toString().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(user);
                    }
                    break;
                case "Mã người dùng":
                    if (String.valueOf(user.getUserID()).toString().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(user);
                    }
                    break;
                case "Tên người dùng":
                    if (user.getUserName().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(user);
                    }
                    break;
                case "Email":
                    if (user.getUserEmail().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(user);
                    }
                    break;
                case "Mật khẩu":
                    if (user.getUserPassword().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(user);
                    }
                    break;
                case "Họ và tên":
                    if (user.getUserFullName().toString().toLowerCase().contains(keyword.toLowerCase())) {
                        result.add(user);
                    }
                    break;
            }

        });
        return result;
    }

}
