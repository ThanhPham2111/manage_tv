package DTO;

public class AccountDTO {
    private int id;
    private String email;
    private String username;
    private String password;
    private String employeeId;
    private String roleId;

    public AccountDTO() {
    }

    public AccountDTO(int id, String email, String username, String password, String employeeId, String roleId) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.employeeId = employeeId;
        this.roleId = roleId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}