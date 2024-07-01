package kursanov.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import kursanov.dto.menu.SimpleResponse;
import kursanov.dto.authDto.AuthenticationResponse;
import kursanov.dto.authDto.SignInRequest;
import kursanov.dto.authDto.SignUpRequest;
import kursanov.entities.Application;
import kursanov.entities.Restaurant;
import kursanov.entities.User;
import kursanov.enums.Role;
import kursanov.excaptions.AlreadyException;
import kursanov.excaptions.BedRequestException;
import kursanov.excaptions.ForbiddenException;
import kursanov.repository.ApplicationRepository;
import kursanov.repository.RestaurantRepository;
import kursanov.repository.UserRepository;
import kursanov.config.securety.JwtService;
import kursanov.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantRepository restaurantRepository;
    private final ApplicationRepository applicationRepository;


    @PostConstruct
    public void initSaveAdmin() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminov");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("Admin123"));
        user.setRole(Role.ADMIN);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }

    }

    private void checkEmail(String email){
        boolean b = userRepository.existsByEmail(email);
        if (b) throw new AlreadyException("user with email: "+email+" already exists");
    }

    private void checkAge(SignUpRequest signUpRequest) {
        if (signUpRequest.getRole().equals(Role.CHEF)) {
            int year = signUpRequest.getDateOfBirth().getYear();
            int currentYear = LocalDate.now().getYear();
            int age = currentYear - year;
            if (age >= 45 || age < 25) {
                throw new BedRequestException("The age limit should be from 25 to 45");
            }
            if (signUpRequest.getExperience() < 1) {
                throw new BedRequestException("experience of at least 2 years");
            }
        } else if (signUpRequest.getRole().equals(Role.WAITER)) {
            int year = signUpRequest.getDateOfBirth().getYear();
            int currentYear = LocalDate.now().getYear();
            int age = currentYear - year;
            if (age >= 30 || age < 18) {
                throw new BedRequestException("The age limit should be from 18 to 30");
            }
        }
    }

    @Override @Transactional
    public SimpleResponse signUp(Long restId, SignUpRequest signUpRequest) {
        checkEmail(signUpRequest.getEmail());
        checkAge(signUpRequest);
        Restaurant restaurant = restaurantRepository.getRestaurantById(restId);

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AlreadyException(String.format(
                    "User with email: %s already exists!", signUpRequest.getEmail()));
        }

        if (signUpRequest.getRole().equals(Role.ADMIN)){
            throw new ForbiddenException("Forbidden 403");
        }

        Application user = Application.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .experience(signUpRequest.getExperience())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .build();

        restaurant.addApplication(user);
        user.setRestaurant(restaurant);

        applicationRepository.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("thank you for contacting us," +
                        " we will definitely consider your application")
                .build();
    }


    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getByEmail(signInRequest.getEmail());

        if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Incorrect password!");
        }

        String jwtToken = jwtService.createToken(user);

        return AuthenticationResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }
}