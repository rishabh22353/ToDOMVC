package com.todo.todo1;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class TodoRepositoryImp implements TodoRepository {
    private final Map<Long, ToDo> todos = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public List<ToDo> findAll() {
        return new ArrayList<>(todos.values());
    }

    @Override
    public ToDo findById(Long id) {
        return todos.get(id);
    }

    @Override
    public ToDo save(ToDo todo) {
        if (todo.getId() == null) {
            todo.setId(nextId++);
        }
        todos.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public void deleteById(Long id) {
        todos.remove(id);
    }


}