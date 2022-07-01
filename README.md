# ing-sw-2022-longo-mole-negro

## Gruppo 3
 Virginia Longo
 
 Marco Molè
 
 Pierluigi Negro
 
 
 
## Funzionalità Aggiuntive
- Partite Multiple
- Partita con 4 giocatori
- 12 Carte in modalità esperto

## Come eseguire il gioco

### Server

``` bash
java -jar Main.jar [-d] -s [PORT]
```
- il parametro PORT è opzionale, il default è 9999
- il flag opzionale -d fa partire in modalità debug il server. In modalità debug il server logga tutti i messaggi della room e dei client handler. I file di log saranno creati nello stesso path di esecuzione del server.

### CLI

``` bash
java -jar Main.jar -c -cli
```

### GUI

``` bash
java -jar Main.jar -c -gui
```
