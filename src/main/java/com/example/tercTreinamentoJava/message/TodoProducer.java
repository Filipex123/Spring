package com.example.tercTreinamentoJava.message;

import com.example.tercTreinamentoJava.model.Todo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TodoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTodoMessage(Todo todo){
        rabbitTemplate.convertAndSend("fzanelato", todo);
    }
}
