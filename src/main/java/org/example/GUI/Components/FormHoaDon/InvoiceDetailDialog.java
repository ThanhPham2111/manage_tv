package org.example.GUI.Components.FormHoaDon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.InvoiceBUS;
import org.example.DTO.InvoiceDTO;
import org.example.DTO.InvoiceDetailDTO;
import org.example.DateFormat.UtilsDateFormat;
import org.example.GUI.Components.FormHoaDon.Utils.UtilsJTableStyle;

public class InvoiceDetailDialog extends JDialog{
    private final Font FONT_LABEL = new Font("Segoe UI", java.awt.Font.PLAIN, 14);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_TOTAL = new Font("Roboto", Font.BOLD, 18);
    
    private final int HGAP = 11;
    private final int VGAP = 11;
    private final int LABEL_PREFERRED_WIDTH = 87;
    private final int BUTTON_WIDTH = 125;
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");

    private final InvoiceBUS invoiceBUS = new InvoiceBUS();

    private JPanel mainPanel;
    private JPanel infoPanel;
    private JLabel lblInvoiceID, lblCustomer, lblEmployee, lblDate, lblTime;
    
    private JTable invoiceDetailsTable;
    private DefaultTableModel invoiceDetailsTableModel;
    private JScrollPane sp;

    private JPanel bottomPanel;
    private JLabel lblTotal;
    private JButton printInvoiceBtn;

    String maHoaDon;
    public InvoiceDetailDialog(String maHoaDon){
        super((JFrame) null, "Chi tiết hoá đơn", true);
        setLayout(new BorderLayout(HGAP, VGAP));
        setSize(755, 455);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.maHoaDon = maHoaDon;
        
        initInfoPanel();
        initBottomPanel();
        initDetailTable();

        initMainPanel();
        
        add(mainPanel);
        setVisible(true);
    }

    private void initMainPanel(){
        mainPanel = new JPanel(new BorderLayout(11, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 11, 11, 11));

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(sp, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initInfoPanel(){
        infoPanel = new JPanel(new GridLayout(3, 2));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        lblInvoiceID = new JLabel();
        lblInvoiceID.setFont(FONT_LABEL);

        lblCustomer = new JLabel();
        lblCustomer.setFont(FONT_LABEL);

        lblEmployee = new JLabel();
        lblEmployee.setFont(FONT_LABEL);

        lblDate = new JLabel();
        lblDate.setFont(FONT_LABEL);

        lblTime = new JLabel();
        lblTime.setFont(FONT_LABEL);

        infoPanel.add(createRow(createLabel("Mã hoá đơn: ", LABEL_PREFERRED_WIDTH,
                                FONT_LABEL),
                                lblInvoiceID, FlowLayout.LEADING));

        infoPanel.add(createRow(createLabel("Ngày lập: ", LABEL_PREFERRED_WIDTH,
                                FONT_LABEL),
                                lblDate, FlowLayout.LEADING));

        infoPanel.add(createRow(createLabel("Khách hàng: ", LABEL_PREFERRED_WIDTH,
                                FONT_LABEL),
                                lblCustomer, FlowLayout.LEADING));
                                
        infoPanel.add(createRow(createLabel("Giờ lập: ", LABEL_PREFERRED_WIDTH,
                                FONT_LABEL),
                                lblTime, FlowLayout.LEADING));
                                
        infoPanel.add(createRow(createLabel("Nhân viên: ", LABEL_PREFERRED_WIDTH,
                                FONT_LABEL),
                                lblEmployee, FlowLayout.LEADING));
    }

    private void initDetailTable(){
        String columnNames[] = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        invoiceDetailsTableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        invoiceDetailsTable = new JTable(invoiceDetailsTableModel);
        invoiceDetailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sp = new JScrollPane(invoiceDetailsTable);
        styleTable();
        loadInvoice();
    }

    private void initBottomPanel(){
        bottomPanel = new JPanel(new BorderLayout());
        lblTotal = new JLabel();
        lblTotal.setFont(FONT_TOTAL);
        printInvoiceBtn = new JButton("In hoá đơn");

        printInvoiceBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, printInvoiceBtn.getPreferredSize().height));
        JPanel tmp = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        tmp.add(printInvoiceBtn);

        bottomPanel.add(createRow(createLabel("Tổng tiền: ", LABEL_PREFERRED_WIDTH,
                        FONT_TOTAL),
                        lblTotal, FlowLayout.TRAILING), BorderLayout.CENTER);

        bottomPanel.add(tmp, BorderLayout.SOUTH);
    }

    private void loadInvoice(){
        invoiceDetailsTableModel.setRowCount(0);
        InvoiceDTO invoice = invoiceBUS.getInvoiceDetailsByID(maHoaDon);

        infoPanel.setIgnoreRepaint(true);

        lblInvoiceID.setText(invoice.getMaHoaDon());
        lblCustomer.setText(invoice.getMaKhachHang() + " - " + invoice.getTenKhachHang());
        lblEmployee.setText(invoice.getMaNhanVien() + " - " + invoice.getTenNhanVien());
        lblDate.setText(UtilsDateFormat.formatDate(invoice.getNgayLap()));
        lblTime.setText(UtilsDateFormat.formatTime(invoice.getThoiGianLap()));
        lblTotal.setText(priceFormatter.format(invoice.getTongTien()));
        
        infoPanel.setIgnoreRepaint(true);
        infoPanel.repaint();
        
        ArrayList<InvoiceDetailDTO> invoiceDetails = invoice.getInvoiceDetails();

        for(InvoiceDetailDTO invoiceDetail: invoiceDetails){
            invoiceDetailsTableModel.addRow(new Object[]{
                invoiceDetail.getMaSanPham(),
                invoiceDetail.getTenSanPham(),
                invoiceDetail.getSoLuong(),
                priceFormatter.format(invoiceDetail.getDonGia()),
                priceFormatter.format(invoiceDetail.getThanhTien())
            });
        }
    }

    private void styleTable(){
        invoiceDetailsTable.setRowHeight(35);
        invoiceDetailsTable.setShowGrid(true);
        invoiceDetailsTable.setGridColor(new Color(230, 230, 230));

        UtilsJTableStyle.setStyleTableHeader(invoiceDetailsTable, FONT_TITLE, Color.WHITE, new Color(70, 130, 180));

        UtilsJTableStyle.setTableCellAlignment(invoiceDetailsTable, 0, SwingConstants.LEFT);
        UtilsJTableStyle.setTableCellAlignment(invoiceDetailsTable, 1, SwingConstants.LEFT);
        UtilsJTableStyle.setTableCellAlignment(invoiceDetailsTable, 2, SwingConstants.CENTER);
        UtilsJTableStyle.setTableCellAlignment(invoiceDetailsTable, 3, SwingConstants.RIGHT);
        UtilsJTableStyle.setTableCellAlignment(invoiceDetailsTable, 4, SwingConstants.RIGHT);
        
        
        UtilsJTableStyle.setTableColumnsWidth(invoiceDetailsTable, sp.getPreferredSize().width,
                                            15, 27, 8, 20, 20);
    }

    private JPanel createRow(JLabel lblKey, JLabel lblVal, int rowAlignment){
        JPanel panel = new JPanel(new FlowLayout(rowAlignment));

        panel.add(lblKey);
        panel.add(lblVal);

        return panel;
    }

    private JLabel createLabel(String text, int width, Font font){
        JLabel lbl = new JLabel(text);
        if(font != null) lbl.setFont(font);
        lbl.setHorizontalAlignment(JLabel.LEADING);
        lbl.setPreferredSize(new Dimension(width, lbl.getPreferredSize().height));
        return lbl;
    }
    public static void main(String[] args) {
        
    }
}
