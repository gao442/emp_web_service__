package com.itheima.aop;

import com.alibaba.fastjson.JSONObject;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;


@Slf4j
@Aspect
@Component
public class LogAspect {

    //private void pt(){};
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;


    @Around("@annotation(com.itheima.annotation.Log)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        String jwt = httpServletRequest.getHeader("token");
        Map<String, Object> map = JwtUtils.parseJwt(jwt);
        OperateLog operateLog=new OperateLog();
        long begin=System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        operateLog.setOperateUser((Integer) map.get("id"));
        operateLog.setClassName(joinPoint.getTarget().getClass().getName());
        operateLog.setMethodName(joinPoint.getSignature().getName());
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setMethodParams(joinPoint.getArgs().toString());
        operateLog.setCostTime(end-begin);
        operateLog.setReturnValue(JSONObject.toJSONString(result));

        operateLogMapper.insert(operateLog);

        return result;

    }
}
