package co.edu.icesi.dev.uccareapp.transport.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductSubcategoryRepository;

@Service
public class ProductSubcategoryServiceImp implements ProductSubcategoryService{

	ProductSubcategoryRepository productSubcategoryRepository;
	ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	public ProductSubcategoryServiceImp(ProductSubcategoryRepository productSubcategoryRepository, ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
		this.productSubcategoryRepository = productSubcategoryRepository;
	}
	
	public Productsubcategory Save(Productsubcategory productsubcategory, Integer categoryIdentifier) {
		if(Objects.isNull(productsubcategory)) {
			throw new IllegalArgumentException("you can not enter a null object to be saved");
		}
		
		if(productsubcategory.getName().length()<5) {
			throw new NumberFormatException("you can not enter an object which name is less than 5 caracters");
		}
		
		if(Objects.isNull(categoryIdentifier)) {
			throw new UnsupportedOperationException("A Productsubcategory needs an asociation to a Productcategory");
		}
		
		Optional<Productcategory> pc = productCategoryRepository.findById(categoryIdentifier);
		
		if(pc.isEmpty()) {
			throw new NullPointerException("The productCategory referenced does not exist");
		}
		
		
		
		Productcategory productCategory = pc.get();
		productsubcategory.setProductcategory(productCategory);
		
		return productSubcategoryRepository.save(productsubcategory);
	}
	
	public Productsubcategory Update(Productsubcategory productsubcategory, Integer categoryIdentifier) {
		if(Objects.isNull(productsubcategory)) {
			throw new IllegalArgumentException("you can not enter a null object to be saved");
		}
		
		if(productsubcategory.getName().length()<5) {
			throw new NumberFormatException("you can not enter an object which name is less than 5 caracters");
		}
		
		if(Objects.isNull(categoryIdentifier)) {
			throw new UnsupportedOperationException("A Productsubcategory needs an asociation to a Productcategory");
		}
		
		Optional<Productcategory> pc = productCategoryRepository.findById(categoryIdentifier);
		Optional<Productsubcategory> psc = productSubcategoryRepository.findById(productsubcategory.getProductsubcategoryid());
		
		if(pc.isEmpty()) {
			throw new NullPointerException("The productCategory referenced does not exist");
		}
		
		if(psc.isEmpty()) {
			throw new NullPointerException("The productsubcategory that is going to be edited does not exist");
		}
		
		Productcategory productCategory = pc.get();
		
		Productsubcategory productsubcategoryToEdit = psc.get();
		
		productsubcategoryToEdit.setModifieddate(productsubcategory.getModifieddate());
		productsubcategoryToEdit.setName(productsubcategory.getName());
		productsubcategoryToEdit.setProductcategory(productCategory);
		productsubcategoryToEdit.setProductsubcategoryid(productsubcategory.getProductsubcategoryid());
		productsubcategoryToEdit.setRowguid(productsubcategory.getRowguid());
		
		return productSubcategoryRepository.save(productsubcategoryToEdit);
	}

	public Iterable<Productsubcategory> findAll() {
		// TODO Auto-generated method stub
		return productSubcategoryRepository.findAll();
	}

	public Optional<Productsubcategory> findById(Integer id) {
		// TODO Auto-generated method stub
		return productSubcategoryRepository.findById(id);
	}

	public void delete(Productsubcategory productSubcategory) {
		// TODO Auto-generated method stub
		productSubcategoryRepository.delete(productSubcategory);
	}
}
