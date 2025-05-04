package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.TopProductsDTO;
import org.example.UtilsDate.UtilsDateFormat;

public class TopProductsDAO {
    public ArrayList<TopProductsDTO> getTopProducts(int year, int limit){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<TopProductsDTO> topProducts = new ArrayList<>();

        String query = "";
        query+= "SELECT CTHD.MaSP, SP.TenSP, SUM(CTHD.SoLuong) AS TotalQuantity ";
        query+= "FROM invoice_details AS CTHD ";
        query+= "INNER JOIN invoice AS HD ";
        query+= "ON CTHD.MaHD = HD.MaHD ";
        query+= "INNER JOIN product AS SP ";
        query+= "ON CTHD.MaSP = SP.MaSP ";
        query+= "WHERE YEAR(HD.NgayLap)=? ";
        query+= "GROUP BY CTHD.MaSP ";
        query+= "ORDER BY TotalQuantity DESC ";
        query+= "LIMIT ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, year);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                topProducts.add(new TopProductsDTO(rs.getString("MaSP"),
                                                   rs.getString("TenSP"),
                                                   rs.getInt("TotalQuantity")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        
        return topProducts;
    }

    public ArrayList<TopProductsDTO> getTopProducts(int year, int month, int limit){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<TopProductsDTO> topProducts = new ArrayList<>();

        String query = "";
        query+= "SELECT CTHD.MaSP, SP.TenSP, SUM(CTHD.SoLuong) AS TotalQuantity ";
        query+= "FROM invoice_details AS CTHD ";
        query+= "INNER JOIN invoice AS HD ";
        query+= "ON HD.MaHD = CTHD.MaHD ";
        query+= "INNER JOIN product AS SP ";
        query+= "ON CTHD.MaSP = SP.MaSP ";
        query+= "WHERE YEAR(HD.NgayLap)=? AND MONTH(HD.NgayLap)=? ";
        query+= "GROUP BY CTHD.MaSP ";
        query+= "ORDER BY TotalQuantity DESC ";
        query+= "LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, year);
            ps.setInt(2, month);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                topProducts.add(new TopProductsDTO(rs.getString("MaSP"),
                                                   rs.getString("TenSP"),
                                                   rs.getInt("TotalQuantity")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        
        return topProducts;
    }

    public ArrayList<TopProductsDTO> getTopProductsByDate(Date fromDate, Date toDate, int limit){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<TopProductsDTO> topProducts = new ArrayList<>();

        String query = "";
        query+= "SELECT CTHD.MaSP, SP.TenSP, SUM(CTHD.SoLuong) AS TotalQuantity ";
        query+= "FROM invoice_details AS CTHD ";
        query+= "INNER JOIN invoice AS HD ";
        query+= "ON CTHD.MaHD = HD.MaHD ";
        query+= "INNER JOIN product AS SP ";
        query+= "ON CTHD.MaSP = SP.MaSP ";
        query+= "WHERE HD.NgayLap BETWEEN ? AND ? ";
        query+= "GROUP BY CTHD.MaSP ";
        query+= "ORDER BY TotalQuantity DESC ";
        query+= "LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, UtilsDateFormat.formatCustom("yyyy-MM-dd", fromDate));
            ps.setString(2, UtilsDateFormat.formatCustom("yyyy-MM-dd", toDate));
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                topProducts.add(new TopProductsDTO(rs.getString("MaSP"),
                                                   rs.getString("TenSP"),
                                                   rs.getInt("TotalQuantity")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        
        return topProducts;
    }
}
