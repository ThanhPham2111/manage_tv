package org.example.GUI.Components.FormProducts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class IconCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        // Nếu giá trị là một ImageIcon
        if (value instanceof ImageIcon) {
            JLabel label = new JLabel((ImageIcon) value); // Tạo nhãn chứa biểu tượng
            label.setHorizontalAlignment(JLabel.CENTER); // Căn giữa hình ảnh trong ô
            return label; // Trả về nhãn để hiển thị trong ô
        }
        // Nếu không phải ImageIcon, sử dụng renderer mặc định
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}