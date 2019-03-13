package com.example.tercTreinamentoJava.repository;

import com.example.tercTreinamentoJava.model.Todo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {

//    @Query(value="Select t from Todo t where t.title = ?1",
//            countQuery = "Select count(t) from Todo t where t.title =?1")

    List<Todo> findByTitle(String title, PageRequest pageRequest);

    @Query("Select t from Todo t where t.title = ?1 and t.id=?2")
    List<Todo> findByTitleAndId(String title,String id);



}
