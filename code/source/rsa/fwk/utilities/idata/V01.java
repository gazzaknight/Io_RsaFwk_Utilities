package rsa.fwk.utilities.idata;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2012-11-05 10:38:21 GMT
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import java.io.*;
import com.wm.lang.ns.*;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void removeNullFields (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeNullFields)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] record:0:required inputDocument
		// [i] field:0:optional removeEmpty {"false","true"}
		// [i] field:0:optional trimFields {"false","true"}
		// [o] record:0:required outputDocument
		// remove null fields in pipeline
		IDataCursor cursor = pipeline.getCursor();
		IData input = IDataUtil.getIData(cursor, "inputDocument");
		if (input != null) {
			boolean trimFields = IDataUtil.getBoolean(cursor, "trimFields", false);
			boolean removeEmpty = IDataUtil.getBoolean(cursor, "removeEmpty", false);
			IDataUtil.put(cursor, "outputDocument", removeNullFields(input, removeEmpty, trimFields));
			cursor.destroy();
		}
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static IData removeNullFields(IData inIData, boolean removeEmpty, boolean trimFields)
	{
		IData outIData = IDataFactory.create();
		IDataCursor outCursor = outIData.getCursor();
	
		if (inIData != null) {
			IDataCursor inCursor = inIData.getCursor();
	
			while (inCursor.next())
			{
				Object obj = inCursor.getValue();
				if (obj == null)
				{
					continue;
				}
	
				if (obj instanceof String)
				{
					String temp = (String) obj;
					if (trimFields)
					{
						temp = temp.trim();
					}
	
					if (temp.length() > 0 || !removeEmpty)
					{
						IDataUtil.put(outCursor, inCursor.getKey(), temp);
					}
				}
				else if (obj instanceof IData)
				{
					IData out = removeNullFields((IData) obj, removeEmpty, trimFields);
					if (out != null && out.getCursor().hasMoreData())
					{
						IDataUtil.put(outCursor, inCursor.getKey(), out);
					}
				}
				else if (obj instanceof IData[])
				{
					IData[] objArray = (IData[]) obj;
					ArrayList outArrayList = new ArrayList();
	
					for (int i = 0; i < objArray.length; i++)
					{
						IData out = removeNullFields(objArray[i], removeEmpty, trimFields);
						if (out != null && out.getCursor().hasMoreData())
						{
							outArrayList.add(out);
						}
					}
	
					IData[] outArray = null;
					if (outArrayList.size() > 0)
					{
						outArray = new IData[outArrayList.size()];
						outArrayList.toArray(outArray);
						IDataUtil.put(outCursor, inCursor.getKey(), outArray);
					}
				}
				else
				{
					IDataUtil.put(outCursor, inCursor.getKey(), obj);
				}
			}
	
			inCursor.destroy();
		}
	
		outCursor.destroy();
		return outIData;
	}
	
	private static IData trimValues(IData inIData, boolean removeEmptyFields)
	{
	  IData outIData = IDataFactory.create();
	  IDataCursor outCursor = outIData.getCursor();
	  IDataCursor inCursor = inIData.getCursor();
	
	  while (inCursor.next())
	  {
	    Object obj = inCursor.getValue();
	    if (obj instanceof String)
	    {
	      if (obj != null)
	      {
	        String temp = ((String) obj).trim();
	        if (temp.length() > 0 || removeEmptyFields == false)
	        {
	          IDataUtil.put(outCursor, inCursor.getKey(), temp);
	        }
	        else
	        {
	          IDataUtil.put(outCursor, inCursor.getKey(), null);
	        }
	      }
	    }
	    else if (obj instanceof IData)
	    {
	      IDataUtil.put(outCursor, inCursor.getKey(), trimValues((IData) obj, removeEmptyFields));
	    }
	    else if (obj instanceof IData[])
	    {
	      IData[] objArray = (IData[]) obj;
	      IData[] outArray = new IData[objArray.length];
	      for (int i = 0; i < objArray.length; i++)
	      {
	        outArray[i] = trimValues(objArray[i], removeEmptyFields);
	      }
	      IDataUtil.put(outCursor, inCursor.getKey(), outArray);
	    }
	    else
	    {
	      IDataUtil.put(outCursor, inCursor.getKey(), obj);
	    }
	  }
	  outCursor.destroy();
	  inCursor.destroy();
	  return outIData;
	}
		
	// --- <<IS-END-SHARED>> ---
}

