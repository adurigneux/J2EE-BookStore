package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Book;

import javax.ejb.Remote;
import java.util.List;

/**
 * Cette interface décrit les interactions possibles avec la base de données et
 * la table des livres
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Remote
public interface BookDal {

    /**
     * Cette méthode permet d'initialiser la base de données des livres avec
     * quelques exemplaires pré-remplis
     *
     * @return boolean true si l'initialisation s'est correctement déroulée;
     * false sinon
     * @throws Exception
     */
    boolean init() throws Exception;

    /**
     * Cette méthode retourne la liste des auteurs dont au moins un livre est
     * présent sur le site
     *
     * @return List<String> la liste des noms des auteurs présents
     * @throws Exception
     */
    List<String> getAuthors() throws Exception;

    /**
     * Cette méthode retourne la liste des livres présents sur le site
     *
     * @return List<Book> la liste des livres présents
     * @throws Exception
     */
    List<Book> getBooks() throws Exception;

    /**
     * Cette méthode permet de rajouter un livre sur le site
     *
     * @param title String le titre du livre
     * @param author String le nom de l'auteur
     * @param year int l'année de publication
     * @throws Exception
     */
    void addBook(String title, String author, int year) throws Exception;

    /**
     * Cette méthode permet de rechercher un livre dans la base de données grâce
     * à son titre
     *
     * @param title String le titre du livre recherché
     * @throws Exception
     * @return Book le livre s'il est trouvé en base de données
     */
    Book findBook(String title) throws Exception;

    /**
     * Cette méthode permet de rechercher un livre dans la base de données grâce
     * à son titre ou une partie de son titre
     *
     * @param titlePart String la partie du titre
     * @throws Exception
     * @return List<Book> la liste de livre correspondante
     */
    List<Book> findBooks(String titlePart) throws Exception;
}
