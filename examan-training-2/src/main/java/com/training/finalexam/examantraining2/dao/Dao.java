package com.training.finalexam.examantraining2.dao;

public interface Dao {
    Optional<T> get(long id);

    Collection<T> getAll();

    int save(T t);

    void delete(T t);
}
