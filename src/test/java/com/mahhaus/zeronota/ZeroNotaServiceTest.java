package com.mahhaus.zeronota;

import com.mahhaus.zeronota.api.carro.CarroService;
import com.mahhaus.zeronota.api.carro.CarroDTO;
import com.mahhaus.zeronota.api.carro.Carro;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZeroNotaServiceTest {

    @Autowired
    private CarroService service;

    @Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        CarroDTO c = service.insert(carro);

        assertNotNull(c);

        Long id = c.getId();
        assertNotNull(id);

        // Buscar o objeto
        c = service.getCarroById(id);
        assertNotNull(c);

        assertEquals("Porshe",c.getNome());
        assertEquals("esportivos",c.getTipo());

        // Deletar o objeto
        service.delete(id);

        // Verificar se deletou
        try {
            service.getCarroById(id);
            fail("O carro não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
    }

    @Test
    public void testLista() {

        List<CarroDTO> carros = service.getCarros(PageRequest.of(0,30));

        assertEquals(30, carros.size());
    }

    @Test
    public void testListaPorTipo() {

        assertEquals(10, service.getCarrosByTipo("classicos", PageRequest.of(0,10)).size());
        assertEquals(10, service.getCarrosByTipo("esportivos", PageRequest.of(0,10)).size());
        assertEquals(10, service.getCarrosByTipo("luxo", PageRequest.of(0,10)).size());

        assertEquals(0, service.getCarrosByTipo("x", PageRequest.of(0,10)).size());
    }

    @Test
    public void testGet() {

        CarroDTO c = service.getCarroById(11L);

        assertNotNull(c);


        assertEquals("Ferrari FF", c.getNome());
    }
}