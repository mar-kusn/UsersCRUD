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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet("/user/add")
public class UserAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errorMsg = request.getParameter("errorMsg");
        if (!isEmpty(errorMsg)) {
            request.setAttribute("errorMsg", errorMsg);
        }
        getServletContext().getRequestDispatcher("/users/add.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!isEmpty(userName) && !isEmpty(email) && !isEmpty(password)) {
            UserDao userDao = new UserDao();
            User newUser = userDao.create(new User(email, userName, password));

            if (newUser != null) {
                System.out.println("new user: " + newUser);
                response.sendRedirect(request.getContextPath() + "/user/list");
            } else {
                String errorMsg = "Problem podczas dodawania użytkownika! Popraw dane!";
                response.sendRedirect(request.getContextPath() + "?errorMsg=" + URLEncoder.encode(errorMsg, StandardCharsets.UTF_8));
            }
        } else {
            String errorMsg = "Podaj wszystkie wymagane dane!";
            response.sendRedirect(request.getContextPath() + "?errorMsg=" + URLEncoder.encode(errorMsg, StandardCharsets.UTF_8));
        }
    }
}