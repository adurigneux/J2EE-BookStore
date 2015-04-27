package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Client;
import fr.univ.lille1.exception.NoClientExistException;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Cette classe est l'implémentation concrète de l'interface CommandDal
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Remote(ClientDal.class)
@Stateful
public class ClientDalImpl implements ClientDal {

    @PersistenceContext(unitName = "BookStorePU")
    private EntityManager em;

    private Client currentClient;

    @Override
    public boolean init() throws Exception {
        try {
            //admin
            addClient("clement", "bonjour");
            setAdmin("clement");

            //user
            addClient("antoine", "bonjour");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Client> getClients() throws Exception {
        Query q = em.createNamedQuery("allusers");
        List<Client> users = (List<Client>) q.getResultList();
        em.flush();
        return users;
    }

    @Override
    public Client getClientByUsername(String username) throws Exception {
        Query q = em.createNamedQuery("findByUsername");
        q.setParameter("username", username);
        try {
            Client client = (Client) q.getSingleResult();
            em.flush();
            return client;
        } catch (NoResultException e) {
            throw new NoClientExistException();
        }
    }

    @Override
    public Client addClient(String username, String password) throws Exception {
        Client user = null;
        try {
            user = getClientByUsername(username);
            throw new Exception();
        } catch (NoClientExistException e) {
            // S'il n'existe pas d'utilisateur alors on l'ajoute
            user = new Client(username, password);
            em.persist(user);
            em.flush();
            return user;
        }

    }

    @Override
    public boolean setAdmin(String username) throws Exception {
        try {
            Client user = getClientByUsername(username);
            user.setAdmin(true);
            em.persist(user);
            em.flush();
            return true;
        } catch (NoClientExistException e) {
            return false;
        }

    }

    @Override
    public Client login(String username, String password) throws Exception {

        Client client = getClientByUsername(username);
        if (client.getPassword().compareTo(password) == 0) {
            currentClient = client;
            return client;
        }

        throw new Exception();
    }
@Override
    public Client getCurrentClient() {
        return currentClient;
    }
@Override
    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

}
