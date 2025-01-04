Ovo je projekat koji radim u Javi pomoću Spring Boot-a. 
U ovom projektu cilj mi je kreirati osnovnu strukturu koja će se sastojati od bazičnih API poput create, delete, update i tako dalje. 
Posle toga kreiraću autentifikaciju pomoću koje će se svakom korisniku omogućiti upravljanje sopstvenim profilom i njegovim kanalima. 
Nakon nekog vremena dodaću i mogućnost ubacivanja videa gde će svaki user imati mogućnost da kreira kanal po njegovoj želji.
Ovo je projekat jednog studenta 3. godine Ekonomskog fakulteta u Subotici.

### UPDATE 04.01.2025. 15:36:20 ###

Trenutno stanje je sledece:

    1. Kreiran channel 
        1.1 id,
        1.2 channel_name,
        1.3 description,
        1.4 subscribers,
        1.5 user_id

    2. Kreiran user
        2.1 user_id,
        2.2 password (encrypted),
        2.3 username

    3. Kreiran video
        3.1 id,
        3.2 description,
        3.3 likes,
        3.4 Number of comments,
        3.5 title,
        3.6 video url,
        3.7 channel_id,
        3.8 views (inkrement na svaku HTTP Get metodu)

    4. Kreiran comment
        4.1 id,
        4.2 content,
        4.3 user_id,
        4.4 video_id

    5. Kreirane implementacije servisa, sve kontrolerske klase imaju odgovarajuce API, sem komentara, koje cu dodati u naredna 2 sata,

    6. Basic auth za user-a,

    7. Upravljanje kanalima samo za datog usera koji je trenutno ulogovan

--------------------------------------------------------------------------------------------------------------------------------------

Planiram sledece da uradim:

    1. Liste prijatelja,

    2. Proucavanje nacina za pravi upload videa, trenutno upload vrsim slanjem objekta gde je video url postavljen kao obican string,

    3. Proucavanje dodatne spring boot dokumentacije, nalazenje boljih praksi i novih ideja i mogucnosti

    4. Pokusacu da napravim sitan cron job i poziv na neki eksterni API.

    5. Nauciti mocking i testiranje svih poziva
