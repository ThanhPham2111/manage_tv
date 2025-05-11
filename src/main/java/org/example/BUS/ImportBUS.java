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
import org.example.DAO.ImportDAO;
import org.example.DAO.ImportDetailDAO;
import org.example.DTO.ImportDTO;
import org.example.DTO.ImportDetailDTO;

public class ImportBUS {
    private ImportDAO importSlipDAO;
    private ImportDetailDAO importSlipDetailDAO;

    public ImportBUS() {
        importSlipDAO = new ImportDAO();
        importSlipDetailDAO = new ImportDetailDAO();
    }

    public List<ImportDTO> getAllImportSlips() {
        return importSlipDAO.getAllImportSlips();
    }

    public ImportDTO getImportSlipById(String maPhieuNhap) {
        return importSlipDAO.getImportSlipById(maPhieuNhap);
    }

    public List<ImportDetailDTO> getImportSlipDetails(String maPhieuNhap) {
        return importSlipDetailDAO.getDetailsByImportSlipId(maPhieuNhap);
    }

    public List<ImportDTO> searchImportSlips(String searchValue, String searchType) {
        List<ImportDTO> result = new ArrayList<>();
        List<ImportDTO> allSlips = getAllImportSlips();

        for (ImportDTO slip : allSlips) {
            switch (searchType) {
                case "Mã phiếu nhập":
                    if (slip.getMaPN().toLowerCase().contains(searchValue.toLowerCase())) {
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
                    if (slip.getMaPN().toLowerCase().contains(searchValue.toLowerCase()) ||
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
            String[] columns = { "Mã Phiếu Nhập", "Ngày Nhập", "Giờ Nhập", "Mã Nhân Viên", "Tổng Tiền" };

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
            List<ImportDTO> importSlips = importSlipDAO.getAllImportSlips();

            // Tạo hàng
            int rowNum = 1;
            for (ImportDTO slip : importSlips) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(slip.getMaPN());
                row.createCell(1).setCellValue(slip.getNgayNhap().toString());
                row.createCell(2).setCellValue(slip.getGioNhap().toString());
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

    public String getNextImportID() {
        String maxImportID = importSlipDAO.getMaxImportID();
        int nextNum = 1;
        if (maxImportID != null && !maxImportID.isEmpty()) {
            String numPart = maxImportID.substring(2); // Bỏ qua 2 ký tự đầu "PN"
            try {
                nextNum = Integer.parseInt(numPart) + 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return String.format("PN%03d", nextNum);
    }

    public boolean addImportSlip(ImportDTO importSlip, ArrayList<ImportDetailDTO> details) {
        try {
            // Thêm phiếu nhập
            if (!importSlipDAO.addImportSlip(importSlip)) {
                return false;
            }

            // Thêm chi tiết phiếu nhập
            for (ImportDetailDTO detail : details) {
                if (!importSlipDetailDAO.addImportDetail(detail)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}