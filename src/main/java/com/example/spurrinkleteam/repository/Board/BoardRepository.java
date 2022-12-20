package com.example.spurrinkleteam.repository.Board;

import com.example.spurrinkleteam.entity.Board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BoardRepository extends MongoRepository<Board,String> {
    List<Board> findAllByCategory(String category);

    Page<Board> findAllByCategory(String category, Pageable pageable);

    Page<Board> findAllByCategoryAndTitle(String category, String title, Pageable pageable);
    Page<Board> findAllByAuthor(String author, Pageable pageable);
}
