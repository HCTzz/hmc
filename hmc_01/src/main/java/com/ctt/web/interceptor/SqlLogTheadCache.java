package com.ctt.web.interceptor;

import java.util.List;

/**
 * @author HHF
 * @Description
 * @create 2021-01-09 下午 4:59
 */
public class SqlLogTheadCache {

    private static final InheritableThreadLocal<List<String>> logThread = new InheritableThreadLocal();

    private static final ThreadLocal<Long> timeLocal = new ThreadLocal<>();

    public static void setTimeLocal(long startTime){
        timeLocal.set(startTime);
    }

    public static Long getTimeLocal(){
        return timeLocal.get();
    }

    public static void clearTimeLocal(){
        timeLocal.remove();
    }

    public static void addSql(String sql){
        List<String> list = logThread.get();
        if(list != null) {
            list.add(sql);
        }
    }

    public static void clearSql(){
        logThread.remove();
    }

    public static void setList(List<String> sqlList){
        logThread.set(sqlList);
    }

    public static List<String> getSqlList(){
        return logThread.get();
    }

    public static void main(String[] args) {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        System.out.println(stringThreadLocal.get());
    }

}
