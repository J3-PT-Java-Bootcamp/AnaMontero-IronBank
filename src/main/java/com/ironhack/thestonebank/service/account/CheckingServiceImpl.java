package com.ironhack.thestonebank.service.account;

import com.ironhack.thestonebank.http.requests.account.CreateCheckingRequest;
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
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
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
//            studentChecking.setCreationDate(Instant.now());
            studentChecking.setPrimaryOwner(primaryOwner);

            //FIXME: Set secondary owner
//            if(checkingAccount.getSecondaryOwner()==null){
//            }else{
//                studentChecking.setSecondaryOwner(accountHolderRepository.findById(checkingAccount.getSecondaryOwner()).get());
//            }
            //FIXME: Set account to owner
            //primaryOwner.setAccount(studentChecking);
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
//            checking.setCreationDate(Instant.now());
            checking.setPrimaryOwner(primaryOwner);
            //FIXME: Set secondary owner
//            if(checkingAccount.getSecondaryOwner()==null){
//            }else{
//                checking.setSecondaryOwner(accountHolderRepository.findById(checkingAccount.getSecondaryOwner()).get());
//            }
            //FIXME: Set account to owner
//            primaryOwner.setAccount(checking);
            checkingRepository.save(checking);
        }
    }
}