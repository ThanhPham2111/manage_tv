package org.example.GUI.FormDialog;

import org.example.BUS.ProductBUS;
import org.example.BUS.TypeProductBUS;
import org.example.DTO.ProductDTO;
import org.example.DTO.TypeProduct;
import org.example.GUI.Components.FormProducts.ValidateInp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat; // Thêm import này
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ProductDialog {

    private JFrame parentFrame;
    private ProductBUS productBus;
    private TypeProductBUS typeProductBus;
    private JTable table;
    private DefaultTableModel model;

    public ProductDialog(JFrame parentFrame, ProductBUS productBus, TypeProductBUS typeProductBus, JTable table) {
        this.parentFrame = parentFrame;
        this.productBus = productBus;
        this.typeProductBus = typeProductBus;
        this.table = table;
    }

    public void showDialogToAddProduct() {
        JDialog addDialog = new JDialog(parentFrame, "Thêm sản phẩm", true);
        addDialog.setSize(450, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        JTextField txtMaSP = new JTextField();
        txtMaSP.setText(productBus.getNextID()); // Gán mã sản phẩm tiếp theo từ ProductBUS
        txtMaSP.setEnabled(false);

        JLabel lblMaLSP = new JLabel("Mã lô hàng sản phẩm:");
        ArrayList<TypeProduct> listLoaiSP = typeProductBus.getList();
        ArrayList<String> listMaLSP = new ArrayList<>();
        for (TypeProduct lsp : listLoaiSP) {
            listMaLSP.add(lsp.getMaLSP());
        }
        JComboBox<String> cbLSP = new JComboBox<>(listMaLSP.toArray(new String[0]));

        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        JTextField txtTenSP = new JTextField();

        JLabel lblDonGia = new JLabel("Đơn giá:");
        JTextField txtDonGia = new JTextField();

        JLabel lblFileAnh = new JLabel("File ảnh:");
        JButton btnChooseImage = new JButton("Chọn ảnh");

        JLabel lblSoLuong = new JLabel("Số lượng:");
        JTextField txtSoLuong = new JTextField();

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        JComboBox<String> cboTrangThai = new JComboBox<>(new String[] { "Đang bán", "Ngừng bán", "Chờ nhập hàng" });

        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(
                        new File(System.getProperty("user.dir") + "/src/main/resources/imageTopic"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif",
                        "jpeg");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    btnChooseImage.setText(fileName);
                }
            }
        });

        panel.add(lblMaSP);
        panel.add(txtMaSP);
        panel.add(lblMaLSP);
        panel.add(cbLSP);
        panel.add(lblTenSP);
        panel.add(txtTenSP);
        panel.add(lblDonGia);
        panel.add(txtDonGia);
        panel.add(lblFileAnh);
        panel.add(btnChooseImage);
        panel.add(lblSoLuong);
        panel.add(txtSoLuong);
        panel.add(lblTrangThai);
        panel.add(cboTrangThai);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOK.setEnabled(false); // Ban đầu vô hiệu hóa nút OK

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maSP = txtMaSP.getText();
                String maLSP = (String) cbLSP.getSelectedItem();
                String tenSP = txtTenSP.getText();
                String fileAnh = btnChooseImage.getText();
                String trangThai = convertTrangThai(cboTrangThai.getSelectedItem().toString());
                String donGiaStr = txtDonGia.getText();
                String soLuongStr = txtSoLuong.getText();

                if (!ValidateInp.isNotEmpty(maSP) || !ValidateInp.isNotEmpty(maLSP) || !ValidateInp.isNotEmpty(tenSP)) {
                    JOptionPane.showMessageDialog(addDialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!ValidateInp.isValidNumber(donGiaStr) || !ValidateInp.isValidInteger(soLuongStr)) {
                    JOptionPane.showMessageDialog(addDialog, "Đơn giá và số lượng phải là số hợp lệ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float donGia = Float.parseFloat(donGiaStr);
                int soLuong = Integer.parseInt(soLuongStr);

                if (checkInform(maSP, "addDialog", donGia, soLuong)) {
                    model = (DefaultTableModel) table.getModel();
                    ImageIcon imageIcon = loadImageIcon(fileAnh);
                    model.addRow(new Object[] { maSP, maLSP, tenSP, donGia, soLuong, imageIcon, trangThai });
                    ProductDTO product = new ProductDTO(maSP, maLSP, tenSP, donGia, soLuong, fileAnh,
                            Integer.parseInt(trangThai));
                    productBus.add(product);
                    refreshTable();
                    addDialog.dispose();
                }
            }
        });

        btnCancel.addActionListener(e -> addDialog.dispose());

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }

            private void checkFields() {
                boolean allFieldsFilled = ValidateInp.isNotEmpty(txtMaSP.getText()) &&
                        ValidateInp.isNotEmpty(txtTenSP.getText()) &&
                        ValidateInp.isNotEmpty(btnChooseImage.getText()) &&
                        ValidateInp.isNotEmpty(txtDonGia.getText()) &&
                        ValidateInp.isNotEmpty(txtSoLuong.getText());

                boolean validDonGia = ValidateInp.isValidNumber(txtDonGia.getText());
                boolean validSoLuong = ValidateInp.isValidInteger(txtSoLuong.getText());

                btnOK.setEnabled(allFieldsFilled && validDonGia && validSoLuong);
            }
        };

        txtTenSP.getDocument().addDocumentListener(documentListener);
        txtDonGia.getDocument().addDocumentListener(documentListener);
        txtSoLuong.getDocument().addDocumentListener(documentListener);

        panel.add(buttonPanel);
        addDialog.getContentPane().add(panel);
        addDialog.setLocationRelativeTo(parentFrame);
        addDialog.setVisible(true);
    }

    public void showDialogToEditProduct(String maSP, String maLSP, String tenSP, Float donGia, String fileAnh,
            int soLuong, String trangThai) {
        JDialog editDialog = new JDialog(parentFrame, "Chỉnh sửa sản phẩm", true);
        editDialog.setSize(450, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblMaSP = new JLabel("Mã sản phẩm:");
        JTextField txtMaSP = new JTextField(maSP);
        txtMaSP.setEnabled(false);

        JLabel lblMaLSP = new JLabel("Mã lô hàng sản phẩm:");
        ArrayList<TypeProduct> listLoaiSP = typeProductBus.getList();
        ArrayList<String> listMaLSP = new ArrayList<>();
        for (TypeProduct lsp : listLoaiSP) {
            listMaLSP.add(lsp.getMaLSP());
        }
        JComboBox<String> cbLSP = new JComboBox<>(listMaLSP.toArray(new String[0]));
        cbLSP.setSelectedItem(maLSP);

        JLabel lblTenSP = new JLabel("Tên sản phẩm:");
        JTextField txtTenSP = new JTextField(tenSP);

        JLabel lblDonGia = new JLabel("Đơn giá:");
        DecimalFormat formatter = new DecimalFormat("#,##0"); // Định dạng số với dấu phẩy
        JTextField txtDonGia = new JTextField(formatter.format(donGia)); // Sử dụng DecimalFormat để hiển thị giá

        JLabel lblFileAnh = new JLabel("File ảnh:");
        JButton btnChooseImage = new JButton(fileAnh);

        JLabel lblSoLuong = new JLabel("Số lượng:");
        JTextField txtSoLuong = new JTextField(String.valueOf(soLuong));
        txtSoLuong.setEnabled(false);

        JLabel lblTrangThai = new JLabel("Trạng thái:");
        JComboBox<String> cboTrangThai = new JComboBox<>(new String[] { "Đang bán", "Ngừng bán", "Chờ nhập hàng" });
        cboTrangThai.setSelectedItem(trangThai);

        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(
                        new File(System.getProperty("user.dir") + "/src/main/resources/imageTopic"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif",
                        "jpeg");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    btnChooseImage.setText(fileName);
                }
            }
        });

        panel.add(lblMaSP);
        panel.add(txtMaSP);
        panel.add(lblMaLSP);
        panel.add(cbLSP);
        panel.add(lblTenSP);
        panel.add(txtTenSP);
        panel.add(lblDonGia);
        panel.add(txtDonGia);
        panel.add(lblFileAnh);
        panel.add(btnChooseImage);
        panel.add(lblSoLuong);
        panel.add(txtSoLuong);
        panel.add(lblTrangThai);
        panel.add(cboTrangThai);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        buttonPanel.add(btnOK);
        buttonPanel.add(btnCancel);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newMaLSP = (String) cbLSP.getSelectedItem();
                String newTenSP = txtTenSP.getText();
                String newFileAnh = btnChooseImage.getText();
                String newTrangThai = convertTrangThai(cboTrangThai.getSelectedItem().toString());
                String donGiaStr = txtDonGia.getText().replace(",", ""); // Loại bỏ dấu phẩy trước khi parse
                String soLuongStr = txtSoLuong.getText();

                if (!ValidateInp.isNotEmpty(maSP) || !ValidateInp.isNotEmpty(newMaLSP)
                        || !ValidateInp.isNotEmpty(newTenSP)) {
                    JOptionPane.showMessageDialog(editDialog, "Vui lòng điền đầy đủ các trường bắt buộc!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!ValidateInp.isValidNumber(donGiaStr) || !ValidateInp.isValidInteger(soLuongStr)) {
                    JOptionPane.showMessageDialog(editDialog, "Đơn giá và số lượng phải là số hợp lệ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float newDonGia = Float.parseFloat(donGiaStr);
                int newSoLuong = Integer.parseInt(soLuongStr);

                if (checkInform(maSP, "editDialog", newDonGia, newSoLuong)) {
                    model = (DefaultTableModel) table.getModel();
                    ImageIcon imageIcon = loadImageIcon(newFileAnh);
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        model.setValueAt(newMaLSP, selectedRow, 1);
                        model.setValueAt(newTenSP, selectedRow, 2);
                        model.setValueAt(newDonGia, selectedRow, 3);
                        model.setValueAt(newSoLuong, selectedRow, 4);
                        model.setValueAt(imageIcon, selectedRow, 5);
                        model.setValueAt(newTrangThai, selectedRow, 6);
                    }
                    ProductDTO product = new ProductDTO(maSP, newMaLSP, newTenSP, newDonGia, newSoLuong, newFileAnh,
                            Integer.parseInt(newTrangThai));
                    productBus.update(product);
                    refreshTable();
                    editDialog.dispose();
                }
            }
        });

        btnCancel.addActionListener(e -> editDialog.dispose());

        panel.add(buttonPanel);
        editDialog.getContentPane().add(panel);
        editDialog.setLocationRelativeTo(parentFrame);
        editDialog.setVisible(true);
    }

    public void showDialogToDeleteProduct(String maSP) {
        int confirm = JOptionPane.showConfirmDialog(parentFrame, "Bạn có chắc chắn muốn xóa sản phẩm này?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productBus.updateTrangthai(maSP, 0); // 0 là "Ngừng bán"
            refreshTable();
        }
    }

    private boolean checkInform(String maSP, String dialogType, Float donGia, int soLuong) {
        if (dialogType.equals("addDialog")) {
            if (productBus.checkId(maSP)) {
                JOptionPane.showMessageDialog(parentFrame, "Mã sản phẩm đã tồn tại trong bảng.", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (donGia < 0) {
            JOptionPane.showMessageDialog(parentFrame, "Đơn giá phải là số thực không âm", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!String.valueOf(soLuong).matches("^[1-9]\\d*$")) {
            JOptionPane.showMessageDialog(parentFrame, "Số lượng phải là số dương từ 1 trở lên", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private String convertTrangThai(String trangThai) {
        switch (trangThai) {
            case "Đang bán":
                return "1"; // 1 là "Đang bán"
            case "Ngừng bán":
                return "0"; // 0 là "Ngừng bán"
            case "Chờ nhập hàng":
                return "2";
            default:
                return "1"; // Mặc định là "Đang bán"
        }
    }

    private void refreshTable() {
        ArrayList<ProductDTO> updatedList = productBus.getList();
        model.setRowCount(0);
        for (ProductDTO product : updatedList) {
            ImageIcon imageIcon = loadImageIcon(product.getHinhAnh());
            model.addRow(new Object[] {
                    product.getMaSP(),
                    product.getMaLSP(),
                    product.getTenSP(),
                    product.getDonGia(),
                    product.getSoLuong(),
                    imageIcon,
                    convertTrangThaiToDisplay(product.getTrangthai())
            });
        }
    }

    private String convertTrangThaiToDisplay(int trangThai) {
        switch (trangThai) {
            case 1:
                return "Đang bán"; // 1 là "Đang bán"
            case 0:
                return "Ngừng bán"; // 0 là "Ngừng bán"
            case 2:
                return "Chờ nhập hàng";
            default:
                return "Đang bán"; // Mặc định
        }
    }

    private ImageIcon loadImageIcon(String fileName) {
        String imagePath = "/org/example/GUI/resources/imageTopic/" + fileName;
        java.net.URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            ImageIcon imageIcon = new ImageIcon(imgURL);
            Image img = imageIcon.getImage();
            Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } else {
            System.err.println("Không thể tải hình ảnh: " + imagePath);
            return null;
        }
    }
}