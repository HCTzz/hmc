package com.ctt.response;

import com.ctt.constant.SystemStatusEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className WebResBean
 * @Description web端响应BEAN
 * @Author Administrator
 * @Date 2019-08-07 17:14
 * @Version 1.0
 */
public class WebResBean {

    private Map<String, String> errors = Collections.synchronizedMap(new LinkedHashMap<String, String>());

    private Object data = "1";

    private int code;

    private String message;

    public WebResBean() {
    }

    ;

    private WebResBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static WebResBean createResBean(SystemStatusEnum senum) {
        return new WebResBean(senum.getCode(), senum.getMessage());
    }

    public WebResBean(int code, String obj, Map<String, String> errors) {
        this.code = code;
        this.data = obj;
        this.errors = errors;
    }

    public boolean isValid() {
        return errors.entrySet().isEmpty();
    }

    public boolean pass() {
        return this.isValid();
    }

    public String getErrors(String splitter) {
        if (StringUtils.isBlank(splitter)) {
            splitter = "\\,";
        }
        return errors.entrySet().stream().map(e -> e.getValue()).collect(Collectors.joining(splitter));
    }

    public void addError(String key, String error) {
        errors.put(key, error);
    }

    public void addError(String error) {
        addError(error, error);
    }

    public Map<String, String> getErrorsMap() {
        return this.errors;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public Object getData() {
        return this.data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
