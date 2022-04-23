package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.ProductController;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductService;
import co.edu.icesi.dev.uccareapp.transport.services.ProductServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryServiceImp;

@Controller
public class ProductControllerImp implements ProductController{
	@Autowired
	ProductSubcategoryServiceImp productSubCategoryService;
	
	@Autowired
	ProductCategoryServiceImp productCategoryService;
	
	@Autowired
	ProductServiceImp productService;
	
	@GetMapping("/products/")
	public String indexUser(Model model) {
		model.addAttribute("products", productService.findAll());
		return "products/index-products";
	}
	
	@GetMapping("/products/add")
	public String addUser(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("subcategories", productSubCategoryService.findAll());
		return "products/add-product";
	}

	@GetMapping("/products/del/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		Product product = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		productService.delete(product);
		model.addAttribute("products", productService.findAll());
		return "products/index-products";
	}


	@PostMapping("/products/add")
	public String saveUser(Product product, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel"))
			productService.Save(product, product.getProductsubcategory().getProductcategory().getProductcategoryid(), product.getProductsubcategory().getProductsubcategoryid());
		return "redirect:/products/";
	}

	@GetMapping("/products/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Product> product = productService.findById(id);
		if (product == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		
		model.addAttribute("product", product.get());
		model.addAttribute("subcategories", productSubCategoryService.findAll());
		return "products/update-product";
	}

	@PostMapping("/products/edit/{id}")
	public String updateUser(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,Product product, Model model) {
		if (action != null && !action.equals("Cancel")) {
			productService.Update(product, product.getProductsubcategory().getProductcategory().getProductcategoryid(), product.getProductsubcategory().getProductsubcategoryid());
			model.addAttribute("products", productService.findAll());
		}
		return "redirect:/products/";
	}
}
