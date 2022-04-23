package co.edu.icesi.dev.uccareapp.transport;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryServiceImp;

@SpringBootApplication
public class Taller1Application {
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		SpringApplication.run(Taller1Application.class, args);
	}

	@Bean
	public CommandLineRunner dummy(ProductCategoryServiceImp pcsi, ProductSubcategoryServiceImp pscsi, ProductServiceImp psi) {
		return (args) -> {
			Productcategory pc = new Productcategory();
			pc.setName("ABC");
			pcsi.Save(pc);
			
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			pscsi.Save(psc,1);
			
			Product product = new Product();
			Long currentTime = System.currentTimeMillis();
			product.setName("colores");
			product.setProductnumber("1234");
			product.setSellstartdate(LocalDate.of(2020, 1,8));
			product.setSellenddate(LocalDate.of(2020, 2, 8));
			product.setSize(1);
			product.setWeight(new BigDecimal(1));
			
			psi.Save(product, 1, 1);
		};
	}
}
