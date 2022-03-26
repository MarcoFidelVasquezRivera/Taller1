package co.edu.icesi.dev.uccareapp.transport.services;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Product;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Scrapreason;
import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;
import co.edu.icesi.dev.uccareapp.transport.repositories.ProductRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.ScrapReasonRepository;
import co.edu.icesi.dev.uccareapp.transport.repositories.WorkOrderRepository;

public class WorkOrderServiceImp implements WorkOrderService{

	private WorkOrderRepository workOrderRepository;
	
	private ScrapReasonRepository scrapReasonRepository;
	
	private ProductRepository productRepository;
	
	@Autowired
	public WorkOrderServiceImp(WorkOrderRepository workOrderRepository, ScrapReasonRepository scrapReasonRepository, ProductRepository productRepository) {
		this.workOrderRepository = workOrderRepository;
		this.scrapReasonRepository = scrapReasonRepository;
		this.productRepository = productRepository;
	}
	
	public Workorder Save(Workorder workorder, Integer scrapReasonId, Integer productId) {
		if(Objects.isNull(workorder) || Objects.isNull(scrapReasonId) || Objects.isNull(productId)) {
			throw new IllegalArgumentException("None of the given parameters can be null");
		}
		
		Timestamp startDate = workorder.getStartdate();
		Timestamp endDate = workorder.getEnddate();
		
		if(startDate.after(endDate)) {
			throw new ArithmeticException("Start date can not be after the end date, it must be before the endDate");
		}
		
		if(workorder.getOrderqty()<0 || workorder.getScrappedqty()<0) {
			throw new NumberFormatException("Start date can not be after the end date, it must be before the endDate");
		}
		
		Optional<Scrapreason> osr = scrapReasonRepository.findById(scrapReasonId);
		Optional<Product> op = productRepository.findById(productId);
		
		if(osr.isEmpty()) {
			throw new NullPointerException("no existe un Scrapreason con el id especificado");
		}
		
		if(op.isEmpty()) {
			throw new NullPointerException("no existe un Product con el id especificado");
		}
		
		Scrapreason sr = osr.get();
		Product pr = op.get();
		
		sr.addWorkorder(workorder);
		pr.addWorkorder(workorder);
		
		workorder.setScrapreason(sr);
		workorder.setProduct(pr);
		
		return workOrderRepository.save(workorder);
	}
	
	public Workorder Update(Workorder workorder, Integer scrapReasonId, Integer productId) {
		
		if(Objects.isNull(workorder) || Objects.isNull(scrapReasonId) || Objects.isNull(productId)) {
			throw new IllegalArgumentException("None of the given parameters can be null");
		}
		
		Timestamp startDate = workorder.getStartdate();
		Timestamp endDate = workorder.getEnddate();
		
		if(startDate.after(endDate)) {
			throw new ArithmeticException("Start date can not be after the end date, it must be before the endDate");
		}
		
		if(workorder.getOrderqty()<0 || workorder.getScrappedqty()<0) {
			throw new NumberFormatException("Start date can not be after the end date, it must be before the endDate");
		}
		
		Optional<Scrapreason> osr = scrapReasonRepository.findById(scrapReasonId);
		Optional<Product> op = productRepository.findById(productId);
		Optional<Workorder> owo = workOrderRepository.findById(workorder.getWorkorderid());
		
		if(osr.isEmpty()) {
			throw new NullPointerException("no existe un Scrapreason con el id especificado");
		}
		
		if(op.isEmpty()) {
			throw new NullPointerException("no existe un Product con el id especificado");
		}
		
		if(owo.isEmpty()) {
			throw new NullPointerException("no existe un workorder con el id especificado");
		}
		
		Workorder workOrderToEdit = owo.get();
		Product product = op.get();
		Scrapreason scrapreason = osr.get();
		
		//se eliminan las anteriores referencias a workorder
		workOrderToEdit.getProduct().removeWorkorder(workOrderToEdit);
		workOrderToEdit.getScrapreason().removeWorkorder(workOrderToEdit);
		
		workOrderToEdit.setDuedate(workorder.getDuedate());
		workOrderToEdit.setEnddate(workorder.getEnddate());
		workOrderToEdit.setModifieddate(workorder.getModifieddate());
		workOrderToEdit.setOrderqty(workorder.getOrderqty());
		workOrderToEdit.setProduct(workorder.getProduct());
		workOrderToEdit.setScrappedqty(workorder.getScrappedqty());
		workOrderToEdit.setScrapreason(workorder.getScrapreason());
		workOrderToEdit.setStartdate(workorder.getStartdate());
		workOrderToEdit.setWorkorderroutings(workorder.getWorkorderroutings());
		
		return workOrderRepository.save(workOrderToEdit);
	}
}
