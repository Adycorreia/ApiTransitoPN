package cv.pn.apitransito.services;

import cv.pn.apitransito.utilities.APIResponse;

public interface UserService {

	APIResponse insertUser(String username);
	APIResponse allUser();
	//APIResponse connectUserToOrganic(String user, String idOrganica);
	//APIResponse detailsUser(String username);

}
