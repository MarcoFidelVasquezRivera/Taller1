package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;

@Repository
@Scope("singleton")
public class ProductDAO implements IProductDAO{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Product entity) {
		// TODO Auto-generated method stub
		entityManager.persist(entity);
	}

	@Override
	public void update(Product entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
	}

	@Override
	public void delete(Product entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entity);
	}

	@Override
	public Product findById(Integer codigo) {
		// TODO Auto-generated method stub
		return entityManager.find(Product.class, codigo);
	}

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		TypedQuery<Product> query = entityManager.createQuery(
				"SELECT pr FROM Product pr" , Product.class);
		return 	query.getResultList();
	}

	@Override
	public List<Product> findByProductSubcategoryId(Integer productSubcategoryId) {
		// TODO Auto-generated method stub
		TypedQuery<Product> query = entityManager.createQuery(
				"SELECT pr FROM Product pr WHERE pr.productsubcategory.productsubcategoryid = :subcategoryId" , Product.class);
		query.setParameter("subcategoryId", productSubcategoryId);
		return 	query.getResultList();
	}

	@Override
	public List<Product> findByProductModel(Integer productmodelid) {
		// TODO Auto-generated method stub
		TypedQuery<Product> query = entityManager.createQuery(
				"SELECT pr FROM Product pr WHERE pr.productmodel.productmodelid = :modelId" , Product.class);
		query.setParameter("modelId", productmodelid);
		return 	query.getResultList();
	}

	@Override
	public List<Product> findByUnitMeasureCode(String unitmeasurecode) {
		// TODO Auto-generated method stub
		TypedQuery<Product> query = entityManager.createQuery(
				"SELECT pr FROM Product pr WHERE pr.unitmeasure1.unitmeasurecode = :measureId" , Product.class);
		query.setParameter("measureId", unitmeasurecode);
		return 	query.getResultList();
	}

	@Override
	public List<Product> findByNumberOfWorkOrders(Integer Workorderqty) {
		// TODO Auto-generated method stub
		TypedQuery<Product> query = entityManager.createQuery(
				"SELECT pr FROM Product pr WHERE SIZE(pr.workorders) >= :Workorderqty" , Product.class);
		query.setParameter("Workorderqty", Workorderqty);
		return 	query.getResultList();
	}

}
