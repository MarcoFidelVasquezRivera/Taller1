package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceIntegrationTests {

	private ProductCategoryService productCategoryService;
	private ProductSubcategoryService productSubcategoryService;
	private ProductService productService;
	
	@Autowired
	public ProductServiceIntegrationTests(ProductCategoryService productCategoryService, ProductSubcategoryService productSubcategoryService, ProductService productService) {
		this.productCategoryService = productCategoryService;
		this.productSubcategoryService = productSubcategoryService;
		this.productService = productService;
	}
	
	@Test
	@Order(1)
	@DisplayName("se prueba que una Product se pueda guardar correctamente")
	void ProductSubcategorySave() {
		Product product = new Product();
		Long currentTime = System.currentTimeMillis();
		product.setProductnumber("1234");
		product.setSellstartdate(new Timestamp(currentTime));
		product.setSellenddate(new Timestamp(currentTime+172800000));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductid(1);
		
		Integer categoryID = 1;
		Integer subcategoryID = 1;
		
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		pc.setProductcategoryid(1);
		productCategoryService.Save(pc);
		
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductsubcategoryid(1);
		productSubcategoryService.Save(psc, categoryID);
		
		Product p = productService.Save(product, categoryID, subcategoryID);
		
		assertNotNull(p);
		
		assertEquals(p.getProductnumber(), product.getProductnumber());
		assertEquals(p.getProductid(), product.getProductid());
		
	}
	
	@Test
	@Order(2)
	@DisplayName("se prueba que una Product se pueda editar correctamente")
	void ProductSubcategoryEdit() {
		Product product = new Product();
		Long currentTime = System.currentTimeMillis();
		product.setProductnumber("12345678");
		product.setSellstartdate(new Timestamp(currentTime));
		product.setSellenddate(new Timestamp(currentTime+172800000));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductid(1);
		
		Integer categoryID = 1;
		Integer subcategoryID = 1;
		
		Product p = productService.Update(product, categoryID, subcategoryID);
		
		assertNotNull(p);
		
		assertEquals(p.getProductnumber(), product.getProductnumber());
		assertEquals(p.getProductid(), product.getProductid());
		
	}

}
