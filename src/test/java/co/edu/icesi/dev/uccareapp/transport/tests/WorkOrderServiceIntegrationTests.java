package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;

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
import co.edu.icesi.dev.uccareapp.transport.model.prod.Scrapreason;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ScrapReasonRepository;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.WorkOrderService;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkOrderServiceIntegrationTests {

	private WorkOrderService workOrderService;
	private ScrapReasonRepository scrapReasonRepository;
	private ProductRepository productRepository;
	private ProductSubcategoryService productSubcategoryService;
	private ProductCategoryService productCategoryService;
	
	@Autowired 
	public WorkOrderServiceIntegrationTests(WorkOrderService workOrderService, ScrapReasonRepository scrapReasonRepository,
			ProductRepository productRepository, ProductSubcategoryService productSubcategoryService, ProductCategoryService productCategoryService){
		this.workOrderService = workOrderService;
		this.scrapReasonRepository = scrapReasonRepository;
		this.productRepository = productRepository;
		this.productSubcategoryService = productSubcategoryService;
		this.productCategoryService = productCategoryService;
	}
	
	
	@Test
	@Order(1)
	@DisplayName("se prueba que una Workorder se pueda guardar correctamente")
	void ProductSubcategorySave() {
		Workorder workorder = new Workorder();
		Long currentTime = System.currentTimeMillis();
		workorder.setStartdate(new Timestamp(currentTime));
		workorder.setEnddate(new Timestamp(currentTime+172800000));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setWorkorderid(1);
		
		Integer scrapResonId = 1;
		Integer productId = 1;
		Integer categoryId = 1;
		
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		pc.setProductcategoryid(1);
		productCategoryService.Save(pc);
		
		Scrapreason sr =  new Scrapreason();
		sr.setScrapreasonid(1);
		scrapReasonRepository.save(sr);
		
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductsubcategoryid(1);
		productSubcategoryService.Save(psc, categoryId);

		
		Product pr = new Product();
		pr.setProductid(1);
		productRepository.save(pr);
		
		Workorder wo = workOrderService.Save(workorder, scrapResonId, productId);

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
		workorder.setStartdate(new Timestamp(currentTime+800000));
		workorder.setEnddate(new Timestamp(currentTime+172800000));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setWorkorderid(1);
		
		Integer scrapResonId = 1;
		Integer productId = 1;
		
		Workorder wo = workOrderService.Update(workorder, scrapResonId, productId);

		System.out.println(wo);
		assertNotNull(wo);
		assertEquals(workorder.getStartdate(), wo.getStartdate());
		assertEquals(workorder.getEnddate(), wo.getEnddate());
		assertEquals(workorder.getOrderqty(), wo.getOrderqty());
		assertEquals(workorder.getScrappedqty(), wo.getScrappedqty());

	}

}
