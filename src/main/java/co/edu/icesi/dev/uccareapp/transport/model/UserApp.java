package co.edu.icesi.dev.uccareapp.transport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class UserApp {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	
	@NotNull
	private UserType type;
}
