package org.example.GUI.Components.FormStatistic;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.example.GUI.Components.FormStatistic.Revenue.RevenueTab;
import org.example.GUI.Components.FormStatistic.TopProductsTab.TopProductsTab;

public class AdminDashboardView extends JPanel{
    JTabbedPane tabbedPane;
    public AdminDashboardView(){
        setLayout(new BorderLayout(11, 11));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Doanh thu", new RevenueTab());
        tabbedPane.addTab("Sản phẩm", new TopProductsTab());
        add(tabbedPane);
    }
}
