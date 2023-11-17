package com.example.bookshop.apigateway.config;

import com.example.bookshop.apigateway.CommonConstants;
import com.example.bookshop.apigateway.dto.AccountDto;
import com.example.bookshop.apigateway.exception.InvalidBodyException;
import com.example.bookshop.apigateway.exception.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Config {
        private String role;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
//                return onError(exchange, "No Authorization Bearer", HttpStatus.UNAUTHORIZED);
                throw new InvalidBodyException(HttpStatus.UNAUTHORIZED, "Không có quyền thực hiện thao tác này");
            }
            String authorizationHeader = request.getHeaders().get("Authorization").get(0);
            String jwt = authorizationHeader.replace("Bearer", "");
            System.out.println(authorizationHeader);
            String role = getAccountFromJwt(jwt).getAuthority();
            if(!config.getRole().equals(role)){
                System.out.println(role);
                throw new InvalidBodyException(HttpStatus.FORBIDDEN, "Bạn không có quyền truy cập");
            }
            System.out.println("No error");
            return chain.filter(exchange);
        };
    }

    private AccountDto getAccountFromJwt(String jwt) {
//        boolean isValid = true;
        AccountDto accountDto = new AccountDto();

        String tokenSecret = CommonConstants.JWT_KEY;
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build();
        try {
            Jwt<Header, Claims> parsedToken = jwtParser.parse(jwt);
            Claims claims = parsedToken.getBody();
            accountDto.setId(claims.get("id", Long.class));
            accountDto.setAuthority(claims.get("authority", String.class));
        } catch (Exception e) {
            throw new InvalidJwtTokenException(HttpStatus.FORBIDDEN ,"Hết hạn phiên đăng nhập");
        }

        return accountDto;
    }
}
