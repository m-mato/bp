README

-------------------------------------------------------------------------------

POPIS:

Projekt "optimization" je určený na ukážku optimalizácie bajtkódu pomocou 
nástroja Javassist. Základnou myšlienkou je eliminácia nadbytočných [load] a
[store] inštrukcií pri opakovaní aritmetických operácií na rovnakej premennej 
typu [double].

Program postupne vykonáva optimalizáciu na všetkých metódach zadanej triedy. 
Keďže je po vykonaní zmien upravený aj "class" súbor modifikovanej triedy, nemá 
opätovné spúšťanie aplikácie na rovnakej triede žiadny efekt. V prípade potreby 
je teda nutné modifikovanú triedu pred opätovnou optimalizáciou prekompilovať.

Logika aplikácie je obsiahnutá triedami balíka [application]. Triedou pre prístup 
a spustenie optimalizácií je [ArithmeticOptimizer]. Volaním 
[optimizeClass(String classNameToOpt)] dôjde k optimalizácií každej metódy triedy 
definovanej argumentom [classNameToOpt]. Ostatné triedy balíka [application] sú 
prístupné len jeho triedam, nemali by byť teda referencované ani používané 
triedami mimo tohto balíka.

Aplikáciu je možné využiť pre optimalizáciu ľubovoľnej triedy. Ako príklad pre 
kontrolu funkčnosti uvádzam v balíku [example.simple] a [example.model] príklady, 
na ktorých je možné program otestovať.

--------------------------------------------------------------------------------

SPUSTENIE A POPIS TESTOVACÍCH TRIED BALÍKA [example]

1) [example.simple]

Balík [example.simple] obsahuje jednoduchú triedu [ArithmeticExamle]. Táto trieda
vykonáva niekoľko aritmetických operácií ktoré je možné v bajtkóde modifikovať. 

Spustiteľnou triedou príkladu je trieda [example.simple.Demo], ktorá najskôr používa
nemodifikovanú verziu [ArithmeticExamle], následne vykoná modifikácie pomocou programu 
popísaného vyššie. Na záver skontroluje funkčnosť modifikovanej verzie triedy 
[ArithmeticExamle].

Pre testovanie pomocou príkladu [example.simple] je nutné spustiť 
triedu [example.simple.Demo]

2) [example.model]

Balík [example.model] obsahuje príklad veľmi podobný testovaciemu príkladu aplikácie
[code-improvement]. Trieda [Triangle] reprezentuje pomocou triedy [Vertex] geometrický 
trojuholník. Obsahuje metódy [getLength] a [getArea], ktorých bajtkód je možné optimalizovať.

Spustiteľnou triedou je [example.model.Demo]. Jej spustením dôjde k optimalizácií a následnému
testovaniu funkčnosti triedy [Triangle].

Pre testovanie pomocou príkladu [example.model] je nutné spustiť triedu [exampe.model.Demo]

Bližší popis jednotlivých tried a metód sa nachádza v Javadocu a komentároch aplikácie.
