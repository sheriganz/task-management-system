package taskmanagement.account;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/api/accounts")
    public void createAccount(@RequestBody @Valid RegistrationRequest registrationRequest)
    {
        accountService.register(registrationRequest);
    }

}
