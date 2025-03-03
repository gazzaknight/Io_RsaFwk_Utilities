package rsa.fwk.utilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2013-12-04 09:50:29 GMT
// -----( ON-HOST: lwwiti59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
// --- <<IS-END-IMPORTS>> ---

public final class crypto

{
	// ---( internal utility methods )---

	final static crypto _instance = new crypto();

	static crypto _newInstance() { return new crypto(); }

	static crypto _cast(Object o) { return (crypto)o; }

	// ---( server methods )---




	public static final void hash (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(hash)>> ---
		// @sigtype java 3.5
		// [i] object:0:required data
		// [i] object:0:optional key
		// [i] field:0:required algorithm {"HmacMD5","HmacSHA1","HmacSHA256","MD5","SHA-1","SHA-256","SHA-512"}
		// [o] object:0:required checksum
		// [o] field:0:required checksumHex
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		byte[]	dataB = (byte[])IDataUtil.get( pipelineCursor, "data" );
		byte[]	keyB = (byte[])IDataUtil.get( pipelineCursor, "key" );
		String	algorithm = IDataUtil.getString( pipelineCursor, "algorithm" );
		pipelineCursor.destroy();
						
		byte[] checksumB = null;
		String checksum = null;
		
		try {
			if (algorithm.startsWith("Hmac")) {
				//generate HMAC (Message Authentication Code)
				SecretKeySpec secretKey = new SecretKeySpec(keyB, algorithm);
				Mac mac = Mac.getInstance(algorithm);
				mac.init(secretKey);
				mac.update(dataB);
				checksumB = mac.doFinal();
			} else {
				//generate Message Digest (hash)
				MessageDigest md = MessageDigest.getInstance(algorithm);
				checksumB = md.digest(dataB);								
			}
			//convert hash bytes to hexa string
			StringBuffer hexString = new StringBuffer();
			for (byte b : checksumB) {
		    	String hex = Integer.toHexString(0xFF & b);
		    	if (hex.length() == 1) hexString.append('0');
		    	hexString.append(hex);
			}
			checksum = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException(e);
		} catch (InvalidKeyException e) {
			throw new ServiceException(e);
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "checksum", checksumB);
		IDataUtil.put( pipelineCursor_1, "checksumHex", checksum);
		pipelineCursor_1.destroy();			
		// --- <<IS-END>> ---

                
	}
}

