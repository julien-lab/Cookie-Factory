# Conception logicielle Cookie Factory 

Cookie Factory est une usine à Cookies qui prépare des cookies à ses clients. Elle propose également la livraison à domicile.


## AddOrDeleteCookie.feature

En tant que client 

Je veux pouvoir ajouter/supprimer des cookies de ma commande

Afin de pouvoir construire ma commande

Scenario : add a certain number of cookies / delete a cookie / delete cookies to the point where there is no left / delete more cookies than there is in the orderline

## ChooseOrderShop.feature

En tant que client 

Je veux choisir le magasin qui fera mes cookies

Afin de pouvoir aller les chercher

Scenario : validate the shop choosen / invalidate the shop choosen

## ChooseOrderPickUpTime.feature

En tant que client 

Je veux choisir le moment ou je vais aller chercher mes cookies

Afin de pouvoir aller les chercher quand je suis disponible

Scenario : set order pick-up time / set wrong order pick-up time / set same pick-up time for the 2 orders

## CreateAccount.feature

En tant que client 

Je veux pouvoir créer un compte

Afin de pouvoir profiter des réductions

Scenario : create a simple account / join loyalty program 

## HaveDiscount.feature

En tant que client 

Je veux pouvoir bénéficier des rédutions

Afin de payer moins cher

Scenario : the client reaches 30 cookies while ordering / the client wants apply loyalty discount but has ordered less than 30 cookies
## ManageLocalShop.feature

En tant que manager local 

Je veux pouvoir changer les horaires d'ouverture

Afin de m'adapter à la clientelle

Scenario :  the local manager change opening time of his shop / the local manager change closing time of his shop

En tant que manager local 

Je veux pouvoir accéder aux statistiques de mon magasins

Afin de m'adapter à la clientelle

Scenario :  the local manager wants to access his shop statistic

## ManageLocalShop.feature

En tant que manager national

Je veux pouvoir changer ajouter/supprimer des recettes

Afin de proposer des nouveautés aux clients

Scenario :  National manager wants to add recipe / National manager wants to delete recipe

En tant que manager national 

Je veux pouvoir accéder aux statistiques nationales

Afin de m'adapter à la clientelle

Scenario :  National manager wants to retrieve orders data


## OrdersPrepration.feature

En tant que superviseur de commande

Je veux pouvoir donner les listes des cokkies à préparer aux cuisiniers

Afin de leur permettre de travailler

Scenario :  Order Supervisor send the list of cookies to prepare

En tant que cuisinier

Je veux pouvoir annoncer que j'ai fini de préparer des cookies

Afin que les commandes soit préparée

Scenario :  Cook bake the cookies to prepare

En tant que superviseur de commande

Je veux pouvoir annoncer qu'une commande est terminée et l'envoyer au caissier

Afin de mettre la commande à disposition des clients

Scenario :  Order Supervisor check the order finished and send them to cashier

## PurchaseOrder.feature

En tant que client

Je veux payer ma commande

Afin de la valider

Scenario :  the client purchase his order without problem / the client order's can't be purchased / the client tries to pay his order without a pick up time / the client tries to pay his order but doesn't have enough money

## WithdrawnOrder.feature

En tant que client

Je veux aller chercher ma commande

Afin récupérer mes cookies

Scenario :  a Client pick up his order in the good shop / a Client pick up his order in the wrong shop


## BestOfCookie.feature

En tant que manager local

Je veux connaitre quel cookie est le plus populaire

Afin de pouvoir proposer des réductions

Scenario :  The local manager wants to know the most popular cookie of his shop

En tant que manager national

Je veux connaitre quel cookie est le plus populaire

Afin de pouvoir proposer des réductions

Scenario :  The national manager wants to know the most popular cookie of his entire production chain

En tant que client

Je veux commander le cookie le plus populaire d'un magasin / au niveau nationnal

Afin de pouvoir bénéficier des réductions

Scenario :  a Client wants to order the best cookie of the cookie factory / a Client wants to order the best cookie of his shop

## CreateCustomizedCookieRecipe.feature

En tant client

Je veux pouvoir créer une recette personnalisée

Afin d'avoir un cookie adapté à mes goûts

Scenario :  create a valid customized cookie recipe / create an invalid customized cookie recipe

## kitchenBalancing.feature

En tant manager national

Je veux qu'un nouveau magasin soit proposé au client si celui choisi ne peux pas faire sa commande

Afin de perdre moins de client lors de problème technique, de manque d'horaire disponible et de manque d'ingredient

Scenario :  there is a technical issue in the shop the client choose in his order / there is and ingredient issue in the shop chosen

## ChooseLastMinuteMarcelDelivery.feature

En tant que client 

Je veux pouvoir me faire livrer ma commande

Afin de ne pas avoir à me rendre au magasin en cas d'empechement ou par commodité

Scenario : lastMinuteMarcel receives a doable delivery demand / lastMinuteMarcel receives a doable late delivery demand / lastMinuteMarcel receives an undoable delivery demand



