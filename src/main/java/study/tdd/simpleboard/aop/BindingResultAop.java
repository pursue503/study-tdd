package study.tdd.simpleboard.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import study.tdd.simpleboard.exception.common.GeneralParameterErrorCode;
import study.tdd.simpleboard.exception.post.InvalidPostParameterException;
import study.tdd.simpleboard.exception.post.PostCrudErrorCode;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class BindingResultAop {


    private final Validator validator;

    /**
     *
     * api 패키지 내부 컨트롤러 실행전에 실행
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("execution(* study.tdd.simpleboard.api..*Controller.*(..))")
    public void bindingBefore(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            BindingResult bindingResult = new BeanPropertyBindingResult(arg, arg.getClass().getName());
            validator.validate(arg, bindingResult);
            if (bindingResult.hasErrors()) {
                    throw new InvalidPostParameterException(bindingResult, GeneralParameterErrorCode.INVALID_PARAMETER);
            }
        } // end for
    } // end

}
