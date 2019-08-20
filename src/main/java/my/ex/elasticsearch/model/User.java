package my.ex.elasticsearch.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

	private int id;
	private int acctId;
	
	@JsonProperty("firstname")
	private String firstName;
	
	@JsonProperty("lastname")
	private String lastName;

	private Date lastPasswordChange;
	private boolean resellerAccess;
	private boolean optInNews;
	private int newpass;
	private Date lastLoginDate;
	private String jobTitle;
	private String telephone;
	private String entRole;
	private int i18nLanguageId;
	private String username;
	private boolean isMaster;
	private int secretQuestion;
	private String secretAnswer;
	private Date lastVerified;
	private Date tokenTime;
	private int consecutiveFailedLogins;
	private String token;
	private int defaultPaymentProfileId;
	private String email;
	private Date modificationTime;
	private String hashpass;
	private String firstIp;
	private String accountSummaryEmailFrequency;
	private int forgotBlock;
	private String role;
	private Date agentAgreementDate;
	private int agentAgreementId;
	private Date ctime;
	private String status;
	private boolean recoveryAdmin;
	private boolean superAccess;
	private String type;
	private int entUnitId;
	private int containerId;
	private boolean basicAccess;
	private String telephoneExtension;
	private String tfaPhoneNumber;
	private int isLimitedAdmin;
	private long unixTsInSecs;
}
