package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class OrderMapper {
    ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Order[] getOrders() throws DatabaseException {

        ArrayList<Order> orders = new ArrayList<>();
        String sql =
                "SELECT * FROM cupcake.orders " +
                        "INNER JOIN cupcake.bottom " +
                        "using(bottom_id) " +
                        "INNER JOIN cupcake.topping " +
                        "using(topping_id)" +
                        "INNER JOIN cupcake.user " +
                        "using(user_id) " +
                        "ORDER BY order_id";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    int userId = rs.getInt("user_id");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    String password = rs.getString("password");
                    int balance = rs.getInt("balance");
                    String status = rs.getString("status");
                    String date = rs.getString("date");
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    User user = new User(userId, email, password, role, balance);
                    Topping topping = new Topping(toppingId, toppingPrice, toppingName);
                    Bottom bottom = new Bottom(bottomId, bottomPrice, bottomName);
                    int price = bottomPrice + toppingPrice;
                    Order order = new Order(orderId, user, status, date, topping, bottom, price);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return orders.toArray(new Order[0]);
    }

    public Order[] getOrdersByUser(User user) throws DatabaseException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql =
                "SELECT * FROM cupcake.orders " +
                        "INNER JOIN cupcake.bottom " +
                        "using(bottom_id) " +
                        "INNER JOIN cupcake.topping " +
                        "using(topping_id)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    String status = rs.getString("status");
                    String date = rs.getString("date");
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Order order = new Order(orderId, user, status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return orders.toArray(new Order[0]);
    }

    public Order[] getActiveOrdersByUser(User user) throws DatabaseException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql =
                "SELECT * FROM cupcake.orders " +
                        "INNER JOIN cupcake.bottom " +
                        "using(bottom_id) " +
                        "INNER JOIN cupcake.topping " +
                        "using(topping_id) " +
                        "WHERE user_id = ? AND (status = 'PREPARING' OR status = 'AWAITING_PICKUP')";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, user.getUserId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    String status = rs.getString("status");
                    String date = rs.getString("date");
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Order order = new Order(orderId, user, status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return orders.toArray(new Order[0]);
    }

    public void createOrder(Order order) throws DatabaseException {
        String sql =
                "INSERT INTO cupcake.orders (user_id, topping_id, bottom_id, status, date) " +
                        "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getUser().getUserId());
                ps.setInt(2, order.getBottom().getId());
                ps.setInt(3, order.getTopping().getId());
                ps.setString(4, order.getStatus());
                ps.setString(5, order.getDate());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }

    public Order readOrder(int orderId) throws DatabaseException {
        Order order = null;
        String sql =
                "SELECT * FROM cupcake.orders " +
                        "INNER JOIN cupcake.bottom " +
                        "using(bottom_id) " +
                        "INNER JOIN cupcake.topping " +
                        "using(topping_id) " +
                        "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String status = rs.getString("status");
                    String date = rs.getString("date");
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    order = new Order(orderId, new User(userId), status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return order;
    }

    public void updateOrder(Order order) throws DatabaseException {
        String sql =
                "UPDATE cupcake.orders " +
                        "SET topping_id = ?, bottom_id = ?, status = ?, date = ? " +
                        "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getTopping().getId());
                ps.setInt(2, order.getBottom().getId());
                ps.setString(3, order.getStatus().toString());
                ps.setString(4, order.getDate().toString());
                ps.setInt(5, order.getOrderId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }

    public void deleteOrder(Order order) throws DatabaseException {
        String sql =
                "DELETE FROM cupcake.orders " +
                        "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getOrderId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }

    public void updateOrderStatus(Order order) throws DatabaseException {
        String sql =
                "UPDATE cupcake.orders " +
                        "SET status = ? " +
                        "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, order.getStatus().toString());
                ps.setInt(2, order.getOrderId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }

    public int getMaxOrderId() throws SQLException {
        Statement st = connectionPool.getConnection().createStatement();
        String sql = "SELECT MAX(order_id) FROM cupcake.orders";
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            int orderId = rs.getInt("MAX(order_id)");
            return orderId;
        }
        return 0;
    }
}
