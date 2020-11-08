package pl.coderslab.users;

import pl.coderslab.User;
import pl.coderslab.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/delete")
public class UserDelete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        if (!isEmpty(id)) {
            UserDao userDao = new UserDao();
            User delUser = userDao.read(Integer.parseInt(id));
            request.setAttribute("id", id);
            request.setAttribute("user", delUser);

            getServletContext().getRequestDispatcher("/users/delete.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/users/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (!isEmpty(id) && !isEmpty(action)) {
            if ("delete".equals(action)) {
                UserDao userDao = new UserDao();
                userDao.delete(Integer.parseInt(id));
                System.out.println("deleted user id: " + id);
            }
        }
        response.sendRedirect(request.getContextPath() + "/users/list");
    }

    private boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }
}
