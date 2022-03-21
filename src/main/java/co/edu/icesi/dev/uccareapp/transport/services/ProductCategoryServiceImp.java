package co.edu.icesi.dev.uccareapp.transport.services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService{
	
	private ProductCategoryRepository productCategoryRepository;
	
	public ProductCategoryServiceImp(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}
	
	public Productcategory Save(Productcategory productCategory) {
		if(Objects.isNull(productCategory)) {
			throw new IllegalArgumentException("you can not enter a null object to be saved");
		
		}
		
		if(productCategory.getName().length()<3) {
			throw new NumberFormatException("The length of the name is can't be less that 3 charactes");
		}
		
		return productCategoryRepository.save(productCategory);
	}
	
	public Productcategory Update(Productcategory productCategory) {
		if(Objects.isNull(productCategory)) {
			throw new IllegalArgumentException("you can not enter a null object to update a saved object");
		
		}
		
		if(productCategory.getName().length()<3) {
			throw new NumberFormatException("The length of the name is can't be less that 3 charactes");
		}
		
		Productcategory pc = productCategoryRepository.findById(productCategory.getProductcategoryid()).get();
		
		pc.setModifieddate(productCategory.getModifieddate());
		pc.setName(productCategory.getName());
		pc.setProductsubcategories(productCategory.getProductsubcategories());
		pc.setRowguid(productCategory.getRowguid());
		return productCategoryRepository.save(pc);
	}
}
