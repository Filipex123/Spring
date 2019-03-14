package com.example.tercTreinamentoJava.message;

import com.example.tercTreinamentoJava.model.Todo;
import com.example.tercTreinamentoJava.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class TodoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoConsumer.class);
    private final TodoRepository todoRepository;

    @Autowired
    public TodoConsumer(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @RabbitListener(queues = "fzanelato")
    public void receiveMessage(Message message){
        String messageBody = new String(message.getBody());
        LOGGER.info(messageBody);
        try{
            ObjectMapper mapper = new ObjectMapper();
            Todo todo = mapper.readValue(message.getBody(), Todo.class);
            Todo dbTodo = todoRepository.save(todo);
            LOGGER.info("Todo criado: {}",dbTodo);
        }catch (IOException e){
            LOGGER.error("Erro aoconverter a mensagem.", e);
        }
    }

}
