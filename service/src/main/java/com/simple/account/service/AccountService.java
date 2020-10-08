package com.simple.account.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.account.dao.AccountDao;
import com.simple.account.dto.AccountDto;
import com.simple.account.model.Account;
import com.simple.core.exception.ServiceHelper;
import com.simple.common.api.ResultCode;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.AuthContext;
import com.simple.common.env.EnvConfig;
import com.simple.common.error.ServiceException;
import com.simple.common.props.AppProps;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class AccountService {

    static ILogger logger = SLoggerFactory.getLogger(AccountService.class);

    private final AccountDao accountDao;


    private final AppProps appProps;

    private final EnvConfig envConfig;


    private final ServiceHelper serviceHelper;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;


    public AccountDto create(String name, String email, String phoneNumber, String pwd) {


        // Column name/email/phone_number cannot be null
        if (name == null) {
            name = "";
        }
        if (email == null) {
            email = "";
        }
        if (phoneNumber == null) {
            phoneNumber = "";
        }

        String pwHash = passwordEncoder.encode(pwd);
        Account account = Account.builder()
                .email(email).name(name).phoneNumber(phoneNumber).password(pwHash)
                .build();

        try {
            //Account result = exampleRepo.save(account);

            accountDao.addAccount(account);

            //String userId = result.getId();
            //this.updatePassword(userId, pwd);
        } catch (Exception ex) {
            String errMsg = "Could not create user account";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }


        AccountDto accountDto = this.convertToDto(account);
        return accountDto;
    }

    public AccountDto verifyPassword(String name, String password) {
        //AccountSecret accountSecret = accountSecretRepo.findAccountSecretByEmail(email);
        Account account = null;
        try {
            account = this.accountDao.findByName(name);
        } catch (Exception ex) {
            throw new ServiceException(ResultCode.NOT_FOUND, "account with specified name not found");
        }

        if (account == null) {
            throw new ServiceException(ResultCode.NOT_FOUND, "account with specified name not found");
        }

        if (StringUtils.isEmpty(account.getPasswordHash())) {
            throw new ServiceException(ResultCode.REQ_REJECT, "This user has not set up their password");
        }

        if (!passwordEncoder.matches(password, account.getPasswordHash())) {
            throw new ServiceException(ResultCode.UN_AUTHORIZED, "Incorrect password");
        }

        // You shall pass
        AccountDto accountDto = this.convertToDto(account);
        return accountDto;
    }
//
//
//
//    public AccountDto update(AccountDto newAccountDto) {
//        Account newAccount = this.convertToModel(newAccountDto);
//
//        Account existingAccount = exampleRepo.findAccountById(newAccount.getId());
//        if (existingAccount == null) {
//            throw new ServiceException(ResultCode.NOT_FOUND, String.format("User with id %s not found", newAccount.getId()));
//        }
//        entityManager.detach(existingAccount);
//
//        if (StringUtils.hasText(newAccount.getEmail()) && !newAccount.getEmail().equals(existingAccount.getEmail())) {
//            Account foundAccount = exampleRepo.findAccountByEmail(newAccount.getEmail());
//            if (foundAccount != null) {
//                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that email already exists. Try a password reset");
//            }
//        }
//
//        if (StringUtils.hasText(newAccount.getPhoneNumber()) && !newAccount.getPhoneNumber().equals(existingAccount.getPhoneNumber())) {
//            Account foundAccount = exampleRepo.findAccountByPhoneNumber(newAccount.getPhoneNumber());
//            if (foundAccount != null) {
//                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that phonenumber already exists. Try a password reset");
//            }
//        }
//
//        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
//            if (!existingAccount.isConfirmedAndActive() && newAccount.isConfirmedAndActive()) {
//                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot activate this account");
//            }
//
//            if (!existingAccount.getPhotoUrl().equals(newAccount.getPhotoUrl())) {
//                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the photo through this endpoint (see docs)");
//            }
//            // User can request email change - not do it :-)
//            if (!existingAccount.getEmail().equals(newAccount.getEmail())) {
//
//                newAccount.setEmail(existingAccount.getEmail());
//            }
//        }
//
//        try {
//            exampleRepo.save(newAccount);
//        } catch (Exception ex) {
//            String errMsg = "Could not update the user account";
//            serviceHelper.handleException(logger, ex, errMsg);
//            throw new ServiceException(errMsg, ex);
//        }
//
//        // If account is being activated, or if phone number is changed by current user - send text
//        if (newAccount.isConfirmedAndActive() &&
//                StringUtils.hasText(newAccount.getPhoneNumber()) &&
//                !newAccount.getPhoneNumber().equals(existingAccount.getPhoneNumber())) {
//            //serviceHelper.sendSmsGreeting(newAccount.getId());
//        }
//
//        //this.trackEventWithAuthCheck("account_updated");
//
//        AccountDto accountDto = this.convertToDto(newAccount);
//        return accountDto;
//    }
//

    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    private Account convertToModel(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }


}
