package co.edu.icesi.dev.uccareapp.transport.DAOTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.icesi.dev.uccareapp.transport.daos.IWorkOrderDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductCategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductSubcategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.WorkOrderDAO;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productmodel;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Scrapreason;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Unitmeasure;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductModelRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ScrapReasonRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.UnitMeasureRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.WorkOrderRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class WorkOrderDAOTest {

	@Autowired
	private WorkOrderDAO workOrderDAO;
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ProductCategoryDAO productCategoryDAO;
	
	@Autowired
	private ProductSubcategoryDAO productSubcategoryDAO;
	
	@Autowired
	private ScrapReasonRepository scrapReasonRepository;
//	@Autowired
//	private ProductModelRepository productModelRepository;
//	
//	@Autowired
//	private UnitMeasureRepository unitMeasureRepository;
	
	@BeforeAll
	public void setup1() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		productCategoryDAO.save(pc);
		
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc);
		
		Productsubcategory psc2 = new Productsubcategory();
		psc2.setName("POIUY");
		psc2.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc2);

		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		productDAO.save(product);
		
		Scrapreason scrapReason = new Scrapreason();
		scrapReason.setName("huele feo");
		scrapReasonRepository.save(scrapReason);
		
		Scrapreason scrapReason2 = new Scrapreason();
		scrapReason2.setName("no me gusto");
		scrapReasonRepository.save(scrapReason2);
	}
	
	
	@BeforeEach
	public void clear() {
		workOrderRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	void saveAndFind() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(productDAO.findById(1));
		
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(1);
		
		assertNotNull(workorder.getOrderqty());
		assertEquals(1, workorder.getScrappedqty());
		assertEquals(LocalDate.of(2020, 1,8), workorder.getStartdate());
		assertEquals(LocalDate.of(2020, 2, 8), workorder.getEnddate());
	}
	
	@Test
	@Order(2)
	void update() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(productDAO.findById(1));
		
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(2);
		
		workorder.setOrderqty(20);
		workOrderDAO.update(workorder);
		workorder = workOrderDAO.findById(2);
		
		assertNotNull(workorder);
		assertEquals(20, workorder.getOrderqty());
		assertEquals(1, workorder.getScrappedqty());
		assertEquals(LocalDate.of(2020, 1,8), workorder.getStartdate());
		assertEquals(LocalDate.of(2020, 2, 8), workorder.getEnddate());
	}
	
	@Test
	@Order(3)
	void delete() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(productDAO.findById(1));
		
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(3);
		assertNotNull(workorder);
		
		workOrderDAO.delete(workorder);
		workorder = workOrderDAO.findById(3);
		assertNull(workorder);
	}
	
	@Test
	@Order(4)
	void findAll() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(productDAO.findById(1));

		Workorder workorder2 = new Workorder();
		workorder2.setStartdate(LocalDate.of(2020, 2,8));
		workorder2.setEnddate(LocalDate.of(2020, 3, 8));
		workorder2.setOrderqty(15);
		workorder2.setScrappedqty(8);
		workorder2.setProduct(productDAO.findById(1));
		
		workOrderDAO.save(workorder);
		workOrderDAO.save(workorder2);
		
		List<Workorder> list = workOrderDAO.findAll();
		
		assertNotNull(list);
		assertEquals(list.size(), 2);
		
		assertNotNull(list.get(0));
		assertNotNull(list.get(1));
		
		Workorder wo1 = list.get(0);
		Workorder wo2 = list.get(1);
		
		assertEquals(1, wo1.getOrderqty());
		assertEquals(1, wo1.getScrappedqty());
		assertEquals(LocalDate.of(2020, 1,8), wo1.getStartdate());
		assertEquals(LocalDate.of(2020, 2, 8), wo1.getEnddate());
		
		assertEquals(15, wo2.getOrderqty());
		assertEquals(8, wo2.getScrappedqty());
		assertEquals(LocalDate.of(2020, 2,8), wo2.getStartdate());
		assertEquals(LocalDate.of(2020, 3, 8), wo2.getEnddate());
		
	}
	
	@Test
	@Order(5)
	void findByScrapReason() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(productDAO.findById(1));
		workorder.setScrapreason(scrapReasonRepository.findById(1).get());

		Workorder workorder2 = new Workorder();
		workorder2.setStartdate(LocalDate.of(2020, 2,8));
		workorder2.setEnddate(LocalDate.of(2020, 3, 8));
		workorder2.setOrderqty(15);
		workorder2.setScrappedqty(8);
		workorder2.setProduct(productDAO.findById(1));
		workorder2.setScrapreason(scrapReasonRepository.findById(2).get());
		
		workOrderDAO.save(workorder);
		workOrderDAO.save(workorder2);
		
		List<Workorder> list = workOrderDAO.findByScrapReason(1);
		
		assertNotNull(list);
		assertEquals(list.size(), 1);
		
		assertNotNull(list.get(0));
		
		Workorder wo1 = list.get(0);
		
		assertEquals(1, wo1.getOrderqty());
		assertEquals(1, wo1.getScrappedqty());
		assertEquals(LocalDate.of(2020, 1,8), wo1.getStartdate());
		assertEquals(LocalDate.of(2020, 2, 8), wo1.getEnddate());
	}
	
	@Test
	@Order(6)
	void findByOrderqty() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(productDAO.findById(1));

		Workorder workorder2 = new Workorder();
		workorder2.setStartdate(LocalDate.of(2020, 2,8));
		workorder2.setEnddate(LocalDate.of(2020, 3, 8));
		workorder2.setOrderqty(15);
		workorder2.setScrappedqty(8);
		workorder2.setProduct(productDAO.findById(1));
		
		workOrderDAO.save(workorder);
		workOrderDAO.save(workorder2);
		
		List<Workorder> list = workOrderDAO.findByOrderqty(15);
		
		assertNotNull(list);
		assertEquals(list.size(), 1);
		
		assertNotNull(list.get(0));
		
		Workorder wo1 = list.get(0);
		assertEquals(15, wo1.getOrderqty());
		assertEquals(8, wo1.getScrappedqty());
		assertEquals(LocalDate.of(2020, 2,8), wo1.getStartdate());
		assertEquals(LocalDate.of(2020, 3, 8), wo1.getEnddate());
	}
	
}
