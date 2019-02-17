/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-16)
 * ***************************************************/
package de.seibushin.todo;

import de.seibushin.todo.db.Database;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		// init database
		Database.init();

		// configure tomcat
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(PORT);

		String base = createBaseDir();
		// copy the webapp to the tempDir
		copyWebappToDir(base);
		// set baseDir
		tomcat.setBaseDir(base);
		// add webApp
		tomcat.addWebapp("/todo", new File(base, "webapp").getAbsolutePath());

		// get the default connector
		tomcat.getConnector();
		// start tomcat
		tomcat.start();

		// allow the user to gracefully stop the server
		// this will also ensure that the tempDir is deleted
		log.info("Type 'exit' to stop the server");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while (!br.readLine().equals("exit")) {
				// await
			}
			tomcat.stop();
			close();
		}
	}

	/**
	 * Create base dir
	 *
	 * @return path
	 */
	private static String createBaseDir() {
		File baseDir = new File("tomcat." + PORT);
		if (!baseDir.exists()) {
			baseDir.mkdir();
		}
		log.info("baseDir {}", baseDir.getAbsolutePath());
		return baseDir.getAbsolutePath();
	}

	/**
	 * copy the webapp to the dir
	 *
	 * @param dir path to the dir
	 */
	private static void copyWebappToDir(String dir) {
		File webapp = new File(dir, "webapp");
		webapp.mkdir();

		File webappZip = new File(webapp, "webapp.zip");

		// copy the webappZip to the tempDir
		try (OutputStream os = new FileOutputStream(webappZip)) {
			InputStream is = Main.class.getResourceAsStream("/todo-1.0.zip");
			byte[] buffer = new byte[1024];
			int readBytes;
			while ((readBytes = is.read(buffer)) > 0) {
				os.write(buffer, 0, readBytes);
			}
		} catch (Exception e) {
			log.error("Error copying the zipped webapp, check build.gradle to ensure the webapp is zipped to /todo-1.0.zip", e);
			return;
		}

		// unzip
		unzip(webappZip, webapp);
	}

	/**
	 * Unzip the zip to the given destination
	 *
	 * @param zip  the archive
	 * @param dest the destination for the archives content
	 */
	private static void unzip(File zip, File dest) {
		// extract the archive
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
			byte[] buffer = new byte[1024];
			ZipEntry zipEntry;
			// iterate over the zip entries
			while ((zipEntry = zis.getNextEntry()) != null) {
				// check if the entry is a dir
				if (zipEntry.isDirectory()) {
					// create the dir
					File dir = new File(dest, zipEntry.getName());
					dir.mkdir();
				} else {
					// copy the file from the archive
					File newFile = new File(dest, zipEntry.getName());
					try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {
						int readBytes;
						while ((readBytes = zis.read(buffer)) > 0) {
							bos.write(buffer, 0, readBytes);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error extracting the webapp from the archive", e);
		}
	}

	/**
	 * Close Database
	 */
	private static void close() {
		log.info("Shutdown");
		Database.close();

		// deregister all drivers
		// hibernate does not do this for the org.h2.Driver
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log.info("Unregistered {}", driver);
			} catch (SQLException e) {
				log.error("Error unregistering {}", driver, e);
			}
		}

		System.exit(0);
	}
}