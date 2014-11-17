package publisher.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import publisher.data.NewsItem;
import publisher.data.NewsItemDAO;

public class ViewNewsItemServlet extends HttpServlet
{
   private RequestDispatcher jsp;
   
   public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      jsp = context.getRequestDispatcher("/WEB-INF/jsp/view-news-item.jsp");
   }
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException
   {
       String idString = req.getParameter("id");
       Long id = new Long(idString);
       NewsItem newsItem = new NewsItemDAO().find(id);
       req.setAttribute("newsItem", newsItem);
       jsp.forward(req, resp);
   }
}
