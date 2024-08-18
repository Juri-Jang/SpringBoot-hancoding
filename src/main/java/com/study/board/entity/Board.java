package com.study.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity //테이블
@Data //get으로 데이터 가져오기 가능
public class Board {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql, maria db에서 사용하는 것
    private Integer id; //필드

    private String title; //필드

    private String content; //필드
}
