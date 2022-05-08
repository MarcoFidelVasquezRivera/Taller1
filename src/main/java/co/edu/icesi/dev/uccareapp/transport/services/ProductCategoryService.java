package co.edu.icesi.dev.uccareapp.transport.services;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;

public interface ProductCategoryService {
	
	public Productcategory Save(Productcategory productCategory);
	public Productcategory Update(Productcategory productCategory);
	public Iterable<Productcategory> findAll();
	public Optional<Productcategory> findById(Integer id);
	public void delete(Productcategory productCategory);
}
