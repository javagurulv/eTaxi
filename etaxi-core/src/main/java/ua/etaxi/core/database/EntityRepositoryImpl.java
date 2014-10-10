package ua.etaxi.core.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.etaxi.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * Created by Viktor on 10/10/2014.
 */
@Component
public class EntityRepositoryImpl implements EntityRepository {

    @Autowired
    private SessionFactory sessionFactory;


    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public <E extends BaseEntity> void create(E entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public <E extends BaseEntity, K extends Serializable> E getById(Class clazz, K key) {
        return (E) getCurrentSession().get(clazz, key);
    }

    @Override
    public <E extends BaseEntity, K extends Serializable> E getRequired(Class clazz, K key) {
        E entity = (E) getCurrentSession().get(clazz, key);
        if(entity == null) {
            throw new IllegalArgumentException("Entity with id = " + key + " not found for class = " + clazz.getName());
        } else {
            return entity;
        }
    }

    @Override
    public <E extends BaseEntity> void update(E entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public <E extends BaseEntity> void delete(E entity) {
        getCurrentSession().delete(entity);
    }

}
