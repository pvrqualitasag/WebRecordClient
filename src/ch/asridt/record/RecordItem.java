/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.asridt.record;

import java.io.Serializable;
import java.util.Date;

/**
 * Generic record superclass representing common properties of record objects. 
 * 
 * A record is specified by the following four properties
 * <ul>
 * <li>   recordType:    name of the record type
 * <li>   recordValue:   value of record
 * <li>   recordUnit:    unit that is associated with recordValue
 * <li>   date:          modification date of record file
 * </ul>
 * 
 * Apart from the getter and setter methods 
 * 
 * @author pvr
 */
public class RecordItem implements Serializable {
 
    private String id;
    private String uri;
    private String title;
    private String dateOfRecording;
    private String recordType;
    private double recordValue;
    private String recordUnit;
    private Date date;


    public RecordItem() {}
    
    public RecordItem(String title, String id, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }
    
     /**
     * Get the value of dateOfRecording
     *
     * @return the value of dateOfRecording
     */
    public String getDateOfRecording() {
        return dateOfRecording;
    }

    /**
     * Set the value of dateOfRecording
     *
     * @param dateOfRecording new value of dateOfRecording
     */
    public void setDateOfRecording(String dateOfRecording) {
        this.dateOfRecording = dateOfRecording;
    }

 
    /**
     * Get the type of record based on file extension
     *
     * @return the file extension
     */
    public String getType() {
        // find index of last "."
        int periodIndex = id.lastIndexOf(".");
        // return extension as type
        return id.substring(periodIndex+1).toLowerCase();
    }


    /**
     * Get the value of recordValue
     *
     * @return the value of recordValue
     */
    public double getRecordValue() {
        return recordValue;
    }

    /**
     * Set the value of recordValue
     *
     * @param recordValue new value of recordValue
     */
    public void setRecordValue(double recordValue) {
        this.recordValue = recordValue;
    }

    /**
     * Get the value of recordUnit
     *
     * @return the value of recordUnit
     */
    public String getRecordUnit() {
        return recordUnit;
    }

    /**
     * Set the value of recordUnit
     *
     * @param recordUnit new value of recordUnit
     */
    public void setRecordUnit(String recordUnit) {
        this.recordUnit = recordUnit;
    }

    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the value of recordId
     *
     * @return the value of recordId
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of recordId
     *
     * @param recordId new value of recordId
     */
    public void setId(String recordId) {
        this.id = recordId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
    
    
    @Override
    public String toString() {
        return "Id: "               + this.id    + 
               "\tRecordFileType: " + this.getType()  + 
               "\tDate: "           + this.getDate()  + 
               "\tRecordType: "     + this.getRecordType()  +
               "\tDateOfRecording: " + this.getDateOfRecording() +
               "\tValue: "          + this.getRecordValue() + 
               "\tUnit: "           + this.getRecordUnit()  + "\n";
    }
}
