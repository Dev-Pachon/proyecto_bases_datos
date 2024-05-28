package application.banco.controller;

import application.banco.service.IBitacoraService;
import application.banco.service.serviceImpl.BitacoraService;

public class BitacoraController {

    private final IBitacoraService bitacoraService;

    public BitacoraController() {
        this.bitacoraService = new BitacoraService();
    }

    public void buscarTodos() {
        bitacoraService.buscarTodos().forEach(System.out::println);
    }

    public void buscarPorId() {
        System.out.println(bitacoraService.buscarPorId(1));
    }

}
