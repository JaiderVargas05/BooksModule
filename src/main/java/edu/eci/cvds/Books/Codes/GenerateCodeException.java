package edu.eci.cvds.Books.Codes;

public class GenerateCodeException extends Exception{


    public static final String CODE_GENERATION_FAILED = "An error occurred while generating the code. Please try again.";
    public static final String CODE_INTERNAL_SERVER_ERROR = "An internal server error occurred during code generation.";
    public static final String CODE_ENCODING_ERROR = "An error occurred while encoding the generated code.";


    public GenerateCodeException(String message) {
        super(message);
    }
}

