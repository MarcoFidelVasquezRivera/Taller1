package co.edu.icesi.dev.uccareapp.transport.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Scrapreason;

@Repository
public interface ScrapReasonRepository extends CrudRepository<Scrapreason, Integer>{

}
