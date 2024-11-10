package edu.eci.cvds.Books.Exception;

public class SubcategoryException extends RuntimeException {
    public static final String notNull = "Subcategory object cannot be null";
    public static final String dataNotNull = "Arguments cannot be null";
    public static final String notFound = "Subcategory not found";
    public static final String badFormat = "The format is incorrect";
    public static final String badState = "The state of the Subcategory is not valid";
    public static final String badDispo = "The disponibility of the Subcategory is not valid";
    public static final String badSubcategory = "Cannot create Subcategory, incomplete data";
    public static final String badCategory = "Cannot find category";
    public static final String badValues = "The values of the Subcategory are not valid";
    //public static final String noSubcategorysForBook = "This book does not have any Subcategory";


    public SubcategoryException(String message) {
        super(message);
    }
}
