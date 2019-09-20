package com.mahhaus.zeronota.api.nfce;

import com.mahhaus.zeronota.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
@Service
public class NFCeService {

    @Autowired
    private NFCeRepository nfCeRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    List<NotaFiscalDTO> getNFCes(Pageable pageable) {
        return nfCeRepository.findAll(pageable).stream().map(NotaFiscalDTO::create).collect(Collectors.toList());
    }

    private NotaFiscalDTO insert(NotaFiscal notaFiscal) {
        Assert.isTrue(notaFiscal!=null,"Não foi possível inserir o registro");
        Assert.isTrue(!notaFiscal.getProdutos().isEmpty(),"Nota fiscal sem produtos");

        produtoRepository.saveAll(notaFiscal.getProdutos());
        return NotaFiscalDTO.create(nfCeRepository.save(notaFiscal));

    }

    NotaFiscalDTO getNFCeByUrl(String url) {
        Assert.isTrue(!url.isEmpty(),"Nota fiscal não encontrada!");

        try {
            return getNFCeByChave(NFCe.getChaveByUrl(url));
        } catch (ObjectNotFoundException e) {
            NotaFiscal notaFiscal = NFCe.getNfce(url);

            if (notaFiscal != null) {
                insert(notaFiscal);

                Optional<NotaFiscal> notadb = nfCeRepository.findByChave(notaFiscal.getChave());
                return notadb.map(NotaFiscalDTO::create).orElseThrow(() -> new ObjectNotFoundException("Nota fiscal não encontrada"));
            } else {
                throw new ObjectNotFoundException("Nota fiscal não encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    NotaFiscalDTO getNFCeByChave(String chave) {
        Optional<NotaFiscal> notaFiscal = nfCeRepository.findByChave(chave);
        return notaFiscal.map(NotaFiscalDTO::create).orElseThrow(() -> new ObjectNotFoundException("Nota fiscal não encontrada"));
    }
}