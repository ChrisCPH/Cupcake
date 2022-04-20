package dat.cupcake.model.entities;

import java.util.Objects;

public class User
{
    private int userId;
    private String email;
    private String password;
    private String role;
    private int balance;
    
    public User(int userId, String email, String password, String role, int balance) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }
    
    public User(String email, String password, String role, int balance)
    {
        this.email = email;
        this.password = password;
        this.role = role;
        this.balance = balance;
    }
    
    public User(int userId) {
        this.userId = userId;
    }

    public User(int userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getRole()
    {
        return role;
    }
    
    public void setRole(String role)
    {
        this.role = role;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "userId=" + userId +
                ", email=" + email +
                ", password=" + password +
                ", role=" + role +
                ", balance=" + balance;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEmail(), getPassword(), getRole());
    }
}
