package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.Topping;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class ToppingMapper {
    ConnectionPool connectionPool;
    
    public ToppingMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Topping[] getToppings() throws DatabaseException {
        ArrayList<Topping> toppings = new ArrayList<>();
        String sql = "SELECT * FROM cupcake.topping";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    Topping topping = new Topping(toppingId, toppingPrice, toppingName);
                    toppings.add(topping);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return toppings.toArray(new Topping[0]);
    }
    
    public Topping readTopping(int toppingId) throws DatabaseException {
        Topping topping = null;
        String sql =
                "SELECT * FROM cupcake.topping " +
                "WHERE topping_id = ?";
        String toppingName = null;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, toppingId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    toppingName = rs.getString("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    topping = new Topping(toppingId, toppingPrice, toppingName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (toppingName == null) {
            throw new DatabaseException("Topping with id: " + toppingId + " was not found in the database.");
        }
        return topping;
    }
    
    public Topping readToppingByName(String toppingName) throws DatabaseException {
        Topping topping = null;
        String sql =
                "SELECT * FROM cupcake.topping " +
                "WHERE topping_name = ?";
        int toppingId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, toppingName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    topping = new Topping(toppingId, toppingPrice, toppingName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (toppingId == 0) {
            throw new DatabaseException("Topping with name: " + toppingName + " was not found in the database.");
        }
        return topping;
    }
    
    public void createTopping(Topping topping) throws DatabaseException {
        String findToppingSql =
                "SELECT * FROM cupcake.topping " +
                "WHERE topping_id = ?";
        String sql =
                "INSERT INTO cupcake.topping (topping_price, topping_name) " +
                "VALUES (?, ?)";
        
        boolean toppingFound = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(findToppingSql)) {
                ps.setInt(1, topping.getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    toppingFound = true;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("An error occurred while trying to find topping: " + topping);
        }
        
        if (!toppingFound) {
            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, topping.getPrice());
                    ps.setString(2, topping.getName());
                    ps.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new DatabaseException("An error occurred while trying to create topping: " + topping);
            }
        }
    }
    
    public void deleteTopping(Topping topping) throws DatabaseException {
        String sql =
                "DELETE FROM cupcake.topping " +
                "WHERE topping_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, topping.getId());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }

    public ArrayList<Topping> getTopping(String name) throws SQLException, DatabaseException {
        ArrayList<Topping> toppings = new ArrayList<>();
        String sql = "SELECT * FROM cupcake.topping WHERE topping_name = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ps.setString(1, name);
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    Topping topping = new Topping(toppingId, toppingPrice, toppingName);
                    toppings.add(topping);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return toppings;
    }

    public int getMaxToppingId() throws SQLException {
        Statement st = connectionPool.getConnection().createStatement();
        String sql = "SELECT MAX(topping_id) FROM topping";
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            int toppingId = rs.getInt("MAX(topping_id)");
            return toppingId;
        }
        return 0;
    }
}
