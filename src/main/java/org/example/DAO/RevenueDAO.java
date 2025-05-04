package org.example.DAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.RevenueDTO;
import org.example.UtilsDate.UtilsDateFormat;

public class RevenueDAO {
    public ArrayList<RevenueDTO> getRevenues(){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<RevenueDTO> revenues = new ArrayList<>();

        String query = "SELECT STR_TO_DATE(DATE_FORMAT(NgayLap, \'01-01-%Y\'), \'%d-%m-%Y\') AS Years, ";
              query += "SUM(TongTien) AS Total ";
              query += "FROM invoice WHERE 1 GROUP BY Years ORDER BY Years";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                revenues.add(new RevenueDTO(rs.getDate("Years"), rs.getDouble("Total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }

        return revenues;
    }

    public ArrayList<RevenueDTO> getRevenues(int year){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<RevenueDTO> revenues = new ArrayList<>();
        
        String query = "";
        query = "SELECT STR_TO_DATE(DATE_FORMAT(NgayLap, \'01-%m-%Y\'), \'%d-%m-%Y\') AS MonthsInYear, ";
        query+= "SUM(TongTien) AS Total ";
        query+= "FROM invoice WHERE YEAR(NgayLap)=? ";
        query+= "GROUP BY MonthsInYear ";
        query+= "ORDER BY MonthsInYear";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                revenues.add(new RevenueDTO(rs.getDate("MonthsInYear"), rs.getDouble("Total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            UtilsJDBC.closeConnection();
        }

        return revenues;
    }

    public ArrayList<RevenueDTO> getRevenues(int month, int year){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<RevenueDTO> revenues = new ArrayList<>();

        String query = "";
        query = "SELECT STR_TO_DATE(DATE_FORMAT(NgayLap, \'%d-%m-%Y\'), \'%d-%m-%Y\') AS DaysInMonthYear, ";
        query+= "SUM(TongTien) AS Total ";
        query+= "FROM invoice WHERE MONTH(NgayLap)=? AND YEAR(NgayLap)=? ";
        query+= "GROUP BY DaysInMonthYear ";
        query+= "ORDER BY DaysInMonthYear";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                revenues.add(new RevenueDTO(rs.getDate("DaysInMonthYear"), rs.getDouble("Total")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            UtilsJDBC.closeConnection();
        }

        return revenues;
    }

    public ArrayList<RevenueDTO> getRevenues(Date fromDate, Date toDate){
        Connection conn = UtilsJDBC.getConnectDB();
        ArrayList<RevenueDTO> revenues = new ArrayList<>();

        String query = "";
        query = "SELECT STR_TO_DATE(DATE_FORMAT(NgayLap, \'%d-%m-%Y\'), \'%d-%m-%Y\') AS Dates, ";
        query+= "SUM(TongTien) AS Total ";
        query+= "FROM invoice WHERE NgayLap BETWEEN ? AND ? ";
        query+= "GROUP BY Dates ";
        query+= "ORDER BY Dates";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, UtilsDateFormat.formatCustom("yyyy-MM-dd", fromDate));
            ps.setString(2, UtilsDateFormat.formatCustom("yyyy-MM-dd", toDate));
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                revenues.add(new RevenueDTO(rs.getDate("Dates"), rs.getDouble("Total")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            UtilsJDBC.closeConnection();
        }

        return revenues;
    }
}
