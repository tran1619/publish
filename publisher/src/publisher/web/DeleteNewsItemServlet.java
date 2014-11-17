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

public class DeleteNewsItemServlet extends HttpServlet
{
   private RequestDispatcher jsp;

   public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      jsp = context.getRequestDispatcher("/WEB-INF/jsp/delete-news-item.jsp");
   }
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException
   {
      jsp.forward(req, resp);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException
   {
      String idString = req.getParameter("id");
      
      // Check if cancel button was pressed.
      String cancelButton = req.getParameter("cancel-button");
      if (cancelButton != null)
      {
         resp.sendRedirect("view-news-item?id=" + idString);
         return;
      }

      NewsItemDAO newsItemDAO = new NewsItemDAO();
      Long id = new Long(idString);
      NewsItem newsItem = newsItemDAO.find(id);
      new NewsItemDAO().delete(newsItem);
      resp.sendRedirect("list-news-items");
   }
}