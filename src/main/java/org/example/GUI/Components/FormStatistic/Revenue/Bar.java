package org.example.GUI.Components.FormStatistic.Revenue;

import java.util.Date;

public class Bar {
    Date date;
    double tongTien;

    public Bar(Date date, double tongTien){
        this.date = date;
        this.tongTien = tongTien;
    }

    public Date getDate(){
        return date;
    }

    public double getTongTien(){
        return tongTien;
    }
}
