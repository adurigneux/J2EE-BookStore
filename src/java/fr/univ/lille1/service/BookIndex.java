package fr.univ.lille1.service;

import fr.univ.lille1.dal.ClientDal;
import fr.univ.lille1.entities.Client;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 *
 * Cette servlet permet d'afficher la page d'accueil principale du site (index)
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@WebServlet(name = "BookIndex", urlPatterns = {"/"})
public class BookIndex extends HttpServlet {

    @EJB
    private ClientDal clientSession;

    /**
     * Cette méthode permet d'afficher la page d'accueil du site
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    /**
     * Cette méthode permet de se connecter/inscrire au site
     *
     * @param request Request la requête HTTP
     * @param response Response la réponse HTTP
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String error = "";
        String param = request.getParameter("formName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        switch (param) {
            case "login": {
                try {
                    clientSession.login(username, password);
                    request.getSession(false).setAttribute("client", clientSession.getCurrentClient());

                } catch (Exception ignore) {
                    error = "Erreur de pseudo/mot de passe. Utilisateur inconnu.";
                }
            }
            break;
            case "logout": {
                request.getSession().invalidate();
            }

            break;

            case "register": {
                try {
                    clientSession.addClient(username, password);
                    request.getSession(true).setAttribute("client",clientSession.getCurrentClient());

                } catch (Exception ignore) {
                    error = "Erreur lors de l'inscription. Utilisateur existant.";
                }
            }

            break;

            default:
                error = "Il y a eu une erreur lors du remplissage des formulaires.";
                break;
        }
        request.setAttribute("error", error);
        ServletContext context = request.getServletContext();
        RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

}
