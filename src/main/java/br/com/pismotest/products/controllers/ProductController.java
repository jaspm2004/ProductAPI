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

    @Autowired
    private ProductRepository repository;
    
    /**
     * Retorna uma lista de produtos filtrando pelo nome. 
     * Caso não seja preenchido o nome na pesquisa, 
     * retorna a lista de todos os produtos
     * 
     * @param name  nome para filtrar a pesquisa, o defaul é ""
     * @return      200 se a pesquisa é executada com sucesso, 
     *              404 se não existe produto com esse nome, 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listByName(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        // se o name é null, retorna todos os produtos
        if (name.isEmpty()) {
            return new ResponseEntity(repository.findAll(), HttpStatus.OK);
        }
        
        // se o name não é null, filtra
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
        // o id não pode ser null
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
     * Insere um novo produto
     * 
     * @param product o produto que será inserido
     * @return      200 se o produto é inserido com sucesso, 
     *              400 se falta informação para inserir,
     *              409 se já existe um produto com o mesmo nome
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createProduct(@RequestBody Product product) {
        // o nome e o stock não podem ser null
        if (product.getName().isEmpty() || product.getStock() == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        
        // verifica se já existe um produto com esse nome
        if (repository.existsByName(product.getName())) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(repository.save(product), HttpStatus.OK);
        }
    }
    
    /**
     * Atualiza o stock do produto
     * 
     * @param id    o id do produto que será atualizado
     * @param stock a quantidade que será acrescentada/decrementada do stock
     * @return      200 se o stock é atualizado com sucesso, 
     *              404 se não existe produto com esse id,  
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
