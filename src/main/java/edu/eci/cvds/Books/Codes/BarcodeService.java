package edu.eci.cvds.Books.Codes;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import edu.eci.cvds.Books.Codes.GenerateCodeException;
import edu.eci.cvds.Books.Codes.CodeGenerator;
import org.springframework.stereotype.Service;

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
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
        } catch (IOException e) {
            throw new GenerateCodeException(GenerateCodeException.CODE_ENCODING_ERROR);
        } catch (Exception e) {
            throw new GenerateCodeException(GenerateCodeException.CODE_INTERNAL_SERVER_ERROR);
        }
    }
}

