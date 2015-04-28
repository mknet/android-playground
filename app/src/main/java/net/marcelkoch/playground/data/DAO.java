package net.marcelkoch.playground.data;

import java.util.List;

/**
 * Created by marcel on 28.04.15.
 */
public interface DAO<E> {
    public void save(final E entity);
    public List<E> getList();
}
