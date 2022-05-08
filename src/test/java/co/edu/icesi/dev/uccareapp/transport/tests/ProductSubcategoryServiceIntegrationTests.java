package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductSubcategoryServiceIntegrationTests {
	
	private ProductCategoryService productCategoryService;
	private ProductSubcategoryService productSubcategoryService;
	
	@Autowired
	public ProductSubcategoryServiceIntegrationTests(ProductCategoryService productCategoryService, ProductSubcategoryService productSubcategoryService) {
		this.productCategoryService = productCategoryService;
		this.productSubcategoryService = productSubcategoryService;
	}
	
	@Test
	@Order(1)
	@DisplayName("se prueba que una Productsubcategory se pueda guardar correctamente")
	void ProductSubcategorySave() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		pc.setProductcategoryid(1);
		productCategoryService.Save(pc);
		
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductsubcategoryid(1);
		Integer id = 1;
		
		Productsubcategory productSubcategory = productSubcategoryService.Save(psc, id);
		assertNotNull(productSubcategory);
		productSubcategory = productSubcategoryService.findById(productSubcategory.getProductsubcategoryid()).get();
		assertNotNull(productSubcategory);
		assertSame(psc.getProductsubcategoryid(), productSubcategory.getProductsubcategoryid());
		assertSame(psc.getName(), productSubcategory.getName());
	}
	
	@Test
	@Order(2)
	@DisplayName("se prueba que una Productsubcategory se pueda guardar correctamente")
	void ProductSubcategoryEdit() {
		Productsubcategory psc = new Productsubcategory();
		psc.setName("DFGHI");
		psc.setProductsubcategoryid(1);
		Integer id = 1;
		
		Productsubcategory productSubcategory = productSubcategoryService.Update(psc, id);
		assertNotNull(productSubcategory);
		productSubcategory = productSubcategoryService.findById(productSubcategory.getProductsubcategoryid()).get();
		assertNotNull(productSubcategory);
		assertSame(psc.getProductsubcategoryid(), productSubcategory.getProductsubcategoryid());
		assertSame(psc.getName(), productSubcategory.getName());
	}

}
