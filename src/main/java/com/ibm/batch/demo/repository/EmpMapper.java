package com.ibm.batch.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ibm.batch.demo.domain.Emp;

@Mapper
public interface EmpMapper {
	public List<Emp> findAll();
}
