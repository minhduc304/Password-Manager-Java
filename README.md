# **Password Vault (with password generator)** 


### *Personal Project Proposal*

I want to make a **password vault** with its own built-in password generator.
A user should be able to generate a **random and secure** password, copy this password onto their clipboard 
and assign it
(or optionally create their own password) 
to an object instance **(e.g. SSC account)** that contains things such as their **username**, **password**, **URL link** (if it is a website) etc.
A user should be able to create **multiple** instances like the one above for their different accounts and password, all hashed and stored locally.


I can be very forgetful of the small things in my life. 
This includes passwords which, which I find myself having to note down in **insecure places** or **recycle** the same password for multiple accounts.
In this digitized world where a lot of our information is online, having a way to securely record and access our information is very beneficial, 
and that is why I believe a majority of people today would find this type of application useful. 
However, not having to come up with and remember your passwords are always a plus.
Additionally, I would love to work with and learn more about the **security** and **database** aspect of software construction.
These are the reasons why I am interested in this project.

### *User Stories*

As a user:
- I want to be able to add any password to an object associated (I'll call these password profiles) with the account that uses this password.
- I want to be able to name this profile object, add other login credentials and optional information such as URL.
- I want to be able to view current password profiles created with their associated profile names. 
- I want to be able to copy the password in a password profile into my clipboard.
- I want to be able to edit a password profile after creating it. 
- I want to be able to add and delete as many profiles as I want. 
- I want to be able to save created profiles to file and save changes made to file.
- I want to be able to load saved profiles from file when I start the application. 

#### Phase 4: Task 2
- New profile added to Password Manager.
- New profile added to Password Manager. 
- New profile added to Password Manager.
- New profile added to Password Manager. 
- New profile added to Password Manager. 
- New profile added to Password Manager. 
- New profile added to Password Manager. 
- Displayed all profiles in the Password Manager.
- Displayed all profiles in the Password Manager. 
- A password has been copied. 
- New profile added to Password Manager. 
- Displayed all profiles in the Password Manager.

#### Phase 4: Task 3
I think that there is a good flow to the design of my program.
The Y (in this case the PasswordManager) is the only object that 
gets the X's (the Profiles). In the same vein, the PasswordManagerApp only gets
the PasswordManager and the PasswordManagerGUI only gets the PasswordManagerApp.
If I wanted to make changes to any of these classes, in most cases, it would only be 
necessary to make changes in the class where it is directly associated
with. 

Although, one big change I would make is removing the PasswordManagerApp class (the console class). 
A lot of my methods in the PasswordManagerApp or parts of the methods are deprecated,
so I think it might be better to just remove the class entirely.
However, I do wonder if this would reduce cohesion, since a lot of my GUI methods deal with the JSwing 
components.

