package at.ac.tuwien.big.ame.dyncs.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Pointcut("within(@org.springframework.stereotype.Repository *)" +
      " || within(@org.springframework.stereotype.Service *)" +
      " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void springBeanPointcut() {

  }

  @Pointcut("within(at.ac.tuwien.big.ame.somqm.server.dao..*)" +
      " || within(at.ac.tuwien.big.ame.somqm.server.service..*)" +
      " || within(at.ac.tuwien.big.ame.somqm.server.rest..*)")
  public void applicationPackagePointcut() {

  }

  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    logger.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL",
        e.getMessage(), e);
  }

  @Around("applicationPackagePointcut() && springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    logger.trace("Enter: {}.{}() with argument[s] = {}",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    try {
      Object result = joinPoint.proceed();
      boolean logResult = true;
      String fullMethodName =
          joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature()
              .getName();
      List<String> methodsIgnoredResult = new ArrayList<>();
      methodsIgnoredResult
          .add("UmlModelController.getContent");
      methodsIgnoredResult
          .add("RequirementsServiceImpl.getContent");
      if (methodsIgnoredResult.contains(fullMethodName)) {
        logResult = false;
      }
      logger
          .trace("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
              joinPoint.getSignature().getName(), (logResult ? result : "..."));
      return result;
    } catch (IllegalArgumentException e) {
      logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
      throw e;
    }
  }
}