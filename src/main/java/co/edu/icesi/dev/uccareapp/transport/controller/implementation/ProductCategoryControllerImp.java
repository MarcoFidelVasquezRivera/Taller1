package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.ProductCategoryController;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.services.ProductCategoryServiceImp;

@Controller
public class ProductCategoryControllerImp implements ProductCategoryController{
	@Autowired
	ProductCategoryServiceImp productCategoryService;
	
	@GetMapping("/login")
	public String login(Model model) {
		return "/login";
	}
	
	@GetMapping("/categories/")
	public String indexUser(Model model) {
		model.addAttribute("categories", productCategoryService.findAll());
		return "categories/index-categories";
	}
	
	@GetMapping("/categories/add")
	public String addUser(Model model) {
		model.addAttribute("productcategory", new Productcategory());
		return "categories/add-category";
	}

	@GetMapping("/categories/del/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		Productcategory productCategory = productCategoryService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		productCategoryService.delete(productCategory);
		model.addAttribute("categories", productCategoryService.findAll());
		return "categories/index-categories";
	}


	@PostMapping("/categories/add")
	public String saveUser(@Validated @ModelAttribute Productcategory pc, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "categories/add-category";
			}
			productCategoryService.Save(pc);
		}
		return "redirect:/categories/";
	}

	@GetMapping("/categories/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Productcategory> pc = productCategoryService.findById(id);
		if (pc == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("productcategory", pc.get());
		return "categories/update-category";
	}

	@PostMapping("/categories/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Productcategory pc, BindingResult bindingResult,@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				return "categories/update-category";
			}
			productCategoryService.Update(pc);
			model.addAttribute("categories", productCategoryService.findAll());
		}
		return "redirect:/categories/";
	}
}
