package com.sparta.board10.controller;

import com.sparta.board10.dto.Comment.CommentResponseDto;
import com.sparta.board10.dto.Comment.CommentSaveRequestDto;
import com.sparta.board10.dto.Comment.CommentSaveResponseDto;
import com.sparta.board10.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public CommentSaveResponseDto saveComment(@PathVariable Long boardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.saveComment(boardId, commentSaveRequestDto);
    }

    @GetMapping("/boards/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }
}
