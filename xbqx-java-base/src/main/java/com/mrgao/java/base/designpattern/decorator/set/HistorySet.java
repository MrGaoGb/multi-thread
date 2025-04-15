package com.mrgao.java.base.designpattern.decorator.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description 当前容器用来记录已经删除元素的集合
 * @Author Mr.Gao
 * @Date 2025/4/16 0:00
 */
public class HistorySet<T> implements Set<T> {

    /**
     * 历史记录集合
     */
    private Set<T> historySet = new HashSet<>();

    private Set<T> delegate = new HashSet<>();

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return delegate.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return delegate.add(t);
    }

    @Override
    public boolean remove(Object o) {
        if (delegate.remove(o)) {
            historySet.add((T) o);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return delegate.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (delegate.removeAll(c)) {
            historySet.addAll((Collection<? extends T>) c);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public String toString() {
        return "HistorySet{" +
                "移除的历史元素-historySet=" + historySet +
                ", 当前集合中的元素-delegate=" + delegate +
                '}';
    }
}
