package com.example.taco_cloud_client.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {
    private final OAuth2AuthorizedClientService clientService;

    public RestTemplateFactory(OAuth2AuthorizedClientService clientService){
        this.clientService=clientService;
    }

    public RestTemplate create(){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName()
                );

                if (client != null && client.getAccessToken() != null) {
                    request.getHeaders().setBearerAuth(client.getAccessToken().getTokenValue());
                }
            }

            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
