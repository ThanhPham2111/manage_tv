package org.example.DAO;

import org.example.DTO.EmployeeDTO;
import org.example.ConnectDB.UtilsJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO {
    private ArrayList<EmployeeDTO> dsnv = new ArrayList<>();

    public EmployeeDAO() {
    }

    // Lấy toàn bộ danh sách nhân viên
    public ArrayList<EmployeeDTO> selectAll() {
        Connection con = UtilsJDBC.getConnectDB();
        dsnv.clear(); // Xóa danh sách cũ trước khi thêm mới
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM employee");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maNV = rs.getString("MaNV");
                String tenNV = rs.getString("TenNV");
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                String diaChi = rs.getString("DiaChi");
                String sdt = rs.getString("SDT");
                int trangThai = rs.getInt("TrangThai");

                EmployeeDTO employee = new EmployeeDTO(maNV, tenNV, ngaySinh, diaChi, sdt, trangThai);
                dsnv.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return dsnv;
    }

    // Lấy nhân viên theo mã nhân viên
    public EmployeeDTO getEmployeeByMaNV(String maNV) {
        Connection con = UtilsJDBC.getConnectDB();
        EmployeeDTO employee = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM employee WHERE MaNV = ?");
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tenNV = rs.getString("TenNV");
                java.sql.Date ngaySinh = rs.getDate("NgaySinh");
                String diaChi = rs.getString("DiaChi");
                String sdt = rs.getString("SDT");
                int trangThai = rs.getInt("TrangThai");

                employee = new EmployeeDTO(maNV, tenNV, ngaySinh, diaChi, sdt, trangThai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return employee;
    }

    // Thêm nhân viên mới
    public Boolean add(EmployeeDTO employee) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "INSERT INTO employee (MaNV, TenNV, NgaySinh, DiaChi, SDT, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, employee.getMaNV());
            stmt.setString(2, employee.getTenNV());
            stmt.setDate(3, new java.sql.Date(employee.getNgaySinh().getTime()));
            stmt.setString(4, employee.getDiaChi());
            stmt.setString(5, employee.getSdt());
            stmt.setInt(6, employee.getTrangThai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Cập nhật thông tin nhân viên
    public Boolean update(EmployeeDTO employee) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE employee SET TenNV = ?, NgaySinh = ?, DiaChi = ?, SDT = ?, TrangThai = ? WHERE MaNV = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, employee.getTenNV());
            stmt.setDate(2, new java.sql.Date(employee.getNgaySinh().getTime()));
            stmt.setString(3, employee.getDiaChi());
            stmt.setString(4, employee.getSdt());
            stmt.setInt(5, employee.getTrangThai());
            stmt.setString(6, employee.getMaNV());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Cập nhật trạng thái nhân viên
    public Boolean updateTrangThai(String maNV, int trangThai) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE employee SET TrangThai = ? WHERE MaNV = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, trangThai);
            stmt.setString(2, maNV);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Kiểm tra nhân viên có tồn tại không dựa trên MaNV
    public Boolean getIdEmployee(String maNV) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "SELECT * FROM employee WHERE MaNV = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Trả về true nếu có kết quả, false nếu không
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Main để kiểm tra
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Thêm nhân viên mẫu
        EmployeeDTO employee = new EmployeeDTO("NV001", "Nguyen Van A", new java.sql.Date(System.currentTimeMillis()),
                "123 Hanoi", "0123456789", 1);
        if (dao.add(employee)) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Thêm nhân viên thất bại!");
        }

        // Kiểm tra selectAll
        ArrayList<EmployeeDTO> list = dao.selectAll();
        for (EmployeeDTO emp : list) {
            System.out.println(emp.getTenNV());
        }
    }
}