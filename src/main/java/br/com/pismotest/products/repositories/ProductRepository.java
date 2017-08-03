
package br.com.pismotest.products.repositories;

import br.com.pismotest.products.domain.Product;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author José San Pedro
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

    List<Product> findByName(String name);
    
    boolean existsByName(String name);
}