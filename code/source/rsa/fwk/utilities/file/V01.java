package rsa.fwk.utilities.file;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2016-04-25 09:42:19 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void getPackageConfigDir (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getPackageConfigDir)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required packageName
		// [o] field:0:required fileDir
		// [o] field:0:optional errorCode
		// [o] field:0:optional errorMsg
		// [o] field:0:optional internalErrorMsg
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
			String	This_Package = IDataUtil.getString( pipelineCursor_1, "packageName" );
		
		try {
			String	Curr_Dir = System.getProperty("user.dir");
			String	File_Sep = System.getProperty("file.separator");
		
			String Pub_Dir = Curr_Dir + File_Sep +
							"packages" + File_Sep + This_Package + File_Sep + "config";
		
			String file_Dir = Pub_Dir + File_Sep;
		
			pipelineCursor_1.last();
			pipelineCursor_1.insertAfter( "fileDir", file_Dir);
			pipelineCursor_1.destroy();
		}
		catch (Exception e ) {
				pipelineCursor_1.insertAfter( "errorCode", "CAL_FILESYSTEM_FAILURE");
				pipelineCursor_1.insertAfter( "errorMsg", "Internal error in CAL - getting config dir from filesystem");
				pipelineCursor_1.insertAfter( "internalErrorMsg", "Failed to get config dir from filesystem: " + e.getMessage());
		}
		// --- <<IS-END>> ---

                
	}



	public static final void moveFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(moveFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fileName
		// [i] field:0:required targetDirectory
		// [i] field:0:optional appendTimestamp
		// [o] field:0:required targetFileName
		IDataCursor idcPipeline = pipeline.getCursor();
		String strSource        = IDataUtil.getString( idcPipeline , "fileName" );
		String strDestination   = IDataUtil.getString( idcPipeline , "targetDirectory" );
		String appendTimestamp  = IDataUtil.getString( idcPipeline , "appendTimestamp" );
		
		idcPipeline.destroy();	
		
			String targetFileName = null;
				
			File sourceFile = new File(strSource);
			File destinationFile;
			
			if ( "true".equals(appendTimestamp) )
			{
				Date d = new Date();				
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String timestamp = df.format(d);
		
				String fileName = sourceFile.getName();				
				destinationFile = new File(strDestination, fileName + "_" + timestamp);
			} 
			else 
			{ 
				//do not append timestamp at all
				destinationFile = new File(strDestination,sourceFile.getName());
			}
		
		
			if (! sourceFile.renameTo(destinationFile) )
			{
			    // Rename failed
			    throw new ServiceException("Failed to rename file '" + strSource + "' to '" + destinationFile + "'" );
			}
			targetFileName = destinationFile.getAbsolutePath();
		
		
		IDataCursor odcPipeline  = pipeline.getCursor();
		IDataUtil.put(odcPipeline, "targetFileName", targetFileName);
		odcPipeline.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void renameFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(renameFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required sourceFileName
		// [i] field:0:required targetFileName
		// [i] field:0:optional overwrite {"false","true"}
		IDataCursor pipelineCursor = pipeline.getCursor();
		String sourceFileName = IDataUtil.getString(pipelineCursor, "sourceFileName");
		String targetFileName = IDataUtil.getString(pipelineCursor, "targetFileName");
		boolean overwrite = Boolean.parseBoolean(IDataUtil.getString(pipelineCursor, "overwrite"));
		pipelineCursor.destroy();
		
		Path source = Paths.get(sourceFileName);
		Path target = Paths.get(targetFileName);
		
		//manually handle if the file exists before overwritten
		if (!overwrite) {
			if (target.getParent() == null) {
				Path fullTarget = Paths.get(source.getParent().toString(),targetFileName);
				if (Files.exists(fullTarget)) {
					throw new ServiceException("File [" + fullTarget.toAbsolutePath() + "] already exists");
				}
			} else if (Files.exists(target)) {		
				throw new ServiceException("File [" + targetFileName + "] already exists");
			}
		}
		
		try {
			Files.move(source, source.resolveSibling(targetFileName), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			throw new ServiceException(e);
		}		
		// --- <<IS-END>> ---

                
	}



	public static final void writeDirectToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeDirectToFile)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] field:0:required content
		// [i] field:0:required append {"true","false"}
		// [o] field:0:required successFlag
		IDataCursor idcPipeline = pipeline.getCursor();
		String strContent       = IDataUtil.getString( idcPipeline , "content" );
		String strFilename      = IDataUtil.getString( idcPipeline , "filename" );
		String strAppendFlag    = IDataUtil.getString( idcPipeline , "append" );
		if ( (null == strContent) || (null == strFilename) || (null == strAppendFlag) )
		{
		    // ??? direct_error_message( "ERROR" , "Argument 'filename' not set") ;
		}
		else
		{
		    boolean append = "true".equals(strAppendFlag);
		
		    try
		    {
		        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(strFilename, append));
		        fileWriter.write(strContent);
		        fileWriter.flush();
		        fileWriter.close();
		    }
		    catch (Exception e)
		    {
		        // ??? direct_error_message ( "ERROR" , "Exception " + e.toString() );
		    }
		}
		
		idcPipeline.destroy();	
		// --- <<IS-END>> ---

                
	}
}

