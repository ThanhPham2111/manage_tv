package org.example.GUI.Components.FormNhapHang;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.mysql.cj.exceptions.ConnectionIsClosedException;
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

import org.example.BUS.ImportBUS;
import org.example.BUS.InventoryBUS;
import org.example.BUS.ProductBUS;
import org.example.BUS.EmployeeBUS;
import org.example.BUS.SupplierBUS;
import org.example.BUS.TypeProductBUS;
import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.CustomerDTO;
import org.example.DTO.ImportDTO;
import org.example.DTO.ImportDetailDTO;
import org.example.DTO.ProductDTO;
import org.example.DTO.EmployeeDTO;
import org.example.DTO.SessionManager;
import org.example.DTO.TypeProduct;
import org.example.DTO.UsersDTO;
import org.example.DTO.InventoryDTO;
import org.example.DTO.SupplierDTO;

public class FormNhapHang extends JPanel {
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");
    private ArrayList<ProductDTO> allProducts;
    private ProductBUS productBUS;
    private TypeProductBUS typeProductBUS;
    private ImportBUS importBUS;
    private EmployeeBUS employeeBUS;
    private SupplierBUS supplierBUS;
    private InventoryBUS inventoryBUS;

    private JPanel panel1, panel4, panel2, panel5, panel6, panel7, panel8, panel9, panel10, panel11, panel12, panel13;
    private JTextField txtSearch, txtMaSP, txtLoaiSP, txtTenSP, txtDonGia, txtSoLuong, txtMaPhieuNhap, txtTongTien,
            txtNhaCungCap, txtNgayLap, txtGioLap;
    private JButton btnReset, btnAdd, btnXoa, btnReset2, btnChoose, btnTong;
    private JScrollPane scrollPane2, scrollPane1;
    private JTable tableSell, tableSell2;
    private JLabel image;
    private JComboBox<String> comboBox;

    public FormNhapHang() {
        allProducts = new ArrayList<>();
        productBUS = new ProductBUS();
        typeProductBUS = new TypeProductBUS();
        importBUS = new ImportBUS();
        employeeBUS = new EmployeeBUS();
        supplierBUS = new SupplierBUS();
        inventoryBUS = new InventoryBUS();
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
        txtMaPhieuNhap = new JTextField();
        txtTongTien = new JTextField();
        txtNhaCungCap = new JTextField();
        txtNgayLap = new JTextField();
        txtGioLap = new JTextField();
        panel10 = new JPanel();
        panel11 = new JPanel();
        btnXoa = new JButton();
        btnReset2 = new JButton();
        btnChoose = new JButton();
        scrollPane1 = new JScrollPane();
        tableSell2 = new JTable();
        panel12 = new JPanel();
        panel13 = new JPanel();
        btnTong = new JButton();
        comboBox = new JComboBox<>();

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
        txtSearch.setBorder(new TitledBorder(null, "Tìm kiếm:", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel2.add(txtSearch);

        btnReset.setText("Làm mới");
        btnReset.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png"));
        btnReset.setPreferredSize(new Dimension(120, 40));
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            comboBox.setSelectedItem("Tất cả");
            setDataTable(productBUS.getList());
        });
        panel2.add(btnReset);
        panel4.add(panel2, BorderLayout.NORTH);

        tableSell.setFont(font);
        tableSell.getTableHeader().setFont(fontHeader);
        tableSell.setRowHeight(30);
        tableSell.setBorder(new BevelBorder(BevelBorder.LOWERED));
        tableSell.setModel(new DefaultTableModel(
                new Object[][] { { null, null, null, null, null } },
                new String[] { "STT", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng" }));
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

        panel6.setPreferredSize(new Dimension(150, 40));
        panel6.setAutoscrolls(true);
        panel6.setBorder(new EtchedBorder());
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 7));

        txtMaSP.setPreferredSize(new Dimension(140, 60));
        txtMaSP.setBorder(new TitledBorder(null, "Mã sản phẩm:", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel6.add(txtMaSP);

        txtLoaiSP.setPreferredSize(new Dimension(140, 60));
        txtLoaiSP.setBorder(new TitledBorder(null, "Loại sản phẩm:", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel6.add(txtLoaiSP);

        txtTenSP.setPreferredSize(new Dimension(140, 60));
        txtTenSP.setBorder(new TitledBorder(null, "Tên sản phẩm", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel6.add(txtTenSP);

        txtDonGia.setPreferredSize(new Dimension(140, 60));
        txtDonGia.setBorder(new TitledBorder(null, "Đơn giá:", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel6.add(txtDonGia);

        txtSoLuong.setPreferredSize(new Dimension(140, 60));
        txtSoLuong.setBorder(new TitledBorder(null, "Số lượng", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        panel6.add(txtSoLuong);
        panel5.add(panel6, BorderLayout.CENTER);

        image.setPreferredSize(new Dimension(160, 100));
        image.setForeground(Color.black);
        image.setBackground(Color.black);
        image.setBorder(LineBorder.createBlackLineBorder());
        panel5.add(image, BorderLayout.WEST);

        btnAdd.setText("Thêm");
        btnAdd.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png"));
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

        panel9.setPreferredSize(new Dimension(800, 250));
        panel9.setAutoscrolls(true);
        panel9.setLayout(new FlowLayout(FlowLayout.CENTER, 9, 9));

        txtMaPhieuNhap.setPreferredSize(new Dimension(200, 55));
        txtMaPhieuNhap.setBorder(new TitledBorder(null, "Mã phiếu nhập", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        txtMaPhieuNhap.setText(importBUS.getNextImportID());
        txtMaPhieuNhap.setEditable(false);
        panel9.add(txtMaPhieuNhap);

        txtTongTien.setPreferredSize(new Dimension(200, 55));
        txtTongTien.setBorder(new TitledBorder(null, "Tổng tiền (triệu VND)", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        txtTongTien.setEnabled(false);
        panel9.add(txtTongTien);

        txtNhaCungCap.setPreferredSize(new Dimension(200, 55));
        txtNhaCungCap.setBorder(new TitledBorder(null, "Nhà cung cấp", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        txtNhaCungCap.setEnabled(false);
        panel9.add(txtNhaCungCap);

        btnChoose.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_company_30px.png"));
        btnChoose.setPreferredSize(new Dimension(50, 50));
        btnChoose.addActionListener(e -> {
            JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Chọn Nhà Cung Cấp",
                    Dialog.ModalityType.APPLICATION_MODAL);
            FormChooseSupplier chooseSupplierForm = new FormChooseSupplier();
            chooseSupplierForm.setSupplierSelectedListener(supplier -> {
                txtNhaCungCap.setText(supplier.getTenNCC() + " - " + supplier.getMaNCC());
            });
            dialog.add(chooseSupplierForm);
            dialog.setSize(1000, 800);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        panel9.add(btnChoose);

        txtNgayLap.setPreferredSize(new Dimension(200, 55));
        txtNgayLap.setBorder(new TitledBorder(null, "Ngày lập", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        txtNgayLap.setText(currentDate);
        txtNgayLap.setEnabled(false);
        panel9.add(txtNgayLap);

        txtGioLap.setPreferredSize(new Dimension(200, 55));
        txtGioLap.setBorder(new TitledBorder(null, "Giờ lập", TitledBorder.LEADING,
                TitledBorder.DEFAULT_POSITION, null, Color.black));
        txtGioLap.setText(currentTime);
        txtGioLap.setEnabled(false);
        panel9.add(txtGioLap);

        panel8.add(panel9, BorderLayout.NORTH);

        panel10.setLayout(new BorderLayout());
        panel11.setLayout(new FlowLayout());

        btnXoa.setText("Xóa");
        btnXoa.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_delete_forever_30px_1.png"));
        btnXoa.addActionListener(e -> {
            int selectedRow = tableSell2.getSelectedRow();
            if (selectedRow != -1) {
                allProducts.remove(selectedRow);
                setDataToTableImportDetails(allProducts);
                updateTotalPrice();
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa!");
            }
        });
        panel11.add(btnXoa);

        btnReset2.setText("Làm mới");
        btnReset2.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png"));
        btnReset2.addActionListener(e -> {
            allProducts.clear();
            setDataToTableImportDetails(allProducts);
            updateTotalPrice();
        });
        panel11.add(btnReset2);
        panel10.add(panel11, BorderLayout.PAGE_END);

        tableSell2.setFont(font);
        tableSell2.getTableHeader().setFont(fontHeader);
        tableSell2.setRowHeight(30);
        tableSell.setBorder(new BevelBorder(BevelBorder.LOWERED));
        tableSell2.setModel(new DefaultTableModel(
                new Object[][] { { null, null, null, null, null, null } },
                new String[] { "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền" }));
        scrollPane1.setViewportView(tableSell2);
        panel10.add(scrollPane1, BorderLayout.CENTER);
        panel8.add(panel10, BorderLayout.CENTER);

        panel12.setLayout(new FlowLayout());
        panel8.add(panel12, BorderLayout.WEST);

        panel13.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnTong.setText("Nhập hàng");
        btnTong.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_us_dollar_30px.png"));
        btnTong.addActionListener(e -> {
            if (allProducts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào phiếu nhập!");
                return;
            }

            if (txtNhaCungCap.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!");
                return;
            }

            try {
                // Bắt đầu transaction
                Connection conn = UtilsJDBC.getConnectDB();
                conn.setAutoCommit(false);

                try {
                    // Tạo phiếu nhập mới
                    ImportDTO importSlip = new ImportDTO();
                    importSlip.setMaPN(txtMaPhieuNhap.getText());

                    // Lấy mã nhà cung cấp
                    String nccText = txtNhaCungCap.getText();
                    String maNCC = nccText.contains(" - ") ? nccText.split(" - ")[1] : nccText;
                    importSlip.setMaNCC(maNCC);

                    // Lấy thông tin nhân viên từ SessionManager
                    UsersDTO currentUser = SessionManager.getInstance().getCurrentUser();
                    if (currentUser != null && currentUser.getMaNV() != null) {
                        importSlip.setMaNV(currentUser.getMaNV());
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên!");
                        return;
                    }

                    importSlip.setNgayNhap(
                            java.sql.Date
                                    .valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                    importSlip.setGioNhap(
                            java.sql.Time.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
                    importSlip.setTongTien(Float.parseFloat(txtTongTien.getText().replace(",", "")));

                    // Thêm phiếu nhập vào database
                    String sqlImport = "INSERT INTO import (MaPN, MaNCC, MaNV, NgayNhap, GioNhap, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pst = conn.prepareStatement(sqlImport)) {
                        pst.setString(1, importSlip.getMaPN());
                        pst.setString(2, importSlip.getMaNCC());
                        pst.setString(3, importSlip.getMaNV());
                        pst.setDate(4, importSlip.getNgayNhap());
                        pst.setTime(5, importSlip.getGioNhap());
                        pst.setFloat(6, importSlip.getTongTien());

                        if (pst.executeUpdate() > 0) {
                            // Thêm chi tiết phiếu nhập và cập nhật số lượng sản phẩm
                            boolean allDetailsAdded = true;
                            String sqlDetail = "INSERT INTO import_details (MaPN, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
                            String sqlUpdateProduct = "UPDATE product SET SoLuong = SoLuong - ? WHERE MaSP = ?";
                            String sqlUpdateInventory = "UPDATE tonkho SET SoLuong = SoLuong + ? WHERE MaSP = ?";
                            String sqlInsertInventory = "INSERT INTO tonkho (MaSP, MaLSP, TenSP, DonGia, HinhAnh, TrangThai, SoLuong) VALUES (?, ?, ?, ?, ?, ?, ?)";

                            try (PreparedStatement pstDetail = conn.prepareStatement(sqlDetail);
                                    PreparedStatement pstUpdateProduct = conn.prepareStatement(sqlUpdateProduct);
                                    PreparedStatement pstUpdateInventory = conn.prepareStatement(sqlUpdateInventory);
                                    PreparedStatement pstInsertInventory = conn.prepareStatement(sqlInsertInventory)) {

                                for (ProductDTO detail : allProducts) {
                                    // Thêm chi tiết phiếu nhập
                                    pstDetail.setString(1, txtMaPhieuNhap.getText());
                                    pstDetail.setString(2, detail.getMaSP());
                                    pstDetail.setInt(3, detail.getSoLuong());
                                    pstDetail.setFloat(4, detail.getDonGia());

                                    if (pstDetail.executeUpdate() <= 0) {
                                        allDetailsAdded = false;
                                        break;
                                    }

                                    // Cập nhật số lượng trong bảng product
                                    pstUpdateProduct.setInt(1, detail.getSoLuong());
                                    pstUpdateProduct.setString(2, detail.getMaSP());

                                    if (pstUpdateProduct.executeUpdate() <= 0) {
                                        allDetailsAdded = false;
                                        break;
                                    }

                                    // Kiểm tra và cập nhật số lượng trong bảng tonkho
                                    pstUpdateInventory.setInt(1, detail.getSoLuong());
                                    pstUpdateInventory.setString(2, detail.getMaSP());

                                    if (pstUpdateInventory.executeUpdate() <= 0) {
                                        // Nếu không có trong bảng tonkho, thêm mới
                                        pstInsertInventory.setString(1, detail.getMaSP());
                                        pstInsertInventory.setString(2, detail.getMaLSP());
                                        pstInsertInventory.setString(3, detail.getTenSP());
                                        pstInsertInventory.setFloat(4, detail.getDonGia());
                                        pstInsertInventory.setString(5, detail.getHinhAnh());
                                        pstInsertInventory.setInt(6, detail.getTrangthai());
                                        pstInsertInventory.setInt(7, detail.getSoLuong());

                                        if (pstInsertInventory.executeUpdate() <= 0) {
                                            allDetailsAdded = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (allDetailsAdded) {
                                // Commit transaction nếu tất cả thao tác thành công
                                conn.commit();

                                // Reset form
                                allProducts.clear();
                                setDataToTableImportDetails(allProducts);
                                updateTotalPrice();
                                txtMaPhieuNhap.setText(importBUS.getNextImportID());
                                txtNhaCungCap.setText("");

                                // Hiển thị thông báo thành công
                                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!");

                                // Đóng kết nối transaction
                                conn.setAutoCommit(true);
                                conn.close();

                                // Refresh lại danh sách sản phẩm trong EDT
                                SwingUtilities.invokeLater(() -> {
                                    try {
                                        productBUS.listSP();
                                        setDataTable(productBUS.getList());
                                    } catch (Exception ex) {
                                        // Bỏ qua lỗi kết nối đã đóng vì không ảnh hưởng đến chức năng
                                    }
                                });
                            } else {
                                // Rollback nếu có lỗi
                                conn.rollback();
                                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm chi tiết phiếu nhập!");
                            }
                        } else {
                            // Rollback nếu không thêm được phiếu nhập
                            conn.rollback();
                            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tạo phiếu nhập!");
                        }
                    }
                } catch (Exception ex) {
                    // Rollback nếu có exception
                    if (conn != null && !conn.isClosed()) {
                        conn.rollback();
                    }
                    throw ex;
                } finally {
                    // Đóng kết nối
                    if (conn != null && !conn.isClosed()) {
                        conn.setAutoCommit(true);
                        conn.close();
                    }
                }
            } catch (Exception ex) {
                // Chỉ hiển thị thông báo lỗi nếu không phải lỗi kết nối đã đóng
                if (!(ex instanceof SQLNonTransientConnectionException) &&
                        !(ex.getCause() instanceof ConnectionIsClosedException)) {
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage());
                }
            }
        });
        panel13.add(btnTong);
        panel8.add(panel13, BorderLayout.SOUTH);
        add(panel8, BorderLayout.CENTER);

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

    private ImageIcon loadImageIcon(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("Cảnh báo: Không thể tải hình ảnh tại " + path);
            return null;
        }
    }

    public void refresh() {
        productBUS.listSP();
        setDataTable(productBUS.getList());
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

    public void showInfo(String maSP, int soLuong) {
        if (maSP != null) {
            ProductDTO product = productBUS.getProductDTO(maSP);
            if (product != null) {
                int w = image.getWidth();
                int h = image.getHeight();
                ImageIcon lbImage = loadImageIcon("/org/example/GUI/resources/imageTopic/" + product.getHinhAnh());
                if (lbImage != null) {
                    Image imageScaled = lbImage.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
                    image.setIcon(new ImageIcon(imageScaled));
                } else {
                    image.setIcon(null);
                }
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

    public void addDetails(String maSP, int soLuong) {
        ProductDTO product = productBUS.getProductDTO(maSP);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm!");
            return;
        }
        if (soLuong > product.getSoLuong()) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá số lượng sản phẩm!");
            return;
        }
        boolean checkSoLuong = false;
        for (ProductDTO cthd : allProducts) {
            if (cthd.getMaSP().equals(maSP)) {
                int tongSoLuong = soLuong + cthd.getSoLuong();
                if (tongSoLuong > product.getSoLuong()) {
                    JOptionPane.showMessageDialog(this, "Số lượng vượt quá số lượng sản phẩm!");
                    return;
                }
                cthd.setSoLuong(tongSoLuong);
                checkSoLuong = true;
            }
        }
        if (!checkSoLuong) {
            ProductDTO importDetail = new ProductDTO(
                    product.getMaSP(),
                    product.getMaLSP(),
                    product.getTenSP(),
                    product.getDonGia(),
                    soLuong,
                    product.getHinhAnh(),
                    product.getTrangthai());
            allProducts.add(importDetail);
        }
        updateTotalPrice();
        setDataToTableImportDetails(allProducts);
    }

    public void updateTotalPrice() {
        float total = 0;
        for (ProductDTO detail : allProducts) {
            total += detail.getDonGia() * detail.getSoLuong();
        }
        txtTongTien.setText(priceFormatter.format(total));
    }

    public void setDataToTableImportDetails(ArrayList<ProductDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("STT");
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Số lượng");
        model.addColumn("Đơn giá");
        model.addColumn("Thành tiền");
        int stt = 1;
        for (ProductDTO cthd : data) {
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
}