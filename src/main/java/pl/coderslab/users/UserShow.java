package pl.coderslab.users;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;
import static pl.coderslab.utils.Util.isEmpty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/show")
public class UserShow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        if (!isEmpty(id)) {
            UserDao userDao = new UserDao();
            User readUser = userDao.read(Integer.parseInt(id));
            request.setAttribute("user", readUser);

            getServletContext().getRequestDispatcher("/users/show.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect("/user/list");
        }
    }
}
