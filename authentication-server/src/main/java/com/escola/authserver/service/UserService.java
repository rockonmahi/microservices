
package com.escola.authserver.service;

import com.escola.authserver.dto.UserLoginDto;
import com.escola.authserver.form.UserLoginForm;

public interface UserService {

	String saveLoginUser();

	UserLoginDto getUserDetails(UserLoginForm userLogin);

}
