package org.example.BUS;

import org.example.DAO.CustomerDAO;
import org.example.DTO.CustomerDTO;

import java.text.Normalizer;
import java.util.ArrayList;

public class CustomerBUS {
    private ArrayList<CustomerDTO> listKh;
    private CustomerDAO customerDAO;

    public CustomerBUS() {
        customerDAO = new CustomerDAO();
        listKH(); // Khởi tạo danh sách
    }

    // Khởi tạo hoặc làm mới danh sách khách hàng
    public void listKH() {
        listKh = new ArrayList<>();
        listKh = customerDAO.selectAll();
    }

    // Thêm khách hàng
    public Boolean add(CustomerDTO customer) {
        return customerDAO.add(customer);
    }

    // Cập nhật thông tin khách hàng
    public Boolean update(CustomerDTO customer) {
        return customerDAO.update(customer.getMaKH(), customer.getTenKH(), customer.getDiaChi(), customer.getSdt(),
                customer.getTrangThai());
    }

    // Kiểm tra xem mã khách hàng đã tồn tại chưa
    public Boolean checkId(String id) {
        for (CustomerDTO customer : listKh) {
            if (id.equals(customer.getMaKH())) {
                return true;
            }
        }
        return false;
    }

    // Cập nhật trạng thái khách hàng
    public Boolean updateTrangthai(String makh, int trangthai) {
        return customerDAO.updateTrangThai(makh, trangthai);
    }

    // Lấy danh sách khách hàng (luôn lấy dữ liệu mới từ DAO)
    public ArrayList<CustomerDTO> getList() {
        listKh = customerDAO.selectAll(); // Luôn lấy dữ liệu mới từ DAO
        return listKh;
    }

    // Tìm khách hàng theo mã
    public CustomerDTO getCustomerDTO(String makh) {
        for (CustomerDTO kh : getList()) {
            if (kh.getMaKH().equals(makh)) {
                return kh;
            }
        }
        return null;
    }

    // Xóa dấu trong chuỗi (dùng để tìm kiếm không phân biệt dấu)
    private String removeAccents(String input) {
        input = input.toLowerCase();
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // Kiểm tra xem khách hàng có khớp với tiêu chí tìm kiếm không
    public boolean isMatched(CustomerDTO kh, String selectedField, String txt) {
        String normalizedTxt = removeAccents(txt.toLowerCase());

        switch (selectedField) {
            case "MaKH":
                return removeAccents(kh.getMaKH().toLowerCase()).contains(normalizedTxt);
            case "TenKH":
                return removeAccents(kh.getTenKH().toLowerCase()).contains(normalizedTxt);
            case "SDT":
                return removeAccents(kh.getSdt().toLowerCase()).contains(normalizedTxt);
            case "Địa chỉ":
                return removeAccents(kh.getDiaChi().toLowerCase()).contains(normalizedTxt);
            case "Trạng thái":
                return removeAccents(kh.getTrangThai() == 1 ? "Ẩn" : "Hiện").contains(normalizedTxt);
            default:
                return false;
        }
    }

    // Tìm kiếm khách hàng theo giá trị và tiêu chí
    public ArrayList<CustomerDTO> search(String value, String type) {
        ArrayList<CustomerDTO> result = new ArrayList<>();
        String normalizedValue = removeAccents(value.toLowerCase());

        for (CustomerDTO kh : getList()) {
            if (type.equals("Tất cả")) {
                // Kiểm tra tất cả các trường
                if (removeAccents(kh.getMaKH().toLowerCase()).contains(normalizedValue) ||
                        removeAccents(kh.getTenKH().toLowerCase()).contains(normalizedValue) ||
                        removeAccents(kh.getDiaChi().toLowerCase()).contains(normalizedValue) ||
                        removeAccents(kh.getSdt().toLowerCase()).contains(normalizedValue) ||
                        removeAccents(kh.getTrangThai() == 0 ? "Hiện" : "Ẩn").contains(normalizedValue)) {
                    result.add(kh);
                }
            } else {
                // Kiểm tra theo trường cụ thể
                String field = switch (type) {
                    case "Mã khách hàng" -> "MaKH";
                    case "Tên khách hàng" -> "TenKH";
                    case "Số điện thoại" -> "SDT";
                    case "Địa chỉ" -> "Địa chỉ";
                    case "Trạng thái" -> "Trạng thái";
                    default -> "";
                };
                if (isMatched(kh, field, value)) {
                    result.add(kh);
                }
            }
        }
        return result;
    }

    // Lọc khách hàng theo trạng thái
    public ArrayList<CustomerDTO> filterByStatus(int status) {
        ArrayList<CustomerDTO> result = new ArrayList<>();
        for (CustomerDTO kh : getList()) {
            if (kh.getTrangThai() == status) {
                result.add(kh);
            }
        }
        return result;
    }

    // Lấy ID tiếp theo
  public String getNextID() {
    if (listKh == null || listKh.isEmpty()) {
        return "KH001";
    }
    int maxId = 0;
    for (CustomerDTO kh : listKh) {
        String id = kh.getMaKH();
        if (id.startsWith("KH")) {
            try {
                int idNum = Integer.parseInt(id.replace("KH", ""));
                if (idNum > maxId) {
                    maxId = idNum;
                }
            } catch (NumberFormatException e) {
                // Log lỗi nhưng không bỏ qua
                System.err.println("Invalid MaKH format: " + id);
            }
        }
    }
    return String.format("KH%03d", maxId + 1);
}

    // Hiển thị danh sách khách hàng (dùng để debug)
    public void display() {
        for (CustomerDTO kh : getList()) {
            System.out.println("Mã KH: " + kh.getMaKH());
            System.out.println("Tên KH: " + kh.getTenKH());
            System.out.println("Địa chỉ: " + kh.getDiaChi());
            System.out.println("SĐT: " + kh.getSdt());
            System.out.println("Trạng thái: " + kh.getTrangThai());
            System.out.println("-------------------");
        }
    }

    // Lấy headers cho bảng
    public String[] getHeaders() {
        return new String[] { "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "SĐT", "Trạng thái" };
    }

    // Main để test
    public static void main(String[] args) {
        CustomerBUS manager = new CustomerBUS();
        ArrayList<CustomerDTO> list = manager.getList();
        for (CustomerDTO kh : list) {
            System.out.println(kh.getTenKH());
        }
    }
}