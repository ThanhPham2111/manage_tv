package org.example.GUI.Components.FormHoaDon;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.example.BUS.InvoiceBUS;
import org.example.DTO.InvoiceDTO;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Flow;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class InvoiceView extends JPanel {
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");
    private InvoiceBUS invoiceBUS;
    private JPanel mainPanel, topContainer;

    private DefaultTableModel invoiceTableModel;
    private JTable invoiceTable;
    private JScrollPane sp;

    private JButton exportExcel;
    private JPanel infoPanel;
    private JLabel invoiceIDLabel, customerLabel, employeeLabel, dateLabel, totalLabel;
    private JButton invoiceDetailBtn;

    private JPanel filterPanel;
    private JTextField invoiceIDTF, customerSearchTF, employeeSearchTF;
    private JButton applyFilterBtn, resetFilterBtn;
    private JComboBox<String> dateChooserCb;

    private JPanel
    // invoiceIDContainer,
    // customerSearchContainer,
    // employeeSearchContainer,
    // dateChooserContainer,
    customDateContainer,
            buttonContainer;

    private Font titleFont;

    public InvoiceView() {
        invoiceBUS = new InvoiceBUS();
        setLayout(new BorderLayout());
        titleFont = new Font("Segoe UI", Font.BOLD, 16);
        initComponents();
    }

    private void initComponents() {
        initMainPanel();
        add(mainPanel);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(new BorderLayout(11, 11));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        initTopContainer();
        initTable();
        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(sp, BorderLayout.CENTER);
        exportExcel = new JButton("Xuất Excel");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottomPanel.add(exportExcel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopContainer() {
        initInfoPanel();
        initFilterPanel();

        topContainer = new JPanel(new BorderLayout(11, 11));
        topContainer.add(infoPanel, BorderLayout.WEST);
        topContainer.add(filterPanel, BorderLayout.CENTER);
    }

    private void initTable() {
        String[] columnsNames = { "Mã hoá đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Giờ lập", "Tổng tiền" };
        invoiceTableModel = new DefaultTableModel(columnsNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<InvoiceDTO> invoices = invoiceBUS.getAllInvoices();
        invoiceTableModel.setRowCount(0);

        for (InvoiceDTO invoice : invoices) {
            invoiceTableModel.addRow(new Object[] {
                    invoice.getMaHoaDon(),
                    invoice.getMaKhachHang(),
                    invoice.getMaNhanVien(),
                    new SimpleDateFormat("dd/MM/yyyy").format(invoice.getNgayLap()),
                    new SimpleDateFormat("HH:mm:ss").format(invoice.getGioNhap()),
                    priceFormatter.format(invoice.getTongTien()) + " VNĐ"
            });
        }

        invoiceTable = new JTable(invoiceTableModel);
        invoiceTable.setRowHeight(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        for (int i = invoiceTable.getColumnCount() - 1; i >= 0; i--) {
            invoiceTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        invoiceTable.getColumnModel().getColumn(invoiceTable.getColumnCount() - 1).setCellRenderer(rightRenderer);
        invoiceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRowIndex = invoiceTable.getSelectedRow();
                updateInfoPanel(selectedRowIndex);
            }
        });

        JTableHeader header = invoiceTable.getTableHeader();

        header.setFont(titleFont);
        sp = new JScrollPane(invoiceTable);
    }

    private void initInfoPanel() {
        infoPanel = new JPanel(new GridLayout(6, 1));
        invoiceIDLabel = new JLabel("________");
        customerLabel = new JLabel("________");
        employeeLabel = new JLabel("________");
        dateLabel = new JLabel("________");
        totalLabel = new JLabel("________");

        TitledBorder border = BorderFactory.createTitledBorder("Thông tin hoá đơn");
        border.setTitleFont(titleFont);
        infoPanel.setBorder(border);

        infoPanel.setPreferredSize(new Dimension(355, 0));
        infoPanel.add(createRow_LBCPN("Mã hoá đơn: ", 95, invoiceIDLabel));
        infoPanel.add(createRow_LBCPN("Mã khách hàng: ", 95, customerLabel));
        infoPanel.add(createRow_LBCPN("Mã nhân viên: ", 95, employeeLabel));
        infoPanel.add(createRow_LBCPN("Ngày lập: ", 95, dateLabel));
        infoPanel.add(createRow_LBCPN("Tổng tiền: ", 95, totalLabel));

        invoiceDetailBtn = new JButton("Xem chi tiết");
        JPanel tmp = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        invoiceDetailBtn.addActionListener((e) -> {

        });
        tmp.add(invoiceDetailBtn);
        infoPanel.add(tmp);
    }

    private void initFilterPanel() {
        filterPanel = new JPanel(new GridLayout(6, 1, 0, 5));

        TitledBorder border = BorderFactory.createTitledBorder("Bộ lọc");
        border.setTitleFont(titleFont);
        filterPanel.setBorder(border);

        invoiceIDTF = new JTextField(11);
        customerSearchTF = new JTextField(11);
        employeeSearchTF = new JTextField(11);

        String[] dateItems = { "Tất cả", "Hôm nay", "Trong 7 ngày", "Tháng này", "Tuỳ chọn" };

        dateChooserCb = new JComboBox<>(dateItems);
        dateChooserCb.setPreferredSize(customerSearchTF.getPreferredSize());

        JSpinner beginDate = new JSpinner(new SpinnerDateModel());
        beginDate.setEditor(new JSpinner.DateEditor(beginDate, "dd/MM/yyyy"));
        JSpinner endDate = new JSpinner(new SpinnerDateModel());
        endDate.setEditor(new JSpinner.DateEditor(endDate, "dd/MM/yyyy"));

        // invoiceIDContainer = new JPanel(new GridLayout(1, 2));
        // customerSearchContainer = new JPanel(new GridLayout(1, 2));
        // employeeSearchContainer = new JPanel(new GridLayout(1, 2));
        // dateChooserContainer = new JPanel(new GridLayout(1, 2));

        // invoiceIDContainer.add(new JLabel("Mã hoá đơn: "));
        // invoiceIDContainer.add(invoiceIDTF);

        // customerSearchContainer.add(new JLabel("Khách hàng: "));
        // customerSearchContainer.add(customerSearchTF);

        // employeeSearchContainer.add(new JLabel("Nhân viên: "));
        // employeeSearchContainer.add(employeeSearchTF);

        // dateChooserContainer.add(new JLabel("Chọn ngày: "));
        // dateChooserContainer.add(dateChooserCb);

        customDateContainer = new JPanel(new FlowLayout(FlowLayout.LEADING));
        buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));

        customDateContainer.add(new JLabel("Từ ngày"));
        customDateContainer.add(beginDate);
        customDateContainer.add(new JLabel("đến ngày"));
        customDateContainer.add(endDate);
        customDateContainer.setVisible(false);

        applyFilterBtn = new JButton("Tìm kiếm ");
        resetFilterBtn = new JButton("Xoá bộ lọc");

        resetFilterBtn.addActionListener(e -> resetFilter());

        buttonContainer.add(applyFilterBtn);
        buttonContainer.add(resetFilterBtn);

        // filterPanel.add(invoiceIDContainer);
        // filterPanel.add(customerSearchContainer);
        // filterPanel.add(employeeSearchContainer);
        // filterPanel.add(dateChooserContainer);
        // filterPanel.add(customDateContainer);
        // filterPanel.add(buttonContainer);

        dateChooserCb.addActionListener((e) -> {
            String selectedItem = (String) (dateChooserCb.getSelectedItem());
            if ("Tuỳ chọn".equals(selectedItem)) {
                customDateContainer.setVisible(true);
            } else {
                customDateContainer.setVisible(false);
            }
        });

        filterPanel.add(createRow_LBCPN("Mã hoá đơn: ", 95, invoiceIDTF));
        filterPanel.add(createRow_LBCPN("Khách hàng: ", 95, customerSearchTF));
        filterPanel.add(createRow_LBCPN("Nhân viên: ", 95, employeeSearchTF));
        filterPanel.add(createRow_LBCPN("Chọn ngày: ", 95, dateChooserCb));
        filterPanel.add(customDateContainer);
        filterPanel.add(buttonContainer);

    }

    public JPanel createRow_LBCPN(String labelText, int labelWidth, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel lb = new JLabel(labelText);
        lb.setPreferredSize(new Dimension(labelWidth, lb.getPreferredSize().height));

        panel.add(lb);
        panel.add(component);
        return panel;
    }

    public void updateInfoPanel(int selectedRowIndex) {
        if (selectedRowIndex >= 0) {
            String maHoaDon = invoiceTableModel.getValueAt(selectedRowIndex, 0).toString();
            InvoiceDTO invoice = invoiceBUS.getInvoice(maHoaDon);

            infoPanel.setIgnoreRepaint(true);
            invoiceIDLabel.setText(invoice.getMaHoaDon());
            customerLabel.setText(invoice.getMaKhachHang());
            employeeLabel.setText(invoice.getMaNhanVien());
            dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(invoice.getNgayLap()));
            totalLabel.setText(priceFormatter.format(invoice.getTongTien()) + " VNĐ");
            infoPanel.setIgnoreRepaint(false);
            infoPanel.repaint();
        }
    }

    public void resetFilter() {
        filterPanel.setIgnoreRepaint(true);

        invoiceIDTF.setText("");
        customerSearchTF.setText("");
        employeeSearchTF.setText("");
        dateChooserCb.setSelectedIndex(0);
        customDateContainer.setVisible(false);

        filterPanel.setIgnoreRepaint(false);
        filterPanel.repaint();
    }

    public static void main(String[] args) {
        new InvoiceView();
    }
}