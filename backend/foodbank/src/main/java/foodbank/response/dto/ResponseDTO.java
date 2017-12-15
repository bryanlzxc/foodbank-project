package foodbank.response.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO {
	
	public enum Status {
		SUCCESS, FAIL
	}
	
	@NotNull
	@JsonProperty("status")
	private Status status;
	
	@JsonProperty("message")
	private String message;
	
	public ResponseDTO(@JsonProperty("status") Status status, @JsonProperty("message") String message) {
		this.status = status;
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
