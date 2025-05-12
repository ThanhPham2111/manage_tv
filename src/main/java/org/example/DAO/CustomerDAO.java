package org.example.DAO;

import org.example.DTO.CustomerDTO;
import org.example.ConnectDB.UtilsJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO {
    private ArrayList<CustomerDTO> dskh = new ArrayList<>();

    public CustomerDAO() {
    }

    public ArrayList<CustomerDTO> selectAll() {
        Connection con = UtilsJDBC.getConnectDB();
        dskh.clear(); // Xóa danh sách cũ trước khi thêm mới
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM customer");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String makh = rs.getString("MaKH");
                String tenkh = rs.getString("TenKH");
                String diachi = rs.getString("DiaChi");
                String sdt = rs.getString("SDT");
                int trangthai = rs.getInt("TrangThai");
                CustomerDTO kh = new CustomerDTO(makh, tenkh, diachi, sdt, trangthai);
                dskh.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection(); // Đóng kết nối sau khi hoàn thành
        }
        return dskh;
    }
// phuong thuc them
    public Boolean add(CustomerDTO customer) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "INSERT INTO customer(MaKH, TenKH, DiaChi, SDT, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, customer.getMaKH());
            stmt.setString(2, customer.getTenKH());
            stmt.setString(3, customer.getDiaChi());
            stmt.setString(4, customer.getSdt());
            stmt.setInt(5, customer.getTrangThai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Phương thức update với tham số riêng lẻ
    public Boolean update(String maKH, String tenKH, String diaChi, String sdt, int trangThai) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE customer SET TenKH = ?, DiaChi = ?, SDT = ?, TrangThai = ? WHERE MaKH = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tenKH);
            stmt.setString(2, diaChi);
            stmt.setString(3, sdt);         
            stmt.setInt(4, trangThai);
            stmt.setString(5, maKH);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public Boolean updateTrangThai(String maKH, int trangThai) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE customer SET TrangThai = ? WHERE MaKH = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, trangThai);
            stmt.setString(2, maKH);

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
        CustomerDAO dao = new CustomerDAO();
        CustomerDTO kh = new CustomerDTO("KH_TEST1", "Test Customer", "Test Address", "123456789", 0);
        if (dao.add(kh)) {
            System.out.println("Thêm khách hàng thành công!");
        } else {
            System.out.println("Thêm khách hàng thất bại!");
        }

        // Kiểm tra selectAll
        ArrayList<CustomerDTO> list = dao.selectAll();
        for (CustomerDTO c : list) {
            System.out.println(c.getTenKH());
        }
    }
}