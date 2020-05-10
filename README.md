#MHD BA

Aplikácia MHD-BA slúži na plánovanie ciest V MHD Bratislava. Projekt obsahuje dáta statických cestovných poriadkov z roku 2018 a dáta o meškaní z 2/2018, 3/2018, 4/2018.

##Intalačná príručka

mvn clean install
Nainštalujú sa všetky potrebné triedy definované v súbore contract.yaml.

Na vyhľadávanie ciest používame RAPTOR algoritmus, ktorý operuje na dátovej štruktúre. Dátová štruktúra je vždy aktuálna a inicializuje sa pri spustení projektu.
Na inicializáciu potrebuje maž definovanú cestu k súborom statických cestovných poriadkov a dát o meškaní. Cesty sú definované v súbore /src/main/resources/application.properties. 
realtime-data-resources-pattern=classpath:delays/
data-resources-pattern=classpath:data/
Iniciazácia dátovej štruktúry sa spúšťa priamo pri spustení aplikácie. Aplikáciu spustíme spustením súboru Application.java.
Dátová štruktúra sa napĺňa minimálne 20 minút v závislosti od výkonnosti servera. Po nainicializovaní dátovej štruktúry sa dátová štruktúra serializuje do súboru. 
Potom ako vypíše "Data structure successfuly serialized", je dátová štruktúra pre algoritmus nainicializovaná, naplnená dátami a serializovaná do súboru.

Ďalej treba naplniť databázu. V application.properties je potrebné mať na začiatku nastavený názo 
localhost:{port}/api/importk