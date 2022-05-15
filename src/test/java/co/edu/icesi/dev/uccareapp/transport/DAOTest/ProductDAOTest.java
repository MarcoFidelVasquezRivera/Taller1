package co.edu.icesi.dev.uccareapp.transport.DAOTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.icesi.dev.uccareapp.transport.daos.IProductDAO;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOTest {

	@Autowired
	private IProductDAO productDAO;
	
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
		product.setProductid(1);
		
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
		product.setProductid(1);
		
		productDAO.save(product);
		product = productDAO.findById(1);
		
		assertNotNull(product);
		product = productDAO.findById(1);
		
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
		product.setProductid(1);
		
		productDAO.save(product);
		product = productDAO.findById(1);
		
		product.setName("macarena");
		productDAO.update(product);
		product = productDAO.findById(1);
		
		assertNotNull(product);
		assertEquals("1234", product.getProductnumber());
		assertEquals(1, product.getProductid());
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
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 2, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		
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
		fail("Not yet implemented");
	}
	@Test
	@Order(6)
	void findByProductModel() {
		fail("Not yet implemented");
	}
	@Test
	@Order(7)
	void findByUnitMeasureCode() {
		fail("Not yet implemented");
	}
	@Test
	@Order(8)
	void findByNumberOfWorkOrders() {
		fail("Not yet implemented");
	}
}
