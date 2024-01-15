## Objectifs du système à modéliser

 ▷ _On propose de modéliser un système de gestion de vélos électriques. Ce système permettra aux utilisateurs de localiser, réserver et utiliser des vélos électriques, tout en offrant aux gestionnaires de flottes une solution pour gérer ces véhicules de manière optimale._

Pour les **utilisateurs**, l'expérience débute par la localisation du vélo le plus proche, en tenant compte du niveau de batterie. Une fois le vélo réservé, l'utilisateur peut le prendre, l'utiliser et le rendre à la fin de son trajet.

Les **chargeurs** sont responsables de la récupération, de la recharge et de la remise en circulation des vélos. Leurs missions consistent à localiser les vélos ayant besoin de recharge, à les récupérer, à les charger, et finalement à les rendre disponibles dans des zones spécifiques. L'idée est de les inciter à charger davantage en fonction de la quantité d'énergie rechargée, optimisant ainsi le rendement du système.

Les **gestionnaires de flotte** ajustent la taille de la flotte en ajoutant ou supprimant des vélos dans chacune des zones en fonction des besoins.


## Exigences fonctionnelles

**Localisation des vélos :**
_L'utilisateur_ DOIT pouvoir localiser le vélo électrique disponible le plus proche de son emplacement actuel.

**Réservation de vélos :**
_L'utilisateur_ DOIT avoir la possibilité de réserver un vélo électrique.
Une fois réservé, le vélo doit être marqué comme indisponible pour d'autres utilisateurs pendant la période de réservation.

**Gestion de Flotte :**
_Les gestionnaires de flotte_ DOIVENT pouvoir ajouter ou supprimer des vélos à leur flotte par zone en fonction des besoins utilisateurs.

**Optimisation de la Charge :**
_Les chargeurs_ DOIVENT recevoir des informations sur les vélos nécessitant une recharge en fonction de leur niveau de batterie.
_Les chargeurs_ sont rémunérés également en fonction de la distance du vélo récupéré par rapport à la zone disponible la plus proche. 
La rémunération des _chargeurs_ = kilométrage + le pourcentage de batterie chargé.

## Exigences non fonctionnelles

* Les interactions machine-machine doivent se faire via le messaging
* Les interactions machine-utilisateur avec une API REST
* Chaque service a sa propre DB
