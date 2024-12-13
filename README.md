# MonuSearch

**MonuSearch** est une application Android dédiée à la visualisation, gestion et mise à jour de monuments historiques. Elle permet aux utilisateurs d'explorer une liste de monuments, de visualiser leurs détails, de les marquer comme favoris, et de synchroniser ces informations avec un serveur distant via une API.

---

## **Fonctionnalités principales**

- **Liste des monuments** : Affiche une liste des monuments avec des détails tels que leur référence, nom, et département.
- **Favoris** : Possibilité de marquer ou dé-marquer un monument comme favori. L'état est synchronisé avec le serveur.
- **Détails des monuments** : Affiche des informations détaillées sur chaque monument (adresse, commune, département, image, etc.).
- **Mise à jour asynchrone** : Les modifications locales, comme l'état de favori, sont envoyées au serveur via une API REST.
- **Actualisation** : Un bouton de "refresh" permet de récupérer une liste actualisée des monuments depuis le serveur.

---

## **Structure du projet**

### **1. Activités**
- **`MainActivity`** : Contient le conteneur principal pour les fragments.
- **`DetailedMonumentActivity`** : Affiche les détails d'un monument, y compris la gestion des favoris.

### **2. Fragments**
- **`MonumentListFragment`** : Affiche une liste de monuments. Gère la visualisation des favoris à l'aide d'icônes.
- **`MapFragment`** : Affiche une carte avec des points sur l'ensemble des monuments de l'application.
- **`InfoFragment`** : Affiche les principales informations liées  l'application.

### **3. Adapters**
- **`MonumentAdapter`** : Fournit un adaptateur pour afficher les données des monuments dans un `RecyclerView`.
- **`MonumentViewHolder`** : Gère la liaison des données d'un monument à une vue.

### **4. Modèles**
- **`Monument`** : Classe représentant les données d'un monument (référence, nom, département, favori, etc.).
- **`MonumentManagement`** : Gère les données localement, comme la liste des monuments.

### **5. Services**
- **`MonumentService`** : Définit les endpoints de l'API REST, notamment pour récupérer et mettre à jour les monuments.

---

## **API REST**

L'application utilise Retrofit pour communiquer avec une API REST. Les principaux endpoints sont :

1. **Récupération de la liste des monuments** :
   ```http
   GET /monuments
Renvoie la liste de tous les monuments.

2. **Mise à jour de l’état des favoris d’un monument**:
   ```http
   PUT /monuments/{ref}/favorite
Modifie l’état du favori pour le monument avec la référence donnée.

## **Flux de données**

### **1. Récupération des monuments**
- Lors de l’ouverture ou du refresh, l’application appelle GET /monuments.
- La réponse est stockée localement dans MonumentManagement.

### **2. Gestion des favoris**
- Lorsque l’utilisateur coche ou décoche un favori dans les détails d’un monument :
   - L’état est mis à jour localement.
   - Un appel à PUT /monuments/{ref}/favorite est effectué pour synchroniser avec le serveur.
- Afin de mettre à jour la liste des favoris, il est nécessaire d'appuyer sur le bouton "Rafraîchir" après la modification d'un ou plusieurs favoris. Cela mettra à jour l'affichage (les nouveaux favoris seront placés en haut) et les étoiles seront mises à jours dans les fragments.