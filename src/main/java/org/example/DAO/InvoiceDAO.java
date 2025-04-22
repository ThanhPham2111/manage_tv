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
                invoice.setMaHoaDon(rs.getString("MaHD"));
                invoice.setmaKhachHang(rs.getString("MaKH"));
                invoice.setMaNhanVien(rs.getString("MaNV"));
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

    public InvoiceDTO getInvoice(String maHoaDon) {
        Connection conn = UtilsJDBC.getConnectDB();
        InvoiceDTO invoice = new InvoiceDTO();
        String query = "SELECT * FROM invoice WHERE MaHD = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                invoice.setMaHoaDon(rs.getString("MaHD"));
                invoice.setmaKhachHang(rs.getString("MaKH"));
                invoice.setMaNhanVien(rs.getString("MaNV"));
                invoice.setNgayLap(rs.getDate("NgayLap"));
                invoice.setGioNhap(rs.getTime("GioNhap"));
                invoice.setTongTien(rs.getDouble("TongTien"));
                invoice.setTenKhachHang(getCustomerName(invoice.getMaKhachHang()));
                invoice.setTenNhanVien(getemployeeName(invoice.getMaNhanVien()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return invoice;
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
            query += "AND MaHD = ?";
        }
        if(maKhachHang != null && !maKhachHang.isEmpty()){
            query += "AND MaKH = ?";
        }
        if(maNhanVien != null && !maNhanVien.isEmpty()){
            query += "AND MaNV = ?";
        }
        if(beginDate != null && endDate != null){
            query += "AND NgayLap BETWEEN ? AND ?";
        }
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            int index = 1;
            if(maHoaDon != null && !maHoaDon.isEmpty()){
                ps.setString(index++, maHoaDon);
            }
            if(maKhachHang != null && !maKhachHang.isEmpty()){
                ps.setString(index++, maKhachHang);
            }
            if(maNhanVien != null && !maNhanVien.isEmpty()){
                ps.setString(index++, maNhanVien);
            }
            if(beginDate != null && endDate != null){
                ps.setString(index++, new SimpleDateFormat("yyyy-MM-dd").format(beginDate));
                ps.setString(index++, new SimpleDateFormat("yyyy-MM-dd").format(endDate));
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                InvoiceDTO invoice = new InvoiceDTO(
                    rs.getString("MaHD"),
                    rs.getString("MaKH"),
                    rs.getString("MaNV"),
                    rs.getDate("NgayLap"),
                    rs.getTime("GioNhap"),
                    rs.getDouble("TongTien")
                );

                invoices.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            UtilsJDBC.closeConnection();
        }

        return invoices;
    }

    public String getCustomerName(String maKhachHang){
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

    public String getemployeeName(String maNhanVien){
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