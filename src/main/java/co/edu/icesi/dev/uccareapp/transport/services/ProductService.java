package co.edu.icesi.dev.uccareapp.transport.services;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;

public interface ProductService {
	public Product Save(Product product, Integer categoryID, Integer subcategoryID);
	public Product Update(Product product, Integer categoryID, Integer subcategoryID);
	public Iterable<Product> findAll();
	public Optional<Product> findById(Integer id);
	public void delete(Product product);
}
