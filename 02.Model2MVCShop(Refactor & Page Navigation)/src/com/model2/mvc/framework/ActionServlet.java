package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	///Field
	private RequestMapping requestMapping;
	
	///Method
	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		requestMapping=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = request.getRequestURI();
		System.out.println("¾×¼Ç¼­ºí¸´ url : "+url);
		String contextPath = request.getContextPath();
		System.out.println("¾×¼Ç¼­ºí¸´ contextPath : "+contextPath);
		String requestPath = url.substring(contextPath.length());
		System.out.println("¾×¼Ç¼­ºí¸´ requestPath : "+requestPath);
		
		try{
			Action action = requestMapping.getAction(requestPath);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			System.out.println("¾×¼Ç¼­ºí¸´ resultPage : "+resultPage);
			String path=resultPage.substring(resultPage.indexOf(":")+1);
			System.out.println("¾×¼Ç¼­ºí¸´ path : "+path);
			
			if(resultPage.startsWith("forward:")){
				HttpUtil.forward(request, response, path);
			}else{
				HttpUtil.redirect(response, path);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}