package com.smstudy.mylog.user.service;

import javax.transaction.Transactional;

import com.smstudy.mylog.common.dto.ResultDto;
import com.smstudy.mylog.user.model.UserInput;

public interface UserService {

	/**
	 * 회원가입
	 */
	@Transactional
	public ResultDto join(UserInput parameter);
}
