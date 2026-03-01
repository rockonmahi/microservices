package com.escola.authserver.repository;

import com.escola.authserver.entity.RegisteredClientSecret;
import com.escola.authserver.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredClientSecretRepository extends MongoRepository<RegisteredClientSecret, String> {

    RegisteredClientSecret findByClientId(String clientId);

}
