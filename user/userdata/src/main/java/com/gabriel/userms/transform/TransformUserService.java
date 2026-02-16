package com.gabriel.userms.transform;
import com.gabriel.userms.entity.UserData;
import com.gabriel.userms.model.User;
public interface TransformUserService {
	UserData transform(User user);
	User transform(UserData userData);
}
