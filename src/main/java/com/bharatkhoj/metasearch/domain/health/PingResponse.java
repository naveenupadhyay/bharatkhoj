package com.bharatkhoj.metasearch.domain.health;

import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class PingResponse implements Serializable {

    public class Data implements Serializable{
        /**
         *
         */
        private static final long serialVersionUID = 965818185679902105L;

        private double duration;
        private String message;

        public double getDuration() {
            return duration;
        }
        public void setDuration(double duration) {
            this.duration = duration;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
    /**
     *
     */
    private static final long serialVersionUID = -8387287044732853789L;

	/*{
	    "program": "my-service-name",
	    "version": "1.0.0",
	    "release": "commit_id",
	    "datetime": "2016-10-06T19:55:10Z",
	    "timestamp": 1475783710372391716,
	    "status": "success",
	    "code": 200,
	    "message": "OK",
	    "data": {
	        "duration": 33.263465257,
	        "message": "My service is healthy"
	    }
	}*/


    private String programName;

    private String version;

    private String release;

    private long datetime;
    private String status = "success";
    private Integer code = 200;
    private String message = "OK";
    private Data data = new Data();

    public String getProgramName() {
        return programName;
    }
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }


    public String getRelease() {
        return release;
    }
    public void setRelease(String release) {
        this.release = release;
    }
    public long getDatetime() {
        return datetime;
    }
    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}
