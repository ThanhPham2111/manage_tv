package org.example.GUI.Components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class CustomTable extends JTable {
    
    public CustomTable() {
        super();
        setDefaultProperties();
    }

    private void setDefaultProperties() {
        // Thiết lập font cho header và nội dung
        setFont(new Font("Arial", Font.PLAIN, 13));
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        // Căn giữa header
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Thiết lập màu nền cho header
        getTableHeader().setBackground(new Color(71, 120, 197));
        getTableHeader().setForeground(Color.WHITE);
        
        // Thiết lập màu nền cho các dòng
        setSelectionBackground(new Color(173, 205, 239));
        setSelectionForeground(Color.BLACK);
        
        // Thiết lập độ cao của dòng
        setRowHeight(25);
        
        // Không cho phép kéo cột
        getTableHeader().setReorderingAllowed(false);
        
        // Không cho phép chỉnh sửa kích thước cột
        getTableHeader().setResizingAllowed(false);
        
        // Căn giữa nội dung các ô
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        setDefaultRenderer(Object.class, centerRenderer);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Không cho phép chỉnh sửa trực tiếp trên bảng
        return false;
    }

    public void setColumnWidth(int[] widths) {
        for (int i = 0; i < widths.length; i++) {
            if (i < getColumnCount()) {
                TableColumn column = getColumnModel().getColumn(i);
                column.setPreferredWidth(widths[i]);
                column.setMinWidth(widths[i]);
                column.setMaxWidth(widths[i]);
            }
        }
    }
} 