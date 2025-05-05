package org.example.BUS;

import java.util.ArrayList;
import java.util.Date;

import org.example.DAO.TopProductsDAO;
import org.example.DTO.TopProductsDTO;
import org.example.UtilsDate.UtilsDateCustom;

public class TopProductsBUS {
    private TopProductsDAO topProductsDAO;

    public TopProductsBUS(){
        topProductsDAO = new TopProductsDAO();
    }

    public short validateDate(Date fromDate, Date toDate){
        //0: Không có lỗi
        //1: Lỗi trống ngày bắt đầu hoặc ngày kết thúc
        //2: Lỗi ngày bắt đầu lớn hơn ngày kết thúc
        if(fromDate == null || toDate == null) return 1;
        if(fromDate.getTime() > toDate.getTime()) return 2;
        return 0;
    }
    
    public void setFromDateToDateToLastDays(Date refDates[], int days){
        Date today = new Date();
        days = days - 1;
        days = Math.max(days, 0);
        refDates[0] = UtilsDateCustom.addDays(today, 0 - days);
        refDates[1] = today;
    }

    public ArrayList<TopProductsDTO> getTopProducts(String selectedViewBy, int selectedMonth, int selectedYear, Date fromDate, Date toDate,  int selectedLimit){
        ArrayList<TopProductsDTO> topProducts = new ArrayList<>();
        if("Years".equalsIgnoreCase(selectedViewBy)){
            topProducts = topProductsDAO.getTopProducts(selectedYear, selectedLimit);
        } else
        if("Months".equalsIgnoreCase(selectedViewBy)){
            topProducts = topProductsDAO.getTopProducts(selectedYear, selectedMonth, selectedLimit);
        } else
        if("7Days".equalsIgnoreCase(selectedViewBy)){
            topProducts = topProductsDAO.getTopProductsByDate(fromDate, toDate, selectedLimit);
        } else
        if("31Days".equalsIgnoreCase(selectedViewBy)){
            topProducts = topProductsDAO.getTopProductsByDate(fromDate, toDate, selectedLimit);
        }

        return topProducts;
    }

    public ArrayList<TopProductsDTO> getTopProductsByDate(Date fromDate, Date toDate, int selectedLimit){
        return topProductsDAO.getTopProductsByDate(fromDate, toDate, selectedLimit);
    }
}
