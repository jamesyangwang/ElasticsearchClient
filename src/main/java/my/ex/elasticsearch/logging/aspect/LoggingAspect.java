package my.ex.elasticsearch.logging.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

	@Before("execution(* my.ex..*.*(..)) && !execution(* my.ex.elasticsearch.model..*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		log.info(joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName()
				+ "() started.");
	}

	@After("execution(* my.ex..*.*(..)) && !execution(* my.ex.elasticsearch.model..*.*(..))")
	public void logAfter(JoinPoint joinPoint) {
		log.info(joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName()
				+ "() ended.");
	}

	@Around("execution(* my.ex..*.*(..)) && !execution(* my.ex.elasticsearch.model..*.*(..))")
	public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object retVal;
		try {
			retVal = joinPoint.proceed();

		} finally {
			stopWatch.stop();

			StringBuffer logMessage = new StringBuffer();
			logMessage.append(joinPoint.getTarget().getClass().getSimpleName());
			logMessage.append(".");
			logMessage.append(joinPoint.getSignature().getName());
			logMessage.append("() execution time: ");
			logMessage.append(stopWatch.getTotalTimeMillis());
			logMessage.append(" ms");
			log.info(logMessage.toString());
		}

		return retVal;
	}

	@AfterThrowing(pointcut = "execution(* my.ex..*.*(..))", throwing = "ex")
	public void logAfterThrowingAllMethods(Exception ex) throws Throwable {
		log.error(ex.getMessage(), ex);
	}
}
