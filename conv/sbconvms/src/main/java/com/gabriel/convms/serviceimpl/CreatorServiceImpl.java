package com.gabriel.convms.serviceimpl;
import com.gabriel.convms.entity.CreatorData;
import com.gabriel.convms.model.Creator;
import com.gabriel.convms.repository.CreatorDataRepository;
import com.gabriel.convms.service.CreatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CreatorServiceImpl implements CreatorService {
	Logger logger = LoggerFactory.getLogger(CreatorServiceImpl.class);
	@Autowired
	CreatorDataRepository creatorDataRepository;
	@Autowired
	@Override
	public Creator[] getAll() {
		List<CreatorData> creatorsData = new ArrayList<>();
		List<Creator> creators = new ArrayList<>();
		creatorDataRepository.findAll().forEach(creatorsData::add);
		Iterator<CreatorData> it = creatorsData.iterator();
		while(it.hasNext()) {
			CreatorData creatorData = it.next();
			Creator creator = new Creator();
			creator.setId(creatorData.getId());
			creator.setName(creatorData.getName());
			creators.add(creator);
		}
		Creator[] array = new Creator[creators.size()];
		for  (int i=0; i<creators.size(); i++){
			array[i] = creators.get(i);
		}
		return array;
	}
	@Override
	public Creator create(Creator creator) {
		logger.info(" add:Input " + creator.toString());
		CreatorData creatorData = new CreatorData();
		creatorData.setName(creator.getName());
		creatorData = creatorDataRepository.save(creatorData);
		logger.info(" add:Input " + creatorData.toString());
			Creator newCreator = new Creator();
			newCreator.setId(creatorData.getId());
			newCreator.setName(creatorData.getName());
		return newCreator;
	}

	@Override
	public Creator update(Creator creator) {
		Creator updatedCreator = null;
		int id = creator.getId();
		Optional<CreatorData> optional  = creatorDataRepository.findById(creator.getId());
		if(optional.isPresent()){
			CreatorData originalCreatorData = new CreatorData();
			originalCreatorData.setId(creator.getId());
			originalCreatorData.setName(creator.getName());
			originalCreatorData.setCreated(optional.get().getCreated());
			CreatorData creatorData = creatorDataRepository.save(originalCreatorData);
			updatedCreator = new Creator();
			updatedCreator.setId(creatorData.getId());
			updatedCreator.setName(creatorData.getName());
			updatedCreator.setCreated(creatorData.getCreated());
			updatedCreator.setLastUpdated(creatorData.getLastUpdated());
		}
		else {
			logger.error("Creator record with id: " + Integer.toString(id) + " do not exist ");

		}
		return updatedCreator;
	}

	@Override
	public Creator get(Integer id) {
		logger.info(" Input id >> "+  Integer.toString(id) );
		Creator creator = null;
		Optional<CreatorData> optional = creatorDataRepository.findById(id);
		if(optional.isPresent()) {
			logger.info(" Is present >> ");
			creator = new Creator();
			creator.setId(optional.get().getId());
			creator.setName(optional.get().getName());
			creator.setCreated(optional.get().getCreated());
			creator.setLastUpdated(optional.get().getLastUpdated());
		}
		else {
			logger.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
		}
		return creator;
	}
	@Override
	public void delete(Integer id) {
		logger.info(" Input >> " +  Integer.toString(id));
		Optional<CreatorData> optional = creatorDataRepository.findById(id);
		if( optional.isPresent()) {
			creatorDataRepository.delete(optional.get());
			logger.info(" Successfully deleted Creator record with id: " + Integer.toString(id));
		}
		else {
			logger.error(" Unable to locate creator with id:" +  Integer.toString(id));
		}
	}
}
