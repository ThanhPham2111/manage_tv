package org.example.GUI.Components.FormHoaDon.Utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class UtilsJTableStyle {
    public static void setStyleTableHeader(JTable table, Font font, Color fontColor, Color bgColor){
        JTableHeader header = table.getTableHeader();
        header.setOpaque(true);
        header.setFont(font);
        header.setForeground(fontColor);
        header.setBackground(bgColor);
    }

    public static void setStyleTableCell(JTable table, Font font){
        table.setFont(font);
    }

    public static void setAlignmentTableCell(JTable table, int columnIndex, int horizontalAlignment){
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(horizontalAlignment);
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
    }

    public static void setWidthTableColumns(JTable table, int tablePreferredWidth, double... percentages){
        int tableColumns = table.getColumnModel().getColumnCount();
        
        double total = 0.0;
        for(int i = 0; i < tableColumns; i++){
            total += percentages[i];
        }

        for(int i = 0; i < tableColumns; i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(
                                    (int)(percentages[i] / total * tablePreferredWidth));
        }
    }
}
