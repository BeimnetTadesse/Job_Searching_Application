package model;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private String resumeLink;

    // Constructor without userId (e.g. for new users before DB assigns ID)
    public User(String name, String email, String password, String role, String resumeLink) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.resumeLink = resumeLink;
    }

    // Full constructor with userId (e.g. for existing users from DB)
    public User(int userId, String name, String email, String password, String role, String resumeLink) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.resumeLink = resumeLink;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    // ✅ Added this method to fix the error in the controller
    public String getUsername() {
        return name;  // Or return email if preferred
    }
}
