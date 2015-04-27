package fr.univ.lille1.dal;

import fr.univ.lille1.entities.Book;
import fr.univ.lille1.entities.Client;
import fr.univ.lille1.entities.Command;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Cette classe est l'implémentation concrète de l'interface CommandDal
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Remote(CommandDal.class)
@Stateful
public class CommandDalImpl implements CommandDal {

    @PersistenceContext(unitName = "BookStorePU")
    private EntityManager em;

    private List<Book> cart;
    private boolean displayContent = false;

    public CommandDalImpl() {
        cart = new ArrayList<Book>();
    }

    public boolean isDisplayContent() {
        return displayContent;
    }

    public List<Command> getCommands() {
        Query q = em.createNamedQuery("allCommands");
        List<Command> l = (List<Command>) q.getResultList();
        em.flush();
        l.size();
        for (Command cmd : l) {
            cmd.getBooks().size(); // Load
            // << LAZY instantiation
        }
        return l;
    }

    public void addToCart(String title) throws Exception {
        Query q = em.createNamedQuery("findByTitle");
        q.setParameter("title", title);

        try {
            Book book = (Book) q.getSingleResult();
            em.flush();
            cart.add(book);
        } catch (NoResultException e) {
            return;
        }
    }

    public void removeFromCart(String titre) throws Exception {
        Iterator<Book> iter = cart.iterator();
        while (iter.hasNext()) {
            if (iter.next().getTitle().equals(titre)) {
                iter.remove();
                break;
            }
        }
    }

    public void saveCommand(Client client) throws Exception {
        Command cmd = new Command(cart);
        cmd.setClient(client);
        em.persist(cmd);
        em.flush();
        cart.clear();
    }

    public int getNbBooks() {
        return cart.size();
    }

    public List<Book> getCart() {
        return cart;
    }

    @Remove
    public void endSession() {
        cart.clear();
        em.close();
    }
}
