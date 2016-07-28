package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int numberOfCoffees = 0;
    final double COFFEE_PRICE = 5.00;
    final double TOPPING_COST = 0.50;

    /*
    * Ensures only a positive integer number of coffees exist
    * */

    private int checkQuantityBounds(int number){
        if (number < 0){
            number = 0;
        }
        else if (number > 100){
            number = 100;
        }
        return number;
    }
    /**
     * Calculates Price
     * */
    private double calculatePrice(int numberOfCoffees, boolean hasWhippedCream, boolean hasChocolate){
        numberOfCoffees = checkQuantityBounds(numberOfCoffees);
        int toppings = 0;
        if (hasWhippedCream){ toppings++;}
        if (hasChocolate){ toppings++;}
        return numberOfCoffees*COFFEE_PRICE + toppings*TOPPING_COST;
    }
    public void submitOrder(View view){
        // Toppings
        CheckBox hasWhipCheckBox = (CheckBox) findViewById(R.id.checkbox_hasWhippedCream);
        boolean hasWhippedCream = hasWhipCheckBox.isChecked();
        CheckBox hasChocolateBox = (CheckBox) findViewById(R.id.checkbox_hasChocolate);
        boolean hasChocolate = hasChocolateBox.isChecked();

        // Total cost and display messages
        double totalCost = calculatePrice(numberOfCoffees, hasWhippedCream, hasChocolate);
        String message = createOrderSummary(totalCost, hasWhippedCream, hasChocolate);
        displayMessage(message);

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Re: Just Java Order Summary for " + getUserName());
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    private String createOrderSummary(double totalCost, boolean hasWhippedCream, boolean hasChocolate){
        String message = getString(R.string.Name) + ": " + getUserName();
        message += "\nAdd "+ getString(R.string.whipped_cream) + "? " + hasWhippedCream;
        message += "\nAdd " + getString(R.string.chocolate) + "? " + hasChocolate;
        numberOfCoffees = checkQuantityBounds(numberOfCoffees);
        message += "\n" + getString(R.string.quantity) + ": " + numberOfCoffees;
        message += "\n" + getString(R.string.total_cost) + ": " + "$" + totalCost;
        message += "\n" + getString(R.string.thank_you);
        return message;
    }
    /*
    * Increments coffee value on click
    */
    public void increment(View view){
        ++numberOfCoffees;
        displayQuantity(numberOfCoffees);
    }

    /*
    * Decrements coffee value on click
    */
    public void decrement(View view){
        --numberOfCoffees;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    public void displayQuantity(int number){
        number = checkQuantityBounds(number);
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String getUserName(){
        EditText userNameTextView = (EditText) findViewById(R.id.user_name);
        String name = userNameTextView.getText().toString();
        return name;
    }
}
