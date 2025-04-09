package org.example.GUI.Components.FormProducts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ImageRender extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            // Tạo JLabel để hiển thị hình ảnh
            JLabel label = new JLabel(icon);
            // Căn giữa hình ảnh trong ô
            label.setHorizontalAlignment(JLabel.CENTER);
            // Thiết lập nền và viền nếu ô được chọn
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setOpaque(true);
            } else {
                label.setBackground(table.getBackground());
                label.setOpaque(true);
            }
            return label;
        }
        // Nếu giá trị không phải ImageIcon, trả về renderer mặc định
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}