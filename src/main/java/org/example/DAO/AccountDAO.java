package DAO;

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
    private static final String JDBC_USERNAME = "root"; //Username
    private static final String JDBC_PASSWORD = ""; // Thay lại pass

    public AccountDAO() {
        // Khởi tạo dữ liệu mẫu
        accountList = new ArrayList<>();
        accountList.add(new AccountDTO(1, "admin@example.com", "admin", "Thanh123", "NV12", "Q4"));
        accountList.add(new AccountDTO(2, "baduoc@example.com", "BaDuocSeller", "baduoc@123", "NV3", "Q2"));
        accountList.add(new AccountDTO(3, "nhanvien@example.com", "NhanVien", "feafe@123", "NV20", "Q2"));
        accountList.add(new AccountDTO(4, "quanly@example.com", "Quan Ly", "quanly@123", "NV9", "Q1"));
        accountList.add(new AccountDTO(5, "thanhtu@example.com", "ThanhTuNH", "thanhtu@434", "NV5", "Q5"));
        accountList.add(new AccountDTO(6, "tridung@example.com", "TriDungSeller", "tridung@fe3", "NV1", "Q2"));
        accountList.add(new AccountDTO(7, "vanhoang@example.com", "VanHoangAdmin", "vanhoang@feaf2", "NV4", "Q3"));
        accountList.add(new AccountDTO(8, "vantai@example.com", "VanTaiNH", "vantai@1212", "NV12", "Q5"));
        accountList.add(new AccountDTO(9, "yenhan@example.com", "YenHanPhuBH", "yenhan@123", "NV23", "Q3"));
    }

    public List<AccountDTO> getAllAccounts() {
        return accountList;
    }

    public boolean addAccount(AccountDTO account) {
        return accountList.add(account);
    }

    public boolean updateAccount(AccountDTO account) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getId() == account.getId()) {
                accountList.set(i, account);
                return true;
            }
        }
        return false;
    }

    public boolean deleteAccount(int id) {
        return accountList.removeIf(a -> a.getId() == id);
    }

    public List<AccountDTO> searchAccounts(String searchType, String searchText) {
        List<AccountDTO> result = new ArrayList<>();
        searchText = searchText.toLowerCase();
        
        for (AccountDTO account : accountList) {
            switch (searchType) {
                case "Email":
                    if (account.getEmail().toLowerCase().contains(searchText)) {
                        result.add(account);
                    }
                    break;
                case "Tên tài khoản":
                    if (account.getUsername().toLowerCase().contains(searchText)) {
                        result.add(account);
                    }
                    break;
                case "Mã nhân viên":
                    if (account.getEmployeeId().toLowerCase().contains(searchText)) {
                        result.add(account);
                    }
                    break;
                case "Mã quyền":
                    if (account.getRoleId().toLowerCase().contains(searchText)) {
                        result.add(account);
                    }
                    break;
                default: // "Tất cả"
                    if (account.getEmail().toLowerCase().contains(searchText) ||
                        account.getUsername().toLowerCase().contains(searchText) ||
                        account.getEmployeeId().toLowerCase().contains(searchText) ||
                        account.getRoleId().toLowerCase().contains(searchText)) {
                        result.add(account);
                    }
                    break;
            }
        }
        return result;
    }

    public List<AccountDTO> filterByStatus(String status) {
        List<AccountDTO> result = new ArrayList<>();
        
        for (AccountDTO account : accountList) {
            if (status.equals("Tất cả")) {
                result.add(account);
            } else if (status.equals("Hoạt động") && !(account.getRoleId().equals("Q4") || account.getRoleId().equals("Q5"))) {
                result.add(account);
            } else if (status.equals("Khóa") && (account.getRoleId().equals("Q4") || account.getRoleId().equals("Q5"))) {
                result.add(account);
            }
        }
        return result;
    }
}