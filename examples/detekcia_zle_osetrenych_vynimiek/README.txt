README

-------------------------------------------------------------------------------

POPIS:

Tento príklad sa zaoberá detekciou zle ošetrených výnimiek. Konkrétne kontroluje
výskyt prázdny "catch" blokov v zadanej triede.
Demonštruje funkcionalitu knižnice Javassist. Projekt je vo formáte Maven pod
názvom "detect-ex-treatement".

-------------------------------------------------------------------------------

ŠTRUKTÚRA PROJEKTU

Program sa skladá z balíkov application a examples. Dôležitá časť programu
vykonávajúca trasovanie a kontrolu ošetrenia výnimiek sa nachádza v balíku
application. Tento balík obsahuje triedy: Trace, CatchBlockTracer, CBDetector
a CBIndicesHandler. Jedinou triedou ku ktorej je potrebné pristupovať je trieda
Trace. Ostatné 3 triedy sú len pre interné využitie.

Pre testovanie funkčnosti programu odporúčam prejsť na odsek ↓ PRIPRAVENÉ PRÍKLADY ↓

-------------------------------------------------------------------------------

PRÍSTUP:

Pre trasovanie catch blokov ľubovoľnej triedy T je teda nutné len zavolať
metódu "traceCatchBlocks" triedy Trace s argumentom T.class. Následne
aplikácia skontroluje ošetrenie výnimiek všetkých metód a konštruktorov tejto
triedy, zaloguje výstup a vypíše výsledok v podobe počtu "podozrivých" catch
blokov a ich lokácie.

-------------------------------------------------------------------------------

PRIPRAVENÉ PRÍKLADY: 

Balík example obsahuje balíky simple a table, ktoré sú pripravenými príkladmi
na otestovanie funkcionality aplikácie. Balík example.simple obsahuje jednoduché
triedy Person a PersonFactory, ktorých výnimky budú kontrolované. Balík
example.tables obsahuje triedy demo aplikácie prevzatej z balíka
"Java Development Kit Demos and Samples". Spustením tried Demo
balíka "examples.simple", respektíve "examples.table" dôjde k volaniu
metódy "application.Tracer.traceCatchBlocks" na jednotlivých testovacích triedach.

Podrobnosti o testovacích príkladoch sa nachádzajú v komentároch
tried "example.simple.Demo" a "example.tables.Demo"

--------------------------------------------------------------------------------
