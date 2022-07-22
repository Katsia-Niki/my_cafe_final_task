# Cafe

A customer makes an order in the café. He inputs time for picking up. The application shows order sum and offers to pay by account, cash or loyalty points. A customer receives loyalty points depending on payment type. The administrator manages the menu, can block\unblock customers.

## Functional roles
||GUEST|CUSTOMER|ADMIN|
| :- | :-: | :-: | :-: |
|Register|+|-|-|
|Sign in|-|+|+|
|Sign out|-|+|+|
|Look through available to order menu|+|+|+|
|View contact page|+|+|+|
|Change locale|+|+|+|
|Update personal data|-|+|+|
|Change password|-|+|+|
|Add menu item to a cart|-|+|-|
|Delete menu item from cart|-|+|-|
|Order menu items for a concrete time|-|+|-|
|Cancel order|-|+|+|
|Look through his orders|-|+|-|
|Replenish balance|-|+|-|
|Look through all menu|-|-|+|
|Update menu items|-|-|+|
|Create new menu items|-|-|+|
|Add new admins|-|-|+|
|Find by different parameters and update orders|-|-|+|
|Block\unblock customers|-|-|+|

## Database schema
</p>
<p align="center">
  <kbd> <img alt="Database" src="https://user-images.githubusercontent.com/82093777/180476009-f9e2c73c-57ae-49cf-8f8f-0561eaf6bd78.png" width="800" style="border-radius:10px"\></kbd> 
</p>

