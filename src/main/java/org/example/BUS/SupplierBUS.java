package org.example.BUS;

import org.example.DAO.SupplierDAO;
import org.example.DTO.SupplierDTO;

import java.text.Normalizer;
import java.util.ArrayList;

public class SupplierBUS {
    private ArrayList<SupplierDTO> listNcc;
    private SupplierDAO nccDao = new SupplierDAO();

    public SupplierBUS() {
    }

    public void listNCC() {
        listNcc = new ArrayList<>();
        listNcc = nccDao.selectAll();
    }

    public Boolean add(SupplierDTO supplier) {
        return nccDao.add(supplier);
    }

    public Boolean update(SupplierDTO supplier) {
        return nccDao.update(supplier);
    }

    public Boolean checkId(String id) {
        for (SupplierDTO supplier : listNcc) {
            if (id.equals(supplier.getMaNCC())) {
                return true;
            }
        }
        return false;
    }

    public Boolean updateTrangthai(String mancc, int trangthai) {
        return nccDao.updateTrangThai(mancc, trangthai);
    }

    public ArrayList<SupplierDTO> getList() {
        listNcc = nccDao.selectAll(); // Luôn lấy lại dữ liệu mới từ DAO
        return listNcc;
    }  

    public SupplierDTO getSupplierDTO(String mancc) {
        for (SupplierDTO ncc : getList()) {
            if (ncc.getMaNCC().equals(mancc)) {
                return ncc;
            }
        }
        return null;
    }

    private String removeAccents(String input) {
        input = input.toLowerCase();
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public boolean isMatched(SupplierDTO sup, String selectedField, String txt) {
        String normalizedTxt = removeAccents(txt.toLowerCase());

        switch (selectedField) {
            case "MaNCC":
                return removeAccents(sup.getMaNCC().toLowerCase()).contains(normalizedTxt);
            case "TenNCC":
                return removeAccents(sup.getTenNCC().toLowerCase()).contains(normalizedTxt);
            case "SDT":
                return removeAccents(sup.getSDT().toLowerCase()).contains(normalizedTxt);
            case "Địa chỉ":
                return removeAccents(sup.getDiaChi().toLowerCase()).contains(normalizedTxt);
            default:
                return false;
        }
    }

    public static void main(String[] args) {
        SupplierBUS manager = new SupplierBUS();
        ArrayList<SupplierDTO> list = manager.getList();
        for (SupplierDTO a : list) {
            System.out.println(a.getTenNCC());
        }
    }
}