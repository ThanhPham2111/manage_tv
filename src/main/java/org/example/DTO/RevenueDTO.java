package org.example.DTO;

import java.util.Date;

public class RevenueDTO {
    private Date date;
    private double tongTien;

    public RevenueDTO(){
        date = null;
        tongTien = 0.0;
    }

    public RevenueDTO(Date date, double tongTien){
        this.date = date;
        this.tongTien = tongTien;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public double getTongTien(){
        return tongTien;
    }

    public void setTongTien(double tongTien){
        this.tongTien = tongTien;
    }
}
