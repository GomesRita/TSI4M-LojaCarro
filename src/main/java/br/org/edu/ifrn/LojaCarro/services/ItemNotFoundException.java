package br.org.edu.ifrn.LojaCarro.services;

public class ItemNotFoundException extends IllegalArgumentException  {
    public String ItemNotFoundException(){
        return "Carro inexistente";
    }
}
