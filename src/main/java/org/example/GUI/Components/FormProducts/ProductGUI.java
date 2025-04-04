package org.example.GUI.Components.FormProducts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.ProductDTO;

public class ProductGUI extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public ProductGUI() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel();
        table = new JTable(model);

        // Initialize components
        initComponents();
        formatPriceColumn(); // Định dạng cột Đơn giá sau khi khởi tạo bảng
    }

    private void initComponents() {
        // Phần trên cùng: Panel chứa các nút Thêm, Xóa, Sửa, Xem, Xuất Excel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        JButton btnView = new JButton("Xem");
        JButton btnExportExcel = new JButton("Xuất Excel");

        // Thêm icon cho các nút với đường dẫn từ classpath
        ImageIcon addIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png");
        if (addIcon != null) {
            btnAdd.setIcon(addIcon);
        }

        ImageIcon deleteIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_cancel_30px_1.png");
        if (deleteIcon != null) {
            btnDelete.setIcon(deleteIcon);
        }

        ImageIcon editIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_support_30px.png");
        if (editIcon != null) {
            btnEdit.setIcon(editIcon);
        }

        ImageIcon viewIcon = loadImageIcon("/org/example/GUI/resources/images/view.png");
        if (viewIcon != null) {
            btnView.setIcon(viewIcon);
        }

        // Tăng kích thước các nút và chừa chỗ cho icon
        Dimension buttonSize = new Dimension(120, 40);
        btnAdd.setPreferredSize(buttonSize);
        btnDelete.setPreferredSize(buttonSize);
        btnEdit.setPreferredSize(buttonSize);
        btnView.setPreferredSize(buttonSize);
        btnExportExcel.setPreferredSize(buttonSize);

        // Thêm các nút vào panel
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnView);
        buttonPanel.add(btnExportExcel);

        // Phần tìm kiếm: Căn giữa
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JComboBox<String> searchComboBox = new JComboBox<>(new String[] { "Đang bán", "Ngừng bán" });
        searchComboBox.setPreferredSize(new Dimension(150, 30));
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnReset = new JButton("Làm mới");

        // Tăng kích thước các nút tìm kiếm
        btnSearch.setPreferredSize(new Dimension(100, 30));
        btnReset.setPreferredSize(new Dimension(100, 30));

        searchPanel.add(searchComboBox);
        searchPanel.add(searchField);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReset);

        // Phần bảng: Thiết lập các cột dựa trên ProductDTO
        model.addColumn("Mã SP");
        model.addColumn("Mã LSP");
        model.addColumn("Tên SP");
        model.addColumn("Đơn giá");
        model.addColumn("Số lượng");
        model.addColumn("File ảnh");
        model.addColumn("Trạng thái");

        // Set custom renderer for the image column
        table.getColumn("File ảnh").setCellRenderer(new ImageRenderer());

        // Load data from database
        loadDataFromDatabase();

        // Thêm các thành phần vào panel chính
        JScrollPane scrollPane = new JScrollPane(table);

        // Sắp xếp các panel vào layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadDataFromDatabase() {
        List<ProductDTO> products = getProductsFromDatabase();
        for (ProductDTO product : products) {
            addProductToTable(product);
        }
    }

    private List<ProductDTO> getProductsFromDatabase() {
        List<ProductDTO> products = new ArrayList<>();
        String query = "SELECT MaSP, MaLSP, TenSP, DonGia, SoLuong, HinhAnh, Trangthai FROM products";

        try (Connection conn = UtilsJDBC.getConnectDB();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductDTO product = new ProductDTO(
                        rs.getString("MaSP"),
                        rs.getString("MaLSP"),
                        rs.getString("TenSP"),
                        rs.getFloat("DonGia"),
                        rs.getInt("SoLuong"),
                        rs.getString("HinhAnh"),
                        rs.getInt("Trangthai"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn database: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    private void addProductToTable(ProductDTO product) {
        ImageIcon imageIcon = null;
        // Đường dẫn tương đối đến thư mục imageTopic
        String imagePath = "/org/example/GUI/resources/imageTopic/" + product.getHinhAnh();
        java.net.URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            imageIcon = new ImageIcon(imgURL);
            Image img = imageIcon.getImage();
            Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImg);
        } else {
            System.err.println("Không thể tải hình ảnh: " + imagePath);
        }

        model.addRow(new Object[] {
                product.getMaSP(),
                product.getMaLSP(),
                product.getTenSP(),
                product.getDonGia(),
                product.getSoLuong(),
                imageIcon,
                product.getTrangthai() == 1 ? "Đang bán" : "Ngừng bán"
        });
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

    // Phương thức định dạng cột Đơn giá
    private void formatPriceColumn() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            private final DecimalFormat formatter = new DecimalFormat("#,##0");

            @Override
            protected void setValue(Object value) {
                if (value instanceof Number) {
                    setText(formatter.format(((Number) value).floatValue()));
                } else {
                    setText((value == null) ? "" : value.toString());
                }
            }
        };

        // Áp dụng renderer cho cột "Đơn giá" (cột thứ 3, vì index bắt đầu từ 0)
        TableColumn priceColumn = table.getColumnModel().getColumn(3);
        priceColumn.setCellRenderer(renderer);
    }

    // Custom renderer for image column
    class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
                setText(null);
            } else {
                setIcon(null);
                setText(value != null ? value.toString() : "");
            }
            return this;
        }
    }
}