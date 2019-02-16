/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-13)
 * ***************************************************/
package de.seibushin.todo.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database {
	private static SessionFactory sessionFactory;

	/**
	 * Explicitly initialize the sessionFactory this helps to reduce the loading time for the first access of the
	 * web application
	 *
	 * To be clear it is sufficient to just call getSessionFactory without calling {@link #init()} which will result
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
	public static void closeSessionFactory() {
		sessionFactory.close();
	}
}
