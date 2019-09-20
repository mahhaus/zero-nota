package com.mahhaus.zeronota.api.nfce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

/**
 * @author josias.soares
 * Create 01/09/2019
 */
@RestController
@RequestMapping("/api/v1/nfce")
public class NFCeController {

    @Autowired
    private NFCeService service;

    @GetMapping()
    public ResponseEntity get(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        List<NotaFiscalDTO> notas = service.getNFCes(PageRequest.of(page, size));
        return ResponseEntity.ok(notas);
    }


    @PostMapping("/save")
    public ResponseEntity save(@RequestBody SaveRequest saveRequest) {

        //"https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx?p=43190832066551000144650010000080441240438959|2|1|1|583858703FD802777CC18252F3EF655A77B7E393";

//        String decodedUrl = "";
//        String encodedUrl = saveRequest.getEncodedUrl();
//        if (!encodedUrl.isEmpty()) {
////            byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl);
////            decodedUrl = new String(decodedBytes);
////        }
        NotaFiscalDTO notaFiscalDTO = service.getNFCeByUrl(saveRequest.getEncodedUrl());

        if (notaFiscalDTO == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(notaFiscalDTO);
        }
    }

    @GetMapping("/{chave}")
    public ResponseEntity getByChave(@PathVariable(value = "chave") String chave) {
        NotaFiscalDTO nfce = service.getNFCeByChave(chave);
        return ResponseEntity.ok(nfce);
    }
}