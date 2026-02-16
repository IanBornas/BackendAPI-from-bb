package com.gabriel.userms.repository;
import com.gabriel.userms.entity.UserData;
import org.springframework.data.repository.CrudRepository;
public interface UserDataRepository extends CrudRepository<UserData,Integer> {}