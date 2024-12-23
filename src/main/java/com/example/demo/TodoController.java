package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    private TodoService todoService;
    private TodoService todoService2;
    private static List<TodoDemo> todoDemoList;

    public TodoController(@Qualifier("FakeTodoService") TodoService todoService,@Qualifier("AnotherTodoService") TodoService todoService2) {
        this.todoService=todoService;
        this.todoService2=todoService2;
        todoDemoList = new ArrayList<>();
        todoDemoList.add(new TodoDemo(1, 1, "Gaurav Sharma", true));
        todoDemoList.add(new TodoDemo(1, 2, "Navneet Namdev", false));
        System.out.println(todoService.doSomething());
        System.out.println(todoService2.doSomething());
    }

    @GetMapping("/todos")
    public List<TodoDemo> getAllTodos(@RequestParam(required = false,defaultValue = "false") boolean isCompleted) {
        System.out.println("Completed: "+isCompleted);
        return todoDemoList;
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDemo createTodo(@RequestBody TodoDemo newTodo) {
        todoDemoList.add(newTodo);
        return newTodo;
    }

    @PutMapping("/todos/{id}")
    public TodoDemo updateTodoById(@PathVariable int id, @RequestBody TodoDemo newTodo) {
        for (TodoDemo todoDemo : todoDemoList) {
            if (todoDemo.getId() == id) {
                todoDemoList.remove(todoDemo);
                todoDemoList.add(newTodo);
                return newTodo;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with Id:" + id);
    }
    @PatchMapping("/todos/{id}")
    public TodoDemo partialUpdateById(@PathVariable int id, @RequestBody TodoDemo newTodo){
        for(TodoDemo todoDemo: todoDemoList){
            if(todoDemo.getId() == id){
                // Update title if provided
                if (newTodo.getTitle() != null && !newTodo.getTitle().isEmpty()) {
                    todoDemo.setTitle(newTodo.getTitle());
                }
                // Update userId if provided (assuming valid non-negative userId)
                if (newTodo.getUserId() > 0) {
                    todoDemo.setUserId(newTodo.getUserId());
                }
                return todoDemo;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Todo with Id:"+id+" Not found");
    }
    @DeleteMapping("/todos/{id}")
    public String deleteTodoById(@PathVariable int id) {
        Iterator<TodoDemo> iterator = todoDemoList.iterator();
        while (iterator.hasNext()) {
            TodoDemo todoDemo = iterator.next();
            if (todoDemo.getId() == id) {
                iterator.remove();
                return "Todo with Id:" + id + " has been deleted successfully.";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with Id:" + id);
    }
}
