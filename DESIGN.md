#Design Document

Om mijn applicatie naar behoren te laten functioneren, zal gebruik worden gemaakt van een groot aantal verschillende classes. Deze classes zijn onder te verdelen in activities, adapters, datafetchers, helpers, items en lists. Hieronder volgt een overzicht van de activities en hun relatie tot elkaar.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Activities.jpg">

**Activities**

*HomeActivity*

Een welkomscherm met een welkomstekstje en een optie tot inloggen of account aanmaken.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Titelscherm.bmp" width="400">

*LoginActivity*

Een Activity die gebruik maakt van Google Firebase om de gebruiker te laten inloggen of een account aan te maken. Dit is noodzakelijk om de rest van de app te gebruiken.

*StartMenuActivity*

Dit is het menu van de applicatie. Hier kan de gebruiker kiezen welke actie hij met de applicatie wil uitvoeren. Deze activity bevat knoppen naar NewDiveActivity, LogArchiveActiviy, FishActivity en StatsActivity. Ook is hier tezien in welke stikstofcategorie de duiker zich momenteel bevindt. 

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Start%20menu.bmp" width="400">

*NewDiveActivity*

Dit is waarschijnlijk de belangrijkste activity van de applicatie. Hier voert de duiker de log voor een nieuwe duik in. Aan de hand van de bodemtijd, diepte en het vorige stikstoflevel wordt het stikstoflevel van de duiker berekend. Ook kan de duiker hier eventueel vissen die hij heeft gezien toevoegen. De duik kan vervolgens worden opgeslagen in het logboek, waarna de duiker naar ViewDiveAcitivy wordt geleid.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Nieuwe%20duik.bmp" width="400">

*LogArchiveActivity*

Dit is de activity waar alle bewaarde duiken te zien zijn in een recyclerview. Deze duiken kunnen worden gefilterd op locatie, datum en nog een paar andere nader te bepalen punten. Als de duiken worden aangeklikt, wordt de duiker naar de ViewDiveActivity geleidt.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Logboek%20archief.bmp" width="400">

*FishActiviy*

Hier kan de duiker een lijst zien van alle vissen die hij heeft gezien. Misschien dat het nog mogelijk wordt om op de vis te klikken en meer informatie te verkrijgen. Dit hangt af van de tijd.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Vissen%20gezien.bmp" width="400">

*StatsActivity*

Hier vindt de duiker statistieken die zijn bepaald aan de hand van zijn gelogde duiken.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Statistieken.bmp" width="400">

**Adapters**

*LogArchiveAdapter*

Een adapter voor het laten zien van de gelogde duiken in de recyclerview.

**Datafetchers**

*FishAsyncTask*

Moet het mogelijk maken aan de hand van API’s vissen voor het ingegeven gebied te vinden.

**Helpers**

*MenuHelper*

Een helperclass voor het menubalkje dat boven in beeld van de applicatie komt te staan.

*LogArchiveManager*

Een managerclass die de data van het logboek bijhoudt.

*LoginHelper*

Hierin komt code van Google Firebase te staan die de gebruiker in staat stelt in te loggen of een account aan te maken.

*NitroCalculator*

In deze class worden de stikstoflevels in het bloed van de duiker berekend aan de hand van de diepte van de duik, de bodemtijd en de tijd dat de duiker terug aan de oppervlakte is. Deze gegevens worden teruggegeven aan de gebruiker.

*FishManager*

In deze class wordt de lijst van vissen geregeld.

**Items**

*DiveItem*

Hier worden de duikobjecten van het logboek opgeslagen, onder andere de hoeveel lucht er is verbruikt, hoeveel lood de duiker omhad, waar en wanneer de duik was, et cetera.

*User*

In dit object staan de gegevens van de gebruiker.

*FishItem*

Hier worden de verschillende gegevens van de vissen opgeslagen.

**Lists**

*DiveList*

De lijst met alle duiken.

*FishList*

De lijst met alle vissen.

De API voor de vissen per locatie zal dus een externe bron zijn waarvan gebruik zal worden gemaakt. Deze API komt van GitHub. Daarnaast zal dus gebruik worden gemaakt van Google Firebase. Dit zal worden gebruikt om in te loggen en om de data van gebruikers op te slaan. Mocht dit laatste nou niet lukken, zal SQLite gebruikt worden om de data van de gebruiker te bewaren.
