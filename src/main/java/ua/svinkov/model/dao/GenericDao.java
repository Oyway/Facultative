package ua.svinkov.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
	/**
	 * add new line in entity table
	 * 
	 * @param entity object
	 */
	void create(T entity);

	/**
	 * find entity in db by its id
	 * 
	 * @param id of entity
	 * @return entity
	 */
	T findById(int id);

	/**
	 * find all entity in table
	 * 
	 * @return List of entity
	 */
	List<T> findAll();

	/**
	 * update given entity in db
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * delete entity from db by its id
	 * 
	 * @param id of entity
	 */
	void delete(int id);
}
