package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;

public interface IProductSubcategoryDAO {
	public void save(Productsubcategory entity);
    public void update(Productsubcategory entity);
    public void delete(Productsubcategory entity);
    public Productsubcategory findById(Integer codigo);
    public List<Productsubcategory> findAll();
    public List<Productsubcategory> findByProductCategoryID(Integer productCategoryId);
    public List<Productsubcategory> findByName(String Name);
}
