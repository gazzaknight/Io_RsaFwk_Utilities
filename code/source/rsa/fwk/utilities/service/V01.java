package rsa.fwk.utilities.service;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-08-13 09:34:27 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.lang.ns.NSName;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void invoke (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(invoke)>> ---
		// @sigtype java 3.5
		// [i] field:0:required $service
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	targetService = IDataUtil.getString( pipelineCursor, "$service");
			IDataUtil.remove(pipelineCursor, "$service");
		pipelineCursor.destroy();
		
		NSName targetServiceNSName = NSName.create (targetService);
		try {
			IData resultPipeline = Service.doInvoke (targetServiceNSName, pipeline);
			IDataUtil.append(resultPipeline, pipeline);
		} catch (Exception e) {
			throw new ServiceException(e);
		} 
		// --- <<IS-END>> ---

                
	}
}

