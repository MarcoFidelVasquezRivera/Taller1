package co.edu.icesi.dev.uccareapp.transport.services;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;

public interface ProductSubcategoryService {
	
	public Productsubcategory Save(Productsubcategory productsubcategory, Integer categoryIdentifier);
	public Productsubcategory Update(Productsubcategory productsubcategory, Integer categoryIdentifier);
}
