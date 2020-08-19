package pl.coderslab.web;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        if (name.equals("") || surname.equals("") || email.equals("") || password.equals("") || repassword.equals("")) {
            response.sendRedirect("/noData.jsp");
            return;
        }
        Admins admins = new Admins(name, surname, email, password);
        AdminsDao adminsDao = new AdminsDao();
        List<Admins> adminsList = adminsDao.findAll();
        for (Admins adminsTmp : adminsList) {
            if (adminsTmp.getEmail().equals(admins.getEmail())) {
                request.setAttribute("admins", admins);
                getServletContext().getRequestDispatcher("/repeatedEmail.jsp").forward(request,response);
                return;
            }
        }
        if (!password.equals(repassword)) {
            request.setAttribute("admins", admins);
            getServletContext().getRequestDispatcher("/wrongRepassword.jsp").forward(request,response);
            return;
        }
        adminsDao.create(admins);
        response.sendRedirect("/login");    // mnie przekierowuje na stronę główną czyli localhost:8080/home.jsp, w sumie po rejestracji warto by stworzyć stronę z info "Użytkownik zarejestrowany pomyślnie" i link do logowania ?
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/register.jsp");
    }
}
