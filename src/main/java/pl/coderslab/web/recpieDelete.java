package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "recpieDelete", value = "/app/recipe/delete")
public class recpieDelete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ses = request.getSession();
        String id  = (String)ses.getAttribute("recipeId");
        RecipeDao recipeDao = new RecipeDao();
        recipeDao.delete(Integer.parseInt(id));
        ses.removeAttribute("recipeId");
        response.sendRedirect("/app/recipe/list");
    }
}
