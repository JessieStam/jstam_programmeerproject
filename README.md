# Scuba Scan

**Jessie Stam** -
*10560599*

Een applicatie die duikers in staat stelt een logboek bij te houden. De stikstoflevels worden voor de duiker berekend. Meer informatie over de exacte werking van de app is te vinden in de het report. Onderstaande text bevat het oorspronkelijke plan.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/titelscherm.png" width="600">

Als duiker wordt je getracht een logboek bij te houden van alle duiken die je maakt. Niet alleen om bij te houden waar je bent geweest en hoeveel uren van je leven je in totaal op de bodem van de zee hebt doorgebracht, maar ook vooral voor het garanderen van je veiligheid. Een logboek is dus erg belangrijk om goed in te vullen. Bij het berekenen van de stikstofpercentages in je bloed is het echter eenvoudig om fouten te maken (zeker als dit percentage te hoog is en de duiker warriger wordt in zijn hoofd). Dit kan gevaarlijk zijn en om die reden wil ik een applicatie voorstellen waarin een duiklogboek bijgehouden kan worden waarin automatisch de stikstoflevels worden berekend. De applicatie kan de duiker dan waarschuwen als hij zich in een gevarenzone bevindt.

De applicatie zal in de eerste plaats hetzelfde werken als een normaal logboek. Waar duikers bij een analoog logboek zelf moeten berekenen hoeveel stikstof ze in hun bloed hebben en hoelang het duurt voordat dit is verdwenen, zal de applicatie dit voor de duiker doen. Door de maximale diepte en de bodemtijd in te voeren kan de applicatie dit berekenen. Door middel van een timer kan de applicatie aangeven hoelang het nog duurt voor de duiker weer veilig onderwater kan gaan na de duik. Door middel van een pushbericht kan de applicatie de duiker een melding geven als de wachttijd om is.

Vanzelfsprekend worden alle duiken opgeslagen in het logboek en kan de duiker ook gemakkelijk duiken terugzoeken in zijn archief door bijvoorbeeld te filteren op land, zee, rif, watersoort, boot of kantduik, datum, et cetera.	Indien de tijd het toelaat, lijkt het me leuk om een optie toe te voegen die een lijst met flora en fauna laat zien voor het ingevoerde gebied. De duiker kan dan aangeven wat hij heeft gezien aan de hand van afbeeldingen. In het echt is het vaak lastig vast te stellen welke vissen er zijn gezien, vanwege taalbarrières tussen duiker en gids. Ook is het een optie een sociaal aspect te implementeren, zodat bevriende duikers elkaars duiken kunnen bekijken en zo ervaringen kunnen delen.

De data die nodig is voor het implementeren voor deze applicatie is in de eerste plaats welk stikstoflevel correspondeert met welke diepte en bodemtijd. Er moet uitgezocht worden of hier een API voor bestaat of dat hier een activity voor geschreven moet worden. Daarnaast moet er eventueel een API worden gevonden voor het vinden van flora en fauna per gebied. Indien dit niet bestaat, kan dit ook handmatig worden geschreven. Voor het delen van het logboek met anderen zal gebruik worden gemaakt van Google Firebase.

De applicatie zal dus bestaan uit het logboekarchief, waarin de duiker zijn geregistreerde duiken kan terugzien, een functie om nieuwe duiken toe te voegen waarbij automatisch het stikstoflevel zal worden berekend en er een optie is om flora en fauna toe te voegen, en tot slot een mogelijkheid om duiken van andere gebruikers door te kijken. Dit laatste is echt een extra mogelijkheid die alleen bij veel tijd over zal worden geïmplementeerd.

Problemen die kunnen ontstaan bij het implementeren van de applicatie zijn vooral het ontbreken van passende API’s. Indien deze niet aanwezig zijn is het niet moeilijk om de informatie zelf te verkrijgen, maar dit zal wel aanzienlijk meer tijd in beslag nemen. Het is dus van belang vanaf begin af aan goed de tijd te managen.

Er zijn wel al een aantal applicaties beschikbaar die ook een logboek bijhouden. Hier wordt echter niet specifiek aandacht gevestigd op de stikstoflevels en veiligheid. Ook is hier geen optie om specifieke flora en fauna toe te voegen aan een duik. Wat mij vooral opvalt is dat veel van de applicaties zeer technisch en complex ogen met een overschot aan technische informatie. Ik denk juist dat een minimalistische en simpele user interface is wat nodig is om een logboek overzichtelijk te houden. Dit is dan ook hoe ik mijn applicatie ga implementeren.

<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Titelscherm.bmp" width="400">
<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Start%20menu.bmp" width="400">
<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Nieuwe%20duik.bmp" width="400">
<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Logboek%20archief.bmp" width="400">
<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Statistieken.bmp" width="400">
<img src="https://github.com/JessieStam/jstam_programmeerproject/blob/master/doc/Vissen%20gezien.bmp" width="400">
