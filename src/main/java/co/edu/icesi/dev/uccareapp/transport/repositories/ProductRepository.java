package co.edu.icesi.dev.uccareapp.transport.repositories;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{

}
