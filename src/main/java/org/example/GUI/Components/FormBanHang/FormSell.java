
package org.example.GUI.Components.FormBanHang;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.example.BUS.InvoiceBUS;
import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.CustomerDTO;
import org.example.DTO.ProductDTO;
import org.example.DTO.UsersDTO;

public class FormSell extends JPanel implements FormChooseCustomer.CustomerSelectedListener {

    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");
    private List<ProductDTO> allProducts;

    public FormSell() {
        allProducts = new ArrayList<>();
        initComponents();
        loadProductsToTable();
        formatPriceColumn();
        addSearchListener();

        // Hiển thị mã hóa đơn lớn nhất
        InvoiceBUS invoiceBUS = new InvoiceBUS();
        String maxInvoiceId = invoiceBUS.getMaxInvoiceID();
        if (maxInvoiceId != null && !maxInvoiceId.isEmpty()) {
            String prefix = maxInvoiceId.substring(0, 2);
            int number = Integer.parseInt(maxInvoiceId.substring(2)) + 1;
            String newInvoiceId = String.format("%s%04d", prefix, number);
            txtMaHoaDon.setText(newInvoiceId);
        } else {
            txtMaHoaDon.setText("HD001");
        }
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
        txtMaHoaDon = new JTextField();
        textField10 = new JTextField();
        txtTongTien = new JTextField();
        txtKhachHang = new JTextField();
        txtNhanvien = new JTextField();
        txtNgayLap = new JTextField();
        txtGioLap = new JTextField();
        txtMaKhuyenMai = new JTextField();
        panel10 = new JPanel();
        panel11 = new JPanel();
        btnXoa = new JButton();
        btnSua = new JButton();
        btnReset2 = new JButton();
        btnChoose = new JButton();
        btnChooseKm = new JButton();
        textField2 = new JTextField();
        scrollPane1 = new JScrollPane();
        tableSell2 = new JTable();
        panel12 = new JPanel();
        panel13 = new JPanel();
        btnHuy = new JButton();
        btnTong = new JButton();
        comboBox = new JComboBox();

        java.awt.Font font = new java.awt.Font("Segoe UI", 0, 16);
        java.awt.Font fontHeader = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16);

        setLayout(new BorderLayout());

        panel1.setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);

        panel4.setMinimumSize(new Dimension(600, 132));
        panel4.setPreferredSize(new Dimension(650, 631));
        panel4.setLayout(new BorderLayout());

        panel2.setLayout(new FlowLayout());
        comboBox.setPreferredSize(new Dimension(110, 35));
        String items[] = { "Tất cả", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng" };
        for (String item : items)
            comboBox.addItem(item);
        panel2.add(comboBox);
        txtSearch.setPreferredSize(new Dimension(180, 60));
        txtSearch.setBorder(new TitledBorder(null, "Tìm kiếm:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel2.add(txtSearch);
        btnReset.setText("Làm mới");
        ImageIcon resetIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png");
        if (resetIcon != null)
            btnReset.setIcon(resetIcon);
        btnReset.setPreferredSize(new Dimension(120, 40));
        panel2.add(btnReset);
        panel4.add(panel2, BorderLayout.NORTH);

        tableSell.setFont(font);
        tableSell.getTableHeader().setFont(fontHeader);
        tableSell.setRowHeight(50);
        TableColumnModel columnModel = tableSell.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(150);
        }
        tableSell.setBorder(new BevelBorder(BevelBorder.LOWERED));
        tableSell.setModel(new DefaultTableModel(new Object[][] {}, getHeaderSell1()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tableSell.setFillsViewportHeight(true);
        tableSell.setSurrendersFocusOnKeystroke(true);
        tableSell.setShowVerticalLines(true);
        tableSell.setShowHorizontalLines(true);
        scrollPane2.setViewportView(tableSell);
        panel4.add(scrollPane2, BorderLayout.CENTER);

        tableSell.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableSell.getSelectedRow() != -1) {
                int selectedRow = tableSell.getSelectedRow();
                String maSP = (String) tableSell.getValueAt(selectedRow, 0);
                String maLSP = (String) tableSell.getValueAt(selectedRow, 1);
                String tenSP = (String) tableSell.getValueAt(selectedRow, 2);
                Float donGia = (Float) tableSell.getValueAt(selectedRow, 3);
                Integer soLuong = (Integer) tableSell.getValueAt(selectedRow, 4);
                ImageIcon hinhAnh = (ImageIcon) tableSell.getValueAt(selectedRow, 5);

                txtMaSP.setText(maSP);
                txtLoaiSP.setText(maLSP);
                txtTenSP.setText(tenSP);
                txtDonGia.setText(priceFormatter.format(donGia));
                txtSoLuong.setText(soLuong.toString());

                if (hinhAnh != null) {
                    Image scaledImage = hinhAnh.getImage().getScaledInstance(160, 100, Image.SCALE_SMOOTH);
                    image.setIcon(new ImageIcon(scaledImage));
                } else {
                    image.setIcon(null);
                    image.setText("Không có ảnh");
                }
            }
        });

        panel5.setPreferredSize(new Dimension(400, 200));
        panel5.setLayout(new BorderLayout());
        panel6.setPreferredSize(new Dimension(200, 40));
        panel6.setAutoscrolls(true);
        panel6.setBorder(new EtchedBorder());
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 7));
        txtMaSP.setPreferredSize(new Dimension(140, 60));
        txtMaSP.setBorder(new TitledBorder(null, "Mã sản phẩm:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        txtMaSP.setEditable(false);
        panel6.add(txtMaSP);
        txtLoaiSP.setPreferredSize(new Dimension(140, 60));
        txtLoaiSP.setBorder(new TitledBorder(null, "Loại sản phẩm:", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        txtLoaiSP.setEditable(false);
        panel6.add(txtLoaiSP);
        txtTenSP.setPreferredSize(new Dimension(140, 60));
        txtTenSP.setBorder(new TitledBorder(null, "Tên sản phẩm", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        txtTenSP.setEditable(false);
        panel6.add(txtTenSP);
        txtDonGia.setPreferredSize(new Dimension(140, 60));
        txtDonGia.setBorder(new TitledBorder(null, "Đơn giá:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        txtDonGia.setEditable(false);
        panel6.add(txtDonGia);
        txtSoLuong.setPreferredSize(new Dimension(140, 60));
        txtSoLuong.setBorder(new TitledBorder(null, "Số lượng", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        txtSoLuong.setText("1");
        txtSoLuong.setEditable(true);
        panel6.add(txtSoLuong);
        panel5.add(panel6, BorderLayout.CENTER);
        image.setPreferredSize(new Dimension(160, 100));
        image.setForeground(Color.black);
        image.setFocusTraversalPolicyProvider(true);
        image.setBackground(Color.black);
        image.setBorder(LineBorder.createBlackLineBorder());
        panel5.add(image, BorderLayout.WEST);
        btnAdd.setText("Thêm");
        ImageIcon addIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png");
        if (addIcon != null)
            btnAdd.setIcon(addIcon);
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> addProductToInvoice());
        panel5.add(btnAdd, BorderLayout.SOUTH);
        panel4.add(panel5, BorderLayout.SOUTH);
        add(panel4, BorderLayout.WEST);

        panel7.setLayout(new BorderLayout());
        add(panel7, BorderLayout.EAST);

        panel8.setPreferredSize(new Dimension(550, 526));
        panel8.setLayout(new BorderLayout());
        panel9.setPreferredSize(new Dimension(800, 250)); // Tăng kích thước để chứa tất cả các trường
        panel9.setAutoscrolls(true);
        panel9.setLayout(new FlowLayout(FlowLayout.LEFT, 9, 9));
        txtMaHoaDon.setPreferredSize(new Dimension(150, 55));
        txtMaHoaDon.setBorder(new TitledBorder(null, "Mã hóa đơn", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel9.add(txtMaHoaDon);
        txtMaHoaDon.setEditable(false);
        textField10.setPreferredSize(new Dimension(0, 55));
        panel9.add(textField10);
        txtTongTien.setPreferredSize(new Dimension(500, 55));
        txtTongTien.setBorder(new TitledBorder(null, "Tổng tiền (triệu VND)", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel9.add(txtTongTien);
        txtTongTien.setEditable(false);
        txtKhachHang.setPreferredSize(new Dimension(150, 55));
        txtKhachHang.setBorder(new TitledBorder(null, "Khách hàng", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel9.add(txtKhachHang);
        txtKhachHang.setEditable(false);
        ImageIcon chooseIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_user_30px.png");
        if (chooseIcon != null)
            btnChoose.setIcon(chooseIcon);
        btnChoose.setPreferredSize(new Dimension(50, 50));
        panel9.add(btnChoose);
        txtNhanvien.setPreferredSize(new Dimension(150, 55));
        txtNhanvien.setBorder(new TitledBorder(null, "Nhân viên", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel9.add(txtNhanvien);
        txtNhanvien.setEditable(false);
        txtNgayLap.setPreferredSize(new Dimension(150, 55));
        txtNgayLap.setBorder(new TitledBorder(null, "Ngày lập", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel9.add(txtNgayLap);
        txtGioLap.setPreferredSize(new Dimension(150, 55));
        txtGioLap.setBorder(new TitledBorder(null, "Giờ lập", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null,
                Color.black));
        panel9.add(txtGioLap);
        txtMaKhuyenMai.setPreferredSize(new Dimension(150, 55));
        txtMaKhuyenMai.setBorder(new TitledBorder(null, "Mã khuyến mãi", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel9.add(txtMaKhuyenMai);
        btnChooseKm.setText("");
        ImageIcon chooseKmIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_gift_30px.png");
        if (chooseKmIcon != null)
            btnChooseKm.setIcon(chooseKmIcon);
        btnChooseKm.setPreferredSize(new Dimension(50, 50));
        panel9.add(btnChooseKm);
        panel8.add(panel9, BorderLayout.NORTH);

        panel10.setLayout(new BorderLayout());
        panel11.setLayout(new FlowLayout());
        btnXoa.setText("Xóa");
        ImageIcon deleteIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_delete_forever_30px_1.png");
        if (deleteIcon != null)
            btnXoa.setIcon(deleteIcon);
        panel11.add(btnXoa);
        btnSua.setText("Sửa");
        ImageIcon editIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_support_30px.png");
        if (editIcon != null)
            btnSua.setIcon(editIcon);
        panel11.add(btnSua);
        btnReset2.setText("Làm mới");
        ImageIcon reset2Icon = loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png");
        if (reset2Icon != null)
            btnReset2.setIcon(reset2Icon);
        panel11.add(btnReset2);
        textField2.setPreferredSize(new Dimension(360, 40));
        textField2.setBorder(new TitledBorder(null, "Tìm kiếm:", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                null, Color.black));
        panel11.add(textField2);
        panel10.add(panel11, BorderLayout.PAGE_END);
        tableSell2.setFont(font);
        tableSell2.getTableHeader().setFont(fontHeader);
        tableSell2.setRowHeight(30);
        TableColumnModel columnModel2 = tableSell2.getColumnModel();
        for (int i = 0; i < columnModel2.getColumnCount(); i++) {
            columnModel2.getColumn(i).setPreferredWidth(150);
        }
        tableSell2.setBorder(new BevelBorder(BevelBorder.LOWERED));
        tableSell2.setModel(new DefaultTableModel(new Object[][] {}, getHeaderSell2()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollPane1.setViewportView(tableSell2);
        panel10.add(scrollPane1, BorderLayout.CENTER);
        panel8.add(panel10, BorderLayout.CENTER);

        panel12.setLayout(new FlowLayout());
        panel8.add(panel12, BorderLayout.WEST);

        panel13.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnHuy.setText("Hủy");
        ImageIcon cancelIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_cancel_30px_1.png");
        if (cancelIcon != null)
            btnHuy.setIcon(cancelIcon);
        panel13.add(btnHuy);
        btnTong.setText("Thanh toán");
        ImageIcon paymentIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_us_dollar_30px.png");
        if (paymentIcon != null)
            btnTong.setIcon(paymentIcon);
        btnTong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel13.add(btnTong);
        panel8.add(panel13, BorderLayout.SOUTH);
        add(panel8, BorderLayout.CENTER);

        // Sự kiện nút chọn khách hàng
        btnChoose.addActionListener(e -> {
            JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chọn Khách Hàng",
                    Dialog.ModalityType.APPLICATION_MODAL);
            FormChooseCustomer chooseCustomerForm = new FormChooseCustomer();
            chooseCustomerForm.setCustomerSelectedListener(this);
            dialog.add(chooseCustomerForm);
            dialog.setSize(800, 600);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        // Gán ngày và giờ hiện tại
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String currentDate = now.format(dateFormatter);
        String currentTime = now.format(timeFormatter);
        txtNgayLap.setText(currentDate);
        txtGioLap.setText(currentTime);
    }

    private void addProductToInvoice() {
        int selectedRow = tableSell.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm từ danh sách!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maSP = txtMaSP.getText().trim();
        String tenSP = txtTenSP.getText().trim();
        String donGiaStr = txtDonGia.getText().trim().replace(",", "");
        String soLuongStr = txtSoLuong.getText().trim();

        if (maSP.isEmpty() || tenSP.isEmpty() || donGiaStr.isEmpty() || soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thông tin sản phẩm không đầy đủ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float donGia;
        int soLuong;
        try {
            donGia = Float.parseFloat(donGiaStr);
            soLuong = Integer.parseInt(soLuongStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá hoặc số lượng không hợp lệ!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int availableQuantity = (Integer) tableSell.getValueAt(selectedRow, 4);
        if (soLuong <= 0 || soLuong > availableQuantity) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ hoặc vượt quá tồn kho!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        float thanhTien = donGia * soLuong;

        DefaultTableModel model = (DefaultTableModel) tableSell2.getModel();
        int stt = model.getRowCount() + 1;

        model.addRow(new Object[] {
                stt,
                maSP,
                tenSP,
                donGia,
                soLuong,
                thanhTien
        });

        formatInvoiceTableColumns();

        updateTotalAmount();

        txtMaSP.setText("");
        txtLoaiSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("1");
        image.setIcon(null);
        image.setText("Không có ảnh");

        JOptionPane.showMessageDialog(this, "Thêm sản phẩm vào hóa đơn thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void formatInvoiceTableColumns() {
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
        TableColumn priceColumn = tableSell2.getColumnModel().getColumn(3);
        TableColumn totalColumn = tableSell2.getColumnModel().getColumn(5);
        priceColumn.setCellRenderer(renderer);
        totalColumn.setCellRenderer(renderer);
    }

    private void updateTotalAmount() {
        DefaultTableModel model = (DefaultTableModel) tableSell2.getModel();
        float total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            float thanhTien = (Float) model.getValueAt(i, 5);
            total += thanhTien;
        }
        txtTongTien.setText(priceFormatter.format(total));
    }

    @Override
    public void onCustomerSelected(CustomerDTO customer) {
        if (customer != null) {
            txtKhachHang.setText(customer.getTenKH() + " - " + customer.getMaKH());
            System.out.println("Selected customer: " + customer.getMaKH());
        } else {
            txtKhachHang.setText("");
            System.out.println("No customer selected");
        }
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

    public String[] getHeaderSell1() {
        return new String[] { "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng", "Hình ảnh", "Trạng thái" };
    }

    public String[] getHeaderSell2() {
        return new String[] { "STT", "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền" };
    }

    private void loadProductsToTable() {
        Connection conn = UtilsJDBC.getConnectDB();
        allProducts.clear();
        String query = "SELECT MaSP, MaLSP, TenSP, DonGia, SoLuong, HinhAnh, TrangThai FROM product";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setMaSP(rs.getString("MaSP"));
                product.setMaLSP(rs.getString("MaLSP"));
                product.setTenSP(rs.getString("TenSP"));
                product.setDonGia(rs.getFloat("DonGia"));
                product.setSoLuong(rs.getInt("SoLuong"));
                product.setHinhAnh(rs.getString("HinhAnh"));
                product.setTrangthai(rs.getInt("TrangThai"));
                if (product.getSoLuong() > 0) {
                    allProducts.add(product);
                }
            }
            updateTable(allProducts);
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy dữ liệu sản phẩm: " + e.getMessage());
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    private void updateTable(List<ProductDTO> products) {
        String[] headers = getHeaderSell1();
        DefaultTableModel model = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String basePath = "/org/example/GUI/resources/imageTopic/";
        for (ProductDTO product : products) {
            ImageIcon imageIcon = null;
            String imagePath = basePath + product.getHinhAnh();
            java.net.URL imgURL = getClass().getResource(imagePath);
            if (imgURL != null) {
                imageIcon = new ImageIcon(imgURL);
                Image img = imageIcon.getImage();
                Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImg);
            } else {
                System.err.println("Không thể tải hình ảnh: " + imagePath);
            }
            Object[] row = {
                    product.getMaSP(),
                    product.getMaLSP(),
                    product.getTenSP(),
                    product.getDonGia(),
                    product.getSoLuong(),
                    imageIcon,
                    product.getTrangthai() == 1 ? "Hoạt động" : "Ngừng bán"
            };
            model.addRow(row);
        }
        tableSell.setModel(model);
        tableSell.getColumn("Hình ảnh").setCellRenderer(new ImageRenderer());
        formatPriceColumn();
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
        TableColumn priceColumn = tableSell.getColumnModel().getColumn(3);
        priceColumn.setCellRenderer(renderer);
    }

    class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
                setText(null);
            } else {
                setIcon(null);
                setText(value != null ? value.toString() : "Không có ảnh");
            }
            return this;
        }
    }

    private void addSearchListener() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchProducts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchProducts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchProducts();
            }
        });

        comboBox.addActionListener(e -> searchProducts());
    }

    private void searchProducts() {
        String searchText = txtSearch.getText().trim().toLowerCase();
        String searchType = comboBox.getSelectedItem().toString();
        List<ProductDTO> filteredProducts = new ArrayList<>();

        if (searchText.isEmpty() && searchType.equals("Tất cả")) {
            filteredProducts.addAll(allProducts);
        } else {
            for (ProductDTO product : allProducts) {
                boolean matches = false;
                switch (searchType) {
                    case "Tất cả":
                        matches = product.getMaSP().toLowerCase().contains(searchText) ||
                                product.getMaLSP().toLowerCase().contains(searchText) ||
                                product.getTenSP().toLowerCase().contains(searchText) ||
                                String.valueOf(product.getDonGia()).contains(searchText) ||
                                String.valueOf(product.getSoLuong()).contains(searchText);
                        break;
                    case "Mã sản phẩm":
                        matches = product.getMaSP().toLowerCase().contains(searchText);
                        break;
                    case "Mã loại":
                        matches = product.getMaLSP().toLowerCase().contains(searchText);
                        break;
                    case "Tên":
                        matches = product.getTenSP().toLowerCase().contains(searchText);
                        break;
                    case "Đơn giá":
                        matches = String.valueOf(product.getDonGia()).contains(searchText);
                        break;
                    case "Số lượng":
                        matches = String.valueOf(product.getSoLuong()).contains(searchText);
                        break;
                }
                if (matches) {
                    filteredProducts.add(product);
                }
            }
        }

        updateTable(filteredProducts);
    }

    private JPanel panel1, panel4, panel2, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12, panel13;
    private JTextField txtSearch, txtMaSP, txtLoaiSP, txtTenSP, txtDonGia, txtSoLuong, txtMaHoaDon, textField10,
            txtTongTien, txtKhachHang, txtNhanvien, txtNgayLap, txtGioLap, txtMaKhuyenMai, textField2;
    private JButton btnReset, btnAdd, btnXoa, btnSua, btnReset2, btnChoose, btnChooseKm, btnHuy, btnTong;
    private JScrollPane scrollPane2, scrollPane1;
    private JTable tableSell, tableSell2;
    private JLabel image;
    private JComboBox comboBox;
}