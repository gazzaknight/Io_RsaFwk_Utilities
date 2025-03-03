package rsa.fwk.utilities.hex;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-02-28 10:24:49 GMT
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void bytesToHex (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(bytesToHex)>> ---
		// @sigtype java 3.5
		// [i] object:0:required bytes
		// [o] field:0:required hexString
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			byte[]	bytes = (byte[])IDataUtil.get( pipelineCursor, "bytes" );
		pipelineCursor.destroy();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "hexString", DatatypeConverter.printHexBinary(bytes) );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}
}

