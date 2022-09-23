package com.ironhack.thestonebank.service.account;

import com.ironhack.thestonebank.enums.AccountStatus;
import com.ironhack.thestonebank.http.requests.account.CreateCheckingRequest;
import com.ironhack.thestonebank.model.account.Account;
import com.ironhack.thestonebank.model.account.Checking;
import com.ironhack.thestonebank.model.account.Money;
import com.ironhack.thestonebank.model.account.StudentChecking;
import com.ironhack.thestonebank.repository.account.CheckingRepository;
import com.ironhack.thestonebank.repository.account.StudentCheckingRepository;
import com.ironhack.thestonebank.repository.user.AccountHolderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.ironhack.thestonebank.enums.AccountStatus.ACTIVE;

@Service
public class CheckingServiceImpl implements CheckingService {

    final AccountHolderRepository accountHolderRepository;

    final CheckingRepository checkingRepository;

    final StudentCheckingRepository studentCheckingRepository;

    public CheckingServiceImpl(AccountHolderRepository accountHolderRepository, CheckingRepository checkingRepository, StudentCheckingRepository studentCheckingRepository) {
        this.accountHolderRepository = accountHolderRepository;
        this.checkingRepository = checkingRepository;
        this.studentCheckingRepository = studentCheckingRepository;
    }

    @Override
    public void createChecking(CreateCheckingRequest checkingAccount) throws NoSuchAlgorithmException {

        var primaryOwner = accountHolderRepository.findByName(checkingAccount.getPrimaryOwner());
        int age = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        if(age < 24){
            StudentChecking studentChecking = new StudentChecking();
            studentChecking.setId(new Random().nextLong(1000000000000000L,10000000000000000L));
            studentChecking.setStatus(ACTIVE);
            studentChecking.setSecretKey(secretKey.toString());
            studentChecking.setBalance(new Money(checkingAccount.getBalance()));
            studentChecking.setPrimaryOwner(primaryOwner);
            studentCheckingRepository.save(studentChecking);
        }
        else {
            Checking checking = new Checking();
            checking.setId(new Random().nextLong(1000000000000000L,10000000000000000L));
            checking.setStatus(ACTIVE);
            checking.setSecretKey(secretKey.toString());
            if(checkingAccount.getBalance().doubleValue() < checking.getMinimumBalance().getAmount().doubleValue()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum balance is not met");
            }
            checking.setBalance(new Money(checkingAccount.getBalance()));
            checking.setPrimaryOwner(primaryOwner);
            checkingRepository.save(checking);
        }
    }

    @Override
    public List<Account> getAccounts(String username) {
        var accountHolder = accountHolderRepository.findByUsername(username);
        return (List<Account>) accountHolder.getAccount();
    }

    @Override
    public void updateCheckingStatus(Long id, AccountStatus status) {
        Checking account = checkingRepository.findById(id).orElseThrow();
            if (account instanceof Checking) {
                account.setStatus(status);
                checkingRepository.save(account);
            } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    @Override
    public void updateCheckingBalance(Long id, String amount) {
        Optional<Checking> account = checkingRepository.findById(id);
        if(account.isPresent()) {
            account.get().setBalance(new Money(new BigDecimal(amount)));
            checkingRepository.save(account.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    @Override
    public void deleteAccount(Long id) {
        var foundAccount = checkingRepository.findById(id).orElseThrow();
        checkingRepository.delete(foundAccount);
    }
}