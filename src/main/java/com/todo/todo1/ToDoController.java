package com.todo.todo1;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class ToDoController {
    private final TodoRepository todoRepository;

    public ToDoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public List<ToDo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ToDo getTodoById(@PathVariable Long id) {
        return todoRepository.findById(id);
    }

    @PostMapping
    public ToDo createTodo(@RequestBody ToDo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public ToDo updateTodo(@PathVariable Long id, @RequestBody ToDo todo) {
        ToDo existingTodo = todoRepository.findById(id);
        existingTodo.setTitle(todo.getTitle());
        existingTodo.setCompleted(todo.isCompleted());
        return todoRepository.save(existingTodo);
    }
 
    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
