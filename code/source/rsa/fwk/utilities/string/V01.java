package rsa.fwk.utilities.string;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2016-02-18 16:17:44 GMT
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import org.apache.commons.lang.RandomStringUtils;
import com.wm.util.security.WmSecureString;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void compareIgnoreCase (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compareIgnoreCase)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string1
		// [i] field:0:required string2
		// [o] field:0:required comparisonResult {"false","true"}
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	string1 = IDataUtil.getString( pipelineCursor, "string1" );
			String	string2 = IDataUtil.getString( pipelineCursor, "string2" );
		pipelineCursor.destroy();
		
		String comparisonResult = "false";
		if(string1.equalsIgnoreCase(string2)) {
			comparisonResult = "true";
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "comparisonResult", comparisonResult );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void convertDocumentToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertDocumentToString)>> ---
		// @sigtype java 3.5
		// [i] record:0:required inputDocument
		// [o] record:0:required outputDocument
		IDataCursor cursor = pipeline.getCursor();
		IData input = IDataUtil.getIData(cursor, "inputDocument");
		if (input != null) {
			IDataUtil.put(cursor, "outputDocument", convertDocToString(input));
			cursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void convertToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [o] field:0:required string
		//converts input object to String with .toString() method
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		Object	object = IDataUtil.get( pipelineCursor, "object" );
		pipelineCursor.destroy();		
		if (object != null) {							
			// pipeline
			IDataCursor pipelineCursor_1 = pipeline.getCursor();
			IDataUtil.put( pipelineCursor_1, "string", object.toString());
			pipelineCursor_1.destroy();
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void generateRandomString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateRandomString)>> ---
		// @sigtype java 3.5
		// [i] field:0:required count
		// [o] field:0:required randomString
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	count = IDataUtil.getString( pipelineCursor, "count" );
		pipelineCursor.destroy();
		
		int countInt = Integer.parseInt(count);
		String randomStr = null;
		randomStr = RandomStringUtils.randomAlphanumeric(countInt);
				
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		Object randomString = new Object();
		IDataUtil.put( pipelineCursor_1, "randomString", randomStr);
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void split (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(split)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional inString
		// [i] field:0:required delim
		// [i] field:0:optional limit
		// [o] field:1:required valueList
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	inString = IDataUtil.getString( pipelineCursor, "inString" );
		String	delim = IDataUtil.getString( pipelineCursor, "delim" );
		String limit = IDataUtil.getString( pipelineCursor, "limit" );			 				
				
		if (inString != null) {
			if (limit == null)
				IDataUtil.put( pipelineCursor, "valueList", inString.split(delim) );
			else {
				int i = Integer.parseInt(limit);
				IDataUtil.put( pipelineCursor, "valueList", inString.split(delim, i) );
			}		
		}
		pipelineCursor.destroy();	
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static IData convertDocToString(IData inIData) {
		IData outIData = IDataFactory.create();
		IDataCursor outCursor = outIData.getCursor();
	
		if (inIData != null) {
			IDataCursor inCursor = inIData.getCursor();
	
			while (inCursor.next())	{
				Object obj = inCursor.getValue();
				if (obj == null) {
					IDataUtil.put(outCursor, inCursor.getKey(), null);
				} else if (obj instanceof String)	{
					IDataUtil.put(outCursor, inCursor.getKey(), (String) obj);
				} else if (obj instanceof String[]) {
					IDataUtil.put(outCursor, inCursor.getKey(), (String[]) obj);
				} else if (obj instanceof String[][]) {
					IDataUtil.put(outCursor, inCursor.getKey(), (String[][]) obj);
				} else if (obj instanceof IData) {
					IData out = convertDocToString((IData) obj);
					if (out != null && out.getCursor().hasMoreData()) {
						IDataUtil.put(outCursor, inCursor.getKey(), out);
					}
				} else if (obj instanceof IData[]) {
					IData[] objArray = (IData[]) obj;
					ArrayList<IData> outArrayList = new ArrayList<IData>();
	
					for (int i = 0; i < objArray.length; i++) {
						IData out = convertDocToString(objArray[i]);
						if (out != null && out.getCursor().hasMoreData()) {
							outArrayList.add(out);
						}
					}
	
					IData[] outArray = null;
					if (outArrayList.size() > 0) {
						outArray = new IData[outArrayList.size()];
						outArrayList.toArray(outArray);
						IDataUtil.put(outCursor, inCursor.getKey(), outArray);
					}
				} else if (obj instanceof Object[]) {
					Object[] objArray = (Object[]) obj;
					String[] outArray = new String[objArray.length];
					for (int i = 0; i < objArray.length; i++) {
						if (objArray[i] == null) outArray[i] = null;
						else outArray[i] = objArray[i].toString(); 
					}
					IDataUtil.put(outCursor, inCursor.getKey(), outArray);
				} else {
					IDataUtil.put(outCursor, inCursor.getKey(), obj.toString());
				}
			}
	
			inCursor.destroy();
		}
	
		outCursor.destroy();
		return outIData;
	}
	// --- <<IS-END-SHARED>> ---
}

