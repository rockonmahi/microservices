
package com.escola.authserver.service;

import com.escola.authserver.dto.UserLoginDto;
import com.escola.authserver.entity.UserDetails;
import com.escola.authserver.entity.Users;
import com.escola.authserver.form.UserLoginForm;
import com.escola.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	@Override
	public String saveLoginUser() {


		UserDetails userDetails = UserDetails.builder()
				.firstName("Mahendra")
				.middleName("Pratap")
				.lastName("Singh")
				.build();

		Users user = Users.builder()
                .userName("rockonmahi")
                .fullName("Mahendra Pratap")
                .password(passwordEncoder.encode("test"))
                .active(1)
                .accountLock(0)
				.userDetails(userDetails)
                .build();

		userRepository.save(user);

		List<Users> loginUserList = userRepository.findByUserNameAndAccountLock(user.getUserName(),1);
		if (!loginUserList.isEmpty()) {
			user = loginUserList.get(0);
		}

		return user.getId();
	}

	@Override
	public UserLoginDto getUserDetails(UserLoginForm userLogin) {
		List<Users> userList = userRepository.findByUserNameAndAccountLock(userLogin.getUsername(),0);
		UserLoginDto userLoginDto= new UserLoginDto();
		List<String> authorities= Arrays.asList("openid","email","phone");
		if (userList.isEmpty()) {
			String userId = saveLoginUser();
			Users user = userRepository.findById(userId).get();
			userLoginDto.setUsername(user.getUserName());
			userLoginDto.setPassword(user.getPassword());
			userLoginDto.setAuthorities(authorities);
		}else{
			Users user=userList.get(0);
			userLoginDto.setUsername(user.getUserName());
			userLoginDto.setPassword(user.getPassword());
			userLoginDto.setAuthorities(authorities);
		}
		return userLoginDto;
	}
}
