package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.ImportDTO;
import org.example.DTO.ImportDetailDTO;

public class ImportDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public ImportDAO() {
        try {
            conn = UtilsJDBC.getConnectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMaxImportID() {
        String query = "SELECT MAX(MaPN) as MaxMaPN FROM import";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("MaxMaPN");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return null;
    }

    public List<ImportDTO> getAllImportSlips() {
        List<ImportDTO> importSlips = new ArrayList<>();
        String query = "SELECT * FROM import";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ImportDTO importSlip = new ImportDTO(
                        rs.getString("MaPN"),
                        rs.getString("MaNCC"),
                        rs.getString("MaNV"),
                        rs.getDate("NgayNhap"),
                        rs.getTime("GioNhap"),
                        rs.getFloat("TongTien"));
                importSlips.add(importSlip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return importSlips;
    }

    public ImportDTO getImportSlipById(String maPN) {
        String query = "SELECT * FROM import WHERE MaPN = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maPN);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ImportDTO(
                        rs.getString("MaPN"),
                        rs.getString("MaNCC"),
                        rs.getString("MaNV"),
                        rs.getDate("NgayNhap"),
                        rs.getTime("GioNhap"),
                        rs.getFloat("TongTien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ImportDetailDTO> getImportDetails(String maPN) {
        List<ImportDetailDTO> details = new ArrayList<>();
        String query = "SELECT * FROM import_details WHERE MaPN = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, maPN);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ImportDetailDTO detail = new ImportDetailDTO(
                        rs.getString("MaPN"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getFloat("DonGia"));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    public boolean addImportSlip(ImportDTO importSlip) {
        String query = "INSERT INTO import (MaPN, MaNCC, MaNV, NgayNhap, GioNhap, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, importSlip.getMaPN());
            ps.setString(2, importSlip.getMaNCC());
            ps.setString(3, importSlip.getMaNV());
            ps.setDate(4, importSlip.getNgayNhap());
            ps.setTime(5, importSlip.getGioNhap());
            ps.setFloat(6, importSlip.getTongTien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addImportDetail(ImportDetailDTO detail) {
        String query = "INSERT INTO import_details (MaPN, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, detail.getMaPN());
            ps.setString(2, detail.getMaSP());
            ps.setInt(3, detail.getSoLuong());
            ps.setFloat(4, detail.getDonGia());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}