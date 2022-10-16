package com.laith.AdminDashboard.Repositeries;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.laith.AdminDashboard.Models.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
	User findByUsername(String username);
	
	List<User> findAll();
}
