package org.example.GUI.Components.FormAuth;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.example.BUS.UserBUS;
import org.example.DTO.UsersDTO;
import org.example.GUI.Application.Application;

import net.miginfocom.swing.MigLayout;

public class SignUpForm extends JPanel {
    private JPanel container;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton signUpButton;
    private JButton backLoginButton;
    private JLabel lblTitle;
    private JLabel lblUsername;
    private JLabel lblFullName;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblLogo;
    private JLabel lblAppName;
    private JLabel lblFooter;
    private JTextField txtUsername;
    private JTextField txtFullName;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private UserBUS userBUS = new UserBUS();

    public SignUpForm() {
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(240, 242, 245));
        setLayout(new MigLayout("fill, al center center", "[grow]", "[grow]"));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        container = new JPanel(new MigLayout("ins 0", "[400px][400px]", "[500px]"));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));

        // Left Panel (Logo and Text) với Gradient
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 102), 0, getHeight(),
                        new Color(0, 153, 153));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setLayout(new MigLayout("fill, al center center", "[grow]", "push[]50[]30[]push"));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblLogo = new JLabel(new ImageIcon(getClass().getResource("/org/example/GUI/menu/logo/logojavawing.png")));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(lblLogo, "center, wrap");

        lblAppName = new JLabel("QUẢN LÝ TV");
        lblAppName.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        lblAppName.setForeground(Color.WHITE);
        lblAppName.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(lblAppName, "center, wrap");

        lblFooter = new JLabel("© 2025 All rights reserved");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFooter.setForeground(new Color(204, 204, 204));
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(lblFooter, "center");

        // Right Panel (Sign Up Form)
        rightPanel = new JPanel(
                new MigLayout("fill, al center center, wrap 1", "[grow]", "[]20[]10[]10[]10[]10[]20[]10[]"));
        rightPanel.setBackground(Color.WHITE);

        lblTitle = new JLabel("ĐĂNG KÝ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 102, 102));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(lblTitle, "center");

        // Username Field
        JPanel usernamePanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]"));
        usernamePanel.setBackground(Color.WHITE);
        lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsername.setForeground(new Color(66, 80, 102));
        usernamePanel.add(lblUsername);
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        txtUsername.setBackground(Color.WHITE);
        usernamePanel.add(txtUsername, "growx");
        rightPanel.add(usernamePanel, "width 300px, center");

        // Full Name Field
        JPanel fullNamePanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]"));
        fullNamePanel.setBackground(Color.WHITE);
        lblFullName = new JLabel("Họ và tên:");
        lblFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFullName.setForeground(new Color(66, 80, 102));
        fullNamePanel.add(lblFullName);
        txtFullName = new JTextField();
        txtFullName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFullName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        txtFullName.setBackground(Color.WHITE);
        fullNamePanel.add(txtFullName, "growx");
        rightPanel.add(fullNamePanel, "width 300px, center");

        // Email Field
        JPanel emailPanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]"));
        emailPanel.setBackground(Color.WHITE);
        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(new Color(66, 80, 102));
        emailPanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        txtEmail.setBackground(Color.WHITE);
        emailPanel.add(txtEmail, "growx");
        rightPanel.add(emailPanel, "width 300px, center");

        // Password Field
        JPanel passwordPanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]"));
        passwordPanel.setBackground(Color.WHITE);
        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setForeground(new Color(66, 80, 102));
        passwordPanel.add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        txtPassword.setBackground(Color.WHITE);
        passwordPanel.add(txtPassword, "growx");
        rightPanel.add(passwordPanel, "width 300px, center");

        // Sign Up Button
        signUpButton = new JButton("Đăng ký");
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpButton.setBackground(new Color(0, 102, 102));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signUpButton.setBackground(new Color(0, 153, 153));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signUpButton.setBackground(new Color(0, 102, 102));
            }
        });
        signUpButton.addActionListener(e -> performSignUp());
        rightPanel.add(signUpButton, "center");

        // Back to Login
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        backPanel.setBackground(Color.WHITE);
        JLabel lblBack = new JLabel("Đã có tài khoản?");
        lblBack.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblBack.setForeground(new Color(66, 80, 102));
        backLoginButton = new JButton("Đăng nhập");
        backLoginButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        backLoginButton.setForeground(new Color(255, 51, 51));
        backLoginButton.setBorderPainted(false);
        backLoginButton.setContentAreaFilled(false);
        backLoginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLoginButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                LoginForm loginForm = new LoginForm();
                Application.getInstance().setContentPane(loginForm);
                Application.getInstance().revalidate();
                Application.getInstance().repaint();
            });
        });
        backPanel.add(lblBack);
        backPanel.add(backLoginButton);
        rightPanel.add(backPanel, "center");

        container.add(leftPanel, "grow");
        container.add(rightPanel, "grow");
        add(container, "center");
    }

    private void performSignUp() {
        String username = txtUsername.getText().trim();
        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        for (UsersDTO user : userBUS.getListAccount()) {
            if (user.getUserName().equalsIgnoreCase(username) || user.getUserEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(null,
                        "Tên đăng nhập hoặc Email đã tồn tại! Vui lòng chọn tên khác.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (username.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(null, "Email không hợp lệ! Vui lòng nhập lại.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải có ít nhất 6 ký tự!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsersDTO newUser = new UsersDTO();
        newUser.setUserName(username);
        newUser.setUserFullName(fullName);
        newUser.setUserEmail(email);
        newUser.setUserPassword(password);
        newUser.setIsAdmin(false);

        boolean success = userBUS.insertUser(newUser);
        if (success) {
            JOptionPane.showMessageDialog(null, "Đăng ký thành công! 🎉", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(() -> {
                LoginForm loginForm = new LoginForm();
                Application.getInstance().setContentPane(loginForm);
                Application.getInstance().revalidate();
                Application.getInstance().repaint();
            });
        } else {
            JOptionPane.showMessageDialog(null, "Đăng ký thất bại! Vui lòng thử lại.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sign Up Form Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SignUpForm());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}