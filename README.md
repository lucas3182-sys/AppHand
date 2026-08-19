# HandContrats — Application EPS Handball

Application tablette (Android) pour le suivi des contrats individuels lors d'un tournoi de handball en 3 équipes (EPS).

## Ce que fait l'application

- Composition de 3 équipes de 4 joueurs
- Réglage par le professeur des objectifs de contrats (buts, passes décisives, récupérations, arrêts du gardien)
- 3 matchs (A-B, B-C, C-A), avec choix du contrat par chaque élève avant chaque match
- Écran d'observation en direct pendant le match (score + progression des contrats)
- Récapitulatif après chaque match, classement final avec export PDF automatique
- Fonctionne **hors-ligne** (aucune connexion internet nécessaire)
- Sauvegarde automatique : reprise du tournoi en cours si l'application est fermée accidentellement
- Verrouillée en mode **paysage**

## Comment récupérer l'APK (aucune compétence technique requise)

Ce dépôt est configuré pour construire automatiquement l'APK à chaque mise à jour, grâce à **GitHub Actions**. Tu n'as rien à installer sur ton ordinateur.

### Étape 1 — Créer le dépôt GitHub

1. Va sur [github.com](https://github.com) et connecte-toi (ou crée un compte gratuit).
2. Clique sur **New repository** (bouton vert en haut à droite).
3. Donne-lui un nom, par exemple `handball-tournament-app`.
4. Laisse-le en **Public** ou **Private** (peu importe), ne coche aucune case (pas de README, pas de licence), puis clique **Create repository**.

### Étape 2 — Envoyer les fichiers sur GitHub

Sur la page qui s'affiche, GitHub te donne des commandes. Le plus simple si tu ne connais pas Git :

1. Sur la page du dépôt vide, clique sur **uploading an existing file**.
2. Fais glisser **tout le contenu** de ce dossier (sauf `node_modules` s'il existe) dans la zone de dépôt.
3. Clique **Commit changes**.

*(Si tu es à l'aise avec Git en ligne de commande, la méthode classique fonctionne aussi : `git init`, `git add .`, `git commit -m "premier envoi"`, `git remote add origin <url>`, `git push`.)*

### Étape 3 — Récupérer l'APK généré automatiquement

1. Une fois les fichiers envoyés, va dans l'onglet **Actions** en haut du dépôt.
2. Tu verras un workflow **« Build Android APK »** en train de tourner (cercle jaune qui tourne). Ça prend environ 3 à 5 minutes.
3. Une fois terminé (coche verte ✅), clique dessus.
4. En bas de la page, dans la section **Artifacts**, télécharge **`handball-tournament-app-debug`** — c'est un fichier `.zip` qui contient l'APK.
5. Dézippe-le : tu obtiens `app-debug.apk`.

### Étape 4 — Installer l'APK sur la tablette

1. Transfère le fichier `app-debug.apk` sur la tablette (clé USB, câble, ou lien de téléchargement type Google Drive/e-mail).
2. Sur la tablette, ouvre le fichier — Android te demandera d'autoriser l'installation depuis une « source inconnue » (normal, car l'appli n'est pas sur le Google Play Store). Accepte.
3. L'application **HandContrats** s'installe, avec son icône (rond jaune, joueur de handball).

### Relancer une nouvelle version

À chaque fois que le code est modifié et renvoyé sur GitHub (nouvel « upload » de fichier ou nouveau commit), l'APK se reconstruit automatiquement dans l'onglet **Actions** — il suffit de retélécharger l'artifact le plus récent.

## Structure du projet (pour référence)

```
handball-tournament-app/
├── www/                     → l'application (HTML/CSS/JS), tout est ici
│   ├── index.html
│   ├── css/fonts.css
│   ├── js/vendor/           → bibliothèques locales (Capacitor, PDF)
│   ├── fonts/                → polices locales (hors-ligne)
│   └── assets/hero.png
├── android/                  → projet Android natif (généré par Capacitor)
├── resources/                 → images sources (icône, splash screen)
├── capacitor.config.json     → configuration de l'application
├── package.json
└── .github/workflows/build-apk.yml   → construction automatique de l'APK
```

## Notes techniques

- L'APK généré est une version **debug** (installable directement, pas besoin de signature particulière). Pour une diffusion plus large (Play Store), il faudrait une version *release* signée — pas nécessaire pour un usage en établissement scolaire via transfert direct.
- L'export PDF du classement final s'enregistre directement dans le dossier **Documents** de la tablette (fonctionne hors-ligne).
- Le bouton retour Android est intercepté pendant le tournoi pour éviter une sortie accidentelle.
