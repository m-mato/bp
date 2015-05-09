README

-------------------------------------------------------------------------------

POPIS:

Projekt code-improvement je určený na zlepšenie a sprehľadnenie produkčného
kódu. 

Aplikácia je Java projekt typu Maven. Obsahuje 2 balíky - "application"
a "example". Balík "application" obsahuje logiku aplikácie, balík
"example" je len jednoduchým príkladom na overenie funkčnosti programu.

Program pracuje s dvoma triedami - nazvime ich <C1> a <C2>. 
Nahrádza priame volanie atribútov triedy <C1> v triede <C2> volaním
"get" a "set" metód, ktoré generuje. Triedy <C1> a <C2> môžu reprezentovať tú
istú triedu, ale aj dve rôzne v závislosti typu: <C2> číta / zapisuje do
atribútov <C1>.

Program modifikuje class súbory oboch tried nasledujúcim spôsobom:
- Do triedy <C1> pridáva "get" a "set" metódy sprístupňujúce jej atribúty
- Operácie priameho čítania a zápisu atribútov triedy <C1> v triede <C2> nahrádza
  volaním generovaných "get" a "set" metód.
Pri opakovanej aplikácií programu na už modifikované triedy nebude program nanovo
generovať prístupové metódy - nič zaujímavé sa nestane. V prípade potreby opätovného
generovania metód na už modifikovaných triedach je teda nutné tieto triedu predtým
rekompilovať do pôvodného stavu.

Uskutočnené zmeny sa neprejavia v zdrojovom kóde a nijako neovplyvnia vnútornú
logiku modifikovaných tried. Zmeny volania a generované metódy je možné pozorovať
v class súboroch tried - príkaz: javap -c [cestaClassSuboru].

Postup spustenia programu je popísaný nižšie.
Prístupová metóda programu je "application.FieldChecker.fixFieldsAccess()".
Triedy pre modifikáciu sú špecifikované v argumentoch konštruktora triedy
"application.FieldChecker".
Popis funkcionality jednotlivých tried je v JavaDocu.

Funkčnosť aplikácie je možné testovať spustením triedy [example.Demo], prípadne
aplikovať nižšie uvedený postup pre spustenie na ľubovoľných iných
triedach.

--------------------------------------------------------------------------------

SPUSTENIE PROGRAMU PRE VŠEOBECNÉ TRIEDY <C1> a <C2>:

1) Pred každým / po každom spustení programu (prepisuje class súbory) je nutné
   rebuildnuť balík example, aby sa class súbory dostali do pôvodného stavu.

2) Predpokladajme že trieda <C1> obsahuje atribúty, ktoré sú priamo volané
   triedou <C2>. Pre generovanie "get" a "set" metód pre atribúty a zmenu
   priameho volania na volanie generovaných  "get" a "set" metód je potrebné:

3) Vytvoriť objekt typu application.FieldChecker:
   [FiedChecker f = new FieldChecker(<C1>, <C2>, path);]
   Kde [path] je cesta k balíku obsahujúcemu "class" súbory triedy <C2>.
   Ak sú triedy súčasťou projektu "code-improvement" nieje potrebné
   argument "path" uvádzať.

4) Volaním metódy [f.fixFieldsAccess()] prebehne celá logika aplikácie popísaná
   vyššie. Informácie o pridaných metódach a modifikovaných referenciách sa
   zobrazia na štandardný výstup.

----------------------------------------------------------------------------------

OVERENIE FUNKČNOSTI PROGRAMU V PRÍPADE TRIED BALÍKA EXAMPLE:

Overenie správnosti zmeny je možné taktiež porovnaním zmeny class súborov.
   
 ** Do súboru target/classes/example/Triangle.class pribudli "get" a "set"
    metódy typu:
   
        public static example.Vertex getAStatGenerated(java.lang.Object);
	    Code:
	       0: aload_0
	       1: checkcast     #18                 // class example/Triangle
	       4: astore_1
	       5: aload_1
	       6: getfield      #35                 // Field example/Triangle.a:Lexample/Vertex;
	       9: areturn

	public static void setAStatGenerated(java.lang.Object, example.Vertex);
	    Code:
	       0: aload_0
	       1: checkcast     #18                 // class example/Triangle
	       4: astore_2
	       5: aload_2
	       6: aload_1
	       7: putfield      #35                 // Field example/Triangle.a:Lexample/Vertex;
	      10: return

   
 ** V súbore target/classes/example/Initializer.class bol nahradený priamy zápis do atribútov:
       8: aload_3
       9: aload_0
      10: putfield      #8                  // Field example/Triangle.a:Lexample/Vertex;
      13: aload_3
      14: aload_1
      15: putfield      #9                  // Field example/Triangle.b:Lexample/Vertex;
      18: aload_3
      19: aload_2
      20: putfield      #10                 // Field example/Triangle.c:Lexample/Vertex;
   
    Volaním set metód:
       8: aload_3
       9: aload_0
      10: invokestatic  #47                 // Method example/Triangle.setAStatGenerated:(Ljava/lang/Object;Lexample/Vertex;)V
      13: aload_3
      14: aload_1
      15: invokestatic  #50                 // Method example/Triangle.setBStatGenerated:(Ljava/lang/Object;Lexample/Vertex;)V
      18: aload_3
      19: aload_2
      20: invokestatic  #53                 // Method example/Triangle.setCStatGenerated:(Ljava/lang/Object;Lexample/Vertex;)V


--------------------------------------------------------------------------------------------------------------------------
