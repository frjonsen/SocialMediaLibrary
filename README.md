# SocialMediaLibrary

## Allmän strategi

- Gå igenom relevanta APIer (Facebook och Twitter som start).
- Ta fram gemensam funktionalitet som kan vara relevant att bryta ut till ett biblioteksinteface
- Mycket önskad funktionelitet som saknas i något API bör lösas genom antingen direkt throughput till någon getRaw, om det finns, eller att direkt anropa APIet.
- Mycket önskad funkationalitet som endast finns i något API bör ändå göras tillgängligt genom att antingen ge användaren möjlighet att skippa interfacet, eller genom direkt åtkomst till det bakomliggande bibliteket (när något sådant används).

# Facebook

För alla calls som görs, ska ?fields= läggas till för att faktiskt säga vad man vill ha ut. För att få ut message text, används /v2.8/<message id>?fields=message

## Page Token
Page tokens varar bara i en timme. Biblioteket får därför ta in en user token med rätt permissions, och "skapa" sin egen page token vid behov.

1. Logga in som någon som har manage-rättigheter till pagen. Sätt de permissions som behövs.
2. /v2.8/me/accounts för att se IDn för alla pages man har tillgång till
3. /<page id>/fields=access_token ger access token för en page

### Message calls
- /v2.8/me/conversations ger IDn till konversationer med användare
- /v2.8/<conversation id>/messages ger IDn på alla meddelanden

### Övrigt
Insights, användbart, men bara för Facebook

# Testning
```
mvn -PallTests test                         //använd denna för att köra alla tester
mvn -Dtests=regular, apiCall        //använd denna för att välja vilka av typerna som skall köras(regular/apiCall)
```

## Facebook

På grund av hur Facebook hanterar IDn, där varje IDn är unika till varje App (dvs en användare har ett id för ett AppId, medan samma användare
har ett annat ID för ett annat AppID) kommer integrationstesterna för Facebook att fungera för något annat AppID än våran testapplikation.
# Future Plans
- Get latest post by a user