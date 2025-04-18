package BUS;

import DAO.AccountDAO;
import DTO.AccountDTO;
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
        List<AccountDTO> result = accountDAO.getAllAccounts();
        
        if (!status.equals("Tất cả")) {
            result.retainAll(accountDAO.filterByStatus(status));
        }
        
        if (!searchText.isEmpty()) {
            result.retainAll(accountDAO.searchAccounts(searchType, searchText));
        }
        
        return result;
    }
}