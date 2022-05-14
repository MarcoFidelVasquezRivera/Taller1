package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;

public interface IWorkOrderDAO {
	public void save(Workorder entity);
    public void update(Workorder entity);
    public void delete(Workorder entity);
    public Workorder findById(Integer codigo);
    public List<Workorder> findAll();
    public List<Workorder> findByScrapReason(Integer scrapReasonId);
    public List<Workorder> findByOrderqty(Integer orderqty);
    
}
