package org.example.GUI.Components.FormCustomer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.example.BUS.CustomerBUS;
import org.example.DTO.CustomerDTO;

public class CustomerGUI extends JPanel {

    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JButton button1, button2, button3, button4, button8;
    private JComboBox<String> comboBox1, comboBox2;
    private JTextField textField1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private CustomerBUS qlkh;

    public CustomerGUI() {
        qlkh = new CustomerBUS();
        initComponents();
        refresh();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel 1 (Top panel with buttons and search)
        panel1 = new JPanel(new GridLayout(2, 1, 0, 10)); // Giảm từ 3 xuống 2 hàng để đảm bảo hiển thị

        // Panel 2 (Buttons)
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        button1 = new JButton("Thêm");
        button2 = new JButton("Xóa");
        button3 = new JButton("Sửa");
        button4 = new JButton("Xuất Excel");
        panel2.add(button1);
        panel2.add(button2);
        panel2.add(button3);
        panel2.add(button4);

        // Thêm icon cho các nút
        ImageIcon addIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_add_30px.png");
        if (addIcon != null)
            button1.setIcon(addIcon);

        ImageIcon deleteIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_cancel_30px_1.png");
        if (deleteIcon != null)
            button2.setIcon(deleteIcon);

        ImageIcon editIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_support_30px.png");
        if (editIcon != null)
            button3.setIcon(editIcon);

        ImageIcon exportIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_ms_excel_30px.png");
        if (exportIcon != null)
            button4.setIcon(exportIcon);

        panel1.add(panel2);

        // Panel 3 (Search and filter)
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        // Panel 5 (Trạng thái khách hàng)
        panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel5.setBorder(new TitledBorder("Trạng thái khách hàng"));
        comboBox2 = new JComboBox<>(new String[] { "Đang hoạt động", "Đã khóa" });
        comboBox2.setPreferredSize(new Dimension(200, 40));
        panel5.add(comboBox2);
        panel3.add(panel5);

        // Panel 4 (Tìm kiếm)
        panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel4.setBorder(new TitledBorder("Tìm kiếm"));
        textField1 = new JTextField(15);
        textField1.setPreferredSize(new Dimension(200, 40));
        panel4.add(textField1);
        panel3.add(panel4);

        // Nút Làm mới
        button8 = new JButton("Làm mới");
        ImageIcon refreshIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png");
        if (refreshIcon != null)
            button8.setIcon(refreshIcon);
        button8.setPreferredSize(new Dimension(140, 40));
        panel3.add(button8);

        panel1.add(panel3);

        add(panel1, BorderLayout.NORTH);

        // Table
        table1 = new JTable();
        scrollPane1 = new JScrollPane(table1);
        add(scrollPane1, BorderLayout.CENTER);

        // Set sizes cho các nút
        Dimension buttonSize = new Dimension(144, 43);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);

        // Action listeners
        button1.addActionListener(e -> showAddDialog());
        button2.addActionListener(e -> deleteCustomer());
        button3.addActionListener(e -> editCustomer());
        button4.addActionListener(e -> exportToExcel());
        button8.addActionListener(e -> {
            comboBox1.setSelectedIndex(0);
            textField1.setText("");
            refresh();
        });

        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    performSearch(true);
                }
            }
        });

        // Thêm sự kiện Enter cho ô tìm kiếm
        textField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    performSearch(true);
                }
            }
        });

        // Thêm sự kiện khi ô tìm kiếm mất focus
        textField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                performSearch(true);
            }
        });

        // Thêm chức năng tìm kiếm realtime
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch(false);
            }
        });
    }

    private void showAddDialog() {
        new AddButtonCustomer("Thêm", "", this);
    }

    private void deleteCustomer() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            String makh = (String) table1.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (qlkh.updateTrangthai(makh, 1)) {
                    refresh();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng cần xóa!");
        }
    }

    private void editCustomer() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            String makh = (String) table1.getValueAt(selectedRow, 1);
            new AddButtonCustomer("Sửa", makh, this);
        } else {
            JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng cần sửa!");
        }
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Khách hàng");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("STT");
            headerRow.createCell(1).setCellValue("Mã khách hàng");
            headerRow.createCell(2).setCellValue("Tên khách hàng");
            headerRow.createCell(3).setCellValue("Địa chỉ");
            headerRow.createCell(4).setCellValue("Số điện thoại");
            headerRow.createCell(5).setCellValue("Trạng thái");

            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    row.createCell(j).setCellValue(model.getValueAt(i, j).toString());
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                workbook.write(outputStream);
                JOptionPane.showMessageDialog(this, "Xuất file thành công");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Xuất file không thành công");
                e.printStackTrace();
            }
        }
    }

    private void performSearch(boolean showMessage) {
        String searchText = textField1.getText().trim();
        String selectedStatus = (String) comboBox2.getSelectedItem();
        ArrayList<CustomerDTO> result = new ArrayList<>();
        ArrayList<CustomerDTO> allCustomers = qlkh.getList();

        for (CustomerDTO customer : allCustomers) {
            boolean matches = false;

            // Kiểm tra trạng thái khách hàng
            boolean statusMatch = (selectedStatus.equals("Đang hoạt động") && customer.getTrangThai() == 0) ||
                                (selectedStatus.equals("Đã khóa") && customer.getTrangThai() == 1);

            // Kiểm tra tất cả các trường nếu có từ khóa tìm kiếm
            boolean textMatch = true;
            if (!searchText.isEmpty()) {
                textMatch = customer.getMaKH().toLowerCase().contains(searchText.toLowerCase()) ||
                           customer.getTenKH().toLowerCase().contains(searchText.toLowerCase()) ||
                           customer.getDiaChi().toLowerCase().contains(searchText.toLowerCase()) ||
                           customer.getSdt().toLowerCase().contains(searchText.toLowerCase());
            }

            matches = statusMatch && textMatch;

            if (matches) {
                result.add(customer);
            }
        }

        setDataToTable(result);

        if (showMessage && result.isEmpty() && !searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy khách hàng nào phù hợp với điều kiện tìm kiếm!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void refresh() {
        setDataToTable(qlkh.getList());
    }

    private void setDataToTable(ArrayList<CustomerDTO> data) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("STT");
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.addColumn("Địa chỉ");
        model.addColumn("Số điện thoại");
        model.addColumn("Trạng thái");

        for (int i = 0; i < data.size(); i++) {
            CustomerDTO kh = data.get(i);
            model.addRow(new Object[] {
                    i + 1,
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getDiaChi(),
                    kh.getSdt(),
                    kh.getTrangThai() == 0 ? "Đang hoạt động" : "Đã khóa"
            });
        }
        table1.setModel(model);
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Customer GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CustomerGUI());
        frame.pack();
        frame.setMinimumSize(new Dimension(800, 600)); // Đặt kích thước tối thiểu để đảm bảo hiển thị đầy đủ
        frame.setVisible(true);
    }
}
