package org.example.GUI.Components.FormSupplier;

import org.example.BUS.SupplierBUS;
import org.example.DTO.SupplierDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SupplierGUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBoxSearch;
    private JTextField textFieldSearch;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbTrangThai;
    private JButton btnAdd, btnView, btnDelete, btnEdit, btnExportExcel, btnImportExcel, btnRefresh;
    private SupplierBUS supplierBus = new SupplierBUS();
    private ArrayList<SupplierDTO> supplierList = new ArrayList<>();
    private JDialog addDialog, editDialog;

    public SupplierGUI() {
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel controlPanel = new JPanel(new GridLayout(3, 1));
        add(controlPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        controlPanel.add(buttonPanel);

        btnAdd = new JButton("Thêm");
        ImageIcon addIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png");
        if (addIcon != null)
            btnAdd.setIcon(addIcon);
        btnAdd.setPreferredSize(new Dimension(120, 42));
        btnAdd.addActionListener(e -> showDialogToAddSupplier());
        buttonPanel.add(btnAdd);

        btnView = new JButton("Xem");
        ImageIcon viewIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_show_property_30px.png");
        if (viewIcon != null)
            btnView.setIcon(viewIcon);
        btnView.setPreferredSize(new Dimension(120, 42));
        btnView.addActionListener(e -> viewSelectedSupplier());
        buttonPanel.add(btnView);

        btnDelete = new JButton("Xóa");
        ImageIcon deleteIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_delete_forever_30px_1.png");
        if (deleteIcon != null)
            btnDelete.setIcon(deleteIcon);
        btnDelete.setPreferredSize(new Dimension(120, 42));
        btnDelete.addActionListener(e -> deleteSelectedSupplier());
        buttonPanel.add(btnDelete);

        btnEdit = new JButton("Sửa");
        ImageIcon editIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_wrench_30px.png");
        if (editIcon != null)
            btnEdit.setIcon(editIcon);
        btnEdit.setPreferredSize(new Dimension(120, 42));
        btnEdit.addActionListener(e -> editSelectedSupplier());
        buttonPanel.add(btnEdit);

        btnExportExcel = new JButton("Xuất Excel");
        ImageIcon exportIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_ms_excel_30px.png");
        if (exportIcon != null)
            btnExportExcel.setIcon(exportIcon);
        btnExportExcel.setPreferredSize(new Dimension(135, 42));
        btnExportExcel.addActionListener(e -> exportToExcel());
        buttonPanel.add(btnExportExcel);

        btnImportExcel = new JButton("Nhập Excel");
        ImageIcon importIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_ms_excel_30px.png");
        if (importIcon != null)
            btnImportExcel.setIcon(importIcon);
        btnImportExcel.setPreferredSize(new Dimension(135, 42));
        btnImportExcel.addActionListener(e -> importFromExcel());
        buttonPanel.add(btnImportExcel);

        JPanel searchPanel = new JPanel(new FlowLayout());
        controlPanel.add(searchPanel);

        String[] statusOptions = { "Đang hoạt động", "Đã xóa" };
        cbTrangThai = new JComboBox<>(statusOptions);
        cbTrangThai.addActionListener(e -> refreshTable());
        searchPanel.add(cbTrangThai);

        JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.setBorder(new TitledBorder("Tìm kiếm"));
        searchFieldPanel.setLayout(new FlowLayout());
        searchPanel.add(searchFieldPanel);

        String[] searchItems = { "MaNCC", "TenNCC", "Địa chỉ", "SDT" };
        comboBoxSearch = new JComboBox<>(searchItems);
        comboBoxSearch.setPreferredSize(new Dimension(115, 35));
        comboBoxSearch.addActionListener(e -> {
            String option = (String) comboBoxSearch.getSelectedItem();
            textFieldSearch.setText(option);
            addFocusListenerToTextField(option);
        });
        searchFieldPanel.add(comboBoxSearch);

        textFieldSearch = new JTextField();
        textFieldSearch.setPreferredSize(new Dimension(140, 35));
        textFieldSearch.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSearch.addActionListener(e -> searchSuppliers());
        searchFieldPanel.add(textFieldSearch);

        btnRefresh = new JButton("Làm mới");
        ImageIcon refreshIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png");
        if (refreshIcon != null)
            btnRefresh.setIcon(refreshIcon);
        btnRefresh.setPreferredSize(new Dimension(135, 43));
        btnRefresh.addActionListener(e -> refreshTable());
        searchPanel.add(btnRefresh);

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        model = new DefaultTableModel(new Object[] { "MaNCC", "TenNCC", "DiaChi", "SDT", "Fax", "Trạng thái" }, 0);
        table.setModel(model);
        scrollPane.setViewportView(table);

        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontHeader = new Font("Segoe UI", Font.BOLD, 16);
        table.setFont(font);
        table.getTableHeader().setFont(fontHeader);
        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(150);
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row != -1 && table.getValueAt(row, 5).equals("Đã xóa")) {
                        showConfirmationDialog(table.getValueAt(row, 0).toString());
                    }
                }
            }
        });

        refreshTable(); // Khởi tạo bảng lần đầu
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

    private void showDialogToAddSupplier() {
        addDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Thêm nhà cung cấp",
                Dialog.ModalityType.APPLICATION_MODAL);
        addDialog.setSize(450, 400);
        JPanel panel = createSupplierFormPanel(null);
        addDialog.getContentPane().add(panel);
        addDialog.setLocationRelativeTo(this);
        addDialog.setVisible(true);
    }

    private void editSelectedSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNCC = table.getValueAt(selectedRow, 0).toString();
            SupplierDTO supplier = supplierBus.getSupplierDTO(maNCC);
            if (supplier != null) {
                editDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Sửa nhà cung cấp",
                        Dialog.ModalityType.APPLICATION_MODAL);
                editDialog.setSize(450, 400);
                JPanel panel = createSupplierFormPanel(supplier);
                editDialog.getContentPane().add(panel);
                editDialog.setLocationRelativeTo(this);
                editDialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để chỉnh sửa", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void viewSelectedSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNCC = table.getValueAt(selectedRow, 0).toString();
            SupplierDTO supplier = supplierBus.getSupplierDTO(maNCC);
            if (supplier != null) {
                JDialog viewDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Xem nhà cung cấp",
                        Dialog.ModalityType.APPLICATION_MODAL);
                viewDialog.setSize(400, 300);
                JPanel panel = new JPanel(new GridLayout(6, 2, 5, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                Font font = new Font("Arial", Font.BOLD, 14);
                panel.add(new JLabel("Mã NCC:"));
                JTextField txtMaNCC = new JTextField(supplier.getMaNCC());
                txtMaNCC.setEnabled(false);
                txtMaNCC.setFont(font);
                txtMaNCC.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(txtMaNCC);

                panel.add(new JLabel("Tên NCC:"));
                JTextField txtTenNCC = new JTextField(supplier.getTenNCC());
                txtTenNCC.setEnabled(false);
                txtTenNCC.setFont(font);
                txtTenNCC.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(txtTenNCC);

                panel.add(new JLabel("Địa chỉ:"));
                JTextField txtDiaChi = new JTextField(supplier.getDiaChi());
                txtDiaChi.setEnabled(false);
                txtDiaChi.setFont(font);
                txtDiaChi.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(txtDiaChi);

                panel.add(new JLabel("SĐT:"));
                JTextField txtSDT = new JTextField(supplier.getSDT());
                txtSDT.setEnabled(false);
                txtSDT.setFont(font);
                txtSDT.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(txtSDT);

                panel.add(new JLabel("Fax:"));
                JTextField txtFax = new JTextField(supplier.getFax());
                txtFax.setEnabled(false);
                txtFax.setFont(font);
                txtFax.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(txtFax);

                panel.add(new JLabel("Trạng thái:"));
                JTextField txtTrangThai = new JTextField(convertTrangThai(supplier.getTrangThai()));
                txtTrangThai.setEnabled(false);
                txtTrangThai.setFont(font);
                txtTrangThai.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(txtTrangThai);

                viewDialog.getContentPane().add(panel);
                viewDialog.setLocationRelativeTo(this);
                viewDialog.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xem", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel createSupplierFormPanel(SupplierDTO supplier) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaNCC = new JLabel("Mã NCC:");
        JTextField txtMaNCC = supplier == null ? new JTextField() : new JTextField(supplier.getMaNCC());
        txtMaNCC.setEnabled(supplier == null);
        panel.add(lblMaNCC);
        panel.add(txtMaNCC);

        JLabel lblTenNCC = new JLabel("Tên NCC:");
        JTextField txtTenNCC = supplier == null ? new JTextField() : new JTextField(supplier.getTenNCC());
        panel.add(lblTenNCC);
        panel.add(txtTenNCC);

        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        JTextField txtDiaChi = supplier == null ? new JTextField() : new JTextField(supplier.getDiaChi());
        panel.add(lblDiaChi);
        panel.add(txtDiaChi);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        JTextField txtSDT = supplier == null ? new JTextField() : new JTextField(supplier.getSDT());
        panel.add(lblSDT);
        panel.add(txtSDT);

        JLabel lblFax = new JLabel("Fax:");
        JTextField txtFax = supplier == null ? new JTextField() : new JTextField(supplier.getFax());
        panel.add(lblFax);
        panel.add(txtFax);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        String[] items = { "Đang hoạt động", "Đã xóa" };
        JComboBox<String> cbTrangThai = new JComboBox<>(items);
        if (supplier != null)
            cbTrangThai.setSelectedItem(convertTrangThai(supplier.getTrangThai()));
        panel.add(lblTrangThai);
        panel.add(cbTrangThai);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOK.setEnabled(false);
        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        btnOK.addActionListener(e -> {
            int trangThai = cbTrangThai.getSelectedItem().equals("Đang hoạt động") ? 0 : 1;
            if (checkInfo(supplier == null ? addDialog : editDialog, txtMaNCC.getText(), txtSDT.getText())) {
                SupplierDTO newSupplier = new SupplierDTO(txtMaNCC.getText(), txtTenNCC.getText(), txtDiaChi.getText(),
                        txtSDT.getText(), txtFax.getText(), trangThai);
                if (supplier == null) {
                    supplierBus.add(newSupplier);
                    JOptionPane.showMessageDialog(addDialog, "Thêm nhà cung cấp thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    addDialog.dispose();
                } else {
                    supplierBus.update(newSupplier);
                    JOptionPane.showMessageDialog(editDialog, "Sửa nhà cung cấp thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                }
                SwingUtilities.invokeLater(this::refreshTable); // Cập nhật bảng ngay lập tức trong EDT
            }
        });

        btnCancel.addActionListener(e -> (supplier == null ? addDialog : editDialog).dispose());

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
                boolean allFieldsFilled = !txtMaNCC.getText().isEmpty() && !txtTenNCC.getText().isEmpty() &&
                        !txtDiaChi.getText().isEmpty() && !txtSDT.getText().isEmpty() && !txtFax.getText().isEmpty();
                btnOK.setEnabled(allFieldsFilled);
            }
        };

        txtMaNCC.getDocument().addDocumentListener(documentListener);
        txtTenNCC.getDocument().addDocumentListener(documentListener);
        txtDiaChi.getDocument().addDocumentListener(documentListener);
        txtSDT.getDocument().addDocumentListener(documentListener);
        txtFax.getDocument().addDocumentListener(documentListener);

        panel.add(buttonPanel);
        return panel;
    }

    private void deleteSelectedSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNCC = table.getValueAt(selectedRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhà cung cấp này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                supplierBus.updateTrangthai(maNCC, 1);
                JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(this::refreshTable); // Cập nhật bảng ngay lập tức trong EDT
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhà cung cấp để xóa", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = new File(fileChooser.getSelectedFile() + ".xlsx");
            try (Workbook workbook = new XSSFWorkbook();
                    FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                Sheet sheet = workbook.createSheet("Nhà cung cấp");
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    headerRow.createCell(i).setCellValue(table.getColumnName(i));
                }
                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        row.createCell(j).setCellValue(table.getValueAt(i, j).toString());
                    }
                }
                workbook.write(outputStream);
                JOptionPane.showMessageDialog(this, "Xuất file thành công");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Xuất file không thành công");
                e.printStackTrace();
            }
        }
    }

    private void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (FileInputStream inputStream = new FileInputStream(fileToOpen);
                    Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        String maNCC = row.getCell(0).getStringCellValue();
                        String tenNCC = row.getCell(1).getStringCellValue();
                        String diaChi = row.getCell(2).getStringCellValue();
                        String sdt = row.getCell(3).getStringCellValue();
                        String fax = row.getCell(4).getStringCellValue();
                        int trangThai = Integer.parseInt(row.getCell(5).getStringCellValue());
                        SupplierDTO supplier = new SupplierDTO(maNCC, tenNCC, diaChi, sdt, fax, trangThai);
                        supplierBus.add(supplier);
                    }
                }
                SwingUtilities.invokeLater(this::refreshTable); // Cập nhật bảng ngay lập tức trong EDT
                JOptionPane.showMessageDialog(this, "Nhập file thành công");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Nhập file không thành công");
                e.printStackTrace();
            }
        }
    }

    private void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            String option = (String) cbTrangThai.getSelectedItem();
            int trangThai = option.equals("Đang hoạt động") ? 0 : 1;
            model.setRowCount(0); // Xóa dữ liệu cũ
            supplierList = supplierBus.getList(); // Lấy danh sách mới nhất
            for (SupplierDTO sup : supplierList) {
                if (sup.getTrangThai() == trangThai) {
                    model.addRow(new Object[] { sup.getMaNCC(), sup.getTenNCC(), sup.getDiaChi(), sup.getSDT(),
                            sup.getFax(), convertTrangThai(sup.getTrangThai()) });
                }
            }
            model.fireTableDataChanged(); // Thông báo dữ liệu đã thay đổi
            table.revalidate();
            table.repaint();
        });
    }

    private void searchSuppliers() {
        SwingUtilities.invokeLater(() -> {
            String txt = textFieldSearch.getText();
            String option = (String) cbTrangThai.getSelectedItem();
            int trangThai = option.equals("Đang hoạt động") ? 0 : 1;
            String selectedField = (String) comboBoxSearch.getSelectedItem();
            model.setRowCount(0);
            supplierList = supplierBus.getList();
            for (SupplierDTO sup : supplierList) {
                if (sup.getTrangThai() == trangThai && supplierBus.isMatched(sup, selectedField, txt)) {
                    model.addRow(new Object[] { sup.getMaNCC(), sup.getTenNCC(), sup.getDiaChi(), sup.getSDT(),
                            sup.getFax(), convertTrangThai(sup.getTrangThai()) });
                }
            }
            model.fireTableDataChanged(); // Thông báo dữ liệu đã thay đổi
            table.revalidate();
            table.repaint();
        });
    }

    private String convertTrangThai(int state) {
        return state == 0 ? "Đang hoạt động" : "Đã xóa";
    }

    private boolean checkInfo(JDialog dialog, String maNCC, String sdt) {
        if (dialog == addDialog && supplierBus.checkId(maNCC)) {
            JOptionPane.showMessageDialog(dialog, "Mã nhà cung cấp đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!sdt.matches("^0[1-9]\\d{8}$")) {
            JOptionPane.showMessageDialog(dialog, "Số điện thoại không hợp lệ. Vui lòng nhập lại.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void addFocusListenerToTextField(String defaultText) {
        textFieldSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldSearch.getText().equals(defaultText))
                    textFieldSearch.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldSearch.getText().isEmpty())
                    textFieldSearch.setText(defaultText);
            }
        });
    }

    private void showConfirmationDialog(String maNCC) {
        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục nhà cung cấp này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            supplierBus.updateTrangthai(maNCC, 0);
            JOptionPane.showMessageDialog(this, "Khôi phục nhà cung cấp thành công!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(this::refreshTable); // Cập nhật bảng ngay lập tức trong EDT
        }
    }
}