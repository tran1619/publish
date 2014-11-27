package publisher.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import publisher.data.NewsItem;
import publisher.data.NewsItemDAO;
import publisher.data.User;
import publisher.data.UserDAO;

public class PublishNewsItemService extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass());
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			logger.debug("doPost()");
			
			// Read the XML request document
			BufferedReader br = req.getReader();
			SAXBuilder builder = new SAXBuilder();
			Document requestDocument = null;
			try
			{
				requestDocument = builder.build(br);
			} catch (JDOMException e)
			{
				throw new RuntimeException(e);
			}
			
			// Extract title and link from request
			Element item = requestDocument.getRootElement();
			Element titleElement = item.getChild("title");
			String title = titleElement.getText();
			Element linkElement = item.getChild("link");
			String link = linkElement.getText();
			
			  // Authenticate client.
		      Element accessKeyElement = item.getChild("accessKey");
		      if (accessKeyElement == null)
		      {
		         resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		         return;
		      }
		      String accessKey = accessKeyElement.getText();
		      User user = new UserDAO().findByAccessKey(accessKey);
		      if (user == null)
		      {
		         resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		         return;
		      }
		      
			// Create news item from submitted data
			NewsItem newsItem = new NewsItem();
			newsItem.setTitle(title);
			newsItem.setUrl(link);
			new NewsItemDAO().create(newsItem);
			
			// Create response document with id of newly
			// created news item
			Element idElement = new Element("id");
			idElement.addContent(newsItem.getId().toString());
			Document responseDocument = new Document(idElement);
			StringWriter sw = new StringWriter();
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(responseDocument, sw);
			String responseDocumentString = sw.toString();
			
			// Return response document
			byte[] responseBytes = responseDocumentString.getBytes("UTF-8");
			resp.setContentLength(responseBytes.length);
			resp.setContentType("text/html");
			OutputStream os = resp.getOutputStream();
			os.write(responseBytes);
			os.flush();
	}
}
