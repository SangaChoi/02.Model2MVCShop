package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO=new ProductDAO();
		System.out.println("프로덕트 서비스 임플 DAO 생성");
	}

	@Override
	public void addProduct(Product product) throws Exception {
		productDAO.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		System.out.println("프로덕트 서비스 임플 getProductList() 시작");
		return productDAO.getProductList(search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDAO.updateProduct(product);
		
	}

	

}
