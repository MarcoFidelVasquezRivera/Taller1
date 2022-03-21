package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

	
	

}
