package com.jzy.sup.starter.log;



import com.jzy.sup.common.model.ResponseResult;
import com.jzy.sup.common.model.ServiceResult;
import com.jzy.sup.framework.exception.SupException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * <b>功能：</b><br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Aspect
public class logAspectAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static HashMap<String, StringBuffer> requestMappers = new HashMap<String, StringBuffer>();

    private ThreadLocal<StringBuffer> prefix = new ThreadLocal();

    private static String prefix_flag = "---";
    @Autowired
    private MessageSource messageSource;

//    @Autowired
//    private MessageProducer logSyncSender;

    @Pointcut(value = "execution(* com.jzy..controller.*Controller.*(..))")
    public void controllerPointCut() {
        //Controller层日志拦截及异常统一处理
    }

    @Pointcut(value = "execution(* com.jzy..service..*Service.*(..))||" +
            "execution(* com.jzy..dao.*Dao.*(..))")
    public void servicePointCut() {
        //Service层日志拦截及异常统一处理
    }

    @Around(value = "controllerPointCut()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuffer p = prefix.get() == null ? new StringBuffer("") : prefix.get();
        prefix.set(p.append(prefix_flag));
        StringBuffer requestMapper = getRequestMapper(proceedingJoinPoint);
        StringBuffer sb = new StringBuffer();
        sb.append(p).append(requestMapper);
        for (Object object : proceedingJoinPoint.getArgs()) {
            if (null == object) {
                object = "null";
            }
            sb.append("，param:[" + object + "]");
        }
        logger.debug(sb.toString());
        ResponseEntity<ResponseResult> result = null;
        long start = System.currentTimeMillis();
        try {
            result = (ResponseEntity<ResponseResult>) proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("系统异常", throwable);
            result = ResponseEntity.ok().body(ResponseResult.failure());
            throw throwable;
        } finally {
            Method method_r = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
//            Log log_ = method_r.getAnnotation(Log.class);
//            if (log_ != null) {
//                LogInfo log = new LogInfo();
//                log.setFunction(log_.fun().getName());
//                log.setFunctionCode(log_.fun().getCode());
//                log.setModule(log_.module().getName());
//                log.setModuleCode(log_.module().getCode());
//                log.setOpt(log_.opt().getName());
//                log.setOptCode(log_.opt().getCode());
//                log.setResCode(result.getBody().getCode());
//                log.setResMsg(result.getBody().getMsg());
////                log.setType(log_.persistence().getType());
//                log.setCreateTime(new Date());
//                if (RequestContext.getPrivilege() != null) {
//                    log.setUserName(RequestContext.getPrivilege().getUsername());
//                    log.setFullName(RequestContext.getPrivilege().getFullname());
//                    log.setIp(RequestContext.getPrivilege().getIp());
//                    log.setMobile(RequestContext.getPrivilege().getMobile());
//                } else {
//                    logger.warn("no RequestContext info !");
//                }
//                logSyncSender.produce(log);
//            }

            StringBuffer prefix_flag_ = prefix.get();
            prefix_flag_.delete(prefix_flag_.length() - prefix_flag.length(), prefix_flag_.length());
            prefix.set(prefix_flag_);
            long end = System.currentTimeMillis();
            logger.info(new StringBuffer().append(p).append(requestMapper).append(", cost({}) ").toString(), end - start);
        }
        return result;
    }

    @Around(value = "servicePointCut()")
    public Object doServiceAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuffer p = prefix.get() == null ? new StringBuffer("") : prefix.get();
        prefix.set(new StringBuffer().append(p).append(prefix_flag));
        StringBuffer requestMapper = getRequestMapper(proceedingJoinPoint);
        StringBuffer sb = new StringBuffer();
        sb.append(p).append(requestMapper);
        for (Object object : proceedingJoinPoint.getArgs()) {
            if (null == object) {
                object = "null";
            }
            sb.append("，param:[" + object + "]");
        }
        logger.debug(sb.toString());
        Object result = null;
        long start = System.currentTimeMillis();
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof SupException) {
                String code = ((SupException) throwable).getMessageCode();
                logger.error(ResponseResult.getMessage(messageSource, code), throwable);
                result = ServiceResult.failure(code);
            } else {
                logger.error("系统异常", throwable);
                result = ServiceResult.failure();
            }
            throw throwable;
        } finally {
            StringBuffer prefix_flag_ = prefix.get();
            prefix_flag_.delete(prefix_flag_.length() - prefix_flag.length(), prefix_flag_.length());
            prefix.set(prefix_flag_);
            long end = System.currentTimeMillis();
            logger.info(new StringBuffer().append(p).append(requestMapper).append(", cost({}) ").toString(), end - start);
        }
        return result;
    }

    private StringBuffer getRequestMapper(ProceedingJoinPoint proceedingJoinPoint) {
        String objName = proceedingJoinPoint.getSignature().getDeclaringType().getName();
        Class clazz = proceedingJoinPoint.getSignature().getDeclaringType();
        RequestMapping rm = (RequestMapping) clazz.getAnnotation(RequestMapping.class);

        // 类上没有 RequestMapping 该注解
        String clazzPath = "";
        if (rm == null) {
            clazzPath = "";
        } else {
            if(rm.path().length > 0){
                clazzPath = rm.path()[0];
            }
            if(rm.value().length > 0){
                clazzPath = rm.value()[0];
            }
        }

        Method method_r = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        String methodName = method_r.getName();
        String key = objName + ":" + methodName;
        if (requestMappers.containsKey(key))
            return requestMappers.get(key);
        RequestMapping rm_m = method_r.getAnnotation(RequestMapping.class);
        StringBuffer sb;


        if (rm_m != null)


            sb = new StringBuffer(key)
                    .append("([P]")
                    .append(clazzPath)
                    .append(rm_m.path().length>0?rm_m.path()[0]:null)
                    .append("[M]")
                    .append(rm_m.value()[0])
                    .append("[p]")
                    .append(rm_m.produces().length>0?rm_m.produces()[0]:null)
                    .append(")");
        else
            sb = new StringBuffer(key);
        requestMappers.put(key, sb);
        return sb;
    }

}
