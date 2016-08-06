package me.gking2224.model.controller;

public abstract class EntityCreationRequest<T> {

    protected T entity;
    protected T cleanEntity() {
        return entity;
    }
    public T getEntity() {
        return entity;
    }
    public void setEntity(T entity) {
        this.entity = entity;
    }
}
