package edu.eci.cvds.Books.Codes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class BarcodeService implements CodeGenerator {

    @Override
    public String generateCode(String id) throws GenerateCodeException {
        try {

            Code128Writer barcodeWriter = new Code128Writer();
            BitMatrix bitMatrix = barcodeWriter.encode(id, BarcodeFormat.CODE_128, 200, 100);

            // Convertir BitMatrix a BufferedImage
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Crear una imagen con espacio para el texto
            int width = barcodeImage.getWidth();
            int height = barcodeImage.getHeight() + 20;
            BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = combinedImage.createGraphics();

            // Combinar imagen del código de barras y texto
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.drawImage(barcodeImage, 0, 0, null);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString(id, 10, height - 5);
            g.dispose();

            // Convertir la imagen combinada a un string en base 64
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(combinedImage, "PNG", pngOutputStream);
            pngOutputStream.flush();
            String base64Image = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
            pngOutputStream.close(); // Cerrar el flujo
            System.out.println("Tamaño de la cadena Base64: " + base64Image.length());

            return base64Image;

        } catch (IOException e) {
            throw new GenerateCodeException(GenerateCodeException.CODE_ENCODING_ERROR);
        } catch (Exception e) {
            throw new GenerateCodeException(GenerateCodeException.CODE_INTERNAL_SERVER_ERROR);
        }
    }
}