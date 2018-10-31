package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.*;

public class PurchaseDAO {
	
	public PurchaseDAO() {
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
				
		Connection con = DBUtil.getConnection();
		
		String sql="INSERT INTO transaction"
				+ " VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		return map;
	}

}
