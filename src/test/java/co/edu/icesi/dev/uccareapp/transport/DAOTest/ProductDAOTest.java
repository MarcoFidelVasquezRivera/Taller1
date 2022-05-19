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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import co.edu.icesi.dev.uccareapp.transport.daos.IProductDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductCategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductSubcategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.WorkOrderDAO;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productmodel;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Unitmeasure;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductModelRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.UnitMeasureRepository;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Rollback(false)
class ProductDAOTest {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ProductCategoryDAO productCategoryDAO;
	
	@Autowired
	private ProductSubcategoryDAO productSubcategoryDAO;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductModelRepository productModelRepository;
	
	@Autowired
	private UnitMeasureRepository unitMeasureRepository;
	
	@Autowired
	private WorkOrderDAO workOrderDao;
	
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
		
		Productmodel pm = new Productmodel();
		pm.setName("productmodel");
		productModelRepository.save(pm);
		
		Productmodel pm2 = new Productmodel();
		pm2.setName("productmodel2");
		productModelRepository.save(pm2);
		
		Unitmeasure um =  new Unitmeasure();
		um.setName("um1");
		unitMeasureRepository.save(um);
		
		Unitmeasure um2 =  new Unitmeasure();
		um2.setName("um2");
		unitMeasureRepository.save(um2);
	}
	
	@BeforeEach
	public void clear() {
		productRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	void saveAndFind() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));

		
		productDAO.save(product);
		
		product = productDAO.findById(1);
		
		assertNotNull(product);
		assertEquals("1234", product.getProductnumber());
		assertEquals(1, product.getProductid());
		assertEquals("colores", product.getName());
	}
	
	@Test
	@Order(2)
	void delete() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		
		productDAO.save(product);
		product = productDAO.findById(2);
		
		productDAO.delete(product);
		
		assertNotNull(product);
		product = productDAO.findById(2);
		
		assertNull(product);
	}
	
	@Test
	@Order(3)
	void update() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		
		productDAO.save(product);
		product = productDAO.findById(3);
		
		product.setName("macarena");
		productDAO.update(product);
		product = productDAO.findById(3);
		
		assertNotNull(product);
		assertEquals("1234", product.getProductnumber());
		assertEquals(3, product.getProductid());
		assertEquals("macarena", product.getName());
	}
	@Test
	@Order(4)
	void findAll() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 2, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		product2.setProductsubcategory(productSubcategoryDAO.findById(1));
		
		productDAO.save(product);
		productDAO.save(product2);
		
		List<Product> list = productDAO.findAll();
		
		assertNotNull(list);
		assertEquals(list.size(), 2);
		
		assertNotNull(list.get(0));
		assertNotNull(list.get(1));
		
		assertEquals(list.get(0).getName(), "colores");
		assertEquals(list.get(1).getName(), "macarena");
		
		assertEquals(list.get(0).getProductnumber(), "1234");
		assertEquals(list.get(1).getProductnumber(), "0987");
	}
	@Test
	@Order(5)
	void findByProductSubcategoryId() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 2, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		product2.setProductsubcategory(productSubcategoryDAO.findById(1));
		
		Product product3 = new Product();
		product3.setName("macarena");
		product3.setProductnumber("0987");
		product3.setSellstartdate(LocalDate.of(2020, 1,8));
		product3.setSellenddate(LocalDate.of(2020, 2, 8));
		product3.setSize(1);
		product3.setWeight(new BigDecimal(1));
		product3.setProductsubcategory(productSubcategoryDAO.findById(2));
		
		productDAO.save(product);
		productDAO.save(product2);
		productDAO.save(product3);
		
		List<Product> list = productDAO.findByProductSubcategoryId(1);
		
		assertNotNull(list);
		assertEquals(list.size(), 2);
		
		assertNotNull(list.get(0));
		assertNotNull(list.get(1));
		
		assertEquals(list.get(0).getName(), "colores");
		assertEquals(list.get(1).getName(), "macarena");
		
		assertEquals(list.get(0).getProductnumber(), "1234");
		assertEquals(list.get(1).getProductnumber(), "0987");
	}
	@Test
	@Order(6)
	void findByProductModel() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		product.setProductmodel(productModelRepository.findById(2).get());
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 2, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		product2.setProductsubcategory(productSubcategoryDAO.findById(1));
		product2.setProductmodel(productModelRepository.findById(2).get());
		
		Product product3 = new Product();
		product3.setName("ajolote");
		product3.setProductnumber("3456");
		product3.setSellstartdate(LocalDate.of(2020, 1,8));
		product3.setSellenddate(LocalDate.of(2020, 2, 8));
		product3.setSize(1);
		product3.setWeight(new BigDecimal(1));
		product3.setProductsubcategory(productSubcategoryDAO.findById(1));
		product3.setProductmodel(productModelRepository.findById(1).get());
		
		productDAO.save(product);
		productDAO.save(product2);
		productDAO.save(product3);
		
		List<Product> list = productDAO.findByProductModel(1);
		
		assertNotNull(list);
		assertEquals(list.size(), 1);
		assertNotNull(list.get(0));
		assertEquals(list.get(0).getName(), "ajolote");
		assertEquals(list.get(0).getProductnumber(), "3456");
		assertEquals(list.get(0).getProductmodel().getName(), "productmodel");
	}
	@Test
	@Order(7)
	void findByUnitMeasureCode() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		product.setProductmodel(productModelRepository.findById(2).get());
		product.setUnitmeasure1(unitMeasureRepository.findById(1).get());
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 2, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		product2.setProductsubcategory(productSubcategoryDAO.findById(1));
		product2.setProductmodel(productModelRepository.findById(2).get());
		product2.setUnitmeasure1(unitMeasureRepository.findById(2).get());
		
		Product product3 = new Product();
		product3.setName("ajolote");
		product3.setProductnumber("3456");
		product3.setSellstartdate(LocalDate.of(2020, 1,8));
		product3.setSellenddate(LocalDate.of(2020, 2, 8));
		product3.setSize(1);
		product3.setWeight(new BigDecimal(1));
		product3.setProductsubcategory(productSubcategoryDAO.findById(1));
		product3.setProductmodel(productModelRepository.findById(1).get());
		product3.setUnitmeasure1(unitMeasureRepository.findById(1).get());
		
		productDAO.save(product);
		productDAO.save(product2);
		productDAO.save(product3);
		
		List<Product> list = productDAO.findByUnitMeasureCode(2);
		
		assertNotNull(list);
		assertEquals(list.size(), 1);
		assertNotNull(list.get(0));
		assertEquals(list.get(0).getName(), "macarena");
		assertEquals(list.get(0).getProductnumber(), "0987");
		assertEquals(list.get(0).getUnitmeasure1().getUnitmeasurecode(), 2);
	}
	@Test
	@Order(8)
	void findByNumberOfWorkOrders() {
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(1));
		product.setProductmodel(productModelRepository.findById(2).get());
		product.setUnitmeasure1(unitMeasureRepository.findById(1).get());
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 2, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		product2.setProductsubcategory(productSubcategoryDAO.findById(1));
		product2.setProductmodel(productModelRepository.findById(2).get());
		product2.setUnitmeasure1(unitMeasureRepository.findById(2).get());
		
		Product product3 = new Product();
		product3.setName("ajolote");
		product3.setProductnumber("3456");
		product3.setSellstartdate(LocalDate.of(2020, 1,8));
		product3.setSellenddate(LocalDate.of(2020, 2, 8));
		product3.setSize(1);
		product3.setWeight(new BigDecimal(1));
		product3.setProductsubcategory(productSubcategoryDAO.findById(1));
		product3.setProductmodel(productModelRepository.findById(1).get());
		product3.setUnitmeasure1(unitMeasureRepository.findById(1).get());
		
		productDAO.save(product);
		productDAO.save(product2);
		productDAO.save(product3);
		
		product = productDAO.findById(15);
		product2 = productDAO.findById(16);
		product3 = productDAO.findById(17);
		
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		workorder.setProduct(product);
		
		Workorder workorder2 = new Workorder();
		workorder2.setStartdate(LocalDate.of(2020, 1,8));
		workorder2.setEnddate(LocalDate.of(2020, 2, 8));
		workorder2.setOrderqty(2);
		workorder2.setScrappedqty(2);
		workorder2.setProduct(product2);
		
		Workorder workorder3 = new Workorder();
		workorder3.setStartdate(LocalDate.of(2020, 1,8));
		workorder3.setEnddate(LocalDate.of(2020, 2, 8));
		workorder3.setOrderqty(6);
		workorder3.setScrappedqty(6);
		workorder3.setProduct(product2);
		
		workOrderDao.save(workorder);
		workOrderDao.save(workorder2);
		workOrderDao.save(workorder3);
		
		
		
		List<Product> list = productDAO.findByNumberOfWorkOrders();
		
		assertNotNull(list);
		assertEquals(list.size(), 1);
		assertNotNull(list.get(0));
		assertEquals(list.get(0).getName(), "macarena");
		assertEquals(list.get(0).getProductnumber(), "0987");
		assertEquals(list.get(0).getUnitmeasure1().getUnitmeasurecode(), 2);
	}
}
