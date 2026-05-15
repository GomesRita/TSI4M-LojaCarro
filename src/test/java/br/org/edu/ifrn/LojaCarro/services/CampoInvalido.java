package br.org.edu.ifrn.LojaCarro.services;

public class CampoInvalido extends IllegalArgumentException {
    public CampoInvalido(String campo) {
        super("Campo inválido: " + campo);
    }
}