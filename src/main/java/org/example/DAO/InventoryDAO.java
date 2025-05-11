package org.example.DAO;

import org.example.DTO.InventoryDTO;
import org.example.ConnectDB.UtilsJDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public InventoryDAO() {
        try {
            conn = UtilsJDBC.getConnectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<InventoryDTO> getAllInventory() {
        List<InventoryDTO> list = new ArrayList<>();
        String query = "SELECT p.*, tk.SoLuongTon, tk.NgayNhap, tk.NgayXuat, tk.GhiChu " +
                "FROM Product p " +
                "JOIN tonkho tk ON p.MaSP = tk.MaSP";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new InventoryDTO(
                        rs.getString("MaSP"),
                        rs.getString("MaLSP"),
                        rs.getString("TenSP"),
                        rs.getFloat("DonGia"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("TrangThai"),
                        rs.getInt("SoLuongTon"),
                        rs.getString("NgayNhap"),
                        rs.getString("NgayXuat"),
                        rs.getString("GhiChu")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return list;
    }

    public InventoryDTO getInventoryByID(String maSP) {
        String query = "SELECT p.*, tk.SoLuongTon, tk.NgayNhap, tk.NgayXuat, tk.GhiChu " +
                "FROM Product p " +
                "JOIN tonkho tk ON p.MaSP = tk.MaSP " +
                "WHERE p.MaSP = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, maSP);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new InventoryDTO(
                        rs.getString("MaSP"),
                        rs.getString("MaLSP"),
                        rs.getString("TenSP"),
                        rs.getFloat("DonGia"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("TrangThai"),
                        rs.getInt("SoLuongTon"),
                        rs.getString("NgayNhap"),
                        rs.getString("NgayXuat"),
                        rs.getString("GhiChu"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return null;
    }

    public boolean updateInventory(InventoryDTO inventory) {
        String query = "UPDATE tonkho SET SoLuongTon = ?, NgayNhap = ?, NgayXuat = ?, GhiChu = ? WHERE MaSP = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, inventory.getSoLuongTon());
            ps.setString(2, inventory.getNgayNhap());
            ps.setString(3, inventory.getNgayXuat());
            ps.setString(4, inventory.getGhiChu());
            ps.setString(5, inventory.getMaSP());
            boolean result = ps.executeUpdate() > 0;
            UtilsJDBC.closeConnection();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            UtilsJDBC.closeConnection();
            return false;
        }
    }

    public boolean insertInventory(InventoryDTO inventory) {
        String query = "INSERT INTO tonkho (MaSP, SoLuongTon, NgayNhap, NgayXuat, GhiChu) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, inventory.getMaSP());
            ps.setInt(2, inventory.getSoLuongTon());
            ps.setString(3, inventory.getNgayNhap());
            ps.setString(4, inventory.getNgayXuat());
            ps.setString(5, inventory.getGhiChu());
            boolean result = ps.executeUpdate() > 0;
            UtilsJDBC.closeConnection();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            UtilsJDBC.closeConnection();
            return false;
        }
    }

    public boolean deleteInventory(String maSP) {
        String query = "DELETE FROM tonkho WHERE MaSP = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, maSP);
            boolean result = ps.executeUpdate() > 0;
            UtilsJDBC.closeConnection();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            UtilsJDBC.closeConnection();
            return false;
        }
    }
}
