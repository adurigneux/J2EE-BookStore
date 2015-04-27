package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Client;
import javax.ejb.Remote;
import java.util.List;

/**
 * Cette interface permet d'intéragir avec la base de données et la table des
 * clients
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Remote
public interface ClientDal {

    /**
     * Cette méthode permet d'initialiser la table des clients avec quelques
     * clients de base
     *
     * @return boolean true si l'initialisation s'est correctement déroulé,
     * false sinon
     * @throws Exception
     */
    boolean init() throws Exception;

    /**
     * Cette méthode permet de récupérer la liste des clients du site
     *
     * @return List<Client> la liste des clients
     * @throws Exception
     */
    List<Client> getClients() throws Exception;

    /**
     * Cette méthode permet de récupérer un client sur base de son nom
     * d'utilisateur
     *
     * @param username String le nom d'utilisateur recherché
     * @return Client le client s'il est trouvé
     * @throws Exception
     */
    Client getClientByUsername(String username) throws Exception;

    /**
     * Cette méthode permet d'ajouter un client dans la base de données
     *
     * @param username String le nom d'utilisateur du nouveau client
     * @param password String le mot de passe associé au nouveau compte
     * @return boolean true si l'ajout s'est correctement déroulé, false sinon
     * @throws Exception
     */
    Client addClient(String username, String password) throws Exception;

    /**
     * Cette méthode permet de définir un utilisateur en tant qu'administrateur
     * du site
     *
     * @param username String le nom d'utilisateur qui va être promu
     * @return boolean true si la modification s'est correctement déroulé, false
     * sinon
     * @throws Exception
     */
    boolean setAdmin(String username) throws Exception;

    /**
     * Cette methode permet de se connecter via un nom d'utilisateur et mot de
     * passe
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    Client login(String username, String password) throws Exception;

    public Client getCurrentClient() throws Exception;

    public void setCurrentClient(Client currentClient) throws Exception;
}
