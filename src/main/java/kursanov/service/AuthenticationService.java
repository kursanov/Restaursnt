package kursanov.service;

import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.authDto.AuthenticationResponse;
import kursanov.dto.authDto.SignInRequest;
import kursanov.dto.authDto.SignUpRequest;

public interface AuthenticationService {

    SimpleResponse signUp(Long restId, SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest signInRequest);
}