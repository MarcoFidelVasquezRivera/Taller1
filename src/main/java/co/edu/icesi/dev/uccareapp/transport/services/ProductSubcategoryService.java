package co.edu.icesi.dev.uccareapp.transport.services;

import java.util.Optional;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;

public interface ProductSubcategoryService {
	
	public Productsubcategory Save(Productsubcategory productsubcategory, Integer categoryIdentifier);
	public Productsubcategory Update(Productsubcategory productsubcategory, Integer categoryIdentifier);
	public Iterable<Productsubcategory> findAll();
	public Optional<Productsubcategory> findById(Integer id);
	public void delete(Productsubcategory productSubcategory);
}
