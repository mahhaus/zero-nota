package com.mahhaus.zeronota.api.carro;

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
public class CarroService {

    @Autowired
    private CarroRepository repository;

    public List<CarroDTO> getCarros(Pageable pageable) {
        List<CarroDTO> list = repository.findAll(pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
        return list;
    }

    public CarroDTO getCarroById(Long id) {
        Optional<Carro> carro = repository.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarrosByTipo(String tipo, Pageable pageable) {
        return repository.findByTipo(tipo, pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(),"Não foi possível inserir o registro");

        return CarroDTO.create(repository.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        // Busca o carro no banco de dados
        Optional<Carro> optional = repository.findById(id);
        if (optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id " + db.getId());

            // Atualiza o carro
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
