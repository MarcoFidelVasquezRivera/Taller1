package co.edu.icesi.dev.uccareapp.transport.services;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;

public interface ProductCategoryService {
	
	public Productcategory Save(Productcategory productCategory);
	public Productcategory Update(Productcategory productCategory);
}
