package publisher.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publisher.data.NewsItem;
import publisher.data.NewsItemDAO;

public class CreateNewsItemServlet extends HttpServlet
{
   private RequestDispatcher jsp;
   
   public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      jsp = context.getRequestDispatcher("/WEB-INF/jsp/edit-news-item.jsp");
   }
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      jsp.forward(req, resp);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException
   {
      // Check if cancel button was pressed.
      String cancelButton = req.getParameter("cancel-button");
      if (cancelButton != null)
      {
         resp.sendRedirect("list-news-items");
         return;
      }
      Map<String, String> errors = EditNewsItemServlet.validate(req);
      if (!errors.isEmpty())
      {
         jsp.forward(req, resp);
         return;
      }

      NewsItem newsItem = (NewsItem) req.getAttribute("newsItem");
      new NewsItemDAO().create(newsItem);
      resp.sendRedirect("view-news-item?id=" + newsItem.getId());
   }
}