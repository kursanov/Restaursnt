package kursanov.api;

import jakarta.validation.Valid;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.authDto.AuthenticationResponse;
import kursanov.dto.authDto.SignInRequest;
import kursanov.dto.authDto.SignUpRequest;
import kursanov.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationApi {

    private  final  AuthenticationService authenticationService;


    @PostMapping("/sign-up/{restId}")
    SimpleResponse signUp(@PathVariable Long restId,
                          @RequestBody @Valid SignUpRequest signUpRequest){
        return authenticationService.signUp(restId, signUpRequest);
    }

    @PostMapping("/sign-in")
    AuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest){
        return authenticationService.signIn(signInRequest);
    }



}
