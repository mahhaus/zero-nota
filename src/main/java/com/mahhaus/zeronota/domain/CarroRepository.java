package com.mahhaus.zeronota.domain;

import com.mahhaus.zeronota.domain.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByTipo(String tipo);
}
