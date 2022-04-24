package co.edu.icesi.dev.uccareapp.transport.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.icesi.dev.uccareapp.transport.model.UserApp;

public interface UserRepository extends CrudRepository<UserApp, Integer> {
	Optional<UserApp> findByName(String name);
}
