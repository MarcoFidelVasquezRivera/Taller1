package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

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
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductSubcategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryServiceImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Taller1Application.class)
class ProductSubcategoryServiceTests {

	@Mock
	private ProductCategoryRepository productCategoryRepository;
	@Mock
	private ProductSubcategoryRepository productSubcategoryRepository;

	private ProductSubcategoryService productSubcategoryService;

	@BeforeEach
	public void setup2() {
		productSubcategoryService = new ProductSubcategoryServiceImp(productSubcategoryRepository, productCategoryRepository);
	}

	@Nested
	class Save{
		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory nulo")
		public void Save1() {
			assertThrows(IllegalArgumentException.class, () -> {
				productSubcategoryService.Save(null, null);
			});
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory cuyo nombre tenga una longitud menor a 5 caracteres")
		public void Save2() {
			assertThrows(NumberFormatException.class, () -> {
				Productsubcategory psc = new Productsubcategory();
				psc.setName("ABCD");
				productSubcategoryService.Save(psc, null);
			});
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory que no tenga referencia a un Productcategory")
		public void Save3() {
			assertThrows(UnsupportedOperationException.class, () -> {
				Productsubcategory psc = new Productsubcategory();
				psc.setName("ABCDE");
				productSubcategoryService.Save(psc, null);
			});
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory que no tenga referencia a un Productcategory")
		public void Save4() {
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			Integer id = 1;

			when(productCategoryRepository.findById(id)).thenReturn(null);

			assertThrows(NullPointerException.class, () -> {
				productSubcategoryService.Save(psc, id);
			});

			// Verifies behavior happened once
			verify(productCategoryRepository).findById(id);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory que no tenga referencia a un Productcategory")
		public void Save5() {
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			Integer id = 1;

			Productcategory pc = new Productcategory();
			pc.setName("ABC");
			pc.setProductcategoryid(id);
			pc.setProductsubcategories(new ArrayList<Productsubcategory>());

			when(productCategoryRepository.findById(id)).thenReturn(Optional.of(pc));
			when(productSubcategoryRepository.save(psc)).thenReturn(psc);

			Productsubcategory savedpsc = productSubcategoryService.Save(psc, id);

			assertEquals(psc, savedpsc);

			// Verifies behavior happened once
			verify(productCategoryRepository).findById(id);
			verify(productSubcategoryRepository).save(psc);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			verifyNoMoreInteractions(productSubcategoryRepository);
		}

	}
	
	@Nested
	class Edit{
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un ProductSubcategory por uno nulo")
		public void Edit1() {
			assertThrows(IllegalArgumentException.class, () -> {
				productSubcategoryService.Update(null, null);
			});
		}

		@Test
		@DisplayName("Se prueba que no sea posible actualizar un ProductSubcategory por otro cuyo nombre tenga una longitud menor a 5 caracteres")
		public void Edit2() {
			assertThrows(NumberFormatException.class, () -> {
				Productsubcategory psc = new Productsubcategory();
				psc.setName("ABCD");
				productSubcategoryService.Update(psc, null);
			});
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory por uno que no tenga referencia a un Productcategory")
		public void Edit3() {
			assertThrows(UnsupportedOperationException.class, () -> {
				Productsubcategory psc = new Productsubcategory();
				psc.setName("ABCDE");
				productSubcategoryService.Update(psc, null);
			});
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory que no tenga referencia a un Productcategory")
		public void Edit4() {
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			Integer id = 1;

			when(productCategoryRepository.findById(id)).thenReturn(null);

			assertThrows(NullPointerException.class, () -> {
				productSubcategoryService.Update(psc, id);
			});

			// Verifies behavior happened once
			verify(productCategoryRepository).findById(id);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
		}

		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory que no tenga referencia a un Productcategory")
		public void Edit5() {
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			psc.setProductsubcategoryid(20);
			Integer id = 1;

			Productcategory pc = new Productcategory();
			pc.setName("ABC");
			pc.setProductcategoryid(id);
			pc.setProductsubcategories(new ArrayList<Productsubcategory>());

			when(productCategoryRepository.findById(id)).thenReturn(Optional.of(pc));
			when(productSubcategoryRepository.findById(psc.getProductsubcategoryid())).thenReturn(null);

			assertThrows(NullPointerException.class, () -> {
				productSubcategoryService.Update(psc, id);
			});

			// Verifies behavior happened once
			verify(productCategoryRepository).findById(id);
			verify(productSubcategoryRepository).findById(psc.getProductsubcategoryid());

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			verifyNoMoreInteractions(productSubcategoryRepository);
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un ProductSubcategory que no tenga referencia a un Productcategory")
		public void Edit6() {
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			Integer id = 1;

			Productcategory pc = new Productcategory();
			pc.setName("ABC");
			pc.setProductcategoryid(id);
			pc.setProductsubcategories(new ArrayList<Productsubcategory>());
			pc.addProductsubcategory(psc);

			when(productCategoryRepository.findById(id)).thenReturn(Optional.of(pc));
			when(productSubcategoryRepository.findById(psc.getProductsubcategoryid())).thenReturn(Optional.of(psc));
			when(productSubcategoryRepository.save(psc)).thenReturn(psc);

			Productsubcategory savedpsc = productSubcategoryService.Update(psc, id);

			assertEquals(psc, savedpsc);

			// Verifies behavior happened once
			verify(productCategoryRepository).findById(id);
			verify(productSubcategoryRepository).findById(psc.getProductsubcategoryid());
			verify(productSubcategoryRepository).save(psc);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			verifyNoMoreInteractions(productSubcategoryRepository);
		}

	}


}
