package org.example.GUI.Application.other;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class LoginPanel extends JPanel {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton btnLogin;
    private JButton btnForgotPassword;
    private JLabel lblTitle;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(240, 242, 245)); // Nền nhẹ nhàng
        setLayout(new MigLayout("fill, al center center", "[grow]", "[grow]"));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel container = new JPanel(new MigLayout("ins 0", "[300px][400px]", "[400px]"));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));

        // Left Panel (Trang trí với gradient)
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(),
                        new Color(30, 90, 140));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new MigLayout("fill, al center center", "[grow]", "push[]push"));
        JLabel lblWelcome = new JLabel("Chào mừng bạn!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(lblWelcome, "center");
        container.add(leftPanel, "grow");

        // Right Panel (Form đăng nhập)
        rightPanel = new JPanel(new MigLayout("fill, al center center, wrap 1", "[grow]", "[]20[]10[]10[]20[]10[]"));
        rightPanel.setBackground(Color.WHITE);

        lblTitle = new JLabel("Đăng Nhập");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(30, 90, 140));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(lblTitle, "center");

        // Email Field
        JPanel emailPanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]"));
        emailPanel.setBackground(Color.WHITE);
        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(new Color(50, 50, 50));
        emailPanel.add(lblEmail);

        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        emailField.setBackground(Color.WHITE);
        emailPanel.add(emailField, "growx");
        rightPanel.add(emailPanel, "width 300px, center");

        // Password Field
        JPanel passwordPanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]"));
        passwordPanel.setBackground(Color.WHITE);
        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setForeground(new Color(50, 50, 50));
        passwordPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        passwordField.setBackground(Color.WHITE);
        passwordPanel.add(passwordField, "growx");
        rightPanel.add(passwordPanel, "width 300px, center");

        // Login Button
        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(btnLogin, "center");

        // Forgot Password Link
        btnForgotPassword = new JButton("Quên mật khẩu?");
        btnForgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnForgotPassword.setForeground(new Color(30, 90, 140));
        btnForgotPassword.setBorderPainted(false);
        btnForgotPassword.setContentAreaFilled(false);
        btnForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(btnForgotPassword, "center");

        container.add(rightPanel, "grow");
        add(container, "center");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new LoginPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}