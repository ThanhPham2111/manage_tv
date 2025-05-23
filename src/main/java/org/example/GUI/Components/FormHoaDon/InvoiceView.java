package org.example.GUI.Components.FormHoaDon;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.InvoiceBUS;
import org.example.DTO.InvoiceDTO;
import org.example.GUI.Components.FormHoaDon.Utils.UtilsJTableStyle;
import org.example.UtilsDate.UtilsDateFormat;
import org.example.UtilsDate.FormattedDatePicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class InvoiceView extends JPanel {
    private final Font FONT_LABEL = new Font("Segoe UI", java.awt.Font.PLAIN, 16);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 19);

    private final int HGAP = 11;
    private final int VGAP = 11;
    private final int BUTTON_WIDTH = 125;
    private final int LABEL_PREFERRED_WIDTH = 125;
    private final int TEXTFIELD_COLUMNS = 17;
    private final int PADDING = 7;
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");

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
    private JLabel lblInvoiceID, lblCustomer, lblEmployee, lblDateTime, lblTotal;
    private JButton invoiceDetailBtn;

    private JPanel filterPanel;
    private JTextField txtInvoiceID, txtCustomerID, txtEmployeeID;
    private JComboBox<ComboItem> cbxDateCb;
    private FormattedDatePicker fromDatePicker, toDatePicker;
    private JPanel customDateContainer, buttonContainer;
    private JButton applyFilterBtn, resetFilterBtn;

    public InvoiceView() {
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

        setListeners();
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
        invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

    private void initBottomContainer() {
        bottomContainer = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        exportExcelBtn = new JButton("Xuất Excel");
        exportExcelBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, invoiceDetailBtn.getPreferredSize().height));
        exportExcelBtn.setBackground(new Color(40, 167, 69));
        exportExcelBtn.setForeground(Color.WHITE);
        exportExcelBtn.setFocusPainted(false);
        bottomContainer.add(exportExcelBtn);
    }

    private void initInfoPanel() {
        infoPanel = new JPanel(new GridLayout(6, 1));
        infoPanel.setPreferredSize(new Dimension(395, 0));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                createTitledBorder("Thông tin hoá đơn"),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)));

        lblInvoiceID = new JLabel();
        lblInvoiceID.setFont(FONT_LABEL);

        lblCustomer = new JLabel();
        lblCustomer.setFont(FONT_LABEL);

        lblEmployee = new JLabel();
        lblEmployee.setFont(FONT_LABEL);

        lblDateTime = new JLabel();
        lblDateTime.setFont(FONT_LABEL);

        lblTotal = new JLabel();
        lblTotal.setFont(FONT_LABEL);

        invoiceDetailBtn = new JButton("Xem chi tiết");
        invoiceDetailBtn.setFocusPainted(false);
        invoiceDetailBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, invoiceDetailBtn.getPreferredSize().height));

        infoPanel.add(createRow(createLabel("Mã hoá đơn: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                lblInvoiceID, FlowLayout.LEADING));
        infoPanel.add(createRow(createLabel("Khách hàng: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                lblCustomer, FlowLayout.LEADING));
        infoPanel.add(createRow(createLabel("Nhân viên: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                lblEmployee, FlowLayout.LEADING));
        infoPanel.add(createRow(createLabel("Thời gian lập: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                lblDateTime, FlowLayout.LEADING));
        infoPanel.add(createRow(createLabel("Tổng tiền: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                lblTotal, FlowLayout.LEADING));
        infoPanel.add(createRow(null, invoiceDetailBtn, FlowLayout.TRAILING));
    }

    private void initFilterPanel() {
        filterPanel = new JPanel(new GridLayout(6, 1, 0, 5));
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
                createTitledBorder("Bộ lọc"),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)));

        txtInvoiceID = new JTextField(TEXTFIELD_COLUMNS);
        txtCustomerID = new JTextField(TEXTFIELD_COLUMNS);
        txtEmployeeID = new JTextField(TEXTFIELD_COLUMNS);

        ComboItem[] dateItems = {
                new ComboItem("Tất cả", "All"),
                new ComboItem("Hôm nay", "Today"),
                new ComboItem("Hôm qua", "Yesterday"),
                new ComboItem("Trong 7 ngày", "7Days"),
                new ComboItem("Trong 31 ngày", "31Days"),
                new ComboItem("Tuần này", "ThisWeek"),
                new ComboItem("Tháng này", "ThisMonth"),
                new ComboItem("Năm nay", "ThisYear"),
                new ComboItem("Tuỳ chọn", "Custom")
        };

        cbxDateCb = new JComboBox<>(dateItems);

        cbxDateCb.setPreferredSize(txtCustomerID.getPreferredSize());

        Date today = new Date();
        fromDatePicker = new FormattedDatePicker(today);
        toDatePicker = new FormattedDatePicker(today);
        customDateContainer = new JPanel(new FlowLayout(FlowLayout.LEADING));
        buttonContainer = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));

        customDateContainer.add(new JLabel("Từ ngày"));
        customDateContainer.add(fromDatePicker);
        customDateContainer.add(new JLabel("đến ngày"));
        customDateContainer.add(toDatePicker);
        customDateContainer.setVisible(false);

        applyFilterBtn = new JButton("Tìm kiếm ");
        applyFilterBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, invoiceDetailBtn.getPreferredSize().height));
        applyFilterBtn.setFocusPainted(false);
        resetFilterBtn = new JButton("Xoá bộ lọc");
        resetFilterBtn.setPreferredSize(new Dimension(BUTTON_WIDTH - 35, invoiceDetailBtn.getPreferredSize().height));
        resetFilterBtn.setFocusPainted(false);

        buttonContainer.add(applyFilterBtn);
        buttonContainer.add(resetFilterBtn);

        filterPanel.add(createRow(createLabel("Mã hoá đơn: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                txtInvoiceID, FlowLayout.LEADING));
        filterPanel.add(createRow(createLabel("Mã khách hàng: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                txtCustomerID, FlowLayout.LEADING));
        filterPanel.add(createRow(createLabel("Mã nhân viên: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                txtEmployeeID, FlowLayout.LEADING));
        filterPanel.add(createRow(createLabel("Chọn thời gian: ", LABEL_PREFERRED_WIDTH,
                FONT_LABEL),
                cbxDateCb, FlowLayout.LEADING));
        filterPanel.add(customDateContainer);
        filterPanel.add(buttonContainer);
    }

    public void setListeners() {
        applyFilterBtn.addActionListener(e -> applyFilter());

        resetFilterBtn.addActionListener(e -> resetFilter());

        exportExcelBtn.addActionListener((e) -> exportExcel());

        invoiceDetailBtn.addActionListener((e) -> {
            int selectedRow = invoiceTable.getSelectedRow();
            if (selectedRow >= 0) {
                showInvoiceDetails((String) invoiceTable.getValueAt(selectedRow, 0));
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một hoá đơn để xem chi tiết", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        });

        cbxDateCb.addActionListener((e) -> {
            ComboItem selectedItem = (ComboItem) (cbxDateCb.getSelectedItem());
            String logicValue = selectedItem.getLogicValue();

            if ("Custom".equals(logicValue)) {
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

    public void updateInfoPanel(int selectedRowIndex) {
        if (selectedRowIndex >= 0) {
            String maHoaDon = invoiceTableModel.getValueAt(selectedRowIndex, 0).toString();
            InvoiceDTO invoice = invoiceBUS.getInvoiceByID(maHoaDon);

            infoPanel.setIgnoreRepaint(true);

            lblInvoiceID.setText(invoice.getMaHD());
            lblCustomer.setText(invoice.getMaKH() + " - " + invoice.getTenKH());
            lblEmployee.setText(invoice.getMaNV() + " - " + invoice.getTenNV());
            lblDateTime.setText(UtilsDateFormat.formatDateTime(invoice.getThoiGianLap()));
            lblTotal.setText(priceFormatter.format(invoice.getTongTien()) + " VNĐ");

            infoPanel.setIgnoreRepaint(false);
            infoPanel.repaint();
        }
    }

    public void clearInfoPanel() {
        infoPanel.setIgnoreRepaint(true);

        lblInvoiceID.setText("________");
        lblCustomer.setText("________");
        lblEmployee.setText("________");
        lblDateTime.setText("________");
        lblTotal.setText("________");

        infoPanel.setIgnoreRepaint(false);
        infoPanel.repaint();
    }

    public void applyFilter() {
        String maHoaDon = txtInvoiceID.getText().trim().toUpperCase();
        String maKhachHang = txtCustomerID.getText().trim().toUpperCase();
        String maNhanVien = txtEmployeeID.getText().trim().toUpperCase();
        ComboItem selectedItem = (ComboItem) cbxDateCb.getSelectedItem();
        String dateOption = selectedItem.getLogicValue();
        Date fromDate = null, toDate = null;
        Date customFromDate, customToDate;

        Date[] refDates = new Date[3];
        // refDates[0]: fromDate
        // refDates[1]: toDate

        customFromDate = fromDatePicker.getDate();
        customToDate = toDatePicker.getDate();

        invoiceBUS.setFromDateToDate(dateOption, refDates, customFromDate, customToDate);
        fromDate = refDates[0];
        toDate = refDates[1];

        String invalidMessages = invoiceBUS.validateFilter(dateOption, maHoaDon, maKhachHang, maNhanVien, fromDate,
                toDate);

        if (!invalidMessages.isEmpty()) {
            showInvalidMessages("Sai định dạng", invalidMessages);
        } else {
            ArrayList<InvoiceDTO> invoices = invoiceBUS.getFilteredInvoices(maHoaDon,
                    maKhachHang,
                    maNhanVien,
                    fromDate,
                    toDate);
            loadInvoices(invoices);
        }
    }

    public void showInvalidMessages(String invalidTitle, String invalidMessages) {
        JOptionPane.showMessageDialog(null, invalidMessages, invalidTitle, JOptionPane.ERROR_MESSAGE);
    }

    public void resetFilter() {
        filterPanel.setIgnoreRepaint(true);

        txtInvoiceID.setText("");
        txtCustomerID.setText("");
        txtEmployeeID.setText("");
        cbxDateCb.setSelectedIndex(0);
        customDateContainer.setVisible(false);

        Date today = new Date();
        fromDatePicker.setDate(today);
        toDatePicker.setDate(today);

        filterPanel.setIgnoreRepaint(false);
        filterPanel.repaint();

        loadAllInvoices();
    }

    public void showInvoiceDetails(String maHoaDon) {
        new InvoiceDetailDialog(maHoaDon);
    }

    public void exportExcel() {
        InvoiceExportDialog exportDialog = new InvoiceExportDialog();
        if (exportDialog.getConfirmStatus() == false)
            return;
        String selectedOption = exportDialog.getSelectedOption();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn địa điểm lưu");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String path = fileToSave.getAbsolutePath();
            if (!path.toLowerCase().endsWith("xlsx")) {
                path += ".xlsx";
            }

            DefaultTableModel toExportModel = new DefaultTableModel();
            if (selectedOption == "AllInvoices") {
                ArrayList<InvoiceDTO> invoices = invoiceBUS.getAllInvoices();
                String[] columnsNames = { "Mã hoá đơn", "Mã khách hàng", "Mã nhân viên", "Ngày lập", "Giờ lập",
                        "Tổng tiền" };
                toExportModel = new DefaultTableModel(columnsNames, 0);
                for (InvoiceDTO invoice : invoices) {
                    toExportModel.addRow(new Object[] {
                            invoice.getMaHD(),
                            invoice.getMaKH(),
                            invoice.getMaNV(),
                            new SimpleDateFormat("dd/MM/yyyy").format(invoice.getNgayLap()),
                            new SimpleDateFormat("HH:mm:ss").format(invoice.getGioNhap()),
                            priceFormatter.format(invoice.getTongTien())
                    });
                }
            } else if (selectedOption == "CurrentInvoices") {
                toExportModel = invoiceTableModel;
            }

            Boolean success = invoiceBUS.exportExcel(toExportModel, path);
            if (success) {
                JOptionPane.showMessageDialog(null, "Xuất Excel thành công!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Xuát Excel không thành công.", "Thông báo",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadAllInvoices() {
        ArrayList<InvoiceDTO> invoices = invoiceBUS.getAllInvoices();
        loadInvoices(invoices);
    }

    public void loadInvoices(ArrayList<InvoiceDTO> invoices) {
        invoiceTableModel.setRowCount(0);
        for (InvoiceDTO invoice : invoices) {
            invoiceTableModel.addRow(new Object[] {
                    invoice.getMaHD(),
                    invoice.getMaKH(),
                    invoice.getMaNV(),
                    new SimpleDateFormat("dd/MM/yyyy").format(invoice.getNgayLap()),
                    new SimpleDateFormat("HH:mm:ss").format(invoice.getGioNhap()),
                    priceFormatter.format(invoice.getTongTien())
            });
        }

        checkEmptyTable();
        clearInfoPanel();
    }

    public void styleTable() {
        invoiceTable.setRowHeight(35);
        invoiceTable.setShowGrid(true);
        invoiceTable.setGridColor(new Color(230, 230, 230));

        UtilsJTableStyle.setStyleTableHeader(invoiceTable, FONT_TITLE, Color.WHITE, new Color(70, 130, 180));
        UtilsJTableStyle.setStyleTableCell(invoiceTable, FONT_LABEL);

        UtilsJTableStyle.setAlignmentTableCell(invoiceTable, 0, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(invoiceTable, 1, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(invoiceTable, 2, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(invoiceTable, 3, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(invoiceTable, 4, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(invoiceTable, 5, SwingConstants.RIGHT);
    }

    public void checkEmptyTable() {
        emptyLabel.setVisible(invoiceTable.getRowCount() == 0);
    }

    public TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(title);
        border.setTitleFont(FONT_TITLE);
        return border;
    }

    private JPanel createRow(JLabel label, JComponent component, int rowAlignment) {
        JPanel panel = new JPanel(new FlowLayout(rowAlignment));
        if (label != null)
            panel.add(label);
        panel.add(component);
        return panel;
    }

    private JLabel createLabel(String text, int width, Font font) {
        JLabel lbl = new JLabel(text);
        if (font != null)
            lbl.setFont(font);
        lbl.setHorizontalAlignment(JLabel.LEADING);
        lbl.setPreferredSize(new Dimension(width, lbl.getPreferredSize().height));
        return lbl;
    }

    public static void main(String[] args) {

    }
}