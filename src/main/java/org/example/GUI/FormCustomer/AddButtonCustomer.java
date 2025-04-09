package org.example.GUI.FormCustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import org.example.BUS.CustomerBUS;
import org.example.DTO.CustomerDTO;

public class AddButtonCustomer extends JFrame {

    private String title;
    private String maKhachHang;
    private CustomerGUI parentPanel;

    private String type;
    private CustomerBUS qlkhBUS = new CustomerBUS();
    private CustomerDTO khSua;

    private JTextField txMakh;
    private JTextField txTenkh;
    private JTextField txDiachi;
    private JTextField txSDT;
    private JComboBox<String> cbChonTrangThai;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnHuy;

    public AddButtonCustomer(String title, String maKhachHang, CustomerGUI parentPanel) {
        this.title = title;
        this.maKhachHang = maKhachHang;
        this.parentPanel = parentPanel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.type = this.title;

        // Panel cho inputs
        JPanel plInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        plInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Khởi tạo các trường nhập liệu
        txMakh = new JTextField(15);
        txTenkh = new JTextField(15);
        txDiachi = new JTextField(15);
        txSDT = new JTextField(15);
        cbChonTrangThai = new JComboBox<>(new String[] { "Ẩn", "Hiện" });

        // Gán tiêu đề cho các trường
        txMakh.setBorder(BorderFactory.createTitledBorder("Mã khách hàng"));
        txTenkh.setBorder(BorderFactory.createTitledBorder("Tên khách hàng"));
        txDiachi.setBorder(BorderFactory.createTitledBorder("Địa chỉ"));
        txSDT.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));

        // Panel cho trạng thái
        JPanel plChonTT = new JPanel(new FlowLayout(FlowLayout.LEFT));
        plChonTT.setBorder(BorderFactory.createTitledBorder("Trạng thái"));
        plChonTT.add(new JLabel("Trạng thái: "));
        plChonTT.add(cbChonTrangThai);

        // Thêm các trường vào panel input
        plInput.add(txMakh);
        plInput.add(txTenkh);
        plInput.add(txDiachi);
        plInput.add(txSDT);
        plInput.add(plChonTT);

        // Panel cho buttons
        JPanel plButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnHuy = new JButton("Hủy");

        // Thêm icon cho các nút
        ImageIcon addIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png");
        if (addIcon != null)
            btnThem.setIcon(addIcon);

        ImageIcon editIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_support_30px.png");
        if (editIcon != null)
            btnSua.setIcon(editIcon);

        ImageIcon cancelIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_cancel_30px_1.png");
        if (cancelIcon != null)
            btnHuy.setIcon(cancelIcon);

        // Xử lý logic Thêm hoặc Sửa
        if ("Thêm".equals(type)) {
            setTitle("Thêm khách hàng");
            txMakh.setText(qlkhBUS.getNextID());
            txMakh.setEnabled(false);
            cbChonTrangThai.setSelectedItem("Hiện");
            plButton.add(btnThem);
        } else {
            setTitle("Sửa khách hàng");
            khSua = qlkhBUS.getCustomerDTO(maKhachHang);
            if (khSua == null) {
                JOptionPane.showMessageDialog(this, "Lỗi, không tìm thấy khách hàng");
                dispose();
                return;
            }
            cbChonTrangThai.setSelectedItem(khSua.getTrangThai() == 0 ? "Hiện" : "Ẩn");
            txMakh.setText(khSua.getMaKH());
            txMakh.setEnabled(false);
            txTenkh.setText(khSua.getTenKH());
            txDiachi.setText(khSua.getDiaChi());
            txSDT.setText(khSua.getSdt());
            plButton.add(btnSua);
        }
        plButton.add(btnHuy);

        // Thêm các panel vào JFrame
        add(plInput, BorderLayout.CENTER);
        add(plButton, BorderLayout.SOUTH);

        // Action listeners
        btnThem.addActionListener(e -> btnThemMouseClicked());
        btnSua.addActionListener(e -> btnSuaMouseClicked());
        btnHuy.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void btnThemMouseClicked() {
        if (checkEmpty()) {
            String makh = txMakh.getText();
            String tenkh = txTenkh.getText();
            String diachi = txDiachi.getText();
            String sdt = txSDT.getText();
            int trangthai = "Hiện".equals(cbChonTrangThai.getSelectedItem().toString()) ? 0 : 1;

            // Kiểm tra định dạng số điện thoại
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (phải bắt đầu bằng 0 và có 10 chữ số)");
                txSDT.requestFocus();
                return;
            }

            CustomerDTO newCustomer = new CustomerDTO(makh, tenkh, diachi, sdt, trangthai);
            if (qlkhBUS.add(newCustomer)) {
                JOptionPane.showMessageDialog(this, "Thêm " + tenkh + " thành công!");
                if (parentPanel != null) {
                    parentPanel.refresh();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        }
    }

    private void btnSuaMouseClicked() {
        if (checkEmpty()) {
            String makh = txMakh.getText();
            String tenkh = txTenkh.getText();
            String diachi = txDiachi.getText();
            String sdt = txSDT.getText();
            int trangthai = "Hiện".equals(cbChonTrangThai.getSelectedItem().toString()) ? 0 : 1;

            // Kiểm tra định dạng số điện thoại
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (phải bắt đầu bằng 0 và có 10 chữ số)");
                txSDT.requestFocus();
                return;
            }

            CustomerDTO updatedCustomer = new CustomerDTO(makh, tenkh, diachi, sdt, trangthai);
            if (qlkhBUS.update(updatedCustomer)) {
                JOptionPane.showMessageDialog(this, "Sửa " + makh + " thành công!");
                if (parentPanel != null) {
                    parentPanel.refresh();
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!");
            }
        }
    }

    private Boolean checkEmpty() {
        String makh = txMakh.getText().trim();
        String tenkh = txTenkh.getText().trim();
        String diachi = txDiachi.getText().trim();
        String sdt = txSDT.getText().trim();

        if (makh.isEmpty())
            return showErrorTx(txMakh, "Mã khách hàng không được để trống");
        if (tenkh.isEmpty())
            return showErrorTx(txTenkh, "Tên khách hàng không được để trống");
        if (diachi.isEmpty())
            return showErrorTx(txDiachi, "Địa chỉ không được để trống");
        if (sdt.isEmpty())
            return showErrorTx(txSDT, "Số điện thoại không được để trống");

        return true;
    }

    private Boolean showErrorTx(JTextField tx, String errorInfo) {
        JOptionPane.showMessageDialog(this, errorInfo);
        tx.requestFocus();
        return false;
    }

    private ImageIcon loadImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Không thể tải hình ảnh: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        new AddButtonCustomer("Thêm", "", null);
    }
}