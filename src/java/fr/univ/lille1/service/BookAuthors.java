package fr.univ.lille1.service;

import fr.univ.lille1.dal.BookDal;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 *
 * Cette servlet permet d'afficher la liste des auteurs des livres présents sur
 * le site
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@WebServlet(name = "BookAuthors", urlPatterns = {"/authors"})
public class BookAuthors extends HttpServlet {

    @EJB
    private BookDal bookSession;

    /**
     * Cette méthode affiche la liste des auteurs des livres
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<String> authors = bookSession.getAuthors();
            request.setAttribute("authors", authors);
        } catch (Exception e) {
            request.setAttribute("error", true);
        }

        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/authors.jsp");
        rd.forward(request, response);
    }

}
