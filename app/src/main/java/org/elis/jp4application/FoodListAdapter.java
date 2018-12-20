package org.elis.jp4application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter {

    private LayoutInflater mInflter;
    private ArrayList<Food> data= new ArrayList<>();

    public OnQuantityChange onQuantityChange;




    public interface OnQuantityChange {
        public void onItemAdded(double price);
        public void onItemRemoved(double price);
    }



    public void setOnQuantityChange(OnQuantityChange onQuantityChange){
        this.onQuantityChange = onQuantityChange;
    }


    public FoodListAdapter(Context context,ArrayList<Food> data ) {
        this.data = data;
        mInflter = LayoutInflater.from(context);
    }
    public FoodListAdapter(Context context) {
        mInflter = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Food> data) {
        this.data= data;
        notifyDataSetChanged();
    }



    @NonNull
    @Override //crea un oggetto che tiene in memoria gli oggetti della lista
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= mInflter.inflate(R.layout.row_item,viewGroup, false);
        return new FoodViewHolder(v);
    }

    @Override  //onBindViewHolder è un metodo di cortesia che ti dice dove si trova l'oggetto in quale posizione
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FoodViewHolder foodViewHolder =(FoodViewHolder)viewHolder; //castiamo
        Food currentFood = data.get(i);

        foodViewHolder.productName.setText(data.get(i).getNome());
        foodViewHolder.productQuantity.setText(String.valueOf(data.get(i).getQuantity()));
        foodViewHolder.productPrice.setText(String.valueOf(data.get(i).getPrice()));



    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    private void addItem(){


    }
    private void removeItem(){


    }
    //classe innestata che modella la view del singolo elemento della lista
    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productName, productPrice, productQuantity;
        public Button addBtn, removeBtn;

        public FoodViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.item_name);
            productPrice = itemView.findViewById(R.id.item_price);
            productQuantity = itemView.findViewById(R.id.item_quantity);

            addBtn = itemView.findViewById(R.id.item_plus);

            removeBtn = itemView.findViewById(R.id.item_minus);

            addBtn.setOnClickListener(this);
            removeBtn.setOnClickListener(this);

        }

        public void onClick(View view) {
            if (view.getId() == R.id.item_plus) {
                Food food = data.get(getAdapterPosition());
                food.increaseQuantity();
                notifyItemChanged(getAdapterPosition());

                onQuantityChange.onItemAdded(food.getPrice());


            } else if (view.getId() == R.id.item_minus) {
                Food food = data.get(getAdapterPosition());
                Integer itemQuantity =food.getQuantity();
                if (itemQuantity>0) {
                    food.decreaseQuantity();
                    notifyItemChanged(getAdapterPosition());

                    onQuantityChange.onItemRemoved(food.getPrice());
                }
            }
        }


    }
}
