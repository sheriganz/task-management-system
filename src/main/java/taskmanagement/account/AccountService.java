package taskmanagement.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@Service
public class AccountService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegistrationRequest request) {
        String email = request.email().toLowerCase(Locale.ROOT);
        if (appUserRepository.existsByEmail(email)) {throw new ResponseStatusException(CONFLICT);}
        String password = passwordEncoder.encode(request.password());
        AppUser appUser = new AppUser(email, password);
        appUserRepository.save(appUser);
    }
}
