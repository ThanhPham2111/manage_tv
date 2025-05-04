package org.example.GUI.Components.FormStatistic.Revenue;

import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import org.example.UtilsDate.UtilsDateFormat;

public class BarChartPanel extends JPanel{
    final static int PADDING_LEFT = 15;
    final static int PADDING_RIGHT = 25;
    final static int PADDING_TOP = 55;
    final static int PADDING_LABEL = 35;
    private final int NUM_OF_Y_DIVISIONS = 5;
    private final int BARS_GAP_MAX = 35;
    private final int BAR_WIDTH_MAX = 125;
    private final Font FONT_NORMAL = new Font("Roboto", Font.PLAIN, 14);
    private final Font FONT_BOLD = new Font("Arial", Font.BOLD, 16);
    private final DecimalFormat priceFormatter = new DecimalFormat("#,##0");

    private int chartWidth;
    private int chartHeight;
    private int numOfBars;
    private double maxBarValue;
    private double yGap;

    private String selectedViewBy;
    private ArrayList<Bar> datas;
    private ArrayList<Rectangle2D> barBounds;
    private ArrayList<String> tooltipTexts;

    public BarChartPanel(){
        // setPreferredSize(new Dimension(555, 455));
        ToolTipManager.sharedInstance().registerComponent(this);
        ToolTipManager.sharedInstance().setInitialDelay(50);
        ToolTipManager.sharedInstance().setDismissDelay(5000);


        barBounds = new ArrayList<>();
        tooltipTexts = new ArrayList<>();
        setListeners();
    }

    @Override
    public String getToolTipText(MouseEvent e){
        Point p = e.getPoint();
        int sz = barBounds.size();
        for(int i = 0; i < sz; i++){
            if(barBounds.get(i).contains(p)){
                return tooltipTexts.get(i);
            }
        }

        return null;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        if(numOfBars == 0){
            g2.dispose();
            return;
        }

        chartWidth = getWidth() - PADDING_LABEL - PADDING_LEFT - PADDING_RIGHT;
        chartHeight = getHeight() - PADDING_LABEL - PADDING_TOP;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawGrids(chartWidth, chartHeight, g2);
        drawX_Axis(chartWidth, chartHeight, g2);
        drawBars(chartWidth, chartHeight, g2);
        drawY_Axis(chartWidth, chartHeight, g2);
        g2.dispose();
    }

    private void drawX_Axis(int chartWidth, int chartHeight, Graphics2D g2){
        int x1 = PADDING_LEFT + PADDING_LABEL;
        int y1 = PADDING_TOP + chartHeight;
        int x2 = x1 + chartWidth;
        int y2 = y1;

        g2.setFont(FONT_NORMAL);
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.BLACK);
        g2.drawLine(x1, y1, x2, y2);
    }
    
    private void drawY_Axis(int chartWidth, int chartHeight, Graphics2D g2){
        int x1 = PADDING_LEFT + PADDING_LABEL;
        int y1 = PADDING_TOP;
        int x2 = x1;
        int y2 = y1 + chartHeight;

        g2.setFont(FONT_NORMAL);
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.BLACK);
        g2.drawLine(x1, y1, x2, y2);
        
        
        String str = "Triệu (VNĐ)";
        int strWidth = g2.getFontMetrics().stringWidth(str);
        int strHeight = g2.getFontMetrics().getHeight();

        g2.setFont(FONT_BOLD);
        g2.setColor(UIManager.getColor("Label.foreground"));
        g2.drawString(str, x1 - strWidth / 3, y1 - strHeight - 5);
    }

    private void drawGrids(int chartWidth, int chartHeight, Graphics2D g2){
        g2.setFont(FONT_NORMAL);
        g2.setStroke(new BasicStroke(1.0f));
        
        for(int i = 0; i <= NUM_OF_Y_DIVISIONS; i++){
            int x1 = PADDING_LEFT + PADDING_LABEL;
            int y1 = PADDING_TOP + (int) (1.0f * chartHeight * i / NUM_OF_Y_DIVISIONS);
            int x2 = x1 + chartWidth;
            int y2 = y1;
            
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(x1, y1, x2, y2);

            double value = yGap * (NUM_OF_Y_DIVISIONS - i);
            BigDecimal bigValue = BigDecimal.valueOf(value).divide(BigDecimal.valueOf(1000000f));

            String str = priceFormatter.format(bigValue);
            int strWidth = g2.getFontMetrics().stringWidth(str);

            g2.setColor(UIManager.getColor("Label.foreground"));
            g2.drawString(str, x1 - strWidth - 15, y1 + g2.getFontMetrics().getHeight() / 2);
        }
    }

    private void drawBars(int chartWidth, int chartHeight, Graphics2D g2){
        g2.setFont(FONT_NORMAL);
        g2.setStroke(new BasicStroke(1.0f));

        ArrayList<Double> barXs = new ArrayList<>();
        ArrayList<String> barTitles = new ArrayList<>();
        barBounds.clear();
        tooltipTexts.clear();

        // Nếu quá nhiều cột, gộp title các cột lại
        Boolean showShortDate = (numOfBars >= 10);
        if(showShortDate){
            barXs.clear();
            barTitles.clear();
        }

        double barsGap = 1.0f * chartWidth / numOfBars / 2;
        barsGap = Math.min(barsGap, BARS_GAP_MAX);

        double barWidth = (chartWidth - (barsGap * numOfBars)) / numOfBars;
        barWidth = Math.min(barWidth, BAR_WIDTH_MAX);

        for(int i = 0; i < numOfBars; i++){
            Bar bar = datas.get(i);
            
            double barHeight = ((bar.getTongTien() / maxBarValue) * chartHeight);
            double x = barsGap + PADDING_LEFT + PADDING_LABEL + i * (barWidth + barsGap);
            double y = PADDING_TOP + chartHeight - barHeight;
            
            Rectangle2D rectBar = new Rectangle2D.Double(x, y, barWidth, barHeight);
            barBounds.add(rectBar);
            tooltipTexts.add(UtilsDateFormat.formatDate(bar.getDate()));

            g2.setColor(Color.BLUE);
            g2.fill(rectBar);

            if(!showShortDate){
                String str = "";
                if("Years".equalsIgnoreCase(selectedViewBy)){
                    str = UtilsDateFormat.formatYear(datas.get(i).getDate());
                } else
                if("Months".equalsIgnoreCase(selectedViewBy)){
                    str = UtilsDateFormat.formatMonthYear(datas.get(i).getDate());
                } else{
                    str = UtilsDateFormat.formatDate(datas.get(i).getDate());
                }

                int strWidth = g2.getFontMetrics().stringWidth(str);
                double strX = x + (barWidth - strWidth)/ 2;
                double strY = PADDING_TOP + chartHeight + PADDING_LABEL - 5;

                g2.setColor(UIManager.getColor("Label.foreground"));
                g2.drawString(str, (float) strX, (float) strY);
            } else{
                int groupOf = Math.max(3, 7); // Mỗi nhóm sẽ có groupOf cột, ít nhất là nhóm 3 cột để không bị đè
                
                if((i % groupOf == 0) || (i % groupOf == (groupOf - 1))){
                    barXs.add(x);
                    barTitles.add(UtilsDateFormat.formatCustom("dd/MM", bar.getDate()));
                }
                // Nếu đã có cột bắt đầu mà không đủ groupOf cột và đã là cột cuối rồi, thì kết thúc ở đó luôn
                if((i == (numOfBars - 1)) && (i % groupOf != (groupOf - 1))){
                    barXs.add(x);
                    barTitles.add(UtilsDateFormat.formatCustom("dd/MM", bar.getDate()));
                }
            }
        }
        
        if(showShortDate){
            drawDateRange(barXs, barTitles , barWidth ,chartWidth, chartHeight, g2);
        }
    }

    private void drawDateRange(ArrayList<Double> barXs, ArrayList<String> barTitles, double barWidth, int chartWidth, int chartHeight, Graphics2D g2){
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(UIManager.getColor("Label.foreground"));
        int sz = barTitles.size();
        for(int i = 0; (i + 1) < sz; i += 2){
            String str = "";
            boolean onlyOneDate = barTitles.get(i).equalsIgnoreCase(barTitles.get(i + 1));
            if(onlyOneDate) str = barTitles.get(i);
            else str = barTitles.get(i) + " - " + barTitles.get(i + 1);

            int strWidth = g2.getFontMetrics().stringWidth(str);

            // int x1 = (int) ((double) (barXs.get(i) + barWidth / 2));
            int x1 = (int) (Math.round(barXs.get(i)));
            int y1 = PADDING_TOP + chartHeight;
            int x2 = x1;
            int y2 = y1 + PADDING_LABEL - 21;
            // g2.setColor(Color.BLACK);
            g2.setFont(FONT_BOLD);
            g2.drawLine(x1, y1, x1, y2);

            // x2 = (int) ((double) (barXs.get(i + 1) + barWidth / 2));
            x2 = (int) (Math.round(barXs.get(i + 1) + barWidth)) - 1;
            g2.drawLine(x2, y1, x2, y2);

            g2.drawLine(x1, y2, x2, y2);

            double strX = barXs.get(i) + (barXs.get(i + 1) + barWidth - barXs.get(i)) / 2 - strWidth / 2.0f;
            double strY = PADDING_TOP + chartHeight + PADDING_LABEL - 5;

            g2.setFont(FONT_NORMAL);
            g2.drawString(str, (float) strX, (float) strY);
        }
    }

    public void setData(ArrayList<Bar> datas, String selectedViewBy){
        this.selectedViewBy = selectedViewBy;
        this.datas = datas;
        numOfBars = datas.size();
        maxBarValue = 0.0;
        for(Bar data : datas){
            maxBarValue = Math.max(maxBarValue, data.getTongTien());
        }

        yGap = Math.round(maxBarValue / NUM_OF_Y_DIVISIONS);
        maxBarValue = yGap * NUM_OF_Y_DIVISIONS;
    }

    private void setListeners(){
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e){
                Point p = e.getPoint();
                Boolean isHovering = false;

                int sz = barBounds.size();
                for(int i = 0; i < sz; i++){
                    if(barBounds.get(i).contains(p)){
                        isHovering = true;
                        break;
                    }
                }
                
                if(isHovering){
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else{
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
    }
    void setListenerMouseClicked(MouseAdapter mouseAdapter){
        this.addMouseListener(mouseAdapter);
    }


    ArrayList<Rectangle2D> getBarBounds(){
        return barBounds;
    }
}
