package org.example.GUI.FormDialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.ImportBUS;
import org.example.BUS.ProductBUS;
import org.example.DTO.ImportDTO;
import org.example.DTO.ImportDetailDTO;
import org.example.DTO.ProductDTO;

public class FormChiTietPhieuNhap extends JDialog {
    private String maPhieuNhap;
    private JPanel pnMain;
    private JTable tblChiTiet;
    private ProductBUS productBUS;
    private ImportBUS importSlipBUS;
    private JTextField txtMaPhieuNhap, txtNhaCungCap, txtNhanVien;
    private JTextField txtNgayLap, txtGioLap, txtTongTien;

    public FormChiTietPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
        this.productBUS = new ProductBUS();
        this.importSlipBUS = new ImportBUS();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Chi tiết phiếu nhập - " + maPhieuNhap);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setModal(true);

        pnMain = new JPanel();
        pnMain.setLayout(new BorderLayout());
        pnMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Panel thông tin phiếu nhập
        JPanel pnInfo = new JPanel(new GridLayout(2, 3, 5, 5));
        pnInfo.setBorder(new TitledBorder("Thông tin phiếu nhập"));

        txtMaPhieuNhap = new JTextField();
        txtNhaCungCap = new JTextField();
        txtNhanVien = new JTextField();
        txtNgayLap = new JTextField();
        txtGioLap = new JTextField();
        txtTongTien = new JTextField();

        txtMaPhieuNhap.setEditable(false);
        txtNhaCungCap.setEditable(false);
        txtNhanVien.setEditable(false);
        txtNgayLap.setEditable(false);
        txtGioLap.setEditable(false);
        txtTongTien.setEditable(false);

        pnInfo.add(createInfoPanel("Mã phiếu nhập:", txtMaPhieuNhap));
        pnInfo.add(createInfoPanel("Nhà cung cấp:", txtNhaCungCap));
        pnInfo.add(createInfoPanel("Nhân viên:", txtNhanVien));
        pnInfo.add(createInfoPanel("Ngày lập:", txtNgayLap));
        pnInfo.add(createInfoPanel("Giờ lập:", txtGioLap));
        pnInfo.add(createInfoPanel("Tổng tiền:", txtTongTien));

        // Table chi tiết
        String[] columns = {"STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        tblChiTiet = new JTable();
        tblChiTiet.setModel(new DefaultTableModel(columns, 0));
        JScrollPane scrollPane = new JScrollPane(tblChiTiet);

        // Add components
        pnMain.add(pnInfo, BorderLayout.NORTH);
        pnMain.add(scrollPane, BorderLayout.CENTER);

        // Add to dialog
        setLayout(new BorderLayout());
        add(pnMain, BorderLayout.CENTER);
    }

    private JPanel createInfoPanel(String label, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    private void loadData() {
        // Load thông tin phiếu nhập
        ImportDTO importSlip = importSlipBUS.getImportSlipById(maPhieuNhap);
        if (importSlip == null) {
            System.out.println("Không tìm thấy thông tin phiếu nhập");
            return;
        }

        
        if (importSlip != null) {
            txtMaPhieuNhap.setText(importSlip.getMaPN());
            txtNhaCungCap.setText(importSlip.getMaNCC());
            txtNhanVien.setText(importSlip.getMaNV());
            txtNgayLap.setText(new SimpleDateFormat("dd/MM/yyyy").format(importSlip.getNgayNhap()));
            txtGioLap.setText(new SimpleDateFormat("HH:mm:ss").format(importSlip.getGioNhap()));
            txtTongTien.setText(String.format("%,.0f VNĐ", importSlip.getTongTien()));
        }

        // Load chi tiết phiếu nhập
        DefaultTableModel model = (DefaultTableModel) tblChiTiet.getModel();
        model.setRowCount(0);

        List<ImportDetailDTO> details = importSlipBUS.getImportSlipDetails(maPhieuNhap);
        int stt = 1;
        if (details == null || details.isEmpty()) {
            System.out.println("Không tìm thấy chi tiết phiếu nhập");
            return;
        }
        for (ImportDetailDTO detail : details) {
            ProductDTO product = productBUS.getProductDTO(detail.getMaSP());
            String tenSP = product != null ? product.getTenSP() : "";
            
            model.addRow(new Object[]{
                stt++,
                detail.getMaSP(),
                tenSP,
                detail.getSoLuong(),
                String.format("%,.0f VNĐ", detail.getDonGia()),
                String.format("%,.0f VNĐ", detail.getThanhTien())
            });
        }

        // Auto-resize columns
        for (int i = 0; i < tblChiTiet.getColumnCount(); i++) {
            tblChiTiet.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }
} 