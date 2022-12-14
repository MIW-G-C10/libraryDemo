package nl.miwgroningen.ch10.vincent.libraryDemo.service;

import nl.miwgroningen.ch10.vincent.libraryDemo.repository.LibraryUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Vincent Velthuizen <v.r.velthuizen@pl.hanze.nl>
 * <p>
 * Dit is wat het programma doet.
 */

@Service
public class LibraryUserDetailService implements UserDetailsService {

    private final LibraryUserRepository libraryUserRepository;

    public LibraryUserDetailService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return libraryUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with name " + username + " was not found."));
    }
}
