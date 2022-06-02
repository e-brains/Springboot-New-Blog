package com.kye.springbootBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kye.springbootBlog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{
	
	@Modifying
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(long userId, long boardId, String content); // 저장된 갯수 리턴
}
