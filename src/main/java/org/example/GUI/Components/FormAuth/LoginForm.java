package org.example.GUI.Components.FormAuth;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import org.example.DTO.SessionManager;
import org.example.DTO.UsersDTO;
import org.example.GUI.Application.Application;

import net.miginfocom.swing.MigLayout;

public class LoginForm extends JPanel {
    UserBUS userBUS = new UserBUS();
    private Runnable loginSuccessListener;
    private JPanel Left;
    private JPanel Right;
    private JButton btnForgotPassword;
    private JButton btnLogin;
    private JButton btnSignUp;
    private JLabel lblTitle;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblLogo;
    private JLabel lblAppName;
    private JLabel lblFooter;
    private JPasswordField passwordField;
    private JTextField emailField;
    SessionManager sessionManager = new SessionManager();
    private static final String LOGIN_INFO_FILE = "last_login.txt";

    public LoginForm() {
        initComponents();
        setLoginSuccessListener(() -> Application.login());
        loadLastLoginInfo();
    }

    private void initComponents() {
        setBackground(new Color(240, 242, 245));
        setLayout(new MigLayout("fill, al center center", "[grow]", "[grow]"));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JPanel container = new JPanel(new MigLayout("ins 0", "[400px][400px]", "[500px]"));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));

        // Left Panel (Logo and Text) với Gradient
        Left = new JPanel() {
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
        Left.setLayout(new MigLayout("fill, al center center", "[grow]", "push[]50[]30[]push"));
        Left.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblLogo = new JLabel(new ImageIcon(getClass().getResource("/org/example/GUI/menu/logo/logojavawing.png")));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        Left.add(lblLogo, "center, wrap");

        lblAppName = new JLabel("QUẢN LÝ TV");
        lblAppName.setFont(new Font("Showcard Gothic", Font.BOLD, 24));
        lblAppName.setForeground(Color.WHITE);
        lblAppName.setHorizontalAlignment(SwingConstants.CENTER);
        Left.add(lblAppName, "center, wrap");

        lblFooter = new JLabel("© 2025 All rights reserved");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFooter.setForeground(new Color(204, 204, 204));
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        Left.add(lblFooter, "center");

        // Right Panel (Login Form)
        Right = new JPanel(new MigLayout("fill, al center center, wrap 1", "[grow]", "[]30[]15[]15[]20[]10[]"));
        Right.setBackground(Color.WHITE);

        lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(0, 102, 102));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        Right.add(lblTitle, "center");

        // Email Field
        JPanel emailPanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]")); // Sửa 'g娶ow' thành 'grow'
        emailPanel.setBackground(Color.WHITE);
        lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(new Color(66, 80, 102));
        emailPanel.add(lblEmail);

        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        emailField.setBackground(Color.WHITE);
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick();
                }
            }
        });
        emailPanel.add(emailField, "growx");
        Right.add(emailPanel, "width 300px, center");

        // Password Field
        JPanel passwordPanel = new JPanel(new MigLayout("fill", "[]10[grow]", "[]")); // Sửa 'g娶ow' thành 'grow'
        passwordPanel.setBackground(Color.WHITE);
        lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setForeground(new Color(66, 80, 102));
        passwordPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        passwordField.setBackground(Color.WHITE);
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick();
                }
            }
        });
        passwordPanel.add(passwordField, "growx");
        Right.add(passwordPanel, "width 300px, center");

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 102, 102));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 153, 153));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 102, 102));
            }
        });
        btnLogin.addActionListener(e -> performLogin());
        Right.add(btnLogin, "center");

        btnForgotPassword = new JButton("Quên mật khẩu?");
        btnForgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnForgotPassword.setForeground(new Color(0, 102, 255));
        btnForgotPassword.setBorderPainted(false);
        btnForgotPassword.setContentAreaFilled(false);
        btnForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnForgotPassword.addActionListener(
                e -> JOptionPane.showMessageDialog(null, "Redirecting to Forgot Password screen..."));
        Right.add(btnForgotPassword, "center");

        JPanel signUpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        signUpPanel.setBackground(Color.WHITE);
        JLabel lblSignUp = new JLabel("Chưa có tài khoản?");
        lblSignUp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSignUp.setForeground(new Color(66, 80, 102));
        btnSignUp = new JButton("Đăng ký");
        btnSignUp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSignUp.setForeground(new Color(255, 51, 51));
        btnSignUp.setBorderPainted(false);
        btnSignUp.setContentAreaFilled(false);
        btnSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignUp.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                SignUpForm signUp = new SignUpForm();
                Application.getInstance().setContentPane(signUp);
                Application.getInstance().revalidate();
                Application.getInstance().repaint();
            });
        });
        signUpPanel.add(lblSignUp);
        signUpPanel.add(btnSignUp);
        Right.add(signUpPanel, "center");

        container.add(Left, "grow");
        container.add(Right, "grow");
        add(container, "center");
    }

    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email và mật khẩu không được để trống!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsersDTO foundUser = userBUS.getListAccount().stream()
                .filter(user -> user.getUserEmail().equals(email))
                .findFirst()
                .orElse(null);

        if (foundUser == null) {
            JOptionPane.showMessageDialog(null, "Email không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Mật khẩu trong DB: " + foundUser.getUserPassword());
        if (foundUser.getUserPassword().equals(password)) {
            sessionManager.setCurrentUser(foundUser);
            saveLoginInfo(email, password);
            JOptionPane.showMessageDialog(null, "Đăng nhập thành công! 🎉", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            UsersDTO currentUser = SessionManager.getCurrentUser();
            if (currentUser != null) {
                System.out.println("Current username: " + currentUser.getUserName());
            } else {
                System.err.println("Current user is null after login!");
            }
            if (loginSuccessListener != null) {
                SwingUtilities.invokeLater(loginSuccessListener);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Mật khẩu không đúng! ❌", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveLoginInfo(String email, String password) {
        try (FileWriter writer = new FileWriter(LOGIN_INFO_FILE)) {
            writer.write(email + "\n");
            writer.write(password + "\n");
        } catch (IOException e) {
            System.err.println("Error saving login info: " + e.getMessage());
        }
    }

    private void loadLastLoginInfo() {
        File file = new File(LOGIN_INFO_FILE);
        if (file.exists()) {
            try {
                String[] lines = Files.readAllLines(Paths.get(LOGIN_INFO_FILE)).toArray(new String[0]);
                if (lines.length >= 2) {
                    emailField.setText(lines[0]);
                    passwordField.setText(lines[1]);
                }
            } catch (IOException e) {
                System.err.println("Error loading login info: " + e.getMessage());
            }
        }
    }

    public void setLoginSuccessListener(Runnable listener) {
        this.loginSuccessListener = listener;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Form Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new LoginForm());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}