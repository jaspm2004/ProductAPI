package br.com.pismotest.test.repository;

import br.com.pismotest.products.domain.Product;
import br.com.pismotest.products.repositories.ProductRepository;
import br.com.pismotest.test.AbstractTest;
import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Teste do repositório
 * 
 * @author José San Pedro
 */
@DataJpaTest
public class ProductReporsitoryTest extends AbstractTest {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    public void whenFindByName_thenReturnProduct() throws Exception {
        // given
        Product tomate = new Product();
        tomate.setName("tomate");
        tomate.setStock(3);
        entityManager.persist(tomate);
        entityManager.flush();

        // when
        Product found = productRepository.findByName(tomate.getName()).get(0);

        // then
        assertThat(found.getName()).isEqualTo(tomate.getName());
        assertThat(found.getStock()).isEqualTo(tomate.getStock());
    }
}