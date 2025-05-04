package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.ImportDetailDTO;

public class ImportDetailDAO {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public List<ImportDetailDTO> getDetailsByImportSlipId(String maPhieuNhap) {
        List<ImportDetailDTO> details = new ArrayList<>();
        String sql = "SELECT * FROM import_details WHERE MaPN=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                ImportDetailDTO detail = new ImportDetailDTO();
                detail.setMaPN(rs.getString("MaPN"));
                detail.setMaSP(rs.getString("MaSP"));
                detail.setSoLuong(rs.getInt("SoLuong"));
                detail.setDonGia(rs.getFloat("DonGia"));
                detail.setThanhTien(rs.getFloat("ThanhTien"));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return details;
    }

    public boolean addImportSlipDetail(ImportDetailDTO detail) {
        String sql = "INSERT INTO import_details (MaPN, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, detail.getMaPN());
            pst.setString(2, detail.getMaSP());
            pst.setInt(3, detail.getSoLuong());
            pst.setFloat(4, detail.getDonGia());
            pst.setFloat(5, detail.getThanhTien());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean updateImportSlipDetail(ImportDetailDTO detail) {
        String sql = "UPDATE import_details SET SoLuong=?, DonGia=?, ThanhTien=? WHERE MaPN=? AND MaSP=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, detail.getSoLuong());
            pst.setFloat(2, detail.getDonGia());
            pst.setFloat(3, detail.getThanhTien());
            pst.setString(4, detail.getMaPN());
            pst.setString(5, detail.getMaSP());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean deleteImportSlipDetail(String maPhieuNhap, String maSP) {
        String sql = "DELETE FROM import_details WHERE MaPN=? AND MaSP=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            pst.setString(2, maSP);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean deleteAllDetailsForImportSlip(String maPhieuNhap) {
        String sql = "DELETE FROM import_details WHERE MaPN=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }
} 