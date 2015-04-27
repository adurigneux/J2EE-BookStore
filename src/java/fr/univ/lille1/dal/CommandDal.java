package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Book;
import fr.univ.lille1.entities.Client;
import fr.univ.lille1.entities.Command;
import java.util.List;
import javax.ejb.Remote;

/**
 * Cette interface décrit les interactions possibles avec la base de données et
 * la table des livres
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Remote
public interface CommandDal {

    /**
     * Cette méthode permet d'ajouter un livre au panier du client courant
     *
     * @param titre String le titre du livre à ajouter
     * @throws Exception
     */
    void addToCart(String titre) throws Exception;

    /**
     * Cette méthode permet de retirer un livre du panier du client courant
     *
     * @param titre String le titre du livre à retirer
     * @throws Exception
     */
    void removeFromCart(String titre) throws Exception;

    /**
     * Cette méthode permet de récupérer la liste des commandes effectuées par
     * le client courant
     * 
     * @return
     * @throws Exception 
     */
    List<Command> getCommands() throws Exception;

    /**
     * Cette méthode permet de commander les livres présents dans le panier du
     * client courant
     *
     * @param client Client le client courant
     * @throws Exception
     */
    void saveCommand(Client client) throws Exception;

    /**
     * Cette méthode permet d'obtenir le nombre de livres présents dans le
     * panier du client courant
     *
     * @return int le nombre de livres
     * @throws Exception 
     */
    int getNbBooks() throws Exception;

    /**
     * Cette méthode permet de récupérer la liste des livres présents dans le
     * panier du client courant
     *
     * @return List<Book> la liste des livres
     * @throws Exception 
     */
    List<Book> getCart() throws Exception;

    /**
     * Cette méthode permet de nettoyer le panier à la fin de la session du
     * client courant
     * @throws Exception 
     */
    void endSession() throws Exception;
}
