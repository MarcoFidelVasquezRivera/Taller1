package co.edu.icesi.dev.uccareapp.transport.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductSubcategoryRepository;

@Service
public class ProductServiceImp implements ProductService{
	
	private ProductCategoryRepository productCategoryRepository;
	
	private ProductSubcategoryRepository productSubcategoryRepository;
	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImp(ProductCategoryRepository productCategoryRepository, ProductSubcategoryRepository productSubcategoryRepository, ProductRepository productRepository) {
		this.productCategoryRepository = productCategoryRepository;
		this.productSubcategoryRepository = productSubcategoryRepository;
		this.productRepository = productRepository;
	}
	
	public Product Save(Product product, Integer categoryID, Integer subcategoryID) {
		
		if(Objects.isNull(product) || Objects.isNull(categoryID) || Objects.isNull(subcategoryID)) {
			throw new IllegalArgumentException("None of the given parameters can be null");
		}
		
		if(Objects.isNull(product.getProductnumber())) {
			throw new NullPointerException("The productNumber attribute can't be null");
		}
		
		Timestamp startDate = product.getSellstartdate();
		Timestamp endDate = product.getSellenddate();
		
		if(startDate.after(endDate)) {
			throw new ArithmeticException("Start date can not be after the end date, it must be before the endDate");
		}
		
		if(product.getSize()<=0) {
			throw new NumberFormatException("The size of the product can't be negative lower than zero");
		}
		
		if(product.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
			throw new NumberFormatException("The weight of the product can't be negative lower than zero");
		}
		
		Optional<Productcategory> opc = productCategoryRepository.findById(categoryID);
		Optional<Productsubcategory> ops = productSubcategoryRepository.findById(subcategoryID);
		
		if(opc.isEmpty()) {
			throw new NullPointerException("The Productcategory referenced is null");
		}
		
		if(ops.isEmpty()) {
			throw new NullPointerException("The Productsubcategory referenced is null");
		}
		
		Productsubcategory productSubcategory = ops.get();
		product.setProductsubcategory(productSubcategory);
		
		return productRepository.save(product);
	}
	
	public Product Update(Product product, Integer categoryID, Integer subcategoryID) {
		
		if(Objects.isNull(product) || Objects.isNull(categoryID) || Objects.isNull(subcategoryID)) {
			throw new IllegalArgumentException("None of the given parameters can be null");
		}
		
		if(Objects.isNull(product.getProductnumber())) {
			throw new NullPointerException("The productNumber attribute can't be null");
		}
		
		Timestamp startDate = product.getSellstartdate();
		Timestamp endDate = product.getSellenddate();
		
		if(startDate.after(endDate)) {
			throw new ArithmeticException("Start date can not be after the end date, it must be before the endDate");
		}
		
		if(product.getSize()<=0) {
			throw new NumberFormatException("The size of the product can't be negative lower than zero");
		}
		
		if(product.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
			throw new NumberFormatException("The weight of the product can't be negative lower than zero");
		}
		
		Optional<Productcategory> opc = productCategoryRepository.findById(categoryID);
		Optional<Productsubcategory> ops = productSubcategoryRepository.findById(subcategoryID);
		Optional<Product> op = productRepository.findById(product.getProductid()); 
		
		if(opc.isEmpty()) {
			throw new NullPointerException("The Productcategory referenced is null");
		}
		
		if(ops.isEmpty()) {
			throw new NullPointerException("The Productsubcategory referenced is null");
		}
		
		if(op.isEmpty()) {
			throw new NullPointerException("The Productsubcategory referenced is null");
		}
		
		Product productToEdit = op.get();
		Productsubcategory productSubcategory = ops.get();

		productToEdit.setBillofmaterials1(product.getBillofmaterials1());
		productToEdit.setBillofmaterials2(product.getBillofmaterials2());
		productToEdit.setClass_(product.getClass_());
		productToEdit.setColor(product.getColor());
		productToEdit.setDaystomanufacture(product.getDaystomanufacture());
		productToEdit.setDiscontinueddate(product.getDiscontinueddate());
		productToEdit.setFinishedgoodsflag(product.getFinishedgoodsflag());
		productToEdit.setListprice(product.getListprice());
		productToEdit.setMakeflag(product.getMakeflag());
		productToEdit.setModifieddate(product.getModifieddate());
		productToEdit.setName(product.getName());
		productToEdit.setProductcosthistories(product.getProductcosthistories());
		productToEdit.setProductdocuments(product.getProductdocuments());
		productToEdit.setProductid(product.getProductid());
		productToEdit.setProductinventories(product.getProductinventories());
		productToEdit.setProductline(product.getProductline());
		productToEdit.setProductlistpricehistories(product.getProductlistpricehistories());
		productToEdit.setProductmodel(product.getProductmodel());
		productToEdit.setProductnumber(product.getProductnumber());
		productToEdit.setProductproductphotos(product.getProductproductphotos());
		productToEdit.setProductreviews(product.getProductreviews());
		productToEdit.setProductsubcategory(productSubcategory);
		productToEdit.setReorderpoint(product.getReorderpoint());
		productToEdit.setRowguid(product.getRowguid());
		productToEdit.setSafetystocklevel(product.getSafetystocklevel());
		productToEdit.setSellenddate(product.getSellenddate());
		productToEdit.setSellstartdate(product.getSellstartdate());
		productToEdit.setSize(product.getSize());
		productToEdit.setStandardcost(product.getStandardcost());
		productToEdit.setStyle(product.getStyle());
		productToEdit.setTransactionhistories(product.getTransactionhistories());
		productToEdit.setUnitmeasure1(product.getUnitmeasure1());
		productToEdit.setUnitmeasure2(product.getUnitmeasure2());
		productToEdit.setWeight(product.getWeight());
		productToEdit.setWorkorders(product.getWorkorders());
		
		return productRepository.save(productToEdit);
	}
	
}
