package co.edu.icesi.dev.uccareapp.transport.DAOTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.icesi.dev.uccareapp.transport.daos.IWorkOrderDAO;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkOrderDAOTest {

	@Autowired
	private IWorkOrderDAO workOrderDAO;
	
	@Test
	@Order(1)
	void saveAndFind() {
		Workorder workorder = new Workorder();
		workorder.setStartdate(LocalDate.of(2020, 1,8));
		workorder.setEnddate(LocalDate.of(2020, 2, 8));
		workorder.setOrderqty(1);
		workorder.setScrappedqty(1);
		
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(1);
		
		assertNotNull(workorder.getOrderqty());
		assertEquals(1, workorder.getScrappedqty());
		assertEquals(1, workorder);
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
		
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(1);
		
		workorder.setOrderqty(20);
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(1);
		
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
		
		workOrderDAO.save(workorder);
		workorder = workOrderDAO.findById(1);
		
		assertNotNull(workorder);
		workOrderDAO.delete(workorder);
		workorder = workOrderDAO.findById(1);
		
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

		Workorder workorder2 = new Workorder();
		workorder2.setStartdate(LocalDate.of(2020, 2,8));
		workorder2.setEnddate(LocalDate.of(2020, 3, 8));
		workorder2.setOrderqty(15);
		workorder2.setScrappedqty(8);
		
		workOrderDAO.save(workorder);
		workOrderDAO.save(workorder2);
		
		List<Workorder> list = workOrderDAO.findAll();
		
		assertNotNull(list);
		assertEquals(list.size(), 2);
		
		assertNotNull(list.get(0));
		assertNotNull(list.get(1));
		
		Workorder wo1 = list.get(0);
		Workorder wo2 = list.get(0);
		
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
		fail("Not yet implemented");
	}
	
	@Test
	@Order(6)
	void findByOrderqty() {
		fail("Not yet implemented");
	}
	
}
