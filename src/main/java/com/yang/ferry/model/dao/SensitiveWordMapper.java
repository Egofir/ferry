package com.yang.ferry.model.dao;

import com.yang.ferry.model.pojo.SensitiveWord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensitiveWordMapper {
    List<SensitiveWord> querySensitiveWordList();
}
