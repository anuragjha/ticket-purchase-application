/**
 * 
 */
package server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import handlers.BlockingServlet;


/**
 * @author anuragjha
 *
 */
public class Pro4Server {

	private Server jettyServer;

	private boolean shouldRun;

	private ServletHandler handler;

	private final int PORT;

	/**
	 * constructor
	 * @param port
	 */
	public Pro4Server(int port) {

		this.PORT = port;
		this.initialize();
	}

	/**
	 * @return the jettyServer
	 */
	public Server getJettyServer() {
		return jettyServer;
	}
	
	private void initialize() {

		this.shouldRun = true;

		QueuedThreadPool threadpool = getThreadpool(10, 100, 120);

		this.jettyServer = new Server(threadpool);

		ServerConnector connector = new ServerConnector(this.jettyServer);
		connector.setPort(this.PORT);
		this.jettyServer.setConnectors(new Connector[] {connector});


		this.handler = new ServletHandler();
		this.jettyServer.setHandler(this.handler);
		//this.addMapping(BlockingServlet.class, "/status");

		//this.jettyServer.setHandler(this.handler);




	}


	private QueuedThreadPool getThreadpool(int minThreads, int maxThreads, int idleTimeout) {
		return new QueuedThreadPool(minThreads, maxThreads, idleTimeout);
	}

	public void addMapping(Class clazz, String path) {
		//this.handler.addServletWithMapping(BlockingServlet.class, "/status");
		//((ServletHandler)this.jettyServer.getHandler()).addServletWithMapping(BlockingServlet.class, "/status");

		//TODO : handle such that only httpservlet class can be mapped
		if(clazz.getGenericSuperclass().toString().endsWith(".HttpServlet")) {
			((ServletHandler) this.jettyServer.getHandler()).addServletWithMapping(clazz, path);
			System.out.println("servlet superclass: "+clazz.getGenericSuperclass());
		}
		



	}

	public void start() {
		try {
			this.jettyServer.start();
			this.jettyServer.join();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		Pro4Server ps = new Pro4Server(7070);
		ps.initialize();
		ps.addMapping(BlockingServlet.class, "/status");
		ps.start();
	}

}
