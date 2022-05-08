package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;

public interface IProductCategoryDAO {
	public void save(Productcategory entity);
    public void update(Productcategory entity);
    public void delete(Productcategory entity);
    public Productcategory findById(Integer codigo);
    public List<Productcategory> findAll();
}
