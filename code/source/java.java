package Default.GK;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class java

{
	// ---( internal utility methods )---

	final static java _instance = new java();

	static java _newInstance() { return new java(); }

	static java _cast(Object o) { return (java)o; }

	// ---( server methods )---




	public static final void addNums (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addNums)>> ---
		// @sigtype java 3.5
		// [i] field:0:required num1
		// [i] field:0:required num2
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	num1 = IDataUtil.getString( pipelineCursor, "num1" );
			String	num2 = IDataUtil.getString( pipelineCursor, "num2" );
		pipelineCursor.destroy();
		
		int number1 = Integer.parseInt(num1);
		int number2 = Integer.parseInt(num2);
		int result1 = number1 + number2;
		String result = String.valueOf(result1);
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}
}

