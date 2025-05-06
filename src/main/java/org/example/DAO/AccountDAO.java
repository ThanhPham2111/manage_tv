package org.example.DAO;

import org.example.DTO.AccountDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tv";
    private static final String JDBC_USERNAME = "root"; //Thay user nếu có khác
    private static final String JDBC_PASSWORD = "1234"; // Thay pass

    public List<AccountDTO> getAllAccounts() {
        List<AccountDTO> accountList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                AccountDTO account = new AccountDTO(
                    rs.getInt("userID"),
                    rs.getString("userEmail"),
                    rs.getString("userName"),
                    rs.getString("userPassword"),
                    rs.getString("maNV"), // Sửa từ userFullName sang maNV
                    rs.getInt("isAdmin") == 1 ? "Q1" : "Q2" // Q1 là admin, Q2 là user thường
                );
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public boolean addAccount(AccountDTO account) {
        String sql = "INSERT INTO users (userName, userEmail, userPassword, userFullName, isAdmin) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getEmail());
            pstmt.setString(3, account.getPassword());
            pstmt.setString(4, account.getEmployeeId()); // Sử dụng employeeId cho userFullName
            pstmt.setInt(5, account.getRoleId().equals("Q1") ? 1 : 0);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(AccountDTO account) {
        String sql = "UPDATE users SET userName = ?, userEmail = ?, userPassword = ?, userFullName = ?, isAdmin = ? WHERE userID = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getEmail());
            pstmt.setString(3, account.getPassword());
            pstmt.setString(4, account.getEmployeeId()); // Sử dụng employeeId cho userFullName
            pstmt.setInt(5, account.getRoleId().equals("Q1") ? 1 : 0);
            pstmt.setInt(6, account.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int id) {
        String sql = "DELETE FROM users WHERE userID = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AccountDTO> searchAccounts(String searchType, String searchText) {
        List<AccountDTO> result = new ArrayList<>();
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
            default: // Tất cả
                sql += "(userEmail LIKE ? OR userName LIKE ? OR userFullName LIKE ?)";
                break;
        }
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (searchType.equals("Tất cả")) {
                pstmt.setString(1, "%" + searchText + "%");
                pstmt.setString(2, "%" + searchText + "%");
                pstmt.setString(3, "%" + searchText + "%");
            } else if (searchType.equals("Mã quyền")) {
                pstmt.setInt(1, searchText.equalsIgnoreCase("Q1") ? 1 : 0);
            } else {
                pstmt.setString(1, "%" + searchText + "%");
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AccountDTO account = new AccountDTO(
                        rs.getInt("userID"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword"),
                        rs.getString("userFullName"),
                        rs.getInt("isAdmin") == 1 ? "Q1" : "Q2"
                    );
                    result.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<AccountDTO> filterByStatus(String status) {
        List<AccountDTO> result = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        if (!status.equals("Tất cả")) {
            sql += " WHERE isAdmin = ?";
        }
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (!status.equals("Tất cả")) {
                pstmt.setInt(1, status.equals("Khóa") ? 1 : 0);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    AccountDTO account = new AccountDTO(
                        rs.getInt("userID"),
                        rs.getString("userEmail"),
                        rs.getString("userName"),
                        rs.getString("userPassword"),
                        rs.getString("userFullName"),
                        rs.getInt("isAdmin") == 1 ? "Q1" : "Q2"
                    );
                    result.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}