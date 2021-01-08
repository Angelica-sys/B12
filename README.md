# Skapa projektet 
Projektet innehåller två delar. En klient och en serverdel. 
B12 projektets klientdel är byggd i…. Klientdelen...

B12 projektets serverdel i IntelliJ IEDA och dessa instruktioner är anpassade till 
IntelliJ. Det går naturligtvis att öppna och köra servern i valfritt IDE. 
Serverndelen öppnas i IntelliJ genom att välja File | Open i main menyn. 
Välj projektet B12 i vald katalog. Välj sedan om du vill öppna projektet i en ny vy 
eller stanna i den vyn du är inne i. 

# mvn paket 
Aktuella Maven projekt skall finnas i projektets pom.xml. 

# Starta projektet
Klienten och servern behöver startas var för sig. Börja med att starta servern
genom att välja Run och sedan välj Run ‘Main’ i main menyn. Servern kommer då att 
anropa Livsmedelsverkets API. Om detta sker korrekt skrivs “200” ut i konsolen. 
Servern kommer då att börja hämta hem och behandla data från Livsmedelsverket. 
Detta kan ta lite tid. Så starta servern i god tid. När hämtningen är färdig skrivs
“Finish caching” och “Finish unmarshalling” ut i konsolen. 
Efter hämtningen från Livsmedelsverket öppnas kommunikationen med databasen SQLite. 
Nu kan du följs uppkopplingen till databasen i konsolen. Vid anslutning till SQLite skrivs
bland annat “Driver name: SQLiteJDBC” ut. Sedan startar projektets egna API och “API runs”
skrivs i konsolen. Nu är servern redo att ta emot förfrågningar från klienter. 

Starta sedan klienten…..Datan från Lisvmedelsverkets API innehåller 2161 livsmedel samt dess 
näringsämnen. Dessa livsmedel måste anges på korrekt för att få fram rätt mäng B12. 
Här är några exempel på livsmedel att använda: Äggstanning, Ostkräm, Tomat, Pitepalt, 
Majonnäs fett 80%,  Aioli, Pepparsås, Arraksboll, Chokladboll, Mandelkubb, Lamm kött rå, 
Lantpaté, Nöt lever stekt, Regnbågslax rå. Till projektet är även en lista med samtliga 
livsmedel bifogad se. All_FoodItems.txt


Efter processen är färdig skall både server och klient stängas ner. 
Servern stängs genom Run och sedan välj Stop ‘Main’ ...