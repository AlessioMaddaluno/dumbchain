# dumbchain
Minimal blockchain simulation.

** NOTE: This project is made for learning purpose. It's far away to be a working (and secure) blockchain. **

## Usage

To run the program you can either compile with an IDE or execute it directly by the .jar file. In both cases you'll need JavaFX library dependecy on your machine as it is not included in the Java SDK anymore. If you want execute the .jar directly you need to manually include the javafx libs manually: 

```sh
$ . java --module-path PATH_TO_JAVAFX --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar DumbchainGUI.jar    
```

Where "PATH_TO_JAVAFX" is the /lib javafx directory path.

More info about the GUI in "RELAZIONE.pdf" (English version soon).
