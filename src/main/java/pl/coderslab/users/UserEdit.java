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

@WebServlet("/users/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        UserDao userDao = new UserDao();
        User readUser = userDao.read(Integer.parseInt(id));
        request.setAttribute("user", readUser);

        getServletContext().getRequestDispatcher("/users/edit.jsp")
                .forward(request, response);
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
            User editUser = userDao.read(Integer.parseInt(id));

            editUser.setUserName(userName);
            editUser.setEmail(email);
            editUser.setPassword(password);

            userDao.update(editUser);
            response.sendRedirect("/users/list");
        } else {
            response.sendRedirect("/users/error.jsp");
        }
    }

    private boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }
}