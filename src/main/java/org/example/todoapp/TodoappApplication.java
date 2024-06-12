package org.example.todoapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@SpringBootApplication
public class TodoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}
	@Bean
	public CommandLineRunner run(){
		return args -> {
			String region= "ap-northeast-2";

			SsmClient ssmClient = SsmClient.builder()
					.region(Region.of(region))
					.build();

			System.out.println("todo_DB_USERNAME:::"+getPamameterValue(ssmClient, "/todo/config/DB_USERNAME"));
			System.out.println("todo_DB_PASSWORD:::"+getPamameterValue(ssmClient, "/todo/config/DB_PASSWORD"));

		};
	}
	private String getPamameterValue(SsmClient ssmClient, String parameterName){
		GetParameterRequest parameterRequest= GetParameterRequest.builder()
				.name(parameterName)
				.withDecryption(true)
				.build(); //파라미터 리퀘스트를 만들어줌
		GetParameterResponse parameterResponse= ssmClient.getParameter(parameterRequest); //파라미터 리퀘스트를 보내고 리스폰스를 받음
		return parameterResponse.parameter().value(); //받은 리스폰스에서 파라미터의 값을 리턴
//		return ssmClient.getParameter(request -> request.name(parameterName)).parameter().value();
	}
}
