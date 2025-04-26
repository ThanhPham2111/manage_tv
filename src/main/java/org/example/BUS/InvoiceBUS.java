package org.example.BUS;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.InvoiceDAO;
import org.example.DTO.InvoiceDTO;

public class InvoiceBUS {
    private static final String INVOICE_PREFIX = "HD";
    private static final String CUSTOMER_PREFIX = "KH";
    private static final String EMPLOYEE_PREFIX = "NV";

    private InvoiceDAO invoiceDAO;

    public InvoiceBUS() {
        invoiceDAO = new InvoiceDAO();
    }

    public String getNextInvoiceID(){
        final String INVOICE_PREFIX = "HD";
        String maxInvoiceID = invoiceDAO.getMaxInvoiceID();
        String numPart = maxInvoiceID.substring(INVOICE_PREFIX.length());
        int nextNum = Integer.parseInt(numPart) + 1;

        return String.format("%s%03d", INVOICE_PREFIX, nextNum);
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

    public InvoiceDTO getInvoiceDetailsByID(String maHoaDon){
        return invoiceDAO.getInvoiceDetailsByID(maHoaDon);
    }
    
    public ArrayList<InvoiceDTO> getFilteredInvoices(String maHoaDon,
                                                     String maKhachHang,
                                                     String maNhanVien,
                                                     Date beginDate,
                                                     Date endDate) {
        return invoiceDAO.getFilteredInvoices(maHoaDon, maKhachHang, maNhanVien, beginDate, endDate);
    }

    public String validateFilter(String maHoaDon,
                          String maKhachHang,
                          String maNhanVien,
                          Date beginDate,
                          Date endDate){
        final String invalidInvoiceIDMessage = String.format("- Mã hoá đơn phải bắt đầu bằng %s   \n  VD: %s", '\"' + INVOICE_PREFIX + '\"', INVOICE_PREFIX + "001");
        final String invalidCustomerIDMessage = String.format("- Mã khách hàng phải bắt đầu bằng %s   \n  VD: %s", '\"' + CUSTOMER_PREFIX + '\"', CUSTOMER_PREFIX + "001");
        final String invalidEmployeeIDMessage = String.format("- Mã nhân viên phải bắt đầu bằng %s   \n  VD: %s", '\"' + EMPLOYEE_PREFIX + '\"', EMPLOYEE_PREFIX + "001");
        final String invalidCustomDate = "- Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc   ";

        ArrayList<String> invalidMessages = new ArrayList<>();

        if(!isValidInvoiceID(maHoaDon)) invalidMessages.add(invalidInvoiceIDMessage);
        if(!isValidCustomerID(maKhachHang)) invalidMessages.add(invalidCustomerIDMessage);
        if(!isValidEmployeeID(maNhanVien)) invalidMessages.add(invalidEmployeeIDMessage);
        if(!isValidCustomDate(beginDate, endDate)) invalidMessages.add(invalidCustomDate);

        return String.join("\n\n", invalidMessages);
    }

    public Boolean isValidID(String prefixStandardID, String ID){
        if(ID == null || ID.isEmpty()) return true;
        if(ID.length() < prefixStandardID.length()) return false;

        String prefixID = ID.substring(0, prefixStandardID.length());
        return prefixStandardID.equalsIgnoreCase(prefixID);
    }
    
    public Boolean isValidInvoiceID(String maHoaDon){
        return isValidID(INVOICE_PREFIX, maHoaDon);
    }
    
    public Boolean isValidCustomerID(String maKhachHang){
        return isValidID(CUSTOMER_PREFIX, maKhachHang);
    }

    public Boolean isValidEmployeeID(String maNhanVien){
        return isValidID(EMPLOYEE_PREFIX, maNhanVien);
    }

    public Boolean isValidCustomDate(Date beginDate, Date endDate){
        if(beginDate == null || endDate == null) return true;
        if(beginDate.getTime() > endDate.getTime()) return false;

        return true;
    }

    public Boolean exportExcel(TableModel model, String path){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hoá đơn");

        Row header = sheet.createRow(0);
        
        for(int i = 0; i < model.getColumnCount(); i++){
            header.createCell(i).setCellValue(model.getColumnName(i));
        }

        for(int i = 0; i < model.getRowCount(); i++){
            Row row = sheet.createRow(i + 1);
            for(int j = 0; j < model.getColumnCount(); j++){
                Object value = model.getValueAt(i, j);
                if(value != null){
                    row.createCell(j).setCellValue(value.toString());
                }
            }
        }

        try (FileOutputStream out = new FileOutputStream(path)) {
            workbook.write(out);
            workbook.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public void setBeginAndEndDate(String dateOption, Date refDates[], Date customBeginDate, Date customEndDate){
        Date today = new Date();
        
        switch (dateOption) {
            case "Today":
                refDates[0] = refDates[1] = today;
                break;
            case "Yesterday":
                refDates[0] = refDates[1] = addDays(today, -1);
                break;
            case "ThisWeek":
                refDates[0] = getFirstDayOfWeek(today);
                refDates[1] = getLastDayOfWeek(today);
                break;
            case "ThisMonth":
                refDates[0] = getFirstDayOfMonth(today);
                refDates[1] = getLastDayOfMonth(today);
                break;
            case "ThisYear":
                refDates[0] = getFirstDayOfYear(today);
                refDates[1] = getLastDayOfYear(today);
                break;
            case "Custom":
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