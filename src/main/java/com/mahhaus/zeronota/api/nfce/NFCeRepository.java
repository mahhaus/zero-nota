package com.mahhaus.zeronota.api.nfce;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
public interface NFCeRepository extends JpaRepository<NotaFiscal, Long> {
    Optional<NotaFiscal> findByChave(String chave);
}
