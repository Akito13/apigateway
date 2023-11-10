package com.example.bookshop.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator bookShopRouteConfig(RouteLocatorBuilder locatorBuilder){
		return locatorBuilder.routes()
				.route(r -> r.path("/bookshop/api/sach/**")
						.filters(f -> f.rewritePath("/bookshop/api/sach/(?<segment>.*)", "/api/sach/${segment}"))
						.uri("lb://SACH"))
				.route(r -> r.path("/bookshop/api/cart/**")
						.filters(f -> f.rewritePath("/bookshop/api/cart/(?<segment>.*)", "/api/cart/${segment}"))
						.uri("lb://CART"))
				.route(r -> r.path("/bookshop/api/account/**")
						.filters(f -> f.rewritePath("/bookshop/api/account/(?<segment>.*)", "/api/account/${segment}"))
						.uri("lb://ACCOUNT"))
				.route(r -> r.path("/bookshop/api/confirm/**")
						.filters(f -> f.rewritePath("/bookshop/api/confirm/(?<segment>.*)", "/api/confirm/${segment}"))
						.uri("lb://MAIL-SERVICE"))
				.build();
	}
}
