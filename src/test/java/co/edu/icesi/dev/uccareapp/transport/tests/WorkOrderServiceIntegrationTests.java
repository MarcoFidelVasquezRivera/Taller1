package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

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
import co.edu.icesi.dev.uccareapp.transport.model.prod.Scrapreason;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ScrapReasonRepository;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.WorkOrderService;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkOrderServiceIntegrationTests {

	private WorkOrderService workOrderService;
	private ScrapReasonRepository scrapReasonRepository;
	private ProductService productService;
	private ProductSubcategoryService productSubcategoryService;
	private ProductCategoryService productCategoryService;
	
	@Autowired 
	public WorkOrderServiceIntegrationTests(WorkOrderService workOrderService, ScrapReasonRepository scrapReasonRepository,
			ProductService productService, ProductSubcategoryService productSubcategoryService, ProductCategoryService productCategoryService){
		this.workOrderService = workOrderService;
		this.scrapReasonRepository = scrapReasonRepository;
		this.productService = productService;
		this.productSubcategoryService = productSubcategoryService;
		this.productCategoryService = productCategoryService;
	}
	
	
	@Test
	@Order(1)
	@DisplayName("se prueba que una Workorder se pueda guardar correctamente")
	void ProductSubcategorySave() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setWorkorderid(1);
		
		Integer productId = 1;
		Integer categoryId = 1;
		
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		pc.setProductcategoryid(1);
		productCategoryService.Save(pc);
		
		
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductsubcategoryid(1);
		productSubcategoryService.Save(psc, categoryId);

		
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		productService.Save(product, categoryId, categoryId);
		
		Workorder wo = workOrderService.Save(workorder, 0, productId);
		assertNotNull(wo);
		wo = workOrderService.findById(wo.getWorkorderid()).get();
		assertNotNull(wo);
		assertEquals(workorder.getStartdate(), wo.getStartdate());
		assertEquals(workorder.getEnddate(), wo.getEnddate());
		assertEquals(workorder.getOrderqty(), wo.getOrderqty());
		assertEquals(workorder.getScrappedqty(), wo.getScrappedqty());
	}
	
	@Test
	@Order(2)
	@DisplayName("se prueba que una Workorder se pueda editar correctamente")
	void ProductSubcategoryEdit() {
		Workorder workorder = new Workorder();
		Long currentTime = System.currentTimeMillis();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setWorkorderid(1);
		
		Integer scrapResonId = 1;
		Integer productId = 1;
		
		Workorder wo = workOrderService.Update(workorder, scrapResonId, productId);

		assertNotNull(wo);
		wo = workOrderService.findById(wo.getWorkorderid()).get();
		assertNotNull(wo);
		assertEquals(workorder.getStartdate(), wo.getStartdate());
		assertEquals(workorder.getEnddate(), wo.getEnddate());
		assertEquals(workorder.getOrderqty(), wo.getOrderqty());
		assertEquals(workorder.getScrappedqty(), wo.getScrappedqty());

	}

}
