package org.example.BUS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.example.DAO.InvoiceDAO;
import org.example.DTO.InvoiceDTO;

public class InvoiceBUS {
    private InvoiceDAO invoiceDAO;

    public InvoiceBUS() {
        invoiceDAO = new InvoiceDAO();
    }

    public String getNextInvoiceID(){
        final String prefix = "HD";
        String maxInvoiceID = invoiceDAO.getMaxInvoiceID();
        String numPart = maxInvoiceID.substring(prefix.length());
        int nextNum = Integer.parseInt(numPart) + 1;

        return String.format("%s%03d", prefix, nextNum);
    }
    public Boolean add(InvoiceDTO invoice){
        return invoiceDAO.insertInvoice(invoice);
    }

    public ArrayList<InvoiceDTO> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    public InvoiceDTO getInvoiceByID(String maHoaDon) {
        return invoiceDAO.getInvoiceByID(maHoaDon);
    }

    public ArrayList<InvoiceDTO> getFilteredInvoices(String maHoaDon,
                                                     String maKhachHang,
                                                     String maNhanVien,
                                                     Date beginDate,
                                                     Date endDate) {
        return invoiceDAO.getFilteredInvoices(maHoaDon, maKhachHang, maNhanVien, beginDate, endDate);
    }

    public void setBeginAndEndDate(String dateOption, Date refDates[], Date customBeginDate, Date customEndDate){
        Date today = new Date();
        
        switch (dateOption) {
            case "Hôm nay":
                refDates[0] = refDates[1] = today;
                break;
            case "Hôm qua":
                refDates[0] = refDates[1] = addDays(today, -1);
                break;
            case "Tuần này":
                refDates[0] = getFirstDayOfWeek(today);
                refDates[1] = getLastDayOfWeek(today);
                break;
            case "Tháng này":
                refDates[0] = getFirstDayOfMonth(today);
                refDates[1] = getLastDayOfMonth(today);
                break;
            case "Năm nay":
                refDates[0] = getFirstDayOfYear(today);
                refDates[1] = getLastDayOfYear(today);
                break;
            case "Tuỳ chọn":
                refDates[0] = customBeginDate;
                refDates[1] = customEndDate;
                break;
            default:
                refDates[0] = refDates[1] = null;
                break;
        }
    }
    
    public Date addDays(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    public Date getFirstDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Ngày đầu tiên trong tuần là thứ 2
        cal.setFirstDayOfWeek(2);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    public Date getLastDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // Ngày đầu tiên trong tuần là thứ 2
        cal.setFirstDayOfWeek(2);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        return cal.getTime();
    }

    public Date getFirstDayOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public Date getLastDayOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public Date getFirstDayOfYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    public Date getLastDayOfYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        return cal.getTime();
    }
}