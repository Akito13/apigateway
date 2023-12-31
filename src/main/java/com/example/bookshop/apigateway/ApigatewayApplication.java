package com.example.bookshop.apigateway;

import com.example.bookshop.apigateway.config.AuthorizationHeaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;


@SpringBootApplication
//@EnableWebSecurity
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator bookShopRouteConfig(RouteLocatorBuilder locatorBuilder, AuthorizationHeaderFilter authorizationHeaderFilter){
		return locatorBuilder.routes()
				.route(r -> r.path("/bookshop/api/sach/**","/bookshop/api/loai/**")
						.and().method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH)
						.filters(f -> f
								.rewritePath("/bookshop/api/sach/(?<segment>.*)", "/api/sach/${segment}")
								.rewritePath("/bookshop/api/loai/(?<segment>.*)", "/api/loai/${segment}")
								.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config("ROLE_ADMIN"))))
						.uri("lb://SACH"))
				.route(r -> r.path("/bookshop/api/sach/**", "/bookshop/api/loai/**")
						.and().method(HttpMethod.GET)
						.filters(f -> f
								.rewritePath("/bookshop/api/sach/(?<segment>.*)", "/api/sach/${segment}")
								.rewritePath("/bookshop/api/loai/(?<segment>.*)", "/api/loai/${segment}"))
						.uri("lb://SACH"))
				.route(r -> r.path("/bookshop/api/cart/**")
						.filters(f -> f
								.rewritePath("/bookshop/api/cart/(?<segment>.*)", "/api/cart/${segment}")
								.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config("ROLE_USER"))))
						.uri("lb://CART"))
				.route(r -> r
						.path("/bookshop/api/account/**")
						.filters(f -> f.rewritePath("/bookshop/api/account/(?<segment>.*)", "/api/account/${segment}"))
						.uri("lb://ACCOUNT"))
				.route(r -> r.path("/bookshop/api/confirm/**")
						.filters(f -> f.rewritePath("/bookshop/api/confirm/(?<segment>.*)", "/api/confirm/${segment}"))
						.uri("lb://MAIL-SERVICE"))
				.route(r -> r.path("/bookshop/api/order/**")
						.filters(f -> f.rewritePath("/bookshop/api/order/(?<segment>.*)", "/api/order/${segment}"))
						.uri("lb://ORDER"))
				.route(r -> r.path("/bookshop/api/statistics/**")
						.filters(f -> f.rewritePath("/bookshop/api/statistics/(?<segment>.*)", "/api/statistics/${segment}"))
						.uri("lb://STATISTICS"))
				.build();
	}
}
