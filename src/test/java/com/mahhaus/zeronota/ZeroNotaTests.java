package com.mahhaus.zeronota;

import com.mahhaus.zeronota.domain.entity.Carro;
import com.mahhaus.zeronota.domain.CarroService;
import com.mahhaus.zeronota.domain.dto.CarroDTO;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZeroNotaTests {

    @Autowired
    private CarroService service;

    @Test
    public void testSave() {
        Carro carro = new Carro();
        carro.setNome("Carro teste");
        carro.setTipo("Teste tipo");

        CarroDTO c = service.insert(carro);
        Long id = c.getId();

        assertNotNull(c);
        assertNotNull(id);

        c = service.getCarroById(id);
        assertNotNull(c);

        // valida as informações
        assertEquals("Carro teste", c.getNome());
        assertEquals("Teste tipo", c.getTipo());

        //deleta o objeto
        service.delete(id);

        // Verifcar se deletou
        try {
            assertNull(service.getCarroById(id));
            fail();
        } catch (ObjectNotFoundException e) {
            // ok
        }
    }

    @Test
    public void testList() {
        List<CarroDTO> carroDTOS = service.getCarros();

        assertTrue(carroDTOS.size() == 30);
    }

    @Test
    public void testGet() {
        CarroDTO carro = service.getCarroById(11L);

        assertNotNull(carro);
    }

}
