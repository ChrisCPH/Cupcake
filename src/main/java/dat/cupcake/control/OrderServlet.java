package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.Bottom;
import dat.cupcake.model.entities.Order;
import dat.cupcake.model.entities.Topping;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "OrderServlet", urlPatterns = {"/order"} )
public class OrderServlet extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doPost(request, response);
        response.sendRedirect("order.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        BottomMapper bottomMapper = new BottomMapper(connectionPool);
        Bottom[] bottomList = new Bottom[0];
        ToppingMapper toppingMapper = new ToppingMapper(connectionPool);
        Topping[] toppingList = new Topping[0];
        try {
            bottomList = bottomMapper.getBottoms();
            toppingList = toppingMapper.getToppings();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("bottomList", bottomList);
        request.setAttribute("toppingList", toppingList);

        UserMapper userMapper = new UserMapper(connectionPool);
        OrderMapper orderMapper = new OrderMapper(connectionPool);

        String status = "Awaiting payment";
        String date = "2022-04-21 11:36:21";
        String bottomName = request.getParameter("bottomName");
        String toppingName = request.getParameter("toppingName");

        try {
            ArrayList<Topping> topping = toppingMapper.getTopping(toppingName);
            ArrayList<Bottom> bottom = bottomMapper.getBottom(bottomName);
            int orderId = orderMapper.getMaxOrderId() + 1;
            int userId = userMapper.getMaxUserId() + 1;
            int price = topping.get(0).getPrice() + bottom.get(0).getPrice();
            User user = new User(userId);
            Order order = new Order(orderId, user, status, date, new Topping(topping.get(0).getId(), topping.get(0).getPrice(), topping.get(0).getName()), new Bottom(bottom.get(0).getId(), bottom.get(0).getPrice(), bottom.get(0).getName()), price);
            orderMapper.createOrder(order);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
        }
    }

    public void destroy()
    {

    }
}