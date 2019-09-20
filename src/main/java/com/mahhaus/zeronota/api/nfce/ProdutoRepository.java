package com.mahhaus.zeronota.api.nfce;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
