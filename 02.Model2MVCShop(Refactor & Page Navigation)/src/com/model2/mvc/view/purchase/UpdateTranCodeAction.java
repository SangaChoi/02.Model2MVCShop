package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class UpdateTranCodeAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		String tranCode=request.getParameter("tranCode");
		
		String buyerId=request.getParameter("buyerId");
		System.out.println("업데이트트랜코드액션 buyerId"+buyerId);
		
		Product product=new Product();
		product.setProTranCode(tranCode);
		
		Purchase purchase=new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPurchaseProd(product);
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);
			
		return "redirect:/listPurchase.do?buyerId="+buyerId;
	}

}
