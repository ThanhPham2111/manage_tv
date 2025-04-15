package org.example.GUI.FormEmployee;

import org.example.BUS.EmployeeBUS;
import org.example.DTO.EmployeeDTO;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmployeeGUI extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtTimKiem;
    private JTable table;
    private EmployeeBUS employeeBUS = new EmployeeBUS();
    private DefaultTableModel model;
    private JButton btnView;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnAdd;
    private JButton btnNhap;
    private JButton btnXuat;
    private ArrayList<EmployeeDTO> list;
    private JDialog editDialog;
    private JDialog addDialog;
    private JComboBox<String> comboBox;
    private JButton btnLamMoi;
    private JComboBox<String> cbHienThi;

    public EmployeeGUI() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel up = new JPanel();
        up.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        btnView = new JButton("Xem");
        btnView.setPreferredSize(new Dimension(144, 43));
        btnView.setIcon(loadImageIcon("/org/example/GUI/resources/images/view.png"));
        up.add(btnView);

        btnView.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maNV = table.getValueAt(selectedRow, 0).toString();
                String tenNV = table.getValueAt(selectedRow, 1).toString();
                String ngaySinhStr = table.getValueAt(selectedRow, 2).toString();
                String diaChi = table.getValueAt(selectedRow, 3).toString();
                String soDienThoai = table.getValueAt(selectedRow, 4).toString();
                String trangThai = table.getValueAt(selectedRow, 5).toString();
                showDialogViewEmployee(maNV, tenNV, ngaySinhStr, diaChi, soDienThoai, trangThai);
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Vui lòng chọn một nhân viên để xem",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEdit = new JButton("Sửa");
        btnEdit.setPreferredSize(new Dimension(144, 43));
        btnEdit.setIcon(loadImageIcon("/org/example/GUI/resources/images/editing.png"));
        up.add(btnEdit);

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String maNV = table.getValueAt(selectedRow, 0).toString();
                String tenNV = table.getValueAt(selectedRow, 1).toString();
                String ngaySinhStr = table.getValueAt(selectedRow, 2).toString();
                String diaChi = table.getValueAt(selectedRow, 3).toString();
                String soDienThoai = table.getValueAt(selectedRow, 4).toString();
                String trangThai = table.getValueAt(selectedRow, 5).toString();

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date parsedDate = dateFormat.parse(ngaySinhStr);
                    java.sql.Date ngaySinhDate = new java.sql.Date(parsedDate.getTime());
                    showDialogEditEmployee(maNV, tenNV, ngaySinhDate, diaChi, soDienThoai, trangThai);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(EmployeeGUI.this, "Ngày sinh không hợp lệ", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Vui lòng chọn một nhân viên để sửa",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete = new JButton("Xóa");
        btnDelete.setPreferredSize(new Dimension(144, 43));
        btnDelete.setIcon(loadImageIcon("/org/example/GUI/resources/images/bin.png"));
        up.add(btnDelete);

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để xóa", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maNV = table.getValueAt(selectedRow, 0).toString();
            int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                employeeBUS.updateTrangThai(maNV, 1);
                refreshTable();
            }
        });

        btnAdd = new JButton("Thêm");
        btnAdd.setPreferredSize(new Dimension(144, 43));
        btnAdd.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnAdd.setIcon(loadImageIcon("/org/example/GUI/resources/images/plus.png"));
        up.add(btnAdd);

        btnAdd.addActionListener(e -> showDialogToAddEmployee());

        btnNhap = new JButton("Nhập Excel");
        btnNhap.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_ms_excel_30px.png"));
        btnNhap.setPreferredSize(new Dimension(144, 43));
        btnNhap.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnNhap.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");

            int userSelection = fileChooser.showOpenDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();
                try (FileInputStream inputStream = new FileInputStream(fileToOpen)) {
                    Workbook workbook = new XSSFWorkbook(inputStream);
                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.iterator();

                    if (rowIterator.hasNext()) {
                        rowIterator.next();
                    }

                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        String maNV = row.getCell(0).getStringCellValue();
                        String tenNV = row.getCell(1).getStringCellValue();
                        Date ngaySinh = Date.valueOf(row.getCell(2).getStringCellValue());
                        String diaChi = row.getCell(3).getStringCellValue();
                        String soDienThoai = row.getCell(4).getStringCellValue();
                        int trangThai = (int) row.getCell(5).getNumericCellValue();

                        EmployeeDTO employee = new EmployeeDTO(maNV, tenNV, ngaySinh, diaChi, soDienThoai, trangThai);
                        employeeBUS.add(employee);
                    }

                    refreshTable();
                    JOptionPane.showMessageDialog(null, "Nhập file thành công");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Nhập file không thành công");
                    ex.printStackTrace();
                }
            }
        });
        up.add(btnNhap);

        btnXuat = new JButton("Xuất Excel");
        btnXuat.setPreferredSize(new Dimension(144, 43));
        btnXuat.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_ms_excel_30px.png"));
        btnXuat.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12));
        btnXuat.addActionListener(e -> xuatexcel());
        up.add(btnXuat);

        txtTimKiem = new JTextField("");
        txtTimKiem.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 15));
        txtTimKiem.setHorizontalAlignment(SwingConstants.CENTER);
        txtTimKiem.setPreferredSize(new Dimension(180, 40));

        String[] options = { "MaNV", "TenNV", "SDT", "DiaChi" };
        comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(90, 30));
        comboBox.addActionListener(e -> {
            String option = (String) comboBox.getSelectedItem();
            txtTimKiem.setText(option);
            addFocusListenerToTextField(option);
        });

        JPanel timkiem = new JPanel();
        timkiem.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        String[] hienThiOptions = { "Đang hoạt động", "Đã xóa" };
        cbHienThi = new JComboBox<>(hienThiOptions);
        cbHienThi.setPreferredSize(new Dimension(130, 40));
        cbHienThi.addActionListener(e -> refreshTable());
        timkiem.add(cbHienThi);

        JPanel comTk = new JPanel();
        comTk.add(comboBox);
        comTk.add(txtTimKiem);
        comTk.setBorder(new TitledBorder("Tìm kiếm"));
        timkiem.add(comTk);

        txtTimKiem.addActionListener(e -> {
            String txt = txtTimKiem.getText();
            String option = (String) cbHienThi.getSelectedItem();
            int trangThai = option.equals("Đang hoạt động") ? 0 : 1;
            timKiem(txt, trangThai);
        });

        JPanel chucNang = new JPanel();
        chucNang.setLayout(new GridLayout(3, 1, 10, 0));
        chucNang.add(up);
        chucNang.add(timkiem);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(110, 43));
        btnLamMoi.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png"));
        btnLamMoi.addActionListener(e -> refreshTable());
        timkiem.add(btnLamMoi);

        table = new JTable();
        java.awt.Font font = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16);
        java.awt.Font fontHeader = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16);
        table.setFont(font);
        table.getTableHeader().setFont(fontHeader);
        table.setRowHeight(30);

        model = new DefaultTableModel();
        model.addColumn("Mã NV");
        model.addColumn("Tên NV");
        model.addColumn("Ngày sinh");
        model.addColumn("Địa chỉ");
        model.addColumn("SĐT");
        model.addColumn("Trạng thái");

        trangThai((String) cbHienThi.getSelectedItem());
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(740, 402));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String trangThai = table.getValueAt(row, 5).toString();
                        if (trangThai.equals("Đã xóa")) {
                            String maNV = table.getValueAt(row, 0).toString();
                            showConfirmationDialog(maNV);
                        }
                    }
                }
            }
        });

        add(chucNang, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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

    public JPanel getPanel() {
        return this;
    }

    public JPanel getPanelDisable() {
        disableButtons();
        return this;
    }

    private void showDialogToAddEmployee() {
        addDialog = new JDialog((Frame) null, "Thêm thông tin nhân viên mới", true);
        addDialog.setSize(450, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        JTextField txtMaNV = new JTextField();
        JLabel lblTenNV = new JLabel("Tên nhân viên:");
        JTextField txtTenNV = new JTextField();
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        JComboBox<Integer> dayComboBox = new JComboBox<>();
        JComboBox<Integer> monthComboBox = new JComboBox<>();
        JComboBox<Integer> yearComboBox = new JComboBox<>();

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new GridLayout(1, 3));
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 100; i <= currentYear; i++) {
            yearComboBox.addItem(i);
        }

        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        JTextField txtDiaChi = new JTextField();
        JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
        JTextField txtSoDienThoai = new JTextField();
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        JComboBox<String> cboTrangThai = new JComboBox<>(new String[] { "Đang hoạt động", "Đã xóa" });

        panel.add(lblMaNV);
        panel.add(txtMaNV);
        panel.add(lblTenNV);
        panel.add(txtTenNV);
        panel.add(lblNgaySinh);
        panel.add(datePanel);
        panel.add(lblDiaChi);
        panel.add(txtDiaChi);
        panel.add(lblSoDienThoai);
        panel.add(txtSoDienThoai);
        panel.add(lblTrangThai);
        panel.add(cboTrangThai);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOK.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png"));
        btnCancel.setIcon(loadImageIcon("/org/example/GUI/resources/images/icons8_cancel_30px_1.png"));

        btnOK.setEnabled(false);
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        btnOK.addActionListener(e -> {
            String maNV = txtMaNV.getText();
            String tenNV = txtTenNV.getText();
            int day = (int) dayComboBox.getSelectedItem();
            int month = (int) monthComboBox.getSelectedItem();
            int year = (int) yearComboBox.getSelectedItem();
            String ngaySinhStr = String.format("%d-%02d-%02d", year, month, day);
            java.sql.Date ngaySinh = java.sql.Date.valueOf(ngaySinhStr);
            String diaChi = txtDiaChi.getText();
            String soDienThoai = txtSoDienThoai.getText();
            String trangThai = doiTrangThai(cboTrangThai.getSelectedItem().toString());

            if (checkInfo("addDialog", maNV, soDienThoai, addDialog)) {
                EmployeeDTO employee = new EmployeeDTO(maNV, tenNV, ngaySinh, diaChi, soDienThoai,
                        Integer.parseInt(trangThai));
                employeeBUS.add(employee);
                refreshTable();
                addDialog.dispose();
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }

            private void checkFields() {
                boolean allFieldsFilled = !txtMaNV.getText().isEmpty() &&
                        !txtTenNV.getText().isEmpty() &&
                        !txtDiaChi.getText().isEmpty() &&
                        !txtSoDienThoai.getText().isEmpty();
                btnOK.setEnabled(allFieldsFilled);
            }
        };

        txtMaNV.getDocument().addDocumentListener(documentListener);
        txtTenNV.getDocument().addDocumentListener(documentListener);
        txtDiaChi.getDocument().addDocumentListener(documentListener);
        txtSoDienThoai.getDocument().addDocumentListener(documentListener);

        panel.add(buttonPanel);
        addDialog.getContentPane().add(panel);
        addDialog.setLocationRelativeTo(null);
        addDialog.setVisible(true);
    }

    private void showDialogEditEmployee(String maNV, String tenNV, Date ngaySinh, String diaChi, String soDienThoai,
            String trangThai) {
        editDialog = new JDialog((Frame) null, "Chỉnh sửa thông tin nhân viên", true);
        editDialog.setSize(450, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        JTextField txtMaNV = new JTextField(maNV);
        txtMaNV.setEnabled(false);
        JLabel lblTenNV = new JLabel("Tên nhân viên:");
        JTextField txtTenNV = new JTextField(tenNV);
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");

        Calendar cal = Calendar.getInstance();
        cal.setTime(ngaySinh);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        JComboBox<Integer> dayComboBox = new JComboBox<>();
        JComboBox<Integer> monthComboBox = new JComboBox<>();
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new GridLayout(1, 3));
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 100; i <= currentYear; i++) {
            yearComboBox.addItem(i);
        }

        dayComboBox.setSelectedItem(day);
        monthComboBox.setSelectedItem(month);
        yearComboBox.setSelectedItem(year);

        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        JTextField txtDiaChi = new JTextField(diaChi);
        JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
        JTextField txtSoDienThoai = new JTextField(soDienThoai);
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        JComboBox<String> cboTrangThai = new JComboBox<>(new String[] { "Đang hoạt động", "Đã xóa" });
        cboTrangThai.setSelectedItem(trangThai);

        panel.add(lblMaNV);
        panel.add(txtMaNV);
        panel.add(lblTenNV);
        panel.add(txtTenNV);
        panel.add(lblNgaySinh);
        panel.add(datePanel);
        panel.add(lblDiaChi);
        panel.add(txtDiaChi);
        panel.add(lblSoDienThoai);
        panel.add(txtSoDienThoai);
        panel.add(lblTrangThai);
        panel.add(cboTrangThai);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        btnOK.addActionListener(e -> {
            String updatedMaNV = txtMaNV.getText();
            String updatedTenNV = txtTenNV.getText();
            int updatedDay = (int) dayComboBox.getSelectedItem();
            int updatedMonth = (int) monthComboBox.getSelectedItem();
            int updatedYear = (int) yearComboBox.getSelectedItem();
            String updatedNgaySinh = String.format("%d-%02d-%02d", updatedYear, updatedMonth, updatedDay);
            String updatedDiaChi = txtDiaChi.getText();
            String updatedSoDienThoai = txtSoDienThoai.getText();
            String updatedTrangThai = doiTrangThai(cboTrangThai.getSelectedItem().toString());

            java.sql.Date updatedNgaySinhDate = java.sql.Date.valueOf(updatedNgaySinh);

            if (checkInfo("Edit", updatedMaNV, updatedSoDienThoai, editDialog)) {
                EmployeeDTO employee = new EmployeeDTO(updatedMaNV, updatedTenNV, updatedNgaySinhDate, updatedDiaChi,
                        updatedSoDienThoai, Integer.parseInt(updatedTrangThai));
                employeeBUS.update(employee);
                refreshTable();
                editDialog.dispose();
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }

            private void checkFields() {
                boolean allFieldsFilled = !txtMaNV.getText().isEmpty() &&
                        !txtTenNV.getText().isEmpty() &&
                        !txtDiaChi.getText().isEmpty() &&
                        !txtSoDienThoai.getText().isEmpty();
                btnOK.setEnabled(allFieldsFilled);
            }
        };

        txtMaNV.getDocument().addDocumentListener(documentListener);
        txtTenNV.getDocument().addDocumentListener(documentListener);
        txtDiaChi.getDocument().addDocumentListener(documentListener);
        txtSoDienThoai.getDocument().addDocumentListener(documentListener);

        panel.add(buttonPanel);
        editDialog.getContentPane().add(panel);
        editDialog.setLocationRelativeTo(null); // Sửa ở đây
        editDialog.setVisible(true);
    }

    private void showDialogViewEmployee(String maNV, String tenNV, String ngaySinh, String diaChi, String soDienThoai,
            String trangThai) {
        JDialog viewDialog = new JDialog((Frame) null, "Xem thông tin nhân viên", true);
        viewDialog.setSize(450, 350);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        JTextField txtMaNV = new JTextField(maNV);
        txtMaNV.setFont(font);
        txtMaNV.setHorizontalAlignment(SwingConstants.CENTER);
        txtMaNV.setEnabled(false);
        JLabel lblTenNV = new JLabel("Tên nhân viên:");
        JTextField txtTenNV = new JTextField(tenNV);
        txtTenNV.setHorizontalAlignment(SwingConstants.CENTER);
        txtTenNV.setFont(font);
        txtTenNV.setEnabled(false);
        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        JTextField txtNgaySinh = new JTextField(ngaySinh);
        txtNgaySinh.setFont(font);
        txtNgaySinh.setHorizontalAlignment(SwingConstants.CENTER);
        txtNgaySinh.setEnabled(false);
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        JTextField txtDiaChi = new JTextField(diaChi);
        txtDiaChi.setHorizontalAlignment(SwingConstants.CENTER);
        txtDiaChi.setFont(font);
        txtDiaChi.setEnabled(false);
        JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
        JTextField txtSoDienThoai = new JTextField(soDienThoai);
        txtSoDienThoai.setHorizontalAlignment(SwingConstants.CENTER);
        txtSoDienThoai.setFont(font);
        txtSoDienThoai.setEnabled(false);
        JLabel lblTrangThai = new JLabel("Trạng thái:");
        JTextField txtTrangThai = new JTextField(trangThai);
        txtTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
        txtTrangThai.setFont(font);
        txtTrangThai.setEnabled(false);

        panel.add(lblMaNV);
        panel.add(txtMaNV);
        panel.add(lblTenNV);
        panel.add(txtTenNV);
        panel.add(lblNgaySinh);
        panel.add(txtNgaySinh);
        panel.add(lblDiaChi);
        panel.add(txtDiaChi);
        panel.add(lblSoDienThoai);
        panel.add(txtSoDienThoai);
        panel.add(lblTrangThai);
        panel.add(txtTrangThai);

        viewDialog.getContentPane().add(panel);
        viewDialog.setLocationRelativeTo(null);
        viewDialog.setVisible(true);
    }

    public boolean checkInfo(String dialogType, String maNV, String sdt, JDialog dialog) {
        if ("addDialog".equals(dialogType)) {
            if (employeeBUS.checkMaNV(maNV)) {
                JOptionPane.showMessageDialog(dialog, "Mã nhân viên đã tồn tại trong bảng.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (!sdt.matches("^0[1-9]\\d{8}$")) {
            JOptionPane.showMessageDialog(dialog, "Số điện thoại không hợp lệ. Vui lòng nhập lại.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public void refreshTable() {
        String option = (String) cbHienThi.getSelectedItem();
        trangThai(option);
        comboBox.setSelectedItem("MaNV");
        addFocusListenerToTextField("MaNV");
    }

    public void trangThai(String option) {
        model.setRowCount(0);
        employeeBUS = new EmployeeBUS();
        list = employeeBUS.getList();
        for (EmployeeDTO employee : list) {
            if (option.equals("Đang hoạt động") && employee.getTrangThai() == 0
                    || option.equals("Đã xóa") && employee.getTrangThai() == 1) {
                addRowToModel(employee);
            }
        }
    }

    public void timKiem(String txt, int trangThai) {
        String selectedField = (String) comboBox.getSelectedItem();
        model.setRowCount(0);

        for (EmployeeDTO employee : list) {
            if (employee.getTrangThai() == trangThai && employeeBUS.isMatched(employee, selectedField, txt)) {
                addRowToModel(employee);
            }
        }
    }

    public void addRowToModel(EmployeeDTO employee) {
        String ngaySinh = formatDate(employee.getNgaySinh());
        Object[] row = { employee.getMaNV(), employee.getTenNV(), ngaySinh, employee.getDiaChi(), employee.getSdt(),
                doiTrangThai(employee.getTrangThai() + "") };
        model.addRow(row);
    }

    private void addFocusListenerToTextField(String defaultText) {
        txtTimKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtTimKiem.getText().equals(defaultText)) {
                    txtTimKiem.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtTimKiem.getText().isEmpty()) {
                    txtTimKiem.setText(defaultText);
                }
            }
        });
    }

    public String doiTrangThai(String state) {
        if (state.equals("0")) {
            return "Đang hoạt động";
        } else if (state.equals("Đang hoạt động")) {
            return "0";
        } else if (state.equals("1")) {
            return "Đã xóa";
        } else if (state.equals("Đã xóa")) {
            return "1";
        }
        return "Lỗi";
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public void showConfirmationDialog(String maNV) {
        int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn khôi phục nhân viên này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            employeeBUS.updateTrangThai(maNV, 0);
            refreshTable();
        }
    }

    private void xuatexcel() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Employees");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(table.getColumnName(i));
                }

                for (int j = 0; j < table.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < table.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (table.getValueAt(j, k) != null) {
                            cell.setCellValue(table.getValueAt(j, k).toString());
                        }
                    }
                }

                try (FileOutputStream out = new FileOutputStream(saveFile)) {
                    wb.write(out);
                    wb.close();
                    JOptionPane.showMessageDialog(null, "Xuất file thành công");
                    openFile(saveFile.toString());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xuất file không thành công");
            e.printStackTrace();
        }
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disableButtons() {
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
        btnView.setEnabled(false);
        btnNhap.setEnabled(false);
        btnXuat.setEnabled(false);
        cbHienThi.removeItem("Đã xóa");
    }
}