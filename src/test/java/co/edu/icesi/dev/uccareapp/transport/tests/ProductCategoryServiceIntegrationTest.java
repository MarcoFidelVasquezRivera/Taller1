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
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryServiceIntegrationTest {

	private ProductCategoryService productCategoryService;
	
	@Autowired
	public ProductCategoryServiceIntegrationTest(ProductCategoryService productCategoryService) {
		this.productCategoryService = productCategoryService;
	}
	
	@Test
	@Order(1)
	@DisplayName("se prueba que una Productcategory se pueda guardar correctamente")
	void ProductCategorySave() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		pc.setProductcategoryid(1);
		Productcategory productcategory = productCategoryService.Save(pc);
		assertNotNull(productcategory);
		assertSame(pc.getProductcategoryid(), productcategory.getProductcategoryid());
		assertSame(pc.getName(), productcategory.getName());
	}
	
	@Test
	@Order(2)
	@DisplayName("se prueba que una Productcategory se pueda editar correctamente")
	void ProductCategoryEdit() {
		Productcategory pc2 = new Productcategory();
		pc2.setName("ABCD");
		pc2.setProductcategoryid(1);
		Productcategory productcategory = productCategoryService.Update(pc2);
		assertSame(pc2.getName(), productcategory.getName());
		assertSame(pc2.getProductcategoryid(), productcategory.getProductcategoryid());
	}
	
	

}
