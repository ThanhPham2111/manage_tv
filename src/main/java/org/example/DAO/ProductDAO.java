package org.example.DAO;

import org.example.DTO.ProductDTO;
import org.example.ConnectDB.UtilsJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    private ArrayList<ProductDTO> dssp = new ArrayList<>();

    public ProductDAO() {
    }

    // Lấy toàn bộ danh sách sản phẩm
    public ArrayList<ProductDTO> selectAll() {
        Connection con = UtilsJDBC.getConnectDB();
        dssp.clear(); // Xóa danh sách cũ trước khi thêm mới
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM product");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maSP = rs.getString("MaSP");
                String maLSP = rs.getString("MaLSP");
                String tenSP = rs.getString("TenSP");
                float donGia = rs.getFloat("DonGia");
                int soLuong = rs.getInt("SoLuong");
                String hinhAnh = rs.getString("HinhAnh");
                int trangThai = rs.getInt("TrangThai");

                ProductDTO product = new ProductDTO(maSP, maLSP, tenSP, donGia, soLuong, hinhAnh, trangThai);
                dssp.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection(); // Đóng kết nối sau khi hoàn thành
        }
        return dssp;
    }

    // Thêm sản phẩm mới
    public Boolean add(ProductDTO product) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "INSERT INTO product(MaSP, MaLSP, TenSP, DonGia, SoLuong, HinhAnh, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, product.getMaSP());
            stmt.setString(2, product.getMaLSP());
            stmt.setString(3, product.getTenSP());
            stmt.setFloat(4, product.getDonGia());
            stmt.setInt(5, product.getSoLuong());
            stmt.setString(6, product.getHinhAnh());
            stmt.setInt(7, product.getTrangthai());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }








    // Cập nhật thông tin sản phẩm
    public Boolean update(ProductDTO product) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE product SET MaLSP = ?, TenSP = ?, DonGia = ?, SoLuong = ?, HinhAnh = ?, TrangThai = ? WHERE MaSP = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, product.getMaLSP());
            stmt.setString(2, product.getTenSP());
            stmt.setFloat(3, product.getDonGia());
            stmt.setInt(4, product.getSoLuong());
            stmt.setString(5, product.getHinhAnh());
            stmt.setInt(6, product.getTrangthai());
            stmt.setString(7, product.getMaSP());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Cập nhật trạng thái sản phẩm
    public Boolean updateTrangThai(String maSP, int trangThai) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE product SET TrangThai = ? WHERE MaSP = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, trangThai);
            stmt.setString(2, maSP);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Cập nhật số lượng sản phẩm
    public Boolean updateSoLuong(String maSP, int soLuong) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "UPDATE product SET SoLuong = ? WHERE MaSP = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, soLuong);
            stmt.setString(2, maSP);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    // Kiểm tra sản phẩm có tồn tại không dựa trên MaSP
    public Boolean getIdProduct(String maSP) {
        Connection con = UtilsJDBC.getConnectDB();
        String sql = "SELECT * FROM product WHERE MaSP = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maSP);
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
        ProductDAO dao = new ProductDAO();

        // Thêm sản phẩm mẫu
        ProductDTO product = new ProductDTO("SP001", "LSP001", "Sản phẩm test", 10000.5f, 50, "image.jpg", 1);
        if (dao.add(product)) {
            System.out.println("Thêm sản phẩm thành công!");
        } else {
            System.out.println("Thêm sản phẩm thất bại!");
        }

        // Kiểm tra selectAll
        ArrayList<ProductDTO> list = dao.selectAll();
        for (ProductDTO p : list) {
            System.out.println(p.getTenSP());
        }
    }
}