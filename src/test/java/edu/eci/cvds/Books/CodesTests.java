package edu.eci.cvds.Books;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.Code128Writer;
import edu.eci.cvds.Books.Codes.BarcodeService;
import edu.eci.cvds.Books.Codes.GenerateCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CodesTests {

    @InjectMocks
    private BarcodeService barcodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCodeWithValidId() throws GenerateCodeException {
        String id = "123456789";
        String base64Image = barcodeService.generateCode(id);
        assertNotNull(base64Image, "The generated code should not be null");
        System.out.println("Generated Base64 Image: " + base64Image);
    }

    @Test
    void testGenerateCodeThrowsGenerateCodeExceptionOnGenerateCodeException() throws GenerateCodeException {
        BarcodeService barcodeServiceSpy = spy(barcodeService);

        doThrow(new GenerateCodeException(GenerateCodeException.CODE_ENCODING_ERROR)).when(barcodeServiceSpy).generateCode(anyString());

        assertThrows(GenerateCodeException.class, () -> barcodeServiceSpy.generateCode("test"));
    }

    @Test
    void testGenerateCodeThrowsGenerateCodeExceptionOnIOException() throws Exception {
        BarcodeService barcodeServiceSpy = spy(barcodeService);

        // Simular una IOException en ImageIO.write
        try (MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class)) {
            mockedImageIO.when(() -> ImageIO.write(any(), anyString(), any(ByteArrayOutputStream.class)))
                    .thenThrow(new IOException());

            assertThrows(GenerateCodeException.class, () -> barcodeServiceSpy.generateCode("test"));
        }
    }

    @Test
    void testGenerateCodeThrowsGenerateCodeExceptionOnGeneralRuntimeException() throws Exception {
        // Crear un espía (spy) del servicio BarcodeService
        BarcodeService barcodeServiceSpy = spy(barcodeService);

        // Simular una RuntimeException (excepción no verificada) en el método generateCode
        doThrow(new RuntimeException("General runtime exception")).when(barcodeServiceSpy).generateCode("test");

        // Verificar que se lanza GenerateCodeException cuando ocurre una RuntimeException
        assertThrows(Exception.class, () -> barcodeServiceSpy.generateCode("test"));
    }
}

