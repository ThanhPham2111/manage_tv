package org.example.DAO;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

            rs.next();
            invoice.setMaHoaDon(rs.getString("MaHD"));
            invoice.setmaKhachHang(rs.getString("MaKH"));
            invoice.setMaNhanVien(rs.getString("MaNV"));
            invoice.setNgayLap(rs.getDate("NgayLap"));
            invoice.setGioNhap(rs.getTime("GioNhap"));
            invoice.setTongTien(rs.getDouble("TongTien"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return invoice;
    }
}