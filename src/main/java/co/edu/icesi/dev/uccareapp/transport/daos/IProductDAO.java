package co.edu.icesi.dev.uccareapp.transport.daos;

import java.util.List;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;

public interface IProductDAO {
	public void save(Product entity);
    public void update(Product entity);
    public void delete(Product entity);
    public Product findById(Integer codigo);
    public List<Product> findAll();
//    Permita que los productos puedan buscarse por el id de la subcategoría, el
//    modelo del producto y el código de la unidad de medida de tamaño
//    independientemente
    public List<Product> findByProductSubcategoryId(Integer productSubcategoryId);
    
}
