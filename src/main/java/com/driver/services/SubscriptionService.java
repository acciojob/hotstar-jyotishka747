package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){
        Subscription subscription= new Subscription();
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

//        String s = subscriptionEntryDto.getSubscriptionType().toString();
        int x = 0;
//        if (subscription.getSubscriptionType().equals("ELITE")) {
//
//            x = 1000 + 350 * subscription.getNoOfScreensSubscribed();
//        }
//        else if (subscription.getSubscriptionType().equals("PRO")) {
//            x = 800 + 250 * subscription.getNoOfScreensSubscribed();
//        }
//        else {
//            x = 500 + 200 * subscription.getNoOfScreensSubscribed();
//        }

        if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.ELITE)
        {
            x = 1000 + 350*subscriptionEntryDto.getNoOfScreensRequired();
            subscription.setTotalAmountPaid(x);
        }
        else if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.PRO)
        {
            x = 800 + 250*subscriptionEntryDto.getNoOfScreensRequired();
            subscription.setTotalAmountPaid(x);
        }else if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.BASIC)
        {
            x = 500 + 200*subscriptionEntryDto.getNoOfScreensRequired();
            subscription.setTotalAmountPaid(x);
        }
        subscription.setStartSubscriptionDate(new Date());
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setUser(user);
        user.setSubscription(subscription);

         userRepository.save(user);
        return x;


        //Save The subscription Object into the Db and return the total Amount that user has to pay


    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
       /* public SubscriptionType getSubscriptionType() {
            return subscriptionType;
        }*/

       User user = userRepository.findById(userId).get();
       Subscription subscription = user.getSubscription();
       int diffamt=0;
        if(subscription.getSubscriptionType() == SubscriptionType.ELITE)
            throw new Exception("Already the best Subscription");
        else if(subscription.getSubscriptionType() == SubscriptionType.PRO) {
            subscription.setSubscriptionType(SubscriptionType.ELITE);
            diffamt = (1000 + (350*subscription.getNoOfScreensSubscribed())) - subscription.getTotalAmountPaid();
            subscription.setTotalAmountPaid(1000 + (350*subscription.getNoOfScreensSubscribed()));
            subscription.setStartSubscriptionDate(new Date());
        }
        else if(subscription.getSubscriptionType()== SubscriptionType.BASIC){
            subscription.setSubscriptionType(SubscriptionType.PRO);
            diffamt = (800 + (250*subscription.getNoOfScreensSubscribed())) - subscription.getTotalAmountPaid();
            subscription.setTotalAmountPaid(800 + (250*subscription.getNoOfScreensSubscribed()));
            subscription.setStartSubscriptionDate(new Date());
        }


        return diffamt;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        List<Subscription> subscriptionList= subscriptionRepository.findAll();
        int rev=0;
        for(Subscription subscription:subscriptionList)
        {
            rev += subscription.getTotalAmountPaid();
        }

       /* for(int i=0;i<n;i++)
            x=x+subscriptionList[i].getTotalAmountPaid();*/



        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        return rev;
    }

}
