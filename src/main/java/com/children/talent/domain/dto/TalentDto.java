package com.children.talent.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TalentDto {

    int num;
    String article;
    String name;
    int entity;
    String nums;



    public TalentDto(int num, String article, String name, int entity) {
        this.num = num;
        this.article = article;
        this.name = name;
        this.entity = entity;
    }

    public TalentDto(String name, String nums) {
        this.name = name;
        this.nums = nums;
    }


}