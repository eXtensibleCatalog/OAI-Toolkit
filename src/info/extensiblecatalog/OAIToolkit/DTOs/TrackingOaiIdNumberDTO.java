/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package info.extensiblecatalog.OAIToolkit.DTOs;

/**
 *
 * @author shreyanshv
 */
public class TrackingOaiIdNumberDTO extends DataTransferObject{

    /** the id of the token */
	private Integer trackingId;

	/** the creation date of the token */
	private Integer trackedOaiidnumber;

    public TrackingOaiIdNumberDTO() {}

	public TrackingOaiIdNumberDTO(Integer trackedOaiidnumber) {
		this.trackedOaiidnumber = trackedOaiidnumber;
	}

    //public TrackingOaiIdNumberDTO(Integer trackingId) {
      //  this.trackingId = trackingId;
    //}
	public TrackingOaiIdNumberDTO(Integer trackingId, Integer trackedOaiidnumber) {
		this.trackingId = trackingId;
        this.trackedOaiidnumber = trackedOaiidnumber;
	}

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}

    public Integer getTrackedOaiidnumber() {
		return trackedOaiidnumber;
	}

	public void setTrackedOaiidnumber(Integer trackedOaiidnumber) {
		this.trackedOaiidnumber = trackedOaiidnumber;
	}

}
