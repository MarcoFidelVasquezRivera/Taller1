package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;

@Repository
@Scope("singleton")
@Transactional
public class WorkOrderDAO implements IWorkOrderDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Workorder entity) {
		// TODO Auto-generated method stub
		entityManager.persist(entity);
	}

	@Override
	public void update(Workorder entity) {
		// TODO Auto-generated method stub
		entityManager.merge(entity);
	}

	@Override
	public void delete(Workorder entity) {
		// TODO Auto-generated method stub
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	@Override
	public Workorder findById(Integer codigo) {
		// TODO Auto-generated method stub
		return entityManager.find(Workorder.class, codigo);
	}

	@Override
	public List<Workorder> findAll() {
		// TODO Auto-generated method stub
		TypedQuery<Workorder> query = entityManager.createQuery(
				"SELECT wo FROM Workorder wo" , Workorder.class);
		return 	query.getResultList();
	}

	@Override
	public List<Workorder> findByScrapReason(Integer scrapReasonId) {
		// TODO Auto-generated method stub
		TypedQuery<Workorder> query = entityManager.createQuery(
				"SELECT wo FROM Workorder wo WHERE wo.scrapreason.scrapreasonid = :scrapId" , Workorder.class);
		query.setParameter("scrapId", scrapReasonId);
		return 	query.getResultList();
	}

	@Override
	public List<Workorder> findByOrderqty(Integer orderqty) {
		// TODO Auto-generated method stub
		TypedQuery<Workorder> query = entityManager.createQuery(
				"SELECT wo FROM Workorder wo WHERE wo.orderqty = :orderqty" , Workorder.class);
		query.setParameter("orderqty", orderqty);
		return 	query.getResultList();
	}

}
