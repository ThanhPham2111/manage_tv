package org.example.BUS;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DAO.ImportSlipDAO;
import org.example.DAO.ImportSlipDetailDAO;
import org.example.DTO.ImportSlipDTO;
import org.example.DTO.ImportSlipDetailDTO;

public class ImportSlipBUS {
    private ImportSlipDAO importSlipDAO;
    private ImportSlipDetailDAO importSlipDetailDAO;

    public ImportSlipBUS() {
        importSlipDAO = new ImportSlipDAO();
        importSlipDetailDAO = new ImportSlipDetailDAO();
    }

    public List<ImportSlipDTO> getAllImportSlips() {
        return importSlipDAO.getAllImportSlips();
    }

    public ImportSlipDTO getImportSlipById(String maPhieuNhap) {
        return importSlipDAO.getImportSlipById(maPhieuNhap);
    }

    public List<ImportSlipDetailDTO> getImportSlipDetails(String maPhieuNhap) {
        return importSlipDetailDAO.getDetailsByImportSlipId(maPhieuNhap);
    }

    public List<ImportSlipDTO> searchImportSlips(String searchValue, String searchType) {
        List<ImportSlipDTO> result = new ArrayList<>();
        List<ImportSlipDTO> allSlips = getAllImportSlips();

        for (ImportSlipDTO slip : allSlips) {
            switch (searchType) {
                case "Mã phiếu nhập":
                    if (slip.getMaPhieuNhap().toLowerCase().contains(searchValue.toLowerCase())) {
                        result.add(slip);
                    }
                    break;
                case "Mã nhân viên":
                    if (slip.getMaNV().toLowerCase().contains(searchValue.toLowerCase())) {
                        result.add(slip);
                    }
                    break;
                case "Mã nhà cung cấp":
                    if (slip.getMaNCC().toLowerCase().contains(searchValue.toLowerCase())) {
                        result.add(slip);
                    }
                    break;
                default:
                    if (slip.getMaPhieuNhap().toLowerCase().contains(searchValue.toLowerCase()) ||
                        slip.getMaNV().toLowerCase().contains(searchValue.toLowerCase()) ||
                        slip.getMaNCC().toLowerCase().contains(searchValue.toLowerCase())) {
                        result.add(slip);
                    }
                    break;
            }
        }
        return result;
    }

    public boolean exportToExcel(String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Phiếu Nhập");
            
            // Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Mã Phiếu Nhập", "Ngày Lập", "Giờ Lập", "Mã Nhân Viên", "Tổng Tiền"};
            
            // Kiểu
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            
            // Tạo ô
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }
            
            // Nhận tất cả các biên lai nhập khẩu
            List<ImportSlipDTO> importSlips = importSlipDAO.getAllImportSlips();
            
            // Tạo hàng
            int rowNum = 1;
            for (ImportSlipDTO slip : importSlips) {
                Row row = sheet.createRow(rowNum++);
                
                row.createCell(0).setCellValue(slip.getMaPhieuNhap());
                row.createCell(1).setCellValue(slip.getNgayLap().toString());
                row.createCell(2).setCellValue(slip.getGioLap().toString());
                row.createCell(3).setCellValue(slip.getMaNV());
                row.createCell(4).setCellValue(slip.getTongTien());
            }
            
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Ghi vào tập tin
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
} 