package rsa.fwk.utilities.server;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2013-06-20 18:18:15 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void getRootContextId (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRootContextId)>> ---
		// @sigtype java 3.5
		// [o] field:0:required rootContextId
		String[] ac = com.wm.app.b2b.server.ServerAPI.getAuditContext();		
		if (ac != null) { 
			// pipeline
			IDataCursor pipelineCursor = pipeline.getCursor();
			IDataUtil.put( pipelineCursor, "rootContextId", ac[0]);
			pipelineCursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}
}

