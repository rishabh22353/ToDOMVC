package com.todo.todo1;

import java.util.List;

public interface TodoRepository {
    List<ToDo> findAll();

    ToDo findById(Long id);

    ToDo save(ToDo todo);

    void deleteById(Long id);
}
