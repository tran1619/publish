package publisher.ws;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import publisher.data.NewsItem;
import publisher.data.NewsItemDAO;
import publisher.data.User;
import publisher.data.UserDAO;

public class UnpublishNewsItemService extends HttpServlet 
{
	private Logger logger = Logger.getLogger(this.getClass());
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		logger.debug("doGet()");
		String id = req.getParameter("id");
		NewsItemDAO newsItemDAO = new NewsItemDAO();
		NewsItem newsItem = newsItemDAO.find(new Long(id));
		if(newsItem != null)
		{
			newsItemDAO.delete(newsItem);
		}
		
		// Authenticate client
		String accessKey = req.getParameter("accessKey");
		if(accessKey == null)
		{
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		User user = new UserDAO().findByAccessKey(accessKey);
		if(user == null)
		{
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
	}
}
