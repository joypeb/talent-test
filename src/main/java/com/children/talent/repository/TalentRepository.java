package com.children.talent.repository;

import com.children.talent.domain.Talent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalentRepository extends JpaRepository<Talent, Integer> {

    List<Talent> findAllByOrderByName();
    List<Talent> findAllByOrderByArticle();

}
