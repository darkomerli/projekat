Ovo je projekat koji radim u Javi pomoću Spring Boot-a. 😃

U ovom projektu cilj mi je kreirati back-end strukturu koja će se sastojati od mnoštva API koji se tiču korisnika, kanala koji su u 
vlasništvu korisnika, videa koji se kače na kanale i komentara. 
Cilj mi je da kreiram autentifikaciju pomoću koje će se svakom korisniku omogućiti upravljanje sopstvenim profilom i njegovim kanalima,
kao i kačenje videa na kanale po sopstvenom izboru, čime će se osposobiti bolja personalizacija svakog kanala i kreiranje kanala po sopstvenim željama. 
Cilj mi je kreirati i export u CSV fajl pomoću kojeg će se videti pregled svih korisnika (njihov username).
Uraditi sitne primere Cron Job-a koji će mi automatizovati određene stvari poput čuvanja fajlova.
Ovo je projekat jednog studenta 3. godine Ekonomskog fakulteta u Subotici.

Dokumentaciju za projekat vodim pomoću Swagger. Na njemu se nalaze svi API kreirani na projektu.

### UPDATE 04.01.2025. ###

Trenutno stanje je sledece:

    1. Kreiran channel ✔️
        1.1 id,
        1.2 channel_name,
        1.3 description,
        1.4 subscribers,
        1.5 user_id

    2. Kreiran user ✔️
        2.1 user_id,
        2.2 password (encrypted),
        2.3 username,
        2.4 created_at,
        2.5 updated_at

    3. Kreiran video ✔️
        3.1 id,
        3.2 description,
        3.3 likes,
        3.4 Number of comments,
        3.5 title,
        3.6 video url,
        3.7 channel_id,
        3.8 views (inkrement na svaku HTTP Get metodu)

    4. Kreiran comment ✔️
        4.1 id,
        4.2 content,
        4.3 user_id,
        4.4 video_id

    5. Kreirane implementacije servisa, sve kontrolerske klase imaju odgovarajuce API, ✔️

    6. Basic auth za user-a, ✔️

    7. Upravljanje kanalima samo za datog usera koji je trenutno ulogovan ✔️

### UPDATE 05.01.2025. ###

    8. Cron job sa izvozom liste usera u csv fajl, smisljam i dodatni use-case ✔️
--------------------------------------------------------------------------------------------------------------------------------------

Planiram sledece da uradim:

    1. Liste prijatelja,

    2. Proucavanje nacina za pravi upload videa, trenutno upload vrsim slanjem objekta gde je video url postavljen kao obican string,

    3. Proucavanje dodatne spring boot dokumentacije, nalazenje boljih praksi i novih ideja i mogucnosti

    4. Poziv na neki eksterni API.

    5. Nauciti mocking i testiranje svih poziva
