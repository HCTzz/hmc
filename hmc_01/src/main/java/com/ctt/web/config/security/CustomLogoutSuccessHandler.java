package com.ctt.web.config.security;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.EncryptUtil;
import com.ctt.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Administrator
 * @Description
 * @create 2020-05-29 下午 1:56
 */
@Service
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static Logger log = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            removeAuthSession(authentication, sessionRegistry);
            httpServletRequest.getSession().invalidate();
        }
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20000);
        ResponseUtils.out(httpServletRequest,httpServletResponse,resBean);

    }

    private void removeAuthSession(Authentication authentication, SessionRegistry sessionRegistry){
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
        // there is only 1 session allowed
        if (sessions.size() > 0) {
            log.debug("removing session {} from registry", sessions.get(0).getSessionId());
            sessionRegistry.removeSessionInformation(sessions.get(0).getSessionId());
        }
    }

}
