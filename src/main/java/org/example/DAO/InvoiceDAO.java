package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public String getMaxInvoiceId() {
        Connection conn = UtilsJDBC.getConnectDB();
        String maxMaHoaDon = "";
        String query = "SELECT MAX(MaHD) AS MaxMaHD FROM invoice";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maxMaHoaDon = rs.getString("MaxMaHD");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return maxMaHoaDon;
    }

    public InvoiceDTO getInvoice(String maHoaDon) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return invoice;
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
            ps.setFloat(4, detail.getDonGia());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }
}