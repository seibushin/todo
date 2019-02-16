/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-15)
 * ***************************************************/
package de.seibushin.todo;

import de.seibushin.todo.db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class Main implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Start");
		Database.getSessionFactory();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Shutdown");
		Database.closeSessionFactory();

		// deregister all drivers
		// hibernate does not do this for the org.h2.Driver
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while(drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log.info("Deregistered {}", driver);
			} catch (SQLException e) {
				log.error("Error deregistering {}", driver, e);
			}
		}
	}
}
