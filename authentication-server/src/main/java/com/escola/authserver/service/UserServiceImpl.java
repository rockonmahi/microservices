
package com.escola.authserver.service;

import com.escola.authserver.dto.UserLoginDto;
import com.escola.authserver.entity.RegisteredUsersDetails;
import com.escola.authserver.entity.RegisteredUsers;
import com.escola.authserver.form.UserLoginForm;
import com.escola.authserver.repository.RegisteredUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;

	private final RegisteredUsersRepository userRepository;

	@Override
	public String saveLoginUser() {

		RegisteredUsersDetails userDetails = RegisteredUsersDetails.builder()
				.firstName("Mahendra")
				.middleName("Pratap")
				.lastName("Singh")
				.build();

		RegisteredUsers user = RegisteredUsers.builder()
                .userName("rockonmahi")
                .fullName("Mahendra Pratap")
                .password(passwordEncoder.encode("test"))
                .active(1)
                .accountLock(0)
				.userDetails(userDetails)
                .build();

		userRepository.save(user);

		List<RegisteredUsers> loginUserList = userRepository.findByUserNameAndAccountLock(user.getUserName(),0);
		if (!loginUserList.isEmpty()) {
			user = loginUserList.get(0);
		}

		return user.getId();
	}

	@Override
	public UserLoginDto getUserDetails(UserLoginForm userLogin) {
		List<RegisteredUsers> userList = userRepository.findByUserNameAndAccountLock(userLogin.getUsername(),0);
		UserLoginDto userLoginDto= new UserLoginDto();
		List<String> authorities= Arrays.asList("openid","email","phone");
		if (userList.isEmpty()) {
			String userId = saveLoginUser();
			RegisteredUsers user = userRepository.findById(userId).get();
			userLoginDto.setUsername(user.getUserName());
			userLoginDto.setPassword(user.getPassword());
			userLoginDto.setAuthorities(authorities);
		}else{
			RegisteredUsers user=userList.get(0);
			userLoginDto.setUsername(user.getUserName());
			userLoginDto.setPassword(user.getPassword());
			userLoginDto.setAuthorities(authorities);
		}
		return userLoginDto;
	}
}