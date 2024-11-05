package edu.eci.cvds.Books.Exception;

public class HttpException  extends Exception{
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


    public HttpException (String message) {
        super(message);
    }

}
