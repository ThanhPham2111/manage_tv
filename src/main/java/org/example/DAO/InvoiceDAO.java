package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.InvoiceDTO;
import org.example.DTO.InvoiceDetailDTO;

public class InvoiceDAO {

    public ArrayList<InvoiceDTO> getAllInvoices() {
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<InvoiceDTO> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoice";

        try (
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setMaHD(rs.getString("MaHD"));
                invoice.setMaKH(rs.getString("MaKH"));
                invoice.setMaNV(rs.getString("MaNV"));
                invoice.setNgayLap(rs.getDate("NgayLap"));
                invoice.setGioNhap(rs.getTime("GioNhap"));
                invoice.setTongTien(rs.getDouble("TongTien"));

                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return invoices;
    }

    public String getMaxInvoiceID() {
        Connection conn = UtilsJDBC.getConnectDB();
        String maxInvoiceID = "";
        String query = "SELECT MAX(MaHD) AS MaxMaHD FROM invoice";

        try (
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ) {
            if (rs.next()) {
                maxInvoiceID = rs.getString("MaxMaHD");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return maxInvoiceID;
    }

    public InvoiceDTO getInvoiceByID(String maHoaDon) {
        Connection conn = UtilsJDBC.getConnectDB();
        InvoiceDTO invoice = new InvoiceDTO();
        String query = "SELECT * FROM invoice WHERE MaHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, maHoaDon);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    invoice.setMaHD(rs.getString("MaHD"));
                    invoice.setMaKH(rs.getString("MaKH"));
                    invoice.setMaNV(rs.getString("MaNV"));
                    invoice.setNgayLap(rs.getDate("NgayLap"));
                    invoice.setGioNhap(rs.getTime("GioNhap"));
                    invoice.setTongTien(rs.getDouble("TongTien"));
                    invoice.setTenKH(getCustomerNameByID(invoice.getMaKH()));
                    invoice.setTenNV(getEmployeeNameByID(invoice.getMaNV()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return invoice;
    }

    public ArrayList<InvoiceDetailDTO> getInvoiceDetailsByID(String maHoaDon){
        ArrayList<InvoiceDetailDTO> invoiceDetails = new ArrayList<>();
        Connection conn = UtilsJDBC.getConnectDB();

        String query = "";
        query += "SELECT MaHD, CTHD.MaSP, TenSP, CTHD.SoLuong, CTHD.DonGia, (CTHD.SoLuong * CTHD.DonGia) AS ThanhTien ";
        query += "FROM invoice_details AS CTHD ";
        query += "INNER JOIN product AS SP ";
        query += "ON CTHD.MaSP = SP.MaSP ";
        query += "WHERE MaHD = ? ";
        query += "ORDER BY CTHD.MaSP ASC";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                InvoiceDetailDTO invoiceDetail = new InvoiceDetailDTO(
                    rs.getString("MaHD"),
                    rs.getString("MaSP"),
                    rs.getInt("SoLuong"),
                    rs.getFloat("DonGia")
                );
                invoiceDetail.setTenSP(rs.getString("TenSP"));
                invoiceDetail.setThanhTien(rs.getDouble("ThanhTien"));

                invoiceDetails.add(invoiceDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoiceDetails;
    }

    public ArrayList<InvoiceDTO> getFilteredInvoices(String maHoaDon,
                                                     String maKhachHang,
                                                     String maNhanVien,
                                                     Date beginDate,
                                                     Date endDate){
        ArrayList<InvoiceDTO> invoices = new ArrayList<>();
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "SELECT * FROM invoice WHERE 1 ";
        
        if(maHoaDon != null && !maHoaDon.isEmpty()){
            query += "AND MaHD LIKE ? ";
        }
        if(maKhachHang != null && !maKhachHang.isEmpty()){
            query += "AND MaKH LIKE ? ";
        }
        if(maNhanVien != null && !maNhanVien.isEmpty()){
            query += "AND MaNV LIKE ? ";
        }
        if(beginDate != null && endDate != null){
            query += "AND NgayLap BETWEEN ? AND ?";
        }
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int index = 1;
            if(maHoaDon != null && !maHoaDon.isEmpty()){
                ps.setString(index++, maHoaDon + "%");
            }
            if(maKhachHang != null && !maKhachHang.isEmpty()){
                ps.setString(index++, maKhachHang + "%");
            }
            if(maNhanVien != null && !maNhanVien.isEmpty()){
                ps.setString(index++, maNhanVien + "%");
            }
            if(beginDate != null && endDate != null){
                ps.setString(index++, new SimpleDateFormat("yyyy-MM-dd").format(beginDate));
                ps.setString(index++, new SimpleDateFormat("yyyy-MM-dd").format(endDate));
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                InvoiceDTO invoice = new InvoiceDTO();
                invoice.setMaHD(rs.getString("MaHD"));
                invoice.setMaKH(rs.getString("MaKH"));
                invoice.setMaNV(rs.getString("MaNV"));
                invoice.setNgayLap(rs.getDate("NgayLap"));
                invoice.setGioNhap(rs.getTime("GioNhap"));
                invoice.setTongTien(rs.getDouble("TongTien"));

                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            UtilsJDBC.closeConnection();
        }

        return invoices;
    }

    public void addInvoice(InvoiceDTO invoice) throws SQLException{
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "INSERT INTO invoice (MaHD, MaKH, MaNV, NgayLap, GioNhap, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, invoice.getMaHD());
            ps.setString(2, invoice.getMaKH());
            ps.setString(3, invoice.getMaNV());
            ps.setDate(4, invoice.getNgayLap());
            ps.setTime(5, invoice.getGioNhap());
            ps.setDouble(6, invoice.getTongTien());
            
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 0) throw new SQLException("Thêm hoá đơn không thành công");

            addInvoiceDetails(conn, invoice.getInvoiceDetails());

            conn.commit();
        } catch (SQLException e) {
            if(conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }   
            }
            e.printStackTrace();
            if(e instanceof SQLIntegrityConstraintViolationException){
                throw new SQLException("Thêm hoá đơn không thành công");
            } else{
                throw e;
            }
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public void addInvoiceDetails(Connection conn, ArrayList<InvoiceDetailDTO> invoiceDetails) throws SQLException{
        String insertDetailQuery = "INSERT INTO invoice_details (MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        String updateProductQuantityQuery = "UPDATE product SET SoLuong = SoLuong - ? ";
              updateProductQuantityQuery += "WHERE MaSP = ? AND SoLuong >= ?";

        try (PreparedStatement psInsertDetail = conn.prepareStatement(insertDetailQuery);
             PreparedStatement psUpdateProductQuantity = conn.prepareStatement(updateProductQuantityQuery)
            ) {
            for(InvoiceDetailDTO invoiceDetail : invoiceDetails){
                psInsertDetail.setString(1, invoiceDetail.getMaHD());
                psInsertDetail.setString(2, invoiceDetail.getMaSP());
                psInsertDetail.setInt(3, invoiceDetail.getSoLuong());
                psInsertDetail.setDouble(4, invoiceDetail.getDonGia());
                psInsertDetail.addBatch();

                psUpdateProductQuantity.setInt(1, invoiceDetail.getSoLuong());
                psUpdateProductQuantity.setString(2, invoiceDetail.getMaSP());
                psUpdateProductQuantity.setInt(3, invoiceDetail.getSoLuong());
                psUpdateProductQuantity.addBatch();
            }

            int insertRowsAffected[] = psInsertDetail.executeBatch();
            int updateRowsAffected[] = psUpdateProductQuantity.executeBatch();

            for(int r : insertRowsAffected){
                if(r == 0) throw new SQLException("Thêm một hoặc nhiều chi tiết hoá đơn thất bại");
            }

            for(int r: updateRowsAffected){
                if(r == 0) throw new SQLException("Không đủ số lượng sản phẩm hoặc cập nhật số lượng sản phẩm thất bại");
            }
        }
    }

    public String getCustomerNameByID(String maKhachHang){
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "SELECT TenKH FROM customer WHERE MaKH = ?";
        String customerName = "";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maKhachHang);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                customerName = rs.getString("TenKH");
            } else {
                System.err.println("Customer not found!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return customerName;
    }

    public String getEmployeeNameByID(String maNhanVien){
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "SELECT TenNV FROM employee WHERE MaNV = ?";
        String employeeName = "";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                employeeName = rs.getString("TenNV");
            } else {
                System.err.println("Employee not found!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return employeeName;
    }
}
