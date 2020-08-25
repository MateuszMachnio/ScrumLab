package pl.coderslab.web;

import pl.coderslab.dao.otherInformationsDao;
import pl.coderslab.model.otherInformations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "About",value = "/about")
public class About extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        otherInformationsDao otherInformationsDao = new otherInformationsDao();
        request.setAttribute("dataInfo",otherInformationsDao.read(1));
        getServletContext().getRequestDispatcher("/about.jsp").forward(request,response);
    }
}
