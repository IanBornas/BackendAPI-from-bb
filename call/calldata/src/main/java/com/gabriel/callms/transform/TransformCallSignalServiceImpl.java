package com.gabriel.callms.transform;
import com.gabriel.callms.entity.CallSignalData;
import com.gabriel.callms.model.CallSignal;
import org.springframework.stereotype.Service;
@Service
public class TransformCallSignalServiceImpl implements TransformCallSignalService {
	@Override
	public CallSignalData transform(CallSignal callSignal){
		CallSignalData callSignalData = new CallSignalData();
		callSignalData.setId(callSignal.getId());
		callSignalData.setCallId(callSignal.getCallId());
		callSignalData.setCallerId(callSignal.getCallerId());
		callSignalData.setReceiverId(callSignal.getReceiverId());
		callSignalData.setCallType(callSignal.getCallType());
		callSignalData.setSignalStatus(callSignal.getSignalStatus());
		callSignalData.setTimestamp(callSignal.getTimestamp());
		return callSignalData;
	}
	@Override

	public CallSignal transform(CallSignalData callSignalData){;
		CallSignal callSignal = new CallSignal();
		callSignal.setId(callSignalData.getId());
		callSignal.setCallId(callSignalData.getCallId());
		callSignal.setCallerId(callSignalData.getCallerId());
		callSignal.setReceiverId(callSignalData.getReceiverId());
		callSignal.setCallType(callSignalData.getCallType());
		callSignal.setSignalStatus(callSignalData.getSignalStatus());
		callSignal.setTimestamp(callSignalData.getTimestamp());
		callSignal.setCreated(callSignalData.getCreated());
		callSignal.setLastUpdated(callSignalData.getLastUpdated());
		return callSignal;
	}
}
