package com.mahhaus.zeronota.api.carro;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
public interface CarroRepository extends JpaRepository<Carro, Long> {

    List<Carro> findByTipo(String tipo, Pageable pageable);
}
