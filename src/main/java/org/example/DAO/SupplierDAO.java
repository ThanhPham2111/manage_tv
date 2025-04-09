package org.example.DAO;

import org.example.DTO.SupplierDTO;
import org.example.ConnectDB.UtilsJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAO {
    private ArrayList<SupplierDTO> dsncc = new ArrayList<>();

    public SupplierDAO() {
    }

    public ArrayList<SupplierDTO> selectAll() {
        Connection con = UtilsJDBC.getConnectDB();
        dsncc.clear(); // Xóa danh sách cũ trước khi thêm mới
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM supplier");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String mancc = rs.getString("MaNCC");
                String tenncc = rs.getString("TenNCC");
                String diachi = rs.getString("DiaChi");
                String sdt = rs.getString("SDT");
                String fax = rs.getString("Fax");
                int trangthai = rs.getInt("TrangThai");
                SupplierDTO ncc = new SupplierDTO(mancc, tenncc, diachi, sdt, fax, trangthai);
                dsncc.add(ncc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection(); // Đóng kết nối sau khi hoàn thành
        }
        return dsncc;
    }

    public Boolean add(SupplierDTO supplier) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "INSERT INTO supplier(MaNCC, TenNCC, DiaChi, SDT, Fax, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, supplier.getMaNCC());
            stmt.setString(2, supplier.getTenNCC());
            stmt.setString(3, supplier.getDiaChi());
            stmt.setString(4, supplier.getSDT());
            stmt.setString(5, supplier.getFax());
            stmt.setInt(6, supplier.getTrangThai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public Boolean update(SupplierDTO supplier) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE supplier SET TenNCC = ?, DiaChi = ?, SDT = ?, Fax = ?, TrangThai = ? WHERE MaNCC = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, supplier.getTenNCC());
            stmt.setString(2, supplier.getDiaChi());
            stmt.setString(3, supplier.getSDT());
            stmt.setString(4, supplier.getFax());
            stmt.setInt(5, supplier.getTrangThai());
            stmt.setString(6, supplier.getMaNCC());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public Boolean updateTrangThai(String maNCC, int trangThai) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE supplier SET TrangThai = ? WHERE MaNCC = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, trangThai);
            stmt.setString(2, maNCC);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public static void main(String[] args) {
        SupplierDAO dao = new SupplierDAO();
        SupplierDTO sup = new SupplierDTO("TEST1", "Test Supplier", "Test Address", "123456789", "987654321", 1);
        if (dao.add(sup)) {
            System.out.println("Thêm nhà cung cấp thành công!");
        } else {
            System.out.println("Thêm nhà cung cấp thất bại!");
        }

        // Kiểm tra selectAll
        ArrayList<SupplierDTO> list = dao.selectAll();
        for (SupplierDTO s : list) {
            System.out.println(s.getTenNCC());
        }
    }
}