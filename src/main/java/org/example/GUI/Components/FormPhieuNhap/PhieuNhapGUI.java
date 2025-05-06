package org.example.GUI.Components.FormPhieuNhap;

import org.example.BUS.ProductBUS;
import org.example.BUS.ImportBUS;
import org.example.BUS.SupplierBUS;
import org.example.DTO.ImportDTO;
import org.example.DTO.SupplierDTO;
import org.example.GUI.FormDialog.FormChiTietPhieuNhap;
import org.example.GUI.Components.FormSupplier.FormNCC;
import org.example.GUI.Components.CustomTable; //được kế thừa để hiển thị bảng dữ liệu

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Properties;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PhieuNhapGUI extends JPanel {
    private JPanel pnTop, pnContent, pnBottom;
    private JButton btnXuatExcel, btnXemChiTiet, btnChonNCC;
    private JComboBox<String> cbxTimKiem;
    private JTextField txtTimKiem, txtTuNgay, txtDenNgay, txtTuTien, txtDenTien;
    private JButton btnChonTuNgay, btnChonDenNgay;
    private CustomTable tblPhieuNhap;
    private JTextField txtMaPhieuNhap, txtNhaCungCap, txtNhanVien, txtNgayLap;
    private JTextField txtGioLap, txtTongTien;
    private ProductBUS productBUS;
    private ImportBUS importSlipBUS;
    private SupplierBUS supplierBUS;
    private String selectedSupplierID;

    public PhieuNhapGUI() {
        setLayout(new BorderLayout());
        productBUS = new ProductBUS();
        importSlipBUS = new ImportBUS();
        supplierBUS = new SupplierBUS();
        initComponents();
        addControls();
        addEvents();
        refresh();
    }

    private void initComponents() {
        // khởi tạo 
        pnTop = new JPanel(new BorderLayout());
        pnContent = new JPanel(new BorderLayout());
        pnBottom = new JPanel(new BorderLayout());

        // panel tìm kiếm 
        JPanel pnSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] searchOptions = {"Tất cả", "Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp"};
        cbxTimKiem = new JComboBox<>(searchOptions);
        txtTimKiem = new JTextField(20);
        pnSearch.add(new JLabel("Tìm kiếm:"));
        pnSearch.add(cbxTimKiem);
        pnSearch.add(txtTimKiem);

        // Bảng lọc
        JPanel pnDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTuNgay = new JTextField(10);
        txtDenNgay = new JTextField(10);
        btnChonTuNgay = new JButton("...");
        btnChonDenNgay = new JButton("...");
        pnDate.add(new JLabel("Từ ngày:"));
        pnDate.add(txtTuNgay);
        pnDate.add(btnChonTuNgay);
        pnDate.add(new JLabel("Đến ngày:"));
        pnDate.add(txtDenNgay);
        pnDate.add(btnChonDenNgay);

        // Bảng lọc giá
        JPanel pnPrice = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTuTien = new JTextField(10);
        txtDenTien = new JTextField(10);
        pnPrice.add(new JLabel("Từ tiền:"));
        pnPrice.add(txtTuTien);
        pnPrice.add(new JLabel("Đến tiền:"));
        pnPrice.add(txtDenTien);

        // nút panel
        JPanel pnButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXuatExcel = new JButton("Xuất Excel");
        btnXemChiTiet = new JButton("Xem chi tiết");
        btnChonNCC = new JButton("Chọn nhà cung cấp");  // Thêm khởi tạo nút này
        pnButton.add(btnXuatExcel);
        pnButton.add(btnXemChiTiet);
        pnButton.add(btnChonNCC);  // Thêm nút vào panel

        JPanel pnFilter = new JPanel(new GridLayout(3, 1));
        pnFilter.add(pnSearch);
        pnFilter.add(pnDate);
        pnFilter.add(pnPrice);

        pnTop.add(pnFilter, BorderLayout.CENTER);
        pnTop.add(pnButton, BorderLayout.EAST);

        initTable();

        pnTop.setBorder(new TitledBorder("Tìm kiếm"));
        pnContent.setBorder(new TitledBorder("Danh sách phiếu nhập"));

        // Khởi tạo các JTextField
        txtMaPhieuNhap = new JTextField(20);
        txtNhaCungCap = new JTextField(20);
        txtNhanVien = new JTextField(20);
        txtNgayLap = new JTextField(20);
        txtGioLap = new JTextField(20);
        txtTongTien = new JTextField(20);

        add(pnTop, BorderLayout.NORTH);
        add(pnContent, BorderLayout.CENTER);
    }

    private void initTable() {
        String[] columns = {"STT", "Mã phiếu nhập", "Mã NCC", "Mã NV", "Ngày lập", "Giờ lập", "Tổng tiền"};
        tblPhieuNhap = new CustomTable();
        tblPhieuNhap.setModel(new DefaultTableModel(columns, 0));
        JScrollPane scrollPane = new JScrollPane(tblPhieuNhap);
        pnContent.add(scrollPane, BorderLayout.CENTER);
    }

    private void addControls() {
        txtMaPhieuNhap.setEnabled(false);
        txtNhaCungCap.setEnabled(false);
        txtNhanVien.setEnabled(false);
        txtNgayLap.setEnabled(false);
        txtGioLap.setEnabled(false);
        txtTongTien.setEnabled(false);
    }

    private void addEvents() {
        btnXuatExcel.addActionListener(e -> xuatExcel());
        btnXemChiTiet.addActionListener(e -> xemChiTiet());
        btnChonTuNgay.addActionListener(e -> showDatePicker(txtTuNgay));
        btnChonDenNgay.addActionListener(e -> showDatePicker(txtDenNgay));

        // chức năng tìm kiếm 
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchImportSlips();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchImportSlips();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchImportSlips();
            }
        });

        cbxTimKiem.addActionListener(e -> searchImportSlips());

        btnChonNCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chonNhaCungCap();
            }
        });
    }

    private void searchImportSlips() {
        String searchValue = txtTimKiem.getText().trim();
        String searchType = cbxTimKiem.getSelectedItem().toString();
        
        List<ImportDTO> searchResults;
        if (searchValue.isEmpty() && searchType.equals("Tất cả")) {
            searchResults = importSlipBUS.getAllImportSlips();
        } else {
            searchResults = importSlipBUS.searchImportSlips(searchValue, searchType);
        }

        updateTable(searchResults);
    }

    private void updateTable(List<ImportDTO> importSlips) {
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
        model.setRowCount(0);
        
        int stt = 1;
        for (ImportDTO slip : importSlips) {
            model.addRow(new Object[]{
                stt++,
                slip.getMaPN(),
                slip.getMaNCC(),
                slip.getMaNV(),
                new SimpleDateFormat("dd/MM/yyyy").format(slip.getNgayNhap()),
                new SimpleDateFormat("HH:mm:ss").format(slip.getGioNhap()),
                String.format("%,.0f VNĐ", slip.getTongTien())
            });
        }
    }

    private void xuatExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            if (importSlipBUS.exportToExcel(filePath)) {
                JOptionPane.showMessageDialog(this, "Xuất Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Xuất Excel thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xemChiTiet() {
        int selectedRow = tblPhieuNhap.getSelectedRow();
        if (selectedRow >= 0) {
            String maPhieuNhap = tblPhieuNhap.getValueAt(selectedRow, 1).toString();
            showChiTietPhieuNhap(maPhieuNhap);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xem chi tiết", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showDatePicker(JTextField textField) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Chọn ngày");
        dialog.setModal(true);
        dialog.setLayout(new FlowLayout());
        
        org.jdatepicker.impl.SqlDateModel model = new org.jdatepicker.impl.SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hôm nay");
        properties.put("text.month", "Tháng");
        properties.put("text.year", "Năm");
        
        org.jdatepicker.impl.JDatePanelImpl datePanel = new org.jdatepicker.impl.JDatePanelImpl(model, properties);
        org.jdatepicker.impl.JDatePickerImpl datePicker = new org.jdatepicker.impl.JDatePickerImpl(datePanel, 
            new org.jdatepicker.impl.DateComponentFormatter());
        
        JButton btnChon = new JButton("Chọn");
        btnChon.addActionListener(e -> {
            if (model.getValue() != null) {
                textField.setText(new SimpleDateFormat("dd/MM/yyyy").format(model.getValue()));
            }
            dialog.dispose();
        });
        
        dialog.add(datePicker);
        dialog.add(btnChon);
        dialog.pack();
        dialog.setLocationRelativeTo(textField);
        dialog.setVisible(true);
    }

    private void showChiTietPhieuNhap(String maPhieuNhap) {
        FormChiTietPhieuNhap dialog = new FormChiTietPhieuNhap(maPhieuNhap);
        dialog.setVisible(true);
    }

    private void chonNhaCungCap() {
        FormNCC formNCC = new FormNCC();
        formNCC.setSupplierSelectedListener(new FormNCC.SupplierSelectedListener() {
            @Override
            public void onSupplierSelected(SupplierDTO supplier) {
                if (supplier != null) {
                    selectedSupplierID = supplier.getMaNCC();
                    txtNhaCungCap.setText(supplier.getTenNCC());
                }
            }
        });
        formNCC.setVisible(true);
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
        model.setRowCount(0);
        
        List<ImportDTO> importSlips = importSlipBUS.getAllImportSlips();
        int stt = 1;
        for (ImportDTO slip : importSlips) {
            model.addRow(new Object[]{
                stt++,
                slip.getMaPN(),
                slip.getMaNCC(),
                slip.getMaNV(),
                new SimpleDateFormat("dd/MM/yyyy").format(slip.getNgayNhap()),
                new SimpleDateFormat("HH:mm:ss").format(slip.getGioNhap()),
                String.format("%,.0f VNĐ", slip.getTongTien())
            });
        }
    }
} 