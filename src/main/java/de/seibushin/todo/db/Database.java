/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-13)
 * ***************************************************/
package de.seibushin.todo.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Gives access to the sessionFactory and therefore access to the database
 */
public class Database {
	private static SessionFactory sessionFactory;

	/**
	 * Explicitly initialize the sessionFactory this helps to reduce the loading time for the first access of the
	 * web application
	 * <p>
	 * To be clear it is sufficient to just call getSessionFactory without calling this method which will result
	 * in lazy initialization
	 */
	public static void init() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
	}

	/**
	 * Get the SessionFactory
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		// lazy init
		init();

		return sessionFactory;
	}

	/**
	 * Close the factory
	 */
	public static void close() {
		sessionFactory.close();
	}
}
