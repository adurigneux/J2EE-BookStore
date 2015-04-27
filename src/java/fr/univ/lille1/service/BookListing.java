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
 * Cette servlet permet de récupérer la liste des livres du site et de
 * l'afficher
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@WebServlet(name = "BookListing", urlPatterns = {"/booklist"})
public class BookListing extends HttpServlet {

    @EJB
    private BookDal bookSession;

    /**
     * Cette méthode permet d'afficher la liste des livres du site
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<Book> books = bookSession.getBooks();
            request.setAttribute("books", books);
        } catch (Exception e) {
            request.setAttribute("error", true);
        }

        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/books.jsp");
        rd.forward(request, response);
    }

}
