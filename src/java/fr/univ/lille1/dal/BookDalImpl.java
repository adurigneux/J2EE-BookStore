package fr.univ.lille1.dal;

import fr.univ.lille1.exception.BookAlreadyExistException;
import fr.univ.lille1.entities.Book;
import java.util.ArrayList;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Cette classe est l'implémentation concrète de l'interface BookDal
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
@Remote(BookDal.class)
@Stateless
public class BookDalImpl implements BookDal {

    @PersistenceContext(unitName = "BookStorePU")
    private EntityManager em;

    @Override
    public boolean init() throws Exception {
        // If exist, do not add again
        try {
            if (em.find(Book.class, "Les gardiens de la galaxie T1") != null) {
                em.flush();
                return false;
            }
        } catch (Exception e) {
            throw new BookAlreadyExistException();
        }

        em.persist(new Book("Les gardiens de la galaxie T1", "M. Bendis", 2014));
        em.persist(new Book("Les gardiens de la galaxie T2", "M. Bendis", 2015));
        em.persist(new Book("Nova T1", "Loeb", 2015));
        em.persist(new Book("Avengers T1", "Hickman", 2015));
        em.persist(new Book("Superior Spiderman T1", "Slott", 2015));
        em.flush();
        return true;
    }

    @Override
    public List<String> getAuthors() throws Exception {
        Query q = em.createNamedQuery("allAuthors");
        List<String> authors = (List<String>) q.getResultList();
        return authors;
    }

    @Override
    public List<Book> getBooks() throws Exception {
        Query q = em.createNamedQuery("allBooks");
        return (List<Book>) q.getResultList();
    }

    @Override
    public void addBook(String title, String authorName, int year) throws Exception {
        Query q = em.createNamedQuery("findByTitle");
        q.setParameter("title", title);
        try {
            q.getSingleResult();
            throw new BookAlreadyExistException();
        } catch (NoResultException e) {
            // S'il n'existe pas de livre alors on l'ajoute
            em.persist(new Book(title, authorName, year));
            em.flush();
        }

    }

    @Override
    public Book findBook(String title) throws Exception {
        Query q = em.createNamedQuery("findByTitle");
        q.setParameter("title", title);
        try {
            Book b = (Book) q.getSingleResult();
            em.flush();
            return b;
        } catch (NoResultException e) {
            // S'il n'existe pas de livre alors on retourne un null
            return null;
        }

    }

    public List<Book> findBooks(String titlePart) throws Exception {
        List<Book> books = new ArrayList<>();
        Query q = em.createNamedQuery("findByTitlePart");
        q.setParameter("title", "%" + titlePart + "%");
        try {
            books.addAll((List<Book>) q.getResultList());
            em.flush();
            books.size();
            return books;
        } catch (NoResultException e) {
            // S'il n'existe pas de livre
            return books;
        }
    }

}
