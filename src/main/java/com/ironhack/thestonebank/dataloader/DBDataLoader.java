package com.ironhack.thestonebank.dataloader;

import com.ironhack.thestonebank.model.account.Checking;
import com.ironhack.thestonebank.model.account.Money;
import com.ironhack.thestonebank.model.account.StudentChecking;
import com.ironhack.thestonebank.model.user.AccountHolder;
import com.ironhack.thestonebank.model.user.Address;
import com.ironhack.thestonebank.repository.account.CheckingRepository;
import com.ironhack.thestonebank.repository.account.StudentCheckingRepository;
import com.ironhack.thestonebank.repository.user.AccountHolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DBDataLoader {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Bean
    void populateData() {
        checkingRepository.deleteAll();
        studentCheckingRepository.deleteAll();
        accountHolderRepository.deleteAll();

        var accountHolder1 = new AccountHolder("cc74ac4b-d13b-4a63-9100-a2742136a941", "user1", "User1",
                LocalDate.of(2002, 10, 3), new Address("Calle Mayor 12",
                "Madrid", "28145", "Spain"), "user1@email.com");
        var accountHolder2 = new AccountHolder("33552174-13ac-4160-97de-3186e634d7ff", "user2", "User2",
                LocalDate.of(1984, 5, 3), new Address("Calle Larga 451",
                "Pontevedra", "54369", "Spain"), "user2@email.com");
        var accountHolder3 = new AccountHolder("26e389ba-6cef-4f27-885e-a2ffe17ad040", "user3", "User3",
                LocalDate.of(1946, 9, 19), new Address("Calle Betis S/N",
                "Sevilla", "41541", "Spain"), "user3@email.com");
        var accountHolder4 = new AccountHolder("a0355931-7682-42c0-9bf9-be4fde777432", "user4", "User4",
                LocalDate.of(1979, 1, 16), new Address("Calle Grande 9",
                "Soria", "43521", "Spain"), "user4@email.com");
        var accountHolder5 = new AccountHolder("2493ba6a-26b1-4b63-88d3-7b60f6784b5e", "user5", "User5",
                LocalDate.of(2002, 4, 29), new Address("Carrer Prat 36",
                "Barcelona", "08019", "Spain"), "user5@email.com");
        var accountHolder6 = new AccountHolder("7c0168e7-af4c-4324-8291-891a74715af6", "user6", "User6",
                LocalDate.of(1988, 4, 28), new Address("Calle Ancha 24",
                "Badajoz", "17986", "Spain"), "user6@email.com");

        List<AccountHolder> accounts = List.of(accountHolder1, accountHolder2, accountHolder3, accountHolder4, accountHolder5, accountHolder6);

        accountHolderRepository.saveAll(accounts);

        var account1 = new Checking(1036348408372826L, new Money(new BigDecimal("2000.49")), accountHolder3,"Dyh9quKYvh");
        var account2 = new Checking(2245780477508536L, new Money(new BigDecimal("26478.00")), accountHolder4,"qtA8SiplWD");
        var account3 = new Checking(6547985432746876L, new Money(new BigDecimal("10321.56")), accountHolder2,"MmP6he6DRX");
        var account4 = new Checking(2014800656465685L, new Money(new BigDecimal("6357.07")), accountHolder6,"6T4Q1u4z9g");
        var account5 = new StudentChecking(9054570456523453L, new Money(new BigDecimal("426.12")), accountHolder1,"MRfAeyGrHS");
        var account6 = new StudentChecking(3650716786365787L, new Money(new BigDecimal("586.78")), accountHolder5,"yYuuvqePE8");

        checkingRepository.save(account1);
        checkingRepository.save(account2);
        checkingRepository.save(account3);
        checkingRepository.save(account4);
        studentCheckingRepository.save(account5);
        studentCheckingRepository.save(account6);
    }
}