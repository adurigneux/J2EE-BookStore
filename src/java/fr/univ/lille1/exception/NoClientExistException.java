package fr.univ.lille1.exception;

/**
 * Exeption utilisée afin de verifier si un client existe ou pas
 * 
 * @author Clement Dupont & Antoine Durigneux
 * @version 1.0
 */
public class NoClientExistException extends Exception {

    public NoClientExistException() {
        super();
    }

    public NoClientExistException(String message) {
        super(message);
    }

}
