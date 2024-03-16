package com.austin.flashcard.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import java.util.Objects;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

/**
 * @Description:
 * @Author: Austin
 * @Create: 3/13/2024 3:40 PM
 */
@RestController
@Slf4j
public class HomeController {

    @Autowired
    private ReactiveOAuth2AuthorizedClientService clientService;

    @GetMapping("/")
    public String index(WebSession session, @AuthenticationPrincipal OidcUser user){
        var userInfoClaims = user.getUserInfo().getClaims();
        userInfoClaims.forEach((k, v) -> log.info("k:{},v:{}", k, v));
        var idTokenClaims = user.getIdToken().getClaims();
        idTokenClaims.forEach((k,v) -> log.info("k:{},v:{}", k, v));
        log.info("session ID:{}", session.getId());
        var attributes = session.getAttributes();
        if(!Objects.isNull(attributes)){
            var context = (SecurityContext) attributes.get(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
            var token = (OAuth2AuthenticationToken)context.getAuthentication();
            var client = clientService.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getPrincipal().getName());
            log.info("class name:{}", context.getAuthentication().getClass().getName());
            client.subscribe(v -> log.info("token:{}", v.getAccessToken().getTokenValue()));
        }
        return "hello";
    }


}
