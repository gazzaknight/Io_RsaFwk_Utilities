package rsa.fwk.utilities.date;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2014-04-03 11:13:35 BST
// -----( ON-HOST: LWWITI59.opd.ads.uk.rsa-ins.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
// --- <<IS-END-IMPORTS>> ---

public final class V01

{
	// ---( internal utility methods )---

	final static V01 _instance = new V01();

	static V01 _newInstance() { return new V01(); }

	static V01 _cast(Object o) { return (V01)o; }

	// ---( server methods )---




	public static final void convertStringDateToDateObj (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertStringDateToDateObj)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required dateStr
		// [i] field:0:required dateStrPattern
		// [o] object:0:required date
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	dateStr = IDataUtil.getString( pipelineCursor, "dateStr" );
		String	dateStrPattern = IDataUtil.getString( pipelineCursor, "dateStrPattern" );
		pipelineCursor.destroy();
		
		Date date = null;
			
		SimpleDateFormat sdf = new SimpleDateFormat(dateStrPattern);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new ServiceException(e);
		}
				
		pipelineCursor = pipeline.getCursor();
		IDataUtil.put(pipelineCursor, "date", date);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void incrementSubtractDateTime (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(incrementSubtractDateTime)>> ---
		// @sigtype java 3.5
		// [i] field:0:required startDate
		// [i] field:0:required startDatePattern
		// [i] field:0:required endDatePattern
		// [i] field:0:optional dateDifferenceMilliseconds
		// [i] field:0:optional dateDifferenceSeconds
		// [i] field:0:optional dateDifferenceMinutes
		// [i] field:0:optional dateDifferenceHours
		// [i] field:0:optional dateDifferenceDays
		// [i] field:0:optional dateDifferenceMonths
		// [i] field:0:optional dateDifferenceYears
		// [o] field:0:required endDate
		  // pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	startDate = IDataUtil.getString( pipelineCursor, "startDate" );
			String	startDatePattern = IDataUtil.getString( pipelineCursor, "startDatePattern" );
			String	endDatePattern = IDataUtil.getString( pipelineCursor, "endDatePattern" );
			String	dateDifferenceMilliseconds = IDataUtil.getString( pipelineCursor, "dateDifferenceMilliseconds" );
			String	dateDifferenceSeconds = IDataUtil.getString( pipelineCursor, "dateDifferenceSeconds" );
			String	dateDifferenceMinutes = IDataUtil.getString( pipelineCursor, "dateDifferenceMinutes" );
			String	dateDifferenceHours = IDataUtil.getString( pipelineCursor, "dateDifferenceHours" );
			String	dateDifferenceDays = IDataUtil.getString( pipelineCursor, "dateDifferenceDays" );
			String	dateDifferenceMonths = IDataUtil.getString( pipelineCursor, "dateDifferenceMonths" );
			String	dateDifferenceYears = IDataUtil.getString( pipelineCursor, "dateDifferenceYears" );
		pipelineCursor.destroy();
		
		Calendar calendar = Calendar.getInstance();
		Date startDateObj = null;
		
		SimpleDateFormat dfIn = new SimpleDateFormat(startDatePattern);
		SimpleDateFormat dfOut = new SimpleDateFormat(endDatePattern);
		try {
			startDateObj = dfIn.parse(startDate);
		} catch (ParseException e) {
			throw new ServiceException(e);
		}
		calendar.setTime(startDateObj);
		if (dateDifferenceMilliseconds != null)
			calendar.add(Calendar.MILLISECOND, Integer.parseInt(dateDifferenceMilliseconds));
		if (dateDifferenceSeconds != null)
			calendar.add(Calendar.SECOND, Integer.parseInt(dateDifferenceSeconds));
		if (dateDifferenceMinutes != null)
			calendar.add(Calendar.MINUTE, Integer.parseInt(dateDifferenceMinutes));
		if (dateDifferenceHours != null)
			calendar.add(Calendar.HOUR, Integer.parseInt(dateDifferenceHours));
		if (dateDifferenceDays != null)
			calendar.add(Calendar.DATE, Integer.parseInt(dateDifferenceDays));
		if (dateDifferenceMonths != null)
			calendar.add(Calendar.MONTH, Integer.parseInt(dateDifferenceMonths));
		if (dateDifferenceYears != null)
			calendar.add(Calendar.YEAR, Integer.parseInt(dateDifferenceYears));		
			
		Date endDateObj = calendar.getTime();
		
		// pipeline
		pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "endDate", dfOut.format(endDateObj) );
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}
}

