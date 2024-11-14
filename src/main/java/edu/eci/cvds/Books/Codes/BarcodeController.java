package edu.eci.cvds.Books.Codes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/barcode")
public class BarcodeController {

    private final CodeGenerator codeGenerator;

    @Autowired
    public BarcodeController(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @GetMapping("/{id}")
    public String generateBarcode(@PathVariable String id) throws GenerateCodeException {
        return codeGenerator.generateCode(id);
    }

}

