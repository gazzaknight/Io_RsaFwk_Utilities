package rsa.fwk.utilities.callStack;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2012-05-28 11:32:53 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
import com.wm.app.b2b.server.Session;
import java.util.Stack;
import com.wm.app.b2b.server.InvokeState;
import com.wm.lang.ns.NSService;
import java.util.Date;
import java.util.Calendar;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void getCallingServiceName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCallingServiceName)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:0:required serviceName
		/* Returns the service name of the service that invoked the service from 
		 * which getCallingService is being executed. If there is no 
		 * calling service, this service returns a default message 
		 */
		
		String serviceName = "unknown";
		
		try
		{
		    serviceName = Service.getCallingService().toString();
		}
		catch (Exception e)
		{
		    // nowt to do
		}
		
		// Insert the result into the pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "serviceName", serviceName );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getCallingServiceParent (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCallingServiceParent)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:0:required serviceParent
	String serviceName = "unknown";


	Stack callStack = InvokeState.getCurrentState().getCallStack(); 
	int size = callStack.size(); 
	if (size > 2)
	{
		NSService myService = (NSService) callStack.elementAt(size-3); 
		serviceName = myService.getNSName().getFullName(); 
	}

	IDataCursor idcPipeline = pipeline.getCursor(); 
	IDataUtil.put(idcPipeline, "serviceParent" , serviceName );
	idcPipeline.destroy();

		// --- <<IS-END>> ---

                
	}



	public static final void getCallingServiceRoot (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCallingServiceRoot)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:0:required serviceRoot
	String serviceName = "unknown";


	Stack callStack = InvokeState.getCurrentState().getCallStack(); 
	int size = callStack.size(); 
	if (size > 0)
	{
		NSService myService = (NSService) callStack.elementAt(0); 
		serviceName = myService.getNSName().getFullName(); 
	}

	IDataCursor idcPipeline = pipeline.getCursor(); 
	IDataUtil.put(idcPipeline, "serviceRoot" , serviceName );
	idcPipeline.destroy();

		// --- <<IS-END>> ---

                
	}



	public static final void getCallingServiceStack (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCallingServiceStack)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:1:required serviceStack
	Stack callStack = InvokeState.getCurrentState().getCallStack(); 
	int size = callStack.size() - 1; 
	if (size > 0)
	{
		String[] nameList = new String[size];
		for (int i=0; i<size ; i++ )
		{
			NSService myService = (NSService) callStack.elementAt(i); 
			String serviceName = myService.getNSName().getFullName(); 
	
			nameList[size-i-1] = serviceName;
		} 
		IDataCursor idcPipeline = pipeline.getCursor(); 
		IDataUtil.put(idcPipeline, "serviceStack" , nameList );
		idcPipeline.destroy();
	}


		// --- <<IS-END>> ---

                
	}
}

