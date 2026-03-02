package com.gabriel.userms.transform;
import com.gabriel.userms.entity.UserData;
import com.gabriel.userms.model.User;
import org.springframework.stereotype.Service;
@Service
public class TransformUserServiceImpl implements TransformUserService {
	@Override
	public UserData transform(User user){
		UserData userData = new UserData();
		userData.setId(user.getId());
		userData.setUserId(user.getUserId());
		userData.setUsername(user.getUsername());
		userData.setDisplayName(user.getDisplayName());
		userData.setEmail(user.getEmail());
		userData.setAvatarUrl(user.getAvatarUrl());
		userData.setOnline(user.isOnline());
		userData.setLastSeen(user.getLastSeen());
		userData.setStatusMessage(user.getStatusMessage());
		userData.setDeviceToken(user.getDeviceToken());
		return userData;
	}
	@Override

	public User transform(UserData userData){;
		User user = new User();
		user.setId(userData.getId());
		user.setUserId(userData.getUserId());
		user.setUsername(userData.getUsername());
		user.setDisplayName(userData.getDisplayName());
		user.setEmail(userData.getEmail());
		user.setAvatarUrl(userData.getAvatarUrl());
		user.setOnline(userData.isOnline());
		user.setLastSeen(userData.getLastSeen());
		user.setStatusMessage(userData.getStatusMessage());
		user.setDeviceToken(userData.getDeviceToken());
		user.setCreated(userData.getCreated());
		user.setLastUpdated(userData.getLastUpdated());
		return user;
	}
}
