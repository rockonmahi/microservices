
package com.escola.authserver.service;

import com.escola.authserver.dto.UserLoginDto;
import com.escola.authserver.entity.User;
import com.escola.authserver.entity.UserDetails;
import com.escola.authserver.form.UserLoginForm;
import com.escola.authserver.repository.UserDetailsRepository;
import com.escola.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Override
	public String saveLoginUser() {
		User user = User.builder()
                .userName("rockonmahi")
                .fullName("Mahendra Pratap")
                .password(passwordEncoder.encode("test"))
                .active(1)
                .accountLock(0)
                .build();

		userRepository.save(user);

		List<User> loginUserList = userRepository.findByUserNameAndAccountLock(user.getUserName(),1);
		if (!loginUserList.isEmpty()) {
			user = loginUserList.get(0);
		}

		UserDetails userDetails = UserDetails.builder()
                .firstName("Mahendra")
                .middleName("Pratap")
                .lastName("Singh")
                .user(user)
                .build();

		userDetailsRepository.save(userDetails);

		return user.getId().toString();
	}

	@Override
	public UserLoginDto getUserDetails(UserLoginForm userLogin) {

		List<User> userList = userRepository.findByUserNameAndAccountLock(userLogin.getUsername(),1);
		UserLoginDto userLoginDto= new UserLoginDto();
		List<String> authorities= Arrays.asList("Admin");
		if (userList.isEmpty()) {
			String userId = saveLoginUser();
			User user = userRepository.findById(userId).get();
			userLoginDto.setUsername(user.getUserName());
			userLoginDto.setPassword(user.getPassword());
			userLoginDto.setAuthorities(authorities);
		}else{
			User user=userList.get(0);
			userLoginDto.setUsername(user.getUserName());
			userLoginDto.setPassword(user.getPassword());
			userLoginDto.setAuthorities(authorities);
		}
		return userLoginDto;
	}
}
