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
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.example.BUS.InvoiceBUS;
import org.example.DTO.InvoiceDTO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Date;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class InvoiceView extends JPanel {
    private final int HGAP = 11;
    private final int VGAP = 11;
    private final int BUTTON_WIDTH = 125;
    private final int LABEL_PREFERRED_WIDTH = 95;
    private final int TEXTFIELD_COLUMNS = 17;
    private final int PADDING = 7;
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");

    private Font titleFont;
    private InvoiceBUS invoiceBUS;

    private JPanel topContainer;

    private JPanel centerContainer;
    private DefaultTableModel invoiceTableModel;
    private JTable invoiceTable;
    private JScrollPane sp;
    private JLabel emptyLabel;

    private JPanel bottomContainer;
    private JButton exportExcelBtn;

    private JPanel infoPanel;
    private JLabel invoiceIDLabel, customerLabel, employeeLabel, dateLabel, totalLabel;
    private JButton invoiceDetailBtn;

    private JPanel filterPanel;
    private JTextField txtInvoiceID, txtCustomerID, txtEmployeeID;
    private JComboBox<String> dateChooserCb;
    JSpinner beginDateSpn, endDateSpn;
    private JPanel customDateContainer, buttonContainer;
    private JButton applyFilterBtn, resetFilterBtn;
    
    public InvoiceView() {
        titleFont = new Font("Segoe UI", Font.BOLD, 16);
        invoiceBUS = new InvoiceBUS();

        setLayout(new BorderLayout(HGAP, VGAP));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        initTopContainer();
        initCenterContainer();
        initBottomContainer();

        add(topContainer, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);

        setActionListener();
    }

    private void initTopContainer() {
        initInfoPanel();
        initFilterPanel();

        topContainer = new JPanel(new BorderLayout(HGAP, VGAP));
        topContainer.add(infoPanel, BorderLayout.WEST);
        topContainer.add(filterPanel, BorderLayout.CENTER);
    }

    private void initCenterContainer() {
        centerContainer = new JPanel();
        centerContainer.setLayout(new OverlayLayout(centerContainer));
        String[] columnsNames = { "Mã hoá đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Giờ lập", "Tổng tiền" };
        invoiceTableModel = new DefaultTableModel(columnsNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        invoiceTable = new JTable(invoiceTableModel);
        sp = new JScrollPane(invoiceTable);

        emptyLabel = new JLabel("DANH SÁCH TRỐNG", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 32));
        emptyLabel.setForeground(Color.GRAY);
        emptyLabel.setAlignmentX(0.5f);
        emptyLabel.setAlignmentY(0.5f);

        centerContainer.add(emptyLabel);
        centerContainer.add(sp);

        styleTable();
        loadAllInvoices();
    }

    private void initBottomContainer(){
        bottomContainer = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        exportExcelBtn = new JButton("Xuất Excel");
        exportExcelBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, invoiceDetailBtn.getPreferredSize().height));
        bottomContainer.add(exportExcelBtn);
    }

    private void initInfoPanel() {
        infoPanel = new JPanel(new GridLayout(6, 1));
        infoPanel.setPreferredSize(new Dimension(355, 0));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            createTitledBorder("Thông tin hoá đơn"),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING))
        );

        invoiceIDLabel = new JLabel();
        customerLabel = new JLabel();
        employeeLabel = new JLabel();
        dateLabel = new JLabel();
        totalLabel = new JLabel();

        invoiceDetailBtn = new JButton("Xem chi tiết");
        invoiceDetailBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, invoiceDetailBtn.getPreferredSize().height));
        JPanel tmp = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        tmp.add(invoiceDetailBtn);

        infoPanel.add(createRow("Mã hoá đơn: ", LABEL_PREFERRED_WIDTH, invoiceIDLabel));
        infoPanel.add(createRow("Khách hàng: ", LABEL_PREFERRED_WIDTH, customerLabel));
        infoPanel.add(createRow("Nhân viên: ", LABEL_PREFERRED_WIDTH, employeeLabel));
        infoPanel.add(createRow("Thời gian lập: ", LABEL_PREFERRED_WIDTH, dateLabel));
        infoPanel.add(createRow("Tổng tiền: ", LABEL_PREFERRED_WIDTH, totalLabel));
        infoPanel.add(tmp);
    }

    private void initFilterPanel() {
        filterPanel = new JPanel(new GridLayout(6, 1, 0, 5));
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            createTitledBorder("Bộ lọc"),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING))
        );

        txtInvoiceID = new JTextField(TEXTFIELD_COLUMNS);
        txtCustomerID = new JTextField(TEXTFIELD_COLUMNS);
        txtEmployeeID = new JTextField(TEXTFIELD_COLUMNS);

        String[] dateItems = { "Tất cả", "Hôm nay", "Hôm qua", "Tuần này", "Tháng này", "Năm nay", "Tuỳ chọn" };

        dateChooserCb = new JComboBox<>(dateItems);
        
        dateChooserCb.setPreferredSize(txtCustomerID.getPreferredSize());
        

        beginDateSpn = new JSpinner(new SpinnerDateModel());
        beginDateSpn.setEditor(new JSpinner.DateEditor(beginDateSpn, "dd/MM/yyyy"));
        endDateSpn = new JSpinner(new SpinnerDateModel());
        endDateSpn.setEditor(new JSpinner.DateEditor(endDateSpn, "dd/MM/yyyy"));

        customDateContainer = new JPanel(new FlowLayout(FlowLayout.LEADING));
        buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));

        customDateContainer.add(new JLabel("Từ ngày"));
        customDateContainer.add(beginDateSpn);
        customDateContainer.add(new JLabel("đến ngày"));
        customDateContainer.add(endDateSpn);
        customDateContainer.setVisible(false);

        applyFilterBtn = new JButton("Tìm kiếm ");
        applyFilterBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, invoiceDetailBtn.getPreferredSize().height));
        resetFilterBtn = new JButton("Xoá bộ lọc");
        resetFilterBtn.setPreferredSize(new Dimension(BUTTON_WIDTH - 35, invoiceDetailBtn.getPreferredSize().height));

        buttonContainer.add(applyFilterBtn);
        buttonContainer.add(resetFilterBtn);

        filterPanel.add(createRow("Mã hoá đơn: ", LABEL_PREFERRED_WIDTH, txtInvoiceID));
        filterPanel.add(createRow("Mã khách hàng: ", LABEL_PREFERRED_WIDTH, txtCustomerID));
        filterPanel.add(createRow("Mã nhân viên: ", LABEL_PREFERRED_WIDTH, txtEmployeeID));
        filterPanel.add(createRow("Chọn ngày: ", LABEL_PREFERRED_WIDTH, dateChooserCb));
        filterPanel.add(customDateContainer);
        filterPanel.add(buttonContainer);
    }

    public void updateInfoPanel(int selectedRowIndex) {
        if (selectedRowIndex >= 0) {
            String maHoaDon = invoiceTableModel.getValueAt(selectedRowIndex, 0).toString();
            InvoiceDTO invoice = invoiceBUS.getInvoice(maHoaDon);

            infoPanel.setIgnoreRepaint(true);

            invoiceIDLabel.setText(invoice.getMaHoaDon());
            customerLabel.setText(invoice.getMaKhachHang() + " - " + invoice.getTenKhachHang());
            employeeLabel.setText(invoice.getMaNhanVien() + " - " + invoice.getTenNhanVien());
            dateLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(invoice.getNgayLap()) + " - "
                            + new SimpleDateFormat("HH:mm:ss").format(invoice.getGioNhap()));
            totalLabel.setText(priceFormatter.format(invoice.getTongTien()) + " VNĐ");
            
            infoPanel.setIgnoreRepaint(false);
            infoPanel.repaint();
        }
    }
    
    public void clearInfoPanel(){
        infoPanel.setIgnoreRepaint(true);
        
        invoiceIDLabel.setText("________");
        customerLabel.setText("________");
        employeeLabel.setText("________");
        dateLabel.setText("________");
        totalLabel.setText("________");

        infoPanel.setIgnoreRepaint(false);
        infoPanel.repaint();
    }

    public void applyFilter(){
        String maHoaDon = txtInvoiceID.getText().trim().toUpperCase();
        String maKhachHang = txtCustomerID.getText().trim().toUpperCase();
        String maNhanVien = txtEmployeeID.getText().trim().toUpperCase();
        String dateOption = (String) dateChooserCb.getSelectedItem();
        Date beginDate, endDate;
        Date customBeginDate, customEndDate;

        Date[] refDates = new Date[3];
        //refDates[0]: beginDate
        //refDates[1]: endDate
        
        customBeginDate = (Date) beginDateSpn.getValue();
        customEndDate = (Date) endDateSpn.getValue();

        invoiceBUS.setBeginAndEndDate(dateOption, refDates, customBeginDate, customEndDate);
        beginDate = refDates[0];
        endDate = refDates[1];

        ArrayList<InvoiceDTO> invoices = invoiceBUS.getFilteredInvoices(maHoaDon,
                                                                        maKhachHang,
                                                                        maNhanVien,
                                                                        beginDate,
                                                                        endDate);
        loadInvoices(invoices);
    }

    public void resetFilter() {
        filterPanel.setIgnoreRepaint(true);

        txtInvoiceID.setText("");
        txtCustomerID.setText("");
        txtEmployeeID.setText("");
        dateChooserCb.setSelectedIndex(0);
        customDateContainer.setVisible(false);

        filterPanel.setIgnoreRepaint(false);
        filterPanel.repaint();

        loadAllInvoices();
    }

    public void setActionListener(){
        invoiceDetailBtn.addActionListener((e) -> {
            // Chưa hoàn thành
        });

        applyFilterBtn.addActionListener(e -> applyFilter());

        resetFilterBtn.addActionListener(e -> resetFilter());

        dateChooserCb.addActionListener((e) -> {
            String selectedItem = (String) (dateChooserCb.getSelectedItem());
            if ("Tuỳ chọn".equals(selectedItem)) {
                customDateContainer.setVisible(true);
            } else {
                customDateContainer.setVisible(false);
            }
        });

        ListSelectionModel model = invoiceTable.getSelectionModel();
        model.addListSelectionListener(e -> {
            int selectedRowIndex = invoiceTable.getSelectedRow();
            updateInfoPanel(selectedRowIndex);
        });
    }

    public void loadAllInvoices(){
        ArrayList<InvoiceDTO> invoices = invoiceBUS.getAllInvoices();
        loadInvoices(invoices);
    }

    public void loadInvoices(ArrayList<InvoiceDTO> invoices){
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

        checkEmptyTable();
        clearInfoPanel();
    }

    public void styleTable(){
        invoiceTable.setRowHeight(35);
        invoiceTable.setShowGrid(true);
        invoiceTable.setGridColor(new Color(230, 230, 230));
        JTableHeader header = invoiceTable.getTableHeader();

        header.setFont(titleFont);
        header.setOpaque(true);
        header.setBackground(new Color(70, 130, 180)); // Steel blue
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        for (int i = invoiceTable.getColumnCount() - 1; i >= 0; i--) {
            invoiceTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        invoiceTable.getColumnModel().getColumn(invoiceTable.getColumnCount() - 1).setCellRenderer(rightRenderer);
    }

    public void checkEmptyTable(){
        emptyLabel.setVisible(invoiceTable.getRowCount() == 0);
    }

    public TitledBorder createTitledBorder(String title){
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleFont(titleFont);
        return border;
    }
    public JPanel createRow(String labelText, int labelWidth, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel lb = new JLabel(labelText);
        lb.setPreferredSize(new Dimension(labelWidth, lb.getPreferredSize().height));

        panel.add(lb);
        panel.add(component);
        return panel;
    }

    public static void main(String[] args) {

    }
}