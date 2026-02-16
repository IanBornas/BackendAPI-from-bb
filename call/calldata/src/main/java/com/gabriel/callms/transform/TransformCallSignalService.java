package com.gabriel.callms.transform;
import com.gabriel.callms.entity.CallSignalData;
import com.gabriel.callms.model.CallSignal;
public interface TransformCallSignalService {
	CallSignalData transform(CallSignal callSignal);
	CallSignal transform(CallSignalData callSignalData);
}
