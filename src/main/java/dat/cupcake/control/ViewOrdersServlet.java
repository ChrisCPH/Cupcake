package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.Order;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewOrderServlet", value = "/vieworders")
public class ViewOrdersServlet extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        OrderMapper orderMapper = new OrderMapper(connectionPool);
        Order[] orderList = new Order[0];
        try {
            orderList = orderMapper.getOrders();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/WEB-INF/vieworders.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

    }

    public void destroy()
    {

    }
}