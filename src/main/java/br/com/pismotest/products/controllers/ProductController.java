
package br.com.pismotest.products.controllers;

import br.com.pismotest.products.domain.Product;
import br.com.pismotest.products.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author José San Pedro
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    /**
     * Repository to access the product data.
     */
    @Autowired
    private ProductRepository repository;
    
    /**
     * Returns a list of Product according to the given name.
     * 
     * @param name The name to be filtered.
     * @return Http Status 200 if a product List according to the given name was found, 
     * 404 if a product with the given name was not found.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listByName(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        // if the patameter name is omited
        if (name.isEmpty()) {
            return new ResponseEntity(repository.findAll(), HttpStatus.OK);
        }
        
        // if the patameter name is passed
        List<Product> listByName = repository.findByName(name);
        if (listByName.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(listByName, HttpStatus.OK);
    }
    
    /**
     * Pesquisa o produto filtrando pelo id
     * 
     * @param id    id do produto
     * @return      200 se o produto é encontrado, 
     *              404 se não existe produto com esse id, 
     *              400 se o id não é recebido
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity listById(@PathVariable("id") Long id) {
        // se o id não é preenchido
        if (id == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        Product product = repository.findOne(id);
        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity(product, HttpStatus.OK);
    }
    
    /**
     * Crates a new Product.
     * 
     * @param product The product to be created.
     * @return 409 if the product already exists,
     * 400 if product has invalid data,
     * 200 if the product was created.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createProduct(@RequestBody Product product) {
        // empty product name or stock not allowed
        if (product.getName().isEmpty() || product.getStock() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        // check if product already exists
        if (repository.existsByName(product.getName())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(repository.save(product), HttpStatus.OK);
        }
    }
    
    /**
     * Updates the quantity of Products in the stock.
     * 
     * @param id The product identifier.
     * @param stock The quantity to be increased/decreased.
     * @return 
     */
    @RequestMapping(value = "{id}/stock/{stock}", method = RequestMethod.PATCH)
    public ResponseEntity uptateStock(@PathVariable("id") Long id, @PathVariable("stock") Integer stock) {
        // get product with the given id
        Product product = repository.findOne(id);
        
        // check if the product exists
        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        product.setStock(product.getStock() + stock);
        product = repository.save(product);
        return new ResponseEntity(product, HttpStatus.OK);
    }
}
