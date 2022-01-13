package study.tdd.simpleboard.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import study.tdd.simpleboard.exception.common.GeneralParameterErrorCode;
import study.tdd.simpleboard.exception.post.InvalidPostParameterException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

@Slf4j
@Component
@Aspect
public class BindingResultAop {


    /**
     *
     * api 패키지 내부 컨트롤러 실행전에 실행
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("execution(* study.tdd.simpleboard.api..*Controller.*(..))")
    public void bindingBefore(JoinPoint joinPoint) throws Throwable {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;

                if (result.hasErrors()) {
                    throw new InvalidPostParameterException(result, GeneralParameterErrorCode.INVALID_PARAMETER);
                }

            }
        } // end for
    } // end

}
