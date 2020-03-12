package by.arhor.university.service;

import by.arhor.university.service.dto.UserDTO;
import by.arhor.university.service.trait.Creator;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;
import by.arhor.university.service.trait.Updater;

public interface UserService
    extends Creator<UserDTO>
          , Reader<UserDTO, Long>
          , Updater<UserDTO>
          , Deleter<UserDTO, Long> {
}
