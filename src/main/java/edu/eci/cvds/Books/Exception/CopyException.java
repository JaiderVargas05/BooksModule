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

    // Informational Responses (100-199)
    public static final String CONTINUE = "The request has been received and the process is continuing.";
    public static final String SWITCHING_PROTOCOLS = "The server is switching to a different protocol.";

    // Successful Responses (200-299)
    public static final String OK = "The request has succeeded.";
    public static final String CREATED = "The request has been fulfilled and a new resource has been created.";
    public static final String ACCEPTED = "The request has been accepted for processing, but it has not yet been completed.";

    // Redirection Messages (300-399)
    public static final String MULTIPLE_CHOICES = "There are multiple options for the requested resource.";
    public static final String MOVED_PERMANENTLY = "The requested resource has been permanently moved to a new URI.";
    public static final String FOUND = "The requested resource is temporarily located at a different URI.";

    // Client Error Responses (400-499)
    public static final String BAD_REQUEST = "The request could not be understood or was malformed.";
    public static final String UNAUTHORIZED = "Authentication is required to access the requested resource.";
    public static final String FORBIDDEN = "The server understands the request, but refuses to authorize it.";
    public static final String NOT_FOUND = "The requested resource could not be found.";
    public static final String METHOD_NOT_ALLOWED = "The request method is not allowed for the requested resource.";

    // Server Error Responses (500-599)
    public static final String INTERNAL_SERVER_ERROR = "An internal server error has occurred.";
    public static final String NOT_IMPLEMENTED = "The server does not recognize the request method or cannot implement it.";
    public static final String BAD_GATEWAY = "The server, while acting as a gateway or proxy, received an invalid response.";
    public static final String SERVICE_UNAVAILABLE = "The server is temporarily unable to handle the request.";


    public CopyException(String message) {
        super(message);
    }

}
