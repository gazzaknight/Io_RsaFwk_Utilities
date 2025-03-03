package rsa.fwk.utilities.error;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-09-17 21:30:39 BST
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




	public static final void throwServiceException (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(throwServiceException)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required errorMsg
		// input
		IDataCursor pipelineCursor = pipeline.getCursor();
		String errorMsg = IDataUtil.getString( pipelineCursor, "errorMsg" );
		pipelineCursor.destroy();
		
		throw new ServiceException(errorMsg);
		// --- <<IS-END>> ---

                
	}
}

