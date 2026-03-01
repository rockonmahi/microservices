package com.escola.authserver.repository;

import com.escola.authserver.entity.RegisteredClients;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredClientsRepository extends MongoRepository<RegisteredClients, String> {

    Optional<RegisteredClients> findByClientId(String clientId);

}
