package rsa.fwk.utilities.session;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2013-07-31 12:19:16 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.InvokeState;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void getUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getUser)>> ---
		// @sigtype java 3.5
		// [o] field:0:required username
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put(pipelineCursor, "username", InvokeState.getCurrentSession().getUser().getName());
		pipelineCursor.destroy();			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
 
	// --- <<IS-END-SHARED>> ---
}

