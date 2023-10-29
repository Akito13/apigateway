package com.example.bookshop.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApigatewayApplication {

	private final String externalSachServiceApi = "/bookshop/api/sach/";

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator bookShopRouteConfig(RouteLocatorBuilder locatorBuilder){
		return locatorBuilder.routes()
				.route(r -> r.path("/bookshop/api/sach/**")
						.filters(f -> f.rewritePath("/bookshop/api/sach/(?<segment>.*)", "/api/sach/${segment}"))
						.uri("lb://SACH"))
				.build();
	}
}
