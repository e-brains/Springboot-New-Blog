package com.kye.springbootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kye.springbootBlog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
