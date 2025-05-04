
package org.example.GUI.Components.FormBanHang;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.example.BUS.InvoiceBUS;
import org.example.BUS.ProductBUS;
import org.example.BUS.EmployeeBUS;
import org.example.BUS.CustomerBUS;
import org.example.BUS.TypeProductBUS;
import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.CustomerDTO;
import org.example.DTO.InvoiceDTO;
import org.example.DTO.InvoiceDetailDTO;
import org.example.DTO.ProductDTO;
import org.example.DTO.EmployeeDTO;
import org.example.DTO.SessionManager;
import org.example.DTO.TypeProduct;
import org.example.DTO.UsersDTO;

public class FormSellDisable extends JPanel {

    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");
    private ArrayList<ProductDTO> allProducts;
    private ArrayList<InvoiceDetailDTO> dscthd;
    private ProductBUS productBUS;
    private TypeProductBUS typeProductBUS;
    private InvoiceBUS invoiceBUS;
    private EmployeeBUS employeeBUS;

    public FormSellDisable() {
        allProducts = new ArrayList<>();
        dscthd = new ArrayList<>();
        productBUS = new ProductBUS();
        typeProductBUS = new TypeProductBUS();
        invoiceBUS = new InvoiceBUS();
        employeeBUS = new EmployeeBUS();
        initComponents();
        refresh();
    }

    private void initComponents() {
        panel1 = new JPanel();
        panel4 = new JPanel();
        panel2 = new JPanel();
        txtSearch = new JTextField();
        btnReset = new JButton();
        scrollPane2 = new JScrollPane();
        tableSell = new JTable();
        panel5 = new JPanel();
        panel6 = new JPanel();
        txtMaSP = new JTextField();
        txtLoaiSP = new JTextField();
        txtTenSP = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();
        image = new JLabel();
        btnAdd = new JButton();
        panel7 = new JPanel();
        panel8 = new JPanel();
        panel9 = new JPanel();
        txtMaHD = new JTextField();
        textField10 = new JTextField();
        txtTongTien = new JTextField();
        txtKhachHang = new JTextField();
        txtNhanVien = new JTextField();
        txtNgayLap = new JTextField();
        txtGioLap = new JTextField();
        panel10 = new JPanel();
        panel11 = new JPanel();
        btnXoa = new JButton();
        btnSua = new JButton();
        btnReset2 = new JButton();
        btnChoose = new JButton();
        textField2 = new JTextField();
        scrollPane1 = new JScrollPane();
        tableSell2 = new JTable();
        panel12 = new JPanel();
        panel13 = new JPanel();
        btnHuy = new JButton();
        btnTong = new JButton();
        comboBox = new JComboBox();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentDate = now.format(dateFormatter);
        String currentTime = now.format(timeFormatter);

        Font font = new Font("Segoe UI", 0, 16);
        Font fontHeader = new Font("Segoe UI", Font.BOLD, 16);

        setLayout(new BorderLayout());

        panel1.setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);

        panel4.setMinimumSize(new Dimension(600, 132));
        panel4.setPreferredSize(new Dimension(650, 631));
        panel4.setLayout(new BorderLayout());

        panel2.setLayout(new FlowLayout());
        comboBox.setPreferredSize(new Dimension(110, 35));
        String items[] = { "Tất cả", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng" };
        for (String item : items) {
            comboBox.addItem(item);
        }
        panel2.add(comboBox);
        txtSearch.setPreferredSize(new Dimension(180, 60));
        txtSearch.setBorder(new TitledBorder(null, "Tìm kiếm:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel2.add(txtSearch);
        btnReset.setText("Làm mới");
        btnReset.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_data_backup_30px.png")));
        btnReset.setPreferredSize(new Dimension(120, 40));
        panel2.add(btnReset);
        panel4.add(panel2, BorderLayout.NORTH);

        tableSell.setFont(font);
        tableSell.getTableHeader().setFont(fontHeader);
        tableSell.setRowHeight(30);
        tableSell.setBorder(new BevelBorder(BevelBorder.LOWERED));
        tableSell
                .setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null } }, getHeaderSell1()));
        tableSell.setFillsViewportHeight(true);
        tableSell.setSurrendersFocusOnKeystroke(true);
        tableSell.setShowVerticalLines(true);
        tableSell.setShowHorizontalLines(true);
        tableSell.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableSell.getSelectedRow();
                if (selectedRow != -1) {
                    String maSP = (String) tableSell.getValueAt(selectedRow, 1);
                    showInfo(maSP, 1);
                }
            }
        });
        tableSell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tableSell.rowAtPoint(e.getPoint());
                    tableSell.setRowSelectionInterval(row, row);
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem detail = new JMenuItem("Xem chi tiết");
                    popup.add(detail);
                    detail.addActionListener(evt -> {
                        String maSP = (String) tableSell.getValueAt(row, 1);
                        ProductDTO selectedProduct = productBUS.getProductDTO(maSP);
                        if (selectedProduct != null) {
                            JOptionPane.showMessageDialog(null,
                                    "Mã sản phẩm: " + selectedProduct.getMaSP() +
                                            "\nTên sản phẩm: " + selectedProduct.getTenSP() +
                                            "\nLoại sản phẩm: " + selectedProduct.getMaLSP() +
                                            "\nĐơn giá: " + priceFormatter.format(selectedProduct.getDonGia()) +
                                            "\nSố lượng: " + selectedProduct.getSoLuong());
                        } else {
                            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm");
                        }
                    });
                    popup.show(tableSell, e.getX(), e.getY());
                }
            }
        });
        scrollPane2.setViewportView(tableSell);
        panel4.add(scrollPane2, BorderLayout.CENTER);

        panel5.setPreferredSize(new Dimension(400, 200));
        panel5.setLayout(new BorderLayout());
        panel6.setPreferredSize(new Dimension(200, 40));
        panel6.setAutoscrolls(true);
        panel6.setBorder(new EtchedBorder());
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 7));
        txtMaSP.setPreferredSize(new Dimension(140, 60));
        txtMaSP.setBorder(new TitledBorder(null, "Mã sản phẩm:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel6.add(txtMaSP);
        txtLoaiSP.setPreferredSize(new Dimension(140, 60));
        txtLoaiSP.setBorder(new TitledBorder(null, "Loại sản phẩm:", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel6.add(txtLoaiSP);
        txtTenSP.setPreferredSize(new Dimension(140, 60));
        txtTenSP.setBorder(new TitledBorder(null, "Tên sản phẩm", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel6.add(txtTenSP);
        txtDonGia.setPreferredSize(new Dimension(140, 60));
        txtDonGia.setBorder(new TitledBorder(null, "Đơn giá:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel6.add(txtDonGia);
        txtSoLuong.setPreferredSize(new Dimension(140, 60));
        txtSoLuong.setBorder(new TitledBorder(null, "Số lượng", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel6.add(txtSoLuong);
        panel5.add(panel6, BorderLayout.CENTER);
        image.setPreferredSize(new Dimension(160, 100));
        image.setForeground(Color.black);
        image.setFocusTraversalPolicyProvider(true);
        image.setBackground(Color.black);
        image.setBorder(LineBorder.createBlackLineBorder());
        panel5.add(image, BorderLayout.WEST);
        btnAdd.setText("Thêm");
        btnAdd.setIcon(new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_add_30px.png")));
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> {
            try {
                String maSP = txtMaSP.getText();
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                if (soLuong > 0) {
                    addDetails(maSP, soLuong);
                } else {
                    JOptionPane.showMessageDialog(txtSoLuong, "Số lượng phải là số dương!");
                    txtSoLuong.requestFocus();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(txtSoLuong, "Số lượng phải là số nguyên!");
                txtSoLuong.requestFocus();
            }
        });
        panel5.add(btnAdd, BorderLayout.SOUTH);
        panel4.add(panel5, BorderLayout.SOUTH);
        add(panel4, BorderLayout.WEST);

        panel7.setLayout(new BorderLayout());
        add(panel7, BorderLayout.EAST);

        panel8.setPreferredSize(new Dimension(550, 526));
        panel8.setLayout(new BorderLayout());
        panel9.setPreferredSize(new Dimension(800, 250)); // Tăng kích thước để chứa tất cả các trường
        panel9.setAutoscrolls(true);
        panel9.setLayout(new FlowLayout(FlowLayout.CENTER, 9, 9));
        txtMaHD.setPreferredSize(new Dimension(200, 55));
        txtMaHD.setBorder(new TitledBorder(null, "Mã hóa đơn", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        String nextInvoiceID = invoiceBUS.getNextInvoiceID();
        txtMaHD.setText(nextInvoiceID);
        panel9.add(txtMaHD);
        textField10.setPreferredSize(new Dimension(0, 55));
        panel9.add(textField10);
        txtTongTien.setPreferredSize(new Dimension(200, 55));
        txtTongTien.setBorder(new TitledBorder(null, "Tổng tiền (triệu VND)", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel9.add(txtTongTien);
        txtKhachHang.setPreferredSize(new Dimension(200, 55));
        txtKhachHang.setBorder(new TitledBorder(null, "Khách hàng", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel9.add(txtKhachHang);
        btnChoose.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_user_30px.png")));
        btnChoose.setPreferredSize(new Dimension(50, 50));
        btnChoose.addActionListener(e -> {
            JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chọn Khách Hàng",
                    Dialog.ModalityType.APPLICATION_MODAL);
            FormChooseCustomer chooseCustomerForm = new FormChooseCustomer();
            chooseCustomerForm.setCustomerSelectedListener(customer -> {
                txtKhachHang.setText(customer.getTenKH() + " - " + customer.getMaKH());
            });
            dialog.add(chooseCustomerForm);
            dialog.setSize(1000, 800);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        panel9.add(btnChoose);
        txtNhanVien.setPreferredSize(new Dimension(200, 55));
        txtNhanVien.setBorder(new TitledBorder(null, "Nhân viên", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        UsersDTO currentUser = SessionManager.getInstance().getCurrentUser();
        EmployeeDTO employee = null;
        if (currentUser != null && currentUser.getMaNV() != null) {
            employee = employeeBUS.getEmployeeByMaNV(currentUser.getMaNV());
        }
        if (employee != null) {
            txtNhanVien.setText(employee.getTenNV() + " - " + employee.getMaNV());
        } else {
            txtNhanVien.setText("Không tìm thấy nhân viên");
        }
        panel9.add(txtNhanVien);
        txtNgayLap.setPreferredSize(new Dimension(200, 55));
        txtNgayLap.setBorder(new TitledBorder(null, "Ngày lập", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        txtNgayLap.setText(currentDate);
        panel9.add(txtNgayLap);
        txtGioLap.setPreferredSize(new Dimension(200, 55));
        txtGioLap.setBorder(new TitledBorder(null, "Giờ lập", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null,
                Color.black));
        txtGioLap.setText(currentTime);
        panel9.add(txtGioLap);
        panel8.add(panel9, BorderLayout.NORTH);

        panel10.setLayout(new BorderLayout());
        panel11.setLayout(new FlowLayout());
        btnXoa.setText("Xóa");
        btnXoa.setIcon(new ImageIcon(
                getClass().getResource("/org/example/GUI/resources/images/icons8_delete_forever_30px_1.png")));
        btnXoa.addActionListener(e -> {
            int selectedRow = tableSell2.getSelectedRow();
            if (selectedRow != -1) {
                dscthd.remove(selectedRow);
                setDataToTableInvoiceDetails(dscthd);
                updateTotalPrice();
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa!");
            }
        });
        panel11.add(btnXoa);
        btnSua.setText("Sửa");
        btnSua.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_support_30px.png")));
        panel11.add(btnSua);
        btnReset2.setText("Làm mới");
        btnReset2.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_data_backup_30px.png")));
        btnReset2.addActionListener(e -> {
            dscthd.clear();
            setDataToTableInvoiceDetails(dscthd);
            updateTotalPrice();
        });
        panel11.add(btnReset2);
        textField2.setPreferredSize(new Dimension(360, 40));
        textField2.setBorder(new TitledBorder(null, "Tìm kiếm:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel11.add(textField2);
        panel10.add(panel11, BorderLayout.PAGE_END);
        tableSell2.setFont(font);
        tableSell2.getTableHeader().setFont(fontHeader);
        tableSell2.setRowHeight(30);
        tableSell2.setBorder(new BevelBorder(BevelBorder.LOWERED));
        tableSell2
                .setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null } }, getHeaderSell2()));
        scrollPane1.setViewportView(tableSell2);
        panel10.add(scrollPane1, BorderLayout.CENTER);
        panel8.add(panel10, BorderLayout.CENTER);

        panel12.setLayout(new FlowLayout());
        panel8.add(panel12, BorderLayout.WEST);

        panel13.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnHuy.setText("Hủy");
        btnHuy.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_cancel_30px_1.png")));
        panel13.add(btnHuy);
        btnTong.setText("Thanh toán");
        btnTong.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_us_dollar_30px.png")));
        btnTong.setEnabled(true);
        btnTong.addActionListener(e -> processPayment());
        panel13.add(btnTong);
        panel8.add(panel13, BorderLayout.SOUTH);
        add(panel8, BorderLayout.CENTER);

        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            comboBox.setSelectedItem("Tất cả");
            setDataTable(productBUS.getList());
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }

            private void performSearch() {
                String value = txtSearch.getText();
                ArrayList<ProductDTO> result = new ArrayList<>();
                String selectedField = (String) comboBox.getSelectedItem();
                for (ProductDTO product : productBUS.getList()) {
                    if (selectedField.equals("Tất cả") || productBUS.isMatched(product, selectedField, value)) {
                        result.add(product);
                    }
                }
                setDataTable(result);
            }
        });
    }

    private void processPayment() {
        if (dscthd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách sản phẩm trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtKhachHang.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maHD = txtMaHD.getText();
        String khachHangText = txtKhachHang.getText();
        String maKH = khachHangText.contains("-") ? khachHangText.split("-")[1].trim() : "";
        String nhanVienText = txtNhanVien.getText();
        String maNV = nhanVienText.contains("-") ? nhanVienText.split("-")[1].trim() : "";
        String ngayLapStr = txtNgayLap.getText();
        String gioLapStr = txtGioLap.getText();
        String tongTienStr = txtTongTien.getText().replace(",", "");

        Date ngayLap = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate = dateFormat.parse(ngayLapStr);
            ngayLap = new Date(parsedDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày lập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Time gioNhap = null;
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.util.Date parsedTime = timeFormat.parse(gioLapStr);
            gioNhap = new Time(parsedTime.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng giờ lập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double tongTien;
        try {
            tongTien = Double.parseDouble(tongTienStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tổng tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        InvoiceDTO invoice = new InvoiceDTO(maHD, maKH, maNV, ngayLap, gioNhap, tongTien);
        invoice.setInvoiceDetails(dscthd);

        try {
            // if (!invoiceBUS.addInvoice(invoice)) {
            //     throw new SQLException("Không thể thêm hóa đơn");
            // }

            invoiceBUS.addInvoice(invoice);

            dscthd.clear();
            setDataToTableInvoiceDetails(dscthd);
            updateTotalPrice();
            txtMaSP.setText("");
            txtLoaiSP.setText("");
            txtTenSP.setText("");
            txtDonGia.setText("");
            txtSoLuong.setText("");
            txtKhachHang.setText("");
            image.setIcon(null);
            refresh();

            String nextInvoiceID = invoiceBUS.getNextInvoiceID();
            txtMaHD.setText(nextInvoiceID);

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public String[] getHeaderSell1() {
        return new String[] { "STT", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng" };
    }

    public String[] getHeaderSell2() {
        return new String[] { "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" };
    }

    public void refresh() {
        productBUS.listSP();
        setDataTable(productBUS.getList());
    }

    public void addDetails(String maSP, int soLuong) {
        ProductDTO product = productBUS.getProductDTO(maSP);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
            return;
        }
        boolean checkSoLuong = false;
        for (InvoiceDetailDTO cthd : dscthd) {
            if (cthd.getMaSP().equals(maSP)) {
                int tongSoLuong = soLuong + cthd.getSoLuong();
                if (tongSoLuong > product.getSoLuong()) {
                    JOptionPane.showMessageDialog(this,
                            "Số lượng sản phẩm trong kho không đủ (" + product.getSoLuong() + ")");
                    return;
                } else {
                    cthd.setSoLuong(tongSoLuong);
                    checkSoLuong = true;
                }
            }
        }
        if (!checkSoLuong) {
            if (soLuong > product.getSoLuong()) {
                JOptionPane.showMessageDialog(this,
                        "Số lượng sản phẩm trong kho không đủ (" + product.getSoLuong() + ")");
                return;
            }
            InvoiceDetailDTO invoiceDetail = new InvoiceDetailDTO(txtMaHD.getText(), maSP, soLuong,
                    product.getDonGia());
            dscthd.add(invoiceDetail);
        }
        updateTotalPrice();
        setDataToTableInvoiceDetails(dscthd);
    }

    public void updateTotalPrice() {
        float total = 0;
        for (InvoiceDetailDTO detail : dscthd) {
            total += detail.getDonGia() * detail.getSoLuong();
        }
        txtTongTien.setText(priceFormatter.format(total));
    }

    public void showInfo(String maSP, int soLuong) {
        if (maSP != null) {
            ProductDTO product = productBUS.getProductDTO(maSP);
            if (product != null) {
                int w = image.getWidth();
                int h = image.getHeight();
                ImageIcon lbImage = new ImageIcon(
                        getClass().getResource("/org/example/GUI/resources/imageTopic/" + product.getHinhAnh()));
                Image imageScaled = lbImage.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                image.setIcon(new ImageIcon(imageScaled));
                TypeProduct typeProduct = typeProductBUS.getTypeProductDTO(product.getMaLSP());
                String tenLSP = (typeProduct != null) ? typeProduct.getTenLSP() : "Không tìm thấy";
                txtMaSP.setText(product.getMaSP());
                txtTenSP.setText(product.getTenSP());
                txtLoaiSP.setText(tenLSP + " - " + product.getMaLSP());
                txtDonGia.setText(priceFormatter.format(product.getDonGia()));
                txtSoLuong.setText(String.valueOf(soLuong));
            }
        }
    }

    private void setDataTable(ArrayList<ProductDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("STT");
        model.addColumn("Mã sản phẩm");
        model.addColumn("Mã loại");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Đơn giá");
        model.addColumn("Số lượng");
        int stt = 1;
        for (ProductDTO product : data) {
            model.addRow(new Object[] {
                    stt++,
                    product.getMaSP(),
                    product.getMaLSP(),
                    product.getTenSP(),
                    product.getDonGia(),
                    product.getSoLuong()
            });
        }
        tableSell.setModel(model);
        formatPriceColumn();
    }

    public void setDataToTableInvoiceDetails(ArrayList<InvoiceDetailDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("STT");
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Số lượng");
        model.addColumn("Đơn giá");
        model.addColumn("Thành tiền");
        int stt = 1;
        for (InvoiceDetailDTO cthd : data) {
            ProductDTO sp = productBUS.getProductDTO(cthd.getMaSP());
            String tensp = sp != null ? sp.getTenSP() : "Không tìm thấy";
            int soluong = cthd.getSoLuong();
            float dongia = cthd.getDonGia();
            float thanhtien = soluong * dongia;
            model.addRow(new Object[] {
                    stt++,
                    cthd.getMaSP(),
                    tensp,
                    soluong,
                    dongia,
                    thanhtien
            });
        }
        tableSell2.setModel(model);
        formatInvoiceTableColumns();
    }

    private void formatPriceColumn() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText(priceFormatter.format(((Number) value).floatValue()));
                } else {
                    setText((value == null) ? "" : value.toString());
                }
            }
        };
        TableColumn priceColumn = tableSell.getColumnModel().getColumn(4);
        priceColumn.setCellRenderer(renderer);
    }

    void formatInvoiceTableColumns() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText(priceFormatter.format(((Number) value).floatValue()));
                } else {
                    setText((value == null) ? "" : value.toString());
                }
            }
        };
        TableColumn priceColumn = tableSell2.getColumnModel().getColumn(4);
        TableColumn totalColumn = tableSell2.getColumnModel().getColumn(5);
        priceColumn.setCellRenderer(renderer);
        totalColumn.setCellRenderer(renderer);
    }

    public interface CustomerSelectedListener {
        void onCustomerSelected(CustomerDTO customer);
    }

    private JPanel panel1, panel4, panel2, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12, panel13;
    private JTextField txtSearch, txtMaSP, txtLoaiSP, txtTenSP, txtDonGia, txtSoLuong, txtMaHD, textField10,
            txtTongTien, txtKhachHang, txtNhanVien, txtNgayLap, txtGioLap, textField2;
    private JButton btnReset, btnAdd, btnXoa, btnSua, btnReset2, btnChoose, btnHuy, btnTong;
    private JScrollPane scrollPane2, scrollPane1;
    private JTable tableSell, tableSell2;
    private JLabel image;
    private JComboBox comboBox;
}