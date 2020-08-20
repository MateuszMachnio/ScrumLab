package pl.coderslab.web;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.AdminsDao;
import pl.coderslab.model.Admins;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AdminsDao adminsDao = new AdminsDao();
        //pobieramy inputy
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //jesli puste odsyłamy do logowania
        if (email.equals("") || password.equals("")) {
            response.sendRedirect("/noDataLogin.jsp");
            return;
        }
        //sprawdzamy czy uzytkownik jest w bazie
        try {
            Admins user = adminsDao.readByEmail(email);
            //sprawdzamy czy haslo uzytkownika sie zgadza
            boolean checkPass = BCrypt.checkpw(password, user.getPassword());
            //jeśli hasło i enable sie zgadza otwieramy sesje
            if (checkPass && user.getEnable() == 1) {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(1200);
                session.setAttribute("logged", user.getId());
                System.out.println("tworzenie sesji");
                response.sendRedirect("/app");
                //jeśli nieaktywny odsyła do strony logowania z komunikatem ze nieaktywny
            } else if (checkPass && user.getEnable() != 1) {
                response.sendRedirect("/notActiveUser.jsp");
            } else {
                //jeśli hasło się nie zgadza odsyłamy do logowania z wypisanym juz emailem
                request.setAttribute("email", email);
                getServletContext().getRequestDispatcher("/wrongPassword.jsp").forward(request, response);
            }
            //jeśli uzytkownika nie ma w bazie odsyłamy do logowania
        } catch (NullPointerException e) {
            response.sendRedirect("/wrongLogin.jsp");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = request.getParameter("msg");
        if (msg != null) {
            request.setAttribute("msg", msg);
        }
        getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
    }
}
