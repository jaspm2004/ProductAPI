# ProductAPI

API de produtos responsável pelo cadastro e controle de estoque de cada produto. 
Oferece uma interface REST para cadastro e busca de produtos.

### Tecnologias utilizadas

* Maven
* Spring Boot
* H2

### Para rodar este projeto localmente
```
$ git clone https://github.com/jaspm2004/ProductAPI
$ mvn clean install
$ mvn dependency:copy-dependencies
$ cd target
$ java -jar .\ProductAPI-1.0.0.jar
```
A API REST fica em http://localhost:8080/productapi/products


### Para importar este projeto utilizando IDE

* Fazer o clone: https://github.com/jaspm2004/ProductAPI
* Clean & Build
* Run

### Exemplos de uso
* Para cadastrar um novo produto
```
fazer um POST em http://localhost:8080/productapi/products
passar parâmetros no Request Body: name = <string> e stock = <int>
```
* Para pesquisar produto
```
fazer um GET em http://localhost:8080/productapi/products (lista todos os produtos)
fazer um GET em http://localhost:8080/productapi/products/<id> (filtra pelo id)
fazer um GET em http://localhost:8080/productapi/products?name=<nome do produto> (filtra pelo nome)
```
* Para modificar stock de um produto
```
fazer um PATCH em http://localhost:8080/productapi/products/<id>/stock/<qtd>
qtd > 0 para acrescentar 
qtd < 0 para decrementar
```
