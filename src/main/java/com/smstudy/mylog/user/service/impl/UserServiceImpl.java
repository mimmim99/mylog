package com.smstudy.mylog.user.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.smstudy.mylog.common.dto.ResultDto;
import com.smstudy.mylog.user.entity.RoleType;
import com.smstudy.mylog.user.entity.User;
import com.smstudy.mylog.user.model.UserInput;
import com.smstudy.mylog.user.repository.UserRepository;
import com.smstudy.mylog.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public ResultDto join(UserInput parameter) {
		
		User user = User.builder().username(parameter.getUsername())
								  .password(parameter.getPassword())
								  .email(parameter.getEmail())
								  .nickname(parameter.getNickname())
								  .accessScope(parameter.getAccessScope())
								  .authKey(UUID.randomUUID().toString())
								  .authYn(false)
								  .role(RoleType.USER)
								  .build();
		
		userRepository.save(user);
		
		return new ResultDto();
	}

}
