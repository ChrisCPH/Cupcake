package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.Bottom;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

public class BottomMapper {
    ConnectionPool connectionPool;
    
    public BottomMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Bottom[] getBottoms() throws DatabaseException {
        ArrayList<Bottom> bottoms = new ArrayList<>();
        String sql = "SELECT * FROM bottom";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Bottom bottom = new Bottom(bottomId, bottomPrice, bottomName);
                    bottoms.add(bottom);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return bottoms.toArray(new Bottom[0]);
    }
    
    public Bottom readBottom(int bottomId) throws DatabaseException {
        Bottom bottom = null;
        String sql =
                "SELECT * FROM bottom " +
                "WHERE bottom_id = ?";
        String bottomName = null;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, bottomId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomName = rs.getString("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    bottom = new Bottom(bottomId, bottomPrice, bottomName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (bottomName == null) {
            throw new DatabaseException("Bottom with id: " + bottomId + " was not found in the database.");
        }
        return bottom;
    }
    
    public Bottom readBottomByName(String bottomName) throws DatabaseException {
        Bottom bottom = null;
        String sql =
                "SELECT * FROM bottom " +
                "WHERE bottom_name = ?";
        int bottomId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, bottomName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    bottom = new Bottom(bottomId, bottomPrice, bottomName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (bottomId == 0) {
            throw new DatabaseException("Bottom: " + bottomName + " was not found in the database.");
        }
        return bottom;
    }
    
    public void createBottom(Bottom bottom) throws DatabaseException {
        String findBottomSql =
                "SELECT * FROM bottom " +
                "WHERE bottom_id = ?";
        String sql =
                "INSERT INTO bottom (bottom_price, bottom_name) " +
                "VALUES (?, ?)";
        
        boolean bottomFound = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(findBottomSql)) {
                ps.setInt(1, bottom.getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomFound = true;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("An error occurred while trying to find bottom: " + bottom);
        }
        
        if (!bottomFound) {
            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, bottom.getPrice());
                    ps.setString(2, bottom.getName());
                    ps.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new DatabaseException("An error occurred while trying to create bottom: " + bottom);
            }
        }
    }
    
    public void deleteBottom(Bottom bottom) throws DatabaseException {
        String sql =
                "DELETE FROM bottom " +
                "WHERE bottom_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, bottom.getId());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }

    public ArrayList<Bottom> getBottom(String name) throws SQLException, DatabaseException {
        ArrayList<Bottom> bottoms = new ArrayList<>();
        String sql = "SELECT * FROM cupcake.bottom WHERE bottom_name = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ps.setString(1, name);
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Bottom bottom = new Bottom(bottomId, bottomPrice, bottomName);
                    bottoms.add(bottom);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return bottoms;
    }

    public int getMaxBottomId() throws SQLException {
        Statement st = connectionPool.getConnection().createStatement();
        String sql = "SELECT MAX(bottom_id) FROM bottom";
        ResultSet rs = st.executeQuery(sql);
        if(rs.next()) {
            int bottomId = rs.getInt("MAX(bottom_id)");
            return bottomId;
        }
        return 0;
    }
}
