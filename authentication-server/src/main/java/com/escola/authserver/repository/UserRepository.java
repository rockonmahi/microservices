package com.escola.authserver.repository;

import com.escola.authserver.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    List<Users> findByUserNameAndAccountLock(String userName, int accountLock);

}
