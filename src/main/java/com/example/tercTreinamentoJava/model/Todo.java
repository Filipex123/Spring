package com.example.tercTreinamentoJava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Setter @Getter @NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private Date finish;
}
