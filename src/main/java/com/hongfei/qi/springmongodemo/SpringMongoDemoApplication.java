package com.hongfei.qi.springmongodemo;

import com.hongfei.qi.springmongodemo.convert.MoneyReadConverter;
import com.hongfei.qi.springmongodemo.model.Coffee;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringMongoDemoApplication implements ApplicationRunner {

	@Autowired
	MongoTemplate mongoTemplate;

	@Bean
	public MongoCustomConversions mongoCustomConversions(){
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}

	public static void main(String[] args) {
		
		SpringApplication.run(SpringMongoDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Coffee coffee = Coffee.builder()
				.name("natie")
				.price(Money.of(CurrencyUnit.of("CNY"), 25))
				.createTime(new Date())
				.updateTime(new Date())
				.build();
		Coffee insert = mongoTemplate.insert(coffee);
		log.info("after insert = {}",insert);

		Query query = Query.query(Criteria.where("name").is("natie"));
		List<Coffee> coffees = mongoTemplate.find(query, Coffee.class);
		log.info("query coffees = {}",coffees);

		//更新
		UpdateResult updateResult = mongoTemplate.updateFirst(query
				, new Update().set("price", Money.of(CurrencyUnit.of("CNY"), 30))
						.currentDate("updateTime")
				, Coffee.class);
		log.info("updateResult = {}",updateResult);
	}
}
