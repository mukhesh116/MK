package com.mufg.us.amh.vln_ced_401.processer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mufg.us.amh.vln_ced_401.entity.User;
import com.mufg.us.amh.vln_ced_401.repository.UserRepository;
import com.mufg.us.amh.vln_ced_401.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CANLOANProcessor implements Processor{
	
	
	@Autowired
	UserRepository repo;
	
	public void process(Exchange exchange) throws Exception {
		log.info("CANLOANProcessor :: process() :: Init");
		exchange.getIn().setBody(CommonUtil.toGson(findAllUsers()));
		log.info("CANLOANProcessor :: process() :: Ends");
	}

	public List<User> findAllUsers() {
		 return  StreamSupport.stream(repo.findAll().spliterator(), false)
		    .collect(Collectors.toList());		
	}
	
}
