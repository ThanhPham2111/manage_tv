package org.example.GUI.Components.FormProducts;

import org.example.BUS.ProductBUS;
import org.example.DTO.ProductDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ProductDisableButton extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private ProductBUS productBUS = new ProductBUS();
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnEdit;
    private JButton btnView;
    private JButton btnExportExcel;
    private JComboBox<String> cbDisplayStatus;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<ProductDTO> productList;
    private JTextField txtSearch;
    private JComboBox<String> cbSearchField;

    public ProductDisableButton() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel chứa các nút và tìm kiếm
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new GridLayout(2, 1));
        contentPane.add(panelTop, BorderLayout.NORTH);

        // Panel nút
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelTop.add(panelButtons);

        btnAdd = new JButton("Thêm");
        btnAdd.setPreferredSize(new Dimension(144, 43));
        btnAdd.setIcon(new ImageIcon(getClass().getResource("/images/plus.png")));
        panelButtons.add(btnAdd);

        btnDelete = new JButton("Xóa");
        btnDelete.setPreferredSize(new Dimension(144, 43));
        btnDelete.setIcon(new ImageIcon(getClass().getResource("/images/bin.png")));
        panelButtons.add(btnDelete);

        btnEdit = new JButton("Sửa");
        btnEdit.setPreferredSize(new Dimension(144, 43));
        btnEdit.setIcon(new ImageIcon(getClass().getResource("/images/editing.png")));
        panelButtons.add(btnEdit);

        btnView = new JButton("Xem");
        btnView.setPreferredSize(new Dimension(144, 43));
        btnView.setIcon(new ImageIcon(getClass().getResource("/images/view.png")));
        panelButtons.add(btnView);

        btnExportExcel = new JButton("Xuất Excel");
        btnExportExcel.setPreferredSize(new Dimension(144, 43));
        btnExportExcel.setIcon(new ImageIcon(getClass().getResource("/images/icons8_ms_excel_30px.png")));
        panelButtons.add(btnExportExcel);

        // Panel tìm kiếm và trạng thái
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelTop.add(panelSearch);

        String[] statusOptions = { "Đang bán", "Đã xóa" };
        cbDisplayStatus = new JComboBox<>(statusOptions);
        panelSearch.add(cbDisplayStatus);

        JPanel panelSearchField = new JPanel();
        panelSearchField.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        panelSearch.add(panelSearchField);

        String[] searchFields = { "MaSP", "TenSP", "MaLSP", "DonGia", "SoLuong" };
        cbSearchField = new JComboBox<>(searchFields);
        cbSearchField.setPreferredSize(new Dimension(130, 40));
        panelSearchField.add(cbSearchField);

        txtSearch = new JTextField("");
        txtSearch.setHorizontalAlignment(SwingConstants.CENTER);
        txtSearch.setPreferredSize(new Dimension(144, 40));
        panelSearchField.add(txtSearch);

        // Table hiển thị sản phẩm
        scrollPane = new JScrollPane();
        table = new JTable();
        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontHeader = new Font("Segoe UI", Font.BOLD, 16);
        table.setFont(font);
        table.getTableHeader().setFont(fontHeader);
        table.setRowHeight(30);
        scrollPane.setViewportView(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Khởi tạo model cho bảng
        model = new DefaultTableModel();
        model.addColumn("Mã SP");
        model.addColumn("Mã LSP");
        model.addColumn("Tên SP");
        model.addColumn("Đơn giá");
        model.addColumn("Số lượng");
        model.addColumn("File ảnh");
        model.addColumn("Trạng thái");
        table.setModel(model);

        // Định dạng cột DonGia để hiển thị số nguyên chính xác
        DefaultTableCellRenderer priceRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof Number) {
                    setText(String.format("%,d", ((Number) value).longValue())); // Định dạng số nguyên, thêm dấu phẩy
                }
                setHorizontalAlignment(SwingConstants.RIGHT); // Căn phải cho giá tiền
                return this;
            }
        };
        table.getColumnModel().getColumn(3).setCellRenderer(priceRenderer); // Áp dụng cho cột DonGia (cột thứ 4)

        // Sự kiện và khởi tạo dữ liệu
        initializeEvents();
        refreshTable();
        disableButtons(); // Vô hiệu hóa các nút theo yêu cầu
    }

    // Vô hiệu hóa các nút
    private void disableButtons() {
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        btnEdit.setEnabled(false);
        btnView.setEnabled(false);
        cbDisplayStatus.removeItem("Đã xóa"); // Chỉ hiển thị "Đang bán"
    }

    // Làm mới bảng
    private void refreshTable() {
        String status = (String) cbDisplayStatus.getSelectedItem();
        updateTableByStatus(status);
    }

    // Cập nhật bảng theo trạng thái
    private void updateTableByStatus(String status) {
        model.setRowCount(0); // Xóa các hàng hiện tại
        productList = productBUS.getList();

        for (ProductDTO product : productList) {
            if ((status.equals("Đã xóa") && product.getTrangthai() == 1) ||
                    (status.equals("Đang bán") && product.getTrangthai() == 0)) {
                addRowToModel(product);
            }
        }
    }

    // Thêm hàng vào model
    private void addRowToModel(ProductDTO product) {
        model.addRow(new Object[] {
                product.getMaSP(),
                product.getMaLSP(),
                product.getTenSP(),
                product.getDonGia(),
                product.getSoLuong(),
                product.getHinhAnh(),
                convertStatus(product.getTrangthai())
        });
    }

    // Chuyển đổi trạng thái từ số sang chuỗi
    private String convertStatus(int status) {
        return status == 0 ? "Đang bán" : "Đã xóa";
    }

    // Khởi tạo các sự kiện
    private void initializeEvents() {
        // Sự kiện thay đổi trạng thái hiển thị
        cbDisplayStatus.addActionListener(e -> refreshTable());

        // Sự kiện tìm kiếm
        txtSearch.addActionListener(e -> {
            String searchText = txtSearch.getText();
            String searchField = (String) cbSearchField.getSelectedItem();
            String status = (String) cbDisplayStatus.getSelectedItem();
            int trangThai = status.equals("Đang bán") ? 0 : 1;
            searchProducts(searchText, searchField, trangThai);
        });

        // Sự kiện xuất Excel
        btnExportExcel.addActionListener(e -> exportToExcel());

        // Sự kiện click vào bảng để khôi phục sản phẩm đã xóa
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String trangThai = table.getValueAt(row, 6).toString();
                        if (trangThai.equals("Đã xóa")) {
                            String maSP = table.getValueAt(row, 0).toString();
                            showRestoreConfirmation(maSP);
                        }
                    }
                }
            }
        });
    }

    // Tìm kiếm sản phẩm
    private void searchProducts(String txt, String field, int trangThai) {
        model.setRowCount(0);
        for (ProductDTO product : productList) {
            if (product.getTrangthai() == trangThai && productBUS.isMatched(product, field, txt)) {
                addRowToModel(product);
            }
        }
    }

    // Hiển thị xác nhận khôi phục sản phẩm
    private void showRestoreConfirmation(String maSP) {
        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn khôi phục sản phẩm này?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            productBUS.updateTrangthai(maSP, 0);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Khôi phục sản phẩm thành công!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Xuất dữ liệu ra file Excel
    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = new File(fileChooser.getSelectedFile() + ".xlsx");
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Sản phẩm");

                // Tạo hàng tiêu đề
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    headerRow.createCell(i).setCellValue(table.getColumnName(i));
                }

                // Thêm dữ liệu
                for (int i = 0; i < table.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Object value = table.getValueAt(i, j);
                        row.createCell(j).setCellValue(value != null ? value.toString() : "");
                    }
                }

                // Ghi file
                try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                    workbook.write(outputStream);
                    JOptionPane.showMessageDialog(this, "Xuất file thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Xuất file thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    // Trả về panel để sử dụng trong GUI khác
    public JPanel getPanel() {
        return contentPane;
    }

    // Main để chạy thử
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ProductDisableButton frame = new ProductDisableButton();
                frame.setMinimumSize(new Dimension(700, 600));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}