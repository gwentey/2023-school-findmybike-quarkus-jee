## Objectifs du système à modéliser

 ▷ _On propose de modéliser un système de gestion de vélos électriques. Ce système permettra aux utilisateurs de localiser, réserver et utiliser des vélos électriques, tout en offrant aux gestionnaires de flottes une solution pour gérer ces véhicules de manière optimale._

Pour les **utilisateurs**, l'expérience débute par la localisation du vélo le plus proche, en tenant compte du niveau de batterie. Une fois le vélo réservé, l'utilisateur peut le prendre, l'utiliser et le rendre à la fin de son trajet.

Les **chargeurs** sont responsables de la récupération, de la recharge et de la remise en circulation des vélos. Leur mission consiste à localiser les vélos ayant besoin de recharge, à les récupérer, à les charger, et finalement à les rendre disponibles dans des zones spécifiques. L'idée est de les inciter à charger davantage en fonction de la quantité d'énergie rechargée, optimisant ainsi le rendement du système.

Les **gestionnaires de flotte** ajuster la taille de la flotte en ajoutant ou supprimant des vélos dans chacune des zones en fonction des besoins.

## Interfaces


## Schéma relationnel


## Exigences fonctionnelles


## Exigences non fonctionnelles

* Les interactions machine-machine doivent se faire via le messaging
* Les interactions machine-utilisateur avec une API REST
* Chaque service a sa propre DB
