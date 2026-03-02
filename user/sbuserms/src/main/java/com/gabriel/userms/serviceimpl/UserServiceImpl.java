package com.gabriel.userms.serviceimpl;
import com.gabriel.userms.entity.UserData;
import com.gabriel.userms.model.User;
import com.gabriel.userms.repository.UserDataRepository;
import com.gabriel.userms.service.UserService;
import com.gabriel.userms.transform.TransformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserDataRepository userDataRepository;
	@Autowired
	TransformUserService tansformUserService;
	@Override
	public User[] getAll() {
		List<UserData> usersData = new ArrayList<>();
		List<User> users = new ArrayList<>();
		userDataRepository.findAll().forEach(usersData::add);
		Iterator<UserData> it = usersData.iterator();
		while(it.hasNext()) {
			UserData userData = it.next();
			User user = tansformUserService.transform(userData);
			users.add(user);
		}
		User[] array = new User[users.size()];
		for  (int i=0; i<users.size(); i++){
			array[i] = users.get(i);
		}
		return array;
	}
	@Override
	public User create(User user) {
		logger.info(" add:Input " + user.toString());
		UserData userData = tansformUserService.transform(user);
		userData = userDataRepository.save(userData);
		logger.info(" add:Input " + userData.toString());
			User newUser = tansformUserService.transform(userData);
		return newUser;
	}

	@Override
	public User update(User user) {
		User updatedUser = null;
		int id = user.getId();
		Optional<UserData> optional  = userDataRepository.findById(user.getId());
		if(optional.isPresent()){
			UserData originalUserData = tansformUserService.transform(user);
			originalUserData.setCreated(optional.get().getCreated());
			UserData userData = userDataRepository.save(originalUserData);
			updatedUser = tansformUserService.transform(userData);
		}
		else {
			logger.error("User record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedUser;
	}

	@Override
	public User get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		User user = null;
		Optional<UserData> optional = userDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			user = tansformUserService.transform(optional.get());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return user;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<UserData> optional = userDataRepository.findById(id);
		if( optional.isPresent()) {
			userDataRepository.delete(optional.get());
			logger.info(" Successfully deleted User record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate user with id:" +  Integer.toString(id));
		}
	}
}
