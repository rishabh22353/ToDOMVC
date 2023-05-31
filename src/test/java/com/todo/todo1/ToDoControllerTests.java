package com.todo.todo1;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ToDoControllerTests {
    @InjectMocks
    private ToDoController todoController;

    @Mock
    private TodoRepository todoRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    public void testGetAllTodos() throws Exception {
        ToDo todo1 = new ToDo();
        todo1.setId(1L);
        todo1.setTitle("Task 1");
        todo1.setCompleted(false);
        ToDo todo2 = new ToDo();
        todo2.setId(2L);
        todo2.setTitle("Task 2");
        todo2.setCompleted(true);
        List<ToDo> todos = Arrays.asList(todo1, todo2);
        Mockito.when(todoRepository.findAll()).thenReturn(todos);
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(todos.size()));
    }

    @Test
    public void testGetTodoById() throws Exception {
        ToDo todo = new ToDo();
        todo.setId(1L);
        todo.setTitle("Task 1");
        todo.setCompleted(false);
        when(todoRepository.findById(1L)).thenReturn(todo);
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void testCreateTodo() throws Exception {
        ToDo todo = new ToDo();
        todo.setId(1L);
        todo.setTitle("Task 1");
        todo.setCompleted(false);
        when(todoRepository.save(todo)).thenReturn(todo);
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\": \"Task 1\", \"completed\": false }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        ToDo existingTodo = new ToDo();
        existingTodo.setId(1L);
        existingTodo.setTitle("Task 1");
        existingTodo.setCompleted(false);
        when(todoRepository.findById(1L)).thenReturn(existingTodo);
        when(todoRepository.save(existingTodo)).thenReturn(existingTodo);
        mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"title\": \"Updated Task\", \"completed\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void testDeleteTodoById() throws Exception {
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk());
        verify(todoRepository).deleteById(1L);
    }
}
