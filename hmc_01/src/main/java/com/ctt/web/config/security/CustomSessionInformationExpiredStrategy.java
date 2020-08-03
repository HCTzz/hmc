package com.ctt.web.config.security;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author HHF
 * @Description
 * @create 2020-06-11 下午 5:08
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    public CustomSessionInformationExpiredStrategy(){

    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        System.out.println(event.getSessionInformation().isExpired());
        System.out.println(event);
    }
}
