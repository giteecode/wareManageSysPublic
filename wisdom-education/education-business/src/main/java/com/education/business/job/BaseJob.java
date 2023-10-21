package com.education.business.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class BaseJob extends QuartzJobBean {

     final Logger logger = LoggerFactory.getLogger(this.getClass());
}
