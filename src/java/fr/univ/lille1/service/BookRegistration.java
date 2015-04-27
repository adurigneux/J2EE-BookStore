package fr.univ.lille1.service;

import fr.univ.lille1.dal.BookDal;
import fr.univ.lille1.exception.BookAlreadyExistException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * Cette servlet permet d'enregistrer un nouveau livre en base de données
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@WebServlet(name = "BookRegistration", urlPatterns = {"/registration"})
public class BookRegistration extends HttpServlet {

    private static final long serialVersionUID = 3128205959131150559L;

    @EJB
    private BookDal bookSession;

    /**
     * Cette méthode permet d'afficher le formulaire d'enregistrement du livre
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/registration.jsp");
        rd.forward(request, response);
    }

    /**
     * Cette méthode permet de traiter les données envoyées par l'utilisateur
     * pour l'ajout du livre
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String year = request.getParameter("year");
        String info = "Le livre à bien été ajouté";

        try {
            bookSession.addBook(title, author, Integer.parseInt(year));
        } catch (BookAlreadyExistException bookE) {
            info = "Le livre existe déjà";
        } catch (Exception e) {
            info = "Une erreur est survenue lors de l'ajout du livre.";
        }

        request.setAttribute("bookinfo", info);
        request.setAttribute("resume", true);

        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/registration.jsp");
        rd.forward(request, response);

    }

}
