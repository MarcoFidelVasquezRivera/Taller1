package co.edu.icesi.dev.uccareapp.transport.services;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;

public interface ProductService {
	public Product Save(Product product, Integer categoryID, Integer subcategoryID);
	public Product Update(Product product, Integer categoryID, Integer subcategoryID);
}
