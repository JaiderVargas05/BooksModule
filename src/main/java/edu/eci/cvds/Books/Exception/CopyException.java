package edu.eci.cvds.Books.Exception;

public class CopyException extends Exception{
    public static final String notNull = "Ejemplar object cannot be null";
    public static final String dataNotNull = "Arguments cannot be null";
    public static final String notFound = "Ejemplar not found";
    public static final String barCodeIncorrect = "The bar code is incorrect";
    public static final String badFormat = "The format is incorrect";
    public static final String badState = "The state of the Ejemplar is not valid";
    public static final String badDispo = "The disponibility of the Ejemplar is not valid";
    public static final String readCode = "Cannot read the code, try again";
    public static final String badEjemplar = "Cannot create ejemplar, incomplete data";
    public static final String badBook = "Cannot find book";
    public static final String badValues = "The values of the Ejemplar are not valid";
    public static final String noEjemplarsForBook = "This book does not have any Ejemplar";


    public CopyException(String message) {
        super(message);
    }

}
