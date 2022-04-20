package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewAccountsServlet", value = "/viewaccounts")
public class ViewAccountsServlet extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        UserMapper userMapper = new UserMapper(connectionPool);
        User[] userList = new User[0];
        try {
            userList = userMapper.getAllUsers();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/WEB-INF/viewaccounts.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

    }

    public void destroy()
    {

    }
}