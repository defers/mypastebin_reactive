package com.defers.mypastebin.aspect;

import com.defers.mypastebin.util.MessagesUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
* Aspect for logging execution methods
* @author defers
*
*/
@Aspect
@Component
public class LoggingAspect {

    private final boolean logEnabled;
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    public LoggingAspect(@Value(value = "${app.all-methods-log.enabled}") boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    @Before(value = "execution(* com.defers.mypastebin..*..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        if (logEnabled) {
            log.info(MessagesUtils.getFormattedMessage("Method starts: %s", joinPoint.getSignature()));
        }
    }
    @After(value = "execution(* com.defers.mypastebin..*..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        if (logEnabled) {
            log.info(MessagesUtils.getFormattedMessage("Method ends: %s", joinPoint.getSignature()));
        }
    }
}
