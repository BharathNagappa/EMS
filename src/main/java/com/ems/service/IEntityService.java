package com.ems.service;

public interface IEntityService<T> {
	
public Iterable<T> getAllEntities();
public T saveEntity(T entity);
}
