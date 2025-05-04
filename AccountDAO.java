package org.example.DAO;

import org.example.DTO.AccountDTO;
import org.example.ConnectDB.UtilsJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private ArrayList<AccountDTO> dsAccount = new ArrayList<>();

    public AccountDAO() {
    }

    public ArrayList<AccountDTO> getAllAccounts() {
        Connection con = UtilsJDBC.getConnectDB();
        dsAccount.clear();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AccountDTO account = new AccountDTO(
                    rs.getInt("userID"),
                    rs.getString("userEmail"),
                    rs.getString("userName"),
                    rs.getString("userPassword"),
                    rs.getString("userFullName"),
                    rs.getInt("isAdmin") == 1 ? "Admin" : "User"
                );
                dsAccount.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return dsAccount;
    }

    public Boolean addAccount(AccountDTO account) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "INSERT INTO users (userEmail, userName, userPassword, userFullName, isAdmin) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getEmployeeId());
            stmt.setInt(5, account.getRoleId().equals("Admin") ? 1 : 0);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public Boolean updateAccount(AccountDTO account) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE users SET userEmail = ?, userName = ?, userPassword = ?, userFullName = ?, isAdmin = ? WHERE userID = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getEmployeeId());
            stmt.setInt(5, account.getRoleId().equals("Admin") ? 1 : 0);
            stmt.setInt(6, account.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public Boolean deleteAccount(int id) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "DELETE FROM users WHERE userID = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public ArrayList<AccountDTO> searchAccounts(String searchType, String searchText) {
        Connection con = UtilsJDBC.getConnectDB();
        ArrayList<AccountDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE ";
        
        switch (searchType) {
            case "Email":
                sql += "userEmail LIKE ?";
                break;
            case "Tên tài khoản":
                sql += "userName LIKE ?";
                break;
            case "Mã nhân viên":
                sql += "userFullName LIKE ?";
                break;
            case "Mã quyền":
                sql += "isAdmin = ?";
                break;
            default: // "Tất cả"
                sql += "(userEmail LIKE ? OR userName LIKE ? OR userFullName LIKE ? OR isAdmin = ?)";
                break;
        }
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            if (searchType.equals("Mã quyền")) {
                stmt.setInt(1, searchText.equalsIgnoreCase("Admin") ? 1 : 0);
            } else if (searchType.equals("Tất cả")) {
                stmt.setString(1, "%" + searchText + "%");
                stmt.setString(2, "%" + searchText + "%");
                stmt.setString(3, "%" + searchText + "%");
                stmt.setInt(4, searchText.equalsIgnoreCase("Admin") ? 1 : 0);
            } else {
                stmt.setString(1, "%" + searchText + "%");
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO account = new AccountDTO(
                    rs.getInt("userID"),
                    rs.getString("userEmail"),
                    rs.getString("userName"),
                    rs.getString("userPassword"),
                    rs.getString("userFullName"),
                    rs.getInt("isAdmin") == 1 ? "Admin" : "User"
                );
                result.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return result;
    }

    public ArrayList<AccountDTO> filterByStatus(String status) {
        Connection con = UtilsJDBC.getConnectDB();
        ArrayList<AccountDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        if (!status.equals("Tất cả")) {
            sql += " WHERE isAdmin = ?";
        }
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            if (!status.equals("Tất cả")) {
                stmt.setInt(1, status.equals("Hoạt động") ? 0 : 1);
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AccountDTO account = new AccountDTO(
                    rs.getInt("userID"),
                    rs.getString("userEmail"),
                    rs.getString("userName"),
                    rs.getString("userPassword"),
                    rs.getString("userFullName"),
                    rs.getInt("isAdmin") == 1 ? "Admin" : "User"
                );
                result.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return result;
    }
}