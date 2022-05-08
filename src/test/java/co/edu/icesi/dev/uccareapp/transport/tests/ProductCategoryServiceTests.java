package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import co.edu.icesi.dev.uccareapp.transport.Taller1Application;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Taller1Application.class)
class ProductCategoryServiceTests {

	@Mock
	private ProductCategoryRepository productCategoryRepository;
	
	private ProductCategoryService productCategoryService;
	
	@BeforeEach
	public void  Setup1() {
		productCategoryService = new ProductCategoryServiceImp(productCategoryRepository);
	}
	
	@Nested
	class Save{
		@Test
		@DisplayName("Se prueba que no sea posible guardar un objeto nulo")
		public void Save1() {
			assertThrows(IllegalArgumentException.class, () -> {
				productCategoryService.Save(null);
		    });
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un objeto cuyo nombre tenga menos de tres letras")
		public void Save2() {
			assertThrows(NumberFormatException.class, () -> {
				Productcategory pc = new Productcategory();
				pc.setName("AB");
				productCategoryService.Save(pc);
		    });
		}
		
		@Test
		@DisplayName("Se prueba que el objeto se pueda guardar correctamente si no es nulo y su nombre tiene tres caracteres o mas")
		public void Save3() {
			Productcategory pc = new Productcategory();
			pc.setName("ABC");
			
			when(productCategoryRepository.save(pc)).thenReturn(pc);
			
			Productcategory productcategory = productCategoryService.Save(pc);
			assertSame(pc, productcategory);
			
			// Verifies behavior happened once
			verify(productCategoryRepository).save(pc);
			
			// asserts that during the test, there are no other
			 verifyNoMoreInteractions(productCategoryRepository);
			
		}
		
		
	}
	
	@Nested
	class Edit{
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un objeto por otro un objeto nulo")
		public void Edit1() {
			assertThrows(IllegalArgumentException.class, () -> {
				productCategoryService.Update(null);
		    });
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible editar un objeto por otro cuyo nombre tenga menos de tres letras")
		public void Edit2() {
			assertThrows(NumberFormatException.class, () -> {
				Productcategory pc = new Productcategory();
				pc.setName("AB");
				productCategoryService.Update(pc);
		    });
		}
		
		@Test
		@DisplayName("Se prueba que el objeto se pueda guardar correctamente si no es nulo y su nombre tiene tres caracteres o mas")
		public void Edit3() {
			Optional<Productcategory> pc = Optional.of(new Productcategory());
			pc.get().setName("ABC");
			pc.get().setProductcategoryid(1234);
			
			when(productCategoryRepository.save(pc.get())).thenReturn(pc.get());
			when(productCategoryRepository.findById(pc.get().getProductcategoryid())).thenReturn(pc);
			
			Productcategory productcategory = productCategoryService.Update(pc.get());
			assertSame(pc.get().getProductcategoryid(), productcategory.getProductcategoryid());
			
			// Verifies behavior happened once
			verify(productCategoryRepository).save(pc.get());
			verify(productCategoryRepository).findById(pc.get().getProductcategoryid());
			
			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			
		}
		
		@Test
		@DisplayName("Se prueba que pueda ser editado ya que no existe un objeto guardado con el mismo id")
		public void Edit4() {
			Optional<Productcategory> pc = Optional.of(new Productcategory());
			pc.get().setName("ABC");
			pc.get().setProductcategoryid(1234);
			
			when(productCategoryRepository.findById(pc.get().getProductcategoryid())).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				productCategoryService.Update(pc.get());
		    });
			
			// Verifies behavior happened once
			verify(productCategoryRepository).findById(pc.get().getProductcategoryid());
			
			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			
		}
	}

	
	

}
