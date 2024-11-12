package edu.eci.cvds.Books.Exception;

public class CategoryException extends RuntimeException {
  public static final String notNull = "Category object cannot be null";
  public static final String dataNotNull = "Arguments cannot be null";
  public static final String notFound = "Category not found";
  public static final String badFormat = "The format is incorrect";
  public static final String badCategory = "Cannot create Category, incomplete data";
  public static final String badValues = "The values of the Category are not valid";
  public CategoryException(String message) {
    super(message);
  }
}
