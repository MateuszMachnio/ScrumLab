package pl.coderslab.web;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.AdminsDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminsDao adminsDao = new AdminsDao();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email.equals("") || password.equals("")) {
            response.sendRedirect("/noDataLogin.jsp");
            return;
        }
        Admins user = adminsDao.readByEmail(email);
        boolean checkpw = BCrypt.checkpw(password, user.getPassword());
        if (checkpw) {
            response.sendRedirect("/");
        } else response.sendRedirect("/wrongLogin.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/login.jsp");

    }
}
