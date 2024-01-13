package fr.pantheonsorbonne.ufr27.miage.exception;

public class InternalServorException extends RuntimeException{
    public InternalServorException(){
        super("Erreur interne du serveur");
    }
}
