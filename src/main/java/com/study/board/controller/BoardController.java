package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){


        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(@ModelAttribute Board board) {
        boardService.write(board);
        return "";
    }

    @GetMapping("board/list")
    public String boardList(Model model){
        model.addAttribute("list",boardService.boardList()); //실행시 반환된 boardList를 list라는 이름으로 받아서 넘김

        return "boardlist";
    }

}
