

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.DBManager;

/**
 * Application Lifecycle Listener implementation class MyAppListener
 *
 */
@WebListener
public class MyAppListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public MyAppListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	ServletContext ctx = arg0.getServletContext();
    	String url; 
    	String user; 
    	String pass; 
    	String os = System.getProperty("os.name");
    	if (os != null && os.startsWith("Windows")){
    		url = ctx.getInitParameter("DB_URL1");
    		user = ctx.getInitParameter("DB_USER1");
    		pass = ctx.getInitParameter("DB_PASS1");
    	}
    	else {
    		url = ctx.getInitParameter("DB_URL2");
    		user = ctx.getInitParameter("DB_USER2");
    		pass = ctx.getInitParameter("DB_PASS2");
    	}

    	DBManager.DB_URL = url;
    	DBManager.DB_PASS = pass;
    	DBManager.DB_USER = user;
    		

    }
	
}
