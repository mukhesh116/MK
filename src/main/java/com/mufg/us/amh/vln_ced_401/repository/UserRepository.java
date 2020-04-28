package com.mufg.us.amh.vln_ced_401.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mufg.us.amh.vln_ced_401.entity.User;
import com.sun.xml.bind.v2.model.core.ID;


@Repository
public interface UserRepository extends CrudRepository<User, ID> {

}
