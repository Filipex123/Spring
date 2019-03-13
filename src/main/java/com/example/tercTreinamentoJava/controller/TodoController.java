package com.example.tercTreinamentoJava.controller;


import com.example.tercTreinamentoJava.model.Todo;
import com.example.tercTreinamentoJava.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("todo")
public class TodoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TodoController.class);
    private TodoRepository todoRepository;
    @Autowired
    public TodoController(TodoRepository todoRepository){this.todoRepository = todoRepository;}

    @GetMapping()
    public ResponseEntity findAll(@RequestParam(value = "title", required = false)String title,
                                  @RequestParam(value = "id", required = false)Long id,
                                  @RequestParam(value = "offset", required = false,defaultValue = "0")int page,
                                  @RequestParam(value = "limit", required = false, defaultValue = "5")int size,
                                  Sort sort){

        LOGGER.trace("Trace");
        LOGGER.debug("Debug");
        LOGGER.info("Info");
        LOGGER.warn("Warn");
        LOGGER.error("Error");
        LOGGER.info("findAll: title:{}|id:{}|offset:{}|limit:{}|sort: {}", title,id,page,size,sort);

        PageRequest pageRequest = PageRequest.of(page,size,sort);
        if(id != null)
            return new ResponseEntity(todoRepository.findById(id), HttpStatus.OK);

        if(title != null)
            return new ResponseEntity(todoRepository.findByTitle(title,pageRequest), HttpStatus.OK);

        return new ResponseEntity(todoRepository.findAll(pageRequest), HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo){
        Todo dbTodo = todoRepository.save(todo);
        return new ResponseEntity<>(dbTodo, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo, @PathVariable long id){

        Todo dbTodo = todoRepository.findById(id).orElse(todo);
        dbTodo.setTitle(todo.getTitle());
        dbTodo.setDescription(todo.getDescription());
        dbTodo.setFinish(todo.getFinish());
        todoRepository.save(dbTodo);
        return new ResponseEntity<>(todo,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteTodo(@PathVariable long id){
       todoRepository.deleteById(id);
    return "deletado";
    }

}
