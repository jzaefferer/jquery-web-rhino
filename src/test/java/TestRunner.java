

import org.mortbay.jetty.AbstractConnector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class TestRunner {

	static int port = 8001;

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		
		// SelectChannelConnector is required for Comet support
		AbstractConnector connector = new SelectChannelConnector();
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(port);
		server.addConnector(connector);

		WebAppContext webapp = new WebAppContext();
		webapp.setDefaultsDescriptor("src/test/resources/webdefault.xml");
		webapp.setServer(server);
		webapp.setContextPath("/");
		webapp.setWar("src/main/webapp");

		server.addHandler(webapp);
		
		run(server);
	}
	
	private static void run(Server server) {
		try {
			System.out.println(">>> Starting Jetty, press enter to stop, r + enter to restart");
			server.start();
			System.out.println("Started server at http://localhost:" + port + "/blog");
			while (System.in.available() == 0) {
				Thread.sleep(500);
			}
			server.stop();
			server.join();
			if (System.in.read() == 114) {
				System.in.skip(1000);
				run(server);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
	
}
