package co.edu.icesi.dev.uccareapp.transport.DAOTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.icesi.dev.uccareapp.transport.daos.IProductCategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductCategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCategoryDaoTest {

	@Autowired
	private ProductCategoryDAO productCategoryDAO;
	
	@Autowired ProductCategoryRepository productCategoryRepository;
	
	@BeforeEach
	public void cleaner() {
		productCategoryRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	public void saveAndFind() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		
		productCategoryDAO.save(pc);
		Productcategory pc2 = productCategoryDAO.findById(1);
		
		assertEquals(pc.getName(), pc2.getName());
	}
	
	@Test
	@Order(2)
	public void delete() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		
		productCategoryDAO.save(pc);
		Productcategory pc2 = productCategoryDAO.findById(2);
		assertNotNull(pc2);
		
		productCategoryDAO.delete(pc2);
		
		pc2 = productCategoryDAO.findById(2);
		assertNull(pc2);
	}
	
	@Test
	@Order(3)
	public void update() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		
		productCategoryDAO.save(pc);
		Productcategory pc2 = productCategoryDAO.findById(3);
		
		assertNotNull(pc2);
		
		pc2.setName("ALO");
		productCategoryDAO.update(pc2);
		pc = productCategoryDAO.findById(3);
		
		assertEquals(pc.getName(), pc2.getName());
	}

	@Test
	@Order(4)
	public void findAll() {
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		productCategoryDAO.save(pc);
		
		pc = new Productcategory();
		pc.setName("ALO");
		productCategoryDAO.save(pc);
		
		List<Productcategory> productCategories = productCategoryDAO.findAll();
		
		assertNotNull(productCategories);
		assertEquals(productCategories.size(),2);
		
		assertNotNull(productCategories.get(0));
		assertNotNull(productCategories.get(1));
		
		assertEquals(productCategories.get(0).getName(), "ABC");
		assertEquals(productCategories.get(1).getName(), "ALO");
	}
}
