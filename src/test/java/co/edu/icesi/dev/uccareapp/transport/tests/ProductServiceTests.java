package co.edu.icesi.dev.uccareapp.transport.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductCategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductSubcategoryRepository;
import co.edu.icesi.dev.uccareapp.transport.services.ProductService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductServiceImp;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = Taller1Application.class)
class ProductServiceTests {
	
	@Mock
	private ProductCategoryRepository productCategoryRepository;
	
	@Mock
	private ProductSubcategoryRepository productSubcategoryRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	private ProductService productService;
	
	@BeforeEach
	public void Setup4() {
		this.productService = new ProductServiceImp(productCategoryRepository, productSubcategoryRepository, productRepository);
	}
	
	@Nested
	class Save{
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product nulo o con referencias nulas")
		public void Save1() {
			assertThrows(IllegalArgumentException.class, () -> {
				productService.Save(null, null, null);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product cuyo productNumber es nulo")
		public void Save2() {
			Product product = new Product();
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(NullPointerException.class, () -> {
				productService.Save(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product con fecha de inicio de venta mayor a la de finalizacion de las ventas")
		public void Save3() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime+172800000));
			product.setSellenddate(new Timestamp(currentTime));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(ArithmeticException.class, () -> {
				productService.Save(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product con tamaño menor a cero")
		public void Save4() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(-1);
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(NumberFormatException.class, () -> {
				productService.Save(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product con peso menor a cero")
		public void Save5() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(-1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(NumberFormatException.class, () -> {
				productService.Save(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product con una referencia a una categoria que no existe")
		public void Save6() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				productService.Save(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible guardar un Product con una referencia a una subcategoria que no existe")
		public void Save7() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			Productcategory productCategory = new Productcategory();
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(Optional.of(productCategory));
			when(productSubcategoryRepository.findById(subcategoryID)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				productService.Save(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que sea posible guardar un Product cuando este cumpla con todas las condiciones")
		public void Save8() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			Productcategory productCategory = new Productcategory();
			Productsubcategory productsubcategory = new Productsubcategory();
			productsubcategory.setProducts(new ArrayList<Product>());
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(Optional.of(productCategory));
			when(productSubcategoryRepository.findById(subcategoryID)).thenReturn(Optional.of(productsubcategory));
			when(productRepository.save(product)).thenReturn(product);
			
			Product p = productService.Save(product, categoryID, subcategoryID);
			assertEquals(p, product);
			
			// Verifies behavior happened once
			verify(productCategoryRepository).findById(categoryID);
			verify(productSubcategoryRepository).findById(subcategoryID);
			verify(productRepository).save(product);

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			verifyNoMoreInteractions(productSubcategoryRepository);
			verifyNoMoreInteractions(productRepository);
		}
		
	}
	
	@Nested
	class Edit {
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product or uno nulo o con referencias nulas")
		public void Edit1() {
			assertThrows(IllegalArgumentException.class, () -> {
				productService.Update(null, null, null);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product por uno cuyo productNumber es nulo")
		public void Edit2() {
			Product product = new Product();
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(NullPointerException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product por uno con fecha de inicio de venta mayor a la de finalizacion de las ventas")
		public void Edit3() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime+172800000));
			product.setSellenddate(new Timestamp(currentTime));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(ArithmeticException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product por uno con tamaño menor a cero")
		public void Edit4() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(-1);
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(NumberFormatException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product por uno con peso menor a cero")
		public void Edit5() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(-1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			assertThrows(NumberFormatException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product por uno con una referencia a una categoria que no existe")
		public void Edit6() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product por uno con una referencia a una subcategoria que no existe")
		public void Edit7() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			Productcategory productCategory = new Productcategory();
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(Optional.of(productCategory));
			when(productSubcategoryRepository.findById(subcategoryID)).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
		}
		
		@Test
		@DisplayName("Se prueba que no sea posible actualizar un Product que no exite")
		public void Edit8() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			Productcategory productCategory = new Productcategory();
			Productsubcategory productsubcategory = new Productsubcategory();
			productsubcategory.setProducts(new ArrayList<Product>());
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(Optional.of(productCategory));
			when(productSubcategoryRepository.findById(subcategoryID)).thenReturn(Optional.of(productsubcategory));
			when(productRepository.findById(product.getProductid())).thenReturn(null);
			
			assertThrows(NullPointerException.class, () -> {
				productService.Update(product, categoryID, subcategoryID);
			});
			
			// Verifies behavior happened once
			verify(productCategoryRepository).findById(categoryID);
			verify(productSubcategoryRepository).findById(subcategoryID);
			verify(productRepository).findById(product.getProductid());

			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			verifyNoMoreInteractions(productSubcategoryRepository);
			verifyNoMoreInteractions(productRepository);
		}
		
		@Test
		@DisplayName("Se prueba que sea posible actualizar un Product por otro cuando este cumpla con todas las condiciones")
		public void Edit9() {
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setProductnumber("1234");
			product.setSellstartdate(new Timestamp(currentTime));
			product.setSellenddate(new Timestamp(currentTime+172800000));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));

			
			Integer categoryID = 1;
			Integer subcategoryID = 2;
			
			Productcategory productCategory = new Productcategory();
			Productsubcategory productsubcategory = new Productsubcategory();
			productsubcategory.setProducts(new ArrayList<Product>());
			product.setProductsubcategory(productsubcategory);
			
			when(productCategoryRepository.findById(categoryID)).thenReturn(Optional.of(productCategory));
			when(productSubcategoryRepository.findById(subcategoryID)).thenReturn(Optional.of(productsubcategory));
			when(productRepository.findById(product.getProductid())).thenReturn(Optional.of(product));
			when(productRepository.save(product)).thenReturn(product);
			
			Product p = productService.Update(product, categoryID, subcategoryID);
			assertEquals(p, product);
			
			// Verifies behavior happened once
			verify(productCategoryRepository).findById(categoryID);
			verify(productSubcategoryRepository).findById(subcategoryID);
			verify(productRepository).findById(product.getProductid());
			verify(productRepository).save(product);
			
			// asserts that during the test, there are no other
			verifyNoMoreInteractions(productCategoryRepository);
			verifyNoMoreInteractions(productSubcategoryRepository);
			verifyNoMoreInteractions(productRepository);
		}
	}
	
	


}
