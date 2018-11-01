package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.*;


public class ProductDAO {

	public ProductDAO() {
	}
	
	public void insertProduct(Product productVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="insert into product values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replace("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	public Map<String, Object> getProductList(Search search) throws Exception{
		
		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql= 
				"SELECT p.PROD_NO, p.prod_name, p.prod_detail, p.MANUFACTURE_DAY, p.PRICE, p.IMAGE_FILE, p.reg_date, t.tran_status_code"
				+" FROM product p, transaction t ";
		
		if(search.getSearchCondition() !=null) {
			if(search.getSearchCondition().equals("0")) {
				sql +="WHERE p.PROD_NO like '%"+search.getSearchKeyword()+"%' AND p.prod_no=t.prod_no(+)";
			}else if(search.getSearchCondition().equals("1")) {
				sql +="WHERE p.PROD_NAME like '%"+search.getSearchKeyword()+"%' AND p.prod_no=t.prod_no(+)";
			}else if(search.getSearchCondition().equals("2")) {
				sql +="WHERE p.PRICE like '%"+search.getSearchKeyword()+"%' AND p.prod_no=t.prod_no(+)";
			}
		}else {
			sql+=" WHERE p.prod_no=t.prod_no(+)";
		}
		sql += " ORDER BY PROD_NO";
		
		System.out.println("DAO getProductList 오리지널 sql : "+sql);

		int totalCount = this.getTotalCount(sql);
		System.out.println("UserDAO :: totalCount  :: " + totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()) {
			Product product=new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			
			if(rs.getString("tran_status_code")==null) {
				product.setProTranCode("0");
			}else {
				product.setProTranCode(rs.getString("tran_status_code"));
			}
			
			list.add(product);
			
			System.out.println(product);
				
		}
		
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();
		
		return map;
	}


	public Product findProduct(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		Product productVO=null;
		while(rs.next()) {
			productVO=new Product();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();
		return productVO;
	}
	
	public void updateProduct(Product productVO) throws Exception{
		
		System.out.println("************DAO updateProduct() 시작");
		Connection con = DBUtil.getConnection();
		
		String sql = "update product set prod_name=?, prod_detail=?, MANUFACTURE_DAY=?, PRICE=?, IMAGE_FILE=? where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
		System.out.println("************DAO updateProduct() 끝");
	}
	
	private int getTotalCount(String sql) throws Exception {
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
