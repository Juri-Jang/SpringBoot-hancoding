package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class BoardService {
    @Autowired //사용시 new로 생성하지 않아도 스프링빈이 자동으로 의존성 주입해줌
    private BoardRepository boardRepository;

    @Transactional
    public void write(Board board){
        boardRepository.save(board);
        System.out.println("서비스 완료");

    }
}
