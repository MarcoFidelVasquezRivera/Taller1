package co.edu.icesi.dev.uccareapp.transport.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;

@Repository
public interface ProductSubcategoryRepository extends CrudRepository<Productsubcategory, Integer>{

}
