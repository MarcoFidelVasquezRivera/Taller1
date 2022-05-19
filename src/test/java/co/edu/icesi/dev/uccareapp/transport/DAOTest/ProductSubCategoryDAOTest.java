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
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import co.edu.icesi.dev.uccareapp.transport.daos.IProductSubcategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductCategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductDAO;
import co.edu.icesi.dev.uccareapp.transport.daos.ProductSubcategoryDAO;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductSubcategoryRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Rollback(false)
class ProductSubCategoryDAOTest {

	@Autowired
	private ProductCategoryDAO productCategoryDAO;

	@Autowired
	private ProductSubcategoryDAO productSubcategoryDAO;

	@Autowired
	private ProductSubcategoryRepository productSubcategoryRepository;
	
	@Autowired
	private ProductDAO productDAO;

	@BeforeAll
	public void setup1(){
		Productcategory pc = new Productcategory();
		pc.setName("ABC");
		productCategoryDAO.save(pc);
	}

	@BeforeEach
	public void clear() {
		productSubcategoryRepository.deleteAll();
	}

	@Test
	@Order(1)
	void saveAndFind() {
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));

		productSubcategoryDAO.save(psc);

		Productsubcategory psc2 = productSubcategoryDAO.findById(1);

		assertNotNull(psc2);
		assertEquals(psc2.getName(), psc.getName());
	}

	@Test
	@Order(2)
	void delete() {
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));

		productSubcategoryDAO.save(psc);
		psc = productSubcategoryDAO.findById(2);

		assertNotNull(psc);

		productSubcategoryDAO.delete(psc);
		psc = productSubcategoryDAO.findById(2);

		assertNull(psc);
	}

	@Test
	@Order(3)
	void update() {
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));

		productSubcategoryDAO.save(psc);
		psc = productSubcategoryDAO.findById(3);

		psc.setName("AGUAA");
		productSubcategoryDAO.update(psc);
		psc = productSubcategoryDAO.findById(3);

		assertNotNull(psc);
		assertEquals(psc.getName(), "AGUAA");
	}

	@Test
	@Order(4)
	void findAll() {
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc);

		Productsubcategory psc2 = new Productsubcategory();
		psc2.setName("AGUAA");
		psc2.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc2);


		List<Productsubcategory> list = productSubcategoryDAO.findAll();

		assertNotNull(list);
		assertEquals(list.size(), 2);

		assertNotNull(list.get(0));
		assertNotNull(list.get(1));

		assertEquals(list.get(0).getName(), "ABCDE");
		assertEquals(list.get(1).getName(), "AGUAA");
	}

	@Test
	@Order(5)
	void findByName() {
		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));

		productSubcategoryDAO.save(psc);

		List<Productsubcategory> list = productSubcategoryDAO.findByName("ABCDE");

		assertNotNull(list);
		assertEquals(list.size(), 1);

		assertNotNull(list.get(0));
		assertEquals(list.get(0).getName(), "ABCDE");
	}

	@Test
	@Order(6)
	void findByCategoryId() {
		Productcategory pc = new Productcategory();
		pc.setName("JOJO");
		productCategoryDAO.save(pc);

		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc);

		Productsubcategory psc2 = new Productsubcategory();
		psc2.setName("AGUAA");
		psc2.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc2);

		Productsubcategory psc3 = new Productsubcategory();
		psc3.setName("GUAYABA");
		psc3.setProductcategory(productCategoryDAO.findById(2));
		productSubcategoryDAO.save(psc3);


		List<Productsubcategory> list = productSubcategoryDAO.findByProductCategoryID(1);

		assertNotNull(list);
		assertEquals(list.size(), 2);

		assertNotNull(list.get(0));
		assertNotNull(list.get(1));

		assertEquals(list.get(0).getName(), "ABCDE");
		assertEquals(list.get(1).getName(), "AGUAA");
	}

	@Test
	void findByCategoryAndDates(){
		Productcategory pc = new Productcategory();
		pc.setName("JOJO");
		productCategoryDAO.save(pc);

		Productsubcategory psc = new Productsubcategory();
		psc.setName("ABCDE");
		psc.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc);

		Productsubcategory psc2 = new Productsubcategory();
		psc2.setName("AGUAA");
		psc2.setProductcategory(productCategoryDAO.findById(1));
		productSubcategoryDAO.save(psc2);

		Productsubcategory psc3 = new Productsubcategory();
		psc3.setName("GUAYABA");
		psc3.setProductcategory(productCategoryDAO.findById(2));
		productSubcategoryDAO.save(psc3);
		
		System.out.println(productSubcategoryDAO.findById(10).getName());
		System.out.println(productSubcategoryDAO.findById(11).getName());

		
		Product product = new Product();
		product.setName("colores");
		product.setProductnumber("1234");
		product.setSellstartdate(LocalDate.of(2020, 1,8));
		product.setSellenddate(LocalDate.of(2020, 2, 8));
		product.setSize(1);
		product.setWeight(new BigDecimal(1));
		product.setProductsubcategory(productSubcategoryDAO.findById(10));
		
		Product product2 = new Product();
		product2.setName("macarena");
		product2.setProductnumber("0987");
		product2.setSellstartdate(LocalDate.of(2020, 1,8));
		product2.setSellenddate(LocalDate.of(2020, 6, 8));
		product2.setSize(1);
		product2.setWeight(new BigDecimal(1));
		product2.setProductsubcategory(productSubcategoryDAO.findById(11));
		
		Product product3 = new Product();
		product3.setName("ajolote");
		product3.setProductnumber("3456");
		product3.setSellstartdate(LocalDate.of(2020, 1,8));
		product3.setSellenddate(LocalDate.of(2020, 2, 8));
		product3.setSize(1);
		product3.setWeight(new BigDecimal(1));
		product3.setProductsubcategory(productSubcategoryDAO.findById(10));
		
		productDAO.save(product);
		productDAO.save(product2);
//		productDAO.save(product3);
		
		List<Object[]> list = productSubcategoryDAO.findByCategoryAndDates(1, LocalDate.of(2020, 1,1), LocalDate.of(2020, 3, 8));
		
		assertNotNull(list);
		assertEquals(list.size(), 1);
		assertEquals(list.get(0).length, 2);
		Productsubcategory ps = (Productsubcategory)list.get(0)[0];
//		Product p = (Product) list.get(0)[1]; Al intentar hacer este cast me tira un errir, creo que tiene que ver 
		//con algo de las llaver pero no pude resolverlo
		
		assertEquals(ps.getName(), "ABCDE");
//		assertEquals(, null);
	}

}
