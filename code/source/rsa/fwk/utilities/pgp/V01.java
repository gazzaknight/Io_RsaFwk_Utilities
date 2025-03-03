package rsa.fwk.utilities.pgp;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2015-05-27 12:49:42 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.PublicKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureSubpacketVector;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.bc.BcPGPObjectFactory;
import org.bouncycastle.openpgp.bc.BcPGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.bc.BcPGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void decrypt (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(decrypt)>> ---
		// @sigtype java 3.5
		// [i] object:0:required encData
		// [i] object:0:required privateKey
		// [i] field:0:required password
		// [o] object:0:required clearData
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			byte[]	encData = (byte[])IDataUtil.get( pipelineCursor, "encData" );
			byte[]	privateKey = (byte[])IDataUtil.get( pipelineCursor, "privateKey" );		
			String	password = IDataUtil.getString( pipelineCursor, "password" );
		pipelineCursor.destroy();
		
		InputStream keyIn = null;
		byte[] clearData = null;
		try {
			keyIn = new ByteArrayInputStream(privateKey);
			clearData = decrypt(encData, keyIn, password.toCharArray());
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
			try {keyIn.close();} catch (Exception e) {}
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "clearData", clearData );
		pipelineCursor_1.destroy();	
		// --- <<IS-END>> ---

                
	}



	public static final void encrypt (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(encrypt)>> ---
		// @sigtype java 3.5
		// [i] object:0:required clearData
		// [i] object:0:required publicKey
		// [i] field:0:required integrityCheck {"true","false"}
		// [i] field:0:required asciiArmored {"true","false"}
		// [o] object:0:required encData
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		byte[]	clearData = (byte[])IDataUtil.get( pipelineCursor, "clearData" );
		byte[]	publicKey =  (byte[])IDataUtil.get( pipelineCursor, "publicKey" );
		boolean	integrityCheck = Boolean.parseBoolean(IDataUtil.getString( pipelineCursor, "integrityCheck" ));
		boolean	asciiArmored = Boolean.parseBoolean(IDataUtil.getString( pipelineCursor, "asciiArmored" ));
		pipelineCursor.destroy();
		
		InputStream keyIn = null;
		byte[] encData = null;
		try {
			keyIn = new ByteArrayInputStream(publicKey);
			encData = encrypt(clearData, readPublicKey(keyIn), integrityCheck, asciiArmored);
		} catch (Exception e) {
			throw new ServiceException(e);
		} finally {
			try {keyIn.close();} catch (Exception e) {}
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "encData", encData);
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static final int   KEY_FLAGS = 27;
	private static final int[] MASTER_KEY_CERTIFICATION_TYPES = new int[]{
		PGPSignature.POSITIVE_CERTIFICATION,
		PGPSignature.CASUAL_CERTIFICATION,
		PGPSignature.NO_CERTIFICATION,
		PGPSignature.DEFAULT_CERTIFICATION
	};
	
	/**
	 * decrypt the passed in message stream
	 * 
	 * @param encrypted
	 *            The message to be decrypted.
	 * @param passPhrase
	 *            Pass phrase (key)
	 * 
	 * @return Clear text as a byte array. I18N considerations are not handled
	 *         by this routine
	 * @exception IOException
	 * @exception PGPException
	 * @exception NoSuchProviderException
	 */
	public static byte[] decrypt(byte[] encrypted, InputStream keyIn, char[] password)
	        throws IOException, PGPException, NoSuchProviderException {
	    InputStream in = new ByteArrayInputStream(encrypted);
	
	    in = PGPUtil.getDecoderStream(in);
	
	    PGPObjectFactory pgpF = new BcPGPObjectFactory(in);
	    PGPEncryptedDataList enc = null;
	    Object o = pgpF.nextObject();
	
	    //
	    // the first object might be a PGP marker packet.
	    //
	    if (o instanceof PGPEncryptedDataList) {
	        enc = (PGPEncryptedDataList) o;
	    } else {
	        enc = (PGPEncryptedDataList) pgpF.nextObject();
	    }
	
	
	
	    //
	    // find the secret key
	    //
	    @SuppressWarnings("unchecked")
	    Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
	    PGPPrivateKey sKey = null;
	    PGPPublicKeyEncryptedData pbe = null;
	 
	    while (sKey == null && it.hasNext()) {
	        pbe = it.next();
	 
	        sKey = findPrivateKey(keyIn, pbe.getKeyID(), password);
	    }
	 
	    if (sKey == null) {
	        throw new IllegalArgumentException("Secret key for message not found.");
	    }
	    
	    InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
	
	
	
	    PGPObjectFactory pgpFact = new BcPGPObjectFactory(clear);
	
	    PGPCompressedData cData = (PGPCompressedData) pgpFact.nextObject();
	
	    pgpFact = new BcPGPObjectFactory(cData.getDataStream());
	
	    PGPLiteralData ld = (PGPLiteralData) pgpFact.nextObject();
	
	    InputStream unc = ld.getInputStream();
	
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    int ch;
	
	    while ((ch = unc.read()) >= 0) {
	        out.write(ch);
	
	    }
	
	    byte[] returnBytes = out.toByteArray();
	    in.close();
	    out.close();
	    return returnBytes;
	}	
	
	
	/**
	 * Simple PGP encryptor between byte[].
	 * 
	 * @param clearData
	 *            The test to be encrypted
	 * @param passPhrase
	 *            The pass phrase (key). This method assumes that the key is a
	 *            simple pass phrase, and does not yet support RSA or more
	 *            sophisiticated keying.
	 * @param fileName
	 *            File name. This is used in the Literal Data Packet (tag 11)
	 *            which is really inly important if the data is to be related to
	 *            a file to be recovered later. Because this routine does not
	 *            know the source of the information, the caller can set
	 *            something here for file name use that will be carried. If this
	 *            routine is being used to encrypt SOAP MIME bodies, for
	 *            example, use the file name from the MIME type, if applicable.
	 *            Or anything else appropriate.
	 * 
	 * @param armor
	 * 
	 * @return encrypted data.
	 * @exception IOException
	 * @exception PGPException
	 * @exception NoSuchProviderException
	 */	
	   public static byte[] encrypt(byte[] clearData, PGPPublicKey encKey,
	       boolean withIntegrityCheck, boolean armor)
	       throws IOException, PGPException, NoSuchProviderException {
	
	   ByteArrayOutputStream encOut = new ByteArrayOutputStream();
	
	   OutputStream out = encOut;
	   if (armor) {
	       out = new ArmoredOutputStream(out);
	   }
	
	   ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	
	   PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(
	           PGPCompressedDataGenerator.ZIP);
	   OutputStream cos = comData.open(bOut); // open it with the final
	   // destination
	   PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
	
	   // we want to generate compressed data. This might be a user option
	   // later,
	   // in which case we would pass in bOut.
	   OutputStream pOut = lData.open(cos, // the compressed output stream
	           PGPLiteralData.BINARY, 
	           PGPLiteralData.CONSOLE, // "filename" to store
	           clearData.length, // length of clear data
	           new Date() // current time
	           );
	   pOut.write(clearData);
	
	   lData.close();
	   comData.close();
	
	   BcPGPDataEncryptorBuilder dataEncryptor = new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256);
	   dataEncryptor.setWithIntegrityPacket(withIntegrityCheck);
	   dataEncryptor.setSecureRandom(new SecureRandom());
	       
	   PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptor);
	   encryptedDataGenerator.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(encKey));
	
	   byte[] bytes = bOut.toByteArray();
	
	   OutputStream cOut = encryptedDataGenerator.open(out, bytes.length);
	
	   cOut.write(bytes); // obtain the actual bytes from the compressed stream
	
	   cOut.close();
	
	   out.close();
	
	   return encOut.toByteArray();
	}
	
		/**
		 * Load a secret key ring collection from keyIn and find the private key corresponding to
		 * keyID if it exists.
		 *
		 * @param keyIn input stream representing a key ring collection.
		 * @param keyID keyID we want.
		 * @param pass passphrase to decrypt secret key with.
		 * @return
		 * @throws IOException
		 * @throws PGPException
		 * @throws NoSuchProviderException
		 */
		public  static PGPPrivateKey findPrivateKey(InputStream keyIn, long keyID, char[] pass)
			throws IOException, PGPException, NoSuchProviderException
		{
		    PGPSecretKeyRingCollection pgpSec = new BcPGPSecretKeyRingCollection(PGPUtil.getDecoderStream(keyIn));
		    return findPrivateKey(pgpSec.getSecretKey(keyID), pass);
		
		}   
		
		/**
		 * Load a secret key and find the private key in it
		 * @param pgpSecKey The secret key
		 * @param pass passphrase to decrypt secret key with
		 * @return
		 * @throws PGPException
		 */
		public static PGPPrivateKey findPrivateKey(PGPSecretKey pgpSecKey, char[] pass)
			throws PGPException
		{
			if (pgpSecKey == null) return null;
		    PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider()).build(pass);
		    return pgpSecKey.extractPrivateKey(decryptor);
		}
	   
	   
	@SuppressWarnings("unchecked")
	public static PGPPublicKey readPublicKey(InputStream in)
		throws IOException, PGPException
	{
	
	    PGPPublicKeyRingCollection keyRingCollection = new BcPGPPublicKeyRingCollection(PGPUtil.getDecoderStream(in));
	
	    //
	    // we just loop through the collection till we find a key suitable for encryption
	    //
	    PGPPublicKey publicKey = null;
	
	    //
	    // iterate through the key rings.
	    //
	    Iterator<PGPPublicKeyRing> rIt = keyRingCollection.getKeyRings();
	
	    while (publicKey == null && rIt.hasNext()) {
	        PGPPublicKeyRing kRing = rIt.next();
	        Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
	        while (publicKey == null && kIt.hasNext()) {
	            PGPPublicKey key = kIt.next();
	            if (key.isEncryptionKey()) {
	                publicKey = key;
	            }
	        }
	    }
	
	    if (publicKey == null) {
	        throw new IllegalArgumentException("Can't find public key in the key ring.");
	    }
	    if (!isForEncryption(publicKey)) {
	        throw new IllegalArgumentException("KeyID " + publicKey.getKeyID() + " not flagged for encryption.");
	    }
	
	    return publicKey;
	}
	
	/**
	 * From LockBox Lobs PGP Encryption tools.
	 * http://www.lockboxlabs.org/content/downloads
	 *
	 * @param key
	 * @return
	 */
	public static boolean isForEncryption(PGPPublicKey key)
	{
	    if (key.getAlgorithm() == PublicKeyAlgorithmTags.RSA_SIGN
	        || key.getAlgorithm() == PublicKeyAlgorithmTags.DSA
	        || key.getAlgorithm() == PublicKeyAlgorithmTags.EC
	        || key.getAlgorithm() == PublicKeyAlgorithmTags.ECDSA)
	    {
	        return false;
	    }
	
	    return hasKeyFlags(key, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);
	}
	
	/**
	 * From LockBox Lobs PGP Encryption tools.
	 * http://www.lockboxlabs.org/content/downloads
	 *
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static boolean hasKeyFlags(PGPPublicKey encKey, int keyUsage) {
	    if (encKey.isMasterKey()) {
	        for (int i = 0; i != MASTER_KEY_CERTIFICATION_TYPES.length; i++) {
	            for (Iterator<PGPSignature> eIt = encKey.getSignaturesOfType(MASTER_KEY_CERTIFICATION_TYPES[i]); eIt.hasNext();) {
	                PGPSignature sig = eIt.next();
	                if (!isMatchingUsage(sig, keyUsage)) {
	                    return false;
	                }
	            }
	        }
	    }
	    else {
	        for (Iterator<PGPSignature> eIt = encKey.getSignaturesOfType(PGPSignature.SUBKEY_BINDING); eIt.hasNext();) {
	            PGPSignature sig = eIt.next();
	            if (!isMatchingUsage(sig, keyUsage)) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	/**
	 * From LockBox Lobs PGP Encryption tools.
	 * http://www.lockboxlabs.org/content/downloads
	 *
	 * @param key
	 * @return
	 */
	private static boolean isMatchingUsage(PGPSignature sig, int keyUsage) {
	    if (sig.hasSubpackets()) {
	        PGPSignatureSubpacketVector sv = sig.getHashedSubPackets();
	        if (sv.hasSubpacket(KEY_FLAGS)) {
	            if (sv.getKeyFlags() == 0 && (keyUsage == 0)) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
		
	// --- <<IS-END-SHARED>> ---
}

