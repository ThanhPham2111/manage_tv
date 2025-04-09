package org.example.DAO;

import org.example.DTO.TypeProduct;
import org.example.ConnectDB.UtilsJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TypeProductDAO {
    private ArrayList<TypeProduct> dsLoaiSP = new ArrayList<>();

    public TypeProductDAO() {
    }

    // Lấy toàn bộ danh sách loại sản phẩm
    public ArrayList<TypeProduct> selectAll() {
        Connection con = UtilsJDBC.getConnectDB();
        dsLoaiSP.clear();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM typeproduct");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maLSP = rs.getString("MaLSP");
                String tenLSP = rs.getString("TenLSP");
                String moTa = rs.getString("MoTa");

                TypeProduct typeProduct = new TypeProduct(maLSP, tenLSP, moTa);
                dsLoaiSP.add(typeProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return dsLoaiSP;
    }

    // Thêm loại sản phẩm mới
    public Boolean add(TypeProduct typeProduct) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "INSERT INTO typeproduct(MaLSP, TenLSP, MoTa) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, typeProduct.getMaLSP());
            stmt.setString(2, typeProduct.getTenLSP());
            stmt.setString(3, typeProduct.getMoTa());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Cập nhật thông tin loại sản phẩm
    public Boolean update(TypeProduct typeProduct) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE typeproduct SET TenLSP = ?, MoTa = ? WHERE MaLSP = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, typeProduct.getTenLSP());
            stmt.setString(2, typeProduct.getMoTa());
            stmt.setString(3, typeProduct.getMaLSP());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Kiểm tra loại sản phẩm có tồn tại không dựa trên MaLSP
    public Boolean getIdTypeProduct(String maLSP) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "SELECT * FROM typeproduct WHERE MaLSP = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maLSP);
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
        TypeProductDAO dao = new TypeProductDAO();

        // Thêm loại sản phẩm mẫu
        TypeProduct typeProduct = new TypeProduct("LSP001", "Loại sản phẩm 1", "Mô tả loại 1");
        if (dao.add(typeProduct)) {
            System.out.println("Thêm loại sản phẩm thành công!");
        } else {
            System.out.println("Thêm loại sản phẩm thất bại!");
        }

        // Kiểm tra selectAll
        ArrayList<TypeProduct> list = dao.selectAll();
        for (TypeProduct tp : list) {
            System.out.println(tp.getTenLSP());
        }
    }
}