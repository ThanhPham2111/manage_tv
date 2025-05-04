package org.example.GUI.Components.FormStatistic.Revenue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.example.BUS.RevenueBUS;
import org.example.DTO.RevenueDTO;
import org.example.GUI.Components.FormHoaDon.ComboItem;
import org.example.GUI.Components.FormHoaDon.Utils.UtilsJTableStyle;
import org.example.UtilsDate.FormattedDatePicker;
import org.example.UtilsDate.UtilsDateFormat;


public class RevenueTab extends JPanel{
    private final RevenueBUS revenueBUS = new RevenueBUS();
    private final Font FONT_LABEL = new Font("Segoe UI", java.awt.Font.PLAIN, 16);
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 19); 
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");
    private final int BUTTON_WIDTH = 125;

    private JPanel chartPanel, tablePanel;
    private BarChartPanel barChartPanel;
    
    private DefaultTableModel revenueTableModel;
    private JTable revenueTable;
    private JScrollPane spRevenueTable;
    private JLabel lblTitle;
    private JLabel noDataLabel;

    private JPanel filterPanel;
    private JPanel viewByPanel, monthPanel, yearPanel, customDatePanel;
    private JComboBox<ComboItem> cbxViewBy;
    private JComboBox<ComboItem> cbxMonth;
    private JComboBox<ComboItem> cbxYear;
    private FormattedDatePicker fromDatePicker;
    private FormattedDatePicker toDatePicker;
    private JButton applyFilterBtn;
    private final ComboItem optionsViewBy[] = {

        new ComboItem("Trong 7 ngày", "7Days"),
        new ComboItem("Trong 30 ngày", "30Days"),
        new ComboItem("Năm", "Years"),
        new ComboItem("Tháng", "Months"),
        new ComboItem("Ngày", "Days"),
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
        new ComboItem("2023", "2023"),
        new ComboItem("2024", "2024"),
        new ComboItem("2025", "2025")
    };

    public RevenueTab(){
        setLayout(new BorderLayout());
        initFilterPanel();
        initChartPanel();
        initTablePanel();
        
        add(filterPanel, BorderLayout.NORTH);
        
        add(tablePanel, BorderLayout.CENTER);
        add(chartPanel, BorderLayout.SOUTH);
        setListeners();
        cbxViewBy.setSelectedIndex(0);
    }

    private void initFilterPanel(){
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        cbxViewBy = new JComboBox<>(optionsViewBy);
        cbxMonth = new JComboBox<>(optionsMonth);
        cbxYear = new JComboBox<>(optionsYear);
        fromDatePicker = new FormattedDatePicker(null);
        toDatePicker = new FormattedDatePicker(null);
        applyFilterBtn = new JButton("Lọc");
        applyFilterBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, applyFilterBtn.getPreferredSize().height));

        ((JLabel)cbxViewBy.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)cbxMonth.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)cbxYear.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        viewByPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        viewByPanel.add(new JLabel("Thống kê theo: "));
        viewByPanel.add(cbxViewBy);

        monthPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        monthPanel.add(new JLabel("trong tháng"));
        monthPanel.add(cbxMonth);

        yearPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        yearPanel.add(new JLabel("trong năm"));
        yearPanel.add(cbxYear);

        customDatePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        customDatePanel.add(new JLabel("từ ngày"));
        customDatePanel.add(fromDatePicker);

        customDatePanel.add(new JLabel("đến ngày"));
        customDatePanel.add(toDatePicker);

        customDatePanel.add(applyFilterBtn);

        viewByPanel.setVisible(true);
        monthPanel.setVisible(false);
        yearPanel.setVisible(false);
        customDatePanel.setVisible(false);

        filterPanel.add(viewByPanel);
        filterPanel.add(monthPanel);
        filterPanel.add(yearPanel);
        filterPanel.add(customDatePanel);
    }

    private void initTablePanel(){
        lblTitle = new JLabel("");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));

        noDataLabel = new JLabel("CHƯA CÓ DỮ LIỆU");
        noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 35));
        noDataLabel.setForeground(Color.LIGHT_GRAY);
        noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noDataLabel.setAlignmentX(0.5f);
        noDataLabel.setAlignmentY(0.5f);

        tablePanel = new JPanel(new BorderLayout(0, 15));
        String columnNames[] = {"Thời gian", "Doanh thu"};
        revenueTableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        revenueTable = new JTable(revenueTableModel);
        revenueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        spRevenueTable = new JScrollPane(revenueTable);

        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));

        overlayPanel.add(noDataLabel);
        overlayPanel.add(spRevenueTable);

        tablePanel.add(lblTitle, BorderLayout.NORTH);
        tablePanel.add(overlayPanel, BorderLayout.CENTER);

        styleTable();
    }

    private void initChartPanel(){
        barChartPanel = new BarChartPanel();

        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(getWidth(), 365));
        chartPanel.add(barChartPanel, BorderLayout.CENTER);
    }

    private void drawBarChart(ArrayList<RevenueDTO> revenues, String selectedViewBy){
        ArrayList<Bar> datas = new ArrayList<>();
        
        for(RevenueDTO revenue : revenues){
            datas.add(new Bar(revenue.getDate(), revenue.getTongTien()));
        }

        barChartPanel.setData(datas, selectedViewBy);
        barChartPanel.revalidate(); 
        barChartPanel.repaint();
    }

    private void loadRevenues(ArrayList<RevenueDTO> revenues, String selectedViewBy){
        revenueTableModel.setRowCount(0);
        if(revenues.size() == 0){
            noDataLabel.setVisible(true);
            spRevenueTable.setVisible(false);
            lblTitle.setText("");
            return;
        }

        noDataLabel.setVisible(false);
        spRevenueTable.setVisible(true);
        
        for(RevenueDTO revenue : revenues){
            String dateStr = "";
            if("Years".equalsIgnoreCase(selectedViewBy)){
                dateStr = UtilsDateFormat.formatYear(revenue.getDate());
            } else
            if("Months".equalsIgnoreCase(selectedViewBy)){
                dateStr = UtilsDateFormat.formatMonthYear(revenue.getDate());
            } else{
                dateStr = UtilsDateFormat.formatDate(revenue.getDate());
            }

            revenueTableModel.addRow(new Object[]{
                dateStr,
                priceFormatter.format(revenue.getTongTien())
            });
        }
    }

    private void setListeners(){
        setListenerCbxViewBy();
        setListenerCbxMonth();
        setListenerCbxYear();
        setListenerBarChart();
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
            if("30Days".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(false);
                yearPanel.setVisible(false);
                customDatePanel.setVisible(false);
            } else
            if("Years".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(false);
                yearPanel.setVisible(false);
                customDatePanel.setVisible(false);
            } else
            if("Months".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(false);
                yearPanel.setVisible(true);
                customDatePanel.setVisible(false);
            } else
            if("Days".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(true);
                yearPanel.setVisible(true);
                customDatePanel.setVisible(false);
            } else
            if("Custom".equalsIgnoreCase(selectedViewBy)){
                monthPanel.setVisible(false);
                yearPanel.setVisible(false);
                customDatePanel.setVisible(true);
            }
        });
        
        cbxViewBy.addActionListener((e) -> {
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

            int currentMonth = Integer.parseInt(currentMonthOption.getLogicValue());
            int currentYear = Integer.parseInt(currentYearOption.getLogicValue());

            String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();
            if("7Days".equalsIgnoreCase(selectedViewBy)){
                lblTitle.setText("Doanh thu trong 7 ngày");
            } else
            if("30Days".equalsIgnoreCase(selectedViewBy)){
                lblTitle.setText("Doanh thu trong 30 ngày");
            } else
            if("Years".equalsIgnoreCase(selectedViewBy)){
                lblTitle.setText("Doanh thu các năm");
            }
            else
            if("Months".equalsIgnoreCase(selectedViewBy)){
                cbxYear.setSelectedItem(currentYearItem);
                lblTitle.setText(String.format("Doanh thu các tháng trong %d", currentYear));
            }
            else
            if("Days".equalsIgnoreCase(selectedViewBy)){
                cbxMonth.setSelectedItem(currentMonthItem);
                cbxYear.setSelectedItem(currentYearItem);
                lblTitle.setText(String.format("Doanh thu các ngày trong %02d/%d", currentMonth, currentYear));
            }

            ArrayList<RevenueDTO> revenues;
            if("7Days".equalsIgnoreCase(selectedViewBy)){
                Date fromDate, toDate;
                Date refDates[] = new Date[3];
                revenueBUS.setFromDateToDate(refDates, 7);
                fromDate = refDates[0];
                toDate = refDates[1];

                revenues = revenueBUS.getRevenuesByDate(fromDate, toDate);
                lblTitle.setText("Doanh thu trong 7 ngày");
            } else
            if("30Days".equalsIgnoreCase(selectedViewBy)){
                Date fromDate, toDate;
                Date refDates[] = new Date[3];
                revenueBUS.setFromDateToDate(refDates, 30);
                fromDate = refDates[0];
                toDate = refDates[1];
                
                revenues = revenueBUS.getRevenuesByDate(fromDate, toDate);
                lblTitle.setText("Doanh thu trong 30 ngày");
            } else{
                revenues = revenueBUS.getRevenues(selectedViewBy, currentMonth, currentYear);
            }
            
            drawBarChart(revenues, selectedViewBy);
            loadRevenues(revenues, selectedViewBy);
        });
    }

    private void setListenerCbxMonth(){
        cbxMonth.addActionListener((e) -> {
            ComboItem selectedMonthOption = ((ComboItem) cbxMonth.getSelectedItem());
            ComboItem selectedYearOption = ((ComboItem) cbxYear.getSelectedItem());
            int selectedMonth = Integer.parseInt(selectedMonthOption.getLogicValue());
            int selectedYear = Integer.parseInt(selectedYearOption.getLogicValue());

            String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();
            ArrayList<RevenueDTO> revenues = revenueBUS.getRevenues(selectedViewBy, selectedMonth, selectedYear);
            lblTitle.setText(String.format("Doanh thu các ngày trong %02d/%d", selectedMonth, selectedYear));
            drawBarChart(revenues, selectedViewBy);
            loadRevenues(revenues, selectedViewBy);
        });
    }

    private void setListenerCbxYear(){
        cbxYear.addActionListener((e) -> {
            ComboItem selectedMonthOption = ((ComboItem) cbxMonth.getSelectedItem());
            ComboItem selectedYearOption = ((ComboItem) cbxYear.getSelectedItem());
            int selectedMonth = Integer.parseInt(selectedMonthOption.getLogicValue());
            int selectedYear = Integer.parseInt(selectedYearOption.getLogicValue());

            String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();
            ArrayList<RevenueDTO> revenues = revenueBUS.getRevenues(selectedViewBy, selectedMonth, selectedYear);
            if("Months".equals(selectedViewBy)){
                lblTitle.setText(String.format("Doanh thu các tháng trong %d", selectedYear));
            } else
            if("Days".equals(selectedViewBy)){
                lblTitle.setText(String.format("Doanh thu các ngày trong %02d/%d", selectedMonth, selectedYear));
            }
            drawBarChart(revenues, selectedViewBy);
            loadRevenues(revenues, selectedViewBy);
        });
    }

    private void setListenerBarChart(){
        barChartPanel.setListenerMouseClicked(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                Point p = e.getPoint();
                ArrayList<Rectangle2D> barBounds = barChartPanel.getBarBounds();
                int sz = barBounds.size();
                for(int i = 0; i < sz; i++){
                    if(barBounds.get(i).contains(p)){
                        revenueTable.setRowSelectionInterval(i, i);
                        Rectangle rect = revenueTable.getCellRect(i, i, true);
                        revenueTable.scrollRectToVisible(rect);
                        break;
                    }
                }
            }
        });
    }

    private void setListenerApplyFilterBtn(){
        applyFilterBtn.addActionListener((e) -> {
            Date fromDate = fromDatePicker.getDate();
            Date toDate = toDatePicker.getDate();

            short statusIsValidCustomDate = revenueBUS.validateDate(fromDate, toDate);
            if(statusIsValidCustomDate == 1){
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu và ngày kết thúc không được trống", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
            } else
            if(statusIsValidCustomDate == 2){
                JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
            } else{
                ArrayList<RevenueDTO> revenues = revenueBUS.getRevenuesByDate(fromDate, toDate);

                String selectedViewBy = ((ComboItem) cbxViewBy.getSelectedItem()).getLogicValue();
                lblTitle.setText(String.format("Doanh thu từ %s đến %s",
                                UtilsDateFormat.formatDate(fromDate),
                                UtilsDateFormat.formatDate(toDate)));
                drawBarChart(revenues, selectedViewBy);
                loadRevenues(revenues, selectedViewBy);
            }
        });

    }
    private int getCurrentComboItemIndex(ComboItem[] items, ComboItem selectedItem){
        int sz = items.length;
        for(int i = 0; i < sz; i++){
            if(items[i].getLogicValue().equalsIgnoreCase(selectedItem.getLogicValue())){
                return i;
            }
        }

        return 0;
    }

    private void styleTable(){
        revenueTable.setRowHeight(35);
        revenueTable.setShowGrid(true);
        revenueTable.setGridColor(new Color(230, 230, 230));

        // spRevenueTable.setPreferredSize(new Dimension(395, 135));
        spRevenueTable.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        UtilsJTableStyle.setStyleTableHeader(revenueTable, FONT_TITLE, Color.WHITE, new Color(70, 130, 180));
        UtilsJTableStyle.setStyleTableCell(revenueTable, FONT_LABEL);

        UtilsJTableStyle.setAlignmentTableCell(revenueTable, 0, SwingConstants.CENTER);
        UtilsJTableStyle.setAlignmentTableCell(revenueTable, 1, SwingConstants.RIGHT);
    }
}
