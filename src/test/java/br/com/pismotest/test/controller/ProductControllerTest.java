package br.com.pismotest.test.controller;

import br.com.pismotest.products.Application;
import br.com.pismotest.products.controllers.ProductController;
import br.com.pismotest.products.domain.Product;
import br.com.pismotest.products.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Teste do controller
 * 
 * @author José San Pedro
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = Application.class)
public class ProductControllerTest {
    
    @Autowired
    private MockMvc mvc;
 
    @MockBean
    private ProductRepository repository;
    
    @Test
    public void givenProducts_whenGetProducts_thenReturnJsonArray() throws Exception {
        // given
        Product tomate = new Product();
        tomate.setName("tomate");
        tomate.setStock(3);
        repository.save(tomate);

        List<Product> allProducts = new ArrayList<>();
        allProducts.add(tomate);

        given(repository.findAll()).willReturn(allProducts);

        // then
        mvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(tomate.getName())))
                .andExpect(jsonPath("$[0].stock", is(tomate.getStock())));
    }
}