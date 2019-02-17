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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@RestController
@RequestMapping("/create")
public class CreateServlet {
	private static final Logger log = LoggerFactory.getLogger(CreateServlet.class);

	@PostMapping
	public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String dueDate = req.getParameter("dueDate");
		log.info("Creating todo {title={} description={} dueDate={}}", title, description, dueDate);

		// create the to-do
		Todo todo = new Todo();
		if (title != null && !title.equals(""))
			todo.setTitle(title);
		if (dueDate != null && !dueDate.equals(""))
			todo.setDueDate(Date.valueOf(req.getParameter("dueDate")));
		// set the description directly which will always be at least an empty string
		todo.setDescription(description);

		// use the dao to insert the to-do
		TodoDao dao = new TodoDao();
		if (todo.getTitle() != null && todo.getDueDate() != null && dao.addTodo(todo)) {
			log.info("... todo created");
			resp.setStatus(201);
			resp.setHeader("content-type", "application/json");
			resp.getWriter().write(new ObjectMapper().writeValueAsString(todo));
			return;
		}

		// the to-do can not be added to the database
		log.info("... unable to create todo");
		resp.setStatus(400);
		resp.setContentType("text/plain");
		if (todo.getTitle() == null) {
			log.info("title must not be null");
			resp.getWriter().write("title must not be null");
		}
		if (todo.getDueDate() == null) {
			log.info("dueDate must not be null");
			resp.getWriter().write("dueDate must not be null");
		}
	}
}
