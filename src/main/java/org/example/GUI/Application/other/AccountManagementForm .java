package GUI.Application.other;

import BUS.AccountBUS;
import DTO.AccountDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class AccountManagementForm extends JFrame {
    private JTable table;
    private JComboBox<String> statusComboBox;
    private JComboBox<String> searchTypeComboBox;
    private JTextField searchField;
    private DefaultTableModel model;
    private AccountBUS accountBUS;

    public AccountManagementForm() {
        setTitle("Quản lý tài khoản");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setSize(1000, 600);

        accountBUS = new AccountBUS();

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

        JButton addButton = new JButton("Thêm");
        JButton deleteButton = new JButton("Xóa");
        JButton editButton = new JButton("Sửa");
        JButton exportButton = new JButton("Xuất Excel");
        JButton importButton = new JButton("Nhập Excel");

        // =============== Thêm ActionListener cho các nút ==========================

        // AddButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo form nhập liệu
                JTextField emailField = new JTextField(20);
                JTextField usernameField = new JTextField(20);
                JPasswordField passwordField = new JPasswordField(20);
                JTextField employeeIdField = new JTextField(20);
                JTextField roleIdField = new JTextField(20);

                JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
                panel.setPreferredSize(new Dimension(400, 250));

                panel.add(new JLabel("Email:"));
                panel.add(emailField);
                panel.add(new JLabel("Tên tài khoản:"));
                panel.add(usernameField);
                panel.add(new JLabel("Mật khẩu:"));
                panel.add(passwordField);
                panel.add(new JLabel("Mã nhân viên:"));
                panel.add(employeeIdField);
                panel.add(new JLabel("Mã quyền:"));
                panel.add(roleIdField);

                boolean isValid = false;

                while (!isValid) {
                    int result = JOptionPane.showConfirmDialog(
                            null, panel, "Thêm tài khoản", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        String email = emailField.getText();
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());
                        String employeeId = employeeIdField.getText();
                        String roleId = roleIdField.getText();

                        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || employeeId.isEmpty()
                                || roleId.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Tất cả các trường phải được điền đầy đủ.", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        } else if (!email.contains("@")) {
                            JOptionPane.showMessageDialog(null, "Email không hợp lệ.", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Tạo account mới và thêm vào BUS
                            AccountDTO newAccount = new AccountDTO(
                                    model.getRowCount() + 1, 
                                    email, 
                                    username, 
                                    password, 
                                    employeeId, 
                                    roleId
                            );
                            
                            if (accountBUS.addAccount(newAccount)) {
                                loadAccountData(accountBUS.getAllAccounts());
                                isValid = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "Thêm tài khoản thất bại.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    if (accountBUS.deleteAccount(id)) {
                        loadAccountData(accountBUS.getAllAccounts());
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa tài khoản thất bại.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa.");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    String email = (String) table.getValueAt(row, 1);
                    String username = (String) table.getValueAt(row, 2);
                    String password = (String) table.getValueAt(row, 3);
                    String employeeId = (String) table.getValueAt(row, 4);
                    String roleId = (String) table.getValueAt(row, 5);

                    JTextField emailField = new JTextField(email, 20);
                    JTextField usernameField = new JTextField(username, 20);
                    JPasswordField passwordField = new JPasswordField(password, 20);
                    JTextField employeeIdField = new JTextField(employeeId, 20);
                    JTextField roleIdField = new JTextField(roleId, 20);

                    JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
                    panel.setPreferredSize(new Dimension(400, 250));

                    panel.add(new JLabel("Email:"));
                    panel.add(emailField);
                    panel.add(new JLabel("Tên tài khoản:"));
                    panel.add(usernameField);
                    panel.add(new JLabel("Mật khẩu:"));
                    panel.add(passwordField);
                    panel.add(new JLabel("Mã nhân viên:"));
                    panel.add(employeeIdField);
                    panel.add(new JLabel("Mã quyền:"));
                    panel.add(roleIdField);

                    boolean isValid = false;

                    while (!isValid) {
                        int result = JOptionPane.showConfirmDialog(null, panel, "Sửa tài khoản",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            String newEmail = emailField.getText();
                            String newUsername = usernameField.getText();
                            String newPassword = new String(passwordField.getPassword());
                            String newEmployeeId = employeeIdField.getText();
                            String newRoleId = roleIdField.getText();

                            if (newEmail.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()
                                    || newEmployeeId.isEmpty() || newRoleId.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Tất cả các trường phải được điền đầy đủ.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                            } else if (!newEmail.contains("@")) {
                                JOptionPane.showMessageDialog(null, "Email không hợp lệ.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                AccountDTO updatedAccount = new AccountDTO(
                                        id, newEmail, newUsername, newPassword, newEmployeeId, newRoleId
                                );
                                
                                if (accountBUS.updateAccount(updatedAccount)) {
                                    loadAccountData(accountBUS.getAllAccounts());
                                    isValid = true;
                                } else {
                                    JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thất bại.", "Lỗi",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            break;
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
                    importFromExcel(fileToImport);
                }
            }
        });

        topButtons.add(addButton);
        topButtons.add(deleteButton);
        topButtons.add(editButton);
        topButtons.add(exportButton);
        topButtons.add(importButton);
        topPanel.add(topButtons);

        // =============== Filter and search =============
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.add(new JLabel("Trạng thái tài khoản:"));

        statusComboBox = new JComboBox<>(new String[] { "Tất cả", "Hoạt động", "Khóa" });
        filterPanel.add(statusComboBox);

        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(new JLabel("Tìm kiếm:"));

        searchTypeComboBox = new JComboBox<>(
                new String[] { "Tất cả", "Email", "Tên tài khoản", "Mã nhân viên", "Mã quyền" });
        filterPanel.add(searchTypeComboBox);

        searchField = new JTextField(15);
        filterPanel.add(searchField);

        JButton refreshButton = new JButton("Làm mới");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilters();
            }
        });
        filterPanel.add(refreshButton);

        topPanel.add(filterPanel);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ===================== Table Data =======================
        String[] columnNames = { "STT", "Email", "Tên tài khoản", "Mật khẩu", "Mã nhân viên", "Mã quyền" };

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Load dữ liệu ban đầu
        loadAccountData(accountBUS.getAllAccounts());
    }

    private void loadAccountData(List<AccountDTO> accounts) {
        model.setRowCount(0);
        for (AccountDTO account : accounts) {
            model.addRow(new Object[]{
                account.getId(),
                account.getEmail(),
                account.getUsername(),
                account.getPassword(),
                account.getEmployeeId(),
                account.getRoleId()
            });
        }
    }

    private void applyFilters() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        String searchText = searchField.getText().toLowerCase();

        List<AccountDTO> filteredAccounts = accountBUS.applyFilters(selectedStatus, searchType, searchText);
        loadAccountData(filteredAccounts);
    }

    private void exportToCSV() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn vị trí lưu file CSV");
            fileChooser.setSelectedFile(new File("TaiKhoan.csv"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                    fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".csv");
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                    int rowCount = model.getRowCount();
                    int columnCount = model.getColumnCount();

                    for (int i = 0; i < columnCount; i++) {
                        writer.write(model.getColumnName(i));
                        if (i < columnCount - 1)
                            writer.write(",");
                    }
                    writer.newLine();

                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < columnCount; j++) {
                            Object value = model.getValueAt(i, j);
                            writer.write(value != null ? value.toString() : "");
                            if (j < columnCount - 1)
                                writer.write(",");
                        }
                        writer.newLine();
                    }

                    JOptionPane.showMessageDialog(null, "Dữ liệu đã được xuất thành công.");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importFromExcel(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] rowData = line.split(",");
                if (rowData.length == 6) {
                    AccountDTO account = new AccountDTO(
                            Integer.parseInt(rowData[0]),
                            rowData[1],
                            rowData[2],
                            rowData[3],
                            rowData[4],
                            rowData[5]
                    );
                    accountBUS.addAccount(account);
                }
            }

            loadAccountData(accountBUS.getAllAccounts());
            JOptionPane.showMessageDialog(null, "Dữ liệu đã được nhập thành công.");
        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi nhập file CSV: " + ex.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}