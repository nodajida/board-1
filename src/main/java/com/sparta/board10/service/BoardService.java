package com.sparta.board10.service;
import com.sparta.board10.dto.Board.*;
import lombok.RequiredArgsConstructor;

import com.sparta.board10.dto.Board.BoardSaveRequestDto;
import com.sparta.board10.dto.Board.BoardSaveResponseDto;
import com.sparta.board10.dto.Board.BoardSimpleResponseDto;
import com.sparta.board10.dto.Board.BoardUpdateResponseDto;
import com.sparta.board10.dto.Board.BoardUpdateRequestDto;

import com.sparta.board10.entity.Board;
import com.sparta.board10.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto) {
        Board newBoard = new Board(
                boardSaveRequestDto.getTitle(),

                boardSaveRequestDto.getContents()
        );
        Board savedBoard = boardRepository.save(newBoard);

        return new BoardSaveResponseDto(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getContents());
    }

    public Page<BoardSimpleResponseDto> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Board> boards = boardRepository.findAllByOrderByModifiedAtDesc(pageable);

        return boards.map(board -> new BoardSimpleResponseDto(
                board.getId(),
                board.getTitle(),
                board.getComments()
        ));

    }

    @Transactional
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NullPointerException("아 보드 없다고"));
        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContents());

        return new BoardUpdateResponseDto(board.getId(), board.getTitle(), board.getContents());
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new NullPointerException("아 보드 없다고");
        }

        boardRepository.deleteById(boardId);
    }
}
