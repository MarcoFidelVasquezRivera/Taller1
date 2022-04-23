package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.ProductSubcategoryController;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.ProductSubcategoryServiceImp;

@Controller
public class ProductSubcategoryControllerImp implements ProductSubcategoryController{
	@Autowired
	ProductSubcategoryServiceImp productSubCategoryService;
	
	@Autowired
	ProductCategoryServiceImp productCategoryService;
	
	@GetMapping("/subcategories/")
	public String indexUser(Model model) {
		model.addAttribute("subcategories", productSubCategoryService.findAll());
		return "subcategories/index-subcategories";
	}
	
	@GetMapping("/subcategories/add")
	public String addUser(Model model) {
		model.addAttribute("subcategory", new Productsubcategory());
		model.addAttribute("categories", productCategoryService.findAll());
		return "subcategories/add-subcategory";
	}

	@GetMapping("/subcategories/del/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		Productsubcategory productSubcategory = productSubCategoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		productSubCategoryService.delete(productSubcategory);
		model.addAttribute("subcategories", productSubCategoryService.findAll());
		return "subcategories/index-subcategories";
	}


	@PostMapping("/subcategories/add")
	public String saveUser(Productsubcategory psc, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel"))
			productSubCategoryService.Save(psc,psc.getProductcategory().getProductcategoryid());
		return "redirect:/subcategories/";
	}

	@GetMapping("/subcategories/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Productsubcategory> psc = productSubCategoryService.findById(id);
		if (psc == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("subcategory", psc.get());
		model.addAttribute("categories", productCategoryService.findAll());
		return "subcategories/update-subcategory";
	}

	@PostMapping("/subcategories/edit/{id}")
	public String updateUser(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,Productsubcategory psc, Model model) {
		if (action != null && !action.equals("Cancel")) {
			productSubCategoryService.Update(psc,psc.getProductcategory().getProductcategoryid());
			model.addAttribute("subcategories", productSubCategoryService.findAll());
		}
		return "redirect:/subcategories/";
	}
}
