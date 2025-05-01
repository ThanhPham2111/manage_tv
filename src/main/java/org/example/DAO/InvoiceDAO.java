package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

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
        String query = "SELECT MAX(MaHD) AS MaxInvoiceID FROM invoice";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxInvoiceID = rs.getString("MaxInvoiceID");
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
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

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

    public boolean addInvoice(InvoiceDTO invoice) {
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "INSERT INTO invoice (MaHD, MaKH, MaNV, NgayLap, GioNhap, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, invoice.getMaHD());
            ps.setString(2, invoice.getMaKH());
            ps.setString(3, invoice.getMaNV());
            ps.setDate(4, invoice.getNgayLap());
            ps.setTime(5, invoice.getGioNhap());
            ps.setDouble(6, invoice.getTongTien());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean addInvoiceDetail(InvoiceDetailDTO detail) {
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "INSERT INTO invoicedetail (MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, detail.getMaHD());
            ps.setString(2, detail.getMaSP());
            ps.setInt(3, detail.getSoLuong());
            ps.setDouble(4, detail.getDonGia());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
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