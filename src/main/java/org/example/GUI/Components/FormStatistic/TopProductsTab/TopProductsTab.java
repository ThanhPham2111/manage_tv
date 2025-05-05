package org.example.GUI.Components.FormStatistic.TopProductsTab;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.TopProductsBUS;
import org.example.DTO.TopProductsDTO;
import org.example.GUI.Components.FormHoaDon.ComboItem;
import org.example.GUI.Components.FormHoaDon.Utils.UtilsJTableStyle;
import org.example.UtilsDate.FormattedDatePicker;
import org.example.UtilsDate.UtilsDateFormat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TopProductsTab extends JPanel {
    private TopProductsBUS topProductsBUS;
    private final Font FONT_LABEL = new Font("Segoe UI", java.awt.Font.PLAIN, 16);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 19); 
    private final int BUTTON_WIDTH = 125;
    private final int LABEL_PREFERRED_WIDTH = 95;

    // Này để ko bị chạy 2 lần ở listener cbxSelectTop
    private boolean isComboBoxChanged = false;

    private JPanel filterPanel;
    private JPanel viewByPanel, monthPanel, yearPanel, customDatePanel, selectTopPanel;
    private JComboBox<ComboItem> cbxViewBy;
    private JComboBox<ComboItem> cbxMonth;
    private JComboBox<ComboItem> cbxYear;
    private JComboBox<ComboItem> cbxSelectTop;
    private FormattedDatePicker fromDatePicker;
    private FormattedDatePicker toDatePicker;
    private JButton applyFilterBtn;
    private final ComboItem optionsViewBy[] = {

        new ComboItem("Trong 7 ngày", "7Days"),
        new ComboItem("Trong 31 ngày", "31Days"),
        new ComboItem("Năm", "Years"),
        new ComboItem("Tháng", "Months"),
        new ComboItem("Tuỳ chọn", "Custom")
    };
    private final ComboItem optionsMonth[] = {
        new ComboItem("01", "01"),
        new ComboItem("02", "02"),
        new ComboItem("03", "03"),
        new ComboItem("04", "04"),
        new ComboItem("05", "05"),
        new ComboItem("06", "06"),
        new ComboItem("07", "07"),
        new ComboItem("08", "08"),
        new ComboItem("09", "09"),
        new ComboItem("10", "10"),
        new ComboItem("11", "11"),
        new ComboItem("12", "12")
    };
    private final ComboItem optionsYear[] = {
        new ComboItem("2025", "2025")
    };

    private final ComboItem optionsSelectTop[] = {
        new ComboItem("1", "1"),
        new ComboItem("2", "2"),
        new ComboItem("3", "3"),
        new ComboItem("4", "4"),
        new ComboItem("5", "5"),
        new ComboItem("6", "6"),
        new ComboItem("7", "7"),
        new ComboItem("8", "8"),
        new ComboItem("9", "9"),
        new ComboItem("10", "10")
    };

    private JPanel tablePanel;
    private JTable table;
    DefaultTableModel tableModel;
    private JScrollPane spTable;
    private JLabel lblTitle;
    private JLabel noDataLabel;

    public TopProductsTab(){
        topProductsBUS = new TopProductsBUS();
        setLayout(new BorderLayout());
        initFilterPanel();
        initTablePanel();
        add(filterPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);

        setListeners();
        cbxSelectTop.setSelectedIndex(4);
    }

    private void initFilterPanel(){
        filterPanel = new JPanel(new GridLayout(2, 1));
        cbxViewBy = new JComboBox<>(optionsViewBy);
        cbxMonth = new JComboBox<>(optionsMonth);
        cbxYear = new JComboBox<>(optionsYear);
        cbxSelectTop = new JComboBox<>(optionsSelectTop);
        cbxSelectTop.setEditable(true);

        fromDatePicker = new FormattedDatePicker(null);
        toDatePicker = new FormattedDatePicker(null);
        applyFilterBtn = new JButton("Lọc");
        applyFilterBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, applyFilterBtn.getPreferredSize().height));
        applyFilterBtn.setFocusPainted(false);

        ((JLabel)cbxViewBy.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)cbxMonth.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)cbxYear.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)cbxSelectTop.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        viewByPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel lbl1 = new JLabel("Thống kê theo: ");
        lbl1.setPreferredSize(new Dimension(LABEL_PREFERRED_WIDTH, lbl1.getPreferredSize().height));
        viewByPanel.add(lbl1);
        viewByPanel.add(cbxViewBy);

        monthPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        monthPanel.add(new JLabel("trong tháng"));
        monthPanel.add(cbxMonth);

        yearPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        yearPanel.add(new JLabel("trong năm"));
        yearPanel.add(cbxYear);

        customDatePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        customDatePanel.add(new JLabel("từ ngày: "));
        customDatePanel.add(fromDatePicker);

        customDatePanel.add(new JLabel("đến ngày: "));
        customDatePanel.add(toDatePicker);

        customDatePanel.add(applyFilterBtn);

        selectTopPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel lbl2 = new JLabel("Xếp theo top: ");
        lbl2.setPreferredSize(new Dimension(LABEL_PREFERRED_WIDTH, lbl2.getPreferredSize().height));
        selectTopPanel.add(lbl2);
        selectTopPanel.add(cbxSelectTop);

        viewByPanel.setVisible(true);
        monthPanel.setVisible(false);
        yearPanel.setVisible(false);
        customDatePanel.setVisible(false);
        
        filterPanel.add(createRow(viewByPanel, monthPanel, yearPanel, customDatePanel));
        filterPanel.add(createRow(selectTopPanel));
    }

    private void initTablePanel(){
        tablePanel = new JPanel(new BorderLayout(0, 15));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new OverlayLayout(centerPanel));

        lblTitle = new JLabel();
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));

        noDataLabel = new JLabel("CHƯA CÓ DỮ LIỆU");
        noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 35));
        noDataLabel.setForeground(Color.LIGHT_GRAY);
        noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noDataLabel.setAlignmentX(0.5f);
        noDataLabel.setAlignmentY(0.1f);

        final String columnNames[] = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng đã bán"};
        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        spTable = new JScrollPane(table);

        centerPanel.add(noDataLabel);
        centerPanel.add(spTable);

        tablePanel.add(lblTitle, BorderLayout.NORTH);
        tablePanel.add(centerPanel, BorderLayout.CENTER);
        styleTable();
    }

    private void setListeners(){
        setListenerCbxViewBy();
        setListenerCbxMonth();
        setListenerCbxYear();
        setListenerCbxSelectTop();
        setListenerApplyFilterBtn();
    }

    private void setListenerCbxViewBy(){
        cbxViewBy.addActionListener((e) -> {
            String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();
            if("7Days".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(false);
                yearPanel.setVisible(false);
                customDatePanel.setVisible(false);
            } else
            if("31Days".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(false);
                yearPanel.setVisible(false);
                customDatePanel.setVisible(false);
            } else
            if("Years".equalsIgnoreCase(selectedViewBy)){
                yearPanel.setVisible(true);
                monthPanel.setVisible(false);
                customDatePanel.setVisible(false);
            } else
            if("Months".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(true);
                yearPanel.setVisible(true);
                customDatePanel.setVisible(false);
            } else
            if("Custom".equalsIgnoreCase(selectedViewBy)){
                customDatePanel.setVisible(true);
                monthPanel.setVisible(false);
                yearPanel.setVisible(false);
            }

            ComboItem currentMonthOption = new ComboItem(
                new SimpleDateFormat("MM").format(new Date()),
                new SimpleDateFormat("MM").format(new Date())
            );

            ComboItem currentYearOption = new ComboItem(
                new SimpleDateFormat("YYYY").format(new Date()),
                new SimpleDateFormat("YYYY").format(new Date())
            );

            ComboItem currentMonthItem = optionsMonth[getCurrentComboItemIndex(optionsMonth, currentMonthOption)];
            ComboItem currentYearItem = optionsYear[getCurrentComboItemIndex(optionsYear, currentYearOption)];

            if("Years".equalsIgnoreCase(selectedViewBy)){
                cbxYear.setSelectedItem(currentYearItem);
            } else
            if("Months".equalsIgnoreCase(selectedViewBy)){
                cbxYear.setSelectedItem(currentYearItem);
                cbxMonth.setSelectedItem(currentMonthItem);
            }

            applySelectFilter();
        });
    }

    private void setListenerCbxMonth(){
        cbxMonth.addActionListener((e) -> {
            applySelectFilter();
        });
    }

    private void setListenerCbxYear(){
        cbxYear.addActionListener((e) -> {
            applySelectFilter();
        });
    }

    private void setListenerCbxSelectTop(){
        
        cbxSelectTop.addActionListener((e) -> {
            String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();

            if(!("Custom".equalsIgnoreCase(selectedViewBy))){
                if("comboBoxChanged".equalsIgnoreCase(e.getActionCommand())){
                    isComboBoxChanged = true;
                    applySelectFilter();
                }
                else if(isComboBoxChanged) isComboBoxChanged = false;
                else applySelectFilter();

            } else{
                Date fromDate = fromDatePicker.getDate();
                Date toDate = toDatePicker.getDate();
                if(fromDate != null & toDate != null){
                    if("comboBoxChanged".equalsIgnoreCase(e.getActionCommand())){
                        isComboBoxChanged = true;
                        applyCustomDateFilter();
                    }
                    else if(isComboBoxChanged) isComboBoxChanged = false;
                    else applyCustomDateFilter();
                }
            }
        });
    }
    
    private void applySelectFilter(){
        String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();
        
        Object selectedItem = cbxSelectTop.getSelectedItem();
        int limit = 0;
        if(selectedItem instanceof String){
            try {
                limit = Integer.parseInt((String) selectedItem);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập vào số nguyên dương", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(limit <= 0){
                JOptionPane.showMessageDialog(null, "Vui lòng nhập vào số nguyên dương", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
                return;    
            }
        } else
        if(selectedItem instanceof ComboItem){
            limit = Integer.parseInt(((ComboItem) cbxSelectTop.getSelectedItem()).getLogicValue());
        }

        int selectedMonth = 0;
        int selectedYear = 0;
        Date fromDate = null, toDate = null;
        Date refDates[] = new Date[3];
        
        ArrayList<TopProductsDTO> topProducts = new ArrayList<>();
        if("7Days".equalsIgnoreCase(selectedViewBy)){
            topProductsBUS.setFromDateToDateToLastDays(refDates, 7);
            fromDate = refDates[0];
            toDate = refDates[1];

            lblTitle.setText("Top " + limit + " sản phẩm trong vòng 7 ngày");
        } else
        if("31Days".equalsIgnoreCase(selectedViewBy)){
            topProductsBUS.setFromDateToDateToLastDays(refDates, 31);
            fromDate = refDates[0];
            toDate = refDates[1];
            
            lblTitle.setText("Top " + limit + " sản phẩm trong vòng 31 ngày");
        } else
        if("Years".equalsIgnoreCase(selectedViewBy)){
            selectedYear = Integer.parseInt(((ComboItem) cbxYear.getSelectedItem()).getLogicValue());

            lblTitle.setText(String.format("Top %d sản phẩm trong năm %d", limit, selectedYear));
        } else
        if("Months".equalsIgnoreCase(selectedViewBy)){
            selectedMonth = Integer.parseInt(((ComboItem) cbxMonth.getSelectedItem()).getLogicValue());
            selectedYear = Integer.parseInt(((ComboItem) cbxYear.getSelectedItem()).getLogicValue());

            lblTitle.setText(String.format("Top %d sản phẩm trong %02d/%d", limit, selectedMonth, selectedYear));
        } else
        if("Custom".equalsIgnoreCase(selectedViewBy)){
            fromDate = fromDatePicker.getDate();
            toDate = toDatePicker.getDate();
            if(fromDate != null && toDate != null){
                lblTitle.setText(String.format("Top %d sản phẩm từ %s đến %s",
                            limit,
                            UtilsDateFormat.formatDate(fromDate),
                            UtilsDateFormat.formatDate(toDate)));
            } else lblTitle.setText("");
        }
        if("Custom".equalsIgnoreCase(selectedViewBy)){
            if(fromDate != null && toDate != null){
                topProducts = topProductsBUS.getTopProductsByDate(fromDate, toDate, limit);
            }
        } else{
            topProducts = topProductsBUS.getTopProducts(selectedViewBy, selectedMonth, selectedYear, fromDate, toDate, limit);
        }
        loadTopProducts(topProducts);
    }

    private void setListenerApplyFilterBtn(){
        applyFilterBtn.addActionListener((e) -> {  
            applyCustomDateFilter();       
        });

    }

    private void applyCustomDateFilter(){
        Date fromDate = fromDatePicker.getDate();
        Date toDate = toDatePicker.getDate();

        short statusIsValidCustomDate = topProductsBUS.validateDate(fromDate, toDate);
        if(statusIsValidCustomDate == 1){
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu và ngày kết thúc không được trống", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
        } else
        if(statusIsValidCustomDate == 2){
            JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
        } else{
            Object selectedItem = cbxSelectTop.getSelectedItem();
            int limit = 0;
            if(selectedItem instanceof String){
                try {
                    limit = Integer.parseInt((String) selectedItem);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập vào số nguyên dương", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                    
                if(limit <= 0){
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập vào số nguyên dương", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
                    return;    
                }
            } else
            if(selectedItem instanceof ComboItem){
                limit = Integer.parseInt(((ComboItem) cbxSelectTop.getSelectedItem()).getLogicValue());
            }

            ArrayList<TopProductsDTO> topProducts = topProductsBUS.getTopProductsByDate(fromDate, toDate, limit);

            lblTitle.setText(String.format("Top %d sản phẩm từ %s đến %s",
                            limit,
                            UtilsDateFormat.formatDate(fromDate),
                            UtilsDateFormat.formatDate(toDate)));

            loadTopProducts(topProducts);
        }
    }
    private void loadTopProducts(ArrayList<TopProductsDTO> topProducts){
        tableModel.setRowCount(0);
        if(topProducts.size() == 0){
            noDataLabel.setVisible(true);
            spTable.setVisible(false);
            return;
        }

        noDataLabel.setVisible(false);
        spTable.setVisible(true);
        for(TopProductsDTO topProduct : topProducts){
            tableModel.addRow(new Object[]{
                topProduct.getMaSP(),
                topProduct.getTenSP(),
                topProduct.getSoLuong()
            });
        }
    }

    private void styleTable(){
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        UtilsJTableStyle.setStyleTableHeader(table, FONT_TITLE, Color.WHITE, new Color(70, 130, 180));
        UtilsJTableStyle.setStyleTableCell(table, FONT_LABEL);

        UtilsJTableStyle.setAlignmentTableCell(table, 0, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(table, 1, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(table, 2, SwingConstants.CENTER);
    }

    private int getCurrentComboItemIndex(ComboItem items[], ComboItem currentItem){
        int sz = items.length;
        for(int i = 0; i < sz; i++){
            if(currentItem.getLogicValue().equals(items[i].getLogicValue())){
                return i;
            }
        }

        return 0;
    }

    private JPanel createRow(JComponent... comps){
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));
        for(JComponent comp : comps){
            row.add(comp);
        }

        return row;
    }
}
