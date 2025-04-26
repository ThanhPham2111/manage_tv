package org.example.GUI.Components.FormHoaDon.Utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class UtilsJTableStyle {
    public static void setStyleTableHeader(JTable table, Font font, Color fontColor, Color bgColor){
        JTableHeader header = table.getTableHeader();
        header.setOpaque(true);
        header.setFont(font);
        header.setForeground(fontColor);
        header.setBackground(bgColor);
    }

    public static void setTableHeaderAlignment(JTable table, int columnIndex, int horizontalAlignment, TableCellRenderer defaultHeaderRenderer){
        TableCellRenderer headerAlignmentRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column){
                Component comp = defaultHeaderRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if(comp instanceof JLabel){
                    JLabel label = (JLabel) comp;
                    if(column == columnIndex){
                        label.setHorizontalAlignment(horizontalAlignment);
                    }
                }

                return comp;
            }
        };

        table.getColumnModel().getColumn(columnIndex).setHeaderRenderer(headerAlignmentRenderer);
    }

    public static void setTableCellAlignment(JTable table, int columnIndex, int horizontalAlignment){
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(horizontalAlignment);
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(cellRenderer);
    }

    public static void setTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages){
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
