package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm(){
        return "boardwrite"; //해당 html 파일명
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(@ModelAttribute Board board, Model model, MultipartFile file) throws IOException {
        boardService.write(board, file);
        model.addAttribute("message", "글 작성이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        //return "redirect:/board/list"; 글 작성 후 list 페이지로 이동
        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page=0, size=10, sort="id", direction = Sort.Direction.DESC ) Pageable pageable,
                            String searchKeyword){

        Page<Board> list = null;

        //검색하고자 하는 단어가 없을 때 (즉 전체 조회)
        if(searchKeyword == null) {
           list = boardService.boardList(pageable);
        }else{ //검색하고자 하는 단어가 있을 때
            list = boardService.boardSearchKeyword(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //웹사이트에서 보기 편하게 + 1
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list",list); //실행시 반환된 boardList를 list라는 이름으로 받아서 넘김
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);


        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/b oard/view?id=1
    public String boardView(Model model, Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable ("id") Integer id, @ModelAttribute Board board, Model model, MultipartFile file) throws IOException {
        Board boardTemp = boardService.boardView(id); //기존의 글이 담겨져 옴
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp, file);

        //수정 메세지 띄우기
        model.addAttribute("message", "수정이 완료 됐습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

}
