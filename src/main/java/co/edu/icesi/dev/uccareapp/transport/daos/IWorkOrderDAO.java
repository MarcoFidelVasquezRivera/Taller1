package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;

public interface IWorkOrderDAO {
	public void save(Workorder entity);
    public void update(Workorder entity);
    public void delete(Workorder entity);
    public Workorder findById(Integer codigo);
    public List<Workorder> findAll();
//    Permita que las órdenes de producto se puedan buscar por id del motivo del
//    desecho y la cantidad de la orden independientemente.  Orderqty
    public List<Workorder> findByOrderqty(Integer orderqty);
    
}
