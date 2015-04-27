package fr.univ.lille1.service;

import fr.univ.lille1.dal.ClientDal;
import fr.univ.lille1.dal.CommandDal;
import fr.univ.lille1.entities.Client;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cette servlet permet de gérer les différentes actions liées au panier et aux
 * commandes de l'utilisateur
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@WebServlet(name = "BookShopping", urlPatterns = {"/cart"})
public class BookShopping extends HttpServlet {

    @EJB
    private CommandDal cmdSession;

    @EJB
    private ClientDal clientsession;

    /**
     * Cette méthode permet de gérer le panier et les commandes de livres de
     * l'utilisateur
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String infocmd = null;
        String returnView = null;

        switch (request.getParameter("action")) {
            case "add":
                try {
                    cmdSession.addToCart(request.getParameter("title"));
                    returnView = "/booklist";
                } catch (Exception e) {
                    infocmd = "une erreur est survenue lors de l'ajout du livre";
                }
                break;
            case "del":
                try {
                    cmdSession.removeFromCart(request.getParameter("title"));
                    request.setAttribute("books", cmdSession.getCart());
                    returnView = "/cart.jsp";
                } catch (Exception e) {
                    infocmd = "une erreur est survenue lors de la suppression du livre";
                }
                break;
            case "cmd":
                try {
                    cmdSession.saveCommand(clientsession.getCurrentClient());
                    request.setAttribute("commands", cmdSession.getCommands());
                    returnView = "/cart.jsp";
                    infocmd = "Cart validated !";
                } catch (Exception e) {
                    infocmd = "une erreur est survenue lors de l'ajout de votre commande ";
                }
                break;
            case "list":
                try {
                    request.setAttribute("commands", cmdSession.getCommands());
                    returnView = "/commands.jsp";
                } catch (Exception e) {
                    infocmd = "une erreur est survenue lors de la récupération des commandes.";
                }
                break;
            case "view":
                try {
                    request.setAttribute("books", cmdSession.getCart());
                    returnView = "/cart.jsp";
                } catch (Exception e) {
                    infocmd = "une erreur est survenue lors de la récupération du panier.";
                }
                break;
        }

        request.setAttribute("message", infocmd);
        this.getServletContext().getRequestDispatcher(returnView).forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
