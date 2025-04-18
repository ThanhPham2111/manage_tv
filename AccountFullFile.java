import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Thinh_File extends JFrame {

    private static JTable table;

    // Chức năng cho nút làm mới
    private JComboBox<String> statusComboBox;
    private JComboBox<String> searchTypeComboBox;
    private JTextField searchField;
    private DefaultTableModel model;

    private Object[][] originalData = {
            { "1", "admin@example.com", "admin", "Thanh123", "NV12", "Q4" },
            { "2", "baduoc@example.com", "BaDuocSeller", "baduoc@123", "NV3", "Q2" },
            { "3", "nhanvien@example.com", "NhanVien", "feafe@123", "NV20", "Q2" },
            { "4", "quanly@example.com", "Quan Ly", "quanly@123", "NV9", "Q1" },
            { "5", "thanhtu@example.com", "ThanhTuNH", "thanhtu@434", "NV5", "Q5" },
            { "6", "tridung@example.com", "TriDungSeller", "tridung@fe3", "NV1", "Q2" },
            { "7", "vanhoang@example.com", "VanHoangAdmin", "vanhoang@feaf2", "NV4", "Q3" },
            { "8", "vantai@example.com", "VanTaiNH", "vantai@1212", "NV12", "Q5" },
            { "9", "yenhan@example.com", "YenHanPhuBH", "yenhan@123", "NV23", "Q3" }
    };

    public Thinh_File() {
        setTitle("Quản lý tài khoản");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Restore về size cụ thể, tránh lỗi resize về quá nhỏ
        addWindowStateListener(e -> {
            if ((e.getOldState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH &&
                    (e.getNewState() & Frame.MAXIMIZED_BOTH) != Frame.MAXIMIZED_BOTH) {
                setSize(1000, 600);
                setLocationRelativeTo(null);
            }
        });

        // =========== Sidebar ===========
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(11, 1, 0, 5));
        sidebar.setBackground(new Color(30, 30, 30));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        String[] sidebarItems = {
                "Bán hàng", "Nhập hàng", "Sản phẩm", "Loại sản phẩm", "Hóa đơn",
                "Phiếu nhập", "Khuyến mãi", "Nhân viên", "Khách hàng", "Nhà cung cấp",
                "Tài khoản"
        };

        for (String item : sidebarItems) {
            JButton btn = new JButton(item);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(240, 240, 240));
            btn.setForeground(Color.BLACK);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            sidebar.add(btn);
        }

        add(sidebar, BorderLayout.WEST);

        // =========== Main Panel ===========
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Top Panel chứa cả nút và bộ lọc ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // === Top buttons ===
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        // Khai báo các nút
        JButton addButton = new JButton("Thêm");
        JButton deleteButton = new JButton("Xóa");
        JButton editButton = new JButton("Sửa");
        JButton exportButton = new JButton("Xuất Excel");
        JButton importButton = new JButton("Nhập Excel");

        // ===============Thêm ActionListener cho các nút==========================

        // AddButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo form nhập liệu
                JTextField emailField = new JTextField(20);
                JTextField usernameField = new JTextField(20);
                JPasswordField passwordField = new JPasswordField(20);
                JTextField maNVField = new JTextField(20);
                JTextField maQuyenField = new JTextField(20);

                JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
                panel.setPreferredSize(new Dimension(400, 250)); // Đặt kích thước cửa sổ

                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Tên tài khoản:"));
                panel.add(usernameField);
                panel.add(new JLabel("Mật khẩu:"));
                panel.add(passwordField);
                panel.add(new JLabel("Mã nhân viên:"));
                panel.add(maNVField);
                panel.add(new JLabel("Mã quyền:"));
                panel.add(maQuyenField);

                boolean isValid = false;

                // Lặp lại cho đến khi thông tin hợp lệ
                while (!isValid) {
                    int result = JOptionPane.showConfirmDialog(
                            null, panel, "Thêm tài khoản", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String email = emailField.getText();
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());
                        String maNV = maNVField.getText();
                        String maQuyen = maQuyenField.getText();

                        // Kiểm tra các trường nhập
                        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || maNV.isEmpty()
                                || maQuyen.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Tất cả các trường phải được điền đầy đủ.", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (!email.contains("@")) {
                            JOptionPane.showMessageDialog(null, "Email không hợp lệ.", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Thêm vào bảng nếu tất cả thông tin hợp lệ
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.addRow(
                                    new Object[] { model.getRowCount() + 1, email, username, password, maNV, maQuyen });
                            isValid = true; // Thông tin hợp lệ, thoát khỏi vòng lặp
                        }
                    } else {
                        break; // Nếu người dùng bấm Cancel, thoát khỏi vòng lặp
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                    // System.out.println(row);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa.");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // Lấy chỉ số dòng được chọn

                if (row != -1) { // Kiểm tra xem có dòng nào được chọn không
                    // Lấy dữ liệu hiện tại của dòng
                    String email = (String) table.getValueAt(row, 1);
                    String username = (String) table.getValueAt(row, 2);
                    String password = (String) table.getValueAt(row, 3);
                    String maNV = (String) table.getValueAt(row, 4);
                    String maQuyen = (String) table.getValueAt(row, 5);

                    // Tạo các trường nhập liệu với dữ liệu hiện tại
                    JTextField emailField = new JTextField(email, 20);
                    JTextField usernameField = new JTextField(username, 20);
                    JPasswordField passwordField = new JPasswordField(password, 20);
                    JTextField maNVField = new JTextField(maNV, 20);
                    JTextField maQuyenField = new JTextField(maQuyen, 20);

                    JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
                    panel.setPreferredSize(new Dimension(400, 250));

                    panel.add(new JLabel("Email:"));
                    panel.add(emailField);
                    panel.add(new JLabel("Tên tài khoản:"));
                    panel.add(usernameField);
                    panel.add(new JLabel("Mật khẩu:"));
                    panel.add(passwordField);
                    panel.add(new JLabel("Mã nhân viên:"));
                    panel.add(maNVField);
                    panel.add(new JLabel("Mã quyền:"));
                    panel.add(maQuyenField);

                    boolean isValid = false;

                    // Lặp lại cho đến khi thông tin hợp lệ
                    while (!isValid) {
                        int result = JOptionPane.showConfirmDialog(null, panel, "Sửa tài khoản",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            String newEmail = emailField.getText();
                            String newUsername = usernameField.getText();
                            String newPassword = new String(passwordField.getPassword());
                            String newMaNV = maNVField.getText();
                            String newMaQuyen = maQuyenField.getText();

                            // Kiểm tra các trường nhập
                            if (newEmail.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()
                                    || newMaNV.isEmpty() || newMaQuyen.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Tất cả các trường phải được điền đầy đủ.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                            } else if (!newEmail.contains("@")) {
                                JOptionPane.showMessageDialog(null, "Email không hợp lệ.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                // Cập nhật lại bảng với dữ liệu mới
                                table.setValueAt(newEmail, row, 1);
                                table.setValueAt(newUsername, row, 2);
                                table.setValueAt(newPassword, row, 3);
                                table.setValueAt(newMaNV, row, 4);
                                table.setValueAt(newMaQuyen, row, 5);

                                isValid = true; // Thông tin hợp lệ, thoát khỏi vòng lặp
                            }
                        } else {
                            break; // Nếu người dùng bấm Cancel, thoát khỏi vòng lặp
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để sửa.");
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức xuất CSV
                exportToCSV();
            }
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn file CSV để nhập");
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

                int userSelection = fileChooser.showOpenDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToImport = fileChooser.getSelectedFile();
                    importFromExcel(fileToImport); // Hoặc đổi tên thành importFromCSV cho rõ ràng
                }
            }
        });

        // Thêm các nút vào topButtons
        topButtons.add(addButton);
        topButtons.add(deleteButton);
        topButtons.add(editButton);
        topButtons.add(exportButton);
        topButtons.add(importButton);

        // Thêm topButtons vào topPanel
        topPanel.add(topButtons);

        // =============== Filter and search =============
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 100));
        filterPanel.add(new JLabel("Trạng thái tài khoản:"));

        // Khởi tạo statusComboBox trước khi sử dụng
        statusComboBox = new JComboBox<>(new String[] { "Tất cả", "Hoạt động", "Khóa" });
        filterPanel.add(statusComboBox);

        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Tìm kiếm:"));

        // Khởi tạo searchTypeComboBox trước khi sử dụng
        searchTypeComboBox = new JComboBox<>(
                new String[] { "Tất cả", "Email", "Tên tài khoản", "Mã nhân viên", "Mã quyền" });
        filterPanel.add(searchTypeComboBox);

        // Khởi tạo searchField trước khi sử dụng
        searchField = new JTextField(15);
        filterPanel.add(searchField);

        // Nút refresh
        JButton refreshButton = new JButton("Làm mới");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        filterPanel.add(refreshButton);

        // Thêm filterPanel vào topPanel
        topPanel.add(filterPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ===================== Table Data =======================
        String[] columnNames = { "STT", "Email", "Tên tài khoản", "Mật khẩu", "Mã nhân viên", "Mã quyền" };


        // Không cho chỉnh sửa nội dung bảng, đồng thời khởi tạo model
        model = new DefaultTableModel(originalData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Không cho kéo thả cột và khởi tạo table
        table = new JTable(model);

        table.getTableHeader().setReorderingAllowed(false);

        table.setFillsViewportHeight(true);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY)); // Đường viền phía trên

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Phương thức cho phần lọc
    private void applyFilters() {
        // Kiểm tra null để đảm bảo an toàn
        if (statusComboBox == null || searchTypeComboBox == null || searchField == null || model == null
                || originalData == null) {
            return;
        }

        String selectedStatus = (String) statusComboBox.getSelectedItem();
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        String searchText = searchField.getText().toLowerCase();

        // Xóa dữ liệu hiện tại
        model.setRowCount(0);

        // Lọc và thêm lại dữ liệu phù hợp
        for (Object[] row : originalData) {
            boolean statusMatch = true;
            boolean searchMatch = true;

            // Lọc theo trạng thái
            if (!selectedStatus.equals("Tất cả")) {
                String maQuyen = (String) row[5];
                if (selectedStatus.equals("Hoạt động") && (maQuyen.equals("Q4") || maQuyen.equals("Q5"))) {
                    statusMatch = false;
                } else if (selectedStatus.equals("Khóa") && !(maQuyen.equals("Q4") || maQuyen.equals("Q5"))) {
                    statusMatch = false;
                }
            }

            // Lọc theo tìm kiếm
            if (!searchText.isEmpty()) {
                int columnToSearch = -1;
                switch (searchType) {
                    case "Email":
                        columnToSearch = 1;
                        break;
                    case "Tên tài khoản":
                        columnToSearch = 2;
                        break;
                    case "Mã nhân viên":
                        columnToSearch = 4;
                        break;
                    case "Mã quyền":
                        columnToSearch = 5;
                        break;
                    default: // "Tất cả"
                        boolean found = false;
                        for (int i = 1; i < row.length; i++) {
                            if (row[i].toString().toLowerCase().contains(searchText)) {
                                found = true;
                                break;
                            }
                        }
                        searchMatch = found;
                        break;
                }

                if (columnToSearch != -1) {
                    searchMatch = row[columnToSearch].toString().toLowerCase().contains(searchText);
                }
            }

            if (statusMatch && searchMatch) {
                model.addRow(row);
            }
        }
    }

    private void exportToCSV() {
        try {
            // Mở cửa sổ chọn vị trí lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu file CSV");
            fileChooser.setSelectedFile(new File("TaoTaiKhoan.csv")); // Đặt tên file mặc định
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                // Đảm bảo file có phần mở rộng .csv
                if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                    fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".csv");
                }

                // Sử dụng BufferedWriter để ghi dữ liệu vào file CSV
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int rowCount = model.getRowCount();
                    int columnCount = model.getColumnCount();

                    // Ghi header (tên cột) vào file CSV
                    for (int i = 0; i < columnCount; i++) {
                        writer.write(model.getColumnName(i));
                        if (i < columnCount - 1)
                            writer.write(","); // Thêm dấu phẩy giữa các cột
                    }
                    writer.newLine(); // Xuống dòng sau khi ghi header

                    // Ghi dữ liệu vào file CSV
                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < columnCount; j++) {
                            Object value = model.getValueAt(i, j);
                            writer.write(value != null ? value.toString() : "");
                            if (j < columnCount - 1)
                                writer.write(","); // Thêm dấu phẩy giữa các cột
                        }
                        writer.newLine(); // Xuống dòng sau khi ghi từng dòng dữ liệu
                    }

                    JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất thành công.");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức nhập từ file CSV
    private void importFromExcel(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Bỏ qua dòng header
                }

                String[] rowData = line.split(",");
                model.addRow(rowData);
            }

            JOptionPane.showMessageDialog(null, "Dữ liệu đã được nhập thành công.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập file CSV: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ========================Main====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Thinh_File frame = new Thinh_File();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}
