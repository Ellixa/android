package org.elis.jp4application;

import org.json.JSONException;
import org.json.JSONObject;

public class Food {

    private String nome;
    private double price;



    private int quantity=0;


        public Food(String nome, int quantity, double price){
            this.nome= nome;
            this.price= price;
            this.quantity=quantity;
            }

            public Food(JSONObject jsonfood ) throws JSONException {
            nome= jsonfood.getString("name");
            price= (double) jsonfood.getInt("price");


            }
    public double getPrice() {
        return price;
    }

    public String getNome() {
        return nome;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void increaseQuantity () {
        this.quantity ++;
    }
    public void decreaseQuantity(){
            this.quantity--;
    }
}
