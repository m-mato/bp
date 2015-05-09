README

-------------------------------------------------------------------------------

POPIS:

Tento príklad demonštruje funkcionalitu nástroja Byteman.
Konkrétne je ukážkou detekcie všetkých výnimiek, ktoré sú počas behu programu
volané pomocou kľúčového slova throw, v bajtkóde reprezentovaného inštrukciou
athrow. Ku každému výskytu throw v zadanej triede je pridaný výpis: 

	System.out.println("Detected athrow, exception: " + [exception]);

Zavolanie výnimky teda spôsobí výpis tejto detekcie.
Funkčnosť tohto skriptu je demonštrovaná na príklade fileChooser, konkrétne
jeho triede ExampleFileSystemView. Tento príklad je súčasťou Java SE
Development Kit Demos and Samples.

Nasledujúci odsek popisuje postup spustenia príkladu s použitím
skriptu na detekciu výnimiek.

---------------------------------------------------------------------------------------------------------

POSTUP:



1..Spustenie Aplikácie fileChooser s byteman agentom..)

  Aplikácia FileChooserDemo sa nachádza v podadresári fileChooser.
  Spustiteľná trieda sa nazýva: "FileChooserDemo.class"
  Túto triedu je nutné spustiť z adresára fileChoosers so zapnutým Byteman agentom- príkazom:

	java -javaagent:../../.byteman/lib/byteman.jar=listener:true,boot:../../.byteman/lib/byteman.jar -Dorg.jboss.byteman.transform.all FileChooserDemo

  Po zadaní tohto príkazu sa spustí GUI aplikácie.



2..Načítanie skriptov..)

  Nasledujúci príkaz je nutné spustiť v ďalšom terminály.
  Po spustení loadScripts.sh príkazom:

	./loadScripts.sh fileChooser/ExampleFileSystemView.class
  (loadScripts.sh sa nachádza priamo v adresári detekcia_volania_vynimiek)

  sa do triedy ExampleFileSystemView aplikácie FileChooser načítajú pravidlá (skripty) Bytemana
  na detekciu výnimiek.
  Tento skript vloží pravidlo na detekciu výnimky do každej metódy tejto triedy
  vrátane konštruktora.
  Skript vytvára pomocný súbor rules.btm, preto je nutné mať povolený zápis do jeho adresára.
  Výstup príkazu by mal vyzerať približne nasledovne:
	----> ExampleFileSystemView
	+ [<init>]
	+ [createNewFolder]
	+ [getRoots]
	+ [getSystemDisplayName]
	install rule detect throw method <init> class ExampleFileSystemView
	install rule detect throw method createNewFolder class ExampleFileSystemView
	install rule detect throw method getRoots class ExampleFileSystemView
	install rule detect throw method getSystemDisplayName class ExampleFileSystemView



3..Vyvolanie výnimky..)

  V poslednom kroku je nutné už len vyvolať nejakú výnimku triedy ExampleFileSystemView
  aplikácie FileChooserDemo.
  Jedným zo spôsobov (z GUI aplikácie) je:
	1. Zaškrtnúť položku "Use FileSystemView" v "Display Options"
	2. Spustiť prehľadávanie súborov kliknutím na "Show FileChooser" 
	3. Pokúsiť sa vytvoriť dva adresáre z názvom "New folder" 
  Následne aplikácia upozorní užívateľa na chybu
  V (prvom) termináli so spustenou aplikáciou FileChooser Byteman upozorní na detekovanú výnimku.

------------------------------------------------------------------------------------------------------

Týmto spôsobom je možné detekovať výnimky ľubovoľnej triedy / tried zadaných do skriptu loadScripts.sh
V prípade viacerých tried je oddeľovačom medzera, napríklad:

	loadScripts.sh Trieda1.class Trieada2.class ...

