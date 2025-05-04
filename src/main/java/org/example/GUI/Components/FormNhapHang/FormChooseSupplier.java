package org.example.GUI.Components.FormNhapHang;

import org.example.BUS.SupplierBUS;
import org.example.DTO.SupplierDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FormChooseSupplier extends JPanel {
    private JTable table1;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JButton button1; // Refresh
    private JButton button2; // Select
    private JButton button3; // Exit
    private DefaultTableModel tableModel;
    private SupplierBUS supplierBUS;
    private SupplierSelectedListener listener;

    public FormChooseSupplier() {
        supplierBUS = new SupplierBUS();
        setBorder(new EmptyBorder(10, 10, 10, 10));
        initComponents();
        refresh();
    }

    public void setSupplierSelectedListener(SupplierSelectedListener listener) {
        this.listener = listener;
        System.out.println(
                "SupplierSelectedListener set: " + (listener != null ? listener.getClass().getName() : "null"));
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // ======== Top Panel (Search and Refresh) ========
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(new TitledBorder("Tìm kiếm"));

        comboBox1 = new JComboBox<>(new String[] {
                "Tất cả", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại"
        });
        comboBox1.setPreferredSize(new Dimension(150, 35));
        comboBox1.setToolTipText("Chọn tiêu chí tìm kiếm");
        searchPanel.add(comboBox1);

        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(200, 35));
        textField1.setBorder(BorderFactory.createTitledBorder("Tất cả"));
        textField1.setToolTipText("Nhập từ khóa tìm kiếm");
        searchPanel.add(textField1);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(searchPanel, gbc);

        // Refresh Button
        button1 = new JButton("Làm mới");
        button1.setPreferredSize(new Dimension(120, 40));
        button1.setToolTipText("Làm mới danh sách nhà cung cấp");
        try {
            button1.setIcon(new ImageIcon(
                    getClass().getResource("/org/example/GUI/resources/images/icons8_data_backup_30px.png")));
        } catch (Exception e) {
            System.out.println("Warning: Could not load refresh icon: " + e.getMessage());
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(button1, gbc);

        add(topPanel, BorderLayout.NORTH);

        // ======== Table ========
        tableModel = new DefaultTableModel(
                new String[] { "STT", "Mã nhà cung cấp", "Tên nhà cung cấp", "Địa chỉ", "Số điện thoại", "Trạng thái" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1 = new JTable(tableModel);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table1.setRowHeight(30);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setGridColor(Color.LIGHT_GRAY);
        table1.setShowGrid(true);

        // Custom renderer for Status column
        table1.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value.equals("Đang hoạt động")) {
                    c.setForeground(new Color(0, 128, 0)); // Green for active
                } else {
                    c.setForeground(Color.RED); // Red for inactive
                }
                return c;
            }
        });

        TableColumnModel columnModel = table1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // STT
        columnModel.getColumn(1).setPreferredWidth(100); // Mã nhà cung cấp
        columnModel.getColumn(2).setPreferredWidth(150); // Tên nhà cung cấp
        columnModel.getColumn(3).setPreferredWidth(200); // Địa chỉ
        columnModel.getColumn(4).setPreferredWidth(120); // Số điện thoại
        columnModel.getColumn(5).setPreferredWidth(100); // Trạng thái

        JScrollPane scrollPane1 = new JScrollPane(table1);
        add(scrollPane1, BorderLayout.CENTER);

        // ======== Bottom Panel (Select and Exit) ========
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        button2 = new JButton("Chọn");
        button2.setPreferredSize(new Dimension(120, 40));
        button2.setToolTipText("Chọn nhà cung cấp được chọn");
        try {
            button2.setIcon(
                    new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_ok_30px.png")));
        } catch (Exception e) {
            System.out.println("Warning: Could not load select icon: " + e.getMessage());
        }
        bottomPanel.add(button2);

        button3 = new JButton("Thoát");
        button3.setPreferredSize(new Dimension(120, 40));
        button3.setToolTipText("Đóng cửa sổ");
        try {
            button3.setIcon(new ImageIcon(
                    getClass().getResource("/org/example/GUI/resources/images/icons8_cancel_30px_1.png")));
        } catch (Exception e) {
            System.out.println("Warning: Could not load exit icon: " + e.getMessage());
        }
        bottomPanel.add(button3);

        add(bottomPanel, BorderLayout.SOUTH);

        // ======== Event Listeners ========

        // ComboBox: Update textField border
        comboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) comboBox1.getSelectedItem();
                textField1.setBorder(BorderFactory.createTitledBorder(
                        switch (selectedItem) {
                            case "Tất cả" -> "Tất cả";
                            case "Mã nhà cung cấp" -> "Nhập mã nhà cung cấp";
                            case "Tên nhà cung cấp" -> "Nhập tên nhà cung cấp";
                            case "Số điện thoại" -> "Nhập số điện thoại";
                            case "Địa chỉ" -> "Nhập địa chỉ";
                            default -> "Tất cả";
                        }));
            }
        });

        // Button1: Refresh
        button1.addActionListener(e -> {
            comboBox1.setSelectedItem("Tất cả");
            textField1.setText("");
            refresh();
        });

        // TextField: Search
        textField1.getDocument().addDocumentListener(new DocumentListener() {
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
        });

        // Table: Right-click for details
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table1.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table1.setRowSelectionInterval(row, row);
                        JPopupMenu popup = new JPopupMenu();
                        JMenuItem detailItem = new JMenuItem("Xem chi tiết");
                        popup.add(detailItem);

                        detailItem.addActionListener(actionEvent -> {
                            String maNCC = (String) table1.getValueAt(row, 1);
                            SupplierDTO supplier = supplierBUS.getSupplierDTO(maNCC);
                            if (supplier != null) {
                                JOptionPane.showMessageDialog(
                                        FormChooseSupplier.this,
                                        """
                                                Mã nhà cung cấp: %s
                                                Tên nhà cung cấp: %s
                                                Địa chỉ: %s
                                                Số điện thoại: %s
                                                Fax: %s
                                                Trạng thái: %s"""
                                                .formatted(
                                                        supplier.getMaNCC(),
                                                        supplier.getTenNCC(),
                                                        supplier.getDiaChi(),
                                                        supplier.getSDT(),
                                                        supplier.getFax(),
                                                        supplier.getTrangThai() == 0 ? "Đang hoạt động"
                                                                : "Ngừng hoạt động"),
                                        "Chi tiết nhà cung cấp",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(
                                        FormChooseSupplier.this,
                                        "Không tìm thấy nhà cung cấp",
                                        "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        popup.show(table1, e.getX(), e.getY());
                    }
                }
            }
        });

        // Button2: Select
        button2.addActionListener(e -> {
            SupplierDTO selectedSupplier = getSelectedSupplier();
            if (selectedSupplier == null) {
                JOptionPane.showMessageDialog(
                        FormChooseSupplier.this,
                        "Vui lòng chọn một nhà cung cấp",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (listener == null) {
                System.out.println("No listener set for supplier selection: " + selectedSupplier.getMaNCC());
                JOptionPane.showMessageDialog(
                        FormChooseSupplier.this,
                        "Không có listener để xử lý lựa chọn nhà cung cấp",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                System.out.println("Triggering listener with supplier: " + selectedSupplier.getMaNCC());
                listener.onSupplierSelected(selectedSupplier);
                Container parent = getParent();
                while (parent != null && !(parent instanceof JDialog)) {
                    parent = parent.getParent();
                }
                if (parent instanceof JDialog) {
                    ((JDialog) parent).dispose();
                } else {
                    System.out.println("No parent JDialog found for closing");
                }
            } catch (Exception ex) {
                System.err.println("Error in listener: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        FormChooseSupplier.this,
                        "Lỗi khi chọn nhà cung cấp: " + ex.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Button3: Exit
        button3.addActionListener(e -> {
            Container parent = getParent();
            while (parent != null && !(parent instanceof JDialog)) {
                parent = parent.getParent();
            }
            if (parent instanceof JDialog) {
                ((JDialog) parent).dispose();
            } else {
                System.out.println("No parent JDialog found for exit");
            }
        });
    }

    public String getSelectedSupplierId() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            return (String) table1.getValueAt(selectedRow, 1);
        }
        return null;
    }

    public SupplierDTO getSelectedSupplier() {
        String selectedSupplierId = getSelectedSupplierId();
        if (selectedSupplierId != null) {
            return supplierBUS.getSupplierDTO(selectedSupplierId);
        }
        return null;
    }

    public void refresh() {
        supplierBUS.listNCC();
        ArrayList<SupplierDTO> suppliers = supplierBUS.getList();
        System.out.println("Refreshing table with " + (suppliers != null ? suppliers.size() : 0) + " suppliers");
        setDataToTable(suppliers);
    }

    private void performSearch() {
        String value = textField1.getText();
        String type = (String) comboBox1.getSelectedItem();
        ArrayList<SupplierDTO> result = new ArrayList<>();
        for (SupplierDTO supplier : supplierBUS.getList()) {
            if (type.equals("Tất cả") || supplierBUS.isMatched(supplier, type, value)) {
                result.add(supplier);
            }
        }
        setDataToTable(result);
    }

    public void setDataToTable(ArrayList<SupplierDTO> data) {
        tableModel.setRowCount(0);
        if (data == null || data.isEmpty()) {
            System.out.println("No suppliers to display in table");
            return;
        }
        int stt = 1;
        for (SupplierDTO supplier : data) {
            tableModel.addRow(new Object[] {
                    stt++,
                    supplier.getMaNCC(),
                    supplier.getTenNCC(),
                    supplier.getDiaChi(),
                    supplier.getSDT(),
                    supplier.getTrangThai() == 0 ? "Đang hoạt động" : "Ngừng hoạt động"
            });
        }
        System.out.println("Loaded " + data.size() + " suppliers into table");
    }

    public interface SupplierSelectedListener {
        void onSupplierSelected(SupplierDTO supplier);
    }
}