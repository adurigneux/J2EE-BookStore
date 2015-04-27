package fr.univ.lille1.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * Cette classe représente l'entité Command en base de données, ainsi que tous
 * les champs associés
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Table(name = "COMMAND")
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "allCommands", query = "SELECT c FROM Command c"),})
public class Command implements Serializable {

    @Id
    @GeneratedValue
    private String id;

    @ManyToMany
    private Collection<Book> books;

    @ManyToOne
    private Client client;

    /**
     * Constructeur par défaut
     */
    public Command() {
        books = new ArrayList<Book>();
    }

    /**
     * Constructeur
     *
     * @param books Collection<Book> la liste des livres associés à la commande
     */
    public Command(Collection<Book> books) {
        this.books = books;
    }

    /**
     * Cette méthode retourne la liste des livres associés à la commande
     * courante
     *
     * @return Collection<Book> la liste des livres
     */
    public Collection<Book> getBooks() {
        return books;
    }

    /**
     * Cette méthode permet de changer la liste des livres de la commande
     * courante
     *
     * @param books Collection<Book> la nouvelle liste de livres
     */
    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    /**
     * Cette méthode retourne l'identifiant de la commande courante
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Cette méthode retourne le client de la commande courante
     *
     * @return Client le client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Cette méthode change le client de la commande courante
     *
     * @param client Client le nouveau client
     */
    public void setClient(Client client) {
        this.client = client;
    }

}
