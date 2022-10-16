package com.laith.AdminDashboard.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.laith.AdminDashboard.Models.User;
import com.laith.AdminDashboard.Repositeries.RoleRepository;
import com.laith.AdminDashboard.Repositeries.UserRepository;
 
@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
       
    //4 
    public List<User> findAll() {
    		return userRepository.findAll();
    }
    public User findById(Long id) {
    	Optional<User> potentialUser = userRepository.findById(id);
    	if(potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }
    
    public User updateUser(User u) {
    	return userRepository.save(u);
    }
	public User upgradeUser(User user) {
		user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
		return userRepository.save(user);
	}
	public void deleteUser(User user) {
		userRepository.delete(user);
	}
}



    