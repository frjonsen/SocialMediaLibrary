# SocialMediaLibrary

## Allmän strategi

- Gå igenom relevanta APIer (Facebook och Twitter som start).
- Ta fram gemensam funktionalitet som kan vara relevant att bryta ut till ett biblioteksinteface
- Mycket önskad funktionelitet som saknas i något API bör lösas genom antingen direkt throughput till någon getRaw, om det finns, eller att direkt anropa APIet.
- Mycket önskad funkationalitet som endast finns i något API bör ändå göras tillgängligt genom att antingen ge användaren möjlighet att skippa interfacet, eller genom direkt åtkomst till det bakomliggande bibliteket (när något sådant används).