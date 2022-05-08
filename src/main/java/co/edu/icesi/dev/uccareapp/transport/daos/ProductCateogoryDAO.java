package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;

@Repository
@Scope("singleton")
public class ProductCateogoryDAO implements IProductCategoryDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Productcategory entity) {
		// TODO Auto-generated method stub
		entityManager.persist(entity);
		
	}

	@Override
	public void update(Productcategory entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
	}

	@Override
	public void delete(Productcategory entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entity);
	}

	@Override
	public Productcategory findById(Integer codigo) {
		// TODO Auto-generated method stub
		return entityManager.find(Productcategory.class, codigo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productcategory> findAll() {
		// TODO Auto-generated method stub
		String jpql = "SELECT pc FROM Productcategory pc";
		return 	entityManager.createQuery(jpql).getResultList();
	}

}
