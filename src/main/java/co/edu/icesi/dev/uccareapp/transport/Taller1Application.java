package co.edu.icesi.dev.uccareapp.transport;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;
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
	public CommandLineRunner dummy(ProductCategoryServiceImp pcsi, ProductSubcategoryServiceImp pscsi) {
		return (args) -> {
			Productcategory pc = new Productcategory();
			pc.setName("ABC");
			pcsi.Save(pc);
			
			Productsubcategory psc = new Productsubcategory();
			psc.setName("ABCDE");
			pscsi.Save(psc,1);
		};
	}
}
