package org.example.BUS;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.InvoiceDAO;
import org.example.DTO.InvoiceDTO;
import org.example.DTO.InvoiceDetailDTO;
import org.example.UtilsDate.UtilsDateCustom;

public class InvoiceBUS {
    private static final String INVOICE_PREFIX = "HD";
    private static final String CUSTOMER_PREFIX = "KH";
    private static final String EMPLOYEE_PREFIX = "NV";
    
    private InvoiceDAO invoiceDAO;

    public InvoiceBUS() {
        invoiceDAO = new InvoiceDAO();
    }

    public String getMaxInvoiceID() {
        return invoiceDAO.getMaxInvoiceID();
    }

    public ArrayList<InvoiceDTO> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    public InvoiceDTO getInvoiceByID(String maHoaDon) {
        return invoiceDAO.getInvoiceByID(maHoaDon);
    }

    public ArrayList<InvoiceDetailDTO> getInvoiceDetailsByID(String maHoaDon){
        return invoiceDAO.getInvoiceDetailsByID(maHoaDon);
    }

    public boolean addInvoice(InvoiceDTO invoice) {
        return invoiceDAO.addInvoice(invoice);
    }

    public boolean addInvoiceDetail(InvoiceDetailDTO detail) {
        return invoiceDAO.addInvoiceDetail(detail);
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

    
    public void setBeginAndEndDate(String dateOption, Date refDates[], Date customFromDate, Date customToDate){
        Date today = new Date();
        
        switch (dateOption) {
            case "Today":
                refDates[0] = refDates[1] = today;
                break;
            case "Yesterday":
                refDates[0] = refDates[1] = UtilsDateCustom.addDays(today, -1);
                break;
            case "ThisWeek":
                refDates[0] = UtilsDateCustom.getFirstDayOfWeek(today);
                refDates[1] = UtilsDateCustom.getLastDayOfWeek(today);
                break;
            case "ThisMonth":
                refDates[0] = UtilsDateCustom.getFirstDayOfMonth(today);
                refDates[1] = UtilsDateCustom.getLastDayOfMonth(today);
                break;
            case "ThisYear":
                refDates[0] = UtilsDateCustom.getFirstDayOfYear(today);
                refDates[1] = UtilsDateCustom.getLastDayOfYear(today);
                break;
            case "Custom":
                refDates[0] = customFromDate;
                refDates[1] = customToDate;
                break;
            default:
                refDates[0] = refDates[1] = null;
                break;
        }
    }
}