package com.gabriel.convms.service;
import com.gabriel.convms.model.Creator;
public interface CreatorService {
	Creator[] getAll() throws Exception;
	Creator get(Integer id) throws Exception;
	Creator create(Creator creator) throws Exception;
	Creator update(Creator creator) throws Exception;
	void delete(Integer id) throws Exception;
}
