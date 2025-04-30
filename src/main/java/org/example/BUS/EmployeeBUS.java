package org.example.BUS;

import org.example.DAO.EmployeeDAO;
import org.example.DTO.EmployeeDTO;

import java.text.Normalizer;
import java.util.ArrayList;

public class EmployeeBUS {
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private ArrayList<EmployeeDTO> dsnv;

    public EmployeeBUS() {
    }

    // Đọc dữ liệu từ cơ sở dữ liệu
    public void readDB() {
        dsnv = employeeDAO.selectAll();
    }

    // Lấy danh sách nhân viên
    public void listEmployees() {
        dsnv = new ArrayList<>();
        dsnv = employeeDAO.selectAll();
    }

    // Lấy nhân viên theo mã nhân viên
    public EmployeeDTO getEmployeeByMaNV(Object maNV) {
        return employeeDAO.getEmployeeByMaNV(maNV);
    }

    // Kiểm tra mã nhân viên có tồn tại không
    public boolean checkMaNV(String id) {
        ArrayList<EmployeeDTO> listEmployees = getList();
        for (EmployeeDTO emp : listEmployees) {
            if (id.equals(emp.getMaNV())) {
                return true;
            }
        }
        return false;
    }

    // Tạo mã nhân viên tiếp theo
    public String getNextID() {
        return "NV" + String.valueOf(this.dsnv.size() + 1);
    }

    // Thêm nhân viên
    public Boolean add(EmployeeDTO employee) {
        Boolean result = employeeDAO.add(employee);
        if (result) {
            dsnv.add(employee);
        }
        return result;
    }

    // Cập nhật thông tin nhân viên
    public Boolean update(EmployeeDTO employee) {
        Boolean result = employeeDAO.update(employee);
        if (result) {
            dsnv.forEach((emp) -> {
                if (emp.getMaNV().equals(employee.getMaNV())) {
                    emp.setTenNV(employee.getTenNV());
                    emp.setNgaySinh(employee.getNgaySinh());
                    emp.setDiaChi(employee.getDiaChi());
                    emp.setSdt(employee.getSdt());
                    emp.setTrangThai(employee.getTrangThai());
                }
            });
        }
        return result;
    }

    // Cập nhật trạng thái nhân viên
    public Boolean updateTrangThai(String maNV, int trangThai) {
        Boolean result = employeeDAO.updateTrangThai(maNV, trangThai);
        if (result) {
            dsnv.forEach((emp) -> {
                if (emp.getMaNV().equals(maNV)) {
                    emp.setTrangThai(trangThai);
                }
            });
        }
        return result;
    }

    // Lấy danh sách nhân viên
    public ArrayList<EmployeeDTO> getList() {
        if (dsnv == null) {
            listEmployees();
        }
        return dsnv;
    }

    // Lấy nhân viên theo mã nhân viên (tìm trong danh sách nội bộ)
    public EmployeeDTO getEmployee(String maNV) {
        if (dsnv == null) {
            listEmployees();
        }
        for (EmployeeDTO emp : dsnv) {
            if (emp.getMaNV().equals(maNV)) {
                return emp;
            }
        }
        return null;
    }

    // Tìm kiếm nhân viên
    public ArrayList<EmployeeDTO> search(String value, String type) {
        ArrayList<EmployeeDTO> result = new ArrayList<>();

        dsnv.forEach((emp) -> {
            if (type.equals("Tất cả")) {
                if (emp.getMaNV().toLowerCase().contains(value.toLowerCase())
                        || emp.getTenNV().toLowerCase().contains(value.toLowerCase())
                        || emp.getNgaySinh().toString().toLowerCase().contains(value.toLowerCase())
                        || emp.getDiaChi().toLowerCase().contains(value.toLowerCase())
                        || emp.getSdt().toLowerCase().contains(value.toLowerCase())
                        || String.valueOf(emp.getTrangThai() == 1 ? "Ẩn" : "Hiện").toLowerCase()
                                .contains(value.toLowerCase())) {
                    result.add(emp);
                }
            } else {
                switch (type) {
                    case "Mã nhân viên":
                        if (emp.getMaNV().toLowerCase().contains(value.toLowerCase())) {
                            result.add(emp);
                        }
                        break;
                    case "Tên nhân viên":
                        if (emp.getTenNV().toLowerCase().contains(value.toLowerCase())) {
                            result.add(emp);
                        }
                        break;
                    case "Ngày sinh":
                        if (emp.getNgaySinh().toString().toLowerCase().contains(value.toLowerCase())) {
                            result.add(emp);
                        }
                        break;
                    case "Địa chỉ":
                        if (emp.getDiaChi().toLowerCase().contains(value.toLowerCase())) {
                            result.add(emp);
                        }
                        break;
                    case "Số điện thoại":
                        if (emp.getSdt().toLowerCase().contains(value.toLowerCase())) {
                            result.add(emp);
                        }
                        break;
                    case "Trạng thái":
                        if (String.valueOf(emp.getTrangThai() == 1 ? "Ẩn" : "Hiện").toLowerCase()
                                .contains(value.toLowerCase())) {
                            result.add(emp);
                        }
                        break;
                }
            }
        });
        return result;
    }

    // Kiểm tra khớp với trường tìm kiếm
    public boolean isMatched(EmployeeDTO employee, String selectedField, String txt) {
        String normalizedTxt = removeAccents(txt.toLowerCase());
        switch (selectedField) {
            case "MaNV":
                return removeAccents(employee.getMaNV().toLowerCase()).contains(normalizedTxt);
            case "TenNV":
                return removeAccents(employee.getTenNV().toLowerCase()).contains(normalizedTxt);
            case "SDT":
                return removeAccents(employee.getSdt().toLowerCase()).contains(normalizedTxt);
            case "DiaChi":
                return removeAccents(employee.getDiaChi().toLowerCase()).contains(normalizedTxt);
            default:
                return false;
        }
    }

    // Loại bỏ dấu tiếng Việt
    private String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // Main để kiểm tra
    public static void main(String[] args) {
        EmployeeBUS manager = new EmployeeBUS();
        ArrayList<EmployeeDTO> list = manager.getList();
        for (EmployeeDTO emp : list) {
            System.out.println(emp.getTenNV());
        }
    }
}