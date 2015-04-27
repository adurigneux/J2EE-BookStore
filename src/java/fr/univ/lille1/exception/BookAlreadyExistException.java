package fr.univ.lille1.exception;

/**
 * Exeception utilisée quand un livre existe deja dans la base de données
 *
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
public class BookAlreadyExistException extends Exception {

    public BookAlreadyExistException() {
        super();
    }

    public BookAlreadyExistException(String message) {
        super(message);
    }

}
