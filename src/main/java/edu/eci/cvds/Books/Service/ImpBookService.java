package edu.eci.cvds.Books.Service;

import edu.eci.cvds.Books.Domain.Book;
import edu.eci.cvds.Books.Repository.BRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("Imp")
public class ImpBookService implements BookService{
    private final BRepository bookRepository;
    @Autowired
    public ImpBookService(@Qualifier("BookRepo") BRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    @Override
    public void saveBook(Book book){
        book.setISBN(generateISBN());
        this.bookRepository.BSave(book);
    }

    private String generateISBN() {
        Random random = new Random();
        StringBuilder isbn = new StringBuilder("978"); // Prefijo para ISBN-13
        for (int i = 0; i < 9; i++) {
            isbn.append(random.nextInt(10)); // Agrega 9 dígitos aleatorios
        }
        isbn.append(calculateISBNCheckDigit(isbn.toString())); // Agrega el dígito de control
        return isbn.toString();
    }

    private int calculateISBNCheckDigit(String isbn) {
        int sum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3; // Alternar multiplicación
        }
        int checkDigit = 10 - (sum % 10);
        return (checkDigit == 10) ? 0 : checkDigit;
    }
}
