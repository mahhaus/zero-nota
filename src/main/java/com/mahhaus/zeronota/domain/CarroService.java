package com.mahhaus.zeronota.domain;

import com.mahhaus.zeronota.domain.dto.CarroDTO;
import com.mahhaus.zeronota.domain.entity.Carro;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CarroService {
    private final CarroRepository repository;

    @Autowired
    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public List<CarroDTO> getCarros() {
        return repository.findAll()
                .stream()
                .map(CarroDTO::create)
                .collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        return repository
                .findById(id)
                .map(CarroDTO::create)
                .orElseThrow(() -> new ObjectNotFoundException(null,"Carro não encontrado"));
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return repository.findByTipo(tipo)
                .stream()
                .map(CarroDTO::create)
                .collect(Collectors.toList());
    }

    public Carro save(Carro carro) {
        return repository.save(carro);
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possivel inserir");

        return CarroDTO.create(repository.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro.");

        Optional<Carro> optional = repository.findById(id);

        if (optional.isPresent()) {
            Carro db = optional.get();

            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            repository.save(db);

            return CarroDTO.create(db);
        } else {
            return null;
//            throw new RuntimeException("Não foi possivel atualizar o registro");
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
