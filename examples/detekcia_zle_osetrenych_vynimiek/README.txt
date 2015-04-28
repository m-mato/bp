README

-------------------------------------------------------------------------------

POPIS:

Tento príklad sa zaoberá dekciou zle ošetrených výnimiek. Konkrétne kontroluje
výkyt prázdny "catch" blokov v zadanej triede.
Demonštruje funcionalitu knižnice Javassist. Projekt je vo formáte Maven pod
názvom "detect-ex-treatement".

-------------------------------------------------------------------------------

ŠTRUKTÚRA PROJEKTU

Program sa skladá z balíkov application a examples. Dôležitá časť programu
vykonávajúca trasovanie a kontrolo ošetrenia výnimiek sa nachádza v balíku
application. Tento balík obsahuje triedy: Trace, CatchBlockTracer, CBDetector
a CBIndicesHandler. Jedinou triedo ku ktorej je potrebné pristupovať je trieda
Trace. Ostatné 3 triedy sú len pre interné využitie.

Pre testovanie funkčnosti programu odporúčam prejsť na odsek ↓ PRIPRAVENÉ PRÍKLADY ↓

-------------------------------------------------------------------------------

PRÍSTUP:

Pre trasovanie catch blokov ľubovoľnej triedy T je teda nutné len zavolať
metódu "traceCatchBlocks" triedy Trace s argumentom T.class. Následne
aplikácia skontroluje ošetrenie výnimiek všetkých meód a konštruktorov tejto
triedy, zaloguje výstup a vypíše výsldok v podobe počtu "podozrivých" catch
blokov a ich lokácie.

-------------------------------------------------------------------------------

PRIPRAVENÉ PRÍKLADY: 

Balík example obsahuje balíky simple a table, ktoré su pripravenými príkladmy
na otestovanie funkcionality aplikácie. Balík example.simple obsahuje jednoducé
triedy Person a PersonFactory, ktorých výnimky budú kontorlované. Balík
example.tables obsahuje triedy demo aplikácie prevzatej z balíka
"Java Development Kit Demos and Samples". Spustením tried Demo
balíka "examples.simple", respektíve "examples.table" dôjde k volaniu
metódy "application.Tracer.traceCatchBlocks" na jednotlivých testovacích triedach.

Podrobnosti o testovacích príkladoch sa nachádzajú v komentároch
tried "example.simple.Demo" a "example.tables.Demo"

--------------------------------------------------------------------------------
