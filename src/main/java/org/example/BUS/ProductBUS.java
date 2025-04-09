package org.example.BUS;

import org.example.DAO.ProductDAO;
import org.example.DTO.ProductDTO;

import java.text.Normalizer;
import java.util.ArrayList;

public class ProductBUS {
    private ArrayList<ProductDTO> listSp;
    private ProductDAO productDao = new ProductDAO();

    public ProductBUS() {
        listSP(); // Khởi tạo danh sách sản phẩm ngay khi tạo đối tượng
    }

    // Lấy danh sách sản phẩm từ DAO
    public void listSP() {
        listSp = new ArrayList<>();
        listSp = productDao.selectAll();
    }

    // Thêm sản phẩm mới
    public Boolean add(ProductDTO product) {
        Boolean success = productDao.add(product);
        if (success) {
            listSp.add(product); // Cập nhật danh sách nội bộ nếu thêm thành công
        }
        return success;
    }

    // Cập nhật thông tin sản phẩm
    public Boolean update(ProductDTO product) {
        Boolean success = productDao.update(product);
        if (success) {
            // Cập nhật lại danh sách nội bộ
            for (int i = 0; i < listSp.size(); i++) {
                if (listSp.get(i).getMaSP().equals(product.getMaSP())) {
                    listSp.set(i, product);
                    break;
                }
            }
        }
        return success;
    }

    // Kiểm tra xem mã sản phẩm đã tồn tại chưa
    public Boolean checkId(String id) {
        for (ProductDTO product : listSp) {
            if (id.equals(product.getMaSP())) {
                return true;
            }
        }
        return productDao.getIdProduct(id); // Kiểm tra thêm trong DB nếu không có trong list
    }

    // Cập nhật trạng thái sản phẩm
    public Boolean updateTrangthai(String maSP, int trangthai) {
        Boolean success = productDao.updateTrangThai(maSP, trangthai);
        if (success) {
            for (ProductDTO product : listSp) {
                if (product.getMaSP().equals(maSP)) {
                    product.setTrangthai(trangthai);
                    break;
                }
            }
        }
        return success;
    }

    // Cập nhật số lượng sản phẩm
    public Boolean updateSoLuong(String maSP, int soLuong) {
        Boolean success = productDao.updateSoLuong(maSP, soLuong);
        if (success) {
            for (ProductDTO product : listSp) {
                if (product.getMaSP().equals(maSP)) {
                    product.setSoLuong(soLuong);
                    break;
                }
            }
        }
        return success;
    }

    // Lấy danh sách sản phẩm (luôn lấy mới từ DAO)
    public ArrayList<ProductDTO> getList() {
        listSp = productDao.selectAll(); // Luôn lấy lại dữ liệu mới từ DAO
        return listSp;
    }

    // Lấy thông tin sản phẩm theo mã sản phẩm
    public ProductDTO getProductDTO(String maSP) {
        for (ProductDTO product : getList()) {
            if (product.getMaSP().equals(maSP)) {
                return product;
            }
        }
        return null;
    }

    // Sinh mã sản phẩm tiếp theo
    public String getNextID() {
        if (listSp == null || listSp.isEmpty()) {
            listSP(); // Đảm bảo danh sách được tải nếu chưa có
        }

        if (listSp.isEmpty()) {
            return "SP001"; // Mã mặc định nếu chưa có sản phẩm nào
        }

        // Tìm mã lớn nhất
        String maxID = "SP000";
        for (ProductDTO product : listSp) {
            String currentID = product.getMaSP();
            if (currentID.compareTo(maxID) > 0) {
                maxID = currentID;
            }
        }

        // Tăng mã lên 1
        String prefix = maxID.substring(0, 2); // "SP"
        int number = Integer.parseInt(maxID.substring(2)); // Lấy phần số
        number++;
        return String.format("%s%03d", prefix, number); // Định dạng lại: SP001, SP002, ...
    }

    // Xóa dấu tiếng Việt để tìm kiếm
    private String removeAccents(String input) {
        input = input.toLowerCase();
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // Kiểm tra sản phẩm có khớp với từ khóa tìm kiếm không
    public boolean isMatched(ProductDTO product, String selectedField, String txt) {
        String normalizedTxt = removeAccents(txt.toLowerCase());

        switch (selectedField) {
            case "MaSP":
                return removeAccents(product.getMaSP().toLowerCase()).contains(normalizedTxt);
            case "MaLSP":
                return removeAccents(product.getMaLSP().toLowerCase()).contains(normalizedTxt);
            case "TenSP":
                return removeAccents(product.getTenSP().toLowerCase()).contains(normalizedTxt);
            case "DonGia":
                return String.valueOf(product.getDonGia()).contains(txt);
            case "SoLuong":
                return String.valueOf(product.getSoLuong()).contains(txt);
            default:
                return false;
        }
    }

    // Main để kiểm tra
    public static void main(String[] args) {
        ProductBUS manager = new ProductBUS();
        ArrayList<ProductDTO> list = manager.getList();
        for (ProductDTO p : list) {
            System.out.println(p.getTenSP());
        }
        // Kiểm tra getNextID
        System.out.println("Next ID: " + manager.getNextID());
    }
}