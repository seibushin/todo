/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-13)
 * ***************************************************/
package de.seibushin.todo.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.seibushin.todo.dao.TodoDao;
import de.seibushin.todo.model.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(SearchServlet.class);

	@PostMapping
	protected void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			String query = req.getParameter("search");
			boolean done = Boolean.parseBoolean(req.getParameter("done"));

			TodoDao dao = new TodoDao();
			List<Todo> todos = dao.getTodos(query, done);
			if (todos.size() > 0) {
				log.info("{} todos matched the query={} done={}", todos.size(), query, done);
				resp.setStatus(200);
				resp.setHeader("content-type", "application/json");

				resp.getWriter().write(new ObjectMapper().writeValueAsString(todos));

				return;
			}
		} catch (Exception e) {
			log.error("Error searching for todos");
		}

		log.info("No todo matched the query={} done={}", req.getParameter("search"), req.getParameter("done"));
		// the to-do can not be updated
		resp.setStatus(404);
		resp.setContentType("text/plain");
		resp.getWriter().write("to-do does not exist");
	}
}
