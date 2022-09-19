package com.cts.kst.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cts.kst.entity.DbSequence;
import com.cts.kst.entity.KeystoneParam;
import com.cts.kst.repository.KeystoneParamRepository;

@Service
public class KeystoneServiceImpl implements KeystoneService{

	@Autowired
	private KeystoneParamRepository keystoneRepo;
	
	@Autowired
    private MongoOperations mongoOperations;
	
	private static final Logger log = LoggerFactory.getLogger(KeystoneServiceImpl.class);
	
	@Override
	public KeystoneParam saveKeystoneParam(KeystoneParam param) {
		log.info("Entering saveKeystoneParam");
		String id = String.valueOf(getSequenceNumber(KeystoneParam.SEQUENCE_NAME));
		param.setId(id);
		log.info("Exiting saveKeystoneParam");
		return keystoneRepo.save(param);
	}

	public int getSequenceNumber(String sequenceName) {
		
		log.info("Entering getSequenceNumber");
        //get sequence no
        Query query = new Query(Criteria.where("id").is(sequenceName));
        //update the sequence no
        Update update = new Update().inc("seq", 1);
        //modify in document
        DbSequence counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        DbSequence.class);
        log.info("Exiting getSequenceNumber");
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

	@Override
	public List<KeystoneParam> getAllKeystoneParams() {
		 List<KeystoneParam> list = keystoneRepo.findAll();
		return list;
	}
	
}
