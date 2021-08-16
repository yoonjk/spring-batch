package com.ibm.batch.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ibm.batch.demo.domain.Bonus;

@Mapper
public interface BonusMapper {
    public void insert(Bonus bonus);
}