package com.yang.mapper;

import com.yang.pojo.SensitiveWord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensitiveWordMapper {
    List<SensitiveWord> querySensitiveWordList();
}
