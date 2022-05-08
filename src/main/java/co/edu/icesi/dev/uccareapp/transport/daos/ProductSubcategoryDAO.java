package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;

@Repository
@Scope("singleton")
public class ProductSubcategoryDAO implements IProductSubcategoryDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Productsubcategory entity) {
		// TODO Auto-generated method stub
		entityManager.persist(entity);
	}

	@Override
	public void update(Productsubcategory entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
	}

	@Override
	public void delete(Productsubcategory entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entity);
	}

	@Override
	public Productsubcategory findById(Integer codigo) {
		// TODO Auto-generated method stub
		return entityManager.find(Productsubcategory.class, codigo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productsubcategory> findAll() {
		// TODO Auto-generated method stub
		String jpql = "SELECT psc FROM Productsubcategory psc";
		return 	entityManager.createQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productsubcategory> findByProductCategoryID(Integer productCategoryId) {
		// TODO Auto-generated method stub
		String jpql = "SELECT psc FROM Productsubcategory pc WHERE psc.productcategory.productcategoryid = :categoryId";
		Query query= entityManager.createQuery(jpql);
		query.setParameter("categoryId", productCategoryId);
		return 	query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productsubcategory> findByName(String name) {
		// TODO Auto-generated method stub
		String jpql = "SELECT psc FROM Productsubcategory pc WHERE psc.name = :name";
		Query query= entityManager.createQuery(jpql);
		query.setParameter("name", name);
		return 	query.getResultList();
	}

}
