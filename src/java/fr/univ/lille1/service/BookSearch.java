package fr.univ.lille1.service;

import fr.univ.lille1.dal.BookDal;
import fr.univ.lille1.entities.Book;

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
 * Cette servlet permet de chercher un livre en base de données grâce à son
 * titre
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@WebServlet(name = "BookSearch", urlPatterns = {"/search"})
public class BookSearch extends HttpServlet {

    @EJB
    private BookDal bookSession;

    /**
     * Cette méthode permet d'afficher le formulaire de recherche du livre
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/search.jsp");
        rd.forward(request, response);
    }

    /**
     * Cette méthode permet de traiter les données envoyées par l'utilisateur
     * pour la recherche du livre
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");

        try {
            List<Book> books = bookSession.findBooks(title);
            request.setAttribute("books", books);
        } catch (Exception e) {
            String message = null;
            message = "Une erreur est survenue lors de la récupération des livres.";
            request.setAttribute("message", message);
        }

        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/search.jsp");
        rd.forward(request, response);

    }

}
