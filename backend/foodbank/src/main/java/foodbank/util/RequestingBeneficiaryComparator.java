package foodbank.util;

import java.util.Comparator;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.request.entity.Request;

public class RequestingBeneficiaryComparator implements Comparator<Request> {

	@Override
	/* This comparator takes the Beneficiary from the Request and sorts in desc order of score */
	public int compare(Request o1, Request o2) {
		Beneficiary firstBeneficiary = o1.getBeneficiary();
		Beneficiary secondBeneficiary = o2.getBeneficiary();
		return firstBeneficiary.compareTo(secondBeneficiary);
	}

}
