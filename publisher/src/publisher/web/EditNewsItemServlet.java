package publisher.web;

import java.io.IOException;
import java.util.HashMap;
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

public class EditNewsItemServlet extends HttpServlet
{
   // private Logger logger = Logger.getLogger(this.getClass());
   private RequestDispatcher jsp;
   
   public void init(ServletConfig config) throws ServletException {
      ServletContext context = config.getServletContext();
      jsp = context.getRequestDispatcher("/WEB-INF/jsp/edit-news-item.jsp");
   }

   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException {
      // logger.debug("doGet()");
      String idString = req.getParameter("id");
      Long id = new Long(idString);
      NewsItem newsItem = new NewsItemDAO().find(id);
      req.setAttribute("newsItem", newsItem);
      jsp.forward(req, resp);
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException
   {
      String id = req.getParameter("id");
      
      // Check if cancel button was pressed.
      String cancelButton = req.getParameter("cancel-button");
      if (cancelButton != null)
      {
  //       logger.debug("cancel button pressed");
         resp.sendRedirect("view-news-item?id=" + id);
         return;
      }
      Map<String, String> errors = validate(req);
      if (!errors.isEmpty())
      {
       //  logger.debug("validation errors");
         jsp.forward(req, resp);
         return;
      }

      NewsItem newsItem = (NewsItem) req.getAttribute("newsItem");
      new NewsItemDAO().update(newsItem);
      resp.sendRedirect("view-news-item?id=" + id);
   }
   
   public static Map<String, String> validate(HttpServletRequest req)
   {
      NewsItem newsItem = new NewsItem();
      HashMap<String, String> errors = new HashMap<String, String>();
      req.setAttribute("errors", errors);
      req.setAttribute("newsItem", newsItem);

      String idString = req.getParameter("id");
      if (idString != null && idString.length() > 0)
      {
         Long id = new Long(idString);
         newsItem.setId(id);
      }
      
      // title
      String title = req.getParameter("title");
      if (title == null || title.trim().length() == 0)
      {
         errors.put("title", "Title required.");
      }
      newsItem.setTitle(title);

      // url
      String url = req.getParameter("url");
      if (url == null || url.trim().length() == 0)
      {
         errors.put("url", "URL required.");
      }
      newsItem.setUrl(url);
      
      return errors;
   }
}