package ua.etaxi.core.database;

import ua.etaxi.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * Created by Viktor on 10/10/2014.
 */
public interface EntityRepository {

    <E extends BaseEntity> void create(E entity);

    <E extends BaseEntity, K extends Serializable> E getById(Class clazz, K key);

    <E extends BaseEntity, K extends Serializable> E getRequired(Class clazz, K key);

    <E extends BaseEntity> void update(E entity);

    <E extends BaseEntity> void delete(E entity);

}
