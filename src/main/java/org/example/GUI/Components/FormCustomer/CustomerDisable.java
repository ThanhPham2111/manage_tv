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

public class CustomerDisable extends JPanel {

    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JButton button1, button2, button3, button4, button8;
    private JComboBox<String> comboBox1, comboBox2;
    private JTextField textField1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private CustomerBUS qlkh;

    public CustomerDisable() {
        qlkh = new CustomerBUS();
        initComponents();
        refresh();
        disableButtons();
    }

    private void disableButtons() {
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel 1 (Top panel with buttons and search)
        panel1 = new JPanel(new GridLayout(3, 1));

        // Panel 2 (Buttons)
        panel2 = new JPanel(new FlowLayout());
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
        panel3 = new JPanel(new FlowLayout());

        // Panel 5 (Trạng thái khách hàng)
        panel5 = new JPanel();
        panel5.setBorder(new TitledBorder("Trạng thái khách hàng"));
        panel5.setLayout(new FlowLayout());
        comboBox2 = new JComboBox<>(new String[] { "Tất cả", "Đang hoạt động", "Đã khóa" });
        comboBox2.setPreferredSize(new Dimension(200, 55));
        panel5.add(comboBox2);
        panel3.add(panel5);

        // Panel 4 (Tìm kiếm)
        panel4 = new JPanel();
        panel4.setBorder(new TitledBorder("Tìm kiếm"));
        panel4.setLayout(new FlowLayout());
        comboBox1 = new JComboBox<>(
                new String[] { "Tất cả", "Mã khách hàng", "Tên khách hàng", "Địa chỉ", "Số điện thoại", "Trạng thái" });
        textField1 = new JTextField(15);
        button8 = new JButton("Làm mới");
        panel4.add(comboBox1);
        panel4.add(textField1);
        panel3.add(panel4);
        panel3.add(button8);

        ImageIcon refreshIcon = loadImageIcon("/org/example/GUI/resources/images/icons8_data_backup_30px.png");
        if (refreshIcon != null)
            button8.setIcon(refreshIcon);

        add(panel1, BorderLayout.NORTH);

        // Table
        table1 = new JTable();
        scrollPane1 = new JScrollPane(table1);
        add(scrollPane1, BorderLayout.CENTER);

        // Set sizes
        Dimension buttonSize = new Dimension(144, 43);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);
        button8.setPreferredSize(new Dimension(140, 40));
        comboBox1.setPreferredSize(new Dimension(140, 40));
        textField1.setPreferredSize(new Dimension(144, 55));

        // Action listeners
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
                    filterByStatus();
                }
            }
        });

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

            private void performSearch() {
                String value = textField1.getText();
                String type = (String) comboBox1.getSelectedItem();
                ArrayList<CustomerDTO> result = qlkh.getList();
                if (!"Tất cả".equals(type)) {
                    result = new ArrayList<>();
                    for (CustomerDTO kh : qlkh.getList()) {
                        if (qlkh.isMatched(kh, type, value)) {
                            result.add(kh);
                        }
                    }
                }
                setDataToTable(result);
            }
        });
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

    private void filterByStatus() {
        String selectedStatus = (String) comboBox2.getSelectedItem();
        ArrayList<CustomerDTO> result;
        if ("Tất cả".equals(selectedStatus)) {
            result = qlkh.getList();
        } else {
            int status = "Đang hoạt động".equals(selectedStatus) ? 0 : 1;
            result = qlkh.filterByStatus(status);
        }
        setDataToTable(result);
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
        JFrame frame = new JFrame("Customer Disable GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CustomerDisable());
        frame.pack();
        frame.setVisible(true);
    }
}