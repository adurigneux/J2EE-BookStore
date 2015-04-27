package fr.univ.lille1.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Cette classe représente l'entité Client en base de données, ainsi que tous
 * les champs associés
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Table(name = "CLIENT")
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "allusers", query = "SELECT u FROM Client u"),
    @NamedQuery(name = "findByUsername", query = "SELECT u FROM Client u WHERE LOWER(u.username) = LOWER(:username)"),})
public class Client implements Serializable {

    @Id
    private String username;

    private String password;

    private boolean admin = false;

    @OneToMany(mappedBy = "client")
    private Collection<Command> commands;

    /**
     * Constructeur par défaut
     */
    public Client() {
        commands = new ArrayList<Command>();
    }

    /**
     * Constructeur
     *
     * @param username String le nom d'utilisateur
     * @param password String le mot de passe de l'utilisateur
     */
    public Client(String username, String password) {
        commands = new ArrayList<Command>();
        this.username = username;
        this.password = password;
    }

    /**
     * Cette méthode retourne le nom d'utilisateur du client courant
     *
     * @return String le nom d'utilisateur
     */
    public String getUsername() {
        return username;
    }

    /**
     * Cette méthode change le nom d'utilisateur du client courant
     *
     * @param username String le nouveau nom d'utilisateur
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Cette méthode retourne le mot de passe du client courant
     *
     * @return String le mot de passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Cette méthode change le mot de passe du client courant
     *
     * @param password String le nouveau mot de passe
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Cette méthode détermine si le client courant est administrateur
     *
     * @return boolean true si le client est administrateur, false sinon
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Cette méthode change le statut du client courant
     *
     * @param admin boolean true si le client doit être administrateur, false
     * sinon
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Cette méthode retourne la liste des commandes associées au client courant
     *
     * @return Collection<Command> la liste des commandes
     */
    public Collection<Command> getCommands() {
        return commands;
    }

    /**
     * Cette méthode change la liste des commandes associées au client courant
     *
     * @param commands Collection<Command> la nouvelle liste des commandes
     */
    public void setCommands(Collection<Command> commands) {
        this.commands = commands;
    }

}
