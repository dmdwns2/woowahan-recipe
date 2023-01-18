package com.woowahan.recipe.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "save_file")
    private String fileName;

    @Column(name = "origin_file")
    private String originFileName;  // 원본 파일 이름

    @Column(name = "save_file")
    private String saveFileName;  // 화면에 보여주는 이름
}
