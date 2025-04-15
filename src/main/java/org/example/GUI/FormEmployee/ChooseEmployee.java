package org.example.GUI.FormEmployee;

import org.example.BUS.EmployeeBUS;
import org.example.DTO.EmployeeDTO;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class ChooseEmployee extends JFrame {

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panelFt;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JPanel panel4;
    private JTextField textField2;
    private JButton button1;
    private JTextField textField3;
    private JButton button2;
    private JButton button3;
    private JButton buttonAdd;
    private JButton buttonExit;
    private JScrollPane scrollPane1;
    private JTable table1;
    private EmployeeBUS employeeBUS = new EmployeeBUS();

    public ChooseEmployee() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        refresh();
    }

    private void initComponents() {
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panelFt = new JPanel();
        comboBox1 = new JComboBox<>();
        textField1 = new JTextField();
        panel4 = new JPanel();
        textField2 = new JTextField();
        button1 = new JButton();
        textField3 = new JTextField();
        button2 = new JButton();
        button3 = new JButton();
        buttonAdd = new JButton();
        buttonExit = new JButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        setLayout(new BorderLayout());

        panel1.setBorder(new TitledBorder(""));
        panel1.setLayout(new GridLayout(1, 1));

        panel2.setLayout(new FlowLayout());

        panel3.setBorder(new TitledBorder("Tìm kiếm"));
        panel3.setLayout(new FlowLayout());
        comboBox1.setPreferredSize(new Dimension(130, 40));
        panel3.add(comboBox1);
        textField1.setPreferredSize(new Dimension(144, 55));
        panel3.add(textField1);
        panel2.add(panel3);

        panel4.setBorder(new TitledBorder("Ngày sinh"));
        panel4.setLayout(new FlowLayout());
        textField2.setPreferredSize(new Dimension(100, 55));
        textField2.setBorder(new TitledBorder("Từ"));
        panel4.add(textField2);
        button1.setPreferredSize(new Dimension(55, 55));
        button1.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_calendar_31_30px.png")));
        panel4.add(button1);
        textField3.setPreferredSize(new Dimension(100, 55));
        textField3.setBorder(new TitledBorder("Đến"));
        panel4.add(textField3);
        button2.setPreferredSize(new Dimension(55, 55));
        button2.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_calendar_31_30px.png")));
        panel4.add(button2);
        panel2.add(panel4);

        button3.setText("Làm mới");
        button3.setPreferredSize(new Dimension(144, 55));
        button3.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_data_backup_30px.png")));
        panel2.add(button3);

        panel1.add(panel2);
        add(panel1, BorderLayout.NORTH);

        table1.setModel(new DefaultTableModel());
        scrollPane1.setViewportView(table1);
        add(scrollPane1, BorderLayout.CENTER);

        panelFt.setLayout(new FlowLayout());
        buttonAdd.setText("Chọn");
        buttonAdd.setPreferredSize(new Dimension(130, 55));
        buttonAdd
                .setIcon(new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_ok_30px.png")));
        panelFt.add(buttonAdd);
        buttonExit.setText("Thoát");
        buttonExit.setPreferredSize(new Dimension(130, 55));
        buttonExit.setIcon(
                new ImageIcon(getClass().getResource("/org/example/GUI/resources/images/icons8_cancel_30px_1.png")));
        panelFt.add(buttonExit);
        add(panelFt, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        // Thiết lập font
        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontHeader = new Font("Segoe UI", Font.BOLD, 16);
        table1.setFont(font);
        table1.getTableHeader().setFont(fontHeader);
        table1.setRowHeight(30);
        TableColumnModel columnModel = table1.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(150);
        }

        // Thêm các mục cho comboBox1
        String[] items = { "Tất cả", "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Địa chỉ", "Số điện thoại",
                "Trạng thái" };
        for (String item : items) {
            comboBox1.addItem(item);
        }

        // Sự kiện khi chọn mục trong comboBox1
        textField1.setBorder(new TitledBorder("Tất cả"));
        comboBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) comboBox1.getSelectedItem();
                textField1.setBorder(new TitledBorder(selectedItem));
            }
        });

        // Sự kiện nút Làm mới
        button3.addActionListener(e -> {
            comboBox1.setSelectedItem("Tất cả");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            refresh();
        });

        // Tìm kiếm
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
                ArrayList<EmployeeDTO> result = employeeBUS.search(value, type);
                setDataToTable(result);
            }
        });

        // Chọn ngày sinh bắt đầu
        button1.addActionListener(e -> showDatePicker(textField2));

        // Chọn ngày sinh kết thúc
        button2.addActionListener(e -> showDatePicker(textField3, textField2));

        // Sự kiện nút Chọn
        buttonAdd.addActionListener(e -> {
            String selectedEmployeeId = getSelectedEmployeeId();
            if (selectedEmployeeId == null) {
                JOptionPane.showMessageDialog(null, "Chưa chọn nhân viên");
                return;
            }
            dispose();
        });

        // Sự kiện nút Thoát
        buttonExit.addActionListener(e -> dispose());
    }

    private void showDatePicker(JTextField targetField) {
        showDatePicker(targetField, null);
    }

    private void showDatePicker(JTextField targetField, JTextField startField) {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.addActionListener(e -> {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(null, "Chưa chọn ngày", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(selectedDate);
            targetField.setText(dateString);

            if (startField != null && !startField.getText().isEmpty()) {
                try {
                    Date startDate = formatter.parse(startField.getText());
                    if (startDate.after(selectedDate)) {
                        JOptionPane.showMessageDialog(null, "Ngày kết thúc phải sau ngày bắt đầu");
                        targetField.setText("");
                        return;
                    }
                    filterByDateRange();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ");
                }
            }
        });
        JOptionPane.showMessageDialog(null, datePicker);
    }

    private void filterByDateRange() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate startDate = LocalDate.parse(textField2.getText(), formatter);
            LocalDate endDate = LocalDate.parse(textField3.getText(), formatter);
            ArrayList<EmployeeDTO> result = employeeBUS.getList().stream()
                    .filter(employee -> {
                        LocalDate birthDate = employee.getNgaySinh().toLocalDate();
                        return (birthDate.isAfter(startDate) || birthDate.isEqual(startDate)) &&
                                (birthDate.isBefore(endDate) || birthDate.isEqual(endDate));
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
            setDataToTable(result);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
        }
    }

    public void addButtonAddActionListener(ActionListener actionListener) {
        buttonAdd.addActionListener(actionListener);
    }

    public String getSelectedEmployeeId() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            return table1.getValueAt(selectedRow, 1).toString();
        }
        return null;
    }

    public void refresh() {
        employeeBUS.readDB();
        setDataToTable(employeeBUS.getList());
    }

    public void setDataToTable(ArrayList<EmployeeDTO> data) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("STT");
        tableModel.addColumn("Mã nhân viên");
        tableModel.addColumn("Tên nhân viên");
        tableModel.addColumn("Ngày sinh");
        tableModel.addColumn("Địa chỉ");
        tableModel.addColumn("Số điện thoại");
        tableModel.addColumn("Trạng thái");

        int stt = 1;
        for (EmployeeDTO employee : data) {
            if (employee.getTrangThai() == 0) {
                tableModel.addRow(new Object[] {
                        stt++,
                        employee.getMaNV(),
                        employee.getTenNV(),
                        employee.getNgaySinh(),
                        employee.getDiaChi(),
                        employee.getSdt(),
                        employee.getTrangThai() == 0 ? "Đang hoạt động" : "Ngừng hoạt động"
                });
            }
        }
        table1.setModel(tableModel);
    }

    // Lớp hỗ trợ định dạng ngày cho JDatePicker
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                return dateFormatter.format(value);
            }
            return "";
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new ChooseEmployee());
    }
}