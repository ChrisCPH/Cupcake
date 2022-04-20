package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper implements IUserMapper
{
    ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public User login(String email, String password) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM cupcake.user WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int userId = rs.getInt("user_id");
                    String role = rs.getString("role");
                    int balance = 100;
                    user = new User(userId, email, password, role, balance);
                } else
                {
                    throw new DatabaseException("Wrong email or password");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }
    
    public User getUser(User user) throws DatabaseException {
        return null;
    }
    
    public User getUser(int userId) throws DatabaseException {
        return getUser(new User(userId, null, null, null, 0));
    }

    @Override
    public User createUser(String email, String password, String role, int balance) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        String sql = "insert into cupcake.user (email, password, role, balance) values (?,?,?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, role);
                ps.setInt(4, balance);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    user = new User(email, password, role, balance);
                } else
                {
                    throw new DatabaseException("The user with email = " + email + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert email into database");
        }
        return user;
    }


    public User[] getAllUsers() throws DatabaseException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM cupcake.user ";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    int balance = rs.getInt("balance");
                    User user = new User(email, password, role, balance);
                    users.add(user);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return users.toArray(new User[0]);
    }

    public int getMaxUserId() throws SQLException {
        Statement st = connectionPool.getConnection().createStatement();
        String sql = "SELECT MAX(user_id) FROM user";
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            int userId = rs.getInt("MAX(user_id)");
            return userId;
        }
        return 0;
    }
}
