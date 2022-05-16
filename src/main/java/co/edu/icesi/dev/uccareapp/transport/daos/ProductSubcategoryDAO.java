package co.edu.icesi.dev.uccareapp.transport.daos;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;

@Repository
@Scope("singleton")
@Transactional
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
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	@Override
	public Productsubcategory findById(Integer codigo) {
		// TODO Auto-generated method stub
		return entityManager.find(Productsubcategory.class, codigo);
	}

	@Override
	public List<Productsubcategory> findAll() {
		// TODO Auto-generated method stub
		TypedQuery<Productsubcategory> query = entityManager.createQuery(
				"SELECT psc FROM Productsubcategory psc" , Productsubcategory.class);
		return 	query.getResultList();
	}

	@Override
	public List<Productsubcategory> findByProductCategoryID(Integer productCategoryId) {
		// TODO Auto-generated method stub
		TypedQuery<Productsubcategory> query = entityManager.createQuery(
				"SELECT psc FROM Productsubcategory psc WHERE psc.productcategory.productcategoryid = :categoryId" , Productsubcategory.class);
		query.setParameter("categoryId", productCategoryId);
		return 	query.getResultList();
	}


	@Override
	public List<Productsubcategory> findByName(String name) {
		// TODO Auto-generated method stub
		TypedQuery<Productsubcategory> query = entityManager.createQuery(
				"SELECT psc FROM Productsubcategory psc WHERE psc.name = :name" , Productsubcategory.class);
		query.setParameter("name", name);
		return 	query.getResultList();

	}

	@Override
	public List<Object[]> findByCategoryAndDates(Integer productCategoryId, LocalDate sellstartdate,
			LocalDate sellenddate) {
//	    La(s) subcategoría (s) con sus datos y cantidad de productos (que iniciaron a venderse
//		en rango de fechas dadas), ordenados por el nombre. Recibe como parámetro una
//		categoría de productos y retorna todas las subcategorías que cumplen con tener al
//		menos una un producto en las fechas dadas.
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(
				"SELECT psc,psc.products FROM Productsubcategory psc WHERE psc.productcategory.productcategoryid = :categoryId "
				+ "AND psc.productsubcategoryid = "
				+ "(SELECT pr.productsubcategory.productsubcategoryid FROM Product pr WHERE pr.sellstartdate>=:sellstartdate AND pr.sellenddate<=:sellenddate)" 
				);
		query.setParameter("categoryId", productCategoryId);
		query.setParameter("sellstartdate", sellstartdate);
		query.setParameter("sellenddate", sellenddate);
		return 	query.getResultList();
	}

}
