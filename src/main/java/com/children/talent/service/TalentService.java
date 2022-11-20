package com.children.talent.service;

import com.children.talent.domain.Talent;
import com.children.talent.domain.dto.TalentDto;
import com.children.talent.repository.TalentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@Slf4j
public class TalentService {

    private final TalentRepository talentRepository;
    private final JdbcTemplate jdbcTemplate;

    /*public String articleList(int num) {
        switch (num) {
            case 1 :
                return "비눗방울 장난감";
            case 2:
                return "슬라임 장난감";
                case 1231 : return "푸쉬팝";
                case 1231 : return "거짓말탐지기";
                case 1231 : return "짹짹이 장난감";
                case 1231 : return "할리갈리";
                case 1231 : return "의자의 탑 게임";
                case 1231 : return "스포츠 핑거게임";
                case 1231 : return "폴리몽키게임";
                case 1231 : return "RC미니카";
            case 1231 : return "공룡장난감";
                case 1231 : return "젤리";
                case 1231 : return "떡꼬치";
                case 1231 : return "팝콘치킨";
                case 1231 : return "동물풍선머리띠세트";
                case 1231 : return "축구공";
                case 1231 : return "아동용핸드백";
                case 1231 : return "어린이용성경책";
                case 1231 : return "LCD패드";
                case 1231 : return "핫팩";
            case 1231 : return "모찌인형(대)";
            case 1231 : return "모찌인형(소)";
            case 1231 : return "배드민턴";
            case 1231 : return "반지갑";
            case 1231 : return "설빙";
            case 1231 : return "GS25";
            case 1231 : return "베라";
            case 1231 : return "그립톡";
            case 1231 : return "스티커";
            case 1231 : return "스케치북",
            case 1231 : return "필통";
            case 1231 : return "곱창밴드";
            case 1231 : return "십자수";
            case 1231 : return "보석십자수";
            case 1231 : return "레고";
            case 1231 : return "한복키링";
            case 1231 : return "곰돌이 볼펜";
        }
    }*/

    String[] articleArr = {"", "비눗방울 장난감", "슬라임 장난감", "푸쉬팝", "거짓말탐지기", "짹짹이 장난감", "할리갈리", "의자의 탑 게임", "스포츠 핑거게임", "폴리몽키게임", "RC미니카",
            "공룡장난감", "젤리", "떡꼬치", "팝콘치킨", "동물풍선머리띠세트", "축구공", "아동용핸드백", "어린이용성경책", "LCD패드", "핫팩",
            "모찌인형(대)", "모찌인형(소)", "배드민턴", "반지갑", "설빙", "GS25", "베라", "그립톡", "스티커", "스케치북",
            "필통", "곱창밴드", "십자수", "보석십자수", "레고", "한복키링", "곰돌이 볼펜"};


    @Autowired
    public TalentService(TalentRepository talentRepository, JdbcTemplate jdbcTemplate) {
        this.talentRepository = talentRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Transactional
    public String[] addRegistraion(String name, String articleNums) {
        String[] returnStr = new String[2];


        String[] nums = articleNums.split(" ");

        log.info("배열화 된 nums " + Arrays.toString(nums));

        List<Talent> list = new ArrayList<>();
        HashMap<String, Integer> articleMap = new HashMap<>();
        Talent talent = null;
        int cnt = 0;

        for (String num : nums) {
            int parseNum = Integer.parseInt(num);
            try {
                if (parseNum > 37) throw new NumberFormatException();
                articleMap.put(articleArr[parseNum], articleMap.getOrDefault(articleArr[parseNum], 0) + 1);
            } catch (Exception e) {
                log.error("숫자의 범위 확인");
                log.error(e.getMessage());
                returnStr[0] = "error";
                returnStr[1] = "숫자의 범위를 다시 확인해주세요";
                return returnStr;
            }
        }

        log.info("hashMap 출력 : " + articleMap.toString());

        List<String> articleList = Arrays.asList(articleArr);

        for (Map.Entry<String, Integer> entry : articleMap.entrySet()) {
            talent = Talent.builder()
                    .num(articleList.indexOf(entry.getKey()))
                    .article(entry.getKey())
                    .name(name)
                    .entity(entry.getValue())
                    .build();
            list.add(talent);
        }

        List<Talent> talents = talentRepository.saveAll(list);

        log.info("talents = " + talents.toString());

        returnStr[0] = "list";
        returnStr[1] = "";
        return returnStr;
    }

    public List<Talent> listArticle() {
        List<Talent> talents = jdbcTemplate.query(
                "select num, sum(entity) as entity from talent group by num order by num",
                new RowMapper<Talent>() {
                    @Override
                    public Talent mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Talent talent = new Talent();
                        talent.setNum(rs.getInt("num"));
                        talent.setArticle(articleArr[rs.getInt("num")]);
                        talent.setEntity(rs.getInt("entity"));
                        return talent;
                    }
                }
        );

        log.info("article 기준으로 정렬 : " + talents.toString());

        return talents;
    }

    public List<Talent> listName() {
        List<Talent> talents = jdbcTemplate.query(
                "select distinct name from talent",
                new RowMapper<Talent>() {
                    @Override
                    public Talent mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Talent talent = new Talent();
                        talent.setName(rs.getString("name"));
                        return talent;
                    }
                }
        );
        return talents;
    }

    public List<Talent> selectName(String name) {
        List<Talent> talents = jdbcTemplate.query(
                "select num,entity from talent where name = ? order by num",
                new RowMapper<Talent>() {
                    @Override
                    public Talent mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Talent talent = new Talent();
                        talent.setNum(rs.getInt("num"));
                        talent.setArticle(articleArr[rs.getInt("num")]);
                        talent.setEntity(rs.getInt("entity"));
                        return talent;
                    }
                }, name
        );
        return talents;
    }

    public List<Talent> listAll() {
        List<Talent> talents = jdbcTemplate.query(
                "select name,article,entity from talent order by name",
                new RowMapper<Talent>() {
                    @Override
                    public Talent mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Talent talent = new Talent();
                        talent.setName(rs.getString("name"));
                        talent.setArticle(rs.getString("article"));
                        talent.setEntity(rs.getInt("entity"));
                        return talent;
                    }
                }
        );
        return talents;
    }

    public boolean searchName(String name) {
        int result = talentRepository.countByNameContaining(name);

        if(result == 0) {
            return true;
        } else{
            return false;
        }
    }
}
