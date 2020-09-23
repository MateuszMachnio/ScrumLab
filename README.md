# Scrum Lab
It is a group project implemented after passing the third module of the course in CodersLab. \
This project brings together various skills from __Java basics__, __OOP__ & __MySQL__ and Servlets.
## What programme is that?
It is a servlet based application. Thanks to it, the user can manage his weekly nutritional plans. \
This is done through CRUD operations performed on plans and specific recipes.
## How it works?
The new user is asked to register, then can log in. \
A logged in user can copy recipes that are in the recipe base of all users to his recipe database.

A specific servlet is assigned to each user action. \
It performs operations by using DAO classes with CRUD methods (which communicate with the database) and passes the data to be displayed to certain JSP files. \
Superadmin has a different appearance of the application and also has an additional tab "users" in the sidebar where a list of all users is displayed.
He can edit user data, promote them to superadmins, or demote other superadmins to regular users.
Superadmin can also block and unblock users.
