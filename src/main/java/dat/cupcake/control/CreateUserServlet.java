package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateUserServlet", urlPatterns = {"/createuser"} )
public class CreateUserServlet extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        UserMapper userMapper = new UserMapper(connectionPool);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = "user";
        int balance = 100;
        try {
            userMapper.createUser(email, password, role, balance);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void destroy()
    {

    }
}