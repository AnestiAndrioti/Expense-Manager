# Motivation

I started my career as a C++ developer. 3 years in, I was proposed to take on a new role on a monitoring framework as a Java developer. Having done little work with Java, I decided to start this little project on my own to practice my skills and get to know the language.

After deciding to experiment a bit with the language, I had to choose a topic. Being short on ideas, I was looking for something that was demarcated enough so that I would not be dispirited to work on it in parallel with my 9 to 7 job. 
I liked the idea of creating a piece of software that would monitor my doings and give me significant statistics, which would eventually give me more insights on myself. I was hesitating between a task manager system and an expense manager system. I ended up going for the expense manager. 


# Technical Details

I am going for a client server architecture with a remote DB to persist data. The communication channel between the different entities would occur using HTTP and ReST. The idea is to create an application that could scale. I tried as much as possible to follow the notion of creating something that is easy to use, and hard to misuse.  

My goal was to learn to manipulate different aspects, concepts, and technologies that Java has to offer.
I hope that I was able to follow the best practices. I tried to abide as much as possible to the SOLID design principles. 
I will detail below the different building blocks of my software.

####  Account
The account is defined by a unique id. It also has an account currency, and a list of expenses. If you end up adding and expense of different currency, a currency converter will take care of converting the expense to the account’s currency. This will be tackled further below. The accounts are stored inside an account repository which is linked to a database.
This class is **thread-safe**

####  Expense
An expense holds different kind of information (id, name, Money (a combination of currency and amount), location, time, category...). 

####  Money
 Combination of Currency and amount.

####  CurrencyConverter
Converts a Money object from a source currency to a destination currency.
To do so, it queries the services offered by www.currencyconverterapi.com. It does so by using Java 11’s net API. It also uses Jackson to parse the received JSON response.

####  ExpenseManager
A static class that is responsible for adding expenses to an account, deleting an expense from an account, and getting the expenses of an account. The ExpenseManager also takes care of converting the expense to the appropriate currency if need be.
To be able to write some tests for CurrencyConverter and ExpenseManager, I used Mockito.

####  StatisticsComputer
A static class where significant statistics are computed such as 
- Computing the sum of expenses for a given month
- Computing the ratios of categories for the expenses of a given month
- Sorting expenses of a given month by amount of the account’s currency

####  ReST API
This is the entrypoint of the application. The nice thing about this software is that the end user will run everything on the backend server. Their devices will just be used to send, receive, and display information.
I tried to abide by the principles of ReST by aiming to have a high Richardson Maturity level. This was a key point for me as this was an investment that would generate an extensible software. (a few examples are, returning the correct HTTP code, having clear URL with no verbs, having clear messages in case of error, specifying the location of the next possible action after an execution, i.e. a post request to create an expense will indicate the url of the created expense and will return a 201 Created…)
For now, here are the defined entrypoints.
- Account endpoints
    -   GET /accounts
    -	GET /accounts/{accountId}
    -	POST /accounts
    -	DELETE /accounts/{accountId}
- Expense endpoints
    -	GET /accounts/{accountId}/expenses
    -	GET /accounts/{accountId}/expenses/{expenseId}
    -	POST /accounts/{accountId}/expenses
    -	DELETE /accounts/{accountId}/expenses/{expenseId}
- Statistics endpoints
    -	GET /accounts/{accountId}/stats/sumOfExpenses/{year}/{month}
    -	GET /accounts/{accountId}/stats/ratiosOfCategories/{year}/{month}
    -	GET /accounts/{accountId}/stats/sortedExpenses/{year}/{month}


# Possible Next steps
- Right now, I am focusing on learning **DART** to be able to implement a **client application** that will be able to communicate with the backend. Fortunately, most of the job is already done on the server side. Thus, it’s just a matter of having a client application that can communicate with the server and have some nice and friendly displays.
- Add **more functions** to the **statistics API** to get more and more data which in turn will give better insights about someone’s personality, habits, state of mind...
- Create an **authentication** and **authorization** **service** to be able to implement a **log in** functionality. The different actions on the account will be **allowed** only after the user has been authorized.
- **Scale** the application **horizontally**. This will make the application more efficient. We can also focus on making it  **Highly Available**. This can be done on different levels. We can have workers that handle incoming requests. We can persist the data on different nodes. We can modify certain aspect of the code, for example, the currency converters and statistics computers could be made non static, and we will have different instances that do the work. We can even add different **metrics** on them to monitor their performances and impact, and act accrodingly. 
- Create a **logging service** that will enhance the logging throughout the application, and store those logs in a **log repository** (Maybe use a NoSQL DB for this such as ElasticSearch)
- As it is now, every account is using the same token to access www.currencyconverterapi.com. We can create a **setup account step** which will fetch a new token that will be used by a given account only. We will need to create another **service** that will **distribute** the right **token** to the right account. 
- Move from a localhost/H2 application to a **Cloud** application with an appropriate **DB vendor**
- If the above-mentioned different services are created following a **microservices architecture**, we will be needing a **Discovery** or **Service Manager** technology in order to handle those different services.

