package com.example.rpodmp.entities;

public class MiningDevice {
    private String _name;
    private int _energyConsumption;
    private double _profit;

    private double _price;

    public MiningDevice(String name, double price, int energyConsumption, double profit){
        _name = name;
        _price = price;
        _energyConsumption = energyConsumption;
        _profit = profit;
    }

    public String getName(){
        return _name;
    }

    public void setName(String name){
        this._name = name;
    }

    public int get_energyConsumption() {
        return _energyConsumption;
    }

    public void set_energyConsumption(int _energyConsumption) {
        this._energyConsumption = _energyConsumption;
    }

    public double get_profit() {
        return _profit;
    }

    public void set_profit(int _profit) {
        this._profit = _profit;
    }

    public double get_price() {
        return _price;
    }

    public void set_price(double _price) {
        this._price = _price;
    }

    @Override
    public String toString() {
        return _name;
    }
}
