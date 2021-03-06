package com.example.bookstore.navigators;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.bookstore.activities.ConfirmOrderActivity;
import com.example.bookstore.activities.LoginActivity;
import com.example.bookstore.activities.OrderConfirmed;
import com.example.bookstore.activities.RegisterActivity;
import com.example.bookstore.activities.StoreActivity;

public class Navigator {
    public static void goToLogin(Activity activity){
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }
    public static void goToRegister(Activity activity){
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }
    public static void goToStore(Activity activity){
        activity.startActivity(new Intent(activity, StoreActivity.class));
    }
    public static void goToConfirmOrder(Activity activity){
        activity.startActivity(new Intent(activity, ConfirmOrderActivity.class));
    }
    public static void goToOrderConfirmed(Activity activity){
        activity.startActivity(new Intent(activity, OrderConfirmed.class));
    }
}
