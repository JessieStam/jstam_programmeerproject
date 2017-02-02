#PROCESS BOOK

**Maandag 09-01-'17**

* Project proposal is af, alleen schetsen moeten worden toegevoegd.

* Schetsen zijn ook toegevoegd.

**Dinsdag 10-01-'17**

* Design document is af. Ik heb dezelfde schetsen gebruikt aangezien deze al vrij uitgebreid waren.

* Begonnen aan proposal.

* Firebase toegevoegd aan app.

**Woensdag 11-01-'17**

* Projecten gepitched voor groepje. Selfie gestuurd naar Martijn.

* Verder werken aan Firebase ingloggen.

**Donderdag 12-01-'17**

* Verder gewerkt aan Firebase inloggen, werkt nog niet helemaal.

* Gewerkt aan de layout, voor alles activities aangemaakt.

**Vrijdag 13-01-'17**

* Presenatatie eerste idee.

* Verder gewerkt aan layout, begonnen met het design.

* Verder gewerkt aan Firebase.

**Maandag 16-01-'17**

* Inloggen werkend gekregen.

* Gelezen over Firebase database.

**Dinsdag 17-01-'17**

* Begonnen aan NewDiveActivity.

* Eerste fragments gemaakt voor data.

**Woensdag 18-01-'17**

* Gekeken naar FishBase api.

* Verder gewerkt aan fragments - het lukt niet om de data per fragment te onthouden.

**Donderdag 19-01-'17**

* Fishbase idee definitief gecancelled, na onderzoek ontdekt dat dit veel teveel werk gaat zijn en dat ik dit nooit ga redden helaas.

* Eerste fragments afgemaakt, data kan nu worden opgehaald door NewDiveActivity, het kan alleen nog niet worden onthouden door Firebase.

* Fragments onthouden nu wel de data, het was een questie van het aantal tabs dat mag worden onthouden in de ViewPager verhogen.

**Vrijdag 20-01-'17**

* App tot nu toe gepresenteerd

**Zaterdag 21-01-'17**

* Rootfragments toegevoegd voor de fragments. Onderscheid gemaakt tussen "unfinished" en "finished" fragments.

* Begonnen aan TextDisplayManager om de ingevulde data in een verhaaltje te laten zien.

**Zondag 22-01-'17**

* Verdergewerkt aan TextDisplayManager

* Fragments werkend gekregen, er zijn nu "unfinished" en "finished" fragments.

**Maandag 23-01-'17**

* Begonnen aan het opslaan van Firebase data.

* Firebase helper class gemaakt die data ophaald en opslaat.

**Dinsdag 24-01-'17**

* De hele dag aan een "bug" gewerkt die zorgt dat het duiknummer steeds nul blijft. Blijken twee dingen te zijn: 1) ik ben zo stom geweest om in de DiveItem setter new_dive_number en dive_number om te wisselen. 2) Firebase listener is niet snel genoeg waardoor mijn app al doorgaat voor hij de data heeft.

**Woensdag 25-01-'17**

* Data slaat nu goed op op Firebase, begonnen aan DiveLog listview.

* Duiken van Firebase in de ListView gekregen, maar soms laadt het niet, soms wel.

**Donderdag 26-01-'17**

* Inmiddels ontdekt dat de Firebase helper class niet werkt, omdat Firebase op een andere thread zit dan de app en dus te langzaam is. Class verwijderd, data wordt nu opgehaald in de activities zelf.

* Divelog listview deed dingen dubbel, maar ik heb het opgelost door eerst steeds de list leeg te maken.

* LogDetails activity toegevoegd, die dezelfde fragments gebruikt als NewDiveActivity. Hele dag bezig geweest om dit goed te krijgen.

**Vrijdag 27-01-'17**

* LogDetails fragmenten aangepast zodat ze data ophalen van Firebase in plaats van dit mee te krijgen van de gebruiker zoals bij NewDiveActivity. Dit lukte eerst weer niet door die thread, dus ik ga hier een andere oplossing voor bedenken.

**Zaterdag 28-01-'17**

* Probleem met data opgelost door in plaats van de data steeds per duik op te halen, dit meteen in het begin van LogDetails activity te doen voor die hele duik en dit steeds mee te geven aan de rootfragment en dan aan het displayfragment.

**Zondag 29-01-'17**

* Begonnen met het maken van de textfiles die in HashMaps moeten worden gelezen voor de NitrogenCalculator class die moet gaan berekenen wat de stikstof is.

* Dit uiteindelijk helemaal afgemaakt en werkend gekregen.

**Maandag 30-01-'17**

* NitrogenCalculator afgemaakt.

* Dialog gemaakt dat de gebruiker zijn stikstoflevels laat zien.

**Dinsdag 31-01-'17**

* De hele dag kleine bugs gefixed.

* StatisticActivity gemaakt en gezorgd dat de statistieken worden opgehaald van Firebase.

**Woensdag 01-01-'17**

* Weer de hele dag kleine bugs eruit gehaald.

* Begonnen met het verslag.

**Donderdag 02-02-'17**

* De hele dag stress omdat de Calculator niet bleek te kloppen, maar uiteindelijk waren dit gewoon weer stomme typfouten.

* Verslag afgemaakt.

* Screenshots gemaakt.

* Readme aangepast.

* Eindelijk ingeleverd...
