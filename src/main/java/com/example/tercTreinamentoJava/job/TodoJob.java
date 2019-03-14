package com.example.tercTreinamentoJava.job;

import com.example.tercTreinamentoJava.model.Todo;
import com.example.tercTreinamentoJava.repository.TodoRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Component
public class TodoJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoJob.class);
    private final TodoRepository todoRepository;

    @Autowired
    public TodoJob(TodoRepository todoRepository){
        this.todoRepository= todoRepository;
    }

    @Scheduled(cron = "0/10 * * ? * *")
    public void execute(){
        LOGGER.info("Iniciando job TodoJob");
        try{
            Todo todo = new Todo();
            todo.setTitle("Titulo");
            todo.setDescription("Descricao");
            todo.setFinish(new Date());
            todoRepository.save(todo);
            int random = new Random().nextInt(3);
            LOGGER.info(String.valueOf(random));
            readFile();
        }catch (Exception e){
            LOGGER.error("Ocorreu um erro no job TodoJob", e);
        }
    }

    private void readFile(){
        LOGGER.info("Lendo csv");
        try{
            CSVReader reader = new CSVReaderBuilder(new FileReader("meucsv.csv"))
                    .withSkipLines(1)
                    .build();
            String[] nextLine;
            while((nextLine = reader.readNext()) != null){
                Todo todo = new Todo();
                todo.setTitle(nextLine[0]);
                todo.setDescription(nextLine[1]);
                todoRepository.save(todo);
                LOGGER.info(todo.getId()+","+todo.getTitle()+", "+todo.getDescription());
            }
        } catch (IOException e) {
            LOGGER.error("Erro ao ler o csv ",e);
        }

    }
}
