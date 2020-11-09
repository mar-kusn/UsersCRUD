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
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        String errorMsg = request.getParameter("errorMsg");

        if (!isEmpty(errorMsg)) {
            request.setAttribute("errorMsg", errorMsg);
        }
        if (!isEmpty(id)) {
            UserDao userDao = new UserDao();
            User readUser = userDao.read(Integer.parseInt(id));
            request.setAttribute("user", readUser);

            getServletContext().getRequestDispatcher("/users/edit.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect("/user/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        PrintWriter writer = response.getWriter();
        writer.append("Edycja użytkownika! ");

        if (!isEmpty(userName) && !isEmpty(email) && !isEmpty(password)) {
            UserDao userDao = new UserDao();
            User editUser = new User();

            editUser.setId(Integer.parseInt(id));
            editUser.setUserName(userName);
            editUser.setEmail(email);
            editUser.setPassword(password);

            int count = userDao.update(editUser);

            if (count > 0) {
                System.out.println("edit user [" + id + "], new values: " + editUser);
                response.sendRedirect(request.getContextPath() + "/user/list");
            } else {
                String errorMsg = "Problem podczas edycji użytkownika! Popraw dane!";
                response.sendRedirect(request.getContextPath() + "/user/edit?id=" + id
                                + "&errorMsg=" + URLEncoder.encode(errorMsg, StandardCharsets.UTF_8));
            }
        } else {
            String errorMsg = "Podaj wszystkie wymagane dane!";
            response.sendRedirect(request.getContextPath() + "/user/edit?id=" + id
                                + "&errorMsg=" + URLEncoder.encode(errorMsg, StandardCharsets.UTF_8));
        }
    }
}