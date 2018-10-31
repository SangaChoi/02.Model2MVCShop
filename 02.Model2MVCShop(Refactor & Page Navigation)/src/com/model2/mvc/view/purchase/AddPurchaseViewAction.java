package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("애드 퍼체이스 뷰 액션 execute() 시작");
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("애드 퍼체이스 뷰 액션 prodNo : "+prodNo);
		
		ProductService service=new ProductServiceImpl();		
		Product product=service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
