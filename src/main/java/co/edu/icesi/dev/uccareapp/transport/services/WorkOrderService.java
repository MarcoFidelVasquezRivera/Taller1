package co.edu.icesi.dev.uccareapp.transport.services;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Workorder;

public interface WorkOrderService {

	public Workorder Save(Workorder workorder, Integer scrapReasonId, Integer productId);
	public Workorder Update(Workorder workorder, Integer scrapReasonId, Integer productId);
}
