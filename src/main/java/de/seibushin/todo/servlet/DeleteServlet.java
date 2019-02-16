/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-13)
 * ***************************************************/
package de.seibushin.todo.servlet;

import de.seibushin.todo.dao.TodoDao;
import de.seibushin.todo.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", urlPatterns = {"/delete"}, loadOnStartup = 1)
public class DeleteServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DeleteServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			int id = Integer.parseInt(req.getParameter("id"));

			TodoDao dao = new TodoDao();
			if (dao.deleteTodo(id)) {
				log.info("Deleted todo with id {}", id);
				resp.setStatus(204);
				return;
			}
		} catch (Exception e) {
			log.error("Error deleting todo");
		}

		// the to-do can not be updated
		log.info("Todo with id {} not found", req.getParameter("id"));
		resp.setStatus(404);
		resp.setContentType("text/plain");
		resp.getWriter().write("to-do does not exist");
	}
}
