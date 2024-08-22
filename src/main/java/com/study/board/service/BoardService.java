package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired //사용시 new로 생성하지 않아도 스프링빈이 자동으로 의존성 주입해줌
    private BoardRepository boardRepository;

    //글 작성 처리
    @Transactional
    public void write(Board board){
        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public List<Board> boardList() {
       return boardRepository.findAll();
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

}
