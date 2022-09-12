package it.gov.pagopa.reportingorgsenrollment.model.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationModelResponse implements Serializable {   
    /**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -1799364622972489661L;
	
	private String organizationFiscalCode;
    private String organizationOnboardingDate;
}
