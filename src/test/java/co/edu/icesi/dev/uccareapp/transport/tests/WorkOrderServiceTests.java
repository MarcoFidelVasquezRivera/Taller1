package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.Taller1Application;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Scrapreason;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ScrapReasonRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.WorkOrderRepository;
import co.edu.icesi.dev.uccareapp.transport.services.WorkOrderService;
import co.edu.icesi.dev.uccareapp.transport.services.WorkOrderServiceImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Taller1Application.class)
class WorkOrderServiceTests {

	@Mock
	private WorkOrderRepository workOrderRepository;
	
	@Mock
	private ScrapReasonRepository scrapReasonRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	private WorkOrderService workOrderService;
	
	@BeforeEach
	public void Setup3() {
		workOrderService = new WorkOrderServiceImp(workOrderRepository, scrapReasonRepository, productRepository);
	}
	
	@Nested
	class Save{
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Workorder nulo o que no tenga referencias a un ScrapReason o Product")
		public void Save1() {
			assertThrows(IllegalArgumentException.class, () -> {
				workOrderService.Save(null, null, null);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Workorder cuya fecha de inicio sea mayor a la fecha final")
		public void Save2() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime+172800000));
			workorder.setEnddate(new Timestamp(currentTime));
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			assertThrows(ArithmeticException.class, () -> {
				workOrderService.Save(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Workorder cuya orderqty sea menor a cero")
		public void Save3() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(-1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			assertThrows(NumberFormatException.class, () -> {
				workOrderService.Save(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Workorder cuya scrappedqty sea menor a cero")
		public void Save4() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(-1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			assertThrows(NumberFormatException.class, () -> {
				workOrderService.Save(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Workorder con una referencia a un scrapreason que no existe")
		public void Save5() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				workOrderService.Save(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Workorder con una referencia a un Product que no existe")
		public void Save6() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			Scrapreason sr =  new Scrapreason();
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(Optional.of(sr));
			when(productRepository.findById(productId)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				workOrderService.Save(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que se pueda guardar un Workorder si este cumple con todas las condiciones")
		public void Save7() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			Scrapreason sr =  new Scrapreason();
			sr.setWorkorders(new ArrayList<Workorder>());
			Product pr = new Product();
			pr.setWorkorders(new ArrayList<Workorder>());
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(Optional.of(sr));
			when(productRepository.findById(productId)).thenReturn(Optional.of(pr));
			when(workOrderRepository.save(workorder)).thenReturn(workorder);
			
			Workorder wo = workOrderService.Save(workorder, scrapResonId, productId);

			assertEquals(wo, workorder);
			
			// Verifies behavior happened once
			verify(scrapReasonRepository).findById(scrapResonId);
			verify(productRepository).findById(productId);
			verify(workOrderRepository).save(workorder);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(scrapReasonRepository);
			verifyNoMoreInteractions(productRepository);
			verifyNoMoreInteractions(workOrderRepository);
		}
	}
	
	@Nested
	class Edit{
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Workorder por uno nulo o que no tenga referencias a un ScrapReason o Product")
		public void Edit1() {
			assertThrows(IllegalArgumentException.class, () -> {
				workOrderService.Update(null, null, null);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un Workorder por uno cuya fecha de inicio sea mayor a la fecha final")
		public void Edit2() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime+172800000));
			workorder.setEnddate(new Timestamp(currentTime));
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			assertThrows(ArithmeticException.class, () -> {
				workOrderService.Update(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un Workorder por uno cuya orderqty sea menor a cero")
		public void Edit3() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(-1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			assertThrows(NumberFormatException.class, () -> {
				workOrderService.Update(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un Workorder por uno cuya scrappedqty sea menor a cero")
		public void Edit4() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(-1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			assertThrows(NumberFormatException.class, () -> {
				workOrderService.Update(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un Workorder por uno con una referencia a un scrapreason que no existe")
		public void Edit5() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				workOrderService.Update(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un Workorder por uno con una referencia a un Product que no existe")
		public void Edit6() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			Scrapreason sr =  new Scrapreason();
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(Optional.of(sr));
			when(productRepository.findById(productId)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				workOrderService.Update(workorder, scrapResonId, productId);
			});
		}
		
		@Test
		@DisplayName("Se prueba que se pueda editar un Workorder que no existe")
		public void Edit7() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			Scrapreason sr =  new Scrapreason();
			sr.setWorkorders(new ArrayList<Workorder>());
			Product pr = new Product();
			pr.setWorkorders(new ArrayList<Workorder>());
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(Optional.of(sr));
			when(productRepository.findById(productId)).thenReturn(Optional.of(pr));
			when(workOrderRepository.findById(workorder.getWorkorderid())).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				workOrderService.Update(workorder, scrapResonId, productId);
			});
			
			// Verifies behavior happened once
			verify(scrapReasonRepository).findById(scrapResonId);
			verify(productRepository).findById(productId);
			verify(workOrderRepository).findById(workorder.getWorkorderid());

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(scrapReasonRepository);
			verifyNoMoreInteractions(productRepository);
			verifyNoMoreInteractions(workOrderRepository);
		}
		
		@Test
		@DisplayName("Se prueba que se pueda editar un Workorder si este cumple con todas las condiciones")
		public void Edit8() {
			Workorder workorder = new Workorder();
			Long currentTime = System.currentTimeMillis();
			workorder.setStartdate(new Timestamp(currentTime));
			workorder.setEnddate(new Timestamp(currentTime+172800000));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			
			Integer scrapResonId = 1;
			Integer productId = 2;
			
			Scrapreason sr =  new Scrapreason();
			sr.setWorkorders(new ArrayList<Workorder>());
			sr.addWorkorder(workorder);
			Product pr = new Product();
			pr.setWorkorders(new ArrayList<Workorder>());
			pr.addWorkorder(workorder);
			
			when(scrapReasonRepository.findById(scrapResonId)).thenReturn(Optional.of(sr));
			when(productRepository.findById(productId)).thenReturn(Optional.of(pr));
			when(workOrderRepository.findById(workorder.getWorkorderid())).thenReturn(Optional.of(workorder));
			when(workOrderRepository.save(workorder)).thenReturn(workorder);
			
			Workorder wo = workOrderService.Update(workorder, scrapResonId, productId);

			assertEquals(wo, workorder);
			
			// Verifies behavior happened once
			verify(scrapReasonRepository).findById(scrapResonId);
			verify(productRepository).findById(productId);
			verify(workOrderRepository).findById(workorder.getWorkorderid());
			verify(workOrderRepository).save(workorder);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(scrapReasonRepository);
			verifyNoMoreInteractions(productRepository);
			verifyNoMoreInteractions(workOrderRepository);
		}
	}


}
