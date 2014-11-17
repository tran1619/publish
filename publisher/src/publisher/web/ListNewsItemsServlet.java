package publisher.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import publisher.data.NewsItem;
import publisher.data.NewsItemDAO;

import org.apache.log4j.Logger;

public class ListNewsItemsServlet extends HttpServlet
{
	private Logger logger = Logger.getLogger(this.getClass());
	
   private RequestDispatcher jsp;

   public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      jsp = context.getRequestDispatcher("/WEB-INF/jsp/list-news-items.jsp");
   }
   
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException
   {
	  logger.debug("doGet()");
      List<NewsItem> newsItems = new NewsItemDAO().findAll();
      req.setAttribute("newsItems", newsItems);
      jsp.forward(req, resp);
   }
}