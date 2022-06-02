package com.kye.springbootBlog.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplySaveRequestDto {
	private long userId;
	private long boardId;
	private String content;
}
