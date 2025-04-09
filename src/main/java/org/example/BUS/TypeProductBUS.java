package org.example.BUS;

import org.example.DAO.TypeProductDAO;
import org.example.DTO.TypeProduct;

import java.text.Normalizer;
import java.util.ArrayList;

public class TypeProductBUS {
    private ArrayList<TypeProduct> listLoaiSP;
    private TypeProductDAO typeProductDao = new TypeProductDAO();

    public TypeProductBUS() {
    }

    // Lấy danh sách loại sản phẩm từ DAO
    public void listLoaiSP() {
        listLoaiSP = new ArrayList<>();
        listLoaiSP = typeProductDao.selectAll();
    }

    // Thêm loại sản phẩm mới
    public Boolean add(TypeProduct typeProduct) {
        return typeProductDao.add(typeProduct);
    }

    // Cập nhật thông tin loại sản phẩm
    public Boolean update(TypeProduct typeProduct) {
        return typeProductDao.update(typeProduct);
    }

    // Kiểm tra xem mã loại sản phẩm đã tồn tại chưa
    public Boolean checkId(String id) {
        for (TypeProduct typeProduct : listLoaiSP) {
            if (id.equals(typeProduct.getMaLSP())) {
                return true;
            }
        }
        return typeProductDao.getIdTypeProduct(id); // Kiểm tra thêm trong DB
    }

    // Lấy danh sách loại sản phẩm (luôn lấy mới từ DAO)
    public ArrayList<TypeProduct> getList() {
        listLoaiSP = typeProductDao.selectAll();
        return listLoaiSP;
    }

    // Lấy thông tin loại sản phẩm theo mã
    public TypeProduct getTypeProductDTO(String maLSP) {
        for (TypeProduct tp : getList()) {
            if (tp.getMaLSP().equals(maLSP)) {
                return tp;
            }
        }
        return null;
    }

    // Xóa dấu tiếng Việt để tìm kiếm
    private String removeAccents(String input) {
        input = input.toLowerCase();
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // Kiểm tra loại sản phẩm có khớp với từ khóa tìm kiếm không
    public boolean isMatched(TypeProduct typeProduct, String selectedField, String txt) {
        String normalizedTxt = removeAccents(txt.toLowerCase());

        switch (selectedField) {
            case "MaLSP": // Sửa thành MaLSP
                return removeAccents(typeProduct.getMaLSP().toLowerCase()).contains(normalizedTxt);
            case "TenLSP": // Sửa thành TenLSP
                return removeAccents(typeProduct.getTenLSP().toLowerCase()).contains(normalizedTxt);
            case "MoTa":
                return removeAccents(typeProduct.getMoTa().toLowerCase()).contains(normalizedTxt);
            default:
                return false;
        }
    }

    // Main để kiểm tra
    public static void main(String[] args) {
        TypeProductBUS manager = new TypeProductBUS();
        ArrayList<TypeProduct> list = manager.getList();
        for (TypeProduct tp : list) {
            System.out.println(tp.getTenLSP());
        }
    }
}