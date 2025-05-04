package org.example.BUS;

import java.util.Date;
import java.util.ArrayList;

import org.example.DAO.RevenueDAO;
import org.example.DTO.RevenueDTO;
import org.example.UtilsDate.UtilsDateCustom;

public class RevenueBUS {
    private final RevenueDAO revenueDAO = new RevenueDAO();
    
    public ArrayList<RevenueDTO> getRevenues(String selectedViewBy, int month, int year){
        ArrayList<RevenueDTO> revenues = new ArrayList<>();

        if("Years".equalsIgnoreCase(selectedViewBy)){
            revenues = revenueDAO.getRevenues();
        } else
        if("Months".equalsIgnoreCase(selectedViewBy)){
            revenues = revenueDAO.getRevenues(year);
        } else
        if("Days".equalsIgnoreCase(selectedViewBy)){
            revenues = revenueDAO.getRevenues(month, year);
        }

        return revenues;
    }

    public ArrayList<RevenueDTO> getRevenuesByDate(Date fromDate, Date toDate){
        return revenueDAO.getRevenues(fromDate, toDate);
    }

    public short validateDate(Date fromDate, Date toDate){
        //0: Không có lỗi
        //1: Lỗi trống ngày bắt đầu hoặc ngày kết thúc
        //2: Lỗi ngày bắt đầu lớn hơn ngày kết thúc
        if(fromDate == null || toDate == null) return 1;
        if(fromDate.getTime() > toDate.getTime()) return 2;
        return 0;
    }

    public void setFromDateToDate(Date[] refDates, int days){
        Date today = new Date();
        days--;
        refDates[0] = UtilsDateCustom.addDays(today, -days);
        refDates[1] = today;
    }
}
