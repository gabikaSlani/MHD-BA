# MHD BA

Aplikácia MHD-BA slúži na plánovanie ciest v MHD Bratislava. 
Pri hľadaní ciest prihliada na meškanie vozidiel a používateľské preferencie a ponúka používateľom alternatívne cesty.

## Intalačná príručka

Aplikácia potrebuje pre svoj beh dáta statických cestovných poriadkov vo formáte GTFS a dáta o meškaní vozidiel.
Tieto dáta je potrebné vložiť do priečinka `api/src/main/resources`. 
Tu sú vložené testovacie dáta v priečinku `/test-data` a `test-delays`.

Po naklonovaní projektu je potrebné spustiť príkaz
`mvn clean install`, aby sa vygenerovali všetky potrebné triedy definované v súbore `contract.yaml`.

Na vyhľadávanie ciest používame RAPTOR algoritmus, ktorý operuje nad dátovou štruktúrou. 
Dátová štruktúra musí byť vždy aktuálna a inicializuje sa pri spustení aplikácie.
Na inicializáciu potrebuje maž definovanú cestu k súborom statických cestovných poriadkov a dát o meškaní. 
Cesty sú definované v súbore `/src/main/resources/application.properties-test`. 

`realtime-data-resources-pattern=classpath:test-delays/`<br/>
`data-resources-pattern=classpath:test-data/`

### Dátová štrutúra 
Dátová štruktúra sa inicializuje a napĺňa dátami pri spustení aplikácie a následne je serializovaná do súboru.
Ak existuje serializovaná dátová štruktúra v projekte, pri spustení sa bude vytvárať deserializáciou zo súboru. 

### Databáza
Ďalej treba na serveri vytvoriť PostgreSQL databázu a naplniť ju dátami zo súborov. 
Do databázy sa ukladajú statické dáta pre aplikáciu. 
Predvolený názov databázy, používateľ a heslo do databázy je nastavené v `application-test.properties`.
Je potrebné vytvoriť databázu s týmito údami, prípadne ich zmeniť v konfiguračnom súbore.

`spring.datasource.url=jdbc:postgresql://localhost:5432/mhdBaTest`<br/>
`spring.datasource.username=postgres`<br/>
`spring.datasource.password=postgres`<br/>

V `application-test.properties`  je potrebné mať pred prvotným spustením aplikácie nastavenú vlastnosť aplikácie
`spring.jpa.hibernate.ddl-auto` na hodnotu `create-drop`. 
Týmto sa vymažú dáta z databázy (ak tam nejaké sú) a vygeneruje sa schéma databázy.

### Spustenie serverovej aplikácie
Teraz už môžeme spustiť serverovú aplikáciu spustením súboru `Application.java` s profilom `test`. 
Po tom ako sa vypíše jedna z hlášok 
*"Data structure successfuly serialized"*
*"Data structure successfuly deserialized from file"*
je dátová štruktúra pripravená. 

Stále však nemáme naplnenú databázu dátami. 
Je potrebné vytvoriť dopyt na `localhost:{port}/api/import`, ktorý spustí import dát do databázy.
Po vypísaní hlášky 
*"Data successfully imported to database"* sú v databáze potrebné dáta pre beh aplikácie.

Hodnotu `spring.jpa.hibernate.ddl-auto` môžeme nastaviť na `validate`, aby ďalšie spustenie aplikácie nepremazalo všetky dáta v databáze a nemuseli sme importovať dáta znovu.

### Spustenie klientskej strany
Pri prvotnom sputení treba nainštalovať všetky potrebné závislosti príkazom `npm install` (v priečinku `web`).

Po nainštalovaní potrebných závislostí, je spustíme klientskú aplikáciu príkazom `npm run serve --fix`.
Vypíše sa port, na ktorom aplikácie beží.

###Testovacie dáta
Keďže v projekte sú priložené iba testovacie GTFS dáta, 
vyhľadávanie funguje obmedzenie od 5.2.2018 - 11.3.2018. 
Jednotlivé linky jazdia približne od 5:00 do 7:00. 

