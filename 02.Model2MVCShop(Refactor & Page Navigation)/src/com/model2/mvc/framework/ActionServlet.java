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
		System.out.println("액션서블릿 init() 시작");
		String resources=getServletConfig().getInitParameter("resources");
		requestMapping=RequestMapping.getInstance(resources);
		System.out.println("액션서블릿 init() 끝");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		System.out.println("--------------------액션서블릿 service() 시작");
		
		String url = request.getRequestURI();
		System.out.println("액션서블릿 url : "+url);
		String contextPath = request.getContextPath();
		System.out.println("액션서블릿 contextPath : "+contextPath);
		String requestPath = url.substring(contextPath.length());
		System.out.println("액션서블릿 requestPath : "+requestPath);
		
		try{
			Action action = requestMapping.getAction(requestPath);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			System.out.println("액션서블릿 resultPage : "+resultPage);
			String path=resultPage.substring(resultPage.indexOf(":")+1);
			System.out.println("액션서블릿 path : "+path);
			
			if(resultPage.startsWith("forward:")){
				HttpUtil.forward(request, response, path);
			}else{
				HttpUtil.redirect(response, path);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("--------------------액션서블릿 service() 끝");
	}
}