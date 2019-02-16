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

@WebServlet(name = "UpdateServlet", urlPatterns = {"/update"}, loadOnStartup = 1)
public class UpdateServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(UpdateServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			String description = req.getParameter("description");

			// create to-do
			Todo todo = new Todo();
			todo.setId(id);
			todo.setDescription(description);

			// update to-do
			TodoDao dao = new TodoDao();
			if (dao.updateTodo(todo)) {
				log.info("Updated todo with id {} description={}", id, description);
				resp.setStatus(204);
				return;
			}
		} catch (Exception e) {
			log.error("Error updating todo");
		}

		// the to-do can not be updated
		log.info("Unable to update todo with id {} description={}", req.getParameter("id"), req.getParameter("description"));
		resp.setStatus(400);
		resp.setContentType("text/plain");
		resp.getWriter().write("title must not be null");
		resp.getWriter().write("dueDate must not be null");
	}
}
