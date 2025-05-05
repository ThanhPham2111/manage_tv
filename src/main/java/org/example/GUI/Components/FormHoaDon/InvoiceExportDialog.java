package org.example.GUI.Components.FormHoaDon;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
public class InvoiceExportDialog extends JDialog{
    private final int BUTTON_WIDTH = 125;
    private final Font FONT_LABEL = new Font("Segoe UI", java.awt.Font.PLAIN, 16);
    JPanel mainPanel, centerPanel, buttonPanel;
    JButton exportBtn, cancelBtn;
    JComboBox<ComboItem> cbxExportOptions;

    Boolean isConfimred;
    String selectedOption;

    final ComboItem[] exportOptions = {
        new ComboItem("Tất cả", "AllInvoices"),
        new ComboItem("Hiện tại", "CurrentInvoices")
    };

    public InvoiceExportDialog(){
        super((JFrame) null, "Thông báo", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        setPreferredSize(new Dimension(335, 195));
        setMinimumSize(getPreferredSize());
        isConfimred = false;
        selectedOption = "";

        mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 25));

        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cbxExportOptions = new JComboBox<>(exportOptions);
        cbxExportOptions.setSelectedIndex(1);
        JLabel lbl = new JLabel("Xuất dữ liệu hoá đơn:   ");
        lbl.setFont(FONT_LABEL);
        centerPanel.add(lbl);
        centerPanel.add(cbxExportOptions);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exportBtn = new JButton("Xác nhận");
        cancelBtn = new JButton("Huỷ");
        exportBtn.setFont(FONT_LABEL);
        cancelBtn.setFont(FONT_LABEL);
        exportBtn.setBackground(new Color(40, 167, 69));
        exportBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.LIGHT_GRAY);
        cancelBtn.setForeground(Color.WHITE);
        exportBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, exportBtn.getPreferredSize().height + 3));
        cancelBtn.setPreferredSize(new Dimension(BUTTON_WIDTH - 35, cancelBtn.getPreferredSize().height + 3));

        exportBtn.addActionListener((e) -> {
            ComboItem selectedItem = (ComboItem) cbxExportOptions.getSelectedItem();
            selectedOption = selectedItem.getLogicValue();
            isConfimred = true;
            dispose();
        });

        cancelBtn.addActionListener((e) -> {
            isConfimred = false;
            dispose();
        });

        buttonPanel.add(exportBtn);
        buttonPanel.add(cancelBtn);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Boolean getConfirmStatus(){
        return isConfimred;
    }

    public String getSelectedOption(){
        return selectedOption;
    }
}
