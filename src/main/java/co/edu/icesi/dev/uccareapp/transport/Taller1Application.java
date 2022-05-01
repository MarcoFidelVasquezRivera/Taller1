package co.edu.icesi.dev.uccareapp.transport;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;
import co.edu.icesi.dev.uccareapp.transport.model.UserType;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.UserRepository;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.WorkOrderServiceImp;

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
	public CommandLineRunner dummy(ProductCategoryServiceImp pcsi, ProductSubcategoryServiceImp pscsi, ProductServiceImp psi, WorkOrderServiceImp wos, UserRepository ur) {
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
			
			Workorder workorder = new Workorder();
			currentTime = System.currentTimeMillis();
			workorder.setStartdate(LocalDate.of(2020, 1,8));
			workorder.setEnddate(LocalDate.of(2020, 2, 8));
			workorder.setOrderqty(1);
			workorder.setScrappedqty(1);
			workorder.setWorkorderid(1);
			
			wos.Save(workorder,null, 1);
			
			UserApp user = new UserApp();
			user.setName("admin");
			user.setPassword("{noop}admin");
			user.setType(UserType.ADMIN);
			ur.save(user);
			
			UserApp user2 = new UserApp();
			user2.setName("operator");
			user2.setPassword("{noop}operator");
			user2.setType(UserType.OPERATOR);
			ur.save(user2);
		};
	}
}
