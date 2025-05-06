package org.example.BUS;

import org.example.DTO.AccountDTO;
import org.example.DAO.AccountDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountBUS {
    private AccountDAO accountDAO;

    public AccountBUS() {
        accountDAO = new AccountDAO();
    }

    public List<AccountDTO> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public boolean addAccount(AccountDTO account) {
        return accountDAO.addAccount(account);
    }

    public boolean updateAccount(AccountDTO account) {
        return accountDAO.updateAccount(account);
    }

    public boolean deleteAccount(int id) {
        return accountDAO.deleteAccount(id);
    }

    public List<AccountDTO> searchAccounts(String searchType, String searchText) {
        return accountDAO.searchAccounts(searchType, searchText);
    }

    public List<AccountDTO> filterByStatus(String status) {
        return accountDAO.filterByStatus(status);
    }

    public List<AccountDTO> applyFilters(String status, String searchType, String searchText) {
        List<AccountDTO> accounts = accountDAO.getAllAccounts();
        List<AccountDTO> result = new ArrayList<>();
    
        for (AccountDTO account : accounts) {
            // Sửa logic lọc quyền tài khoản
            boolean statusMatch = status.equals("Tất cả") ||
                    (status.equals("Admin") && account.getRoleId().equals("Q1")) ||
                    (status.equals("Thường") && account.getRoleId().equals("Q2"));
    
            boolean searchMatch = searchText.isEmpty();
            if (!searchMatch) {
                switch (searchType) {
                    case "Tất cả":
                        searchMatch = account.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                                account.getUsername().toLowerCase().contains(searchText.toLowerCase()) ||
                                account.getEmployeeId().toLowerCase().contains(searchText.toLowerCase()) ||
                                account.getRoleId().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Email":
                        searchMatch = account.getEmail().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Tên tài khoản":
                        searchMatch = account.getUsername().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Mã nhân viên":
                        searchMatch = account.getEmployeeId().toLowerCase().contains(searchText.toLowerCase());
                        break;
                    case "Mã quyền":
                        searchMatch = account.getRoleId().toLowerCase().contains(searchText.toLowerCase());
                        break;
                }
            }
    
            if (statusMatch && searchMatch) {
                result.add(account);
            }
        }
        return result;
    }
}