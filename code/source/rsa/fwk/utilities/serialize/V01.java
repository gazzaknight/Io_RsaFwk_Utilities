package rsa.fwk.utilities.serialize;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-08-13 09:51:36 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.coder.IDataXMLCoder;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void decode (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(decode)>> ---
		// @sigtype java 3.5
		// [i] object:0:optional pipelineData
		IDataCursor pipelineCursor = pipeline.getCursor();						
		IDataXMLCoder coder = new IDataXMLCoder();
		try {
			byte[] pipelineData = (byte[]) IDataUtil.get(pipelineCursor, "pipelineData");
			if (pipelineData != null)
				IDataUtil.append(coder.decodeFromBytes(pipelineData), pipeline);
		} catch(Exception e){
			throw new ServiceException(e);
		} finally {
			pipelineCursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void encode (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(encode)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional setIgnoreInvalid
		// [o] object:0:required pipelineData
		IDataCursor pipelineCursor = pipeline.getCursor();
		boolean setIgnoreInvalid = IDataUtil.getBoolean(pipelineCursor, "setIgnoreInvalid");
		IDataUtil.remove(pipelineCursor, "setIgnoreInvalid");
		IDataXMLCoder coder = new IDataXMLCoder();
		coder.setIgnoreInvalid(setIgnoreInvalid);  	//Throw an InvalidDataTypeException when an unsupported object type is encountered
		try{
			IDataUtil.put(pipelineCursor, "pipelineData", coder.encodeToBytes(pipeline));
		}catch(Exception e){
			throw new ServiceException(e);
		} finally{
			coder = null;
			pipelineCursor.destroy();
		}		
		// --- <<IS-END>> ---

                
	}
}

