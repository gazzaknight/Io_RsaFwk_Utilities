package rsa.fwk.utilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-06-05 10:00:14 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import rsa.util.zip.EncryptZipEntry;
import rsa.util.zip.EncryptZipOutputStream;
// --- <<IS-END-IMPORTS>> ---

public final class zip

{
	// ---( internal utility methods )---

	final static zip _instance = new zip();

	static zip _newInstance() { return new zip(); }

	static zip _cast(Object o) { return (zip)o; }

	// ---( server methods )---




	public static final void zipAndEncrypt (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(zipAndEncrypt)>> ---
		// @sigtype java 3.5
		// [i] field:0:required password
		// [i] record:1:required zipEntry
		// [i] - field:0:required fileName
		// [i] - field:0:required fileContent
		// [o] object:0:required zippedBytes
		// pipeline
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	password = IDataUtil.getString( pipelineCursor, "password");
			IData[]	zipEntry = IDataUtil.getIDataArray( pipelineCursor, "zipEntry" );
		
			byte[] tempBytes = null;
			byte[] buf = null;
			
			ByteArrayOutputStream tempOStream = new ByteArrayOutputStream(1024);
		
			try {
				EncryptZipOutputStream out = new EncryptZipOutputStream(
						tempOStream, password);
		
				for (int i = 0; i < zipEntry.length; i++) {
					IDataCursor zipContentsCursor = zipEntry[i].getCursor();
					String fileName = IDataUtil.getString(zipContentsCursor,
							"fileName");
					byte[] fileContent = IDataUtil.getString(zipContentsCursor, "fileContent").getBytes();
					zipContentsCursor.destroy();
					BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(fileContent));
					out.putNextEntry(new EncryptZipEntry(fileName));
					int len;
					buf = new byte[1024];
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}
				tempOStream.flush();
				out.close();
				tempBytes = tempOStream.toByteArray();
				tempOStream.close();
			} 
			catch (IOException e) {
				pipelineCursor.destroy();
				throw new ServiceException(e);
			}
		
		if (tempBytes != null)
		{
			IDataUtil.put( pipelineCursor, "zippedBytes", tempBytes );
			pipelineCursor.destroy();
		}
		else
		{
			pipelineCursor.destroy();
			throw new ServiceException("zip byte is empty");
		}
		
		
			
		// --- <<IS-END>> ---

                
	}
}

