/* ***************************************************
 * Created by Sebastian Meyer (s.meyer@seibushin.de)
 * (2019-02-13)
 * ***************************************************/
package de.seibushin.todo.dao;

import de.seibushin.todo.db.Database;
import de.seibushin.todo.model.Todo;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TodoDao {
	/**
	 * Add the to-do to the database
	 *
	 * @param t new to-do
	 */
	public boolean addTodo(Todo t) {
		// use session in try-with-resource block
		try (Session session = Database.getSessionFactory().openSession()) {
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Update the description of the to-do using the id
	 *
	 * @param t with id and new description
	 * @return true if update succeeded
	 */
	public boolean updateTodo(Todo t) {
		try (Session session = Database.getSessionFactory().openSession()) {
			session.beginTransaction();
			Query sql = session.createQuery("update Todo set description = :description where id = :id")
					.setParameter("description", t.getDescription())
					.setParameter("id", t.getId());
			int res = sql.executeUpdate();
			session.getTransaction().commit();
			return res > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Delete the to-do
	 *
	 * @param id id for the to-do
	 * @return true if deleted
	 */
	public boolean deleteTodo(int id) {
		try (Session session = Database.getSessionFactory().openSession()) {
			session.beginTransaction();
			Query sql = session.createQuery("delete from Todo where id = :id")
					.setParameter("id", id);
			int res = sql.executeUpdate();
			session.getTransaction().commit();
			return res > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Do the to-do
	 *
	 * @param id id for the to-do
	 * @return true if deleted
	 */
	public boolean doTodo(int id) {
		try (Session session = Database.getSessionFactory().openSession()) {
			session.beginTransaction();
			Query sql = session.createQuery("update Todo set done = true where id = :id")
					.setParameter("id", id);
			int res = sql.executeUpdate();
			session.getTransaction().commit();
			return res > 0;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get all matching todos
	 *
	 * @param query query for the title
	 * @return list of matched todos
	 */
	public List<Todo> getTodos(String query, boolean done) {
		try (Session session = Database.getSessionFactory().openSession()) {
			return session.createQuery("from Todo where title like :query and (done = :done or done = false) order by dueDate", Todo.class)
					.setParameter("query", query + "%")
					.setParameter("done", done)
					.list();
		}
	}

	/**
	 * Get all undone todos
	 *
	 * @return list of all undone
	 */
	public List<Todo> getTodos() {
		return getTodos("", false);
	}
}