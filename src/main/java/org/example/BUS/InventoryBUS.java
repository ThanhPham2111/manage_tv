package org.example.BUS;

import org.example.DAO.InventoryDAO;
import org.example.DTO.InventoryDTO;
import java.util.List;

public class InventoryBUS {
    private InventoryDAO inventoryDAO;

    public InventoryBUS() {
        inventoryDAO = new InventoryDAO();
    }

    public List<InventoryDTO> getAllInventory() {
        return inventoryDAO.getAllInventory();
    }

    public InventoryDTO getInventoryByID(String maSP) {
        return inventoryDAO.getInventoryByID(maSP);
    }

    public boolean updateInventory(InventoryDTO inventory) {
        if (inventory == null || inventory.getMaSP() == null || inventory.getMaSP().trim().isEmpty()) {
            return false;
        }
        return inventoryDAO.updateInventory(inventory);
    }

    public boolean insertInventory(InventoryDTO inventory) {
        if (inventory == null || inventory.getMaSP() == null || inventory.getMaSP().trim().isEmpty()) {
            return false;
        }
        // Kiểm tra xem sản phẩm đã tồn tại trong tồn kho chưa
        if (inventoryDAO.getInventoryByID(inventory.getMaSP()) != null) {
            return false;
        }
        return inventoryDAO.insertInventory(inventory);
    }

    public boolean deleteInventory(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return false;
        }
        return inventoryDAO.deleteInventory(maSP);
    }

    // Phương thức kiểm tra số lượng tồn kho
    public boolean checkInventoryQuantity(String maSP, int quantity) {
        InventoryDTO inventory = inventoryDAO.getInventoryByID(maSP);
        if (inventory == null) {
            return false;
        }
        return inventory.getSoLuongTon() >= quantity;
    }

    // Phương thức cập nhật số lượng tồn kho
    public boolean updateInventoryQuantity(String maSP, int quantity) {
        InventoryDTO inventory = inventoryDAO.getInventoryByID(maSP);
        if (inventory == null) {
            return false;
        }
        inventory.setSoLuongTon(inventory.getSoLuongTon() + quantity);
        return inventoryDAO.updateInventory(inventory);
    }
}