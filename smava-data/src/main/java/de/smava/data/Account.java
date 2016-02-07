package de.smava.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Tomek Samcik
 *
 * Entity representing a bank account
 */
@Entity
@Table(
	uniqueConstraints=
		@UniqueConstraint(columnNames={"iban", "sessionId"})
)
@ApiModel(value="Account", description="Bank account entity")
public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7082958848216315340L;

	@Id
	@GeneratedValue
	@ApiModelProperty(value = "Account entity identifier")
	private Long id;
	
	/**
	 * International Bank Account Number
	 */
	@NotNull
	@Size(min=1, max=100)
	@ApiModelProperty(value = "Account IBAN")
	private String iban;
	
	/**
	 * Business Identifier Code
	 */
	@NotNull
	@Size(min=1, max=20)
	@ApiModelProperty(value = "Account BIC")
	private String bic;
	
	/**
	 * Client's session identifier 
	 */
	@ApiModelProperty(value = "Client's session identifier")
	private String sessionId;
	
	public Account() {
		//
	}
	
	public Account(String bic, String iban, String sessionId) {
		this.bic = bic;
		this.iban = iban;
		this.sessionId = sessionId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", iban=" + iban + ", bic=" + bic + ", sessionId="
				+ sessionId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bic == null) ? 0 : bic.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (bic == null) {
			if (other.bic != null)
				return false;
		} else if (!bic.equals(other.bic))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		return true;
	}

}
