package br.com.pismotest.test.controller;

import br.com.pismotest.products.Application;
import br.com.pismotest.products.domain.Product;
import br.com.pismotest.products.repositories.ProductRepository;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Teste integrado do controller
 * 
 * @author José San Pedro
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, 
                classes = Application.class)
@AutoConfigureMockMvc
public class ProductControllerIntTest {
    
    @Autowired
    private MockMvc mvc;
 
    @Autowired
    private ProductRepository repository;
    
    @Test
    public void givenProducts_whenGetProducts_thenReturnJsonArray() throws Exception {
        Product tomate = new Product();
        tomate.setName("tomate");
        tomate.setStock(3);
        repository.save(tomate);

        mvc.perform(get("/products")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(4))) // o script inicializa já com três produtos
          .andExpect(jsonPath("$[3].name", is(tomate.getName())))
          .andExpect(jsonPath("$[3].stock", is(tomate.getStock())));
    }
}