/**
 * 
 */
package server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;


/**
 * @author anuragjha
 * Pro4Server implements the Jetty Server 
 */
public class Pro4Server {

	private Server jettyServer;
	
	private ServletHandler handler;

	private final int PORT;

	/**
	 * constructor
	 * @param port
	 */
	public Pro4Server(int port) {

		this.PORT = port;
		
	}

	/**
	 * @return the jettyServer
	 */
	public Server getJettyServer() {
		return jettyServer;
	}
	
	/**
	 * Initialises the Jetty server
	 * @param minThreads
	 * @param maxThreads
	 * @param timeout
	 */
	public void initialize(int minThreads, int maxThreads, int timeout) {

		QueuedThreadPool threadpool = getThreadpool(minThreads, maxThreads, timeout);

		this.jettyServer = new Server(threadpool);

		ServerConnector connector = new ServerConnector(this.jettyServer);
		connector.setPort(this.PORT);
		this.jettyServer.setConnectors(new Connector[] {connector});

		
		this.handler = new ServletHandler();
		this.jettyServer.setHandler(this.handler);

	}


	/**
	 * creates a Queued Thread Pool for Jetty to use
	 * @param minThreads
	 * @param maxThreads
	 * @param idleTimeout
	 * @return
	 */
	private QueuedThreadPool getThreadpool(int minThreads, int maxThreads, int idleTimeout) {
		return new QueuedThreadPool(minThreads, maxThreads, idleTimeout);
	}

	/**
	 * adds mapping to the server handler
	 * @param clazz
	 * @param path
	 */
	public void addMapping(Class clazz, String path) {
		//only HttpServlet sub class can be mapped
		if(clazz.getGenericSuperclass().toString().endsWith(".HttpServlet")) {

			this.handler.addServletWithMapping(clazz, path);
			//System.out.println("servlet superclass: "+clazz.getGenericSuperclass());
		}
	}
	

	/**
	 * starts the Jetty server
	 */
	public void start() {
		try {
			this.jettyServer.start();  //
			System.out.println("Server Started");
			this.jettyServer.join();
		} catch (Exception e) {
			System.out.println("Error in starting Jetty engine");
			e.printStackTrace();
			System.exit(1);
		}
		
	}


//	public static void main(String[] args) {
//
////		Pro4Server ps = new Pro4Server(7070);
////		ps.initialize(10, 100, 120);
////		//ps.addMapping(BlockingServlet.class, "/status");
////		ps.start();
//	}

}
