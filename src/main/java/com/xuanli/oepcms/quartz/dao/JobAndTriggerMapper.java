package com.xuanli.oepcms.quartz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xuanli.oepcms.quartz.entity.JobAndTrigger;
@Mapper
public interface JobAndTriggerMapper {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
