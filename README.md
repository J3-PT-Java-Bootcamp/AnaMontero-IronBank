# The Stone Bank

![The StoneBank](img/TheStoneBank.jpg?raw=true "The StoneBank")

This repository contains a **REST API**, created to resolve the **Final project** for Ironhack Java bootcamp (J3PT). 
It runs on a local server.

## Author
- Ana Montero

***

## Index:
- [Technology stack](#first-steps)
- [Class diagram](#class-diagram)
- [First steps](#first-steps)
- [Description of the project](#description-of-the-project)
- [API roues](#api-routes)


***

## Technology stack

<li>IntelliJ IDEA 2022.1.2 (Ultimate Edition)</li>
<li>Java 17</li>
<li>Maven 3.8.6</li>
<li>MySQL 8.0.30</li>
<li>JUnit 5</li>
<li>Java Spring Boot</li>
<li>Spring Security</li>
<li>Keycloak</li>
<li>Java JDBC</li>
<li>Java JPA</li>
<li>Lombok</li>

***

## Class diagram

![Class diagram](img/classDiagaram.png?raw=true "Class diagram")

***

## First steps
1. Download the **project** in: [GitHub repository](https://github.com/J3-PT-Java-Bootcamp/AnaMontero-IronBank)

2. Make sure your **DB** is ready! <br/>
   Otherwise, create `stone_bank` DB to run the app, and `stone_bank_test` DB for testing purposes.
   Use [this script](.run/Create DB.run.xml).

4. Run the **app** from your IDE <br/>
 
5. Available **endpoints** collection is accesible below. <br/>

   [![Run in Postman](https://run.pstmn.io/button.svg)](https://www.getpostman.com/collections/8ff1de0bd0033e94352f)

***

## Description of the project

### User information
Apart from that, **Admins** are in charge of creating new accounts and new users. **_Note_**: Admins can be only created by accessing Keycloak console.

Admins will be able also to modify account statuses and balances, delete accounts, and create and delete users.

**Account holders** can be primary or secondary owners of any type of account.
Once logged in, they can access the information of their own accounts and transfer money from them to any other account in the DB.

### Account information

**Checking accounts** is the basic account type. In the case the account holder is younger than 24 years old, a student account will be created, meaning the account will not have a required minimum balance or a monthly maintenance fee.

**Savings accounts** have an interest rate that is applied automatically every year.

**Credit Card accounts** have a minimum balance according to their credit limit, and an interest rate that is deducted automatically once a month if balance is negative.

**Penalty fees** are applied if an account's balance goes below the minimum balance.

***

## API Routes

| Method | Route                                | Description                                      |
|--------|--------------------------------------|--------------------------------------------------|
| GET    | /admins/accountHolders               | Return a list with all account holder's accounts |
| GET    | /admins/checking                     | Return a list of all checking accounts holders   |
| GET    | /users/accounts                      | Return the account of the authenticated user.    |
| POST   | /admins/create/AccountHolder         | Create an account holder                         |
| POST   | /admins/create/checking              | Create a checking account                        |
| POST   | /users/create/transaction            | Create a transaction between two account holders |
| POST   | /admins/get-user-token               | Get a bearer token for a given user              |
| PATCH  | /admins/update/status/{id}/{status}  | Update the status of an account                  |
| PATCH  | /admins/update/balance/{id}/{amount} | Update the balance of an account                 |
| DELETE | /admins/delete/checking/{id}         | Delete a checking account with its id            |
| DELETE | /admins/delete/accountHolder/{id}    | Delete an account holder using their id          |