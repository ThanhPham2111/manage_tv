package org.example.GUI.FormSupplier;

import org.example.BUS.SupplierBUS;
import org.example.DTO.SupplierDTO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FormNCC extends JFrame {
    private SupplierSelectedListener listener;
    private SupplierBUS supplierBus = new SupplierBUS();
    private JComboBox<String> comboBoxSearch;
    private JTextField textFieldSearch;
    private JTable table;
    private JButton btnSelect, btnExit;
    private DefaultTableModel model;
    private JPanel bottomPanel; // Thêm biến để sửa lỗi bottomPanel

    public FormNCC() {
        setTitle("Chọn nhà cung cấp");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        initComponents();
        refresh();
    }

    public void setSupplierSelectedListener(SupplierSelectedListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JPanel topPanel = new JPanel(new GridLayout(1, 1));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout());
        topPanel.add(searchPanel);

        JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.setBorder(new TitledBorder("Tìm kiếm"));
        searchFieldPanel.setLayout(new FlowLayout());
        searchPanel.add(searchFieldPanel);

        String[] searchItems = { "Tất cả", "MaNCC", "TenNCC", "DiaChi", "SDT", "TrangThai" };
        comboBoxSearch = new JComboBox<>(searchItems);
        comboBoxSearch.setPreferredSize(new Dimension(130, 35));
        searchFieldPanel.add(comboBoxSearch);

        textFieldSearch = new JTextField();
        textFieldSearch.setPreferredSize(new Dimension(180, 35));
        textFieldSearch.setBorder(BorderFactory.createTitledBorder("Tất cả"));
        searchFieldPanel.add(textFieldSearch);

        JButton btnRefresh = new JButton("Làm mới");
        ImageIcon refreshIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png");
        if (refreshIcon != null)
            btnRefresh.setIcon(refreshIcon);
        btnRefresh.setPreferredSize(new Dimension(110, 42));
        btnRefresh.addActionListener(e -> {
            comboBoxSearch.setSelectedItem("Tất cả");
            textFieldSearch.setText("");
            refresh();
        });
        searchPanel.add(btnRefresh);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        model = new DefaultTableModel(new Object[] { "STT", "MaNCC", "TenNCC", "DiaChi", "SDT", "Fax", "TrangThai" },
                0);
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

        bottomPanel = new JPanel(new FlowLayout()); // Khởi tạo bottomPanel
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        btnSelect = new JButton("Chọn");
        ImageIcon selectIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_ok_30px.png");
        if (selectIcon != null)
            btnSelect.setIcon(selectIcon);
        btnSelect.setPreferredSize(new Dimension(110, 42));
        btnSelect.addActionListener(e -> selectSupplier());
        bottomPanel.add(btnSelect);

        btnExit = new JButton("Thoát");
        ImageIcon exitIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_cancel_30px_1.png");
        if (exitIcon != null)
            btnExit.setIcon(exitIcon);
        btnExit.setPreferredSize(new Dimension(110, 42));
        btnExit.addActionListener(e -> dispose());
        bottomPanel.add(btnExit);

        comboBoxSearch.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) comboBoxSearch.getSelectedItem();
                textFieldSearch.setBorder(BorderFactory.createTitledBorder("Nhập " + selectedItem));
            }
        });

        textFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
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

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.setRowSelectionInterval(row, row);
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem detailItem = new JMenuItem("Xem chi tiết");
                    popup.add(detailItem);

                    detailItem.addActionListener(e1 -> {
                        String maNCC = (String) table.getValueAt(row, 1);
                        SupplierDTO supplier = supplierBus.getSupplierDTO(maNCC);
                        if (supplier != null) {
                            JOptionPane.showMessageDialog(null,
                                    "Mã NCC: " + supplier.getMaNCC() + "\n" +
                                            "Tên NCC: " + supplier.getTenNCC() + "\n" +
                                            "Địa chỉ: " + supplier.getDiaChi() + "\n" +
                                            "SĐT: " + supplier.getSDT() + "\n" +
                                            "Fax: " + supplier.getFax() + "\n" +
                                            "Trạng thái: "
                                            + (supplier.getTrangThai() == 0 ? "Đang hoạt động" : "Đã xóa"));
                        }
                    });
                    popup.show(table, e.getX(), e.getY());
                }
            }
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

    private void performSearch() {
        String value = textFieldSearch.getText();
        String type = (String) comboBoxSearch.getSelectedItem();
        ArrayList<SupplierDTO> result = new ArrayList<>();
        for (SupplierDTO sup : supplierBus.getList()) {
            if (sup.getTrangThai() == 0 && (type.equals("Tất cả") || supplierBus.isMatched(sup, type, value))) {
                result.add(sup);
            }
        }
        setDataToTable(result);
    }

    private void selectSupplier() {
        SupplierDTO selectedSupplier = getSelectedSupplier();
        if (selectedSupplier != null && listener != null) {
            listener.onSupplierSelected(selectedSupplier);
            dispose();
        }
    }

    private SupplierDTO getSelectedSupplier() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String maNCC = (String) table.getValueAt(selectedRow, 1);
            return supplierBus.getSupplierDTO(maNCC);
        }
        return null;
    }

    private void refresh() {
        supplierBus.listNCC();
        setDataToTable(supplierBus.getList());
    }

    private void setDataToTable(ArrayList<SupplierDTO> data) {
        model.setRowCount(0);
        int stt = 1;
        for (SupplierDTO supplier : data) {
            if (supplier.getTrangThai() == 0) {
                model.addRow(new Object[] {
                        stt++,
                        supplier.getMaNCC(),
                        supplier.getTenNCC(),
                        supplier.getDiaChi(),
                        supplier.getSDT(),
                        supplier.getFax(),
                        supplier.getTrangThai() == 0 ? "Đang hoạt động" : "Đã xóa"
                });
            }
        }
    }

    public interface SupplierSelectedListener {
        void onSupplierSelected(SupplierDTO supplier);
    }
}