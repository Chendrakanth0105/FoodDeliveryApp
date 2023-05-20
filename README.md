## Getting Started
<br>
Welcome to the HealthyDOOR:) . It is a food delivery app which delivers the best hygenic food to your place.<br>
<br>
## Folder Structure<br>
<br>
The workspace contains two folders by default, where:<br>
<br>
- `src`: the folder to maintain sources<br>
- `lib`: the folder to maintain dependencies<br>
<br>
Meanwhile, the compiled output files will be generated in the `bin` folder by default.<br>
<br>
<br>
## Dependency Management<br>
<br>

# INTERFACES USED IN THE APP:
## a) Customer    b) Delivery Agent    c) Admin 
<br>
The User will be asked to choose the interface.<br>

- Now if the user choose "a", Then the meaning is he is a customer. So we redirect the customer to the "Customer Interface Page"<br>
    - After choosing "a", He will be given 3 Options
        1. Login
        2. Sign Up
        3. Exit<br>

        - If he choose '1' it means that customer wants to login to his account. So we redirect the customer to the "Customer Login Page"
            - So user will be asked to enter his credentials (username and password) to login
            ### VALIDATION STEP:
                - If the customer's entered username and password already appear in the customer login table inside the database, it is likely that the person has already registered using those details. We will allow him to properly log in.
                - If the customer's entered username and password already does not appear in the customer login table inside the database, it is likely that the person has not registered using those details. We will not allow him to log in.
             - After successfull login the customer will be provided with 2 options
                1. Change your password
                2. Order food

                - If the customer choose the option '1', Then user will be asked to enter the new password. So we redirect the customer to the "Customer Password Change Page"
                    ### VALIDATION STEP:
                    - If the customer enters the same password again in the new password option, then the customer will be given a error message.
                    - The customer will successfully change the password if he or she enters a different one, And the password will be updated in the database.
                    
                - If the customer choose the option '2', then it means that he wants to order some food. So we redirect the customer to the "Ordering Page"
                    - Customer will be displayed with the HealthyDOOR food menu from the database.
                    - Now we will ask the customer to order the food from the menu.
                    - Then we will ask the quantity of the food customer is ordering.
                    - We will ask the customer whether he wants to place any additional orders.
                        - If customer say "Yes" then will again repeat the second and third step (ask for the dish and the quantity).
                        - If customer say "No", then will start the following options:
                            - We will place the order.
                            - We will start preparing the order.
                            - We will assign a delivery agent from our company.
                            - We will deliver the food successfully.
                    - After the food is delivered successfully, We redirect the customer to the "Payment Page". We will provide the bill to the customer and provide 3 payment options
                        1. Cash
                        2. Gpay/Phonepe
                        3. Card (This option is unavailable at the movement, we will be working on it.)

                        - If customer choose the option '1', then he will be asked to pay the cash.
                        - If customer choose option '2', then the customer will be provided with a mobile number, so that customer can pay the amount to that mobile number.
                        - If the customer choose option '3', then the customer will be get an error message and redirects to the Payment page.
                    - After the payyment is done successfully, we will ask the customer to rate the food he ordered and the delivery agent who has delivered his food.
                    - After the Customer rate the food and delivery agent, He will asked about whether he wants to exit.
                        - If the customer say "yes", then the customer will be exited successfully and programs ends.
                        - If the customer say "no", then the customer will be redirected to the "Customer Interface Page".
                    - All the details will be updated in the database.

        -  If he choose '2' it means that customer wants to register on our app. So we redirect the customer to the "Customer Registration Page"
            - The Customer will be asked to choose his username, password.
            - The Customer will be asked to enter his mobile number and email id.
            - After entering the details, Customer will be registered successfully. And the details will be added in the database.
            - After Registering successfully, he will be asked about whether he wants to exit.
                - If the customer say "yes", then the customer will be exited successfully and programs ends.
                - If the customer say "no", then the customer will be redirected to the "Customer Interface Page".

        - If he choose '3' it means that customer wants to Exit from the app. So we will end the program right away.

