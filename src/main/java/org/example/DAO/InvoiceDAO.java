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
import org.example.DTO.InvoiceItemDTO;

public class InvoiceDAO {
    public Boolean insertInvoice(InvoiceDTO invoice){
        Connection conn = UtilsJDBC.getConnectDB();
        String insertInvoiceQuery = "INSERT INTO invoice(MaHD, MaNV, MaKH, NgayLap, GioNhap, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
        String insertInvoiceItemQuery = "INSERT INTO invoice_details(MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement psInsertInvoice = conn.prepareStatement(insertInvoiceQuery);
            PreparedStatement psInsertInvoiceItem = conn.prepareStatement(insertInvoiceItemQuery);
            psInsertInvoice.setString(1, invoice.getMaHoaDon());
            psInsertInvoice.setString(2, invoice.getMaNhanVien());
            psInsertInvoice.setString(3, invoice.getMaKhachHang());
            psInsertInvoice.setDate(4, invoice.getNgayLap());
            psInsertInvoice.setTime(5, invoice.getGioNhap());
            psInsertInvoice.setDouble(6, invoice.getTongTien());
            conn.setAutoCommit(false);
            
            int rowsCnt1 = psInsertInvoice.executeUpdate();
            if(rowsCnt1 <= 0){
                conn.rollback();
                System.out.println("❌ Thêm thông tin hoá đơn không thành công!");
                return false;
            }

            ArrayList<InvoiceItemDTO> items = invoice.getInvoiceItems();
            for(InvoiceItemDTO item : items){
                psInsertInvoiceItem.setString(1, invoice.getMaHoaDon());
                psInsertInvoiceItem.setString(2, item.getMaSanPham());
                psInsertInvoiceItem.setLong(3, item.getSoLuong());
                psInsertInvoiceItem.setDouble(4, item.getDonGia());
                psInsertInvoiceItem.addBatch();
            }

            int rowsCnt2[] = psInsertInvoiceItem.executeBatch();
            for(int cnt : rowsCnt2){
                if(cnt <= 0){
                    conn.rollback();
                    System.out.println("❌ Thêm chi tiết hoá đơn không thành công!");
                    return false;
                }
            }

            conn.commit();
            System.out.println("✅ Thêm hoá đơn thành công!");
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("❌ Rollback không thành công!");
                ex.printStackTrace();
                return false;
            }
            System.out.println("❌ Thêm hoá đơn không thành công");
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

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

    public InvoiceDTO getInvoiceByID(String maHoaDon) {
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
                invoice.setTenKhachHang(getCustomerNameByID(invoice.getMaKhachHang()));
                invoice.setTenNhanVien(getemployeeNameByID(invoice.getMaNhanVien()));
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

    public String getMaxInvoiceID(){
        Connection conn = UtilsJDBC.getConnectDB();
        String query = "SELECT MAX(MaHD) AS MaxID FROM invoice";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            String maxInvoiceID = "";
            if(rs.next()){
                maxInvoiceID = rs.getString("MaxID");
            }

            return maxInvoiceID;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    public String getemployeeNameByID(String maNhanVien){
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