package com.escola.authserver.repository;

import com.escola.authserver.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    List<User> findByUserNameAndRecordStatus(String userName, int i);

}
