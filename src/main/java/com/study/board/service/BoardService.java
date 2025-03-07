package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired //사용시 new로 생성하지 않아도 스프링빈이 자동으로 의존성 주입해줌
    private BoardRepository boardRepository;

    //글 작성 처리
    @Transactional
    public void write(Board board, MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files"; //저장 경로
        UUID uuid = UUID.randomUUID(); //랜덤으로 이름 생성 (식별자)
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName); //프로젝트 경로에 fileName 이름으로 파일 저장됨
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) {
       return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchKeyword(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
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
