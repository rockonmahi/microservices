package com.escola.authserver.repository;

import com.escola.authserver.entity.RegisteredUsers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisteredUsersRepository extends MongoRepository<RegisteredUsers, String> {

    List<RegisteredUsers> findByUserNameAndAccountLock(String userName, int accountLock);

}
