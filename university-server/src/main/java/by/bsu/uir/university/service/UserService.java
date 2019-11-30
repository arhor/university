package by.bsu.uir.university.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import by.bsu.uir.university.service.dto.UserDto;
import by.bsu.uir.university.service.trait.Creator;
import by.bsu.uir.university.service.trait.Deleter;
import by.bsu.uir.university.service.trait.Reader;
import by.bsu.uir.university.service.trait.Updater;

public interface UserService
    extends UserDetailsService
          , Creator<UserDto>
          , Reader<UserDto, Long>
          , Updater<UserDto>
          , Deleter<UserDto, Long> {
}
