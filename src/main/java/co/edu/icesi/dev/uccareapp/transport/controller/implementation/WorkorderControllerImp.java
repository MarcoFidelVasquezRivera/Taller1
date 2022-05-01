package co.edu.icesi.dev.uccareapp.transport.controller.implementation;

import java.util.ArrayList;
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

import co.edu.icesi.dev.uccareapp.transport.controller.interfaces.WorkorderController;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Productsubcategory;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.services.ProductServiceImp;
import co.edu.icesi.dev.uccareapp.transport.services.WorkOrderServiceImp;

@Controller
public class WorkorderControllerImp implements WorkorderController{
	@Autowired
	ProductServiceImp productService;
	
	@Autowired
	WorkOrderServiceImp wordOrderService;
	
	@GetMapping("/workorders/")
	public String indexUser(Model model) {
		model.addAttribute("workorders", wordOrderService.findAll());
		return "workorders/index-workorder";
	}
	
	@GetMapping("/workorders/add")
	public String addUser(Model model) {
		model.addAttribute("workorder", new Workorder());
		model.addAttribute("products", productService.findAll());
		return "workorders/add-workorder";
	}

	@GetMapping("/workorders/del/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		Workorder workorder = wordOrderService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		wordOrderService.delete(workorder);
		model.addAttribute("workorders", wordOrderService.findAll());
		return "workorders/index-workorder";
	}


	@PostMapping("/workorders/add")
	public String saveUser(@Validated @ModelAttribute Workorder wo, BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			model.addAttribute("products", productService.findAll());
			if (bindingResult.hasErrors()) {	
				return "workorders/add-workorder";
			}else if(wo.getEnddate().isBefore(wo.getStartdate())) {
				model.addAttribute("dateError", true);
				return "workorders/add-workorder";
			}
			wordOrderService.Save(wo,null,wo.getProduct().getProductid());
		}
		return "redirect:/workorders/";
	}
	
	@GetMapping("/workorders/product/{id}")
	public String showProduct(@PathVariable("id") Integer id, Model model) {
		Product product = productService.findById(id).get();
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(product);
		model.addAttribute("products", products);
		return "products/index-products";
	}

	@GetMapping("/workorders/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Optional<Workorder> wo = wordOrderService.findById(id);
		if (wo == null)
			throw new IllegalArgumentException("Invalid user Id:" + id);
		model.addAttribute("workorder", wo.get());
		model.addAttribute("products", productService.findAll());
		return "workorders/update-workorder";
	}

	@PostMapping("/workorders/edit/{id}")
	public String updateUser(@Validated @ModelAttribute Workorder wo, BindingResult bindingResult,@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action, Model model) {
		if (action != null && !action.equals("Cancel")) {
			model.addAttribute("products", productService.findAll());
			if (bindingResult.hasErrors()) {	
				return "workorders/update-workorder";
			}else if(wo.getEnddate().isBefore(wo.getStartdate())) {
				model.addAttribute("dateError", true);
				return "workorders/update-workorder";
			}
			wordOrderService.Update(wo,null,wo.getProduct().getProductid());
			model.addAttribute("workorders", wordOrderService.findAll());
		}
		return "redirect:/workorders/";
	}
}
