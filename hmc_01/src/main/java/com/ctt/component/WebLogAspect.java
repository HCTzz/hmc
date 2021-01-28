package com.ctt.component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ctt.web.interceptor.SqlLogTheadCache;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author HHF
 * @Description
 * @create 2021-01-09 下午 3:04
 */

@Aspect
@Component
@Order(1)
public class WebLogAspect {

    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("@annotation(com.ctt.component.Log)")
    public void pontCut(){
    }

    @Before(value = "pontCut()")
    public void adBefore(){
        SqlLogTheadCache.setList(new ArrayList<String>(2));
        SqlLogTheadCache.setTimeLocal(System.currentTimeMillis());
    }

    @AfterReturning(value="pontCut()",returning = "result")
    public void doAfterAdvice(JoinPoint joinPoint, Object result){
        WebLog webLog = createWebLog(joinPoint,result);
        Map<String, Object> logMap = createLogMap(webLog,null);
        log.warn(Markers.appendEntries(logMap), JSONObject.toJSON(webLog).toString());
    }

    @AfterThrowing(value = "pontCut()", throwing = "e")
    public void afterThorwingMethod(JoinPoint joinPoint, Exception e) {
        WebLog webLog = createWebLog(joinPoint,null);
        if (e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName() + "." + element.getMethodName();
            int lineNumber = element.getLineNumber();
            String errorPosition = fileName + ":" + lineNumber;
            webLog.setPosition(errorPosition);
            StackTraceElement[] stackTrace = e.getStackTrace();
//            int length = stackTrace.length;
////            int availables = length > 3 ? 3 : length;
////            if(availables > 0){
////                StackTraceElement[] tstack = new StackTraceElement[availables];
////                for (int i = 0 ; i < availables ; i++){
////                    tstack[i] = stackTrace[i];
////                }
////                e.setStackTrace(tstack);
////            }
////            webLog.setE(e);
        }
        Map<String, Object> logMap = createLogMap(webLog,e.getStackTrace());
        log.error(Markers.appendEntries(logMap), JSONObject.toJSON(webLog).toString());
    }

    private Map<String,Object> createLogMap(WebLog webLog,StackTraceElement[] stackTrace){
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("url",webLog.getUrl());
        logMap.put("method",webLog.getMethod());
        logMap.put("parameter",webLog.getParameter());
        logMap.put("spendTime",webLog.getSpendTime());
        logMap.put("descr",webLog.getDescr());
        logMap.put("Trace",stackTrace);
        return logMap;
    }

    private WebLog createWebLog(JoinPoint joinPoint, Object result){
        HttpServletRequest request = getRequest();
        List<String> sqlList = SqlLogTheadCache.getSqlList();
        SqlLogTheadCache.clearSql();
        WebLog webLog = new WebLog();
        webLog.setSqlList(sqlList);
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        //方法描述
        if(method.isAnnotationPresent(Log.class)){
            Log annotation = method.getAnnotation(Log.class);
            webLog.setDescr(annotation.descr());
        }
        //执行时间
        long startTime = SqlLogTheadCache.getTimeLocal();
        SqlLogTheadCache.clearTimeLocal();
        long endTime = System.currentTimeMillis();
        webLog.setSpendTime((int) (endTime - startTime));
        String urlStr = request.getRequestURL().toString();
        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setIp(request.getRemoteUser());
        webLog.setMethod(request.getMethod());
        webLog.setParameter(getParameter(method, joinPoint.getArgs()));
        webLog.setResult(result);
        webLog.setStartTime(startTime);
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        return webLog;
    }

    private HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Object, Object> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("1",1);
    }

}
