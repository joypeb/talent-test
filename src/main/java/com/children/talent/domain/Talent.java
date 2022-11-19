package com.children.talent.domain;

import com.children.talent.domain.dto.TalentDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "talent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Talent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int num;
    @Column(length = 20)
    String article;
    @Column(length = 7)
    String name;
    int entity;


    public Talent(int num, String article, String name, int entity) {
        this.num = num;
        this.article = article;
        this.name = name;
        this.entity = entity;
    }

    public Talent(String article, int entity) {
        this.article = article;
        this.entity = entity;
    }

    public Talent(String name) {
        this.name = name;
    }
}
